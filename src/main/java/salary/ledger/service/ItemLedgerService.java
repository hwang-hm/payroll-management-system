package salary.ledger.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jdbc.connection.ConnectionProvider;
import salary.ledger.dao.ItemLedgerDao;
import salary.ledger.dao.ItemLedgerDao.ItemAmount;
import salary.ledger.viewdto.ItemLedgerData;
import salary.ledger.viewdto.ItemLedgerMonth;
import salary.ledger.viewdto.ItemLedgerRow;
import salary.util.PeriodRangeParams;

public class ItemLedgerService {

	private static final String DEFAULT_ITEM_NAME = "基本給";

	private final ItemLedgerDao itemLedgerDao = new ItemLedgerDao();

	public ItemLedgerData getItemLedgerData(PeriodRangeParams period, String itemName) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int[] normalized = normalizePeriod(period.getStartYear(), period.getStartMonth(), period.getEndYear(),
					period.getEndMonth());
			boolean capped = capToTwelveMonths(normalized);
			String selectedItem = resolveItemName(itemName);

			ItemLedgerData data = new ItemLedgerData();
			data.setStartYear(normalized[0]);
			data.setStartMonth(normalized[1]);
			data.setEndYear(normalized[2]);
			data.setEndMonth(normalized[3]);
			data.setItemName(selectedItem);
			data.setPeriodCapped(capped);
			data.setYearOptions(buildYearOptions(normalized[0], normalized[2]));

			List<ItemLedgerMonth> months = buildMonths(normalized[0], normalized[1], normalized[2], normalized[3]);
			data.setMonths(months);

			int startYm = normalized[0] * 100 + normalized[1];
			int endYm = normalized[2] * 100 + normalized[3];
			List<ItemAmount> amounts = itemLedgerDao.selectItemAmountsByPeriod(conn, selectedItem, startYm, endYm);
			List<ItemLedgerRow> rows = toRows(amounts, months);
			data.setRows(rows);
			data.setSummary(toSummary(rows, months.size()));
			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private String resolveItemName(String itemName) {
		if (itemName != null && !itemName.trim().isEmpty()) {
			return itemName.trim();
		}
		return DEFAULT_ITEM_NAME;
	}

	private int[] normalizePeriod(int startYear, int startMonth, int endYear, int endMonth) {
		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR);
		int currentMonth = now.get(Calendar.MONTH) + 1;

		if (startYear <= 0) {
			startYear = currentYear;
		}
		if (startMonth <= 0) {
			startMonth = 1;
		}
		if (endYear <= 0) {
			endYear = currentYear;
		}
		if (endMonth <= 0) {
			endMonth = currentMonth;
		}
		if (startMonth > 12) {
			startMonth = 12;
		}
		if (endMonth > 12) {
			endMonth = 12;
		}

		int startYm = startYear * 100 + startMonth;
		int endYm = endYear * 100 + endMonth;
		if (startYm > endYm) {
			return new int[] { endYear, endMonth, startYear, startMonth };
		}
		return new int[] { startYear, startMonth, endYear, endMonth };
	}

	// 12개월 초과면 시작월부터 12개월만 남김
	private boolean capToTwelveMonths(int[] period) {
		int startIndex = period[0] * 12 + (period[1] - 1);
		int endIndex = period[2] * 12 + (period[3] - 1);
		if (endIndex - startIndex + 1 <= 12) {
			return false;
		}
		int cappedEndIndex = startIndex + 11;
		period[2] = cappedEndIndex / 12;
		period[3] = (cappedEndIndex % 12) + 1;
		return true;
	}

	private List<ItemLedgerMonth> buildMonths(int startYear, int startMonth, int endYear, int endMonth) {
		List<ItemLedgerMonth> months = new ArrayList<>();
		int year = startYear;
		int month = startMonth;
		while (year * 100 + month <= endYear * 100 + endMonth) {
			String label = String.format("%04d-%02d", year, month);
			months.add(new ItemLedgerMonth(year, month, label));
			month++;
			if (month > 12) {
				month = 1;
				year++;
			}
		}
		return months;
	}

	private List<ItemLedgerRow> toRows(List<ItemAmount> amounts, List<ItemLedgerMonth> months) {
		Map<Integer, ItemLedgerRow> rowsByEmp = new LinkedHashMap<>();
		Map<Integer, Map<Integer, Long>> amountsByEmpYm = new LinkedHashMap<>();

		for (ItemAmount amount : amounts) {
			ItemLedgerRow row = rowsByEmp.get(amount.empId);
			if (row == null) {
				row = new ItemLedgerRow();
				row.setEmpId(amount.empId);
				row.setEmploymentType(amount.employmentType);
				row.setNameKor(amount.nameKor);
				row.setDeptName(amount.deptName);
				row.setPositionName(amount.positionName);
				rowsByEmp.put(amount.empId, row);
			}
			int ym = amount.payYear * 100 + amount.payMonth;
			amountsByEmpYm.computeIfAbsent(amount.empId, key -> new LinkedHashMap<>())
					.merge(ym, amount.amount, Long::sum);
		}

		for (ItemLedgerRow row : rowsByEmp.values()) {
			Map<Integer, Long> byYm = amountsByEmpYm.get(row.getEmpId());
			List<Long> monthAmounts = new ArrayList<>();
			long total = 0;
			for (ItemLedgerMonth month : months) {
				long value = 0;
				if (byYm != null) {
					Long found = byYm.get(month.getYear() * 100 + month.getMonth());
					if (found != null) {
						value = found;
					}
				}
				monthAmounts.add(value);
				total += value;
			}
			row.setMonthAmounts(monthAmounts);
			row.setTotal(total);
		}
		return new ArrayList<>(rowsByEmp.values());
	}

	private ItemLedgerRow toSummary(List<ItemLedgerRow> rows, int monthCount) {
		ItemLedgerRow summary = new ItemLedgerRow();
		List<Long> monthTotals = new ArrayList<>();
		for (int i = 0; i < monthCount; i++) {
			monthTotals.add(0L);
		}
		long grandTotal = 0;
		for (ItemLedgerRow row : rows) {
			List<Long> amounts = row.getMonthAmounts();
			for (int i = 0; i < monthCount && i < amounts.size(); i++) {
				monthTotals.set(i, monthTotals.get(i) + amounts.get(i));
			}
			grandTotal += row.getTotal();
		}
		summary.setMonthAmounts(monthTotals);
		summary.setTotal(grandTotal);
		return summary;
	}

	private List<Integer> buildYearOptions(int startYear, int endYear) {
		List<Integer> years = new ArrayList<>();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int max = Math.max(currentYear, Math.max(startYear, endYear));
		for (int y = max; y >= max - 4; y--) {
			years.add(y);
		}
		if (!years.contains(startYear)) {
			years.add(startYear);
		}
		if (!years.contains(endYear)) {
			years.add(endYear);
		}
		years.sort((a, b) -> Integer.compare(b, a));
		return years;
	}
}
