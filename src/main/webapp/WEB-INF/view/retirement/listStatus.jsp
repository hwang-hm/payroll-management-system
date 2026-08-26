<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">

<title>ヘキサゴンゾーン - 社員退職管理</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/listStatus.css" />

</head>

<body>

	<layout:headerNav />

	<div class="container">

		<!-- 페이지 상단 제목 및 설명 / ページ上部タイトルおよび説明 -->
		<div class="page-header">

			<h2>👥 社員退職管理</h2>

			<p>登録された社員の在職・退職状況を照会するメニューです。</p>

		</div>

		<div class="list-section">

			<!-- 사원 검색 및 상태 필터 / 社員検索および状態フィルター -->
			<form action="${pageContext.request.contextPath}/retirement/list.do"
				method="get">

				<div class="search-area">

					<div class="search-left">

						<select name="searchType" class="search-type">

							<option value="name"
								${param.searchType == 'name' ? 'selected' : ''}>氏名</option>

							<option value="empId"
								${param.searchType == 'empId' ? 'selected' : ''}>社員番号</option>

							<option value="dept"
								${param.searchType == 'dept' ? 'selected' : ''}>部署</option>

						</select> <input type="text" name="keyword" class="search-input"
							value="${param.keyword}" placeholder="検索ワードを入力してください。">

						<button type="submit" class="btn btn-outline btn-search"
							title="検索">🔍</button>

						<a href="${pageContext.request.contextPath}/retirement/list.do"
							class="btn btn-primary"> ▣ 全件表示 </a>

					</div>

					<div class="search-right">

						<select name="status" class="status-select"
							onchange="this.form.submit();">

							<option value="" ${empty param.status ? 'selected' : ''}>
								状態別</option>

							<option value="Y" ${param.status == 'Y' ? 'selected' : ''}>
								在職</option>

							<option value="N" ${param.status == 'N' ? 'selected' : ''}>
								退職</option>

						</select>

					</div>

				</div>

			</form>

			<!-- 사원 퇴직 현황 목록 / 社員退職状況一覧 -->
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

							<th>番号</th>
							<th>状態</th>
							<th>社員番号</th>
							<th>氏名</th>
							<th>部署</th>
							<th>役職</th>
							<th>入社日</th>
							<th>退職日</th>
							<th>勤続年数</th>
							<th>中間精算</th>
							<th>退職精算</th>

						</tr>

					</thead>

					<tbody>

						<c:forEach var="emp"
							items="${retirementEmpPage.retirementEmpListData}"
							varStatus="status">

							<tr>

								<td>${(retirementEmpPage.currentPage - 1)
                                    * 10 + status.count}
								</td>

								<td><c:choose>

										<c:when test="${emp.status == 'Y'}">

											<span class="status-working"> 在職 </span>

										</c:when>

										<c:otherwise>

											<span class="status-retired"> 退職 </span>

										</c:otherwise>

									</c:choose></td>

								<td><a class="emp-link"
									href="${pageContext.request.contextPath}/retirement/read.do?empId=${emp.empId}">

										No-${emp.empId} </a></td>

								<td>${emp.nameKor}</td>

								<td><c:choose>

										<c:when test="${not empty emp.deptName}">
                                        ${emp.deptName}
                                    </c:when>

										<c:otherwise>
                                        -
                                    </c:otherwise>

									</c:choose></td>

								<td><c:choose>

										<c:when test="${not empty emp.positionName}">
                                        ${emp.positionName}
                                    </c:when>

										<c:otherwise>
                                        -
                                    </c:otherwise>

									</c:choose></td>

								<td>${emp.hireDate}</td>

								<td><c:choose>

										<c:when test="${not empty emp.retireDate}">
                                        ${emp.retireDate}
                                    </c:when>

										<c:otherwise>
                                        -
                                    </c:otherwise>

									</c:choose></td>

								<td>${emp.workYears}年</td>

								<td>

									<button type="button" class="calc-icon calc-no calc-button"
										title="中間精算処理" onclick="openRetireModal('${emp.empId}')">

										✕</button>

								</td>

								<td>

									<button type="button" class="calc-icon calc-no calc-button"
										title="退職精算処理" onclick="openRetireModal('${emp.empId}')">

										✕</button>

								</td>

							</tr>

						</c:forEach>

						<c:if test="${empty retirementEmpPage.retirementEmpListData}">

							<tr>

								<td colspan="11" class="empty-row">照会された社員がありません。</td>

							</tr>

						</c:if>

					</tbody>

				</table>

			</div>

			<c:if test="${retirementEmpPage.totalPages > 0}">

				<!-- 페이지 이동 / ページング -->
				<div class="pagination">

					<c:if test="${retirementEmpPage.startPage > 1}">

						<a class="page-link"
							href="${pageContext.request.contextPath}/retirement/list.do?pageNo=${retirementEmpPage.startPage - 1}">

							前へ </a>

					</c:if>

					<c:forEach var="pageNo" begin="${retirementEmpPage.startPage}"
						end="${retirementEmpPage.endPage}">

						<c:choose>

							<c:when test="${pageNo == retirementEmpPage.currentPage}">

								<span class="page-link active"> ${pageNo} </span>

							</c:when>

							<c:otherwise>

								<a class="page-link"
									href="${pageContext.request.contextPath}/retirement/list.do?pageNo=${pageNo}">

									${pageNo} </a>

							</c:otherwise>

						</c:choose>

					</c:forEach>

					<c:if
						test="${retirementEmpPage.endPage < retirementEmpPage.totalPages}">

						<a class="page-link"
							href="${pageContext.request.contextPath}/retirement/list.do?pageNo=${retirementEmpPage.endPage + 1}">

							次へ </a>

					</c:if>

				</div>

			</c:if>

			<!-- 전체 사원 수 / 全社員数 -->
			<div class="list-info">

				<div>

					合計 <strong> ${retirementEmpPage.total} </strong> 名

				</div>

			</div>

		</div>

	</div>

	<!-- 퇴직처리 모달 / 退職処理モーダル -->
	<div id="retireModal" class="retire-modal">

		<div class="retire-modal-content">

			<button type="button" class="retire-modal-close"
				onclick="closeRetireModal()" aria-label="閉じる">×</button>

			<h2>退職者 退職処理</h2>

			<form
				action="${pageContext.request.contextPath}/retirement/retire.do"
				method="post" onsubmit="return validateRetireForm();">

				<input type="hidden" id="retireEmpId" name="emp_id">

				<div class="retire-form-box">

					<div class="retire-form-row">

						<label for="retirementType"> 退職区分 </label> <select
							id="retirementType" name="retirement_type">

							<option value="" selected>選択</option>
							<option value="定年退職">定年退職</option>
							<option value="整理解雇">整理解雇</option>
							<option value="自己都合退職">自己都合退職</option>
							<option value="役員退職">役員退職</option>
							<option value="中間精算">中間精算</option>
							<option value="その他">その他</option>

						</select>

					</div>

					<div class="retire-form-row">

						<label for="retirementDate"> 退職日 </label> <input type="date"
							id="retirementDate" name="retirement_date">

					</div>

					<div class="retire-form-row">

						<label for="retirementReason"> 退職理由 </label> <input type="text"
							id="retirementReason" name="retirement_reason" maxlength="200">

					</div>

					<div class="retire-form-row">

						<label for="contactAfterRetirement"> 退職後連絡先 </label> <input
							type="text" id="contactAfterRetirement"
							name="contact_after_retirement" maxlength="30">

					</div>

				</div>

				<div class="retire-button-area">

					<button type="submit" class="retire-save-btn">保存</button>

				</div>

			</form>

		</div>

	</div>

	<layout:footer />

	<!-- 퇴직처리 모달 및 입력 검증 / 退職処理モーダルおよび入力検証 -->
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

			const retirementType = document.getElementById("retirementType").value;

			const retirementDate = document.getElementById("retirementDate").value;

			if (retirementType === "") {
				alert("退職区分を選択してください。");
				document.getElementById("retirementType").focus();
				return false;
			}

			if (retirementDate === "") {
				alert("退職日を入力してください。");
				document.getElementById("retirementDate").focus();
				return false;
			}

			return confirm("該当社員の退職情報を保存しますか？");
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