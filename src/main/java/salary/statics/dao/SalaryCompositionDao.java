package salary.statics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import salary.statics.service.request.SalaryCompositionDTO;
import salary.statics.service.request.SalaryItemDTO;

public class SalaryCompositionDao {
	public SalaryCompositionDTO selectSalaryComposition(Connection conn, int empId, int year, int month)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT " + "s.total_pay, s.total_deduct, s.net_pay, " + "sd.item_type, sd.item_name, sd.amount "
				+ "FROM salary s " + "JOIN salary_date sd_date ON s.salary_date_id = sd_date.salary_date_id "
				+ "JOIN salary_detail sd ON s.salary_id = sd.salary_id "
				+ "WHERE s.emp_id = ? AND sd_date.pay_year = ? AND sd_date.pay_month = ? "
				+ "ORDER BY sd.item_type DESC, sd.detail_id ASC";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);

			rs = pstmt.executeQuery();

			SalaryCompositionDTO dto = null;

			while (rs.next()) {
				if (dto == null) {
					dto = new SalaryCompositionDTO();
					dto.setEmpId(empId);
					dto.setYear(year);
					dto.setMonth(month);
					dto.setTotalPay(rs.getLong("total_pay"));
					dto.setTotalDeduct(rs.getLong("total_deduct"));
					dto.setNetPay(rs.getLong("net_pay"));
				}

				String itemType = rs.getString("item_type");
				String itemName = rs.getString("item_name");
				long amount = rs.getLong("amount");

				SalaryItemDTO item = new SalaryItemDTO(itemName, itemType, amount);

				if ("PAY".equalsIgnoreCase(itemType)) {
					dto.addPayItem(item);
				} else if ("DEDUCT".equalsIgnoreCase(itemType)) {
					dto.addDeductItem(item);
				}
			}

			if (dto != null) {
				dto.calculateRates();
			}

			return dto;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

}
