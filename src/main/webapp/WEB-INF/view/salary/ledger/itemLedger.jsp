<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>ヘキサゾーン - 項目別台帳</title>
  <style>
    :root {
      --primary: #2563eb;
      --primary-hover: #1d4ed8;
      --bg-gray: #f8fafc;
      --border: #e2e8f0;
      --text-main: #1e293b;
      --text-muted: #64748b;
    }
    body { font-family: 'Pretendard Variable', Pretendard, sans-serif; background: var(--bg-gray); color: var(--text-main); margin: 0; }
    .ItemLedger-container { max-width: 1400px; margin: 0 auto 60px; padding: 0 20px; }
    .ItemLedger-header { margin-bottom: 16px; border-bottom: 2px solid var(--border); padding-bottom: 14px; }
    .ItemLedger-header h2 { font-size: 24px; font-weight: 700; margin: 0 0 4px 0; }
    .ItemLedger-header p { margin: 0; font-size: 13px; color: var(--text-muted); }

    .ItemLedger-filter {
      background: #2b3648; color: #fff; border-radius: 8px; padding: 14px 18px;
      display: flex; flex-wrap: wrap; align-items: center; gap: 14px; margin-bottom: 16px;
    }
    .ItemLedger-filter label { font-size: 13px; font-weight: 600; }
    .ItemLedger-filter select {
      padding: 6px 10px; border-radius: 6px; border: 1px solid #cbd5e1; font-size: 13px;
    }

    .btn {
      display: inline-flex; align-items: center; justify-content: center; gap: 4px;
      padding: 7px 14px; font-size: 13px; font-weight: 600; border-radius: 6px;
      cursor: pointer; border: none; text-decoration: none;
    }
    .btn-primary { background: var(--primary); color: #fff; }
    .btn-primary:hover { background: var(--primary-hover); }
    .btn-muted { background: #64748b; color: #fff; }

    .ItemLedger-panel { background: #fff; border: 1px solid var(--border); border-radius: 8px; overflow: hidden; }
    .ItemLedger-panel-head {
      padding: 10px 14px; background: #f8fafc; border-bottom: 1px solid var(--border);
      display: flex; justify-content: space-between; align-items: center;
    }
    .ItemLedger-panel-head h3 { margin: 0; font-size: 14px; font-weight: 700; }
    .ItemLedger-panel-body { padding: 12px; overflow-x: auto; }

    .ItemLedger-table { width: 100%; border-collapse: collapse; font-size: 12px; min-width: 900px; color: #1e293b; }
    .ItemLedger-table th {
      background: #f8fafc; color: #334155; font-weight: 700; padding: 8px 6px;
      border: 1px solid #c5d0dc; text-align: center; white-space: nowrap;
    }
    .ItemLedger-table td {
      padding: 8px 6px; border: 1px solid var(--border); text-align: center; color: #1e293b;
    }
    .ItemLedger-table tbody tr:nth-child(even) { background: #f8fafc; }
    .ItemLedger-table tbody tr:hover { background: #f1f5f9; }
    .ItemLedger-table tfoot td {
      background: #fef9c3; font-weight: 700; padding: 9px 6px;
      border-top: 2px solid #fde047; color: #1e293b;
    }
    .empty-msg { text-align: center; padding: 40px; color: #94a3b8; font-size: 14px; }
    .ItemLedger-actions { margin-top: 16px; display: flex; justify-content: flex-end; }
  </style>
</head>
<body>

<layout:headerNav />

<div class="ItemLedger-container">
  <div class="ItemLedger-header">
    <h2>項目別台帳</h2>
    <p>期間と項目を選択すると、該当期間の社員別給与項目または控除項目内訳を確認できます。</p>
  </div>

  <form id="ItemLedgerFilterForm" action="${pageContext.request.contextPath}/salary/itemRegister.do" method="get">
    <div class="ItemLedger-filter">
      <label>期間選択</label>
      <select name="startYear">
        <c:forEach var="y" begin="2017" end="2027">
          <option value="${y}" ${y eq itemLedgerData.startYear ? 'selected' : ''}>${y}年</option>
        </c:forEach>
      </select>
      <select name="startMonth">
        <c:forEach var="m" begin="1" end="12">
          <option value="${m}" ${m eq itemLedgerData.startMonth ? 'selected' : ''}>
            <fmt:formatNumber value="${m}" minIntegerDigits="2"/>月
          </option>
        </c:forEach>
      </select>
      <span>~</span>
      <select name="endYear">
        <c:forEach var="y" begin="2017" end="2027">
          <option value="${y}" ${y eq itemLedgerData.endYear ? 'selected' : ''}>${y}年</option>
        </c:forEach>
      </select>
      <select name="endMonth">
        <c:forEach var="m" begin="1" end="12">
          <option value="${m}" ${m eq itemLedgerData.endMonth ? 'selected' : ''}>
            <fmt:formatNumber value="${m}" minIntegerDigits="2"/>月
          </option>
        </c:forEach>
      </select>

      <label>項目選択</label>
      <select name="itemName">
        <option value="基本給" ${itemLedgerData.itemName eq '基本給' ? 'selected' : ''}>基本給</option>
        <option value="食費" ${itemLedgerData.itemName eq '食費' ? 'selected' : ''}>食費</option>
        <option value="国民年金" ${itemLedgerData.itemName eq '国民年金' ? 'selected' : ''}>国民年金</option>
        <option value="健康保険" ${itemLedgerData.itemName eq '健康保険' ? 'selected' : ''}>健康保険</option>
        <option value="介護保険" ${itemLedgerData.itemName eq '介護保険' ? 'selected' : ''}>介護保険</option>
        <option value="雇用保険" ${itemLedgerData.itemName eq '雇用保険' ? 'selected' : ''}>雇用保険</option>
        <option value="所得税" ${itemLedgerData.itemName eq '所得税' ? 'selected' : ''}>所得税</option>
        <option value="住民税" ${itemLedgerData.itemName eq '住民税' ? 'selected' : ''}>住民税</option>
        <option value="共済会費" ${itemLedgerData.itemName eq '共済会費' ? 'selected' : ''}>共済会費</option>
      </select>
      <button type="submit" class="btn btn-primary">照会</button>
    </div>
  </form>

  <div class="ItemLedger-panel">
    <div class="ItemLedger-panel-head">
      <h3>${itemLedgerData.itemName} 内訳</h3>
      <span style="font-size:12px;color:#64748b;">${itemLedgerData.rows.size()}名</span>
    </div>
    <div class="ItemLedger-panel-body">
      <table class="ItemLedger-table">
        <thead>
          <tr>
            <th>区分</th>
            <th>氏名</th>
            <th>部署</th>
            <th>役職</th>
            <c:forEach var="month" items="${itemLedgerData.months}">
              <th>${month.label}</th>
            </c:forEach>
            <th>合計</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="row" items="${itemLedgerData.rows}">
            <tr>
              <td>${row.employmentType}</td>
              <td>${row.nameKor}</td>
              <td>${row.deptName}</td>
              <td>${row.positionName}</td>
              <c:forEach var="amount" items="${row.monthAmounts}">
                <td><fmt:formatNumber value="${amount}" pattern="#,##0"/></td>
              </c:forEach>
              <td><fmt:formatNumber value="${row.total}" pattern="#,##0"/></td>
            </tr>
          </c:forEach>
          <c:if test="${empty itemLedgerData.rows}">
            <tr>
              <td colspan="${4 + itemLedgerData.months.size() + 1}" class="empty-msg">
                該当期間に登録された給与内訳がありません。
              </td>
            </tr>
          </c:if>
        </tbody>
        <c:if test="${not empty itemLedgerData.rows}">
          <tfoot>
            <tr>
              <td>合計</td>
              <td></td>
              <td></td>
              <td></td>
              <c:forEach var="amount" items="${itemLedgerData.summary.monthAmounts}">
                <td><fmt:formatNumber value="${amount}" pattern="#,##0"/></td>
              </c:forEach>
              <td><fmt:formatNumber value="${itemLedgerData.summary.total}" pattern="#,##0"/></td>
            </tr>
          </tfoot>
        </c:if>
      </table>
    </div>
  </div>

  <div class="ItemLedger-actions">
    <a class="btn btn-muted" href="${pageContext.request.contextPath}/salary/ledger.do">給与台帳へ</a>
  </div>
</div>

<layout:footer />

<script>
  <c:if test="${itemLedgerData.periodCapped}">
  alert('照会期間は最大12か月までです。\n開始月から12か月分のみ表示します。');
  </c:if>
</script>

</body>
</html>
