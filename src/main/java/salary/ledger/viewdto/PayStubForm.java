package salary.ledger.viewdto;

import java.util.ArrayList;
import java.util.List;

import salary.model.SalaryDetail;

// 급여명세서 오른쪽 미리보기 폼
public class PayStubForm {

	private String nameKor;
	private String birthDate;
	private String deptName;
	private String positionName;
	private String hireDate;
	private String payDate;
	private List<SalaryDetail> payDetails = new ArrayList<>();
	private List<SalaryDetail> deductDetails = new ArrayList<>();
	private List<PayStubDetailLine> detailLines = new ArrayList<>();
	private long totalPay;
	private long totalDeduct;
	private long netPay;

	public String getNameKor() {
		return nameKor;
	}

	public void setNameKor(String nameKor) {
		this.nameKor = nameKor;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
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

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public List<SalaryDetail> getPayDetails() {
		return payDetails;
	}

	public void setPayDetails(List<SalaryDetail> payDetails) {
		this.payDetails = payDetails == null ? new ArrayList<>() : payDetails;
	}

	public List<SalaryDetail> getDeductDetails() {
		return deductDetails;
	}

	public void setDeductDetails(List<SalaryDetail> deductDetails) {
		this.deductDetails = deductDetails == null ? new ArrayList<>() : deductDetails;
	}

	public List<PayStubDetailLine> getDetailLines() {
		return detailLines;
	}

	public void setDetailLines(List<PayStubDetailLine> detailLines) {
		this.detailLines = detailLines == null ? new ArrayList<>() : detailLines;
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
}
