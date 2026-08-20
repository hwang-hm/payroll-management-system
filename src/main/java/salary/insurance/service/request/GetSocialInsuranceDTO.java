package salary.insurance.service.request;

import java.util.Date;

public class GetSocialInsuranceDTO {
	// 사원정보
	private String employmentType;
	private String nameKor;
	private Date hireDate;
	private String deptName;
	private String positionName;
	// 국민연금
	private Long npBiz; // 사업주 부담금
	private Long npEmp; // 근로자 공제액
	private Long npTotal; // 국민연금 합계

	// 건강보험
	private Long hiBiz; // 사업주 부담금
	private Long hiEmp; // 근로자 공제액
	private Long hiTotal; // 건강보험 합계

	// 노인장기요양보험
	private Long ltcBiz; // 사업주 부담금
	private Long ltcEmp; // 근로자 공제액
	private Long ltcTotal; // 노인장기요양보험 합계

	// 고용보험
	private Long eiBiz; // 사업주 부담금
	private Long eiEmp; // 근로자 공제액
	private Long eiTotal; // 고용보험 합계

	// 총 합계 (우측 3개 열)
	private Long totalBiz; // 사업주 총합계
	private Long totalEmp; // 근로자 총합계
	private Long totalSum; // 최종 총합계

	public GetSocialInsuranceDTO() {
	}

	public void calculateTotals() {
		long npB = nvl(this.npBiz);
		long npE = nvl(this.npEmp);
		long hiB = nvl(this.hiBiz);
		long hiE = nvl(this.hiEmp);
		long ltcB = nvl(this.ltcBiz);
		long ltcE = nvl(this.ltcEmp);
		long eiB = nvl(this.eiBiz);
		long eiE = nvl(this.eiEmp);

		// 각 보험별 소계 필드 계산
		this.npTotal = npB + npE;
		this.hiTotal = hiB + hiE;
		this.ltcTotal = ltcB + ltcE;
		this.eiTotal = eiB + eiE;

		// 우측 총합계 필드 계산
		this.totalBiz = npB + hiB + ltcB + eiB;
		this.totalEmp = npE + hiE + ltcE + eiE;
		this.totalSum = this.totalBiz + this.totalEmp;
	}

	private long nvl(Long val) {
		return val == null ? 0L : val;
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

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
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

	public Long getNpBiz() {
		return npBiz;
	}

	public void setNpBiz(Long npBiz) {
		this.npBiz = npBiz;
	}

	public Long getNpEmp() {
		return npEmp;
	}

	public void setNpEmp(Long npEmp) {
		this.npEmp = npEmp;
	}

	public Long getNpTotal() {
		return npTotal;
	}

	public void setNpTotal(Long npTotal) {
		this.npTotal = npTotal;
	}

	public Long getHiBiz() {
		return hiBiz;
	}

	public void setHiBiz(Long hiBiz) {
		this.hiBiz = hiBiz;
	}

	public Long getHiEmp() {
		return hiEmp;
	}

	public void setHiEmp(Long hiEmp) {
		this.hiEmp = hiEmp;
	}

	public Long getHiTotal() {
		return hiTotal;
	}

	public void setHiTotal(Long hiTotal) {
		this.hiTotal = hiTotal;
	}

	public Long getLtcBiz() {
		return ltcBiz;
	}

	public void setLtcBiz(Long ltcBiz) {
		this.ltcBiz = ltcBiz;
	}

	public Long getLtcEmp() {
		return ltcEmp;
	}

	public void setLtcEmp(Long ltcEmp) {
		this.ltcEmp = ltcEmp;
	}

	public Long getLtcTotal() {
		return ltcTotal;
	}

	public void setLtcTotal(Long ltcTotal) {
		this.ltcTotal = ltcTotal;
	}

	public Long getEiBiz() {
		return eiBiz;
	}

	public void setEiBiz(Long eiBiz) {
		this.eiBiz = eiBiz;
	}

	public Long getEiEmp() {
		return eiEmp;
	}

	public void setEiEmp(Long eiEmp) {
		this.eiEmp = eiEmp;
	}

	public Long getEiTotal() {
		return eiTotal;
	}

	public void setEiTotal(Long eiTotal) {
		this.eiTotal = eiTotal;
	}

	public Long getTotalBiz() {
		return totalBiz;
	}

	public void setTotalBiz(Long totalBiz) {
		this.totalBiz = totalBiz;
	}

	public Long getTotalEmp() {
		return totalEmp;
	}

	public void setTotalEmp(Long totalEmp) {
		this.totalEmp = totalEmp;
	}

	public Long getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(Long totalSum) {
		this.totalSum = totalSum;
	}
}
