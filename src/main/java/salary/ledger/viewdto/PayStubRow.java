package salary.ledger.viewdto;

// 급여명세서 왼쪽 목록 한 줄
public class PayStubRow {

	private int salaryId;
	private String employmentType;
	private String nameKor;
	private long netPay;

	public int getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(int salaryId) {
		this.salaryId = salaryId;
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

	public long getNetPay() {
		return netPay;
	}

	public void setNetPay(long netPay) {
		this.netPay = netPay;
	}
}
