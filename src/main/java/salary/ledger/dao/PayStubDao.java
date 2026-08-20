package salary.ledger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.ledger.viewdto.PayStubForm;
import salary.ledger.viewdto.PayStubRow;

public class PayStubDao {

	// 해당 귀속연월 급여명세서 목록. 구분/성명으로 검색 가능
	public List<PayStubRow> selectRowsBySalaryDateId(Connection conn, int salaryDateId, String searchKeyword)
			throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT s.salary_id, e.employment_type, e.name_kor, s.net_pay "
						+ "FROM salary s "
						+ "JOIN employee e ON s.emp_id = e.emp_id "
						+ "WHERE s.salary_date_id = ? ");
		boolean hasKeyword = searchKeyword != null && !searchKeyword.trim().isEmpty();
		if (hasKeyword) {
			sql.append("AND (e.employment_type LIKE ? OR e.name_kor LIKE ?) ");
		}
		sql.append("ORDER BY s.salary_id");

		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			pstmt.setInt(1, salaryDateId);
			if (hasKeyword) {
				String keyword = "%" + searchKeyword.trim() + "%";
				pstmt.setString(2, keyword);
				pstmt.setString(3, keyword);
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				List<PayStubRow> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toRow(rs));
				}
				return list;
			}
		}
	}

	// 명세서 헤더에 뿌릴 사원·급여 기본정보
	public PayStubForm selectFormBySalaryId(Connection conn, int salaryId) throws SQLException {
		String sql = "SELECT e.name_kor, e.resident_no, e.hire_date, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "sd.pay_date, s.total_pay, s.total_deduct, s.net_pay "
				+ "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "JOIN employee e ON s.emp_id = e.emp_id "
				+ "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id "
				+ "WHERE s.salary_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					return null;
				}
				PayStubForm form = new PayStubForm();
				form.setNameKor(rs.getString("name_kor"));
				form.setBirthDate(formatBirthDate(rs.getString("resident_no")));
				form.setDeptName(rs.getString("dept_name"));
				form.setPositionName(rs.getString("position_name"));
				java.sql.Date hireDate = rs.getDate("hire_date");
				if (hireDate != null) {
					form.setHireDate(hireDate.toString());
				}
				java.sql.Date payDate = rs.getDate("pay_date");
				if (payDate != null) {
					form.setPayDate(payDate.toString());
				}
				form.setTotalPay(rs.getLong("total_pay"));
				form.setTotalDeduct(rs.getLong("total_deduct"));
				form.setNetPay(rs.getLong("net_pay"));
				return form;
			}
		}
	}

	private PayStubRow toRow(ResultSet rs) throws SQLException {
		PayStubRow row = new PayStubRow();
		row.setSalaryId(rs.getInt("salary_id"));
		row.setEmploymentType(rs.getString("employment_type"));
		row.setNameKor(rs.getString("name_kor"));
		row.setNetPay(rs.getLong("net_pay"));
		return row;
	}

	// 주민번호 앞 6자리를 생년월일로 변환
	private String formatBirthDate(String residentNo) {
		if (residentNo == null || residentNo.length() < 7) {
			return "-";
		}
		String digits = residentNo.replace("-", "").trim();
		if (digits.length() < 7) {
			return "-";
		}
		String yymmdd = digits.substring(0, 6);
		int centuryDigit = Character.getNumericValue(digits.charAt(6));
		String century = (centuryDigit == 1 || centuryDigit == 2 || centuryDigit == 5 || centuryDigit == 6)
				? "19" : "20";
		return century + yymmdd.substring(0, 2) + "-" + yymmdd.substring(2, 4) + "-" + yymmdd.substring(4, 6);
	}
}
