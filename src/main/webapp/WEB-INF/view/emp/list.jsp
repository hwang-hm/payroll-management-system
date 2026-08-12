<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>사원현황/관리 - 헥사곤존</title>

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />

<style>
:root {
  --primary-color: #2563eb;
  --primary-hover: #1d4ed8;
  --bg-gray: #f8fafc;
  --border-color: #e2e8f0;
  --text-main: #1e293b;
  --text-muted: #64748b;
}

body {
  font-family: 'Pretendard Variable', Pretendard, sans-serif;
  background-color: var(--bg-gray);
  color: var(--text-main);
  margin: 0;
  padding: 0;
}

.container {
  max-width: 1450px;
  margin: 0 auto 60px auto;
  padding: 0 20px;
}

.page-header {
  margin-bottom: 25px;
  border-bottom: 2px solid var(--border-color);
  padding-bottom: 16px;
}

.page-header h2 {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-main);
  margin: 0 0 6px 0;
}

.page-header p {
  margin: 0;
  font-size: 13px;
  color: var(--text-muted);
}

.status-summary {
  display: grid;
  grid-template-columns: 1.1fr repeat(6, 1fr) 1.1fr 1.1fr;
  gap: 15px;
  margin-bottom: 30px;
}

.summary-card {
  background-color: #ffffff;
  text-align: center;
  border: 1px solid var(--border-color);
  overflow: hidden;
}

.card-head {
  padding: 15px 5px;
  font-size: 14px;
  font-weight: 700;
}

.card-body {
  padding: 14px 5px;
  font-size: 28px;
  font-weight: 500;
  color: #475569;
  background-color: #f8fafc;
}

.card-active .card-head {
  background-color: #ec5a86;
  color: #ffffff;
}

.card-type .card-head {
  background-color: #69afe0;
  color: #ffffff;
}

.card-retired .card-head {
  background-color: #a3a3a3;
  color: #ffffff;
}

.card-total .card-head {
  background-color: #3f3f3f;
  color: #ffffff;
}

.search-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.search-group {
  display: flex;
  align-items: center;
  gap: 7px;
}

.search-group select,
.search-group input[type="text"] {
  height: 35px;
  padding: 0 10px;
  border: 1px solid #cbd5e1;
  background-color: #ffffff;
  font-size: 13px;
  box-sizing: border-box;
  outline: none;
}

.search-group select {
  width: 120px;
}

.search-group input[type="text"] {
  width: 220px;
}

.search-group select:focus,
.search-group input[type="text"]:focus {
  border-color: var(--primary-color);
}

.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 7px 14px;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  border: none;
  text-decoration: none;
  box-sizing: border-box;
}

.btn-primary {
  background-color: var(--primary-color);
  color: #ffffff;
}

.btn-primary:hover {
  background-color: var(--primary-hover);
}

.btn-outline {
  background-color: #ffffff;
  border: 1px solid #cbd5e1;
  color: #334155;
}

.btn-outline:hover {
  background-color: #f1f5f9;
}

.table-container {
  width: 100%;
  overflow-x: auto;
  background-color: #ffffff;
  border-top: 2px solid #3b82f6;
}

.list-table {
  width: 100%;
  min-width: 1350px;
  border-collapse: collapse;
  font-size: 13px;
  text-align: center;
}

.list-table th {
  background-color: #f8fafc;
  color: #1e293b;
  padding: 12px 8px;
  font-weight: 700;
  border-right: 1px solid #dbe2ea;
  border-bottom: 1px solid #dbe2ea;
  white-space: nowrap;
}

.list-table td {
  padding: 12px 8px;
  color: #64748b;
  border-right: 1px solid #e5e7eb;
  border-bottom: 1px solid #e5e7eb;
  background-color: #ffffff;
  white-space: nowrap;
}

.list-table th:last-child,
.list-table td:last-child {
  border-right: none;
}

.list-table tbody tr {
  cursor: pointer;
}

.list-table tbody tr:hover td {
  background-color: #e6f7fb;
}

.list-table tbody tr.selected td {
  background-color: #d9f3f8;
}

.emp-link {
  color: #475569;
  text-decoration: none;
}

.emp-link:hover {
  color: var(--primary-color);
  text-decoration: underline;
}

