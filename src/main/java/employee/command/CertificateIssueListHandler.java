package employee.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.CertificateIssue.service.CertificateIssueListData;
import employee.CertificateIssue.service.CertificateIssueService;
import mvc.command.CommandHandler;

public class CertificateIssueListHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/emp/certificateList.jsp";
	private CertificateIssueService certificateIssueService = new CertificateIssueService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		}

		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return null;
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) {

		List<CertificateIssueListData> certificateList = certificateIssueService.getIssueList();
		request.setAttribute("certificateList", certificateList);

		return FORM_VIEW;
	}

}
