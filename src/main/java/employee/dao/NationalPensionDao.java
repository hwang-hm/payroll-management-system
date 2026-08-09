package employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import employee.model.NationalPensionInfo;
import jdbc.JdbcUtil;

public class NationalPensionDao {
	
	public void insertNP (Connection conn, int empId, NationalPensionInfo npi) throws SQLException {
		
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(
				    "insert into national_pension_information " +
				    "(emp_id, national_no, national_acq_date, national_loss_date, " +
				    "health_no, health_acq_date, health_loss_date, " +
				    "employment_no, employment_acq_date, employment_loss_date, " +
				    "industrial_no, industrial_acq_date, industrial_loss_date) " +
				    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
				);
			
			pstmt.setInt(1, empId);
			pstmt.setString(2, npi.getNationalNo());
			pstmt.setDate(3, npi.getNationalAcqDate());
			pstmt.setDate(4, npi.getNationalLossDate());
			
			pstmt.setString(5, npi.getHealthNo());
			pstmt.setDate(6, npi.getHealthAcqDate());
			pstmt.setDate(7, npi.getHealthLossDate());
			
			pstmt.setString(8, npi.getEmploymentNo());
			pstmt.setDate(9, npi.getEmploymentAcqDate());
			pstmt.setDate(10, npi.getEmploymentLossDate());
			
			pstmt.setString(11, npi.getIndustrialNo());
			pstmt.setDate(12, npi.getIndustrialAcqDate());
			pstmt.setDate(13, npi.getIndustrialLossDate());
			
			
			pstmt.executeUpdate();
			

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
		
	}
}
