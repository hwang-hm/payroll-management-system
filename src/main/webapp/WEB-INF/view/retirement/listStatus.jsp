<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">

<title>사원 퇴직 관리 - 헥사곤존</title>

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
    --danger-color: #ef4444;
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


/* =========================
   페이지 제목
========================= */

.page-header {
    margin-bottom: 24px;
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


/* =========================
   메인 카드
========================= */

.list-section {
    background: #ffffff;
    border: 1px solid var(--border-color);
    border-radius: 12px;
    padding: 24px;

    box-shadow:
        0 4px 6px -1px rgba(0, 0, 0, 0.05);
}


/* =========================
   검색 영역
========================= */

.search-area {
    display: flex;
    justify-content: space-between;
    align-items: center;

    margin-bottom: 18px;
    gap: 12px;
}

.search-left {
    display: flex;
    align-items: center;
    gap: 8px;
}

.search-right {
    display: flex;
    align-items: center;
    gap: 8px;
}


.search-area select,
.search-area input[type="text"] {

    height: 36px;
    padding: 7px 10px;
    font-size: 13px;

    border: 1px solid #cbd5e1;
    border-radius: 6px;

    box-sizing: border-box;
    outline: none;

    background-color: #ffffff;
}


.search-area select:focus,
.search-area input[type="text"]:focus {

    border-color: var(--primary-color);

    box-shadow:
        0 0 0 3px rgba(37, 99, 235, 0.1);
}


.search-type {
    width: 120px;
}

.search-input {
    width: 260px;
}

.status-select {
    width: 120px;
}


/* =========================
   버튼
========================= */

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

    transition: all 0.2s ease;

    text-decoration: none;
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


.btn-search {

    width: 38px;
    height: 36px;

    padding: 0;

    font-size: 15px;
}


/* =========================
   테이블
========================= */

.table-wrap {
    width: 100%;
    overflow-x: auto;
}


.emp-table {

    width: 100%;

    border-collapse: collapse;

    font-size: 13px;

    text-align: center;

    table-layout: fixed;
}


.emp-table th {

    background-color: #f1f5f9;

    padding: 12px 8px;

    border: 1px solid var(--border-color);

    color: #334155;

    font-weight: 700;

    white-space: nowrap;
}


.emp-table td {

    padding: 11px 8px;

    border: 1px solid var(--border-color);

    color: #475569;

    background-color: #ffffff;

    white-space: nowrap;

    overflow: hidden;

    text-overflow: ellipsis;
}


.emp-table tbody tr:hover td {
    background-color: #f8fafc;
}


/* =========================
   사원번호
========================= */

.emp-link {

    color: var(--primary-color);

    text-decoration: none;

    font-weight: 600;
}

.emp-link:hover {
    text-decoration: underline;
}


/* =========================
   재직 / 퇴직 상태
========================= */

.status-working {
    color: #475569;
    font-weight: 600;
}

.status-retired {
    color: #ef4444;
    font-weight: 700;
}


/* =========================
   정산 상태
========================= */

.calc-icon {

    display: inline-flex;

    width: 21px;
    height: 21px;

    align-items: center;
    justify-content: center;

    border-radius: 4px;

    font-size: 12px;

    color: #ffffff;

    font-weight: 700;
}

.calc-no {
    background-color: #93c5fd;
}

.calc-yes {
    background-color: #fb7185;
}


/* =========================
   결과 없음
========================= */

.empty-row {

    height: 120px;

    color: var(--text-muted);

    font-size: 13px;
}


/* =========================
   페이징
========================= */

.pagination {

    display: flex;

    justify-content: center;

    align-items: center;

    gap: 5px;

    margin-top: 20px;
}


.page-link {

    display: inline-flex;

    align-items: center;
    justify-content: center;

    min-width: 32px;
    height: 32px;

    padding: 0 8px;

    border: 1px solid var(--border-color);

    border-radius: 5px;

    background-color: #ffffff;

    color: #475569;

    font-size: 12px;

    text-decoration: none;

    box-sizing: border-box;
}


.page-link:hover {
    background-color: #f1f5f9;
}


.page-link.active {

    background-color: var(--primary-color);

    border-color: var(--primary-color);

    color: #ffffff;

    font-weight: 700;
}


/* =========================
   하단 정보
========================= */

.list-info {

    display: flex;

    justify-content: space-between;

    align-items: center;

    margin-top: 14px;

    font-size: 12px;

    color: var(--text-muted);
}



/* =========================
   퇴직처리 모달
========================= */

.calc-button {
    border: none;
    padding: 0;
    cursor: pointer;
    font-family: inherit;
}

.retire-modal {
    display: none;
    position: fixed;
    z-index: 2000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.35);
    justify-content: center;
    align-items: center;
}

