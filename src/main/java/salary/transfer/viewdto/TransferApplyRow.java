package salary.transfer.viewdto;

import java.text.SimpleDateFormat;
import java.util.Date;

// 이체신청 목록 한 행 (조인 결과 + 이체 실행에 필요한 값)
public class TransferApplyRow {

	private int salaryId;
	private int empId;
	private String employmentType;
	private String nameKor;
	private String nameEng;
	private String deptName;
	private String positionName;
	private Date hireDate;
	private String mobileNum;
	private String email;
	private String salaryBank;
	private String salaryAccount;
	private String withdrawBank;
	private String withdrawAccount;
	private long totalPay;
	private long totalDeduct;
	private long netPay;

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

	public String getNameEng() {
		return nameEng;
	}

	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
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

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getHireDateText() {
		if (hireDate == null) {
			return "-";
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(hireDate);
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalaryBank() {
		return salaryBank;
	}

	public void setSalaryBank(String salaryBank) {
		this.salaryBank = salaryBank;
	}

	public String getSalaryAccount() {
		return salaryAccount;
	}

	public void setSalaryAccount(String salaryAccount) {
		this.salaryAccount = salaryAccount;
	}

	public String getWithdrawBank() {
		return withdrawBank;
	}

	public void setWithdrawBank(String withdrawBank) {
		this.withdrawBank = withdrawBank;
	}

	public String getWithdrawAccount() {
		return withdrawAccount;
	}

	public void setWithdrawAccount(String withdrawAccount) {
		this.withdrawAccount = withdrawAccount;
	}

	public long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(long totalPay) {
		this.totalPay = totalPay;
	}

	public long getTotalDeduct() {
		return totalDeduct;
	}

	public void setTotalDeduct(long totalDeduct) {
		this.totalDeduct = totalDeduct;
	}

	public long getNetPay() {
		return netPay;
	}

	public void setNetPay(long netPay) {
		this.netPay = netPay;
	}

	public String getValueByCode(String code) {
		if (code == null) {
			return "";
		}
		switch (code) {
		case "EMP_TYPE":
			return nullToDash(employmentType);
		case "EMP_ID":
			return String.valueOf(empId);
		case "NAME_KOR":
			return nullToDash(nameKor);
		case "NAME_ENG":
			return nullToDash(nameEng);
		case "DEPT":
			return nullToDash(deptName);
		case "POSITION":
			return nullToDash(positionName);
		case "HIRE_DATE":
			return getHireDateText();
		case "MOBILE":
			return nullToDash(mobileNum);
		case "EMAIL":
			return nullToDash(email);
		case "BANK":
			return nullToDash(salaryBank);
		case "ACCOUNT":
			return nullToDash(salaryAccount);
		case "TOTAL_PAY":
			return String.format("%,d", totalPay);
		case "TOTAL_DEDUCT":
			return String.format("%,d", totalDeduct);
		case "NET_PAY":
			return String.format("%,d", netPay);
		default:
			return "";
		}
	}

	private String nullToDash(String value) {
		return value == null || value.trim().isEmpty() ? "-" : value;
	}
}
