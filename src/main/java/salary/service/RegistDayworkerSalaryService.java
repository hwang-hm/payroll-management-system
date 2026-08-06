package salary.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dao.SalaryDao;
import salary.dao.SalaryDetailDao;
import salary.model.Salary;
import salary.model.SalaryDetail;
import salary.service.request.RegistSalaryRequest;

public class RegistDayworkerSalaryService {

	private SalaryDao salaryDao = new SalaryDao();
	private SalaryDetailDao detailDao = new SalaryDetailDao();

	public Integer regist(RegistSalaryRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			// 1. SALARY (급여 헤더) 데이터 생성 및 저장
			Salary salary = toSalary(req);
			boolean isSalary = salaryDao.insertDayworkerSalary(conn, salary);
			if (!isSalary) {
				throw new RuntimeException("fail to insert DayworkerSalary");
			}

			// 2. SALARY_DETAIL (급여 상세 항목) 데이터 생성 및 저장
			// (req.getDetails()에 담긴 항목들을 반복하거나 단일 항목으로 처리)
			if (req.getDetails() != null && !req.getDetails().isEmpty()) {
				for (RegistSalaryRequest.SalaryDetailRequest detailReq : req.getDetails()) {
					SalaryDetail detail = toDetail(salary.getSalaryId(), detailReq);
					boolean isDetail = detailDao.insertDayworkerSalaryDetail(conn, detail);
					if (!isDetail) {
						throw new RuntimeException("fail to insert DayworkerSalaryDetail");
					}
				}
			} else {
				// 단일 항목 또는 공제 기본 데이터 처리 시
				SalaryDetail detail = toDetail(salary.getSalaryId(), req);
				boolean isDetail = detailDao.insertDayworkerSalaryDetail(conn, detail);
				if (!isDetail) {
					throw new RuntimeException("fail to insert DayworkerSalaryDetail");
				}
			}

			conn.commit();

			// 3. 등록 완료 후 사원 번호(empId) 반환
			return req.getEmpId() != null ? req.getEmpId().intValue() : 0;

		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// Request -> Salary 도메인 모델 변환
	private Salary toSalary(RegistSalaryRequest req) {
		Salary salary = new Salary();

		// 시퀀스 등으로 채워질 primary key 세팅 (DAO/시퀀스에서 채우는 경우 생략 가능)
		// salary.setSalaryId(seqId);

		salary.setEmpId(req.getEmpId());
		salary.setPayYear(req.getPayYear());
		salary.setPayMonth(req.getPayMonth());
		salary.setPaySeq(req.getPaySeq());

		// String -> java.util.Date / java.sql.Date 변환
		salary.setStartDate(parseDate(req.getStartDate()));
		salary.setEndDate(parseDate(req.getEndDate()));
		salary.setPayDate(parseDate(req.getPayDate()));

		salary.setTotalPay(req.getTotalPay());
		salary.setTotalDeduct(req.getTotalDeduct());
		salary.setNetPay(req.getNetPay());

		return salary;
	}

	// Request (다중 항목 개별) -> SalaryDetail 도메인 모델 변환
	private SalaryDetail toDetail(Long salaryId, RegistSalaryRequest.SalaryDetailRequest detailReq) {
		SalaryDetail detail = new SalaryDetail();

		detail.setSalaryId(salaryId);
		detail.setSalaryItemId(detailReq.getSalaryItemId());
		detail.setItemType(detailReq.getItemType());
		detail.setItemName(detailReq.getItemName());
		detail.setTaxYn(detailReq.getTaxYn());
		detail.setAmount(detailReq.getAmount());
		detail.setMemo(detailReq.getMemo());

		return detail;
	}

	// Request (단일 항목 예시용) -> SalaryDetail 도메인 모델 변환
	private SalaryDetail toDetail(Long salaryId, RegistSalaryRequest req) {
		SalaryDetail detail = new SalaryDetail();

		detail.setSalaryId(salaryId);
		detail.setItemType("PAY");
		detail.setItemName("일용직 기본급");
		detail.setTaxYn("Y");
		detail.setAmount(req.getTotalPay());

		return detail;
	}

	// 날짜 문자열(YYYY-MM-DD) -> Date 객체 변환 유틸
	private Date parseDate(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return new Date();
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return new Date();
		}
	}
}