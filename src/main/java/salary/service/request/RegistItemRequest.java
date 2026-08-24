package salary.service.request;

import java.util.Map;

/**
 * 급여 항목 등록 및 수정 요청 파라미터 DTO 및 유효성 검증 클래스
 * 給与項目登録・更新リクエストパラメータDTOおよびバリデーションクラス
 */
public class RegistItemRequest {

	private Integer salaryItemId;    // 급여 항목 ID (수정 시 사용, 신규 등록 시 null) / 給与項目ID (更新時使用、新規登録時null)
	private Integer attendanceTypeId; // 연동 근태 유형 ID / 連動勤怠タイプID
	private String itemType;         // 항목 유형 (PAY: 지급, DEDUCT: 공제) / 項目タイプ (PAY: 支給, DEDUCT: 控除)
	private String itemName;         // 항목명 / 項目名
	private String taxYn;            // 과세 여부 (Y: 과세, N: 비과세) / 課税有無 (Y: 課税, N: 非課税)
	private String taxFreeName;      // 비과세 감면명 / 非課税減免名
	private Long taxFreeLimit;       // 비과세 한도액 / 非課税限度額
	private String calcMethod;       // 계산 방식 / 計算方式
	private String roundUnit;        // 절사/반올림 단위 / 端数処理単位
	private Long defaultAmount;      // 기본 금액 (일괄 지급액) / 基本金額 (一括支給額)
	private String remark;           // 비고 / 備考
	private String useYn;            // 사용 여부 (Y: 사용, N: 미사용) / 使用有無 (Y: 使用, N: 未使用)

	// 기본 생성자
	// 既定のコンストラクタ
	public RegistItemRequest() {
	}

	// 필드 초기화 생성자
	// フィールド初期化コンストラクタ
	public RegistItemRequest(Integer salaryItemId, Integer attendanceTypeId, String itemType, String itemName,
			String taxYn, String taxFreeName, Long taxFreeLimit, String calcMethod, String roundUnit,
			Long defaultAmount, String remark, String useYn) {
		super();
		this.salaryItemId = salaryItemId;
		this.attendanceTypeId = attendanceTypeId;
		this.itemType = itemType;
		this.itemName = itemName;
		this.taxYn = taxYn;
		this.taxFreeName = taxFreeName;
		this.taxFreeLimit = taxFreeLimit;
		this.calcMethod = calcMethod;
		this.roundUnit = roundUnit;
		this.defaultAmount = defaultAmount;
		this.remark = remark;
		this.useYn = useYn;
	}

	// Getter 및 Setter 메서드
	// GetterおよびSetterメソッド
	public Integer getSalaryItemId() {
		return salaryItemId;
	}

	public void setSalaryItemId(Integer salaryItemId) {
		this.salaryItemId = salaryItemId;
	}

	public Integer getAttendanceTypeId() {
		return attendanceTypeId;
	}

	public void setAttendanceTypeId(Integer attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getTaxYn() {
		return taxYn;
	}

	public void setTaxYn(String taxYn) {
		this.taxYn = taxYn;
	}

	public String getTaxFreeName() {
		return taxFreeName;
	}

	public void setTaxFreeName(String taxFreeName) {
		this.taxFreeName = taxFreeName;
	}

	public Long getTaxFreeLimit() {
		return taxFreeLimit;
	}

	public void setTaxFreeLimit(Long taxFreeLimit) {
		this.taxFreeLimit = taxFreeLimit;
	}

	public String getCalcMethod() {
		return calcMethod;
	}

	public void setCalcMethod(String calcMethod) {
		this.calcMethod = calcMethod;
	}

	public String getRoundUnit() {
		return roundUnit;
	}

	public void setRoundUnit(String roundUnit) {
		this.roundUnit = roundUnit;
	}

	public Long getDefaultAmount() {
		return defaultAmount;
	}

	public void setDefaultAmount(Long defaultAmount) {
		this.defaultAmount = defaultAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * 요청 파라미터 필수 입력값 유효성 검증
	 * リクエストパラメータの必須入力値バリデーション
	 */
	public void validate(Map<String, Boolean> errors) {
		// 1. 항목명 검증 (필수)
		// 1. 項目名検証（必須）
		checkEmpty(errors, itemName, "itemName");

		// 2. 항목 구분 검증 (PAY: 지급, DEDUCT: 공제 중 하나여야 함)
		// 2. 項目区分検証（PAY: 支給, DEDUCT: 控除のいずれかであること）
		checkEmpty(errors, itemType, "itemType");

		// 3. 과세 여부 검증 (Y: 과세, N: 비과세)
		// 3. 課税可否の検証 (Y: 課税, N: 非課税)
		// 3. 課税有無検証（Y: 課税, N: 非課税）
		checkEmpty(errors, taxYn, "taxYn");

		// 4. 절사 단위 검증 (필수)
		// 4. 端数処理単位検証（必須）
		checkEmpty(errors, roundUnit, "roundUnit");

		// 5. 사용 여부 검증 (Y 또는 N)
		// 5. 使用有無検証（YまたはN）
		checkEmpty(errors, useYn, "useYn");
	}

	/**
	 * 문자열 null 및 공백 유효성 검증 헬퍼 메서드
	 * null 또는 빈값일 경우 errors 맵에 필드명을 key로, true를 value로 저장
	 * 
	 * 文字列のnullおよび空白バリデーションヘルパーメソッド
	 * nullまたは空文字の場合、errorsマップにフィールド名をkeyとしてtrueを格納
	 */
	private void checkEmpty(Map<String, Boolean> errors, String value, String fieldName) {
		if (value == null || value.trim().isEmpty()) {
			errors.put(fieldName, Boolean.TRUE);
		}
	}
}