package employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import employee.model.Employee;
import employee.model.NationalPensionInfo;
import jdbc.JdbcUtil;

public class NationalPensionDao {

	// 4대보험 등록
	// 4大保険の登録
	public void insertNP(Connection conn, int empId, NationalPensionInfo npi) throws SQLException {

		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("insert into national_pension_information "
					+ "(emp_id, national_no, national_acq_date, national_loss_date, "
					+ "health_no, health_acq_date, health_loss_date, "
					+ "employment_no, employment_acq_date, employment_loss_date, "
					+ "industrial_no, industrial_acq_date, industrial_loss_date) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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

	// 4대보험 수정
	// 4大保険の修正
	public int updateNp(Connection conn, int empId, NationalPensionInfo npi) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"update national_pension_information set national_no = ?, national_acq_date = ?, national_loss_date = ?, "
						+ "health_no = ?, health_acq_date = ?, health_loss_date = ?, "
						+ "employment_no = ?, employment_acq_date = ?, employment_loss_date = ?, "
						+ "industrial_no = ?, industrial_acq_date = ?, industrial_loss_date = ?, updated_at = sysdate where emp_id = ?")) {

			pstmt.setString(1, npi.getNationalNo());
			pstmt.setDate(2, npi.getNationalAcqDate());
			pstmt.setDate(3, npi.getNationalLossDate());

			pstmt.setString(4, npi.getHealthNo());
			pstmt.setDate(5, npi.getHealthAcqDate());
			pstmt.setDate(6, npi.getHealthLossDate());

			pstmt.setString(7, npi.getEmploymentNo());
			pstmt.setDate(8, npi.getEmploymentAcqDate());
			pstmt.setDate(9, npi.getEmploymentLossDate());

			pstmt.setString(10, npi.getIndustrialNo());
			pstmt.setDate(11, npi.getIndustrialAcqDate());
			pstmt.setDate(12, npi.getIndustrialLossDate());

			pstmt.setInt(13, empId);

			return pstmt.executeUpdate();
		}
	}

	// 사원번호로 4대보험 정보 조회
	// 社員番号で4大保険情報を照会
	public NationalPensionInfo selectById(Connection conn, int empId) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("select * from national_pension_information where emp_id = ?");

			pstmt.setInt(1, empId);
			rs = pstmt.executeQuery();

			NationalPensionInfo npi = null;

			if (rs.next()) {
				npi = convertNationalPensionInfo(rs);
			}

			return npi;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// ResultSet 객체를 NationalPensionInfo로 변환
	// ResultSetオブジェクトをNationalPensionInfoに変換
	private NationalPensionInfo convertNationalPensionInfo(ResultSet rs) throws SQLException {

		NationalPensionInfo npi = new NationalPensionInfo();

		npi.setEmpId(rs.getInt("emp_id"));
		npi.setNationalNo(rs.getString("national_no"));
		npi.setNationalAcqDate(rs.getDate("national_acq_date"));
		npi.setNationalLossDate(rs.getDate("national_loss_date"));
		npi.setHealthNo(rs.getString("health_no"));
		npi.setHealthAcqDate(rs.getDate("health_acq_date"));
		npi.setHealthLossDate(rs.getDate("health_loss_date"));
		npi.setEmploymentNo(rs.getString("employment_no"));
		npi.setEmploymentAcqDate(rs.getDate("employment_acq_date"));
		npi.setEmploymentLossDate(rs.getDate("employment_loss_date"));
		npi.setIndustrialNo(rs.getString("industrial_no"));
		npi.setIndustrialAcqDate(rs.getDate("industrial_acq_date"));
		npi.setIndustrialLossDate(rs.getDate("industrial_loss_date"));

		return npi;
	}
}
