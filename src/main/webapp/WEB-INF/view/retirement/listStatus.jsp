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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/listStatus.css"/>

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