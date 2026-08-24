package retirement.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import retirement.model.SeverancePay;
import retirement.severancepay.service.SeveranceCalculateRequest;
import retirement.severancepay.service.SeveranceCalculateResult;
import retirement.severancepay.service.SeveranceSalaryData;

public class SeverancePayDao {
	// 퇴직금 계산용 급여 조회
	// 退職金計算用給与の照会
	public List<SeveranceSalaryData> selectSalaryForSeverance(Connection conn, int empId, Date startDate, Date endDate)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "select s.salary_id, s.salary_date_id, s.emp_id, " + "s.total_pay, sd.pay_year, sd.pay_month, "
					+ "sd.start_date, sd.end_date " + "from salary s " + "join salary_date sd "
					+ "on s.salary_date_id = sd.salary_date_id " + "where s.emp_id = ? " + "and sd.end_date >= ? "
					+ "and sd.start_date <= ? " + "order by sd.start_date";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, empId);
			pstmt.setDate(2, startDate);
			pstmt.setDate(3, endDate);

			rs = pstmt.executeQuery();

			List<SeveranceSalaryData> result = new ArrayList<>();

			while (rs.next()) {
				result.add(convertSeveranceSalaryData(rs));
			}

			return result;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	private SeveranceSalaryData convertSeveranceSalaryData(ResultSet rs) throws SQLException {

		return new SeveranceSalaryData(rs.getInt("salary_id"), rs.getInt("salary_date_id"), rs.getInt("emp_id"),
				rs.getLong("total_pay"), rs.getInt("pay_year"), rs.getInt("pay_month"), rs.getDate("start_date"),
				rs.getDate("end_date"));
	}

	public int insertSeverancePay(Connection conn, SeveranceCalculateRequest request, SeveranceCalculateResult result)
			throws SQLException {

		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "insert into severance_pay (" + "severance_pay_id, " + "emp_id, " + "retirement_bonus, "
					+ "dismissal_allowance, " + "non_taxable_retirement_pay, " + "prepaid_tax, " + "tax_credit, "
					+ "daily_ordinary_wage, " + "retirement_income, " + "calculated_tax, " + "retirement_income_tax, "
					+ "local_income_tax, " + "deferred_retirement_tax, " + "deferred_local_tax, "
					+ "rural_special_tax, " + "other_deduction, " + "taxable_retirement_pay, " + "withholding_tax, "
					+ "net_payment, " + "payment_date) " + "values (seq_severance_pay.nextval, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, request.getEmpId());

			// 퇴직위로금
			// 退職慰労金
			if (request.getRetirementBonus() != null) {
				pstmt.setLong(2, request.getRetirementBonus());
			} else {
				pstmt.setNull(2, Types.NUMERIC);
			}

			// 해고예고수당
			// 解雇予告手当
			if (request.getDismissalAllowance() != null) {
				pstmt.setLong(3, request.getDismissalAllowance());
			} else {
				pstmt.setNull(3, Types.NUMERIC);
			}

			// 비과세 퇴직급여
			// 非課税退職給与
			if (request.getNonTaxableRetirementPay() != null) {
				pstmt.setLong(4, request.getNonTaxableRetirementPay());
			} else {
				pstmt.setNull(4, Types.NUMERIC);
			}

			// 기납부세액
			// 既納付税額
			if (request.getPrepaidTax() != null) {
				pstmt.setLong(5, request.getPrepaidTax());
			} else {
				pstmt.setNull(5, Types.NUMERIC);
			}

			// 세액공제
			// 税額控除
			if (request.getTaxCredit() != null) {
				pstmt.setLong(6, request.getTaxCredit());
			} else {
				pstmt.setNull(6, Types.NUMERIC);
			}

			// 1일 통상임금
			// 1日通常賃金
			if (request.getDailyOrdinaryWage() != null) {
				pstmt.setLong(7, request.getDailyOrdinaryWage());
			} else {
				pstmt.setNull(7, Types.NUMERIC);
			}

			// 계산 결과
			// 計算結果
			pstmt.setLong(8, result.getRetirementIncome());
			pstmt.setLong(9, result.getCalculatedTax());
			pstmt.setLong(10, result.getRetirementIncomeTax());
			pstmt.setLong(11, result.getLocalIncomeTax());
			pstmt.setLong(12, result.getDeferredRetirementTax());
			pstmt.setLong(13, result.getDeferredLocalTax());

			// 농어촌특별세
			// 農漁村特別税
			if (request.getRuralSpecialTax() != null) {
				pstmt.setLong(14, request.getRuralSpecialTax());
			} else {
				pstmt.setNull(14, Types.NUMERIC);
			}

			// 기타공제
			// その他控除
			if (request.getOtherDeduction() != null) {
				pstmt.setLong(15, request.getOtherDeduction());
			} else {
				pstmt.setNull(15, Types.NUMERIC);
			}

			pstmt.setLong(16, result.getTaxableRetirementPay());
			pstmt.setLong(17, result.getWithholdingTax());
			pstmt.setLong(18, result.getNetPayment());

			// 지급일
			// 支給日
			if (request.getPaymentDate() != null) {
				pstmt.setDate(19, request.getPaymentDate());
			} else {
				pstmt.setNull(19, Types.DATE);
			}

			pstmt.executeUpdate();

			// 방금 생성된 severance_pay_id 조회
			// 直前に生成された severance_pay_id を照会
			stmt = conn.createStatement();

			rs = stmt.executeQuery("select seq_severance_pay.currval from dual");

			if (rs.next()) {
				return rs.getInt(1);
			}

			throw new SQLException("severance_pay_id 생성 실패");

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}

	public SeverancePay selectById(Connection conn, int empId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("select * from severance_pay where emp_id = ?");

			pstmt.setInt(1, empId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				SeverancePay severancePay = new SeverancePay();

				severancePay.setSeverancePayId(rs.getInt("severance_pay_id"));
				severancePay.setEmpId(rs.getInt("emp_id"));
				severancePay.setRetirementIncome(rs.getInt("retirement_income"));
				severancePay.setNetPayment(rs.getInt("net_payment"));

				return severancePay;
			}

			return null;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
}
