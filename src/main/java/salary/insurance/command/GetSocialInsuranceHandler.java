package salary.insurance.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.insurance.service.GetSocialInsuranceService;
import salary.insurance.service.request.GetSocialInsuranceDTO;

public class GetSocialInsuranceHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/salary/insurance/_socialInsuranceRows.jsp";
	private GetSocialInsuranceService service = new GetSocialInsuranceService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!req.getMethod().equalsIgnoreCase("GET")) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		String salaryDateIdVal = req.getParameter("salaryDateId");

		if (salaryDateIdVal != null && !salaryDateIdVal.trim().isEmpty()) {
			int salaryDateId = Integer.parseInt(salaryDateIdVal);
			List<GetSocialInsuranceDTO> list = service.getSocialInsuranceList(salaryDateId);
			req.setAttribute("insuranceList", list);
			req.setAttribute("salaryDateId", salaryDateId);

			return FORM_VIEW;
		}

		return "/WEB-INF/view/salary/insurance/socialInsurance.jsp";
	}

}
