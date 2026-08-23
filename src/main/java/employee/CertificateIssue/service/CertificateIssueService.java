package employee.CertificateIssue.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import employee.dao.CertificateIssueDao;
import jdbc.connection.ConnectionProvider;

public class CertificateIssueService {
	private CertificateIssueDao certificateIssueDao = new CertificateIssueDao();

	public List<CertificateIssueListData> getIssueList() {
		try (Connection conn = ConnectionProvider.getConnection()) {
			return certificateIssueDao.selectList(conn);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
