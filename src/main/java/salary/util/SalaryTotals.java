package salary.util;

import java.util.List;

import salary.model.SalaryDetail;

// 급여상세 목록에서 지급/공제 합계 계산
public final class SalaryTotals {

	private SalaryTotals() {
	}

	public static long[] fromDetails(List<SalaryDetail> details) {
		long totalPay = 0;
		long totalDeduct = 0;
		if (details != null) {
			for (SalaryDetail detail : details) {
				long amount = detail.getAmount() != null ? detail.getAmount() : 0L;
				if ("PAY".equals(detail.getItemType())) {
					totalPay += amount;
				} else {
					totalDeduct += amount;
				}
			}
		}
		return new long[] { totalPay, totalDeduct, totalPay - totalDeduct };
	}
}
