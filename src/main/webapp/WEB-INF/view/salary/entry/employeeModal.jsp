<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="empTableContent">
  <table class="list-table" style="width:100%;border-collapse:collapse;font-size:13px;">
    <thead>
      <tr style="background:#f8fafc;">
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;width:30px;">
          <input type="checkbox" id="empCheckAll" onclick="toggleEmpAll(this)">
        </th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">구분</th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">사원번호</th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">성명</th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">부서</th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">직위</th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">상태</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="emp" items="${employeeList}">
        <tr style="border-bottom:1px solid #e2e8f0;">
          <td style="padding:8px;text-align:center;">
            <input type="checkbox" name="empIds" value="${emp.empId}" class="emp-chk" form="addEmpForm">
          </td>
          <td style="padding:8px;text-align:center;">${emp.employmentType}</td>
          <td style="padding:8px;text-align:center;">${emp.empId}</td>
          <td style="padding:8px;text-align:center;">${emp.nameKor}</td>
          <td style="padding:8px;text-align:center;">${emp.deptName}</td>
          <td style="padding:8px;text-align:center;">${emp.positionName}</td>
          <td style="padding:8px;text-align:center;">${emp.status}</td>
        </tr>
      </c:forEach>
      <c:if test="${empty employeeList}">
        <tr>
          <td colspan="7" style="padding:30px;text-align:center;color:#94a3b8;">
            추가 가능한 사원이 없습니다.
          </td>
        </tr>
      </c:if>
    </tbody>
  </table>
</div>
