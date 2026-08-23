<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="empTableContent">
  <table class="list-table" style="width:100%;border-collapse:collapse;font-size:13px;">
    <thead>
      <tr style="background:#f8fafc;">
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;width:30px;">
          <input type="checkbox" id="empCheckAll" onclick="toggleEmpAll(this)">
        </th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">区分</th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">社員番号</th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">氏名</th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">部署</th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">役職</th>
        <th style="padding:8px;border-bottom:2px solid #e2e8f0;">状態</th>
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
            追加可能な社員がいません。
          </td>
        </tr>
      </c:if>
    </tbody>
  </table>
</div>
