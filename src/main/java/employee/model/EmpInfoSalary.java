package employee.model;

import java.sql.Timestamp;

public class EmpInfoSalary {

	private Integer empId;
	private String nationalPensionYN;
	private String healthInsuranceYN;
	private int healthDiscountRate;
	private String longtermCareYN;
	private int longtermCareDiscountRate;
	private String employmentInsuranceYN;
	private String incomeTaxType;
	private String youthTaxReductionYN;
	private int youthTaxReductionRate;
	private String durunuriType;
	private String durunuriSplitYN;
	private int durunuriPensionRate;
	private int durunuriEmploymentRate;
	private int baseSalary;
	private Integer pensionSalary;
	private Integer healthSalary;
	private Integer employmentSalary;
	private String salaryBank;
	private String salaryAccount;

	private Timestamp createdAt;
	private Timestamp updatedAt;

	public EmpInfoSalary() {
	}

	public EmpInfoSalary(Integer empId, String nationalPensionYN, String healthInsuranceYN, int healthDiscountRate,
			String longtermCareYN, int longtermCareDiscountRate, String employmentInsuranceYN, String incomeTaxType,
			String youthTaxReductionYN, int youthTaxReductionRate, String durunuriType, String durunuriSplitYN,
			int durunuriPensionRate, int durunuriEmploymentRate, int baseSalary, Integer pensionSalary,
			Integer healthSalary, Integer employmentSalary, String salaryBank, String salaryAccount,
			Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.empId = empId;
		this.nationalPensionYN = nationalPensionYN;
		this.healthInsuranceYN = healthInsuranceYN;
		this.healthDiscountRate = healthDiscountRate;
		this.longtermCareYN = longtermCareYN;
		this.longtermCareDiscountRate = longtermCareDiscountRate;
		this.employmentInsuranceYN = employmentInsuranceYN;
		this.incomeTaxType = incomeTaxType;
		this.youthTaxReductionYN = youthTaxReductionYN;
		this.youthTaxReductionRate = youthTaxReductionRate;
		this.durunuriType = durunuriType;
		this.durunuriSplitYN = durunuriSplitYN;
		this.durunuriPensionRate = durunuriPensionRate;
		this.durunuriEmploymentRate = durunuriEmploymentRate;
		this.baseSalary = baseSalary;
		this.pensionSalary = pensionSalary;
		this.healthSalary = healthSalary;
		this.employmentSalary = employmentSalary;
		this.salaryBank = salaryBank;
		this.salaryAccount = salaryAccount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getNationalPensionYN() {
		return nationalPensionYN;
	}

	public void setNationalPensionYN(String nationalPensionYN) {
		this.nationalPensionYN = nationalPensionYN;
	}

	public String getHealthInsuranceYN() {
		return healthInsuranceYN;
	}

	public void setHealthInsuranceYN(String healthInsuranceYN) {
		this.healthInsuranceYN = healthInsuranceYN;
	}

	public int getHealthDiscountRate() {
		return healthDiscountRate;
	}

	public void setHealthDiscountRate(int healthDiscountRate) {
		this.healthDiscountRate = healthDiscountRate;
	}

	public String getLongtermCareYN() {
		return longtermCareYN;
	}

	public void setLongtermCareYN(String longtermCareYN) {
		this.longtermCareYN = longtermCareYN;
	}

	public int getLongtermCareDiscountRate() {
		return longtermCareDiscountRate;
	}

	public void setLongtermCareDiscountRate(int longtermCareDiscountRate) {
		this.longtermCareDiscountRate = longtermCareDiscountRate;
	}

	public String getEmploymentInsuranceYN() {
		return employmentInsuranceYN;
	}

	public void setEmploymentInsuranceYN(String employmentInsuranceYN) {
		this.employmentInsuranceYN = employmentInsuranceYN;
	}

	public String getIncomeTaxType() {
		return incomeTaxType;
	}

	public void setIncomeTaxType(String incomeTaxType) {
		this.incomeTaxType = incomeTaxType;
	}

	public String getYouthTaxReductionYN() {
		return youthTaxReductionYN;
	}

	public void setYouthTaxReductionYN(String youthTaxReductionYN) {
		this.youthTaxReductionYN = youthTaxReductionYN;
	}

	public int getYouthTaxReductionRate() {
		return youthTaxReductionRate;
	}

	public void setYouthTaxReductionRate(int youthTaxReductionRate) {
		this.youthTaxReductionRate = youthTaxReductionRate;
	}

	public String getDurunuriType() {
		return durunuriType;
	}

	public void setDurunuriType(String durunuriType) {
		this.durunuriType = durunuriType;
	}

	public String getDurunuriSplitYN() {
		return durunuriSplitYN;
	}

	public void setDurunuriSplitYN(String durunuriSplitYN) {
		this.durunuriSplitYN = durunuriSplitYN;
	}

	public int getDurunuriPensionRate() {
		return durunuriPensionRate;
	}

	public void setDurunuriPensionRate(int durunuriPensionRate) {
		this.durunuriPensionRate = durunuriPensionRate;
	}

	public int getDurunuriEmploymentRate() {
		return durunuriEmploymentRate;
	}

	public void setDurunuriEmploymentRate(int durunuriEmploymentRate) {
		this.durunuriEmploymentRate = durunuriEmploymentRate;
	}

	public int getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(int baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Integer getPensionSalary() {
		return pensionSalary;
	}

	public void setPensionSalary(Integer pensionSalary) {
		this.pensionSalary = pensionSalary;
	}

	public Integer getHealthSalary() {
		return healthSalary;
	}

	public void setHealthSalary(Integer healthSalary) {
		this.healthSalary = healthSalary;
	}

	public Integer getEmploymentSalary() {
		return employmentSalary;
	}

	public void setEmploymentSalary(Integer employmentSalary) {
		this.employmentSalary = employmentSalary;
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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
	
