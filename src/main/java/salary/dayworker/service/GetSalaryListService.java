package salary.dayworker.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.dayworker.dao.SalaryDao;
import salary.dayworker.service.request.GetSalaryListDTO;

/**
 * 일용직 사원의 급여 목록 조회 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 日雇い社員の給与リスト照会ビジネスロジックを処理するサービスクラスです。
 */
public class GetSalaryListService {

	// 급여 데이터베이스 접근을 담당하는 DAO 객체
	// 給与データベースアクセスを担当するDAOオブジェクト
	private SalaryDao salaryDao = new SalaryDao();

	/**
	 * 지정된 급여 지급일 ID(salaryDateId)에 해당하는 일용직 급여 목록을 조회합니다.
	 * 指定された給与支給日ID(salaryDateId)に該当する日雇い給与リストを照会します。
	 * 
	 * @param salaryDateId 급여 지급일 식별 ID / 給与支給日識別ID
	 * @return 급여 목록 DTO 리스트 / 給与リストDTO一覧
	 */
	public List<GetSalaryListDTO> getSalaryList(int salaryDateId) {
		try (Connection conn = ConnectionProvider.getConnection()) {

			// DAO를 호출하여 해당 지급일 ID의 급여 데이터 목록을 DB에서 조회 후 반환
			// DAOを呼び出して該当支給日IDの給与データリストをDBから照会後に返却
			return salaryDao.selectAllByDate(conn, salaryDateId);

		} catch (SQLException e) {
			// DB 조회 작업 중 SQL 예외가 발생할 경우, 런타임 예외로 감싸서(Wrapping) 호출부로 전달
			// DB照会作業中にSQL例外が発生した場合、ランタイム例外でラッピングして呼び出し元へ伝播
			throw new RuntimeException("일용직 급여 목록 조회 실패", e);
		}
	}

}