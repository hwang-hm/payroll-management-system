package salary.transfer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.transfer.viewdto.TransferApplyRow;

public class TransferApplyDao {

	// 이체신청 화면에 뿌릴 사원+계좌+금액 목록
	// 振込申請画面に表示する社員+口座+金額一覧
	public List<TransferApplyRow> selectTransferApplyRowsByPeriod(Connection conn,
			int payYear, int payMonth, int paySeq) throws SQLException {
		String sql = "SELECT s.salary_id, s.emp_id, e.employment_type, e.name_kor, e.name_eng, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "e.hire_date, e.mobile_num, e.email, "
				+ "eis.salary_bank, eis.salary_account, "
				+ "pi.salary_bank AS withdraw_bank, pi.salary_account AS withdraw_account, "
				+ "s.total_pay, s.total_deduct, s.net_pay "
				+ "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "JOIN employee e ON s.emp_id = e.emp_id "
				+ "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id "
				+ "LEFT JOIN employee_information_salary eis ON e.emp_id = eis.emp_id "
				+ "LEFT JOIN (SELECT salary_bank, salary_account FROM payroll_information WHERE ROWNUM = 1) pi "
				+ "ON 1 = 1 "
				+ "WHERE sd.pay_year = ? AND sd.pay_month = ? AND sd.pay_seq = ? "
				+ "ORDER BY s.salary_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, payYear);
			pstmt.setInt(2, payMonth);
			pstmt.setInt(3, paySeq);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<TransferApplyRow> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toTransferApplyRow(rs));
				}
				return list;
			}
		}
	}

	// 이체신청 한 줄로 옮겨담기
	// 振込申請1行へ転記
	private TransferApplyRow toTransferApplyRow(ResultSet rs) throws SQLException {
		TransferApplyRow row = new TransferApplyRow();
		row.setSalaryId(rs.getInt("salary_id"));
		row.setEmpId(rs.getInt("emp_id"));
		row.setEmploymentType(rs.getString("employment_type"));
		row.setNameKor(rs.getString("name_kor"));
		row.setNameEng(rs.getString("name_eng"));
		row.setDeptName(rs.getString("dept_name"));
		row.setPositionName(rs.getString("position_name"));
		row.setHireDate(rs.getDate("hire_date"));
		row.setMobileNum(rs.getString("mobile_num"));
		row.setEmail(rs.getString("email"));
		row.setSalaryBank(rs.getString("salary_bank"));
		row.setSalaryAccount(rs.getString("salary_account"));
		row.setWithdrawBank(rs.getString("withdraw_bank"));
		row.setWithdrawAccount(rs.getString("withdraw_account"));
		row.setTotalPay(rs.getLong("total_pay"));
		row.setTotalDeduct(rs.getLong("total_deduct"));
		row.setNetPay(rs.getLong("net_pay"));
		return row;
	}
}
