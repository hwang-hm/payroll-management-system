<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${not empty dayWorkerList}">
		<c:forEach var="emp" items="${dayWorkerList}">
			<tr onclick="toggleModalRow(this, event)">
				<td style="text-align: center;"><input type="checkbox"
					class="worker-chk" data-emp-id="${emp.empId}"
					data-emp-name="${emp.nameKor}"
					data-dept-name="${not empty emp.deptName ? emp.deptName : ''}"
					data-worker-type="${not empty emp.employmentType ? emp.employmentType : '일용직'}" />
				</td>
				<td>${not empty emp.employmentType ? emp.employmentType : '일용직'}</td>
				<td>No-${emp.empId}</td>
				<td>${emp.nameKor}</td>
				<td>${not empty emp.deptName ? emp.deptName : ''}</td>
				<td>${not empty emp.positionName ? emp.positionName : ''}</td>
				<td>${not empty emp.status ? emp.status : '재직'}</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="7"
				style="padding: 20px; text-align: center; color: #888;">선택할 수
				있는 일용직 근로자가 없습니다.</td>
		</tr>
	</c:otherwise>
</c:choose>