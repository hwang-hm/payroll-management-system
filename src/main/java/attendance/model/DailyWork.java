package attendance.model;

import java.sql.Date;

/**
 * 일용직 근무기록 정보를 저장하는 모델 클래스 日雇い勤務記録情報を保存するモデルクラス
 */

public class DailyWork {

	// 일용직 근무기록 번호 / 日雇い勤務記録番号

	private int daily_work_id;

	// 근무일자 / 勤務日
	private Date work_date;

	// 현장 또는 프로젝트명 / 現場またはプロジェクト名
	private String project_name;

	// 일당 / 日当
	private int daily_wage;

	// 지급률 / 支給率
	private double payment_rate;

	// 소득세 / 所得税
	private int income_tax;

	// 지방소득세 / 地方所得税
	private int local_income_tax;

	// 실지급액 / 実支給額
	private int net_pay;

	// 사원번호 / 社員番号
	private int emp_id;

	// 기본 생성자 / デフォルトコンストラクタ
	public DailyWork() {

	}

	// 전체 필드 생성자 / 全フィールドコンストラクタ
	public DailyWork(int daily_work_id, Date work_date, String project_name, int daily_wage, double payment_rate,
			int income_tax, int local_income_tax, int net_pay, int emp_id) {
		this.daily_work_id = daily_work_id;
		this.work_date = work_date;
		this.project_name = project_name;
		this.daily_wage = daily_wage;
		this.payment_rate = payment_rate;
		this.income_tax = income_tax;
		this.local_income_tax = local_income_tax;
		this.net_pay = net_pay;
		this.emp_id = emp_id;
	}

	// getter 및 setter / getterおよびsetter
	public int getDaily_work_id() {
		return daily_work_id;
	}

	public void setDaily_work_id(int daily_work_id) {
		this.daily_work_id = daily_work_id;
	}

	public Date getWork_date() {
		return work_date;
	}

	public void setWork_date(Date work_date) {
		this.work_date = work_date;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public int getDaily_wage() {
		return daily_wage;
	}

	public void setDaily_wage(int daily_wage) {
		this.daily_wage = daily_wage;
	}

	public double getPayment_rate() {
	    return payment_rate;
	}

	public void setPayment_rate(double payment_rate) {
	    this.payment_rate = payment_rate;
	}

	public int getIncome_tax() {
		return income_tax;
	}

	public void setIncome_tax(int income_tax) {
		this.income_tax = income_tax;
	}

	public int getLocal_income_tax() {
		return local_income_tax;
	}

	public void setLocal_income_tax(int local_income_tax) {
		this.local_income_tax = local_income_tax;
	}

	public int getNet_pay() {
		return net_pay;
	}

	public void setNet_pay(int net_pay) {
		this.net_pay = net_pay;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	// 객체 정보를 문자열로 반환 / オブジェクト情報を文字列として返す
	@Override
	public String toString() {
		return "DailyWork [daily_work_id=" + daily_work_id + ", work_date=" + work_date + ", project_name="
				+ project_name + ", daily_wage=" + daily_wage + ", payment_rate=" + payment_rate + ", income_tax="
				+ income_tax + ", local_income_tax=" + local_income_tax + ", net_pay=" + net_pay + ", emp_id=" + emp_id
				+ "]";
	}

}
