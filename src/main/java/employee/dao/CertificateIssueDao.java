package employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import employee.CertificateIssue.service.CertificateIssueListData;

public class CertificateIssueDao {
	public void insert(Connection conn, int empId, String certificateType, String purpose, String issueDepartment)
			throws SQLException {

		String sql = "INSERT INTO certificate_issue " + "(certificate_issue_id, emp_id, certificate_type, "
				+ "purpose, issue_department, issue_date) "
				+ "VALUES (certificate_issue_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, empId);
			pstmt.setString(2, certificateType);
			pstmt.setString(3, purpose);
			pstmt.setString(4, issueDepartment);

			pstmt.executeUpdate();
		}
	}

	public List<CertificateIssueListData> selectList(Connection conn) throws SQLException {

		String sql = "SELECT " + "c.certificate_issue_id, " + "c.emp_id, " + "c.certificate_type, " + "c.purpose, "
				+ "c.issue_department, " + "c.issue_date, " + "e.name_kor, " + "e.employment_type, " + "d.dept_name, "
				+ "p.position_name " + "FROM certificate_issue c " + "JOIN employee e ON c.emp_id = e.emp_id "
				+ "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id " + "ORDER BY c.certificate_issue_id DESC";

		List<CertificateIssueListData> list = new ArrayList<>();

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {

				CertificateIssueListData data = new CertificateIssueListData();
				
				data.setCertificateIssueId(rs.getInt("certificate_issue_id"));
				data.setEmpId(rs.getInt("emp_id"));
				data.setCertificateType(rs.getString("certificate_type"));
				data.setPurpose(rs.getString("purpose"));
				data.setIssueDepartment(rs.getString("issue_department"));
				data.setIssueDate(rs.getDate("issue_date"));
				data.setNameKor(rs.getString("name_kor"));
				data.setEmploymentType(rs.getString("employment_type"));
				data.setDeptName(rs.getString("dept_name"));
				data.setPositionName(rs.getString("position_name"));

				list.add(data);
			}
		}

		return list;
	}
}
