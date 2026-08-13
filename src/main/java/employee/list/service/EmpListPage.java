package employee.list.service;

import java.util.List;

public class EmpListPage {
	private int total;
    private int currentPage;
    private List<EmpListData> empListData;
    private int totalPage;
    private int startPage;
    private int endPage;
	
    private int regularCount;
    private int contractCount;
    private int temporaryCount;
    private int dispatchedCount;
    private int commissionedCount;
    private int dailyCount;
    private int activeCount;
    private int retiredCount;
    
    public EmpListPage() {
	}

    public EmpListPage(int total, int currentPage, int size, List<EmpListData> empListData,
            		int regularCount, int contractCount, int temporaryCount, int dispatchedCount, int commissionedCount, int dailyCount, int activeCount, int retiredCount) {
    	
        this.total = total;
        this.currentPage = currentPage;
        this.empListData = empListData;

        this.regularCount = regularCount;
        this.contractCount = contractCount;
        this.temporaryCount = temporaryCount;
        this.dispatchedCount = dispatchedCount;
        this.commissionedCount = commissionedCount;
        this.dailyCount = dailyCount;
        this.activeCount = activeCount;
        this.retiredCount = retiredCount;
        
        if (total == 0) {
            totalPage = 0;
            startPage = 0;
            endPage = 0;
        } 
        else {
            totalPage = total / size;

            if (total % size > 0) {
                totalPage++;
            }

            int modVal = currentPage % 5;

            startPage = currentPage / 5 * 5 + 1;

            if (modVal == 0) {
                startPage -= 5;
            }

            endPage = startPage + 4;

            if (endPage > totalPage) {
                endPage = totalPage;
            }
        }
    }
    
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<EmpListData> getEmpListData() {
		return empListData;
	}

	public void setEmpListData(List<EmpListData> empListData) {
		this.empListData = empListData;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getRegularCount() {
		return regularCount;
	}

	public void setRegularCount(int regularCount) {
		this.regularCount = regularCount;
	}

	public int getContractCount() {
		return contractCount;
	}

	public void setContractCount(int contractCount) {
		this.contractCount = contractCount;
	}

	public int getTemporaryCount() {
		return temporaryCount;
	}

	public void setTemporaryCount(int temporaryCount) {
		this.temporaryCount = temporaryCount;
	}

	public int getDispatchedCount() {
		return dispatchedCount;
	}

	public void setDispatchedCount(int dispatchedCount) {
		this.dispatchedCount = dispatchedCount;
	}

	public int getCommissionedCount() {
		return commissionedCount;
	}

	public void setCommissionedCount(int commissionedCount) {
		this.commissionedCount = commissionedCount;
	}

	public int getDailyCount() {
		return dailyCount;
	}

	public void setDailyCount(int dailyCount) {
		this.dailyCount = dailyCount;
	}

	public int getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}

	public int getRetiredCount() {
		return retiredCount;
	}

	public void setRetiredCount(int retiredCount) {
		this.retiredCount = retiredCount;
	}
	
	
    
}
