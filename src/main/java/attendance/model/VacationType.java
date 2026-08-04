package attendance.model;

import java.sql.Date;

/**
 * 휴가항목 정보를 저장하는 모델 클래스 
 * 休暇項目情報を保存するモデルクラス
 */

public class VacationType {

	// 휴가항목 번호 / 休暇項目番号
	private int vacation_type_id;

	// 휴가항목 이름 / 休暇項目名
	private String vacation_name;

	// 적용 시작일 / 適用開始日
	private Date apply_start_date;

	// 적용 종료일 / 適用終了日
	private Date apply_end_date;

	// 휴가일수 / 休暇日数
	private String vacation_days;

	// 사용 여부 / 使用可否
	private String use_yn;

	
	// 기본 생성자 / デフォルトコンストラクタ
	public VacationType() {

	}

	
	// 전체 필드 생성자 / 全フィールドコンストラクタ
	public VacationType(int vacation_type_id, String vacation_name, Date apply_start_date, Date apply_end_date,
			String vacation_days, String use_yn) {
		this.vacation_type_id = vacation_type_id;
		this.vacation_name = vacation_name;
		this.apply_start_date = apply_start_date;
		this.apply_end_date = apply_end_date;
		this.vacation_days = vacation_days;
		this.use_yn = use_yn;
	}

	
	// getter 및 setter / getterおよびsetter
	public int getVacation_type_id() {
		return vacation_type_id;
	}

	public void setVacation_type_id(int vacation_type_id) {
		this.vacation_type_id = vacation_type_id;
	}

	public String getVacation_name() {
		return vacation_name;
	}

	public void setVacation_name(String vacation_name) {
		this.vacation_name = vacation_name;
	}

	public Date getApply_start_date() {
		return apply_start_date;
	}

	public void setApply_start_date(Date apply_start_date) {
		this.apply_start_date = apply_start_date;
	}

	public Date getApply_end_date() {
		return apply_end_date;
	}

	public void setApply_end_date(Date apply_end_date) {
		this.apply_end_date = apply_end_date;
	}

	public String getVacation_days() {
		return vacation_days;
	}

	public void setVacation_days(String vacation_days) {
		this.vacation_days = vacation_days;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	
	// 객체 정보를 문자열로 반환 / オブジェクト情報を文字列として返す
	@Override
	public String toString() {
		return "VacationType [vacation_type_id=" + vacation_type_id + ", vacation_name=" + vacation_name
				+ ", apply_start_date=" + apply_start_date + ", apply_end_date=" + apply_end_date + ", vacation_days="
				+ vacation_days + ", use_yn=" + use_yn + "]";
	}

}