.retire-modal-content {
    position: relative;
    width: 510px;
    background-color: #ffffff;
    border-radius: 12px;
    padding: 24px 24px 26px;
    box-sizing: border-box;
    box-shadow: 0 10px 30px rgba(15, 23, 42, 0.22);
}

.retire-modal-content h2 {
    margin: 0 0 18px;
    font-size: 22px;
    font-weight: 700;
    color: #555555;
}

.retire-modal-close {
    position: absolute;
    right: 18px;
    top: 12px;
    border: none;
    background: transparent;
    color: #777777;
    font-size: 26px;
    cursor: pointer;
}

.retire-form-box {
    border: 1px solid #dddddd;
    padding: 6px 18px;
    box-sizing: border-box;
}

.retire-form-row {
    display: flex;
    align-items: center;
    min-height: 50px;
    border-top: 1px solid #e5e7eb;
}

.retire-form-row:first-child {
    border-top: 2px solid #222222;
}

.retire-form-row label {
    width: 140px;
    flex-shrink: 0;
    text-align: center;
    font-size: 14px;
    font-weight: 600;
    color: #666666;
}

.retire-form-row input,
.retire-form-row select {
    width: 230px;
    height: 30px;
    padding: 4px 8px;
    border: 1px solid #d6d9df;
    box-sizing: border-box;
    font-family: inherit;
    font-size: 13px;
    background-color: #ffffff;
    outline: none;
}

.retire-form-row input:focus,
.retire-form-row select:focus {
    border-color: var(--primary-color);
    box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.08);
}

.retire-button-area {
    margin-top: 14px;
    text-align: center;
}

.retire-save-btn {
    width: 90px;
    height: 40px;
    border: none;
    border-radius: 6px;
    background-color: #3d7dca;
    color: #ffffff;
    font-family: inherit;
    font-size: 15px;
    font-weight: 700;
    cursor: pointer;
}

.retire-save-btn:hover {
    background-color: #326eb6;
}


/* =========================
   반응형
========================= */

@media (max-width: 1100px) {

    .search-area {
        flex-direction: column;
        align-items: stretch;
    }

    .search-left,
    .search-right {
        width: 100%;
    }

    .search-input {
        flex: 1;
    }
}

</style>

</head>

<body>

<layout:headerNav />


