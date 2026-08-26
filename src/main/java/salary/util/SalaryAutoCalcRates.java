package salary.util;

import java.util.LinkedHashMap;
import java.util.Map;

// 급여 공제 자동계산용 요율. 실무 세법 아님
// 給与控除自動計算用レート。実務税法ではない
public final class SalaryAutoCalcRates {

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

	// 확정된 salary_item.item_name을 정규화해서 반환
	// 確定した salary_item.item_name を正規化して返却
	public static String canonicalName(String itemName) {
		String name = normalizeItemName(itemName);
		if (name == null || name.isEmpty()) {
			return null;
		}
		return name;
	}

	public static boolean isBasicPayItem(String itemName) {
		return "基本給".equals(canonicalName(itemName));
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
		map.put("国民年金", amounts.getNationalPension());
		map.put("健康保険", amounts.getHealthInsurance());
		map.put("介護保険", amounts.getLongtermCare());
		map.put("雇用保険", amounts.getEmploymentInsurance());
		map.put("所得税", amounts.getIncomeTax());
		map.put("住民税", amounts.getLocalIncomeTax());
		return map;
	}

	public static boolean isAutoDeductionItem(String itemName) {
		String normalized = canonicalName(itemName);
		return "国民年金".equals(normalized) || "健康保険".equals(normalized)
				|| "介護保険".equals(normalized) || "雇用保険".equals(normalized)
				|| "所得税".equals(normalized) || "住民税".equals(normalized);
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
			String normalized = canonicalName(itemName);
			if (normalized == null) {
				return 0L;
			}
			if ("国民年金".equals(normalized)) {
				return nationalPension;
			}
			if ("健康保険".equals(normalized)) {
				return healthInsurance;
			}
			if ("介護保険".equals(normalized)) {
				return longtermCare;
			}
			if ("雇用保険".equals(normalized)) {
				return employmentInsurance;
			}
			if ("所得税".equals(normalized)) {
				return incomeTax;
			}
			if ("住民税".equals(normalized)) {
				return localIncomeTax;
			}
			return 0L;
		}
	}
}
