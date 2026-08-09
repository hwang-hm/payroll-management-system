package attendance.model;

/** 
 * 근태항목 정보를 저장하는 모델 클래스
 * 勤怠項目情報を保存するモデルクラス
 */

public class AttendanceType {

	// 근태항목 번호 / 勤怠項目番号
	private int attendanceTypeId;

	// 근태항목 이름 / 勤怠項目名
	private String attendanceType;

	// 계산 단위 / 計算単位
	private String unit;

	// 근태 그룹명 / 勤怠グループ名
	private String groupName;

	// 사용 여부 / 使用可否
	private String useYn;

	// 연결된 휴가항목 번호 / 関連する休暇項目番号
	private int vacationTypeId;

	// 기본 생성자 / デフォルトコンストラクタ
	public AttendanceType() {

	}

	
	// 전체 필드 생성자 / 全フィールドコンストラクタ
	public AttendanceType(int attendanceTypeId, String attendanceType, String unit,
            String groupName, String useYn, int vacationTypeId) {
        this.attendanceTypeId = attendanceTypeId;
        this.attendanceType = attendanceType;
        this.unit = unit;
        this.groupName = groupName;
        this.useYn = useYn;
        this.vacationTypeId = vacationTypeId;
    }

	
	// getter 및 setter / getterおよびsetter
	public int getAttendanceTypeId() {
        return attendanceTypeId;
    }

    public void setAttendanceTypeId(int attendanceTypeId) {
        this.attendanceTypeId = attendanceTypeId;
    }

    public String getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public int getVacationTypeId() {
        return vacationTypeId;
    }

    public void setVacationTypeId(int vacationTypeId) {
        this.vacationTypeId = vacationTypeId;
    }

	
	// 객체 정보를 문자열로 반환 / オブジェクト情報を文字列として返す
    @Override
    public String toString() {
        return "AttendanceType [attendanceTypeId=" + attendanceTypeId
                + ", attendanceType=" + attendanceType
                + ", unit=" + unit
                + ", groupName=" + groupName
                + ", useYn=" + useYn
                + ", vacationTypeId=" + vacationTypeId + "]";
    }

}
