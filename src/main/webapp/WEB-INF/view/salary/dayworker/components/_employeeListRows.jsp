<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<c:when test="${not empty salaryList}">
		<c:forEach var="item" items="${salaryList}">
			<tr data-emp-id="${item.empId}"
				onclick="selectWorker(this, '${item.empId}', '${item.nameKor}')">
				<td>${item.employmentType}</td>
				<td>${item.nameKor}</td>
				<td>${item.deptName}</td>
				<td style="text-align: right; font-weight: 600;"><fmt:formatNumber
						value="${item.netPay}" pattern="#,##0" />원</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="4"
				style="text-align: center; padding: 20px; color: #888;">저장된 급여
				내역이 없습니다.</td>
		</tr>
	</c:otherwise>
</c:choose>