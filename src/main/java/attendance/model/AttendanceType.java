package attendance.model;

/** 
 * 근태항목 정보를 저장하는 모델 클래스
 * 勤怠項目情報を保存するモデルクラス
 */

public class AttendanceType {

	// 근태항목 번호 / 勤怠項目番号
	private int attendance_type_id;

	// 근태항목 이름 / 勤怠項目名
	private String attendance_type;

	// 계산 단위 / 計算単位
	private String unit;

	// 근태 그룹명 / 勤怠グループ名
	private String group_name;

	// 사용 여부 / 使用可否
	private String use_yn;

	// 연결된 휴가항목 번호 / 関連する休暇項目番号
	private int vacation_type_id;

	// 기본 생성자 / デフォルトコンストラクタ
	public AttendanceType() {

	}

	
	// 전체 필드 생성자 / 全フィールドコンストラクタ
	public AttendanceType(int attendance_type_id, String attendance_type, String unit, String group_name, String use_yn,
			int vacation_type_id) {
		this.attendance_type_id = attendance_type_id;
		this.attendance_type = attendance_type;
		this.unit = unit;
		this.group_name = group_name;
		this.use_yn = use_yn;
		this.vacation_type_id = vacation_type_id;
	}

	
	// getter 및 setter / getterおよびsetter
	public int getAttendance_type_id() {
		return attendance_type_id;
	}

	public void setAttendance_type_id(int attendance_type_id) {
		this.attendance_type_id = attendance_type_id;
	}

	public String getAttendance_type() {
		return attendance_type;
	}

	public void setAttendance_type(String attendance_type) {
		this.attendance_type = attendance_type;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public int getVacation_type_id() {
		return vacation_type_id;
	}

	public void setVacation_type_id(int vacation_type_id) {
		this.vacation_type_id = vacation_type_id;
	}

	
	// 객체 정보를 문자열로 반환 / オブジェクト情報を文字列として返す
	@Override
	public String toString() {
		return "AttendanceType [attendance_type_id=" + attendance_type_id + ", attendance_type=" + attendance_type
				+ ", unit=" + unit + ", group_name=" + group_name + ", use_yn=" + use_yn + ", vacation_type_id="
				+ vacation_type_id + "]";
	}

}
