package employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import employee.model.Retirement;
import jdbc.JdbcUtil;

public class RetirementDao {
	public void insertRetirement(Connection conn, int empId, Retirement retirement) throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("insert into retirement "
					+ "(emp_id, retirementType, retirementDate, retirementReason, contactAfterRetirement, retirementPay)"
					+ "values (?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, empId);
			pstmt.setString(2, retirement.getRetirementType());
			pstmt.setDate(3, retirement.getRetirementDate());
			pstmt.setString(4, retirement.getRetirementReason());
			pstmt.setString(5, retirement.getContactAfterRetirement());

			if (retirement.getRetirementPay() != null) {
				pstmt.setInt(6, retirement.getRetirementPay());
			} else {
				pstmt.setNull(6, Types.INTEGER);
			}

			pstmt.executeUpdate();

		} finally {
			JdbcUtil.close(pstmt);
		}

	}
	
	
}
