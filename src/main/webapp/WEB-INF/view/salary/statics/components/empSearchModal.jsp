<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${not empty empList}">
		<c:forEach var="emp" items="${empList}">
			<tr onclick="selectModalEmp('${emp.empId}', '${emp.nameKor}')">
				<td>${not empty emp.employmentType ? emp.employmentType : ''}</td>
				<td>No-${emp.empId}</td>
				<td>${emp.nameKor}</td>
				<td>${not empty emp.deptName ? emp.deptName : '-'}</td>
				<td>${not empty emp.positionName ? emp.positionName : '-'}</td>
				<td>${not empty emp.status ? emp.status : ''}</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="6"
				style="padding: 20px; text-align: center; color: #888;">表示可能な社員一覧がありません。</td>
		</tr>
	</c:otherwise>
</c:choose>