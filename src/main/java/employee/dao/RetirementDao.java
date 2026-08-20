package employee.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import employee.model.Retirement;
import jdbc.JdbcUtil;

public class RetirementDao {
	
	//Status 퇴직으로 변경
	 public int updateRetiredStatus (Connection conn, int empId, Date retireDate) throws SQLException {
		 try (PreparedStatement pstmt = conn.prepareStatement("update employee set status = 'N', retire_date = ? where emp_id = ?")) {
			 pstmt.setDate(1, retireDate);
			 pstmt.setInt(2, empId);
			 return pstmt.executeUpdate();
		 }
	 }
	 
	 //퇴직 정보 입력
	 public void insertRetirement (Connection conn, Retirement retirement) throws SQLException {
			PreparedStatement pstmt = null;

			try {
				pstmt = conn.prepareStatement("insert into retirement " +
					    "(emp_id, retirement_type, retirement_date, retirement_reason, contact_after_retirement)"+
					    "values (?, ?, ?, ?, ?)");
				
				pstmt.setInt(1, retirement.getEmpId());
				pstmt.setString(2, retirement.getRetirementType());
				pstmt.setDate(3, retirement.getRetirementDate());
				pstmt.setString(4, retirement.getRetirementReason());
				pstmt.setString(5, retirement.getContactAfterRetirement());
				
				pstmt.executeUpdate();
				
			} finally {
				JdbcUtil.close(pstmt);
			}
			
		}
	 
	 //사원번호로 사원 select
	    public Retirement selectById(Connection conn, int empId) throws SQLException{
			 PreparedStatement pstmt = null;
			 ResultSet rs = null;
			 
			 try {
				pstmt = conn.prepareStatement(
						"select emp_id, retirement_type, retirement_date, " + 
						"retirement_reason, contact_after_retirement " + 
						"from retirement where emp_id = ?"
						);
				
				pstmt.setInt(1, empId);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {

	                Retirement retirement = new Retirement();

	                retirement.setEmpId(rs.getInt("emp_id"));
	                retirement.setRetirementType(rs.getString("retirement_type"));
	                retirement.setRetirementDate(rs.getDate("retirement_date"));
	                retirement.setRetirementReason(rs.getString("retirement_reason"));
	                retirement.setContactAfterRetirement(rs.getString("contact_after_retirement"));

	                return retirement;
	            }
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
			return null;
		 }
	    
	    public int updateRetirement(Connection conn, int empId, Retirement retirement) throws SQLException {
	    	try (PreparedStatement pstmt = conn.prepareStatement(
					"update retirement set retirement_type = ?, retirement_date = ?, retirement_reason = ?, "
					+ "contact_after_retirement = ?, updated_at = sysdate where emp_id = ?")) {
	    		
	    		pstmt.setString(1, retirement.getRetirementType());
	    		pstmt.setDate(2, retirement.getRetirementDate());
	    		pstmt.setString(3, retirement.getRetirementReason());
	    		pstmt.setString(4, retirement.getContactAfterRetirement());
	    		pstmt.setInt(5, empId);
				
	    		return pstmt.executeUpdate();
			}
	    }
}
