<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>급여대장</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
  <style>
    :root {
      --primary: #2563eb;
      --primary-hover: #1d4ed8;
      --pay-color: #2563eb;
      --deduct-color: #e11d48;
      --bg-gray: #f8fafc;
      --border: #e2e8f0;
      --text-main: #1e293b;
      --text-muted: #64748b;
    }
    body { font-family: 'Pretendard Variable', Pretendard, sans-serif; background: var(--bg-gray); color: var(--text-main); margin: 0; }
    .container { max-width: 1400px; margin: 0 auto 60px; padding: 0 20px; }
    .page-header { margin-bottom: 16px; border-bottom: 2px solid var(--border); padding-bottom: 14px; }
    .page-header h2 { font-size: 24px; font-weight: 700; margin: 0 0 4px 0; }
    .page-header p { margin: 0; font-size: 13px; color: var(--text-muted); }

    .filter-bar {
      background: #2b3648; color: #fff; border-radius: 8px; padding: 14px 18px;
      display: flex; flex-wrap: wrap; align-items: center; gap: 14px; margin-bottom: 16px;
    }
    .filter-bar label { font-size: 13px; font-weight: 600; }
    .filter-bar select {
      padding: 6px 10px; border-radius: 6px; border: 1px solid #cbd5e1; font-size: 13px;
    }
    .filter-info { font-size: 12px; color: #cbd5e1; }

    .btn {
      display: inline-flex; align-items: center; justify-content: center; gap: 4px;
      padding: 7px 14px; font-size: 13px; font-weight: 600; border-radius: 6px;
      cursor: pointer; border: none; text-decoration: none;
    }
    .btn-danger { background: #e11d48; color: #fff; }
    .btn-sm { padding: 5px 10px; font-size: 12px; }

    .panel { background: #fff; border: 1px solid var(--border); border-radius: 8px; overflow: hidden; }
    .panel-head {
      padding: 10px 14px; background: #f8fafc; border-bottom: 1px solid var(--border);
      display: flex; justify-content: space-between; align-items: center;
    }
    .panel-head h3 { margin: 0; font-size: 14px; font-weight: 700; }
    .panel-body { padding: 12px; }

    .list-table { width: 100%; border-collapse: collapse; font-size: 13px; }
    .list-table th {
      background: #f8fafc; color: #475569; font-weight: 700; padding: 10px 8px;
      border-bottom: 2px solid var(--border); text-align: center;
    }
    .list-table td {
      padding: 9px 8px; border-bottom: 1px solid var(--border); text-align: center;
    }
    .list-table tbody tr:hover { background: #eff6ff; }
    .amount-pay { color: var(--pay-color); font-weight: 600; }
    .amount-deduct { color: var(--deduct-color); font-weight: 600; }
    .list-table tfoot td {
      background: #fef9c3; font-weight: 700; padding: 10px 8px;
      border-top: 2px solid #fde047; border-bottom: none;
    }
    .empty-msg { text-align: center; padding: 40px; color: #94a3b8; font-size: 14px; }
  </style>
</head>
<body>

<layout:headerNav />

<div class="container">
  <div class="page-header">
    <h2>급여대장</h2>
    <p>귀속연월·차수별 급여 현황을 조회할 수 있습니다.</p>
  </div>

  <form id="filterForm" action="${pageContext.request.contextPath}/salary/register.do" method="get">
    <div class="filter-bar">
      <label>귀속연도</label>
      <select name="payYear" onchange="this.form.submit()">
        <c:forEach var="y" items="${registerData.yearOptions}">
          <option value="${y}" ${y eq registerData.payYear ? 'selected' : ''}>${y} 년</option>
        </c:forEach>
      </select>
      <span class="filter-info">귀속연도를 선택하면 해당 연도의 급여대장 목록을 확인할 수 있습니다.</span>
    </div>
  </form>

  <div class="panel">
    <div class="panel-head">
      <h3>급여대장 목록</h3>
      <span style="font-size:12px;color:#64748b;">${registerData.rows.size()}건</span>
    </div>
    <div class="panel-body">
      <table class="list-table">
        <thead>
          <tr>
            <th>귀속연월</th>
            <th>급여차수</th>
            <th>정산기간</th>
            <th>지급일</th>
            <th>인원</th>
            <th>지급총액</th>
            <th>공제총액</th>
            <th>실지급액</th>
            <th>삭제</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="row" items="${registerData.rows}">
            <tr>
              <td>
                ${row.payYear}-<fmt:formatNumber value="${row.payMonth}" minIntegerDigits="2"/>
              </td>
              <td>
                급여-<fmt:formatNumber value="${row.paySeq}" minIntegerDigits="2"/>차
              </td>
              <td>${row.startDate} ~ ${row.endDate}</td>
              <td>${row.payDate}</td>
              <td>${row.empCount}</td>
              <td class="amount-pay"><fmt:formatNumber value="${row.totalPay}" pattern="#,###"/></td>
              <td class="amount-deduct"><fmt:formatNumber value="${row.totalDeduct}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.netPay}" pattern="#,###"/></td>
              <td>
                <button type="button" class="btn btn-danger btn-sm">삭제</button>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty registerData.rows}">
            <tr><td colspan="9" class="empty-msg">해당 연도에 등록된 급여 대장이 없습니다.</td></tr>
          </c:if>
        </tbody>
        <c:if test="${not empty registerData.rows}">
          <tfoot>
            <tr>
              <td>합계</td>
              <td></td>
              <td></td>
              <td></td>
              <td>${registerData.summary.count}</td>
              <td class="amount-pay"><fmt:formatNumber value="${registerData.summary.totalPay}" pattern="#,###"/></td>
              <td class="amount-deduct"><fmt:formatNumber value="${registerData.summary.totalDeduct}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${registerData.summary.netPay}" pattern="#,###"/></td>
              <td></td>
            </tr>
          </tfoot>
        </c:if>
      </table>
    </div>
  </div>
</div>

<layout:footer />

</body>
</html>
