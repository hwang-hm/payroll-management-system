package salary.dayworker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import salary.dayworker.service.request.GetSalaryListDTO;
import salary.model.Salary;

/**
 * 일용직 사원의 급여 정보 데이터베이스 접근을 담당하는 DAO 클래스입니다.
 * 日雇い社員の給与情報データベースアクセスを担当するDAOクラスです。
 */
public class SalaryDao {

	/**
	 * 특정 지급일 ID와 사원 ID에 해당하는 기존 급여 정보를 조회합니다. 支給日IDと社員IDに該当する既存の給与情報を照会します。
	 * 
	 * @param conn         DB 커넥션 / DBコネクション
	 * @param salaryDateId 급여 지급일 ID / 給与支給日ID
	 * @param empId        사원 ID / 社員ID
	 * @return 급여 모델 객체 (없을 경우 null) / 給与モデルオブジェクト (存在しない場合はnull)
	 */
	public Salary selectByDateAndEmp(Connection conn, int salaryDateId, int empId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 단일 급여 정보 조회 SQL
		// 単一給与情報照会SQL
		String sql = "SELECT salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay "
				+ "FROM salary WHERE salary_date_id = ? AND emp_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryDateId);
			pstmt.setInt(2, empId);
			rs = pstmt.executeQuery();

			// 조회 결과가 존재할 경우 Salary 객체에 바인딩 후 반환
			// 照会結果が存在する場合、Salaryオブジェクトにバインディングして返却
			if (rs.next()) {
				Salary s = new Salary();
				s.setSalaryId(rs.getInt("salary_id"));
				s.setSalaryDateId(rs.getInt("salary_date_id"));
				s.setEmpId(rs.getInt("emp_id"));
				s.setTotalPay(rs.getLong("total_pay"));
				s.setTotalDeduct(rs.getLong("total_deduct"));
				s.setNetPay(rs.getLong("net_pay"));
				return s;
			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * 특정 지급일 ID에 해당하는 일용직 사원 전체의 급여 목록을 조회합니다. (사원 및 부서 정보 JOIN)
	 * 特定の支給日IDに該当する日雇い社員全体の給与リストを照会します。(社員および部署情報のJOIN)
	 * 
	 * @param conn         DB 커넥션 / DBコネクション
	 * @param salaryDateId 급여 지급일 ID / 給与支給日ID
	 * @return 급여 목록 DTO 리스트 / 給与リストDTO一覧
	 */
	public List<GetSalaryListDTO> selectAllByDate(Connection conn, int salaryDateId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 일용직 사원 급여 및 부서 정보 조인 SQL
		// 日雇い社員の給与および部署情報の結合(JOIN) SQL
		String sql = "SELECT s.salary_id, s.salary_date_id, s.emp_id, s.total_pay, s.total_deduct, s.net_pay, "
				+ "e.name_kor, e.employment_type, " + "d.dept_name " + "FROM salary s "
				+ "JOIN employee e ON s.emp_id = e.emp_id " + "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "WHERE s.salary_date_id = ? AND e.employment_type = '일용직'";

		List<GetSalaryListDTO> salaryList = new ArrayList<>();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryDateId);
			rs = pstmt.executeQuery();

			// ResultSet을 순회하며 DTO 생성 후 리스트에 추가
			// ResultSetをループしながらDTOを生成しリストに追加
			while (rs.next()) {
				GetSalaryListDTO dto = new GetSalaryListDTO(rs.getInt("salary_id"), rs.getInt("salary_date_id"),
						rs.getInt("emp_id"), rs.getLong("total_pay"), rs.getLong("total_deduct"), rs.getLong("net_pay"),
						rs.getString("name_kor"), rs.getString("employment_type"), rs.getString("dept_name"));
				salaryList.add(dto);
			}
			return salaryList;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * 신규 급여 정보를 등록합니다. (시퀀스를 사용하여 PK 생성)
	 * 新規給与情報を登録します。(シーケンスを使用してPKを生成)
	 * 
	 * @param conn   DB 커넥션 / DBコネクション
	 * @param salary 저장할 급여 정보 모델 / 保存する給与情報モデル
	 * @return 생성된 신규 급여 ID (salary_id) / 生成された新規給与ID (salary_id)
	 */
	public int insert(Connection conn, Salary salary) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 시퀀스에서 다음 PK 값을 채번하는 SQL
		// シーケンスから次のPK値を採番するSQL
		String seqSql = "SELECT seq_salary.NEXTVAL FROM dual";

		// 신규 급여 정보 저장 SQL
		// 新規給与情報保存SQL
		String insertSql = "INSERT INTO salary (salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, SYSDATE)";
		try {
			// 1. 시퀀스를 통해 신규 salary_id 획득
			// 1. シーケンスを通じて新規 salary_id を取得
			pstmt = conn.prepareStatement(seqSql);
			rs = pstmt.executeQuery();
			int newSalaryId = 0;
			if (rs.next()) {
				newSalaryId = rs.getInt(1);
			}
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);

			// 모델 객체에 새로 채번된 ID 세팅
			// モデルオブジェクトに新たに採番されたIDを設定
			salary.setSalaryId(newSalaryId);

			// 2. 신규 급여 데이터 INSERT 실행
			// 2. 新規給与データのINSERT実行
			pstmt = conn.prepareStatement(insertSql);
			pstmt.setLong(1, salary.getSalaryId());
			pstmt.setLong(2, salary.getSalaryDateId());
			pstmt.setLong(3, salary.getEmpId());
			pstmt.setLong(4, salary.getTotalPay());
			pstmt.setLong(5, salary.getTotalDeduct());
			pstmt.setLong(6, salary.getNetPay());
			pstmt.executeUpdate();

			return newSalaryId;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * 기존 급여의 지급액, 공제액, 실수령액 및 수정일시를 업데이트합니다.
	 * 既存給与の支給額、控除額、手取り額および更新日時を更新します。
	 * 
	 * @param conn   DB 커넥션 / DBコネクション
	 * @param salary 수정할 급여 정보 모델 / 更新する給与情報モデル
	 * @return 수정 성공 여부 (true: 성공) / 更新成功の有無 (true: 成功)
	 */
	public boolean update(Connection conn, Salary salary) throws SQLException {
		PreparedStatement pstmt = null;

		// 급여 헤더 정보 수정 SQL
		// 給与ヘッダー情報更新SQL
		String sql = "UPDATE salary SET total_pay = ?, total_deduct = ?, net_pay = ?, updated_at = SYSDATE "
				+ "WHERE salary_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, salary.getTotalPay());
			pstmt.setLong(2, salary.getTotalDeduct());
			pstmt.setLong(3, salary.getNetPay());
			pstmt.setLong(4, salary.getSalaryId());

			// 수정된 행의 수(affected rows)가 1개 이상이면 true 반환
			// 更新された行数(affected rows)が1以上の場合trueを返却
			return pstmt.executeUpdate() > 0;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}