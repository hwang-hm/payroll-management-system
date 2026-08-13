package retirement.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import retirement.service.RetirementEmpListData;

public class EmpDao {
	//Status 변경
		 public int updateRetiredStatus (Connection conn, int empId, Date retireDate) throws SQLException {
			 try (PreparedStatement pstmt = conn.prepareStatement("update employee set status = 'N', retire_date = ?, updated_at = sysdate where emp_id = ?")) {
				 pstmt.setDate(1, retireDate);
				 pstmt.setInt(2, empId);
				 return pstmt.executeUpdate();
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
		   public List<RetirementEmpListData> select(
		           Connection conn,
		           int firstRow,
		           int endRow) throws SQLException {

		       PreparedStatement pstmt = null;
		       ResultSet rs = null;

		       try {

		           String sql =
		               "select * from (select rownum as rnum, a.* from ( select e.emp_id, e.name_kor, e.hire_date, e.status, " +
		                       "d.dept_name, p.position_name, e.retire_date from employee e " +
		                     "left join department d on e.dept_id = d.dept_id " +
		                       "left join position p on e.position_id = p.position_id " +
		                       "order by e.emp_id desc) a where rownum <= ? ) where rnum >= ?";

		           pstmt = conn.prepareStatement(sql);

		           pstmt.setInt(1, endRow);
		           pstmt.setInt(2, firstRow);

		           rs = pstmt.executeQuery();

		           List<RetirementEmpListData> result = new ArrayList<>();

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
		       private RetirementEmpListData convertEmpListData(ResultSet rs) throws SQLException {

		             return new RetirementEmpListData(
		                 rs.getInt("emp_id"),
		                 rs.getString("name_kor"),
		                 rs.getDate("hire_date"),
		                 rs.getString("dept_name"),
		                 rs.getString("position_name"),
		                 rs.getDate("retire_date"),
		                 rs.getString("status")
		             );
		         }
		 
}
