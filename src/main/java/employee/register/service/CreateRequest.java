package employee.register.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class CreateRequest {
	
	private Integer toInteger(String value) {
	    if (value == null || value.trim().isEmpty()) {
	        return null;
	    }
	    return Integer.valueOf(value);
	}

	private int toInt(String value) {
	    if (value == null || value.trim().isEmpty()) {
	        return 0;
	    }
	    return Integer.parseInt(value);
	}

	private Date toDate(String value) {
	    if (value == null || value.trim().isEmpty()) {
	        return null;
	    }
	    return Date.valueOf(value);
	}
	
	
	public EmpRequest createEmpRequest(HttpServletRequest request) {

	    return new EmpRequest(
	        request.getParameter("nameKor"),
	        toDate(request.getParameter("hireDate")),
	        request.getParameter("employmentType"),

	        toInteger(request.getParameter("deptId")),
	        request.getParameter("nationality"),
	        request.getParameter("nameEng"),
	        toDate(request.getParameter("retireDate")),
	        toInteger(request.getParameter("positionId")),

	        request.getParameter("residentNo"),
	        request.getParameter("zipCode"),
	        request.getParameter("address"),
	        request.getParameter("phoneNum"),
	        request.getParameter("mobileNum"),
	        request.getParameter("email"),
	        request.getParameter("sns"),
	        request.getParameter("remarks"),
	        request.getParameter("photoPath")
	    );
	}
	
	public EmpInfoSalaryRequest createEmpInfoSalaryRequest(
	        HttpServletRequest request) {

	    return new EmpInfoSalaryRequest(
	        request.getParameter("nationalPensionYN"),
	        request.getParameter("healthInsuranceYN"),

	        toInt(request.getParameter("healthDiscountRate")),

	        request.getParameter("longtermCareYN"),

	        toInt(request.getParameter("longtermCareDiscountRate")),

	        request.getParameter("employmentInsuranceYN"),
	        request.getParameter("incomeTaxType"),
	        request.getParameter("youthTaxReductionYN"),

	        toInt(request.getParameter("youthTaxReductionRate")),

	        request.getParameter("durunuriType"),
	        request.getParameter("durunuriSplitYN"),

	        toInt(request.getParameter("durunuriPensionRate")),
	        toInt(request.getParameter("durunuriEmploymentRate")),

	        toInt(request.getParameter("baseSalary")),

	        toInteger(request.getParameter("pensionSalary")),
	        toInteger(request.getParameter("healthSalary")),
	        toInteger(request.getParameter("employmentSalary")),

	        request.getParameter("salaryBank"),
	        request.getParameter("salaryAccount")
	    );
	}
	
	public List<DependentRequest> createDependentRequests(
	        HttpServletRequest request) {

	    List<DependentRequest> dependentReqs = new ArrayList<>();

	    String[] relations =
	            request.getParameterValues("relation");

	    String[] familyNames =
	            request.getParameterValues("familyName");

	    String[] nationalities =
	            request.getParameterValues("nationality");

	    String[] residentNos =
	            request.getParameterValues("residentNo");


	    if (relations == null) {
	        return dependentReqs;
	    }


	    for (int i = 0; i < relations.length; i++) {

	        DependentRequest dependentReq =
	                new DependentRequest(
	                    relations[i],
	                    familyNames[i],
	                    nationalities[i],
	                    residentNos[i],

	                    request.getParameter("disabilityYN_" + i),
	                    request.getParameter("deductionYN_" + i),
	                    request.getParameter("healthInsuranceYN_" + i),
	                    request.getParameter("cohabitationYN_" + i),
	                    request.getParameter("incomeTaxYN_" + i),
	                    request.getParameter("childAgeYN_" + i)
	                );

	        dependentReqs.add(dependentReq);
	    }

	    return dependentReqs;
	}
	
}
