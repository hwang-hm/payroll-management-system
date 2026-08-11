package employee.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
	
}
