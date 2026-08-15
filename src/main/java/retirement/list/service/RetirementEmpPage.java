package retirement.list.service;

import java.util.List;

public class RetirementEmpPage {
	private int total;
    private int currentPage;
    private List<RetirementEmpListData> retirementEmpListData;
    private int totalPage;
    private int startPage;
    private int endPage;

    public RetirementEmpPage(
            int total,
            int currentPage,
            int size,
            List<RetirementEmpListData> retirementEmpListData) {

        this.total = total;
        this.currentPage = currentPage;
        this.retirementEmpListData = retirementEmpListData;

        // 페이지 정보 계산
        if (total == 0) {
            totalPage = 0;
            startPage = 0;
            endPage = 0;
        } else {
            totalPage = total / size;

            if (total % size > 0) {
                totalPage++;
            }

            int modVol = currentPage % 5;

            startPage = currentPage / 5 * 5 + 1;

            if (modVol == 0) {
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

    public boolean hasNoEmployees() {
        return total == 0;
    }

    public boolean hasEmployees() {
        return total > 0;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPage;
    }

    public List<RetirementEmpListData> getRetirementEmpListData() {
        return retirementEmpListData;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }
}
