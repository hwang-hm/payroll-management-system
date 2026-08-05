package salary.model;

import java.util.Date;

public class TransferHistory {

	private Long transferId;
	private Long salaryId;
	private Date transferDate;
	private String withdrawBank;
	private String withdrawAccount;
	private String depositBank;
	private String depositAccount;
	private String depositorName;
	private Long transferAmount;
	private String depositMemo;
	private String transferResult;

	public TransferHistory() {
	}

	public TransferHistory(Long transferId, Long salaryId, Date transferDate, String withdrawBank,
			String withdrawAccount, String depositBank, String depositAccount, String depositorName,
			Long transferAmount, String depositMemo, String transferResult) {
		this.transferId = transferId;
		this.salaryId = salaryId;
		this.transferDate = transferDate;
		this.withdrawBank = withdrawBank;
		this.withdrawAccount = withdrawAccount;
		this.depositBank = depositBank;
		this.depositAccount = depositAccount;
		this.depositorName = depositorName;
		this.transferAmount = transferAmount;
		this.depositMemo = depositMemo;
		this.transferResult = transferResult;
	}

	public Long getTransferId() {
		return transferId;
	}

	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}

	public Long getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(Long salaryId) {
		this.salaryId = salaryId;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public String getWithdrawBank() {
		return withdrawBank;
	}

	public void setWithdrawBank(String withdrawBank) {
		this.withdrawBank = withdrawBank;
	}

	public String getWithdrawAccount() {
		return withdrawAccount;
	}

	public void setWithdrawAccount(String withdrawAccount) {
		this.withdrawAccount = withdrawAccount;
	}

	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	public String getDepositAccount() {
		return depositAccount;
	}

	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}

	public String getDepositorName() {
		return depositorName;
	}

	public void setDepositorName(String depositorName) {
		this.depositorName = depositorName;
	}

	public Long getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Long transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getDepositMemo() {
		return depositMemo;
	}

	public void setDepositMemo(String depositMemo) {
		this.depositMemo = depositMemo;
	}

	public String getTransferResult() {
		return transferResult;
	}

	public void setTransferResult(String transferResult) {
		this.transferResult = transferResult;
	}
}