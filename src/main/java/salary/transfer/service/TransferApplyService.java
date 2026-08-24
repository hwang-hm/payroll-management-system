package salary.transfer.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.transfer.dao.TransferApplyDao;
import salary.transfer.dao.TransferFormDao;
import salary.model.TransferForm;
import salary.model.TransferFormItem;
import salary.transfer.TransferColumn;
import salary.transfer.viewdto.TransferApplyData;
import salary.transfer.viewdto.TransferApplyRow;
import salary.util.PayPeriodParams;

public class TransferApplyService {

	private final TransferApplyDao transferApplyDao = new TransferApplyDao();
	private final TransferFormDao transferFormDao = new TransferFormDao();

	// 이체신청 화면에 뿌릴 양식이랑 대상자 목록 모아줌
	// 振込申請画面に表示する様式と対象者一覧を集約
	public TransferApplyData getApplyData(PayPeriodParams period, Integer formId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			TransferApplyData data = new TransferApplyData();
			data.setPayYear(period.getPayYear());
			data.setPayMonth(period.getPayMonth());
			data.setPaySeq(period.getPaySeq());
			data.setAllColumns(Arrays.asList(TransferColumn.values()));
			data.setYearOptions(buildYearOptions(period.getPayYear()));

			List<TransferForm> forms = transferFormDao.selectAllForms(conn);
			data.setForms(forms);

			if (formId == null && !forms.isEmpty()) {
				formId = forms.get(0).getFormId();
			}
			data.setSelectedFormId(formId);

			String selectedFormName = "";
			List<TransferColumn> visible = new ArrayList<>();
			if (formId != null) {
				for (TransferForm form : forms) {
					if (formId.equals(form.getFormId())) {
						selectedFormName = form.getFormName();
						break;
					}
				}
				for (TransferFormItem item : transferFormDao.selectItemsByFormId(conn, formId)) {
					TransferColumn column = TransferColumn.fromCode(item.getItemCode());
					if (column != null) {
						visible.add(column);
					}
				}
			}
			data.setSelectedFormName(selectedFormName);
			if (visible.isEmpty()) {
				visible.add(TransferColumn.EMP_TYPE);
				visible.add(TransferColumn.NAME_KOR);
				visible.add(TransferColumn.DEPT);
				visible.add(TransferColumn.NET_PAY);
			}
			data.setVisibleColumns(visible);

			List<TransferApplyRow> rows = transferApplyDao.selectTransferApplyRowsByPeriod(conn, period.getPayYear(),
					period.getPayMonth(), period.getPaySeq());
			data.setRows(rows);

			long sumPay = 0;
			long sumDeduct = 0;
			long sumNet = 0;
			for (TransferApplyRow row : rows) {
				sumPay += row.getTotalPay();
				sumDeduct += row.getTotalDeduct();
				sumNet += row.getNetPay();
			}
			data.setSumTotalPay(sumPay);
			data.setSumTotalDeduct(sumDeduct);
			data.setSumNetPay(sumNet);
			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// 이체양식 이름/표시항목 저장. 없으면 새로 만들고 있으면 항목 갈아끼움
	// 振込様式の名前/表示項目を保存。なければ新規作成、あれば項目を差し替え
	public int saveForm(Integer formId, String formName, String[] itemCodes) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			String name = formName == null ? "" : formName.trim();
			if (name.isEmpty()) {
				name = "이체양식";
			}

			int savedFormId;
			if (formId == null || formId <= 0) {
				savedFormId = transferFormDao.insertForm(conn, name);
			} else {
				savedFormId = formId;
				transferFormDao.updateFormName(conn, savedFormId, name);
				transferFormDao.deleteItemsByFormId(conn, savedFormId);
			}

			if (itemCodes != null) {
				List<String> validCodes = new ArrayList<>();
				for (String code : itemCodes) {
					if (TransferColumn.fromCode(code) != null) {
						validCodes.add(code);
					}
				}
				transferFormDao.insertItemsBatch(conn, savedFormId, validCodes);
			}

			conn.commit();
			return savedFormId;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// 올해
	// 今年
	public static int currentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	// 이번 달
	// 今月
	public static int currentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	// 셀렉트박스용 연도 목록. 올해 기준으로 5년치
	// セレクトボックス用年度一覧。今年基準で5年分
	private List<Integer> buildYearOptions(int payYear) {
		List<Integer> years = new ArrayList<>();
		int base = Math.max(payYear, currentYear());
		for (int y = base; y >= base - 4; y--) {
			years.add(y);
		}
		if (!years.contains(payYear)) {
			years.add(0, payYear);
		}
		return years;
	}
}
