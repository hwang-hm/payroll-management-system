<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">

<title>헥사곤존 - 제증명서 발급대장</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/certificateList.css" />

<style>

</style>

</head>

<body>

	<layout:headerNav />

	<main class="certificate-list-container">

		<div class="page-hero">
			<h1>📋 제증명서 발급대장</h1>
			<p>발급된 제증명서 내역을 조회할 수 있습니다.</p>
		</div>

		<div class="search-area">

			<select id="certificateType">
				<option value="">전체</option>
				<option value="WORKING">재직증명서</option>
				<option value="RETIREMENT">퇴직증명서</option>
			</select> <input type="text" id="searchKeyword" placeholder="사원명 검색"
				onkeyup="if(event.key === 'Enter') searchCertificate();">

			<button type="button" class="search-btn"
				onclick="searchCertificate();">검색</button>

			<button type="button" class="all-btn" onclick="showAllCertificate();">
				전체보기</button>

			<div class="total-count">
				총 발급건수 : <strong>${certificateList.size()}</strong>건
			</div>

		</div>

		<table class="certificate-list-table">

			<thead>
				<tr>
					<th style="width: 45px;"><input type="checkbox" id="checkAll"
						onclick="toggleAll(this);"></th>
					<th>발급번호</th>
					<th>발급대장</th>
					<th>발급용도</th>
					<th>구분</th>
					<th>성명</th>
					<th>부서</th>
					<th>직위</th>
					<th>발급부서</th>
					<th>발급일</th>
				</tr>
			</thead>

			<tbody>

				<c:choose>

					<c:when test="${empty certificateList}">
						<tr>
							<td colspan="10" class="empty-row">발급된 증명서가 없습니다.</td>
						</tr>
					</c:when>

					<c:otherwise>

						<c:forEach var="item" items="${certificateList}">

							<tr class="certificate-row" data-type="${item.certificateType}"
								data-name="${item.nameKor}">

								<td><input type="checkbox" class="issue-check"
									value="${item.certificateIssueId}"></td>

								<td><c:choose>
										<c:when test="${item.certificateType == 'WORKING'}">
											재직-${item.certificateIssueId}
										</c:when>

										<c:when test="${item.certificateType == 'RETIREMENT'}">
											퇴직-${item.certificateIssueId}
										</c:when>

										<c:otherwise>
											${item.certificateIssueId}
										</c:otherwise>
									</c:choose></td>

								<td><c:choose>

										<c:when test="${item.certificateType == 'WORKING'}">
											<span class="type-working"> 재직 </span>
										</c:when>

										<c:when test="${item.certificateType == 'RETIREMENT'}">
											<span class="type-retirement"> 퇴직 </span>
										</c:when>

										<c:otherwise>
											<c:out value="${item.certificateType}" />
										</c:otherwise>

									</c:choose></td>

								<td><c:out value="${item.purpose}" /></td>

								<td><c:out value="${item.employmentType}" /></td>

								<td><c:out value="${item.nameKor}" /></td>

								<td><c:out value="${item.deptName}" /></td>

								<td><c:out value="${item.positionName}" /></td>

								<td><c:out value="${item.issueDepartment}" /></td>

								<td><fmt:formatDate value="${item.issueDate}"
										pattern="yyyy-MM-dd" /></td>

							</tr>

						</c:forEach>

					</c:otherwise>

				</c:choose>

			</tbody>

		</table>

		<div class="action-area">

			<button type="button" class="delete-btn" onclick="deleteSelected();">
				선택 삭제</button>

			<button type="button" class="delete-btn" onclick="deleteAll();">
				전체 삭제</button>

		</div>

	</main>

	<layout:footer />

	<script>
		/* ==================================================
		 검색
		================================================== */

		function searchCertificate() {

			const type = document.getElementById("certificateType").value;

			const keyword = document.getElementById("searchKeyword").value
					.trim().toLowerCase();

			const rows = document.querySelectorAll(".certificate-row");

			rows.forEach(function(row) {

				const rowType = row.dataset.type;

				const rowName = row.dataset.name.toLowerCase();

				const typeMatch = !type || rowType === type;

				const nameMatch = !keyword || rowName.includes(keyword);

				if (typeMatch && nameMatch) {

					row.style.display = "";

				} else {

					row.style.display = "none";

				}

			});

		}

		/* ==================================================
		 전체보기
		================================================== */

		function showAllCertificate() {

			document.getElementById("certificateType").value = "";

			document.getElementById("searchKeyword").value = "";

			const rows = document.querySelectorAll(".certificate-row");

			rows.forEach(function(row) {

				row.style.display = "";

			});

		}

		/* ==================================================
		 전체 선택
		================================================== */

		function toggleAll(checkAll) {

			const checks = document.querySelectorAll(".issue-check");

			checks.forEach(function(check) {

				check.checked = checkAll.checked;

			});

		}
	</script>

</body>
</html>