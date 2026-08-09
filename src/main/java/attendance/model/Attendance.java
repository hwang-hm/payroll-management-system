package attendance.model;

import java.sql.Date;

/**
 * 근태기록 정보를 저장하는 모델 클래스
 * 勤怠記録情報を保存するモデルクラス
 */

public class Attendance {

	// 근태기록 번호 / 勤怠記録番号
	private int attendanceId;
	
	// 입력일자 / 入力日
	private Date inputDate;
	
	// 근태 시작일 / 勤怠開始日
	private Date attendanceStartDate;
	
	// 근태 종료일 / 勤怠終了日
	private Date attendanceEndDate;
	
	// 근태일수 / 勤怠日数
	private double attendanceDays;
	
	// 수당 금액 / 手当金額
	private int allowanceAmount;
	
	// 적요 / 摘要
	private String remarks;
	
	// 사원번호 / 社員番号
	private int empId;
	
	// 근태항목 번호 / 勤怠項目番号
	private int attendanceTypeId;

	// 기본 생성자 / デフォルトコンストラクタ

	public Attendance() {

	}

	
	// 전체 필드 생성자 / 全フィールドコンストラクタ
	public Attendance(int attendanceId, Date inputDate, Date attendanceStartDate, Date attendanceEndDate,
			double attendanceDays, int allowanceAmount, String remarks, int empId, int attendanceTypeId) {
		this.attendanceId = attendanceId;
		this.inputDate = inputDate;
		this.attendanceStartDate = attendanceStartDate;
		this.attendanceEndDate = attendanceEndDate;
		this.attendanceDays = attendanceDays;
		this.allowanceAmount = allowanceAmount;
		this.remarks = remarks;
		this.empId = empId;
		this.attendanceTypeId = attendanceTypeId;
	}

	
	// getter 및 setter / getterおよびsetter
	public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Date getAttendanceStartDate() {
        return attendanceStartDate;
    }

    public void setAttendanceStartDate(Date attendanceStartDate) {
        this.attendanceStartDate = attendanceStartDate;
    }

    public Date getAttendanceEndDate() {
        return attendanceEndDate;
    }

    public void setAttendanceEndDate(Date attendanceEndDate) {
        this.attendanceEndDate = attendanceEndDate;
    }

    public double getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(double attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public int getAllowanceAmount() {
        return allowanceAmount;
    }

    public void setAllowanceAmount(int allowanceAmount) {
        this.allowanceAmount = allowanceAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getAttendanceTypeId() {
        return attendanceTypeId;
    }

    public void setAttendanceTypeId(int attendanceTypeId) {
        this.attendanceTypeId = attendanceTypeId;
    }
	
	
	// 객체 정보를 문자열로 반환 / オブジェクト情報を文字列として返す
	@Override
	public String toString() {
        return "Attendance [attendanceId=" + attendanceId
                + ", inputDate=" + inputDate
                + ", attendanceStartDate=" + attendanceStartDate
                + ", attendanceEndDate=" + attendanceEndDate
                + ", attendanceDays=" + attendanceDays
                + ", allowanceAmount=" + allowanceAmount
                + ", remarks=" + remarks
                + ", empId=" + empId
                + ", attendanceTypeId=" + attendanceTypeId + "]";
    }

}
