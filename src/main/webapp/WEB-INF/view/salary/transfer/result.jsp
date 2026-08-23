<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>給与振込結果照会</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
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
    .container { max-width: 1480px; margin: 0 auto 60px; padding: 0 20px; }
    .page-header { margin-bottom: 16px; border-bottom: 2px solid var(--border); padding-bottom: 14px; }
    .page-header h2 {
      font-size: 24px; font-weight: 700; margin: 0 0 4px 0;
      display: flex; align-items: center; gap: 8px;
    }
    .page-header p { margin: 0; font-size: 13px; color: var(--text-muted); }
    .header-icon { width: 22px; height: 22px; color: #334155; }

    .filter-bar {
      background: #2b3648; color: #fff; border-radius: 8px; padding: 14px 18px;
      display: flex; flex-wrap: wrap; align-items: center; gap: 10px; margin-bottom: 16px;
    }
    .filter-bar label { font-size: 13px; font-weight: 600; }
    .required { color: #f87171; margin-right: 2px; }
    .date-field {
      position: relative; display: inline-flex; align-items: center;
    }
    .date-field input[type="text"] {
      width: 128px; padding: 6px 32px 6px 10px; border-radius: 6px;
      border: 1px solid #cbd5e1; font-size: 13px; box-sizing: border-box;
    }
    .date-field input[type="date"] {
      position: absolute; right: 4px; top: 50%; transform: translateY(-50%);
      width: 22px; height: 22px; padding: 0; border: 0; background: transparent;
      color: transparent; cursor: pointer;
    }
    .date-field input[type="date"]::-webkit-calendar-picker-indicator {
      width: 22px; height: 22px; cursor: pointer; opacity: 0.7;
    }
    .range-sep { color: #cbd5e1; }

    .btn {
      display: inline-flex; align-items: center; justify-content: center; gap: 4px;
      padding: 7px 12px; font-size: 13px; font-weight: 600; border-radius: 6px;
      cursor: pointer; border: none; text-decoration: none;
    }
    .btn-primary { background: var(--primary); color: #fff; min-width: 38px; height: 34px; }
    .btn-primary:hover { background: var(--primary-hover); }
    .search-icon { width: 16px; height: 16px; }

    .panel { background: #fff; border: 1px solid var(--border); border-radius: 8px; overflow: hidden; }
    .list-table { width: 100%; border-collapse: collapse; font-size: 13px; }
    .list-table th {
      background: #f8fafc; color: #475569; font-weight: 700; padding: 10px 8px;
      border-bottom: 2px solid #2563eb; text-align: center; white-space: nowrap;
    }
    .list-table td {
      padding: 9px 8px; border-bottom: 1px solid var(--border); text-align: center; white-space: nowrap;
    }
    .list-table tbody tr:hover { background: #eff6ff; }
    .empty-msg { text-align: center; padding: 40px; color: #94a3b8; font-size: 14px; }
    .amount { font-weight: 600; }
    .result-ok { color: #2563eb; font-weight: 700; }
    .result-fail { color: #e11d48; font-weight: 700; }

    .pagination {
      display: flex; justify-content: center; align-items: center; gap: 10px; margin-top: 28px;
    }
    .pagination a, .pagination span {
      color: #475569; text-decoration: none; font-size: 13px;
    }
    .pagination a:hover { color: var(--primary); }
    .page-disabled { color: #94a3b8; cursor: default; }
    .current-page {
      min-width: 32px; height: 32px; display: inline-flex; align-items: center; justify-content: center;
      border: 1px solid #e11d48; color: #e11d48; background-color: #ffffff;
    }
  </style>
</head>
<body>

<layout:headerNav />

<fmt:formatDate value="${resultPage.startDate}" pattern="yyyy-MM-dd" var="startDateText"/>
<fmt:formatDate value="${resultPage.endDate}" pattern="yyyy-MM-dd" var="endDateText"/>

<div class="container">
  <div class="page-header">
    <h2>
      <svg class="header-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <path d="M7 3h8l4 4v14a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1z"/>
        <path d="M15 3v5h5"/>
        <path d="M8 13h8M8 17h5"/>
      </svg>
      給与振込結果照会
    </h2>
    <p>KB国民銀行による給与振込結果を照会できます。最大1年まで照会可能です。</p>
  </div>

  <form id="filterForm" action="${pageContext.request.contextPath}/salary/transfer/result.do" method="get">
    <input type="hidden" name="pageNo" value="1"/>
    <div class="filter-bar">
      <label><span class="required">*</span> 照会期間</label>
      <span class="date-field">
        <input type="text" name="startDate" value="${startDateText}" placeholder="yyyy-MM-dd"
               maxlength="10" pattern="\d{4}-\d{2}-\d{2}" required/>
        <input type="date" value="${startDateText}" tabindex="-1" aria-hidden="true"
               onchange="this.previousElementSibling.value = this.value"/>
      </span>
      <span class="range-sep">~</span>
      <span class="date-field">
        <input type="text" name="endDate" value="${endDateText}" placeholder="yyyy-MM-dd"
               maxlength="10" pattern="\d{4}-\d{2}-\d{2}" required/>
        <input type="date" value="${endDateText}" tabindex="-1" aria-hidden="true"
               onchange="this.previousElementSibling.value = this.value"/>
      </span>
      <button type="submit" class="btn btn-primary" title="照会">
        <svg class="search-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.4">
          <circle cx="11" cy="11" r="7"/>
          <path d="M20 20l-3.5-3.5"/>
        </svg>
      </button>
    </div>
  </form>

  <div class="panel">
    <table class="list-table">
      <thead>
        <tr>
          <th>振込日</th>
          <th>出金銀行</th>
          <th>出金口座</th>
          <th>入金銀行</th>
          <th>入金口座</th>
          <th>口座名義</th>
          <th>振込金額</th>
          <th>受取人通帳表示</th>
          <th>処理結果</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="row" items="${resultPage.content}">
          <tr>
            <td><fmt:formatDate value="${row.transferDate}" pattern="yyyy-MM-dd"/></td>
            <td>${row.withdrawBank}</td>
            <td>${row.withdrawAccount}</td>
            <td>${row.depositBank}</td>
            <td>${row.depositAccount}</td>
            <td>${row.depositorName}</td>
            <td class="amount"><fmt:formatNumber value="${row.transferAmount}" pattern="#,###"/></td>
            <td>${row.depositMemo}</td>
            <td>
              <c:choose>
                <c:when test="${row.transferResult eq '成功'}">
                  <span class="result-ok">${row.transferResult}</span>
                </c:when>
                <c:otherwise>
                  <span class="result-fail">${row.transferResult}</span>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty resultPage.content}">
          <tr>
            <td colspan="9" class="empty-msg">給与振込データがありません。</td>
          </tr>
        </c:if>
      </tbody>
    </table>
  </div>

  <div class="pagination">
    <c:choose>
      <c:when test="${resultPage.currentPage > 1}">
        <a href="${pageContext.request.contextPath}/salary/transfer/result.do?pageNo=${resultPage.currentPage - 1}&startDate=${startDateText}&endDate=${endDateText}">
          ‹ 前のページ
        </a>
      </c:when>
      <c:otherwise>
        <span class="page-disabled">‹ 前のページ</span>
      </c:otherwise>
    </c:choose>

    <c:choose>
      <c:when test="${resultPage.totalPage > 0}">
        <c:forEach var="pageNo" begin="${resultPage.startPage}" end="${resultPage.endPage}">
          <c:choose>
            <c:when test="${pageNo == resultPage.currentPage}">
              <span class="current-page">${pageNo}</span>
            </c:when>
            <c:otherwise>
              <a href="${pageContext.request.contextPath}/salary/transfer/result.do?pageNo=${pageNo}&startDate=${startDateText}&endDate=${endDateText}">
                ${pageNo}
              </a>
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <span class="current-page">1</span>
      </c:otherwise>
    </c:choose>

    <c:choose>
      <c:when test="${resultPage.currentPage < resultPage.totalPage}">
        <a href="${pageContext.request.contextPath}/salary/transfer/result.do?pageNo=${resultPage.currentPage + 1}&startDate=${startDateText}&endDate=${endDateText}">
          次のページ ›
        </a>
      </c:when>
      <c:otherwise>
        <span class="page-disabled">次のページ ›</span>
      </c:otherwise>
    </c:choose>
  </div>
</div>

<layout:footer />
<script>
  (function () {
    var form = document.getElementById('filterForm');
    form.addEventListener('submit', function (e) {
      var startValue = form.startDate.value;
      var endValue = form.endDate.value;
      if (!startValue || !endValue) {
        return;
      }
      var start = new Date(startValue);
      var end = new Date(endValue);
      if (start > end) {
        alert('開始日は終了日より後にできません。');
        e.preventDefault();
        return;
      }
      var diffDays = (end - start) / (1000 * 60 * 60 * 24);
      if (diffDays > 365) {
        alert('照会期間は最大1年まで可能です。');
        e.preventDefault();
      }
    });
  })();
</script>
</body>
</html>
