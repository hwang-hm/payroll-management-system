package employee.register.service;

import java.sql.Date;
import java.util.Map;

public class EmpRequest {
	
	//필수 입력 항목
	// 必須入力項目
	private String nameKor;
	private Date hireDate ;
	private String employmentType;

	//선택 입력 항목
	// 選択入力項目
	private Integer  deptId;
	private String nationality;
	private String nameEng;
	private Date retireDate;
	private Integer  positionId;
	private String residentNo;
	private String zipCode;
	private String address;
	private String phoneNum;
	private String mobileNum;
	private String email;
	private String sns;
	private String remarks;
	private String photoPath;
	
	
	public EmpRequest() {
	}


	public EmpRequest(String nameKor, Date hireDate, String employmentType, Integer deptId,
			String nationality, String nameEng, Date retireDate, Integer positionId, String residentNo, String zipCode,
			String address, String phoneNum, String mobileNum, String email, String sns, String remarks,
			String photoPath) {
		
		this.nameKor = nameKor;
		this.hireDate = hireDate;
		this.employmentType = employmentType;
		this.deptId = deptId;
		this.nationality = nationality;
		this.nameEng = nameEng;
		this.retireDate = retireDate;
		this.positionId = positionId;
		this.residentNo = residentNo;
		this.zipCode = zipCode;
		this.address = address;
		this.phoneNum = phoneNum;
		this.mobileNum = mobileNum;
		this.email = email;
		this.sns = sns;
		this.remarks = remarks;
		this.photoPath = photoPath;
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


	public String getEmploymentType() {
		return employmentType;
	}


	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}


	public Integer getDeptId() {
		return deptId;
	}


	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public String getNameEng() {
		return nameEng;
	}


	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}


	public Date getRetireDate() {
		return retireDate;
	}


	public void setRetireDate(Date retireDate) {
		this.retireDate = retireDate;
	}


	public Integer getPositionId() {
		return positionId;
	}


	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}


	public String getResidentNo() {
		return residentNo;
	}


	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhoneNum() {
		return phoneNum;
	}


	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
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


	public String getSns() {
		return sns;
	}


	public void setSns(String sns) {
		this.sns = sns;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getPhotoPath() {
		return photoPath;
	}


	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	
	public void validate(Map<String, Boolean> errors) {
		if (nameKor == null || nameKor.trim().isEmpty()) {
			errors.put("nameKor", Boolean.TRUE);
		}
		if (hireDate == null) {
			errors.put("hireDate", Boolean.TRUE);
		}
		if (employmentType == null || employmentType.trim().isEmpty()) {
			errors.put("employmentType", Boolean.TRUE);
		}
	}
}

