package salary.ledger.viewdto;

// 사원선택 모달 부서 필터용
// 社員選択モーダルの部署フィルタ用
public class EmpSalaryHistoryDept {

	private int deptId;
	private String deptName;

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
