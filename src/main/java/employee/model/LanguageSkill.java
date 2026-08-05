package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class LanguageSkill {
	private int languageId;
	private int empId;
	private String languageName;
	private String testName;
	private String officialScore;
	private Date acquisitionDate;
	private String readingLevel;
	private String writingLevel;
	private String speakingLevel;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
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
