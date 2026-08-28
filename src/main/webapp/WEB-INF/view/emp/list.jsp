<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ヘキサゾーン - 社員現況・管理</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/list.css" />
</head>

<body>

	<layout:headerNav />

	<div class="container">

		<div class="page-header">
			<h2>👔 社員現況・管理</h2>
			<p>全社員の現況を確認でき、社員を選択すると該当社員の情報修正ページへ移動します。</p>
		</div>

		<!-- 사원 현황 요약 / 社員現況サマリー -->
		<div class="status-summary">

			<div class="summary-card card-active">
				<div class="card-head">在職者</div>
				<div class="card-body">${empListPage.activeCount}</div>
			</div>

			<div class="summary-card card-type">
				<div class="card-head">正社員</div>
				<div class="card-body">${empListPage.regularCount}</div>
			</div>

			<div class="summary-card card-type">
				<div class="card-head">契約社員</div>
				<div class="card-body">${empListPage.contractCount}</div>
			</div>

			<div class="summary-card card-type">
				<div class="card-head">臨時職</div>
				<div class="card-body">${empListPage.temporaryCount}</div>
			</div>

			<div class="summary-card card-type">
				<div class="card-head">派遣社員</div>
				<div class="card-body">${empListPage.dispatchedCount}</div>
			</div>

			<div class="summary-card card-type">
				<div class="card-head">委嘱職</div>
				<div class="card-body">${empListPage.commissionedCount}</div>
			</div>

			<div class="summary-card card-type">
				<div class="card-head">日雇い</div>
				<div class="card-body">${empListPage.dailyCount}</div>
			</div>

			<div class="summary-card card-retired">
				<div class="card-head">退職者</div>
				<div class="card-body">${empListPage.retiredCount}</div>
			</div>

			<div class="summary-card card-total">
				<div class="card-head">全体</div>
				<div class="card-body">${empListPage.total}</div>
			</div>

		</div>

		<!-- 사원 검색 / 社員検索 -->
		<form action="${pageContext.request.contextPath}/emp/list.do"
			method="get">

			<div class="search-bar">

				<div class="search-group">

					<select name="searchType">

						<option value="all" ${param.searchType == 'all' ? 'selected' : ''}>全体</option>

						<option value="name"
							${param.searchType == 'name' ? 'selected' : ''}>氏名</option>

						<option value="empId"
							${param.searchType == 'empId' ? 'selected' : ''}>社員番号</option>

						<option value="dept"
							${param.searchType == 'dept' ? 'selected' : ''}>部署</option>

					</select> <input type="text" name="searchKeyword"
						value="${param.searchKeyword}" placeholder="検索ワードを入力">

					<button type="submit" class="btn btn-primary">🔍 検索</button>

					<a href="${pageContext.request.contextPath}/emp/list.do"
						class="btn btn-outline">全件表示</a>

				</div>

			</div>

		</form>

		<!-- 사원 목록 / 社員一覧 -->
		<div class="table-container">

			<table class="list-table">

				<thead>
					<tr>
						<th width="40"><input type="checkbox" id="checkAll">
						</th>
						<th>区分</th>
						<th>入社日</th>
						<th>社員番号</th>
						<th>氏名</th>
						<th>部署</th>
						<th>役職</th>
						<th>住民登録番号</th>
						<th>携帯電話</th>
						<th>メール</th>
						<th>退職日</th>
						<th>状態</th>
					</tr>
				</thead>

				<tbody>

					<c:forEach var="emp" items="${empListPage.empListData}">

						<tr
							onclick="location.href='${pageContext.request.contextPath}/emp/modify.do?empId=${emp.empId}'">

							<td onclick="event.stopPropagation()"><input type="checkbox"
								class="emp-chk" value="${emp.empId}"></td>

							<td>${emp.employmentType}</td>
							<td>${emp.hireDate}</td>
							<td>${emp.empId}</td>

							<td><a
								href="${pageContext.request.contextPath}/emp/modify.do?empId=${emp.empId}"
								class="emp-link" onclick="event.stopPropagation()">
									${emp.nameKor} </a></td>

							<td>${empty emp.deptName ? '-' : emp.deptName}</td>
							<td>${empty emp.positionName ? '-' : emp.positionName}</td>
							<td>${empty emp.residentNo ? '-' : emp.residentNo}</td>
							<td>${empty emp.mobileNum ? '-' : emp.mobileNum}</td>
							<td>${empty emp.email ? '-' : emp.email}</td>
							<td>${empty emp.retireDate ? '-' : emp.retireDate}</td>

							<td><c:choose>
									<c:when test="${emp.status eq 'N'}">
										<span class="badge badge-retired">退職</span>
									</c:when>
									<c:otherwise>
										<span class="badge badge-active">在職</span>
									</c:otherwise>
								</c:choose></td>

						</tr>

					</c:forEach>

					<c:if test="${empty empListPage.empListData}">
						<tr>
							<td colspan="12" class="empty-row">登録された社員情報がありません。</td>
						</tr>
					</c:if>

				</tbody>

			</table>

		</div>

		<!-- 페이지 이동 / ページング -->
		<c:if test="${empListPage.totalPage > 0}">

			<div class="pagination">

				<c:if test="${empListPage.currentPage > 1}">
					<a
						href="${pageContext.request.contextPath}/emp/list.do?pageNo=${empListPage.currentPage - 1}">
						‹ 前のページ </a>
				</c:if>

				<c:forEach var="pageNo" begin="${empListPage.startPage}"
					end="${empListPage.endPage}">

					<c:choose>

						<c:when test="${pageNo == empListPage.currentPage}">
							<span class="current-page">${pageNo}</span>
						</c:when>

						<c:otherwise>
							<a
								href="${pageContext.request.contextPath}/emp/list.do?pageNo=${pageNo}">
								${pageNo} </a>
						</c:otherwise>

					</c:choose>

				</c:forEach>

				<c:if test="${empListPage.currentPage < empListPage.totalPage}">
					<a
						href="${pageContext.request.contextPath}/emp/list.do?pageNo=${empListPage.currentPage + 1}">
						次のページ › </a>
				</c:if>

			</div>

		</c:if>

		<!-- 하단 작업 버튼 / 下部操作ボタン -->
		<div class="bottom-actions">

			<a href="${pageContext.request.contextPath}/emp/register.do"
				class="btn btn-register">新規社員登録</a>

			<button type="button" class="btn btn-delete">選択削除</button>

			<button type="button" class="btn btn-excel">Excelダウンロード</button>

		</div>

	</div>

	<layout:footer />

	<!-- 체크박스 선택 처리 / チェックボックス選択処理 -->
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