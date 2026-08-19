package employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import employee.model.EmpInfoSalary;
import jdbc.JdbcUtil;

public class EmpInfoSalaryDao {
	public void insertEmpInfoSalary(Connection conn, int empId, EmpInfoSalary empInfoSalary) throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("insert into employee_information_salary "
					+ "(emp_id, national_pension_yn, health_insurance_yn, health_discount_rate, "
					+ "longterm_care_yn, longterm_care_discount_rate, employment_insurance_yn, "
					+ "income_tax_type, youth_tax_reduction_yn, youth_tax_reduction_rate, "
					+ "durunuri_type, durunuri_split_yn, durunuri_pension_rate, "
					+ "durunuri_employment_rate, base_salary, pension_salary, health_salary, "
					+ "employment_salary, salary_bank, salary_account) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, empId);

			pstmt.setString(2, empInfoSalary.getNationalPensionYN());
			pstmt.setString(3, empInfoSalary.getHealthInsuranceYN());
			pstmt.setInt(4, empInfoSalary.getHealthDiscountRate());

			pstmt.setString(5, empInfoSalary.getLongtermCareYN());
			pstmt.setInt(6, empInfoSalary.getLongtermCareDiscountRate());

			pstmt.setString(7, empInfoSalary.getEmploymentInsuranceYN());
			pstmt.setString(8, empInfoSalary.getIncomeTaxType());

			pstmt.setString(9, empInfoSalary.getYouthTaxReductionYN());
			pstmt.setInt(10, empInfoSalary.getYouthTaxReductionRate());

			pstmt.setString(11, empInfoSalary.getDurunuriType());
			pstmt.setString(12, empInfoSalary.getDurunuriSplitYN());

			pstmt.setInt(13, empInfoSalary.getDurunuriPensionRate());
			pstmt.setInt(14, empInfoSalary.getDurunuriEmploymentRate());

			pstmt.setInt(15, empInfoSalary.getBaseSalary());

			if (empInfoSalary.getPensionSalary() != null) {
				pstmt.setInt(16, empInfoSalary.getPensionSalary());
			} else {
				pstmt.setNull(16, Types.INTEGER);
			}

			if (empInfoSalary.getHealthSalary() != null) {
				pstmt.setInt(17, empInfoSalary.getHealthSalary());
			} else {
				pstmt.setNull(17, Types.INTEGER);
			}

			if (empInfoSalary.getEmploymentSalary() != null) {
				pstmt.setInt(18, empInfoSalary.getEmploymentSalary());
			} else {
				pstmt.setNull(18, Types.INTEGER);
			}

			pstmt.setString(19, empInfoSalary.getSalaryBank());
			pstmt.setString(20, empInfoSalary.getSalaryAccount());

			pstmt.executeUpdate();

		} finally {
			JdbcUtil.close(pstmt);
		}

	}

	public EmpInfoSalary selectBankByEmpId(Connection conn, int empId) throws SQLException {
		String sql = "select emp_id, salary_bank, salary_account from employee_information_salary where emp_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, empId);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					EmpInfoSalary empInfoSalary = new EmpInfoSalary();
					empInfoSalary.setEmpId(rs.getInt("emp_id"));
					empInfoSalary.setSalaryBank(rs.getString("salary_bank"));
					empInfoSalary.setSalaryAccount(rs.getString("salary_account"));

					return empInfoSalary;
				}

				return null;
			}

		}

	}
}
