package employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import employee.model.Dependent;
import jdbc.JdbcUtil;

public class DependentDao {

	// 부양가족 정보 입력
	public void insertDependent(Connection conn, int empId, Dependent dependent) throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("insert into dependent "
					+ "(dependent_id, emp_id, relation, family_name, nationality, resident_no, "
					+ "disability_yn, deduction_yn, health_insurance_yn, cohabitation_yn, "
					+ "income_tax_yn, child_age_yn) "
					+ "values (seq_dependent.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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

	// 부양가족 정보 수정
	public int updateDependent(Connection conn, int empId, Dependent dependent) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("update dependent set emp_id = ?, relation = ?, family_name = ?, "
						+ "nationality = ?, resident_no = ?, "
						+ "disability_yn = ?, deduction_yn = ?, health_insurance_yn = ?, "
						+ "cohabitation_yn = ?, income_tax_yn = ?, child_age_yn = ?, "
						+ "updated_at = sysdate where dependent_id = ?")) {

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
			pstmt.setInt(12, dependent.getDependentId());

			return pstmt.executeUpdate();
		}
	}

	// 사원번호로 부양가족 목록 조회
	public List<Dependent> selectById(Connection conn, int empId) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("select * from dependent where emp_id = ? order by dependent_id");

			pstmt.setInt(1, empId);
			rs = pstmt.executeQuery();
			List<Dependent> dependents = new ArrayList<>();

			while (rs.next()) {
				dependents.add(convertDependent(rs));
			}

			return dependents;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// ResultSet 객체를 Dependent로 변환
	private Dependent convertDependent(ResultSet rs) throws SQLException {

		Dependent dependent = new Dependent();
		dependent.setDependentId(rs.getInt("dependent_id"));
		dependent.setEmpId(rs.getInt("emp_id"));
		dependent.setRelation(rs.getString("relation"));
		dependent.setFamilyName(rs.getString("family_name"));
		dependent.setNationality(rs.getString("nationality"));
		dependent.setResidentNo(rs.getString("resident_no"));
		dependent.setDisabilityYN(rs.getString("disability_yn"));
		dependent.setDeductionYN(rs.getString("deduction_yn"));
		dependent.setHealthInsuranceYN(rs.getString("health_insurance_yn"));
		dependent.setCohabitationYN(rs.getString("cohabitation_yn"));
		dependent.setIncomeTaxYN(rs.getString("income_tax_yn"));
		dependent.setChildAgeYN(rs.getString("child_age_yn"));

		return dependent;
	}
}
