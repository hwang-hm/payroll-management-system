package salary.ledger.viewdto;

// 급여대장 상세 한 행 (사원별 고정 지급 9항목)
// 給与台帳詳細1行 (社員別固定支給9項目)
public class SalaryLedgerDetailRow {

	private int salaryId;
	private int empId;
	private String employmentType;
	private String nameKor;
	private String hireDate;
	private String deptName;
	private String positionName;

	private long basicPay;
	private long mealAllowance;
	private long childcareAllowance;
	private long positionAllowance;
	private long carAllowance;
	private long seniorityAllowance;
	private long nightDutyAllowance;
	private long bonus;
	private long holidayAllowance;
	private long totalPay;

	public int getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(int salaryId) {
		this.salaryId = salaryId;
	}

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

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
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

	public long getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(long basicPay) {
		this.basicPay = basicPay;
	}

	public long getMealAllowance() {
		return mealAllowance;
	}

	public void setMealAllowance(long mealAllowance) {
		this.mealAllowance = mealAllowance;
	}

	public long getChildcareAllowance() {
		return childcareAllowance;
	}

	public void setChildcareAllowance(long childcareAllowance) {
		this.childcareAllowance = childcareAllowance;
	}

	public long getPositionAllowance() {
		return positionAllowance;
	}

	public void setPositionAllowance(long positionAllowance) {
		this.positionAllowance = positionAllowance;
	}

	public long getCarAllowance() {
		return carAllowance;
	}

	public void setCarAllowance(long carAllowance) {
		this.carAllowance = carAllowance;
	}

	public long getSeniorityAllowance() {
		return seniorityAllowance;
	}

	public void setSeniorityAllowance(long seniorityAllowance) {
		this.seniorityAllowance = seniorityAllowance;
	}

	public long getNightDutyAllowance() {
		return nightDutyAllowance;
	}

	public void setNightDutyAllowance(long nightDutyAllowance) {
		this.nightDutyAllowance = nightDutyAllowance;
	}

	public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}

	public long getHolidayAllowance() {
		return holidayAllowance;
	}

	public void setHolidayAllowance(long holidayAllowance) {
		this.holidayAllowance = holidayAllowance;
	}

	public long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(long totalPay) {
		this.totalPay = totalPay;
	}

	// 고정 9항목 합계로 지급총액 계산
	// 固定9項目合計で支給総額を計算
	public void recalculateTotals() {
		this.totalPay = basicPay + mealAllowance + childcareAllowance + positionAllowance + carAllowance
				+ seniorityAllowance + nightDutyAllowance + bonus + holidayAllowance;
	}
}
