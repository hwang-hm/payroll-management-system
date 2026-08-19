package retirement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import jdbc.JdbcUtil;
import retirement.severancepay.service.OtherTaxableIncomeRequest;

public class OtherTaxableIncomeDao {

    // 기타 과세소득 입력
    public void insertOtherTaxableIncome(Connection conn, int severancePayId, OtherTaxableIncomeRequest request) throws SQLException {

        PreparedStatement pstmt = null;

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
}