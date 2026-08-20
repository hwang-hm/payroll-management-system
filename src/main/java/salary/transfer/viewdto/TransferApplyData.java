package salary.transfer.viewdto;

import java.util.ArrayList;
import java.util.List;

import salary.model.TransferForm;
import salary.transfer.TransferColumn;

// 이체신청 화면에 뿌릴 전체 데이터
public class TransferApplyData {

	private int payYear;
	private int payMonth;
	private int paySeq;
	private Integer selectedFormId;
	private String selectedFormName;
	private List<Integer> yearOptions = new ArrayList<>();
	private List<TransferForm> forms = new ArrayList<>();
	private List<TransferColumn> visibleColumns = new ArrayList<>();
	private List<TransferColumn> allColumns = new ArrayList<>();
	private List<TransferApplyRow> rows = new ArrayList<>();
	private long sumTotalPay;
	private long sumTotalDeduct;
	private long sumNetPay;
	private String message;

	public int getPayYear() {
		return payYear;
	}

	public void setPayYear(int payYear) {
		this.payYear = payYear;
	}

	public int getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(int payMonth) {
		this.payMonth = payMonth;
	}

	public int getPaySeq() {
		return paySeq;
	}

	public void setPaySeq(int paySeq) {
		this.paySeq = paySeq;
	}

	public Integer getSelectedFormId() {
		return selectedFormId;
	}

	public void setSelectedFormId(Integer selectedFormId) {
		this.selectedFormId = selectedFormId;
	}

	public String getSelectedFormName() {
		return selectedFormName;
	}

	public void setSelectedFormName(String selectedFormName) {
		this.selectedFormName = selectedFormName;
	}

	public List<Integer> getYearOptions() {
		return yearOptions;
	}

	public void setYearOptions(List<Integer> yearOptions) {
		this.yearOptions = yearOptions;
	}

	public List<TransferForm> getForms() {
		return forms;
	}

	public void setForms(List<TransferForm> forms) {
		this.forms = forms;
	}

	public List<TransferColumn> getVisibleColumns() {
		return visibleColumns;
	}

	public void setVisibleColumns(List<TransferColumn> visibleColumns) {
		this.visibleColumns = visibleColumns;
	}

	public List<TransferColumn> getAllColumns() {
		return allColumns;
	}

	public void setAllColumns(List<TransferColumn> allColumns) {
		this.allColumns = allColumns;
	}

	public List<TransferApplyRow> getRows() {
		return rows;
	}

	public void setRows(List<TransferApplyRow> rows) {
		this.rows = rows;
	}

	public long getSumTotalPay() {
		return sumTotalPay;
	}

	public void setSumTotalPay(long sumTotalPay) {
		this.sumTotalPay = sumTotalPay;
	}

	public long getSumTotalDeduct() {
		return sumTotalDeduct;
	}

	public void setSumTotalDeduct(long sumTotalDeduct) {
		this.sumTotalDeduct = sumTotalDeduct;
	}

	public long getSumNetPay() {
		return sumNetPay;
	}

	public void setSumNetPay(long sumNetPay) {
		this.sumNetPay = sumNetPay;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
