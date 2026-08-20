package retirement.command;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.dao.EmpDao;
import employee.dao.EmpInfoSalaryDao;
import employee.dto.EmployeeDetailData;
import employee.model.EmpInfoSalary;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import retirement.severancepay.service.OtherTaxableIncomeRequest;
import retirement.severancepay.service.SeveranceCalculateRequest;
import retirement.severancepay.service.SeveranceCalculateResult;
import retirement.severancepay.service.SeveranceCalculateService;
import retirement.severancepay.service.SeveranceSalaryResult;
import retirement.severancepay.service.TaxDeferredAccountRequest;

public class CalculateSeverancePayHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/retirement/severancePay.jsp";
	private SeveranceCalculateService calculateService = new SeveranceCalculateService();
	private EmpDao empDao = new EmpDao();
	private EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		int empId = Integer.parseInt(req.getParameter("empId"));

		SeveranceCalculateRequest request = new SeveranceCalculateRequest();
		request.setEmpId(empId);
		request.setRetirementBonus(toLong(req.getParameter("retirementBonus")));
		request.setDismissalAllowance(toLong(req.getParameter("dismissalAllowance")));
		request.setNonTaxableRetirementPay(toLong(req.getParameter("nonTaxableRetirementPay")));
		request.setPrepaidTax(toLong(req.getParameter("prepaidTax")));
		request.setTaxCredit(toLong(req.getParameter("taxCredit")));
		request.setDailyOrdinaryWage(toLong(req.getParameter("dailyOrdinaryWage")));
		request.setRuralSpecialTax(toLong(req.getParameter("ruralSpecialTax")));
		request.setOtherDeduction(toLong(req.getParameter("otherDeduction")));

		// 지급일
		String paymentDate = req.getParameter("paymentDate");

		if (paymentDate != null && !paymentDate.trim().isEmpty()) {
			request.setPaymentDate(Date.valueOf(paymentDate));
		}

		// 기타 과세소득
		List<OtherTaxableIncomeRequest> otherTaxableIncomes = createOtherTaxableIncomes(req);
		request.setOtherTaxableIncomes(otherTaxableIncomes);

		// 과세이연계좌
		List<TaxDeferredAccountRequest> taxDeferredAccounts = createTaxDeferredAccounts(req);
		request.setTaxDeferredAccounts(taxDeferredAccounts);

		// 2. 퇴직금 계산
		SeveranceCalculateResult result = calculateService.calculateSeverance(request);

		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			EmployeeDetailData selectedEmp = empDao.selectByIdJoin(conn, empId);
			List<EmployeeDetailData> employeeList = empDao.selectAllJoin(conn);
			SeveranceSalaryResult salaryResult = calculateService.loadSalary(empId);

			Date calculationDate;

			if (selectedEmp.getRetireDate() != null) {
				calculationDate = selectedEmp.getRetireDate();

			} else {
				calculationDate = new Date(System.currentTimeMillis());
			}

			LocalDate hireDate = selectedEmp.getHireDate().toLocalDate();
			long serviceDays = ChronoUnit.DAYS.between(hireDate, calculationDate.toLocalDate()) + 1;
			int serviceYears = (int) Math.ceil(serviceDays / 365.0);

			if (serviceYears < 1) {
				serviceYears = 1;
			}

			EmpInfoSalary empInfoSalary = empInfoSalaryDao.selectBankByEmpId(conn, empId);
			
			req.setAttribute("empInfoSalary", empInfoSalary);
			req.setAttribute("employeeList", employeeList);
			req.setAttribute("selectedEmp", selectedEmp);
			req.setAttribute("serviceDays", serviceDays);
			req.setAttribute("serviceYears", serviceYears);
			req.setAttribute("salaryResult", salaryResult);
			req.setAttribute("severanceRequest", request);
			req.setAttribute("severanceResult", result);

			return FORM_VIEW;

		} finally {

			JdbcUtil.close(conn);
		}
	}

	// String -> Long 입력하지 않았으면 null
	private Long toLong(String value) {

		if (value == null || value.trim().isEmpty()) {

			return null;
		}

		return Long.valueOf(value.replace(",", ""));
	}

	private List<OtherTaxableIncomeRequest> createOtherTaxableIncomes(HttpServletRequest req) {
		List<OtherTaxableIncomeRequest> result = new ArrayList<>();
		String[] paymentMonths = req.getParameterValues("otherPaymentMonth");
		String[] paymentItems = req.getParameterValues("otherPaymentItem");
		String[] amounts = req.getParameterValues("otherAmount");

		if (amounts == null) {
			return result;
		}

		for (int i = 0; i < amounts.length; i++) {

			// 전부 비어있는 행은 저장하지 않음
			if ((amounts[i] == null || amounts[i].trim().isEmpty())
					&& (paymentItems == null || paymentItems[i] == null || paymentItems[i].trim().isEmpty())) {
				continue;
			}

			OtherTaxableIncomeRequest income = new OtherTaxableIncomeRequest();

			if (paymentMonths != null && paymentMonths[i] != null && !paymentMonths[i].trim().isEmpty()) {
				income.setPaymentMonth(Date.valueOf(paymentMonths[i]));
			}

			if (paymentItems != null) {
				income.setPaymentItem(paymentItems[i]);
			}

			income.setAmount(toLong(amounts[i]));

			result.add(income);
		}

		return result;
	}

	private List<TaxDeferredAccountRequest> createTaxDeferredAccounts(HttpServletRequest req) {

		List<TaxDeferredAccountRequest> result = new ArrayList<>();
		String[] providerNames = req.getParameterValues("pensionProviderName");
		String[] businessNos = req.getParameterValues("businessRegistrationNo");
		String[] accountNos = req.getParameterValues("accountNo");
		String[] depositDates = req.getParameterValues("depositDate");
		String[] depositAmounts = req.getParameterValues("depositAmount");

		if (providerNames == null) {
			return result;
		}

		for (int i = 0; i < providerNames.length; i++) {

			boolean emptyProvider = providerNames[i] == null || providerNames[i].trim().isEmpty();
			boolean emptyAccount = accountNos == null || accountNos[i] == null || accountNos[i].trim().isEmpty();
			boolean emptyAmount = depositAmounts == null || depositAmounts[i] == null
					|| depositAmounts[i].trim().isEmpty() || "0".equals(depositAmounts[i]);

			if (emptyProvider && emptyAccount && emptyAmount) {
				continue;
			}

			TaxDeferredAccountRequest account = new TaxDeferredAccountRequest();
			account.setPensionProviderName(providerNames[i]);

			if (businessNos != null) {
				account.setBusinessRegistrationNo(businessNos[i]);
			}

			if (accountNos != null) {
				account.setAccountNo(accountNos[i]);
			}

			if (depositDates != null && depositDates[i] != null && !depositDates[i].trim().isEmpty()) {
				account.setDepositDate(Date.valueOf(depositDates[i]));
			}

			if (depositAmounts != null) {
				account.setDepositAmount(toLong(depositAmounts[i]));
			}

			result.add(account);
		}

		return result;
	}
}