<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>사원현황 / 관리</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
  
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
      max-width: 1300px;
      margin: 0 auto 60px auto;
      padding: 0 20px;
    }

    /* Page Header */
    .page-header {
      margin-bottom: 20px;
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

    /* Summary Cards Bar */
    .status-summary {
      display: flex;
      gap: 8px;
      margin-bottom: 20px;
    }

    .summary-card {
      flex: 1;
      background: #ffffff;
      border: 1px solid var(--border-color);
      border-radius: 8px;
      text-align: center;
      overflow: hidden;
    }

    .summary-card .card-head {
      padding: 8px 4px;
      font-size: 13px;
      font-weight: 600;
      color: #ffffff;
    }

    .summary-card .card-body {
      padding: 10px 0;
      font-size: 20px;
      font-weight: 700;
      color: #334155;
    }

    .card-active .card-head { background-color: #ec4899; }
    .card-type .card-head { background-color: #3b82f6; }
    .card-retired .card-head { background-color: #64748b; }
    .card-total .card-head { background-color: #1e293b; }

    /* Search Bar */
    .search-bar {
      display: flex;
      justify-content: space-between;
      align-items: center;
      background: #ffffff;
      padding: 12px 16px;
      border: 1px solid var(--border-color);
      border-radius: 8px;
      margin-bottom: 16px;
    }

    .search-group {
      display: flex;
      align-items: center;
      gap: 8px;
    }

    input[type="text"], select {
      padding: 7px 10px;
      font-size: 13px;
      border: 1px solid #cbd5e1;
      border-radius: 6px;
      outline: none;
    }

    /* Buttons */
    .btn {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      padding: 7px 14px;
      font-size: 13px;
      font-weight: 600;
      border-radius: 6px;
      cursor: pointer;
      border: none;
      text-decoration: none;
    }

    .btn-primary { background-color: var(--primary-color); color: #fff; }
    .btn-primary:hover { background-color: var(--primary-hover); }
    .btn-outline { background-color: #fff; border: 1px solid #cbd5e1; color: #334155; }

    /* Single Data Table */
    .table-container {
      background: #ffffff;
      border: 1px solid var(--border-color);
      border-radius: 8px;
      overflow-x: auto;
      box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.04);
    }

    .list-table {
      width: 100%;
      border-collapse: collapse;
      font-size: 13px;
      white-space: nowrap;
    }

    .list-table th {
      background-color: #f8fafc;
      color: #475569;
      font-weight: 700;
      padding: 12px 10px;
      border-bottom: 2px solid var(--border-color);
      border-right: 1px solid var(--border-color);
      text-align: center;
    }

    .list-table td {
      padding: 10px 8px;
      border-bottom: 1px solid var(--border-color);
      border-right: 1px solid var(--border-color);
      text-align: center;
      color: #334155;
    }

    .list-table th:last-child, .list-table td:last-child {
      border-right: none;
    }

    .list-table tbody tr:hover {
      background-color: #eff6ff;
      cursor: pointer;
    }

    /* Status Badge */
    .badge {
      display: inline-block;
      padding: 3px 8px;
      font-size: 11px;
      font-weight: 600;
      border-radius: 12px;
    }
    .badge-active { background-color: #dbeafe; color: #1e40af; }
    .badge-retired { background-color: #f1f5f9; color: #64748b; }

    .emp-link {
      color: var(--primary-color);
      font-weight: 600;
      text-decoration: none;
    }
    .emp-link:hover { text-decoration: underline; }
  </style>
</head>
<body>

  <!-- 헤더 태그 -->
  <layout:headerNav />

  <div class="container">
    <div class="page-header">
      <h2>📊 사원현황 / 관리</h2>
      <p>전체 사원 현황을 볼 수 있으며, 선택하면 해당사원 등록(정보) 페이지로 이동합니다.</p>
    </div>

    <!-- 현황 요약 카운트 -->
    <div class="status-summary">
      <div class="summary-card card-active"><div class="card-head">재직자</div><div class="card-body">12</div></div>
      <div class="summary-card card-type"><div class="card-head">정규직</div><div class="card-body">7</div></div>
      <div class="summary-card card-type"><div class="card-head">계약직</div><div class="card-body">1</div></div>
      <div class="summary-card card-type"><div class="card-head">임시직</div><div class="card-body">1</div></div>
      <div class="summary-card card-type"><div class="card-head">파견직</div><div class="card-body">0</div></div>
      <div class="summary-card card-type"><div class="card-head">위촉직</div><div class="card-body">0</div></div>
      <div class="summary-card card-type"><div class="card-head">일용직</div><div class="card-body">3</div></div>
      <div class="summary-card card-retired"><div class="card-head">퇴사자</div><div class="card-body">2</div></div>
      <div class="summary-card card-total"><div class="card-head">전체</div><div class="card-body">14</div></div>
    </div>

    <!-- 검색 영역 -->
    <form action="${pageContext.request.contextPath}/emp/list.do" method="get">
      <div class="search-bar">
        <div class="search-group">
          <select name="searchType">
            <option value="all">전체</option>
            <option value="name_kor">성명</option>
            <option value="emp_no">사원번호</option>
            <option value="department">부서</option>
          </select>
          <input type="text" name="searchKeyword" placeholder="검색어 입력">
          <button type="submit" class="btn btn-primary">🔍 검색</button>
          <a href="${pageContext.request.contextPath}/emp/list.do" class="btn btn-outline">전체보기</a>
        </div>

      </div>
    </form>

    <!-- 단 하나의 사원 목록 표 (c:forEach 적용) -->
    <div class="table-container">
      <table class="list-table">
        <thead>
          <tr>
            <th width="40"><input type="checkbox" id="checkAll"></th>
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
          <!-- DB에서 넘어온 empList 데이터를 반복문으로 출력 -->
          <c:forEach var="emp" items="${empList}">
            <tr onclick="location.href='${pageContext.request.contextPath}/emp/register.do?emp_no=${emp.emp_no}'">
              <td onclick="event.stopPropagation()"><input type="checkbox" class="emp-chk" value="${emp.emp_no}"></td>
              <td>${emp.employment_type}</td>
              <td>${emp.hire_date}</td>
              <td>${emp.emp_no}</td>
              <td>
                <a href="${pageContext.request.contextPath}/emp/register.do?emp_no=${emp.emp_no}" class="emp-link">
                  ${emp.name_kor}
                </a>
              </td>
              <td>${emp.department}</td>
              <td>${emp.position}</td>
              <td>${emp.resident_no}</td>
              <td>${emp.mobile_num}</td>
              <td>${emp.email}</td>
              <td>${empty emp.retire_date ? '-' : emp.retire_date}</td>
              <td>
                <span class="badge ${emp.status eq '퇴직' ? 'badge-retired' : 'badge-active'}">
                  ${empty emp.status ? '재직' : emp.status}
                </span>
              </td>
            </tr>
          </c:forEach>

          <!-- 데이터가 전혀 없을 때 보여줄 기본 안내 -->
          <c:if test="${empty empList}">
            <tr>
              <td colspan="12" style="padding: 30px; color: #94a3b8;">
                등록된 사원 정보가 없습니다.
              </td>
            </tr>
          </c:if>
        </tbody>
      </table>
    </div>
  </div>

  <!-- 푸터 태그 -->
  <layout:footer />

  <script>
    // 전체 선택 Checkbox
    document.getElementById('checkAll').addEventListener('change', function() {
      const checkboxes = document.querySelectorAll('.emp-chk');
      checkboxes.forEach(cb => cb.checked = this.checked);
    });
  </script>

</body>
</html>