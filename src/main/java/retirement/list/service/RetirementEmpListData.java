package retirement.list.service;

import java.sql.Date;
import java.time.LocalDate;

public class RetirementEmpListData {

    private int empId;
    private String nameKor;
    private Date hireDate;

    private String deptName;
    private String positionName;

    private Date retireDate;

    private String status;

    // 근속연수
    // 勤続年数
    private int workYears;


    public RetirementEmpListData() {
    }


    public RetirementEmpListData(
            int empId,
            String nameKor,
            Date hireDate,
            String deptName,
            String positionName,
            Date retireDate,
            String status) {

        this.empId = empId;
        this.nameKor = nameKor;
        this.hireDate = hireDate;
        this.deptName = deptName;
        this.positionName = positionName;
        this.retireDate = retireDate;
        this.status = status;

        // 근속연수 계산
        // 勤続年数の計算
        if (hireDate != null) {

            int hireYear = hireDate.toLocalDate().getYear();

            int endYear;

            // 퇴직일이 있으면 퇴직연도 기준
            // 退職日があれば退職年度基準
            if (retireDate != null) {
                endYear = retireDate.toLocalDate().getYear();

            // 아직 재직 중이면 현재연도 기준
            // まだ在職中なら現在年度基準
            } else {
                endYear = LocalDate.now().getYear();
            }

            this.workYears = endYear - hireYear;
        }
    }


    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getNameKor() {
        return nameKor;
    }

    public void setNameKor(String nameKor) {
        this.nameKor = nameKor;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Date retireDate) {
        this.retireDate = retireDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWorkYears() {
        return workYears;
    }

    public void setWorkYears(int workYears) {
        this.workYears = workYears;
    }
}