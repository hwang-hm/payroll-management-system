package salary.ledger.viewdto;

import java.util.ArrayList;
import java.util.List;

// 항목별 대장 사원 한 줄
public class ItemLedgerRow {

	private int empId;
	private String employmentType;
	private String nameKor;
	private String deptName;
	private String positionName;
	private List<Long> monthAmounts = new ArrayList<>();
	private long total;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getNameKor() {
		return nameKor;
	}

	public void setNameKor(String nameKor) {
		this.nameKor = nameKor;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public List<Long> getMonthAmounts() {
		return monthAmounts;
	}

	public void setMonthAmounts(List<Long> monthAmounts) {
		this.monthAmounts = monthAmounts == null ? new ArrayList<>() : monthAmounts;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
