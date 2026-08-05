package attendance.model;

import java.sql.Date;

/**
 * 일용직 근무기록 정보를 저장하는 모델 클래스 日雇い勤務記録情報を保存するモデルクラス
 */

public class DailyWork {

	// 일용직 근무기록 번호 / 日雇い勤務記録番号

	private int dailyWorkId;

	// 근무일자 / 勤務日
	private Date workDate;

	// 현장 또는 프로젝트명 / 現場またはプロジェクト名
	private String projectName;

	// 일당 / 日当
	private int dailyWage;

	// 지급률 / 支給率
	private double paymentRate;

	// 소득세 / 所得税
	private int incomeTax;

	// 지방소득세 / 地方所得税
	private int localIncomeTax;

	// 실지급액 / 実支給額
	private int netPay;

	// 사원번호 / 社員番号
	private int empId;

	// 기본 생성자 / デフォルトコンストラクタ
	public DailyWork() {

	}

	// 전체 필드 생성자 / 全フィールドコンストラクタ
	public DailyWork(int dailyWorkId, Date workDate, String projectName, int dailyWage,
            double paymentRate, int incomeTax, int localIncomeTax, int netPay, int empId) {
        this.dailyWorkId = dailyWorkId;
        this.workDate = workDate;
        this.projectName = projectName;
        this.dailyWage = dailyWage;
        this.paymentRate = paymentRate;
        this.incomeTax = incomeTax;
        this.localIncomeTax = localIncomeTax;
        this.netPay = netPay;
        this.empId = empId;
    }

	// getter 및 setter / getterおよびsetter
	public int getDailyWorkId() {
        return dailyWorkId;
    }

    public void setDailyWorkId(int dailyWorkId) {
        this.dailyWorkId = dailyWorkId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getDailyWage() {
        return dailyWage;
    }

    public void setDailyWage(int dailyWage) {
        this.dailyWage = dailyWage;
    }

    public double getPaymentRate() {
        return paymentRate;
    }

    public void setPaymentRate(double paymentRate) {
        this.paymentRate = paymentRate;
    }

    public int getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(int incomeTax) {
        this.incomeTax = incomeTax;
    }

    public int getLocalIncomeTax() {
        return localIncomeTax;
    }

    public void setLocalIncomeTax(int localIncomeTax) {
        this.localIncomeTax = localIncomeTax;
    }

    public int getNetPay() {
        return netPay;
    }

    public void setNetPay(int netPay) {
        this.netPay = netPay;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

	// 객체 정보를 문자열로 반환 / オブジェクト情報を文字列として返す
	@Override
	public String toString() {
        return "DailyWork [dailyWorkId=" + dailyWorkId
                + ", workDate=" + workDate
                + ", projectName=" + projectName
                + ", dailyWage=" + dailyWage
                + ", paymentRate=" + paymentRate
                + ", incomeTax=" + incomeTax
                + ", localIncomeTax=" + localIncomeTax
                + ", netPay=" + netPay
                + ", empId=" + empId + "]";
    }

}
