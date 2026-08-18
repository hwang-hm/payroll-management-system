package salary.entry.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.service.SalarySaveDetailService;

//급여입력/관리 페이지에서 급여상세 내역 저장버튼 누를시 호출되는 핸들러.
public class SalarySaveDetailHandler implements CommandHandler {

	private final SalarySaveDetailService saveDetailService = new SalarySaveDetailService();

	// 상세금액 저장하고 다시 급여입력 화면으로 돌아감
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		int salaryId = Integer.parseInt(req.getParameter("salaryId"));
		int payYear = Integer.parseInt(req.getParameter("payYear"));
		int payMonth = Integer.parseInt(req.getParameter("payMonth"));
		int paySeq = Integer.parseInt(req.getParameter("paySeq"));

		String[] detailIdArr = req.getParameterValues("detailId");
		String[] amountArr = req.getParameterValues("amount");
		String[] calcMethodArr = req.getParameterValues("calcMethod");

		List<Integer> detailIds = new ArrayList<>();
		List<Long> amounts = new ArrayList<>();
		List<String> calcMethods = new ArrayList<>();

		if (detailIdArr != null) {
			for (int i = 0; i < detailIdArr.length; i++) {
				detailIds.add(Integer.parseInt(detailIdArr[i]));
				long amount = 0;
				if (amountArr != null && i < amountArr.length && amountArr[i] != null
						&& !amountArr[i].trim().isEmpty()) {
					amount = Long.parseLong(amountArr[i].replace(",", "").trim());
				}
				amounts.add(amount);
				String calcMethod = null;
				if (calcMethodArr != null && i < calcMethodArr.length) {
					calcMethod = calcMethodArr[i];
				}
				calcMethods.add(calcMethod);
			}
		}

		saveDetailService.save(salaryId, detailIds, amounts, calcMethods);

		// 리다이렉트 해주는 이유 : 포워드를 하면 URL이 바뀌지 않기 때문에 페이지 새로고침을 하면
		// 여전히 /salary/entry/save.do를 호출하여 이 핸들러가 호출되는 문제가 발생.
		// 때문에 salary/entry.do로 리다이렉트 해서 URL을 한번 갱신해줌
		return "redirect:/salary/entry.do?payYear=" + payYear + "&payMonth=" + payMonth + "&paySeq=" + paySeq
				+ "&salaryId=" + salaryId;
	}
}
