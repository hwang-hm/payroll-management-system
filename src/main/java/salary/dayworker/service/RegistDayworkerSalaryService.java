package salary.dayworker.service;

import java.sql.Connection;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dayworker.dao.SalaryDao;
import salary.dayworker.dao.SalaryDateDao;
import salary.dayworker.dao.SalaryDetailDao;
import salary.dayworker.service.request.RegistSalaryRequest;
import salary.model.Salary;
import salary.model.SalaryDate;
import salary.model.SalaryDetail;

public class RegistDayworkerSalaryService {

	private SalaryDateDao salaryDateDao = new SalaryDateDao();
	private SalaryDao salaryDao = new SalaryDao();
	private SalaryDetailDao salaryDetailDao = new SalaryDetailDao();

	public boolean regist(RegistSalaryRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false); // 트랜잭션 시작

			// Step 1: SALARY_DATE 확인 및 저장/수정
			SalaryDate salaryDate = salaryDateDao.selectByYearMonthSeq(conn, req.getPayYear(), req.getPayMonth(),
					req.getPaySeq());

			int salaryDateId;
			if (salaryDate == null) {
				SalaryDate newDate = new SalaryDate();
				newDate.setPayYear(req.getPayYear());
				newDate.setPayMonth(req.getPayMonth());
				newDate.setPaySeq(req.getPaySeq());
				newDate.setStartDate(req.getStartDate());
				newDate.setEndDate(req.getEndDate());
				newDate.setPayDate(req.getPayDate());
				salaryDateDao.insert(conn, newDate);

				SalaryDate created = salaryDateDao.selectByYearMonthSeq(conn, req.getPayYear(), req.getPayMonth(),
						req.getPaySeq());
				salaryDateId = created.getSalaryDateId();
			} else {
				salaryDate.setStartDate(req.getStartDate());
				salaryDate.setEndDate(req.getEndDate());
				salaryDate.setPayDate(req.getPayDate());
				salaryDateDao.update(conn, salaryDate);
				salaryDateId = salaryDate.getSalaryDateId();
			}

			// Step 2: SALARY (헤더) 저장/수정
			Salary salary = salaryDao.selectByDateAndEmp(conn, salaryDateId, req.getEmpId());
			int salaryId;

			if (salary == null) {
				Salary newSalary = new Salary();
				newSalary.setSalaryDateId(salaryDateId);
				newSalary.setEmpId(req.getEmpId());
				newSalary.setTotalPay(req.getTotalPay());
				newSalary.setTotalDeduct(req.getTotalDeduct());
				newSalary.setNetPay(req.getNetPay());
				salaryId = salaryDao.insert(conn, newSalary);
			} else {
				salary.setTotalPay(req.getTotalPay());
				salary.setTotalDeduct(req.getTotalDeduct());
				salary.setNetPay(req.getNetPay());
				salaryDao.update(conn, salary);
				salaryId = salary.getSalaryId();
			}

			// Step 3: SALARY_DETAIL (상세) 저장
			salaryDetailDao.deleteBySalaryId(conn, salaryId);

			if (req.getDetails() != null) {
				for (RegistSalaryRequest.SalaryDetailRequest detailReq : req.getDetails()) {
					SalaryDetail detail = new SalaryDetail();
					detail.setSalaryId(salaryId);
					detail.setSalaryItemId(detailReq.getSalaryItemId());
					detail.setItemType(detailReq.getItemType());
					detail.setItemName(detailReq.getItemName());
					detail.setTaxYn(detailReq.getTaxYn());
					detail.setRoundUnit("NONE");
					detail.setAmount(detailReq.getAmount());
					detail.setMemo(detailReq.getMemo());

					salaryDetailDao.insert(conn, detail);
				}
			}

			conn.commit();
			return true;

		} catch (Exception e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("급여 저장 실패: " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}