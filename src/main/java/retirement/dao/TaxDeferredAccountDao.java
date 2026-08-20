package retirement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import jdbc.JdbcUtil;
import retirement.severancepay.service.TaxDeferredAccountRequest;

public class TaxDeferredAccountDao {
	
	public void insertTaxDeferredAccount(Connection conn, int severancePayId, TaxDeferredAccountRequest request) throws SQLException {
		PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(
                    "insert into tax_deferred_account "
                  + "(tax_deferred_account_id, severance_pay_id, pension_provider_name,  business_registration_no, " 
                  + "account_no, deposit_date, deposit_amount) "
                  + "values (seq_tax_deferred_account.nextval, ?, ?, ?, ?, ?, ?)"
            );

            pstmt.setInt(1, severancePayId);
            pstmt.setString(2, request.getPensionProviderName());
            pstmt.setString(3, request.getBusinessRegistrationNo());
            pstmt.setString(4, request.getAccountNo());
            pstmt.setDate(5, request.getDepositDate());

            if (request.getDepositAmount() != null) {
                pstmt.setLong(6, request.getDepositAmount());
            } else {
                pstmt.setNull(6, Types.NUMERIC);
            }

            pstmt.executeUpdate();

        } finally {
            JdbcUtil.close(pstmt);
        }
	}
}
