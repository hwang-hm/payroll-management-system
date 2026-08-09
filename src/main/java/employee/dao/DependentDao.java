package employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import employee.model.Dependent;
import jdbc.JdbcUtil;

public class DependentDao {
	public void insertDependent (Connection conn, int empId, Dependent dependent) throws SQLException {
		
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(
				    "insert into dependent " +
				    "(dependent_id, emp_id, relation, family_name, nationality, resident_no, " +
				    "disability_yn, deduction_yn, health_insurance_yn, cohabitation_yn, " +
				    "income_tax_yn, child_age_yn) " +
				    "values (seq_dependent.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
				);
			
			pstmt.setInt(1, empId);
			pstmt.setString(2, dependent.getRelation());
			pstmt.setString(3, dependent.getFamilyName());
			pstmt.setString(4, dependent.getNationality());
			pstmt.setString(5, dependent.getResidentNo());
			pstmt.setString(6, dependent.getDisabilityYN());
			pstmt.setString(7, dependent.getDeductionYN());
			pstmt.setString(8, dependent.getHealthInsuranceYN());
			pstmt.setString(9, dependent.getCohabitationYN());
			pstmt.setString(10, dependent.getIncomeTaxYN());
			pstmt.setString(11, dependent.getChildAgeYN());
			
			pstmt.executeUpdate();
			

		} finally {

			JdbcUtil.close(pstmt);
		}
		
	}
}
