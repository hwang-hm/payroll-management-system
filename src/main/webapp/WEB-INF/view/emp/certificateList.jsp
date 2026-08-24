<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">

<title>ヘキサゴンゾーン - 証明書発行台帳</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/certificateList.css" />

<style>
</style>

</head>

<body>

	<layout:headerNav />

	<main class="certificate-list-container">

		<div class="page-hero">
			<h1>📋 証明書発行台帳</h1>
			<p>発行された証明書の履歴を確認できます。</p>
		</div>

		<!-- 발급대장 검색 조건 / 発行台帳検索条件 -->
		<div class="search-area">

			<select id="certificateType">
				<option value="">全体</option>
				<option value="WORKING">在職証明書</option>
				<option value="RETIREMENT">退職証明書</option>
			</select> <input type="text" id="searchKeyword" placeholder="社員名検索"
				onkeyup="if(event.key === 'Enter') searchCertificate();">

			<button type="button" class="search-btn"
				onclick="searchCertificate();">検索</button>

			<button type="button" class="all-btn" onclick="showAllCertificate();">
				全件表示</button>

			<div class="total-count">
				総発行件数： <strong>${certificateList.size()}</strong>件
			</div>

		</div>

		<table class="certificate-list-table">

			<thead>
				<tr>
					<th style="width: 45px;"><input type="checkbox" id="checkAll"
						onclick="toggleAll(this);"></th>
					<th>発行番号</th>
					<th>証明書区分</th>
					<th>発行用途</th>
					<th>雇用形態</th>
					<th>氏名</th>
					<th>部署</th>
					<th>役職</th>
					<th>発行部署</th>
					<th>発行日</th>
				</tr>
			</thead>

			<tbody>

				<c:choose>

					<c:when test="${empty certificateList}">
						<tr>
							<td colspan="10" class="empty-row">発行された証明書がありません。</td>
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
											在職-${item.certificateIssueId}
										</c:when>

										<c:when test="${item.certificateType == 'RETIREMENT'}">
											退職-${item.certificateIssueId}
										</c:when>

										<c:otherwise>
											${item.certificateIssueId}
										</c:otherwise>
									</c:choose></td>

								<td><c:choose>

										<c:when test="${item.certificateType == 'WORKING'}">
											<span class="type-working"> 在職 </span>
										</c:when>

										<c:when test="${item.certificateType == 'RETIREMENT'}">
											<span class="type-retirement"> 退職 </span>
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
				選択削除</button>

			<button type="button" class="delete-btn" onclick="deleteAll();">
				全件削除</button>

		</div>

	</main>

	<layout:footer />

	<!-- 검색 및 체크박스 선택 처리 / 検索およびチェックボックス選択処理 -->
	<script>
		

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

		<!-- 전체보기 처리 / 全件表示処理 -->

		function showAllCertificate() {

			document.getElementById("certificateType").value = "";

			document.getElementById("searchKeyword").value = "";

			const rows = document.querySelectorAll(".certificate-row");

			rows.forEach(function(row) {

				row.style.display = "";

			});

		}

		

		function toggleAll(checkAll) {

			const checks = document.querySelectorAll(".issue-check");

			checks.forEach(function(check) {

				check.checked = checkAll.checked;

			});

		}
	</script>

</body>
</html>