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

/**
 * 4대보험 공제 내역 데이터 접근 객체 (DAO)
 * 4大保険控除明細データアクセスオブジェクト (DAO)
 */
public class SocialInsuranceDao {
	
	/**
	 * 급여 정산 ID 기준 사원별 4대보험 공제 내역 목록 조회
	 * 給与計算ID基準の社員別4大保険控除明細一覧照会
	 * 
	 * @param conn DB 커넥션 / DB接続
	 * @param salaryDateId 급여 정산 ID / 給与計算ID
	 * @return 4대보험 공제 내역 DTO 목록 / 4大保険控除明細DTO一覧
	 */
	public List<GetSocialInsuranceDTO> getSocialInsuranceList(Connection conn, int salaryDateId) {
		// 사원 ID(emp_id)를 Key로 사용하여 순서를 보장하면서 그룹핑하기 위한 Map
		// 社員ID(emp_id)をKeyとして順序を保証しながらグルーピングするためのMap
		Map<Integer, GetSocialInsuranceDTO> empMap = new LinkedHashMap<>();

		// 급여 항목 ID 10(국민연금), 11(건강보험), 12(노인장기요양), 13(고용보험) 조회 쿼리
		// 給与項目ID 10(国民年金), 11(健康保険), 12(介護保険), 13(雇用保険) 照会クエリ
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
				// 1. Mapに該当社員が存在しない場合、初回1回のみVOオブジェクトを生成して基本情報を設定後、Mapに追加
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
				// 2. salary_item_idに応じて該当VOオブジェクトの特定フィールドに金額を設定
				int itemId = rs.getInt("salary_item_id");
				long amount = rs.getLong("amount");

				switch (itemId) {
				case 10:
					dto.setNpBiz(amount);
					dto.setNpEmp(amount); // 국민연금 / 国民年金
					break;
				case 11:
					dto.setHiBiz(amount);
					dto.setHiEmp(amount); // 건강보험 / 健康保険
					break;
				case 12:
					dto.setLtcBiz(amount);
					dto.setLtcEmp(amount); // 노인장기요양보험 / 介護保険
					break;
				case 13:
					dto.setEiBiz(amount);
					dto.setEiEmp(amount); // 고용보험 / 雇用保険
					break;
				}
			}

			// 3. 모든 항목 수집 완료 후 각 사원 DTO의 소계 및 우측 총합계 필드 계산
			// 3. 全項目の収集完了後、各社員DTOの小計および右側総合計フィールドを計算
			for (GetSocialInsuranceDTO dto : empMap.values()) {
				dto.calculateTotals();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// DB 자원 해제
			// DBリソースの解放
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}

		// 4. 합계 필드가 모두 채워진 Map의 values를 List로 변환해 반환
		// 4. 合計フィールドがすべて設定されたMapのvaluesをListに変換して返却
		return new ArrayList<>(empMap.values());
	}

}