<div class="container">


    <div class="page-header">

        <h2>👥 사원 퇴직 관리</h2>

        <p>
            등록된 사원의 재직 및 퇴직 현황을 조회하는 메뉴입니다.
        </p>

    </div>


    <div class="list-section">


        <%-- 임시 확인용
        <p>페이지 객체 : ${retirementEmpPage}</p>
        <p>총 인원 : ${retirementEmpPage.total}</p>
        <p>리스트 : ${retirementEmpPage.retirementEmpListData}</p>
        --%>


        <form
            action="${pageContext.request.contextPath}/retirement/list.do"
            method="get">

            <div class="search-area">


                <div class="search-left">


                    <select
                        name="searchType"
                        class="search-type">

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


                    <input
                        type="text"
                        name="keyword"
                        class="search-input"
                        value="${param.keyword}"
                        placeholder="검색어를 입력해주세요.">


                    <button
                        type="submit"
                        class="btn btn-outline btn-search"
                        title="검색">

                        🔍

                    </button>


                    <a
                        href="${pageContext.request.contextPath}/retirement/list.do"
                        class="btn btn-primary">

                        ▣ 전체보기

                    </a>

                </div>


                <div class="search-right">

                    <select
                        name="status"
                        class="status-select"
                        onchange="this.form.submit();">

                        <option value=""
                            ${empty param.status ? 'selected' : ''}>
                            상태별
                        </option>

                        <option value="Y"
                            ${param.status == 'Y' ? 'selected' : ''}>
                            재직
                        </option>

                        <option value="N"
                            ${param.status == 'N' ? 'selected' : ''}>
                            퇴직
                        </option>

                    </select>

                </div>


            </div>

        </form>



        <div class="table-wrap">

            <table class="emp-table">


                <colgroup>

                    <col style="width: 55px;">
                    <col style="width: 85px;">
                    <col style="width: 110px;">
                    <col style="width: 100px;">
                    <col style="width: 120px;">
                    <col style="width: 110px;">
                    <col style="width: 120px;">
                    <col style="width: 120px;">
                    <col style="width: 90px;">
                    <col style="width: 95px;">
                    <col style="width: 95px;">

                </colgroup>


                <thead>

                    <tr>

                        <th>번호</th>
                        <th>상태</th>
                        <th>사원번호</th>
                        <th>성명</th>
                        <th>부서</th>
                        <th>직위</th>
                        <th>입사일</th>
                        <th>퇴직일</th>
                        <th>근속연수</th>
                        <th>중간정산</th>
                        <th>퇴직정산</th>

                    </tr>

                </thead>


                <tbody>


                    <c:forEach
                        var="emp"
                        items="${retirementEmpPage.retirementEmpListData}"
                        varStatus="status">


                        <tr>


                            <td>
                                ${(retirementEmpPage.currentPage - 1)
                                    * 10 + status.count}
                            </td>


                            <td>

                                <c:choose>

                                    <c:when test="${emp.status == 'Y'}">

                                        <span class="status-working">
                                            재직
                                        </span>

                                    </c:when>

                                    <c:otherwise>

                                        <span class="status-retired">
                                            퇴직
                                        </span>

                                    </c:otherwise>

                                </c:choose>

                            </td>


                            <td>

                                <a
                                    class="emp-link"
                                    href="${pageContext.request.contextPath}/retirement/read.do?empId=${emp.empId}">

                                    No-${emp.empId}

                                </a>

                            </td>


                            <td>
                                ${emp.nameKor}
                            </td>


                            <td>

                                <c:choose>

                                    <c:when test="${not empty emp.deptName}">
                                        ${emp.deptName}
                                    </c:when>

                                    <c:otherwise>
                                        -
                                    </c:otherwise>

                                </c:choose>

                            </td>


                            <td>

                                <c:choose>

                                    <c:when test="${not empty emp.positionName}">
                                        ${emp.positionName}
                                    </c:when>

                                    <c:otherwise>
                                        -
                                    </c:otherwise>

                                </c:choose>

                            </td>


                            <td>
                                ${emp.hireDate}
                            </td>


                            <td>

                                <c:choose>

                                    <c:when test="${not empty emp.retireDate}">
                                        ${emp.retireDate}
                                    </c:when>

                                    <c:otherwise>
                                        -
                                    </c:otherwise>

                                </c:choose>

                            </td>


                            <td>
                                ${emp.workYears}년
                            </td>


                            <td>

                                <button
                                    type="button"
                                    class="calc-icon calc-no calc-button"
                                    title="중간정산 처리"
                                    onclick="openRetireModal('${emp.empId}')">

                                    ✕

                                </button>

                            </td>


                            <td>

                                <button
                                    type="button"
                                    class="calc-icon calc-no calc-button"
                                    title="퇴직정산 처리"
                                    onclick="openRetireModal('${emp.empId}')">

                                    ✕

                                </button>

                            </td>


                        </tr>


                    </c:forEach>



                    <c:if test="${empty retirementEmpPage.retirementEmpListData}">

                        <tr>

                            <td
                                colspan="11"
                                class="empty-row">

                                조회된 사원이 없습니다.

                            </td>

                        </tr>

                    </c:if>


                </tbody>


            </table>


        </div>



        <c:if test="${retirementEmpPage.totalPages > 0}">

            <div class="pagination">


                <c:if test="${retirementEmpPage.startPage > 1}">

                    <a
                        class="page-link"
                        href="${pageContext.request.contextPath}/retirement/list.do?pageNo=${retirementEmpPage.startPage - 1}">

                        이전

                    </a>

                </c:if>


                <c:forEach
                    var="pageNo"
                    begin="${retirementEmpPage.startPage}"
                    end="${retirementEmpPage.endPage}">


                    <c:choose>

                        <c:when test="${pageNo == retirementEmpPage.currentPage}">

                            <span class="page-link active">
                                ${pageNo}
                            </span>

                        </c:when>

                        <c:otherwise>

                            <a
                                class="page-link"
                                href="${pageContext.request.contextPath}/retirement/list.do?pageNo=${pageNo}">

                                ${pageNo}

                            </a>

                        </c:otherwise>

                    </c:choose>


                </c:forEach>


                <c:if test="${retirementEmpPage.endPage < retirementEmpPage.totalPages}">

                    <a
                        class="page-link"
                        href="${pageContext.request.contextPath}/retirement/list.do?pageNo=${retirementEmpPage.endPage + 1}">

                        다음

                    </a>

                </c:if>


            </div>

        </c:if>



        <div class="list-info">

            <div>

                총

                <strong>
                    ${retirementEmpPage.total}
                </strong>

                명

            </div>

        </div>


    </div>


