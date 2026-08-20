package salary.insurance.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.insurance.dao.SocialInsuranceDao;
import salary.insurance.service.request.GetSocialInsuranceDTO;

public class GetSocialInsuranceService {
	private SocialInsuranceDao dao = new SocialInsuranceDao();

	public List<GetSocialInsuranceDTO> getSocialInsuranceList(int salaryDateId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			return dao.getSocailInsuranceList(conn, salaryDateId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
