package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class LanguageSkill {
	private Integer languageId;
	private Integer empId;
	private String languageName;
	private String testName;
	private String officialScore;
	private Date acquisitionDate;
	private String readingLevel;
	private String writingLevel;
	private String speakingLevel;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public LanguageSkill() {
	}

	public LanguageSkill(Integer languageId, Integer empId, String languageName, String testName, String officialScore,
			Date acquisitionDate, String readingLevel, String writingLevel, String speakingLevel, Timestamp createdAt,
			Timestamp updatedAt) {
		super();
		this.languageId = languageId;
		this.empId = empId;
		this.languageName = languageName;
		this.testName = testName;
		this.officialScore = officialScore;
		this.acquisitionDate = acquisitionDate;
		this.readingLevel = readingLevel;
		this.writingLevel = writingLevel;
		this.speakingLevel = speakingLevel;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getOfficialScore() {
		return officialScore;
	}

	public void setOfficialScore(String officialScore) {
		this.officialScore = officialScore;
	}

	public Date getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public String getReadingLevel() {
		return readingLevel;
	}

	public void setReadingLevel(String readingLevel) {
		this.readingLevel = readingLevel;
	}

	public String getWritingLevel() {
		return writingLevel;
	}

	public void setWritingLevel(String writingLevel) {
		this.writingLevel = writingLevel;
	}

	public String getSpeakingLevel() {
		return speakingLevel;
	}

	public void setSpeakingLevel(String speakingLevel) {
		this.speakingLevel = speakingLevel;
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
