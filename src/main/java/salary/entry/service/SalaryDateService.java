package salary.entry.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import salary.entry.dao.SalaryDateDao;
import salary.model.SalaryDate;

public class SalaryDateService {

	private final SalaryDateDao salaryDateDao = new SalaryDateDao();

	// 귀속년월 최초선택시 salarydate를 생성해버림. 지급기간과 지급일 데이터가 들어가야 하기 때문.
	// 차후에 더미성 최초 데이터를 한방에 넣는 방식으로 변경 가능성 있음
	public SalaryDate ensureByPeriod(Connection conn, int payYear, int payMonth, int paySeq) throws SQLException {
		SalaryDate salaryDate = salaryDateDao.selectByPeriod(conn, payYear, payMonth, paySeq);
		if (salaryDate != null) {
			return salaryDate;
		}

		int nextMonth = payMonth == 12 ? 1 : payMonth + 1;
		int nextYear = payMonth == 12 ? payYear + 1 : payYear;

		Calendar cal = Calendar.getInstance();
		cal.set(payYear, payMonth - 1, 1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		SalaryDate newDate = new SalaryDate();
		newDate.setPayYear(payYear);
		newDate.setPayMonth(payMonth);
		newDate.setPaySeq(paySeq);
		newDate.setStartDate(Date.valueOf(String.format("%04d-%02d-01", payYear, payMonth)));
		newDate.setEndDate(Date.valueOf(String.format("%04d-%02d-%02d", payYear, payMonth, lastDay)));
		newDate.setPayDate(Date.valueOf(String.format("%04d-%02d-05", nextYear, nextMonth)));
		salaryDateDao.insert(conn, newDate);

		SalaryDate created = salaryDateDao.selectByPeriod(conn, payYear, payMonth, paySeq);
		if (created == null) {
			throw new SQLException("salary_date 생성 후 조회에 실패했습니다.");
		}
		return created;
	}
}
