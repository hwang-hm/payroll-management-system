package salary.util;

import java.util.LinkedHashMap;
import java.util.Map;

// 급여 공제 자동계산용 가라 요율. 실무 세법 아님, 퍼센트 눈속임
// 給与控除自動計算用の仮レート。実務税法ではない、パーセントの仮値
public final class SalaryAutoCalcRates {

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

	// 항목명 → 금액. entry detail 매칭용
	// 項目名 → 金額。entry detail 突合用
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
		return ITEM_NATIONAL_PENSION.equals(itemName) || ITEM_HEALTH_INSURANCE.equals(itemName)
				|| ITEM_LONGTERM_CARE.equals(itemName) || ITEM_EMPLOYMENT_INSURANCE.equals(itemName)
				|| ITEM_INCOME_TAX.equals(itemName) || ITEM_LOCAL_INCOME_TAX.equals(itemName);
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
			if (itemName == null) {
				return 0L;
			}
			switch (itemName) {
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
