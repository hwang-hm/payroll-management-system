package salary.statics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import salary.statics.service.request.SalaryCompositionDTO;
import salary.statics.service.request.SalaryItemDTO;

/**
 * 급여 구성 항목별 통계 데이터 액세스 객체 (DAO)
 * 給与構成項目別統計データアクセスオブジェクト (DAO)
 */
public class SalaryCompositionDao {

	/**
	 * 특정 사원의 지정 연월 급여 구성 항목 상세 내역 및 비율 데이터 조회
	 * 特定社員の指定年月における給与構成項目詳細内訳および割合データの照会
	 */
	public SalaryCompositionDTO selectSalaryComposition(Connection conn, int empId, int year, int month)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 총 지급액, 총 공제액, 실지급액 및 세부 지급/공제 항목 내역을 함께 조회하는 SQL
		// 支給総額、控除総額、手取り額および詳細支給・控除内訳を併せて照会するSQL
		String sql = "SELECT " + "s.total_pay, s.total_deduct, s.net_pay, " + "sd.item_type, sd.item_name, sd.amount "
				+ "FROM salary s " + "JOIN salary_date sd_date ON s.salary_date_id = sd_date.salary_date_id "
				+ "JOIN salary_detail sd ON s.salary_id = sd.salary_id "
				+ "WHERE s.emp_id = ? AND sd_date.pay_year = ? AND sd_date.pay_month = ? "
				+ "ORDER BY sd.item_type DESC, sd.detail_id ASC";

		try {
			// 바인딩 변수 세팅 (사원 ID, 귀속연도, 귀속월) 및 쿼리 실행
			// バインド変数の設定（社員ID、帰属年度、帰属月）およびクエリ実行
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);

			rs = pstmt.executeQuery();

			SalaryCompositionDTO dto = null;

			// 조회 결과셋 순회 및 데이터 매핑
			// 照会結果セットのループ処理およびデータマッピング
			while (rs.next()) {
				// 첫 번째 행 처리 시 DTO 객체 생성 및 급여 요약 정보(총액, 공제액, 실지급액) 세팅
				// 1行目の処理時にDTOオブジェクトを生成し、給与要約情報（総額、控除額、手取り額）を設定
				if (dto == null) {
					dto = new SalaryCompositionDTO();
					dto.setEmpId(empId);
					dto.setYear(year);
					dto.setMonth(month);
					dto.setTotalPay(rs.getLong("total_pay"));
					dto.setTotalDeduct(rs.getLong("total_deduct"));
					dto.setNetPay(rs.getLong("net_pay"));
				}

				String itemType = rs.getString("item_type");
				String itemName = rs.getString("item_name");
				long amount = rs.getLong("amount");

				SalaryItemDTO item = new SalaryItemDTO(itemName, itemType, amount);

				// 항목 유형별(PAY: 지급, DEDUCT: 공제) 매핑 처리
				// 項目タイプ別（PAY: 支給、DEDUCT: 控除）マッピング処理
				if ("PAY".equalsIgnoreCase(itemType)) {
					dto.addPayItem(item);
				} else if ("DEDUCT".equalsIgnoreCase(itemType)) {
					dto.addDeductItem(item);
				}
			}

			// 조회된 데이터가 있는 경우 구성 항목별 비율 계산 실행
			// 照会データが存在する場合、構成項目別の割合計算を実行
			if (dto != null) {
				dto.calculateRates();
			}

			return dto;

		} finally {
			// 데이터베이스 연결 자원 해제
			// データベース接続リソースの解放
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

}