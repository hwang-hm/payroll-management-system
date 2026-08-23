<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="emp" items="${employeeList}">
  <tr class="EmpSalaryHistory-modal-row"
      data-emp-id="${emp.empId}"
      data-emp-name="${emp.nameKor}"
      onclick="selectEmpSalaryHistoryRow(this)">
    <td>${emp.employmentType}</td>
    <td>${emp.empId}</td>
    <td>${emp.nameKor}</td>
    <td>${emp.deptName}</td>
    <td>${emp.positionName}</td>
    <td>${emp.status}</td>
  </tr>
</c:forEach>
<c:if test="${empty employeeList}">
  <tr>
    <td colspan="6" class="empty-msg">照会された社員がいません。</td>
  </tr>
</c:if>