.badge {
  display: inline-block;
  min-width: 40px;
}

.badge-active {
  color: #475569;
}

.badge-retired {
  color: #ef4444;
}

.empty-row {
  padding: 35px !important;
  color: #94a3b8 !important;
  cursor: default;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 28px;
}

.pagination a {
  color: #475569;
  text-decoration: none;
  font-size: 13px;
}

.pagination a:hover {
  color: var(--primary-color);
}

.current-page {
  min-width: 32px;
  height: 32px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #f97316;
  color: #f97316;
  background-color: #ffffff;
}

.bottom-actions {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-top: 35px;
}

.bottom-actions .btn {
  min-width: 150px;
  height: 55px;
  padding: 0 25px;
  border-radius: 6px;
  font-size: 16px;
}

.btn-register {
  background-color: #3f82cc;
  color: #ffffff;
}

.btn-register:hover {
  background-color: #3473b9;
}

.btn-delete {
  background-color: #a3a3a3;
  color: #ffffff;
}

.btn-delete:hover {
  background-color: #8d8d8d;
}

.btn-excel {
  background-color: #08a800;
  color: #ffffff;
}

.btn-excel:hover {
  background-color: #078f00;
}
</style>
</head>

<body>

<layout:headerNav />

<div class="container">

  <div class="page-header">
    <h2>👔 사원현황/관리</h2>
    <p>전체사원 현황을 보실 수 있으며, 선택하면 해당사원 등록(정보) 페이지로 이동합니다.</p>
  </div>

  <div class="status-summary">

    <div class="summary-card card-active">
      <div class="card-head">재직자</div>
      <div class="card-body">${empListPage.activeCount}</div>
    </div>

    <div class="summary-card card-type">
      <div class="card-head">정규직</div>
      <div class="card-body">${empListPage.regularCount}</div>
    </div>

    <div class="summary-card card-type">
      <div class="card-head">계약직</div>
      <div class="card-body">${empListPage.contractCount}</div>
    </div>

    <div class="summary-card card-type">
      <div class="card-head">임시직</div>
      <div class="card-body">${empListPage.temporaryCount}</div>
    </div>

    <div class="summary-card card-type">
      <div class="card-head">파견직</div>
      <div class="card-body">${empListPage.dispatchedCount}</div>
    </div>

    <div class="summary-card card-type">
      <div class="card-head">위촉직</div>
      <div class="card-body">${empListPage.commissionedCount}</div>
    </div>

    <div class="summary-card card-type">
      <div class="card-head">일용직</div>
      <div class="card-body">${empListPage.dailyCount}</div>
    </div>

    <div class="summary-card card-retired">
      <div class="card-head">퇴사자</div>
      <div class="card-body">${empListPage.retiredCount}</div>
    </div>

    <div class="summary-card card-total">
      <div class="card-head">전체</div>
      <div class="card-body">${empListPage.total}</div>
    </div>

  </div>

  <form action="${pageContext.request.contextPath}/emp/list.do"
        method="get">

    <div class="search-bar">

      <div class="search-group">

        <select name="searchType">

          <option value="all"
            ${param.searchType == 'all' ? 'selected' : ''}>
            전체
          </option>

          <option value="name"
            ${param.searchType == 'name' ? 'selected' : ''}>
            성명
          </option>

          <option value="empId"
            ${param.searchType == 'empId' ? 'selected' : ''}>
            사원번호
          </option>

          <option value="dept"
            ${param.searchType == 'dept' ? 'selected' : ''}>
            부서
          </option>

        </select>

        <input type="text"
               name="searchKeyword"
               value="${param.searchKeyword}"
               placeholder="검색어 입력">

        <button type="submit"
                class="btn btn-primary">
          🔍 검색
        </button>

        <a href="${pageContext.request.contextPath}/emp/list.do"
           class="btn btn-outline">
          전체보기
        </a>

      </div>

    </div>

  </form>

  <div class="table-container">

    <table class="list-table">

      <thead>

        <tr>

          <th width="40">
            <input type="checkbox" id="checkAll">
          </th>

          <th>구분</th>
          <th>입사일</th>
          <th>사원번호</th>
          <th>성명(한글)</th>
          <th>부서</th>
          <th>직위</th>
          <th>주민번호</th>
          <th>휴대폰</th>
          <th>이메일</th>
          <th>퇴사일</th>
          <th>상태</th>

        </tr>

      </thead>

      <tbody>

        <c:forEach var="emp"
                   items="${empListPage.empListData}">

          <tr onclick="location.href='${pageContext.request.contextPath}/emp/register.do?empId=${emp.empId}'">

            <td onclick="event.stopPropagation()">

              <input type="checkbox"
                     class="emp-chk"
                     value="${emp.empId}">

            </td>

            <td>
              ${emp.employmentType}
            </td>

            <td>
              ${emp.hireDate}
            </td>

            <td>
              ${emp.empId}
            </td>

            <td>

              <a href="${pageContext.request.contextPath}/emp/register.do?empId=${emp.empId}"
                 class="emp-link"
                 onclick="event.stopPropagation()">

                ${emp.nameKor}

              </a>

            </td>

            <td>
              ${empty emp.deptName ? '-' : emp.deptName}
            </td>

            <td>
              ${empty emp.positionName ? '-' : emp.positionName}
            </td>

            <td>
              ${empty emp.residentNo ? '-' : emp.residentNo}
            </td>

            <td>
              ${empty emp.mobileNum ? '-' : emp.mobileNum}
            </td>

            <td>
              ${empty emp.email ? '-' : emp.email}
            </td>

            <td>
              ${empty emp.retireDate ? '-' : emp.retireDate}
            </td>

            <td>

              <c:choose>

                <c:when test="${emp.status eq 'N'}">
                  <span class="badge badge-retired">
                    퇴사
                  </span>
                </c:when>

                <c:otherwise>
                  <span class="badge badge-active">
                    재직
                  </span>
                </c:otherwise>

              </c:choose>

            </td>

          </tr>

        </c:forEach>

        <c:if test="${empty empListPage.empListData}">

          <tr>

            <td colspan="12"
                class="empty-row">
              등록된 사원 정보가 없습니다.
            </td>

          </tr>

        </c:if>

      </tbody>

    </table>

  </div>

  <c:if test="${empListPage.totalPage > 0}">

    <div class="pagination">

      <c:if test="${empListPage.currentPage > 1}">

        <a href="${pageContext.request.contextPath}/emp/list.do?pageNo=${empListPage.currentPage - 1}">
          ‹ 이전페이지
        </a>

      </c:if>

      <c:forEach var="pageNo"
                 begin="${empListPage.startPage}"
                 end="${empListPage.endPage}">

        <c:choose>

          <c:when test="${pageNo == empListPage.currentPage}">

            <span class="current-page">
              ${pageNo}
            </span>

          </c:when>

          <c:otherwise>

            <a href="${pageContext.request.contextPath}/emp/list.do?pageNo=${pageNo}">
              ${pageNo}
            </a>

          </c:otherwise>

        </c:choose>

      </c:forEach>

      <c:if test="${empListPage.currentPage < empListPage.totalPage}">

        <a href="${pageContext.request.contextPath}/emp/list.do?pageNo=${empListPage.currentPage + 1}">
          다음페이지 ›
        </a>

      </c:if>

    </div>

  </c:if>

  <div class="bottom-actions">

    <a href="${pageContext.request.contextPath}/emp/register.do"
       class="btn btn-register">
      신규사원 등록
    </a>

    <button type="button"
            class="btn btn-delete">
      선택 삭제
    </button>

    <button type="button"
            class="btn btn-excel">
      엑셀 다운로드
    </button>

  </div>

</div>

<layout:footer />

<script>
const checkAll = document.getElementById('checkAll');
const empChecks = document.querySelectorAll('.emp-chk');

if (checkAll) {

  checkAll.addEventListener('change', function() {

    empChecks.forEach(function(check) {

      check.checked = checkAll.checked;
      changeRowColor(check);

    });

  });

}

empChecks.forEach(function(check) {

  check.addEventListener('change', function() {

    changeRowColor(check);

  });

});

function changeRowColor(check) {

  const row = check.closest('tr');

  if (check.checked) {

    row.classList.add('selected');

  } else {

    row.classList.remove('selected');

  }

}
</script>

</body>
</html>