package salary.ledger.viewdto;

// 항목별 대장 월 컬럼
// 項目別台帳の月カラム
public class ItemLedgerMonth {

	private int year;
	private int month;
	private String label;

	public ItemLedgerMonth() {
	}

	public ItemLedgerMonth(int year, int month, String label) {
		this.year = year;
		this.month = month;
		this.label = label;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
