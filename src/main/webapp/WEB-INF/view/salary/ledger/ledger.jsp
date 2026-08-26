<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>ヘキサゴンゾーン - 給与台帳</title>
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
    .list-table tbody tr.clickable { cursor: pointer; }
    .list-table tbody tr.clickable:hover { background: #eff6ff; }
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
    <h2>給与台帳</h2>
    <p>帰属年月・回数別の給与状況を照会できます。</p>
  </div>

  <form id="filterForm" action="${pageContext.request.contextPath}/salary/ledger.do" method="get">
    <div class="filter-bar">
      <label>帰属年度</label>
      <select name="payYear" onchange="this.form.submit()">
        <c:forEach var="y" items="${ledgerData.yearOptions}">
          <option value="${y}" ${y eq ledgerData.payYear ? 'selected' : ''}>${y} 年</option>
        </c:forEach>
      </select>
      <span class="filter-info">帰属年度を選択すると、該当年度の給与台帳一覧を確認できます。</span>
    </div>
  </form>

  <div class="panel">
    <div class="panel-head">
      <h3>給与台帳一覧</h3>
      <span style="font-size:12px;color:#64748b;">${ledgerData.rows.size()}件</span>
    </div>
    <div class="panel-body">
      <table class="list-table">
        <thead>
          <tr>
            <th>帰属年月</th>
            <th>給与回数</th>
            <th>精算期間</th>
            <th>支給日</th>
            <th>人数</th>
            <th>支給総額</th>
            <th>控除総額</th>
            <th>実支給額</th>
            <th>削除</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="row" items="${ledgerData.rows}">
            <tr class="clickable"
                onclick="location.href='${pageContext.request.contextPath}/salary/ledger/detail.do?salaryDateId=${row.salaryDateId}'">
              <td>
                ${row.payYear}-<fmt:formatNumber value="${row.payMonth}" minIntegerDigits="2"/>
              </td>
              <td>
                給与-<fmt:formatNumber value="${row.paySeq}" minIntegerDigits="2"/>回
              </td>
              <td>${row.startDate} ~ ${row.endDate}</td>
              <td>${row.payDate}</td>
              <td>${row.empCount}</td>
              <td class="amount-pay"><fmt:formatNumber value="${row.totalPay}" pattern="#,###"/></td>
              <td class="amount-deduct"><fmt:formatNumber value="${row.totalDeduct}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.netPay}" pattern="#,###"/></td>
              <td onclick="event.stopPropagation();">
                <form action="${pageContext.request.contextPath}/salary/ledger/delete.do" method="post"
                      style="display:inline;margin:0;"
                      onsubmit="return confirm('該当帰属年月・回数の給与をすべて削除しますか？');">
                  <input type="hidden" name="salaryDateId" value="${row.salaryDateId}">
                  <input type="hidden" name="payYear" value="${ledgerData.payYear}">
                  <button type="submit" class="btn btn-danger btn-sm">削除</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty ledgerData.rows}">
            <tr><td colspan="9" class="empty-msg">該当年度に登録された給与台帳がありません。</td></tr>
          </c:if>
        </tbody>
        <c:if test="${not empty ledgerData.rows}">
          <tfoot>
            <tr>
              <td>合計</td>
              <td></td>
              <td></td>
              <td></td>
              <td>${ledgerData.summary.count}</td>
              <td class="amount-pay"><fmt:formatNumber value="${ledgerData.summary.totalPay}" pattern="#,###"/></td>
              <td class="amount-deduct"><fmt:formatNumber value="${ledgerData.summary.totalDeduct}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${ledgerData.summary.netPay}" pattern="#,###"/></td>
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
