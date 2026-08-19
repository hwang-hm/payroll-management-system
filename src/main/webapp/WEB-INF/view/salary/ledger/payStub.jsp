<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>급여명세서</title>
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
    .filter-info strong { color: #fff; }

    .btn {
      display: inline-flex; align-items: center; justify-content: center; gap: 4px;
      padding: 7px 14px; font-size: 13px; font-weight: 600; border-radius: 6px;
      cursor: pointer; border: none; text-decoration: none;
    }
    .btn-primary { background: var(--primary); color: #fff; }
    .btn-primary:hover { background: var(--primary-hover); }
    .btn-outline { background: #fff; border: 1px solid #cbd5e1; color: #334155; }
    .btn-sm { padding: 5px 10px; font-size: 12px; }

    .main-split { display: grid; grid-template-columns: 0.9fr 1.1fr; gap: 16px; align-items: start; }
    .panel { background: #fff; border: 1px solid var(--border); border-radius: 8px; overflow: hidden; }
    .panel-head {
      padding: 10px 14px; background: #f8fafc; border-bottom: 1px solid var(--border);
      display: flex; justify-content: space-between; align-items: center;
    }
    .panel-head h3 { margin: 0; font-size: 14px; font-weight: 700; }
    .panel-body { padding: 12px; }

    .search-bar { display: flex; gap: 6px; margin-bottom: 10px; align-items: center; }
    .search-bar input {
      flex: 1; padding: 7px 10px; border: 1px solid #cbd5e1; border-radius: 6px; font-size: 13px;
    }

    .list-table { width: 100%; border-collapse: collapse; font-size: 13px; }
    .list-table th {
      background: #f8fafc; color: #475569; font-weight: 700; padding: 10px 8px;
      border-bottom: 2px solid var(--border); text-align: center;
    }
    .list-table td {
      padding: 9px 8px; border-bottom: 1px solid var(--border); text-align: center;
    }
    .list-table tbody tr { cursor: pointer; }
    .list-table tbody tr:hover { background: #eff6ff; }
    .list-table tbody tr.selected { background: #dbeafe; outline: 2px solid #93c5fd; outline-offset: -2px; }

    .empty-msg { text-align: center; padding: 40px; color: #94a3b8; font-size: 14px; }

    /* 명세서 미리보기 */
    .payslip-doc {
      border: 1px solid #b8c4d4; border-radius: 4px; padding: 22px 26px 18px; background: #fff;
      min-height: 520px; max-width: 595px; margin: 0 auto;
    }
    .payslip-title {
      text-align: center; font-size: 22px; font-weight: 800; margin: 0 0 20px 0;
      letter-spacing: -0.02em; color: #1e293b;
    }
    .emp-info-table {
      width: 100%; border-collapse: collapse; font-size: 12px; margin-bottom: 14px;
    }
    .emp-info-table th, .emp-info-table td {
      border: 1px solid #b8c4d4; padding: 8px 10px; text-align: center;
    }
    .emp-info-table th { background: #f3f6fa; font-weight: 700; width: 14%; color: #334155; }

    .detail-table { width: 100%; border-collapse: collapse; font-size: 11px; table-layout: fixed; }
    .detail-table th, .detail-table td {
      border: 1px solid #b8c4d4; padding: 4px 6px; vertical-align: middle;
    }
    .detail-table tbody td { height: 16px; line-height: 1.1; }
    .detail-table thead th {
      background: #f3f6fa; font-weight: 700; color: #334155; text-align: center;
      padding: 8px 6px;
      font-size: 12.5px;
    }
    .detail-table .col-section {
      width: 9%; background: #f3f6fa; font-weight: 700; text-align: center;
      writing-mode: vertical-rl; text-orientation: mixed; letter-spacing: 0.08em;
      padding: 4px 2px;
      font-size: 14px;
    }
    .detail-table .col-section.pay { color: #1d4ed8; }
    .detail-table .col-section.deduct { color: #be123c; }
    .detail-table .col-item { width: 26%; text-align: center; }
    /* 산출식(3) : 금액(2) 비율 느낌 */
    .detail-table .col-calc { width: 39%; text-align: center; color: #475569; font-size: 10px; }
    .detail-table .col-amount { width: 26%; text-align: center; font-weight: 600; }
    .detail-table .section-head td {
      background: #eef2f7; font-weight: 700; text-align: center; color: #1e293b;
      padding: 4px 6px;
    }
    .detail-table .section-head.pay td { color: #1d4ed8; }
    .detail-table .section-head.deduct td { color: #be123c; }
    .detail-table .summary-row td {
      background: #fef9c3; font-weight: 700; text-align: center;
      padding: 3px 6px;
    }
    .detail-table .summary-row .col-amount { text-align: center; }
    .detail-table .net-pay-row td {
      background: #f8fafc; font-weight: 800; font-size: 14px;
      padding: 6px 6px;
    }
    .detail-table .net-pay-row .label { text-align: center; color: #1e293b; }
    .detail-table .net-pay-row .col-amount {
      text-align: center; color: #1d4ed8; font-size: 14px;
    }
    .detail-table .empty-row td { text-align: center; color: #94a3b8; padding: 16px; }
    .payslip-footer {
      margin-top: 20px; text-align: center; font-size: 13px; color: #64748b;
    }
  </style>
</head>
<body>

<layout:headerNav />

<div class="container">
  <div class="page-header">
    <h2>급여명세서</h2>
    <p>귀속연월별 사원 급여명세서를 조회할 수 있습니다.</p>
  </div>

  <form id="filterForm" action="${pageContext.request.contextPath}/salary/payslip.do" method="get">
    <input type="hidden" name="searchKeyword" id="hiddenSearchKeyword" value="${payStubData.searchKeyword}">
    <input type="hidden" name="salaryId" id="hiddenSalaryId" value="${payStubData.selectedSalaryId}">
    <div class="filter-bar">
      <label>귀속연월</label>
      <select name="payYear" onchange="submitFilter()">
        <c:forEach var="y" begin="2024" end="2027">
          <option value="${y}" ${y eq payStubData.payYear ? 'selected' : ''}>${y}년</option>
        </c:forEach>
      </select>
      <select name="payMonth" onchange="submitFilter()">
        <c:forEach var="m" begin="1" end="12">
          <option value="${m}" ${m eq payStubData.payMonth ? 'selected' : ''}>${m}월</option>
        </c:forEach>
      </select>
      <label>급여차수</label>
      <select name="paySeq" onchange="submitFilter()">
        <option value="1" ${payStubData.paySeq eq 1 ? 'selected' : ''}>급여-01</option>
        <option value="2" ${payStubData.paySeq eq 2 ? 'selected' : ''}>급여-02</option>
      </select>
      <c:if test="${not empty payStubData.startDate}">
        <span class="filter-info">
          정산기간: <strong>${payStubData.startDate} ~ ${payStubData.endDate}</strong> &nbsp;|&nbsp;
          급여지급일: <strong>${payStubData.payDate}</strong>
        </span>
      </c:if>
    </div>
  </form>

  <div class="main-split">
    <!-- 좌측: 사원 목록 -->
    <div class="panel">
      <div class="panel-head">
        <h3>급여 대상 사원</h3>
        <span style="font-size:12px;color:#64748b;">${payStubData.rows.size()}건</span>
      </div>
      <div class="panel-body">
        <div class="search-bar">
          <input type="text" id="searchKeyword" placeholder="검색어 입력 (구분, 성명)"
                 value="${payStubData.searchKeyword}">
          <button type="button" class="btn btn-primary btn-sm" onclick="searchRows()">검색</button>
          <button type="button" class="btn btn-outline btn-sm" onclick="showAll()">전체보기</button>
        </div>
        <table class="list-table">
          <thead>
            <tr>
              <th>구분</th>
              <th>성명</th>
              <th>실지급액</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="row" items="${payStubData.rows}">
              <tr class="${row.salaryId eq payStubData.selectedSalaryId ? 'selected' : ''}"
                  onclick="selectSalary(${row.salaryId})">
                <td>${row.employmentType}</td>
                <td>${row.nameKor}</td>
                <td><fmt:formatNumber value="${row.netPay}" pattern="#,###"/></td>
              </tr>
            </c:forEach>
            <c:if test="${empty payStubData.rows}">
              <tr><td colspan="3" class="empty-msg">등록된 급여 대상 사원이 없습니다.</td></tr>
            </c:if>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 우측: 급여명세서 미리보기 -->
    <div class="panel">
      <div class="panel-head">
        <h3>급여명세서 미리보기</h3>
      </div>
      <div class="panel-body">
        <c:choose>
          <c:when test="${not empty payStubData.form}">
            <div class="payslip-doc">
              <h3 class="payslip-title">
                ${payStubData.payYear}년 <fmt:formatNumber value="${payStubData.payMonth}" minIntegerDigits="2"/>월 급여명세서
              </h3>

              <table class="emp-info-table">
                <tr>
                  <th>성명</th>
                  <td>${payStubData.form.nameKor}</td>
                  <th>생년월일</th>
                  <td>${payStubData.form.birthDate}</td>
                </tr>
                <tr>
                  <th>부서</th>
                  <td>${payStubData.form.deptName}</td>
                  <th>직급</th>
                  <td>${payStubData.form.positionName}</td>
                </tr>
                <tr>
                  <th>입사일</th>
                  <td>${payStubData.form.hireDate}</td>
                  <th>급여지급일</th>
                  <td>${payStubData.form.payDate}</td>
                </tr>
              </table>

              <table class="detail-table">
                <thead>
                  <tr>
                    <th class="col-section"></th>
                    <th class="col-item">항목명</th>
                    <th class="col-amount">금액</th>
                    <th class="col-calc">산출식 또는 산출방법</th>
                  </tr>
                </thead>
                <tbody>
                  <c:set var="payRowCount" value="${payStubData.form.paySectionLines.size() + 1}"/>
                  <c:forEach var="line" items="${payStubData.form.paySectionLines}" varStatus="paySt">
                    <tr>
                      <c:if test="${paySt.first}">
                        <td rowspan="${payRowCount}" class="col-section pay">지급항목</td>
                      </c:if>
                      <td class="col-item">${line.payItemName}</td>
                      <td class="col-amount">
                        <c:if test="${line.payAmount != null}">
                          <fmt:formatNumber value="${line.payAmount}" pattern="#,###"/>
                        </c:if>
                      </td>
                      <td class="col-calc">${line.payCalcMethod}</td>
                    </tr>
                  </c:forEach>
                  <tr class="summary-row">
                    <td class="col-item">합계</td>
                    <td class="col-amount"><fmt:formatNumber value="${payStubData.form.totalPay}" pattern="#,###"/> 원</td>
                    <td class="col-calc"></td>
                  </tr>

                  <c:set var="deductRowCount" value="${payStubData.form.deductSectionLines.size() + 1}"/>
                  <c:forEach var="line" items="${payStubData.form.deductSectionLines}" varStatus="deductSt">
                    <tr>
                      <c:if test="${deductSt.first}">
                        <td rowspan="${deductRowCount}" class="col-section deduct">공제항목</td>
                      </c:if>
                      <td class="col-item">${line.deductItemName}</td>
                      <td class="col-amount">
                        <c:if test="${line.deductAmount != null}">
                          <fmt:formatNumber value="${line.deductAmount}" pattern="#,###"/>
                        </c:if>
                      </td>
                      <td class="col-calc">${line.payCalcMethod}</td>
                    </tr>
                  </c:forEach>
                  <tr class="summary-row">
                    <td class="col-item">합계</td>
                    <td class="col-amount"><fmt:formatNumber value="${payStubData.form.totalDeduct}" pattern="#,###"/> 원</td>
                    <td class="col-calc"></td>
                  </tr>

                  <tr class="net-pay-row">
                    <td class="col-section"></td>
                    <td class="col-item">실수령액</td>
                    <td class="col-amount"><fmt:formatNumber value="${payStubData.form.netPay}" pattern="#,###"/> 원</td>
                    <td class="col-calc"></td>
                  </tr>
                </tbody>
              </table>

              <p class="payslip-footer">귀하의 노고에 감사드리며, 수고 많으셨습니다.</p>
            </div>
          </c:when>
          <c:otherwise>
            <div class="empty-msg">좌측에서 사원을 선택하면 급여명세서가 표시됩니다.</div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</div>

<layout:footer />

<script>
  const ctx = '${pageContext.request.contextPath}';
  const payYear = ${payStubData.payYear};
  const payMonth = ${payStubData.payMonth};
  const paySeq = ${payStubData.paySeq};

  function buildUrl(salaryId, searchKeyword) {
    let url = ctx + '/salary/payslip.do?payYear=' + payYear + '&payMonth=' + payMonth + '&paySeq=' + paySeq;
    if (salaryId) {
      url += '&salaryId=' + salaryId;
    }
    if (searchKeyword) {
      url += '&searchKeyword=' + encodeURIComponent(searchKeyword);
    }
    return url;
  }

  function submitFilter() {
    document.getElementById('hiddenSearchKeyword').value = document.getElementById('searchKeyword').value.trim();
    document.getElementById('hiddenSalaryId').value = '';
    document.getElementById('filterForm').submit();
  }

  function selectSalary(salaryId) {
    location.href = buildUrl(salaryId, document.getElementById('searchKeyword').value.trim());
  }

  function searchRows() {
    location.href = buildUrl('', document.getElementById('searchKeyword').value.trim());
  }

  function showAll() {
    document.getElementById('searchKeyword').value = '';
    location.href = buildUrl('', '');
  }

  document.getElementById('searchKeyword')?.addEventListener('keydown', function(e) {
    if (e.key === 'Enter') {
      e.preventDefault();
      searchRows();
    }
  });
</script>
</body>
</html>
