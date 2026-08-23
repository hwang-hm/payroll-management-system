package salary.dayworker.service;

import java.sql.Connection;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dayworker.dao.SalaryDao;
import salary.dayworker.dao.SalaryDateDao;
import salary.dayworker.dao.SalaryDetailDao;
import salary.dayworker.service.request.RegistSalaryRequest;
import salary.model.Salary;
import salary.model.SalaryDate;
import salary.model.SalaryDetail;

/**
 * 일용직 급여 등록 및 수정 비즈니스 로직 서비스 (트랜잭션 제어)
 * 日雇い給与登録および修正ビジネスロジックサービス (トランザクション制御)
 */
public class RegistDayworkerSalaryService {

	private SalaryDateDao salaryDateDao = new SalaryDateDao();
	private SalaryDao salaryDao = new SalaryDao();
	private SalaryDetailDao salaryDetailDao = new SalaryDetailDao();

	/**
	 * 일용직 급여 등록/수정 통합 트랜잭션 메서드
	 * 日雇い給与登録/修正統合トランザクションメソッド
	 * 
	 * @param req 급여 등록 요청 데이터 객체 / 給与登録リクエストデータオブジェクト
	 * @return 등록 성공 여부 / 登録成功有無
	 */
	public boolean regist(RegistSalaryRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false); // 수동 트랜잭션 시작 / 手動トランザクション開始

			// Step 1: SALARY_DATE (급여 정산일자) 존재 확인 및 생성/수정
			// Step 1: SALARY_DATE (給与精算日時) 存在確認および生成/更新
			SalaryDate salaryDate = salaryDateDao.selectByYearMonthSeq(conn, req.getPayYear(), req.getPayMonth(),
					req.getPaySeq());

			int salaryDateId;
			if (salaryDate == null) {
				// 정산일자 정보가 없는 경우 신규 등록 / 精算日時情報が存在しない場合、新規登録
				SalaryDate newDate = new SalaryDate();
				newDate.setPayYear(req.getPayYear());
				newDate.setPayMonth(req.getPayMonth());
				newDate.setPaySeq(req.getPaySeq());
				newDate.setStartDate(req.getStartDate());
				newDate.setEndDate(req.getEndDate());
				newDate.setPayDate(req.getPayDate());
				salaryDateDao.insert(conn, newDate);

				// 생선된 PK(salaryDateId) 조회 / 生成されたPK(salaryDateId)照会
				SalaryDate created = salaryDateDao.selectByYearMonthSeq(conn, req.getPayYear(), req.getPayMonth(),
						req.getPaySeq());
				salaryDateId = created.getSalaryDateId();
			} else {
				// 기존 정산일자 기간 정보 업데이트 / 既存の精算日時期間情報の更新
				salaryDate.setStartDate(req.getStartDate());
				salaryDate.setEndDate(req.getEndDate());
				salaryDate.setPayDate(req.getPayDate());
				salaryDateDao.update(conn, salaryDate);
				salaryDateId = salaryDate.getSalaryDateId();
			}

			// Step 2: SALARY (급여 헤더) 저장/수정
			// Step 2: SALARY (給与ヘッダー) 保存/更新
			Salary salary = salaryDao.selectByDateAndEmp(conn, salaryDateId, req.getEmpId());
			int salaryId;

			if (salary == null) {
				// 사원 급여 헤더가 없을 경우 신규 생성 / 社員給与ヘッダーが存在しない場合、新規作成
				Salary newSalary = new Salary();
				newSalary.setSalaryDateId(salaryDateId);
				newSalary.setEmpId(req.getEmpId());
				newSalary.setTotalPay(req.getTotalPay());
				newSalary.setTotalDeduct(req.getTotalDeduct());
				newSalary.setNetPay(req.getNetPay());
				salaryId = salaryDao.insert(conn, newSalary);
			} else {
				// 기존 급여 헤더 금액 수정 / 既存給与ヘッダー金額の更新
				salary.setTotalPay(req.getTotalPay());
				salary.setTotalDeduct(req.getTotalDeduct());
				salary.setNetPay(req.getNetPay());
				salaryDao.update(conn, salary);
				salaryId = salary.getSalaryId();
			}

			// Step 3: SALARY_DETAIL (급여 상세 항목) 재등록 (기존 내역 삭제 후 Insert)
			// Step 3: SALARY_DETAIL (給与詳細項目) 再登録 (既存履歴削除後 Insert)
			salaryDetailDao.deleteBySalaryId(conn, salaryId);

			if (req.getDetails() != null) {
				for (RegistSalaryRequest.SalaryDetailRequest detailReq : req.getDetails()) {
					SalaryDetail detail = new SalaryDetail();
					detail.setSalaryId(salaryId);
					detail.setSalaryItemId(detailReq.getSalaryItemId());
					detail.setItemType(detailReq.getItemType());
					detail.setItemName(detailReq.getItemName());
					detail.setTaxYn(detailReq.getTaxYn());
					detail.setRoundUnit("NONE");
					detail.setAmount(detailReq.getAmount());
					detail.setMemo(detailReq.getMemo());

					salaryDetailDao.insert(conn, detail);
				}
			}

			// 모든 작업 성공 시 Commit / すべての作業成功時 Commit
			conn.commit();
			return true;

		} catch (Exception e) {
			// 오류 발생 시 트랜잭션 Rollback / エラー発生時トランザクション Rollback
			JdbcUtil.rollback(conn);
			throw new RuntimeException("급여 저장 실패 / 給与保存失敗: " + e.getMessage(), e);
		} finally {
			// DB 커넥션 자원 반납 / DBコネクションリソースの返却
			JdbcUtil.close(conn);
		}
	}
}