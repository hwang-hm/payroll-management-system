package retirement.severancepay.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import employee.dao.EmpDao;
import employee.dto.EmployeeDetailData;
import jdbc.connection.ConnectionProvider;
import retirement.dao.SeverancePayDao;

public class SeveranceCalculateService {

	private EmpDao empDao = new EmpDao();
	private SeverancePayDao severancePayDao = new SeverancePayDao();

	//급여 내역 불러오기
	// 給与明細の読込
	public SeveranceSalaryResult loadSalary(int empId) {

		try (Connection conn = ConnectionProvider.getConnection()) {

			EmployeeDetailData emp = empDao.selectByIdJoin(conn, empId);

			if (emp == null) {
				return null;
			}

			Date calculationDate = getCalculationDate(emp);

			return calculateSalary(conn, empId, calculationDate);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	// 전체 퇴직금 계산
	// 退職金の全体計算
	public SeveranceCalculateResult calculateSeverance(SeveranceCalculateRequest request) {

		int empId = request.getEmpId();

		try (Connection conn = ConnectionProvider.getConnection()){

			// 1. 사원 조회
			// 1. 社員照会
			EmployeeDetailData emp = empDao.selectByIdJoin(conn, empId);

			// 2. 계산 기준일
			// 2. 計算基準日
			Date calculationDate = getCalculationDate(emp);

			// 3. 급여내역 및 3개월 급여 합계 계산
			// 3. 給与明細および3か月給与合計の計算
			SeveranceSalaryResult salaryResult = calculateSalary(conn, empId, calculationDate);

			// 4. 기타 과세소득 3개월분
			// 4. その他課税所得 3か月分
			long otherTaxableIncomeThreeMonthTotal = calculateOtherTaxableIncome(request);

			// 5. 3개월 총계
			// 5. 3か月合計
			long threeMonthTotal = salaryResult.getSalaryTotal() + otherTaxableIncomeThreeMonthTotal;

			// 6. 1일 평균임금
			// 6. 1日平均賃金
			long dailyAverageWage = calculateDailyAverageWage(threeMonthTotal, salaryResult.getTotalCalculationDays());

			// 7. 실제 적용할 1일 임금
			// 7. 実際に適用する1日賃金
			long appliedDailyWage = getAppliedDailyWage(request, dailyAverageWage);

			// 8. 근속일수
			// 8. 勤続日数
			long serviceDays = calculateServiceDays(emp, calculationDate);

			// 9. 기본 퇴직금
			// 9. 基本退職金
			long severanceAmount = calculateSeveranceAmount(appliedDailyWage, serviceDays);

			// 10. 퇴직위로금
			// 10. 退職慰労金
			long retirementBonus = request.getRetirementBonus() != null ? request.getRetirementBonus() : 0;

			// 11. 세전 퇴직급여 총액
			// 11. 税引前退職給与総額
			long totalRetirementPay = severanceAmount + retirementBonus;

			// 12. 세금 계산
			// 12. 税金計算
			SeveranceTaxResult taxResult = calculateTax(request, totalRetirementPay, serviceDays);

			// 13. 퇴직 과세연도
			// 13. 退職課税年度
			int retirementTaxYear = calculationDate.toLocalDate().getYear();

			// 14. 최종 결과 반환
			// 14. 最終結果の返却
			return createCalculateResult(threeMonthTotal, dailyAverageWage, retirementTaxYear, taxResult);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 계산 기준일 - 퇴직일이 있으면 퇴직일, 없으면 오늘
	// 計算基準日 - 退職日があれば退職日、なければ今日
	private Date getCalculationDate(EmployeeDetailData emp) {

		if (emp.getRetireDate() != null) {
			return emp.getRetireDate();
		}

		return new Date(System.currentTimeMillis());
	}

	// 최근 3개월 급여내역 계산
	// 直近3か月給与明細の計算
	private SeveranceSalaryResult calculateSalary(Connection conn, int empId, Date calculationDate) throws SQLException {

		// 퇴직금 산정 시작일
		// 退職金算定開始日
		LocalDate startLocalDate = calculationDate.toLocalDate().minusMonths(3).plusDays(1);

		Date startDate = Date.valueOf(startLocalDate);

		List<SeveranceSalaryData> salaryList = severancePayDao.selectSalaryForSeverance(conn, empId, startDate,
				calculationDate);

		long salaryTotal = 0;
		int totalCalculationDays = 0;

		for (SeveranceSalaryData salary : salaryList) {

			LocalDate salaryStart = salary.getStartDate().toLocalDate();
			LocalDate salaryEnd = salary.getEndDate().toLocalDate();
			LocalDate calcStart = startDate.toLocalDate();
			LocalDate calcEnd = calculationDate.toLocalDate();

			// 실제 산정 시작일
			// 実際の算定開始日
			LocalDate actualStart;

			if (salaryStart.isAfter(calcStart)) {
				actualStart = salaryStart;
			} else {
				actualStart = calcStart;
			}

			// 실제 산정 종료일
			// 実際の算定終了日
			LocalDate actualEnd;

			if (salaryEnd.isBefore(calcEnd)) {
				actualEnd = salaryEnd;
			} else {
				actualEnd = calcEnd;
			}

			// 실제 산정일수
			// 実際の算定日数
			int calculationDays = (int) ChronoUnit.DAYS.between(actualStart, actualEnd) + 1;

			// 해당 급여기간 전체 일수
			// 該当給与期間の全日数
			int fullSalaryDays = (int) ChronoUnit.DAYS.between(salaryStart, salaryEnd) + 1;

			// 실제 산정기간에 반영할 급여
			// 実際の算定期間に反映する給与
			long calculatedPay;

			if (calculationDays == fullSalaryDays) {
				calculatedPay = salary.getTotalPay();
			} else {
				calculatedPay = (long) salary.getTotalPay() * calculationDays / fullSalaryDays;
			}

			salary.setCalculationStartDate(Date.valueOf(actualStart));
			salary.setCalculationEndDate(Date.valueOf(actualEnd));
			salary.setCalculationDays(calculationDays);
			salary.setCalculatedPay(calculatedPay);
			totalCalculationDays += calculationDays;
			salaryTotal += calculatedPay;
		}
		return new SeveranceSalaryResult(salaryList, totalCalculationDays, salaryTotal);
	}

	// 기타 과세소득 3개월분
	// その他課税所得 3か月分
	private long calculateOtherTaxableIncome(SeveranceCalculateRequest request) {

		long total = 0;

		if (request.getOtherTaxableIncomes() == null) {
			return 0;
		}

		for (OtherTaxableIncomeRequest income : request.getOtherTaxableIncomes()) {
			if (income.getAmount() != null) {
				long threeMonthAmount = (long) income.getAmount() * 3 / 12;
				total += threeMonthAmount;
			}
		}
		return total;
	}

	// 1일 평균임금
	// 1日平均賃金
	private long calculateDailyAverageWage(long threeMonthTotal, int totalCalculationDays) {
		
		if (totalCalculationDays <= 0) {
			return 0;
		}
		return threeMonthTotal / totalCalculationDays;
	}

	// 실제 적용할 1일 임금 - 통상임금 입력 시 통상임금 우선
	// 実際に適用する1日賃金 - 通常賃金入力時は通常賃金を優先
	private long getAppliedDailyWage(SeveranceCalculateRequest request, long dailyAverageWage) {
		
		if (request.getDailyOrdinaryWage() != null) {
			return request.getDailyOrdinaryWage();
		}
		return dailyAverageWage;
	}

	// 근속일수
	// 勤続日数
	private long calculateServiceDays(EmployeeDetailData emp, Date calculationDate) {

		LocalDate hireDate = emp.getHireDate().toLocalDate();
		return ChronoUnit.DAYS.between(hireDate, calculationDate.toLocalDate()) + 1;
	}

	// 기본 퇴직금
	// 基本退職金
	private long calculateSeveranceAmount(long appliedDailyWage, long serviceDays) {

		return appliedDailyWage * 30 * serviceDays / 365;
	}

	// 세금 계산
	// 税金計算
	private SeveranceTaxResult calculateTax(SeveranceCalculateRequest request, long totalRetirementPay,
			long serviceDays) {

		// 세금 계산용 근속연수
		// 税金計算用勤続年数
		int serviceYears = (int) Math.ceil(serviceDays / 365.0);

		if (serviceYears < 1) {
			serviceYears = 1;
		}

		// 비과세 퇴직급여
		// 非課税退職給与
		long nonTaxableRetirementPay = 0;

		if (request.getNonTaxableRetirementPay() != null) {
			nonTaxableRetirementPay = request.getNonTaxableRetirementPay();
		}

		// 퇴직소득
		// 退職所得
		long retirementIncome = totalRetirementPay - nonTaxableRetirementPay;

		if (retirementIncome < 0) {
			retirementIncome = 0;
		}

		// 근속연수공제
		// 勤続年数控除
		long serviceYearDeduction = calculateServiceYearDeduction(serviceYears);

		// 근속연수공제 후 금액
		// 勤続年数控除後の金額
		long amountAfterServiceDeduction = retirementIncome - serviceYearDeduction;

		if (amountAfterServiceDeduction < 0) {
			amountAfterServiceDeduction = 0;
		}

		// 환산급여
		// 換算給与
		long convertedSalary = amountAfterServiceDeduction * 12 / serviceYears;

		// 환산급여공제
		// 換算給与控除
		long convertedSalaryDeduction = calculateConvertedSalaryDeduction(convertedSalary);

		// 과세표준
		// 課税標準
		long taxBase = convertedSalary - convertedSalaryDeduction;

		if (taxBase < 0) {
			taxBase = 0;
		}

		// 환산산출세액
		// 換算算出税額
		long convertedCalculatedTax = calculateConvertedTax(taxBase);

		// 퇴직소득 산출세액
		// 退職所得算出税額
		long calculatedTax = convertedCalculatedTax * serviceYears / 12;

		// 퇴직소득세
		// 退職所得税
		long retirementIncomeTax = calculatedTax;

		// 기납부세액
		// 既納付税額
		long prepaidTax = 0;

		if (request.getPrepaidTax() != null) {
			prepaidTax = request.getPrepaidTax();
		}

		// 세액공제
		// 税額控除
		long taxCredit = 0;

		if (request.getTaxCredit() != null) {
			taxCredit = request.getTaxCredit();
		}

		retirementIncomeTax = retirementIncomeTax - prepaidTax - taxCredit;

		if (retirementIncomeTax < 0) {
			retirementIncomeTax = 0;
		}

		// 지방소득세
		// 地方所得税
		long localIncomeTax = retirementIncomeTax * 10 / 100;

		// 과세이연은 일단 없는 경우
		// 課税繰延は一旦ない場合
		long deferredRetirementTax = 0;
		long deferredLocalTax = 0;

		// 과세대상 퇴직급여
		// 課税対象退職給与
		long taxableRetirementPay = totalRetirementPay - nonTaxableRetirementPay;

		if (taxableRetirementPay < 0) {
			taxableRetirementPay = 0;
		}

		// 차감원천징수세액
		// 差引源泉徴収税額
		long withholdingTax = retirementIncomeTax + localIncomeTax;

		// 실수령액
		// 実受取額
		long netPayment = totalRetirementPay - withholdingTax;

		return new SeveranceTaxResult(retirementIncome, calculatedTax, retirementIncomeTax, localIncomeTax,
				deferredRetirementTax, deferredLocalTax, taxableRetirementPay, withholdingTax, netPayment);
	}

	// 근속연수공제
	// 勤続年数控除
	private long calculateServiceYearDeduction(int serviceYears) {

		if (serviceYears <= 5) {
			return serviceYears * 1000000L;

		} else if (serviceYears <= 10) {
			return 5000000L + (serviceYears - 5) * 2000000L;

		} else if (serviceYears <= 20) {
			return 15000000L + (serviceYears - 10) * 2500000L;

		} else {
			return 40000000L + (serviceYears - 20) * 3000000L;
		}
	}

	// 환산급여공제
	// 換算給与控除
	private long calculateConvertedSalaryDeduction(long convertedSalary) {

		if (convertedSalary <= 8000000L) {
			return convertedSalary;

		} else if (convertedSalary <= 70_000_000L) {
			return 8000000L + (convertedSalary - 8000000L) * 60 / 100;

		} else if (convertedSalary <= 100_000_000L) {
			return 45200000L + (convertedSalary - 70000000L) * 55 / 100;

		} else if (convertedSalary <= 300_000_000L) {
			return 61700000L + (convertedSalary - 100000000L) * 45 / 100;

		} else {
			return 151700000L + (convertedSalary - 300000000L) * 35 / 100;
		}
	}

	// 환산산출세액
	// 換算算出税額
	private long calculateConvertedTax(long taxBase) {

		if (taxBase <= 14000000L) {
			return taxBase * 6 / 100;

		} else if (taxBase <= 50000000L) {
			return taxBase * 15 / 100 - 1260000L;

		} else if (taxBase <= 88_000_000L) {
			return taxBase * 24 / 100 - 5760000L;

		} else if (taxBase <= 150_000_000L) {
			return taxBase * 35 / 100 - 15440000L;

		} else if (taxBase <= 300_000_000L) {
			return taxBase * 38 / 100 - 19940000L;

		} else if (taxBase <= 500_000_000L) {
			return taxBase * 40 / 100 - 25940000L;
			
		} else if (taxBase <= 1_000_000_000L) {
			return taxBase * 42 / 100 - 35940000L;

		} else {
			return taxBase * 45 / 100 - 65940000L;
		}
	}

	// 최종 계산 결과 생성
	// 最終計算結果の生成
	private SeveranceCalculateResult createCalculateResult(long threeMonthTotal, long dailyAverageWage,
			int retirementTaxYear, SeveranceTaxResult taxResult) {

		return new SeveranceCalculateResult(threeMonthTotal, dailyAverageWage, retirementTaxYear,
				taxResult.getRetirementIncome(), taxResult.getCalculatedTax(), taxResult.getRetirementIncomeTax(),
				taxResult.getLocalIncomeTax(), taxResult.getDeferredRetirementTax(), taxResult.getDeferredLocalTax(),
				taxResult.getTaxableRetirementPay(), taxResult.getWithholdingTax(), taxResult.getNetPayment());
	}
}