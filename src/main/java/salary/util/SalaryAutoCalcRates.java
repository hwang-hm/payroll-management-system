package salary.util;

import java.util.LinkedHashMap;
import java.util.Map;

// 급여 공제 자동계산용 요율. 실무 세법 아님
// 給与控除自動計算用レート。実務税法ではない
public final class SalaryAutoCalcRates {

	public static final String ITEM_BASIC_PAY = "기본급";
	public static final String ITEM_NATIONAL_PENSION = "국민연금";
	public static final String ITEM_HEALTH_INSURANCE = "건강보험";
	public static final String ITEM_LONGTERM_CARE = "장기요양보험";
	public static final String ITEM_EMPLOYMENT_INSURANCE = "고용보험";
	public static final String ITEM_INCOME_TAX = "소득세";
	public static final String ITEM_LOCAL_INCOME_TAX = "지방소득세";

	// 기준액(기본급) 대비 요율
	// 基準額(基本給)に対するレート
	public static final double RATE_NATIONAL_PENSION = 0.045;
	public static final double RATE_HEALTH_INSURANCE = 0.03545;
	public static final double RATE_EMPLOYMENT_INSURANCE = 0.009;
	public static final double RATE_INCOME_TAX = 0.03;
	// 건강보험액 대비
	// 健康保険料額に対する
	public static final double RATE_LONGTERM_CARE_OF_HEALTH = 0.1295;
	// 소득세액 대비
	// 所得税額に対する
	public static final double RATE_LOCAL_INCOME_TAX_OF_INCOME = 0.1;

	private SalaryAutoCalcRates() {
	}

	// 전각공백·NBSP 제거 + 유니코드 정규화 후 비교용 문자열
	// 全角空白・NBSP除去 + Unicode正規化後の比較用文字列
	public static String normalizeItemName(String itemName) {
		if (itemName == null) {
			return null;
		}
		String normalized = java.text.Normalizer.normalize(itemName, java.text.Normalizer.Form.NFKC);
		return normalized.replace('\u3000', ' ').replace('\u00A0', ' ').trim();
	}

	// salary_item.item_name 한/일 표기를 내부 키로 통일
	// salary_item.item_name の韓/日表記を内部キーに統一
	public static String canonicalName(String itemName) {
		String name = normalizeItemName(itemName);
		if (name == null || name.isEmpty()) {
			return null;
		}
		if (isOneOf(name, "기본급", "基本給") || name.contains("基本給") || name.equals("기본급")) {
			return ITEM_BASIC_PAY;
		}
		if (isOneOf(name, "국민연금", "国民年金") || name.contains("国民年金") || name.contains("국민연금")) {
			return ITEM_NATIONAL_PENSION;
		}
		if (isOneOf(name, "건강보험", "健康保険") || name.contains("健康保険") || name.contains("건강보험")) {
			return ITEM_HEALTH_INSURANCE;
		}
		if (isOneOf(name, "장기요양보험", "노인장기요양보험", "介護保険", "介者保険")
				|| name.contains("介護") || name.contains("介者") || name.contains("장기요양") || name.contains("요양보험")) {
			return ITEM_LONGTERM_CARE;
		}
		if (isOneOf(name, "고용보험", "雇用保険") || name.contains("雇用保険") || name.contains("고용보험")) {
			return ITEM_EMPLOYMENT_INSURANCE;
		}
		// 지방/주민세를 소득세보다 먼저 판별 (「所得税」를 포함해도 지방이 우선)
		// 地方/住民税を所得税より先に判定
		if (isOneOf(name, "지방소득세", "住民税", "居民税", "居民稅", "地方所得税", "地方税")
				|| name.contains("住民") || name.contains("居民") || name.contains("地方所得") || name.contains("지방소득")) {
			return ITEM_LOCAL_INCOME_TAX;
		}
		if (isOneOf(name, "소득세", "所得税", "所得稅")
				|| ((name.contains("所得") || name.contains("소득")) && (name.contains("税") || name.contains("稅") || name.contains("세")))) {
			return ITEM_INCOME_TAX;
		}
		return name;
	}

	public static boolean isBasicPayItem(String itemName) {
		return ITEM_BASIC_PAY.equals(canonicalName(itemName));
	}

