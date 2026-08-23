package employee.certificate.service;

import java.sql.Connection;
import java.sql.SQLException;

import employee.dao.CertificateIssueDao;
import employee.dao.EmpDao;
import employee.dto.EmployeeDetailData;
import jdbc.connection.ConnectionProvider;

public class PrintCertificateService {

	private EmpDao empDao = new EmpDao();
	private CertificateIssueDao certificateIssueDao = new CertificateIssueDao();


	public EmployeeDetailData selectEmp(int empId) {

		try (Connection conn = ConnectionProvider.getConnection()) {
			return empDao.selectByIdJoin(conn, empId);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void issueCertificate(int empId, String certificateType, String purpose, String issueDepartment) {

		try (Connection conn = ConnectionProvider.getConnection()) {
			certificateIssueDao.insert(conn, empId, certificateType, purpose, issueDepartment);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
