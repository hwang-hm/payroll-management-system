package salary.transfer.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.model.TransferHistory;
import salary.transfer.dao.TransferHistoryDao;
import salary.transfer.viewdto.TransferHistoryPage;

public class TransferResultService {

	private static final int PAGE_SIZE = 10;
	private static final int MAX_RANGE_YEARS = 1;

	private final TransferHistoryDao transferHistoryDao = new TransferHistoryDao();

	// 조회기간에 맞는 이체결과 한 페이지치 가져옴
	public TransferHistoryPage getResultPage(Date startDate, Date endDate, int pageNo) {
		Date[] period = normalizePeriod(startDate, endDate);
		Date start = period[0];
		Date end = period[1];
		int currentPage = pageNo < 1 ? 1 : pageNo;

		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			int total = transferHistoryDao.selectCountByPeriod(conn, start, end);
			List<TransferHistory> content = Collections.emptyList();
			if (total > 0) {
				int totalPage = total / PAGE_SIZE;
				if (total % PAGE_SIZE > 0) {
					totalPage++;
				}
				if (currentPage > totalPage) {
					currentPage = totalPage;
				}
				int firstRow = (currentPage - 1) * PAGE_SIZE + 1;
				int endRow = firstRow + PAGE_SIZE - 1;
				content = transferHistoryDao.selectByPeriod(conn, start, end, firstRow, endRow);
			} else {
				currentPage = 1;
			}
			return new TransferHistoryPage(start, end, total, currentPage, PAGE_SIZE, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// 처음 들어왔을 때 기본기간. 이번달 1일~오늘
	public static Date[] defaultPeriod() {
		Calendar end = Calendar.getInstance();
		truncateTime(end);
		Calendar start = (Calendar) end.clone();
		start.set(Calendar.DAY_OF_MONTH, 1);
		return new Date[] { start.getTime(), end.getTime() };
	}

	// 시작/종료 뒤바뀌거나 1년 넘어가면 적당히 잘라줌
	private Date[] normalizePeriod(Date startDate, Date endDate) {
		Date[] defaults = defaultPeriod();
		Date start = startDate == null ? defaults[0] : truncate(startDate);
		Date end = endDate == null ? defaults[1] : truncate(endDate);

		Calendar today = Calendar.getInstance();
		truncateTime(today);
		Date todayDate = today.getTime();

		Calendar earliest = (Calendar) today.clone();
		earliest.add(Calendar.YEAR, -MAX_RANGE_YEARS);
		Date earliestDate = earliest.getTime();

		if (start.after(end)) {
			Date tmp = start;
			start = end;
			end = tmp;
		}
		if (end.after(todayDate)) {
			end = todayDate;
		}
		if (start.before(earliestDate)) {
			start = earliestDate;
		}
		if (start.after(end)) {
			start = end;
		}

		Calendar limit = Calendar.getInstance();
		limit.setTime(start);
		limit.add(Calendar.YEAR, MAX_RANGE_YEARS);
		if (end.after(limit.getTime())) {
			end = truncate(limit.getTime());
		}
		return new Date[] { start, end };
	}

	// 날짜에서 시간 버리고 날짜만 남김
	private Date truncate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		truncateTime(cal);
		return cal.getTime();
	}

	// 시분초를 0으로 맞춤
	private static void truncateTime(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}
}
