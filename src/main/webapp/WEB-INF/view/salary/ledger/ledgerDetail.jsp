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
      --bg-gray: #f8fafc;
      --border: #d0d7e2;
      --text-main: #1e293b;
      --text-muted: #64748b;
    }
    body { font-family: 'Pretendard Variable', Pretendard, sans-serif; background: var(--bg-gray); color: var(--text-main); margin: 0; }
    .LedgerDetail-container { max-width: 1600px; margin: 0 auto 60px; padding: 0 20px; }
    .LedgerDetail-header { margin-bottom: 16px; border-bottom: 2px solid #e2e8f0; padding-bottom: 14px; }
    .LedgerDetail-header h2 { font-size: 24px; font-weight: 700; margin: 0 0 4px 0; }
    .LedgerDetail-header p { margin: 0; font-size: 13px; color: var(--text-muted); }

    .LedgerDetail-info {
      background: #f1f5f9; border: 1px solid var(--border); border-radius: 8px;
      padding: 12px 16px; margin-bottom: 16px;
      display: flex; flex-wrap: wrap; gap: 8px 28px; font-size: 13px;
    }
    .LedgerDetail-info .item { display: flex; gap: 8px; align-items: center; }
    .LedgerDetail-info .label { color: var(--text-muted); font-weight: 600; }
    .LedgerDetail-info .value { font-weight: 700; }

    .btn {
      display: inline-flex; align-items: center; justify-content: center; gap: 4px;
      padding: 7px 14px; font-size: 13px; font-weight: 600; border-radius: 6px;
      cursor: pointer; border: none; text-decoration: none;
    }
    .btn-muted { background: #64748b; color: #fff; }

    .LedgerDetail-panel { background: #fff; border: 1px solid var(--border); border-radius: 8px; overflow: hidden; }
    .LedgerDetail-panel-body { padding: 12px; overflow-x: auto; }

    .LedgerDetail-table { width: 100%; border-collapse: collapse; table-layout: fixed; font-size: 12px; min-width: 1280px; color: #1e293b; }
    .LedgerDetail-table th {
      font-weight: 700; padding: 9px 6px;
      border: 1px solid #c5d0dc; text-align: center; white-space: nowrap;
    }
    .LedgerDetail-table th.col-info {
      background: #94a3b8; color: #fff; border-color: #7d8ea3;
    }
    .LedgerDetail-table th.col-item {
      background: #5b7c99; color: #fff; border-color: #4a6780;
    }
    .LedgerDetail-table th.col-total { background: #2563eb; color: #fff; border-color: #1d4ed8; }
    .LedgerDetail-table td {
      padding: 8px 6px; border: 1px solid #c5d0dc; text-align: center; color: #1e293b; white-space: nowrap;
    }
    .LedgerDetail-table td.num { text-align: right; font-variant-numeric: tabular-nums; }
    .LedgerDetail-table td.total { text-align: right; font-weight: 700; color: #1d4ed8; }
    .LedgerDetail-table tbody tr:nth-child(even) { background: #f3f7fb; }
    .LedgerDetail-table tbody tr:hover { background: #e8eef5; }
    .LedgerDetail-table tfoot td {
      background: #fff7cc; font-weight: 700; padding: 9px 6px;
      border: 1px solid #e6d98c; color: #1e293b;
    }
    .empty-msg { text-align: center; padding: 40px; color: #94a3b8; font-size: 14px; }
    .LedgerDetail-actions { margin-top: 16px; display: flex; justify-content: center; }
  </style>
</head>
<body>

<layout:headerNav />

<div class="LedgerDetail-container">
  <div class="LedgerDetail-header">
    <h2>給与台帳</h2>
    <p>帰属年月・回数別の社員給与内訳です。支給9項目は固定表示します。</p>
  </div>

  <div class="LedgerDetail-info">
    <div class="item">
      <span class="label">帰属年月</span>
      <span class="value">
        <c:choose>
          <c:when test="${detailData.payYear > 0}">
            ${detailData.payYear}年 <fmt:formatNumber value="${detailData.payMonth}" minIntegerDigits="2"/>月
          </c:when>
          <c:otherwise>-</c:otherwise>
        </c:choose>
      </span>
    </div>
    <div class="item">
      <span class="label">給与回数</span>
      <span class="value">
        <c:choose>
          <c:when test="${detailData.payYear > 0}">
            給与-<fmt:formatNumber value="${detailData.paySeq}" minIntegerDigits="2"/>回
          </c:when>
          <c:otherwise>-</c:otherwise>
        </c:choose>
      </span>
    </div>
    <div class="item">
      <span class="label">精算期間</span>
      <span class="value">
        <c:choose>
          <c:when test="${not empty detailData.startDate}">${detailData.startDate} ~ ${detailData.endDate}</c:when>
          <c:otherwise>-</c:otherwise>
        </c:choose>
      </span>
    </div>
    <div class="item">
      <span class="label">支給日</span>
      <span class="value">${not empty detailData.payDate ? detailData.payDate : '-'}</span>
    </div>
  </div>

  <div class="LedgerDetail-panel">
    <div class="LedgerDetail-panel-body">
      <table class="LedgerDetail-table">
        <colgroup>
          <col style="width:7%">
          <col style="width:7%">
          <col style="width:8%">
          <col style="width:8%">
          <col style="width:6%">
          <col span="9" style="width:6%">
          <col style="width:8%">
        </colgroup>
        <thead>
          <tr>
            <th class="col-info">区分</th>
            <th class="col-info">氏名</th>
            <th class="col-info">入社日</th>
            <th class="col-info">部署</th>
            <th class="col-info">職位</th>
            <th class="col-item">基本給</th>
            <th class="col-item">食費</th>
            <th class="col-item">保育手当</th>
            <th class="col-item">役職手当</th>
            <th class="col-item">車両維持費</th>
            <th class="col-item">勤続手当</th>
            <th class="col-item">当直手当</th>
            <th class="col-item">賞与</th>
            <th class="col-item">休日手当</th>
            <th class="col-total">支給総額</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="row" items="${detailData.rows}">
            <tr>
              <td>${row.employmentType}</td>
              <td>${row.nameKor}</td>
              <td>${row.hireDate}</td>
              <td>${row.deptName}</td>
              <td>${row.positionName}</td>
              <td class="num"><fmt:formatNumber value="${row.basicPay}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${row.mealAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${row.childcareAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${row.positionAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${row.carAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${row.seniorityAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${row.nightDutyAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${row.bonus}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${row.holidayAllowance}" pattern="#,##0"/></td>
              <td class="total"><fmt:formatNumber value="${row.totalPay}" pattern="#,##0"/></td>
            </tr>
          </c:forEach>
          <c:if test="${empty detailData.rows}">
            <tr>
              <td colspan="15" class="empty-msg">該当する給与台帳データがありません。</td>
            </tr>
          </c:if>
        </tbody>
        <c:if test="${not empty detailData.rows}">
          <tfoot>
            <tr>
              <td></td>
              <td>合計</td>
              <td></td>
              <td></td>
              <td></td>
              <td class="num"><fmt:formatNumber value="${detailData.summary.basicPay}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${detailData.summary.mealAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${detailData.summary.childcareAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${detailData.summary.positionAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${detailData.summary.carAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${detailData.summary.seniorityAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${detailData.summary.nightDutyAllowance}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${detailData.summary.bonus}" pattern="#,##0"/></td>
              <td class="num"><fmt:formatNumber value="${detailData.summary.holidayAllowance}" pattern="#,##0"/></td>
              <td class="total"><fmt:formatNumber value="${detailData.summary.totalPay}" pattern="#,##0"/></td>
            </tr>
          </tfoot>
        </c:if>
      </table>
    </div>
  </div>

  <div class="LedgerDetail-actions">
    <a class="btn btn-muted"
       href="${pageContext.request.contextPath}/salary/ledger.do?payYear=${detailData.payYear > 0 ? detailData.payYear : ''}">
      全件一覧を見る
    </a>
  </div>
</div>

<layout:footer />

</body>
</html>