</div>



<!-- 퇴직처리 모달 -->
<div id="retireModal" class="retire-modal">

    <div class="retire-modal-content">

        <button
            type="button"
            class="retire-modal-close"
            onclick="closeRetireModal()"
            aria-label="닫기">
            ×
        </button>

        <h2>퇴사자 퇴직처리</h2>

        <form
            action="${pageContext.request.contextPath}/retirement/retire.do"
            method="post"
            onsubmit="return validateRetireForm();">

            <input
                type="hidden"
                id="retireEmpId"
                name="emp_id">

            <div class="retire-form-box">

                <div class="retire-form-row">

                    <label for="retirementType">
                        퇴직구분
                    </label>

                    <select
                        id="retirementType"
                        name="retirement_type">

                        <option value="" selected>선택</option>
                        <option value="정년퇴직">정년퇴직</option>
                        <option value="정리해고">정리해고</option>
                        <option value="자발적 퇴직">자발적 퇴직</option>
                        <option value="임원퇴직">임원퇴직</option>
                        <option value="중간정산">중간정산</option>
                        <option value="기타">기타</option>

                    </select>

                </div>

                <div class="retire-form-row">

                    <label for="retirementDate">
                        퇴직일자
                    </label>

                    <input
                        type="date"
                        id="retirementDate"
                        name="retirement_date">

                </div>

                <div class="retire-form-row">

                    <label for="retirementReason">
                        퇴직사유
                    </label>

                    <input
                        type="text"
                        id="retirementReason"
                        name="retirement_reason"
                        maxlength="200">

                </div>

                <div class="retire-form-row">

                    <label for="contactAfterRetirement">
                        퇴직 후 연락처
                    </label>

                    <input
                        type="text"
                        id="contactAfterRetirement"
                        name="contact_after_retirement"
                        maxlength="30">

                </div>

            </div>

            <div class="retire-button-area">

                <button
                    type="submit"
                    class="retire-save-btn">
                    저장
                </button>

            </div>

        </form>

    </div>

</div>


<layout:footer />



<script>
function openRetireModal(empId) {

    document.getElementById("retireEmpId").value = empId;

    document.getElementById("retirementType").value = "";
    document.getElementById("retirementDate").value = "";
    document.getElementById("retirementReason").value = "";
    document.getElementById("contactAfterRetirement").value = "";

    document.getElementById("retireModal").style.display = "flex";
}

function closeRetireModal() {
    document.getElementById("retireModal").style.display = "none";
}

function validateRetireForm() {

    const retirementType =
        document.getElementById("retirementType").value;

    const retirementDate =
        document.getElementById("retirementDate").value;

    if (retirementType === "") {
        alert("퇴직구분을 선택해주세요.");
        document.getElementById("retirementType").focus();
        return false;
    }

    if (retirementDate === "") {
        alert("퇴직일자를 입력해주세요.");
        document.getElementById("retirementDate").focus();
        return false;
    }

    return confirm("해당 사원의 퇴직 정보를 저장하시겠습니까?");
}

window.addEventListener("click", function(event) {

    const modal = document.getElementById("retireModal");

    if (event.target === modal) {
        closeRetireModal();
    }
});

document.addEventListener("keydown", function(event) {

    if (event.key === "Escape") {
        closeRetireModal();
    }
});
</script>


</body>
</html>