	// 기준액으로 공제 6종 금액 계산. Y/N이 N이면 해당 보험은 0
	// 基準額で控除6種の金額を計算。Y/NがNなら該当保険は0
	public static DeductionAmounts calc(long baseAmount, boolean nationalPensionYn, boolean healthInsuranceYn,
			boolean longtermCareYn, boolean employmentInsuranceYn) {
		long base = Math.max(baseAmount, 0L);

		long nationalPension = nationalPensionYn ? round(base * RATE_NATIONAL_PENSION) : 0L;
		long healthInsurance = healthInsuranceYn ? round(base * RATE_HEALTH_INSURANCE) : 0L;
		long longtermCare = longtermCareYn ? round(healthInsurance * RATE_LONGTERM_CARE_OF_HEALTH) : 0L;
		long employmentInsurance = employmentInsuranceYn ? round(base * RATE_EMPLOYMENT_INSURANCE) : 0L;
		long incomeTax = round(base * RATE_INCOME_TAX);
		long localIncomeTax = round(incomeTax * RATE_LOCAL_INCOME_TAX_OF_INCOME);

		return new DeductionAmounts(nationalPension, healthInsurance, longtermCare, employmentInsurance, incomeTax,
				localIncomeTax);
	}

	// 보험 Y/N 없이 전부 적용
	// 保険 Y/N なしで全部適用
	public static DeductionAmounts calc(long baseAmount) {
		return calc(baseAmount, true, true, true, true);
	}

	public static long round(double value) {
		return Math.round(value);
	}

	// 내부 키(canonical) → 금액. entry detail은 canonicalName 후 조회
	// 内部キー(canonical) → 金額。entry detailは canonicalName 後に照会
	public static Map<String, Long> toAmountMap(DeductionAmounts amounts) {
		Map<String, Long> map = new LinkedHashMap<>();
		map.put(ITEM_NATIONAL_PENSION, amounts.getNationalPension());
		map.put(ITEM_HEALTH_INSURANCE, amounts.getHealthInsurance());
		map.put(ITEM_LONGTERM_CARE, amounts.getLongtermCare());
		map.put(ITEM_EMPLOYMENT_INSURANCE, amounts.getEmploymentInsurance());
		map.put(ITEM_INCOME_TAX, amounts.getIncomeTax());
		map.put(ITEM_LOCAL_INCOME_TAX, amounts.getLocalIncomeTax());
		return map;
	}

	public static boolean isAutoDeductionItem(String itemName) {
		String canonical = canonicalName(itemName);
		return ITEM_NATIONAL_PENSION.equals(canonical) || ITEM_HEALTH_INSURANCE.equals(canonical)
				|| ITEM_LONGTERM_CARE.equals(canonical) || ITEM_EMPLOYMENT_INSURANCE.equals(canonical)
				|| ITEM_INCOME_TAX.equals(canonical) || ITEM_LOCAL_INCOME_TAX.equals(canonical);
	}

	private static boolean isOneOf(String name, String... aliases) {
		for (String alias : aliases) {
			if (alias.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public static final class DeductionAmounts {
		private final long nationalPension;
		private final long healthInsurance;
		private final long longtermCare;
		private final long employmentInsurance;
		private final long incomeTax;
		private final long localIncomeTax;

		public DeductionAmounts(long nationalPension, long healthInsurance, long longtermCare,
				long employmentInsurance, long incomeTax, long localIncomeTax) {
			this.nationalPension = nationalPension;
			this.healthInsurance = healthInsurance;
			this.longtermCare = longtermCare;
			this.employmentInsurance = employmentInsurance;
			this.incomeTax = incomeTax;
			this.localIncomeTax = localIncomeTax;
		}

		public long getNationalPension() {
			return nationalPension;
		}

		public long getHealthInsurance() {
			return healthInsurance;
		}

		public long getLongtermCare() {
			return longtermCare;
		}

		public long getEmploymentInsurance() {
			return employmentInsurance;
		}

		public long getIncomeTax() {
			return incomeTax;
		}

		public long getLocalIncomeTax() {
			return localIncomeTax;
		}

		public long byItemName(String itemName) {
			String canonical = canonicalName(itemName);
			if (canonical == null) {
				return 0L;
			}
			switch (canonical) {
			case ITEM_NATIONAL_PENSION:
				return nationalPension;
			case ITEM_HEALTH_INSURANCE:
				return healthInsurance;
			case ITEM_LONGTERM_CARE:
				return longtermCare;
			case ITEM_EMPLOYMENT_INSURANCE:
				return employmentInsurance;
			case ITEM_INCOME_TAX:
				return incomeTax;
			case ITEM_LOCAL_INCOME_TAX:
				return localIncomeTax;
			default:
				return 0L;
			}
		}
	}
}
