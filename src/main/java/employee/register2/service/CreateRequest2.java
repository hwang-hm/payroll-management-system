package employee.register2.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
