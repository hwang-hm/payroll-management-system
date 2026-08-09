package attendance.model;

import java.sql.Date;

/**
 * 휴가항목 정보를 저장하는 모델 클래스 
 * 休暇項目情報を保存するモデルクラス
 */

public class VacationType {

	// 휴가항목 번호 / 休暇項目番号
	private int vacationTypeId;

	// 휴가항목 이름 / 休暇項目名
	private String vacationName;

	// 적용 시작일 / 適用開始日
	private Date applyStartDate;

	// 적용 종료일 / 適用終了日
	private Date applyEndDate;

	// 휴가일수 / 休暇日数
	private String vacationDays;

	// 사용 여부 / 使用可否
	private String useYn;

	
	// 기본 생성자 / デフォルトコンストラクタ
	public VacationType() {

	}

	
	// 전체 필드 생성자 / 全フィールドコンストラクタ
	public VacationType(int vacationTypeId, String vacationName, Date applyStartDate,
            Date applyEndDate, String vacationDays, String useYn) {
        this.vacationTypeId = vacationTypeId;
        this.vacationName = vacationName;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.vacationDays = vacationDays;
        this.useYn = useYn;
    }

	
	// getter 및 setter / getterおよびsetter
	public int getVacationTypeId() {
        return vacationTypeId;
    }

    public void setVacationTypeId(int vacationTypeId) {
        this.vacationTypeId = vacationTypeId;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public Date getApplyStartDate() {
        return applyStartDate;
    }

    public void setApplyStartDate(Date applyStartDate) {
        this.applyStartDate = applyStartDate;
    }

    public Date getApplyEndDate() {
        return applyEndDate;
    }

    public void setApplyEndDate(Date applyEndDate) {
        this.applyEndDate = applyEndDate;
    }

    public String getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(String vacationDays) {
        this.vacationDays = vacationDays;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

	
	// 객체 정보를 문자열로 반환 / オブジェクト情報を文字列として返す
	@Override
	 public String toString() {
        return "VacationType [vacationTypeId=" + vacationTypeId
                + ", vacationName=" + vacationName
                + ", applyStartDate=" + applyStartDate
                + ", applyEndDate=" + applyEndDate
                + ", vacationDays=" + vacationDays
                + ", useYn=" + useYn + "]";
    }

}
