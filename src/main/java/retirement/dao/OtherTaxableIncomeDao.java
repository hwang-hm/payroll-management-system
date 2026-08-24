package retirement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import retirement.model.OtherTaxableIncome;
import retirement.severancepay.service.OtherTaxableIncomeRequest;

public class OtherTaxableIncomeDao {

    // 기타 과세소득 입력
    // その他課税所得の入力
    public void insertOtherTaxableIncome(Connection conn, int severancePayId, OtherTaxableIncomeRequest request) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs= null;

        try {
            pstmt = conn.prepareStatement(
                    "insert into other_taxable_income "
                  + "(other_taxable_income_id, severance_pay_id, payment_month, payment_item, amount) "
                  + "values (seq_other_taxable_income.nextval, ?, ?, ?, ?)"
            );

            pstmt.setInt(1, severancePayId);
            pstmt.setDate(2, request.getPaymentMonth());
            pstmt.setString(3, request.getPaymentItem());

            if (request.getAmount() != null) {
                pstmt.setLong(4, request.getAmount());
            } else {
                pstmt.setNull(4, Types.NUMERIC);
            }

            pstmt.executeUpdate();

        } finally {
            JdbcUtil.close(pstmt);
        }
    }
    
    public List<OtherTaxableIncome> selectById(Connection conn, int severancePayId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("select * from other_taxable_income where severance_pay_id = ?");

			pstmt.setInt(1, severancePayId);
			rs = pstmt.executeQuery();

			List<OtherTaxableIncome> otherTaxableIncome = new ArrayList<>();
			
			while (rs.next()) {
				otherTaxableIncome.add(convertOtherTaxableIncome(rs));
			}

			return otherTaxableIncome;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
    }
    
    private OtherTaxableIncome convertOtherTaxableIncome(ResultSet rs) throws SQLException {
    	OtherTaxableIncome otherTaxableIncome = new OtherTaxableIncome();
		
    	otherTaxableIncome.setPaymentItem(rs.getString("payment_item"));
    	otherTaxableIncome.setAmount(rs.getInt("amount"));
    	
    	return otherTaxableIncome;
    	
	}
}