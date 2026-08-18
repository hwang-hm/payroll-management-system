package salary.transfer.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import salary.model.TransferHistory;

// 이체결과 조회 한 페이지분. 목록이랑 페이지번호 같이 들고있음
public class TransferHistoryPage {

	private final Date startDate;
	private final Date endDate;
	private final int total;
	private final int currentPage;
	private final List<TransferHistory> content;
	private final int totalPage;
	private final int startPage;
	private final int endPage;

	// 총건수 가지고 페이지 번호들 계산해둠
	public TransferHistoryPage(Date startDate, Date endDate, int total, int currentPage, int size,
			List<TransferHistory> content) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.total = total;
		this.currentPage = currentPage;
		this.content = content == null ? Collections.emptyList() : content;

		if (total == 0) {
			this.totalPage = 0;
			this.startPage = 0;
			this.endPage = 0;
		} else {
			int pages = total / size;
			if (total % size > 0) {
				pages++;
			}
			this.totalPage = pages;

			int modVal = currentPage % 5;
			int start = currentPage / 5 * 5 + 1;
			if (modVal == 0) {
				start -= 5;
			}
			int end = start + 4;
			if (end > pages) {
				end = pages;
			}
			this.startPage = start;
			this.endPage = end;
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getTotal() {
		return total;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<TransferHistory> getContent() {
		return content;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}
}
