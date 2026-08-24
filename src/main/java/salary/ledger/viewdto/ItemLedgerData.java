package salary.ledger.viewdto;

import java.util.ArrayList;
import java.util.List;

// 항목별 대장 화면 전체 데이터
// 項目別台帳画面の全体データ
public class ItemLedgerData {

	private int startYear;
	private int startMonth;
	private int endYear;
	private int endMonth;
	private String itemName;
	private boolean periodCapped;
	private List<Integer> yearOptions = new ArrayList<>();
	private List<String> itemOptions = new ArrayList<>();
	private List<ItemLedgerMonth> months = new ArrayList<>();
	private List<ItemLedgerRow> rows = new ArrayList<>();
	private ItemLedgerRow summary;

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public boolean isPeriodCapped() {
		return periodCapped;
	}

	public void setPeriodCapped(boolean periodCapped) {
		this.periodCapped = periodCapped;
	}

	public List<Integer> getYearOptions() {
		return yearOptions;
	}

	public void setYearOptions(List<Integer> yearOptions) {
		this.yearOptions = yearOptions == null ? new ArrayList<>() : yearOptions;
	}

	public List<String> getItemOptions() {
		return itemOptions;
	}

	public void setItemOptions(List<String> itemOptions) {
		this.itemOptions = itemOptions == null ? new ArrayList<>() : itemOptions;
	}

	public List<ItemLedgerMonth> getMonths() {
		return months;
	}

	public void setMonths(List<ItemLedgerMonth> months) {
		this.months = months == null ? new ArrayList<>() : months;
	}

	public List<ItemLedgerRow> getRows() {
		return rows;
	}

	public void setRows(List<ItemLedgerRow> rows) {
		this.rows = rows == null ? new ArrayList<>() : rows;
	}

	public ItemLedgerRow getSummary() {
		return summary;
	}

	public void setSummary(ItemLedgerRow summary) {
		this.summary = summary;
	}
}
