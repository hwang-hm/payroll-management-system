package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDetailDao;
import salary.entry.viewdto.SalaryEntryData;
import salary.entry.viewdto.SalarySummary;
import salary.entry.viewdto.SalaryView;
import salary.model.SalaryDate;
import salary.model.SalaryDetail;

public class SalaryEntryService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();
	private final SalaryDateService salaryDateService = new SalaryDateService();

	// 귀속년월/차수 기준으로 급여입력 화면에 뿌릴 데이터를 모아줌
	public SalaryEntryData getEntryData(int payYear, int payMonth, int paySeq, int selectedSalaryId) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			SalaryDate salaryDate = salaryDateService.ensureByPeriod(conn, payYear, payMonth, paySeq);
			int salaryDateId = salaryDate.getSalaryDateId();
			List<SalaryView> salaryList = salaryDao.selectViewListBySalaryDateId(conn, salaryDateId);

			if (selectedSalaryId == 0 && !salaryList.isEmpty()) {
				selectedSalaryId = salaryList.get(0).getSalaryId();
			}

			SalaryEntryData data = new SalaryEntryData();
			data.setSalaryDateId(salaryDateId);
			data.setPayYear(payYear);
			data.setPayMonth(payMonth);
			data.setPaySeq(paySeq);
			data.setSalaryList(salaryList);
			data.setSummary(toSummary(salaryList));
			data.setSelectedSalaryId(selectedSalaryId);
			setPeriodDates(data, salaryDate);

			if (selectedSalaryId > 0) {
				splitDetails(conn, selectedSalaryId, data);
				data.setSelectedSalary(findSalaryView(salaryList, selectedSalaryId));
			}

			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 왼쪽 사원목록의 인원수, 지급/공제/실지급 합계 계산
	private SalarySummary toSummary(List<SalaryView> salaryList) {
		SalarySummary summary = new SalarySummary();
		summary.setCount(salaryList.size());
		long totalPay = 0;
		long totalDeduct = 0;
		long netPay = 0;
		for (SalaryView view : salaryList) {
			totalPay += view.getTotalPay();
			totalDeduct += view.getTotalDeduct();
			netPay += view.getNetPay();
		}
		summary.setTotalPay(totalPay);
		summary.setTotalDeduct(totalDeduct);
		summary.setNetPay(netPay);
		return summary;
	}

	// 급여상세를 지급항목이랑 공제항목으로 나눠서 담아줌
	private void splitDetails(Connection conn, int salaryId, SalaryEntryData data) throws SQLException {
		List<SalaryDetail> payDetails = new ArrayList<>();
		List<SalaryDetail> deductDetails = new ArrayList<>();
		for (SalaryDetail detail : salaryDetailDao.selectBySalaryId(conn, salaryId)) {
			if ("PAY".equals(detail.getItemType())) {
				payDetails.add(detail);
			} else {
				deductDetails.add(detail);
			}
		}
		data.setPayDetails(payDetails);
		data.setDeductDetails(deductDetails);
	}

	// 목록에서 지금 클릭한 사원 한 명 찾아줌
	private SalaryView findSalaryView(List<SalaryView> salaryList, int salaryId) {
		for (SalaryView view : salaryList) {
			if (view.getSalaryId() == salaryId) {
				return view;
			}
		}
		return null;
	}

	// 정산기간/지급일을 화면용 날짜문자열로 바꿔줌
	private void setPeriodDates(SalaryEntryData data, SalaryDate salaryDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		data.setStartDate(sdf.format(salaryDate.getStartDate()));
		data.setEndDate(sdf.format(salaryDate.getEndDate()));
		data.setPayDate(sdf.format(salaryDate.getPayDate()));
	}
}
