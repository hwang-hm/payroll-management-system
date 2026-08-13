package employee.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import employee.list.service.EmpListData;
import employee.model.Employee;
import jdbc.JdbcUtil;

public class EmpDao {
	
	//사원 기본정보 입력
	public Employee insertEmp(Connection conn, Employee emp) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("insert into employee " +
				    "(emp_id, name_kor, hire_date, dept_id, nationality, employment_type, " +
				    "name_eng, retire_date, position_id, resident_no, zip_code, address, " +
				    "phone_num, mobile_num, email, sns, remarks, status, photo_path) " +
				    "values (seq_employee.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Y', ?)");
			
			pstmt.setString(1, emp.getNameKor());
			pstmt.setDate(2, emp.getHireDate());
			
			if (emp.getDeptId() != null) {
			    pstmt.setInt(3, emp.getDeptId());
			} else {
			    pstmt.setNull(3, Types.INTEGER);
			}
			
			pstmt.setString(4, emp.getNationality());
			pstmt.setString(5, emp.getEmploymentType());
			pstmt.setString(6, emp.getNameEng());
			pstmt.setDate(7, emp.getRetireDate());
			
			if (emp.getPositionId() != null) {
	            pstmt.setInt(8, emp.getPositionId());
	        } else {
	            pstmt.setNull(8, Types.INTEGER);
	        }
			
			pstmt.setString(9, emp.getResidentNo());
			pstmt.setString(10, emp.getZipCode());
			pstmt.setString(11, emp.getAddress());
			pstmt.setString(12, emp.getPhoneNum());
			pstmt.setString(13, emp.getMobileNum());
			pstmt.setString(14, emp.getEmail());
			pstmt.setString(15, emp.getSns());
			pstmt.setString(16, emp.getRemarks());
			pstmt.setString(17, emp.getPhotoPath());
			
			int insertedCount = pstmt.executeUpdate();
			
			if (insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select seq_employee.currval from dual");
				if (rs.next()) {
					int newEmpId = rs.getInt(1);
					emp.setEmpId(newEmpId);
					return emp;
				}
			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
		
	}
	
	//전체 사원수
	 public int selectCount(Connection conn) throws SQLException {

		    String sql = "select count(*) from employee";

		    try (PreparedStatement pstmt = conn.prepareStatement(sql);
		         ResultSet rs = pstmt.executeQuery()) {

		        if (rs.next()) {
		            return rs.getInt(1);
		        }

		        return 0;
		    }
		}
	   
	   // 페이징
	   public List<EmpListData> select(
	           Connection conn,
	           int firstRow,
	           int endRow) throws SQLException {

	       PreparedStatement pstmt = null;
	       ResultSet rs = null;

	       try {

	           String sql =
	               "select * from (select rownum as rnum, a.* from ( select e.emp_id, e.name_kor, e.hire_date, e.status, " +
	               "d.dept_name, p.position_name, e.retire_date, e.resident_no, e.mobile_Num, e.email, e.employment_type from employee e " +
	               "left join department d on e.dept_id = d.dept_id " +
	               "left join position p on e.position_id = p.position_id " +
	               "order by e.emp_id desc) a where rownum <= ? ) where rnum >= ?";

	           pstmt = conn.prepareStatement(sql);

	           pstmt.setInt(1, endRow);
	           pstmt.setInt(2, firstRow);

	           rs = pstmt.executeQuery();

	           List<EmpListData> result = new ArrayList<>();

	           while (rs.next()) {
	               result.add(convertEmpListData(rs));
	           }

	           return result;

	       } finally {
	           JdbcUtil.close(rs);
	           JdbcUtil.close(pstmt);
	       }
	   }
	       
	       //ResultSet 객체를 RetirementEmpListData로 변환
	       private EmpListData convertEmpListData(ResultSet rs) throws SQLException {

	             return new EmpListData(
	                 rs.getInt("emp_id"),
	                 rs.getString("name_kor"),
	                 rs.getDate("hire_date"),
	                 rs.getString("dept_name"),
	                 rs.getString("position_name"),
	                 rs.getDate("retire_date"),
	                 rs.getString("resident_no"),
	                 rs.getString("status"),
	                 rs.getString("mobile_num"),
	                 rs.getString("email"),
	                 rs.getString("employment_type")
	             );
	         }
	
	       //고용형태 count
	       public int selectCountByEmpType (Connection conn, String empType) throws SQLException {
	    	   String sql = "select count(*) from employee where employment_type = ? and status = 'Y'";
	    	   
	    	   try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

	    	        pstmt.setString(1, empType);

	    	        try (ResultSet rs = pstmt.executeQuery()) {

	    	            if (rs.next()) {
	    	                return rs.getInt(1);
	    	            }

	    	            return 0;
	    	        }
	  		    }
	       }
	       
	       //재직, 퇴직 count
	       public int selectCountByStatus(Connection conn, String status)
	    	        throws SQLException {

	    	    String sql = "select count(*) from employee where status = ?";

	    	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

	    	        pstmt.setString(1, status);

	    	        try (ResultSet rs = pstmt.executeQuery()) {
	    	            if (rs.next()) {
	    	                return rs.getInt(1);
	    	            }
	    	        }
	    	    }

	    	    return 0;
	    	}
	       
	       //사원번호로 사원 select
	       public Employee selectById(Connection conn, int empId) throws SQLException{
	  		 PreparedStatement pstmt = null;
	  		 ResultSet rs = null;
	  		 
	  		 try {
	  			pstmt = conn.prepareStatement("select * from employee where emp_id = ?");
	  			pstmt.setInt(1, empId);
	  			rs = pstmt.executeQuery();
	  			Employee emp = null;
	  			
	  			if (rs.next()) {
	  				emp = convertEmployee(rs);
	  			}
	  			return emp;
	  		} finally {
	  			JdbcUtil.close(rs);
	  			JdbcUtil.close(pstmt);
	  		}
	  	 }
	       
	       //ResultSet 객체를 Employee로 변환
	       private Employee convertEmployee(ResultSet rs) throws SQLException {

	             return new Employee(
	            		 rs.getInt("emp_id"),
	            	     rs.getString("name_kor"),
	            	     rs.getDate("hire_date"),
	            	     rs.getString("employment_type"),

	            	     rs.getInt("dept_id"),
	            	     rs.getString("nationality"),
	            	     rs.getString("name_eng"),
	            	     rs.getDate("retire_date"),
	            	     rs.getInt("position_id"),
	            	     rs.getString("resident_no"),
	            	     rs.getString("zip_code"),
	            	     rs.getString("address"),
	            	     rs.getString("phone_num"),
	            	     rs.getString("mobile_num"),
	            	     rs.getString("email"),
	            	     rs.getString("sns"),
	            	     rs.getString("remarks"),
	            	     rs.getString("status"),
	            	     rs.getString("photo_path"),
	            	     rs.getTimestamp("created_at")
	             );
	         }
}
