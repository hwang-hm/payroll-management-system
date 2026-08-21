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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css"/>

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

          <tr onclick="location.href='${pageContext.request.contextPath}/emp/modify.do?empId=${emp.empId}'">

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

              <a href="${pageContext.request.contextPath}/emp/modify.do?empId=${emp.empId}"
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