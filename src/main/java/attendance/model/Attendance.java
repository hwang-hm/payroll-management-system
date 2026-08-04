package attendance.model;

import java.sql.Date;

/**
 * 근태기록 정보를 저장하는 모델 클래스
 * 勤怠記録情報を保存するモデルクラス
 */

public class Attendance {

	// 근태기록 번호 / 勤怠記録番号
	private int attendance_id;
	
	// 입력일자 / 入力日
	private Date input_date;
	
	// 근태 시작일 / 勤怠開始日
	private Date attendance_start_date;
	
	// 근태 종료일 / 勤怠終了日
	private Date attendance_end_date;
	
	// 근태일수 / 勤怠日数
	private double attendance_days;
	
	// 수당 금액 / 手当金額
	private int allowance_amount;
	
	// 적요 / 摘要
	private String remarks;
	
	// 사원번호 / 社員番号
	private int emp_id;
	
	// 근태항목 번호 / 勤怠項目番号
	private int attendance_type_id;

	// 기본 생성자 / デフォルトコンストラクタ

	public Attendance() {

	}

	
	// 전체 필드 생성자 / 全フィールドコンストラクタ
	public Attendance(int attendance_id, Date input_date, Date attendance_start_date, Date attendance_end_date,
			double attendance_days, int allowance_amount, String remarks, int emp_id, int attendance_type_id) {
		this.attendance_id = attendance_id;
		this.input_date = input_date;
		this.attendance_start_date = attendance_start_date;
		this.attendance_end_date = attendance_end_date;
		this.attendance_days = attendance_days;
		this.allowance_amount = allowance_amount;
		this.remarks = remarks;
		this.emp_id = emp_id;
		this.attendance_type_id = attendance_type_id;
	}

	
	// getter 및 setter / getterおよびsetter
	public int getAttendance_id() {
		return attendance_id;
	}

	public void setAttendance_id(int attendance_id) {
		this.attendance_id = attendance_id;
	}

	public Date getInput_date() {
		return input_date;
	}

	public void setInput_date(Date input_date) {
		this.input_date = input_date;
	}

	public Date getAttendance_start_date() {
		return attendance_start_date;
	}

	public void setAttendance_start_date(Date attendance_start_date) {
		this.attendance_start_date = attendance_start_date;
	}

	public Date getAttendance_end_date() {
		return attendance_end_date;
	}

	public void setAttendance_end_date(Date attendance_end_date) {
		this.attendance_end_date = attendance_end_date;
	}

	public double getAttendance_days() {
		return attendance_days;
	}

	public void setAttendance_days(double attendance_days) {
		this.attendance_days = attendance_days;
	}

	public int getAllowance_amount() {
		return allowance_amount;
	}

	public void setAllowance_amount(int allowance_amount) {
		this.allowance_amount = allowance_amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public int getAttendance_type_id() {
		return attendance_type_id;
	}

	public void setAttendance_type_id(int attendance_type_id) {
		this.attendance_type_id = attendance_type_id;
	}
	
	
	// 객체 정보를 문자열로 반환 / オブジェクト情報を文字列として返す
	@Override
	public String toString() {
		return "Attendance [attendance_id=" + attendance_id + ", input_date=" + input_date + ", attendance_start_date="
				+ attendance_start_date + ", attendance_end_date=" + attendance_end_date + ", attendance_days="
				+ attendance_days + ", allowance_amount=" + allowance_amount + ", remarks=" + remarks + ", emp_id="
				+ emp_id + ", attendance_type_id=" + attendance_type_id + "]";
	}

}
