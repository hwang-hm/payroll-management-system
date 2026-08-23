package salary.dayworker.service.request;

import java.util.List;
import salary.model.Salary;
import salary.model.SalaryDetail;
import salary.model.SalaryItem;

/**
 * 일용직 급여 상세 조회 결과 DTO
 * 日雇い給与詳細照会結果DTO
 */
public class SalaryDetailResult {
	private boolean saved; // 저장 여부 / 保存有無
	private Salary salary; // 급여 헤더 정보 / 給与ヘッダー情報
	private List<SalaryDetail> details; // 급여 상세 내역 / 給与詳細履歴
	private List<SalaryItem> activeDeductionItems; // 기본 활성 공제 항목 / 基本活性控除項目

	public SalaryDetailResult(boolean saved, Salary salary, List<SalaryDetail> details, List<SalaryItem> activeDeductionItems) {
		this.saved = saved;
		this.salary = salary;
		this.details = details;
		this.activeDeductionItems = activeDeductionItems;
	}

	// 기존 저장된 데이터가 존재할 때 팩토리 메서드 / 既存保存データが存在する場合のファクトリメソッド
	public static SalaryDetailResult savedResult(Salary salary, List<SalaryDetail> details) {
		return new SalaryDetailResult(true, salary, details, null);
	}

	// 저장된 데이터가 없을 때 팩토리 메서드 / 保存データが存在しない場合のファクトリメソッド
	public static SalaryDetailResult unsavedResult(List<SalaryItem> activeDeductionItems) {
		return new SalaryDetailResult(false, null, null, activeDeductionItems);
	}

	public boolean isSaved() { return saved; }
	public Salary getSalary() { return salary; }
	public List<SalaryDetail> getDetails() { return details; }
	public List<SalaryItem> getActiveDeductionItems() { return activeDeductionItems; }
}