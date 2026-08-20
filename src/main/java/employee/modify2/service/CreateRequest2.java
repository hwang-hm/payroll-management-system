package employee.modify2.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import employee.modify.service.EmpRequest;

public class CreateRequest2 {
	
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
	
	public RetirementRequest createRetirementRequest(HttpServletRequest request) {
		return new RetirementRequest(
				toInt(request.getParameter("empId")), 
				request.getParameter("retirementType"), 
				toDate(request.getParameter("retirementDate")), 
				request.getParameter("retirementReason"),
				request.getParameter("contactAfterRetirement")
				);
	}
}
