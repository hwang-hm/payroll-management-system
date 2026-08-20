package salary.insurance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jdbc.JdbcUtil;
import salary.insurance.service.request.GetSocialInsuranceDTO;

public class SocialInsuranceDao {
	public List<GetSocialInsuranceDTO> getSocailInsuranceList(Connection conn, int salaryDateId) {
		Map<Integer, GetSocialInsuranceDTO> empMap = new LinkedHashMap<>();

		String sql = "SELECT e.emp_id, e.name_kor, e.employment_type, "
				+ "e.hire_date, d.dept_name, p.position_name, sd.salary_item_id, sd.amount " + "FROM salary s "
				+ "JOIN employee e ON s.emp_id = e.emp_id " + "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id "
				+ "JOIN salary_detail sd ON s.salary_id = sd.salary_id " + "WHERE s.salary_date_id = ? "
				+ "AND sd.salary_item_id IN (10, 11, 12, 13) " + "ORDER BY e.emp_id ASC, sd.salary_item_id ASC";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryDateId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int empId = rs.getInt("emp_id");

				// 1. Map에 해당 사원이 없으면 최초 1회만 VO 객체를 생성하여 기본 정보 세팅 후 Map에 추가
				GetSocialInsuranceDTO dto = empMap.get(empId);
				if (dto == null) {
					dto = new GetSocialInsuranceDTO();
					dto.setEmploymentType(rs.getString("employment_type"));
					dto.setNameKor(rs.getString("name_kor"));
					dto.setHireDate(rs.getDate("hire_date"));
					dto.setDeptName(rs.getString("dept_name"));
					dto.setPositionName(rs.getString("position_name"));

					empMap.put(empId, dto);
				}

				// 2. salary_item_id에 맞춰 해당 VO 객체의 특정 필드에 금액 세팅
				int itemId = rs.getInt("salary_item_id");
				long amount = rs.getLong("amount");

				switch (itemId) {
				case 10:
					dto.setNpBiz(amount);
					dto.setNpEmp(amount); // 국민연금
					break;
				case 11:
					dto.setHiBiz(amount);
					dto.setHiEmp(amount); // 건강보험
					break;
				case 12:
					dto.setLtcBiz(amount);
					dto.setLtcEmp(amount); // 노인장기요양
					break;
				case 13:
					dto.setEiBiz(amount);
					dto.setEiEmp(amount); // 고용보험
					break;
				}
			}

			// 3. [신규 추가] 모든 항목 수집 완료 후 각 사원 DTO의 소계 및 우측 총합계 필드 계산
			for (GetSocialInsuranceDTO dto : empMap.values()) {
				dto.calculateTotals();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}

		// 4. 합계 필드가 모두 채워진 Map의 values를 List로 변환해 반환
		return new ArrayList<>(empMap.values());
	}

}