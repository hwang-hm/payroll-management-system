<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 일용직 근로자 목록 출력 --%>
<%-- 日雇い労働者リストの出力 --%>
<c:choose>
	<c:when test="${not empty dayWorkerList}">
		<c:forEach var="emp" items="${dayWorkerList}">
			<%-- 사원 행 클릭 시 체크박스 토글 및 모달/선택 이벤트 처리 --%>
			<%-- 社員行クリック時にチェックボックスの切り替えおよび選択イベント処理 --%>
			<tr onclick="toggleModalRow(this, event)">
				<td style="text-align: center;"><input type="checkbox"
					class="worker-chk" data-emp-id="${emp.empId}"
					data-emp-name="${emp.nameKor}"
					data-dept-name="${not empty emp.deptName ? emp.deptName : '-'}"
					data-worker-type="${not empty emp.employmentType ? emp.employmentType : '-'}" />
				</td>
				<td>${not empty emp.employmentType ? emp.employmentType : '-'}</td>
				<td>No-${emp.empId}</td>
				<td>${emp.nameKor}</td>
				<td>${not empty emp.deptName ? emp.deptName : '-'}</td>
				<td>${not empty emp.positionName ? emp.positionName : '-'}</td>
				<td>${not empty emp.status ? emp.status : '-'}</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="7"
				style="padding: 20px; text-align: center; color: #888;">
				選択できる日雇い労働者がいません。</td>
		</tr>
	</c:otherwise>
</c:choose>