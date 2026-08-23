package salary.dayworker.service;

import java.sql.Connection;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dayworker.dao.SalaryDao;
import salary.dayworker.dao.SalaryDateDao;
import salary.dayworker.dao.SalaryDetailDao;
import salary.dayworker.dao.SalaryItemDao;
import salary.dayworker.service.request.SalaryDetailResult;
import salary.model.Salary;
import salary.model.SalaryDate;
import salary.model.SalaryDetail;
import salary.model.SalaryItem;

/**
 * 일용직 급여 상세 내역 조회 서비스 (DB 커넥션 관리 및 비즈니스 판단)
 * 日雇い給与詳細履歴照会サービス (DBコネクション管理およびビジネス判断)
 */
public class GetDayworkerSalaryDetailService {

	private SalaryDateDao salaryDateDao = new SalaryDateDao();
	private SalaryDao salaryDao = new SalaryDao();
	private SalaryDetailDao salaryDetailDao = new SalaryDetailDao();
	private SalaryItemDao salaryItemDao = new SalaryItemDao();

	/**
	 * 사원 및 정산차수 정보 기반 급여 상세 내역 조회
	 * 社員および精算回数情報に基づく給与詳細履歴の照会
	 * 
	 * @param empId 사원 ID / 社員ID
	 * @param year 귀속년도 / 帰属年度
	 * @param month 귀속월 / 帰属月
	 * @param seq 정산 차수 / 精算回数
	 * @return 급여 상세 조회 결과 DTO / 給与詳細照会結果DTO
	 */
	public SalaryDetailResult getSalaryDetail(int empId, int year, int month, int seq) throws Exception {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();

			// 1. 귀속년월 및 차수 정보 조회 / 帰属年月および回数情報の照会
			SalaryDate salaryDate = salaryDateDao.selectByYearMonthSeq(conn, year, month, seq);
			Salary salary = null;

			if (salaryDate != null) {
				// 2. 해당 정산일자의 사원 급여 헤더 조회 / 該当精算日時の社員給与ヘッダー照会
				salary = salaryDao.selectByDateAndEmp(conn, salaryDate.getSalaryDateId(), empId);
			}

			if (salary != null) {
				// 3-1. 기존 저장된 상세 내역 조회 / 既存保存された詳細履歴の照会
				List<SalaryDetail> details = salaryDetailDao.selectBySalaryId(conn, salary.getSalaryId());
				return SalaryDetailResult.savedResult(salary, details);
			} else {
				// 3-2. 저장 내역이 없는 경우 활성화된 공제 항목 조회 / 保存履歴がない場合、活性化された控除項目の照会
				List<SalaryItem> activeItems = salaryItemDao.selectActiveDeductionItems(conn);
				return SalaryDetailResult.unsavedResult(activeItems);
			}

		} finally {
			// DB 커넥션 자원 반납 / DBコネクションリソースの返却
			JdbcUtil.close(conn);
		}
	}
}