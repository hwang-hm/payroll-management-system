<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">

<title>ヘキサゾーン - 在職証明書発行</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/certificate.css" />

</head>

<body>

	<layout:headerNav />

	<main class="certificate-container">
		<div class="page-hero">
			<h1>📄 各種証明書発行</h1>
			<p>社員を選択すると、該当社員の在職証明書を確認して発行できます。</p>
		</div>
		<div class="certificate-layout">

			<section class="employee-panel">
				<div class="panel-title">社員一覧</div>
				<div class="search-area">
					<input type="text" id="searchKeyword" placeholder="社員名検索"
						onkeyup="if(event.key === 'Enter') searchEmployee();">
					<button type="button" class="btn btn-search"
						onclick="searchEmployee();">検索</button>
					<button type="button" class="btn btn-all"
						onclick="showAllEmployee();">全件表示</button>
				</div>

				<table class="employee-table">
					<thead>
						<tr>
							<th>区分</th>
							<th>氏名</th>
							<th>部署</th>
							<th>役職</th>
							<th>状態</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="employee" items="${empListPage.empListData}">
							<tr class="${selectedEmpId == employee.empId ? 'selected' : ''}"
								onclick="selectEmployee('${employee.empId}', '${empListPage.currentPage}');">
								<td><c:out value="${employee.employmentType}" /></td>
								<td><c:out value="${employee.nameKor}" /></td>
								<td><c:out value="${employee.deptName}" /></td>
								<td><c:out value="${employee.positionName}" /></td>
								<td><c:choose>
										<c:when test="${employee.status == 'Y'}">
											<span class="status-working"> 在職 </span>
										</c:when>
										<c:otherwise>
											<span class="status-retired"> 退職 </span>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<c:if test="${empListPage.totalPage > 1}">

					<div class="pagination">

						<c:forEach var="page" begin="${empListPage.startPage}"
							end="${empListPage.endPage}">

							<a
								href="${pageContext.request.contextPath}/emp/certificate/working.do?pageNo=${page}"
								class="${empListPage.currentPage == page ? 'active' : ''}">
								${page} </a>

						</c:forEach>

					</div>

				</c:if>

			</section>

			<section class="preview-panel">

				<div class="certificate-tabs">

					<a
						href="${pageContext.request.contextPath}/emp/certificate/working.do"
						class="certificate-tab active"> 在職証明書 </a> <a
						href="${pageContext.request.contextPath}/emp/certificate/retirement.do"
						class="certificate-tab"> 退職証明書 </a>

				</div>

				<div class="certificate-preview-area">
					<div class="certificate-paper">

						<div class="cert-top">
							<div class="cert-logo">HEXA ZONE</div>
							<div class="cert-title">在職証明書</div>
							<div class="cert-option">
								<label> <input type="checkbox" id="hideResidentNo"
									onchange="toggleResidentNo();"> 住民登録番号を非表示
								</label>
							</div>
						</div>

						<table class="cert-table">

							<tr>

								<th rowspan="2" class="section-head">個人<br>情報
								</th>
								<th>氏名</th>
								<td><c:out value="${emp.nameKor}" /></td>
								<th>住民登録番号</th>
								<td id="residentNoCell"><c:out value="${emp.residentNo}" />
								</td>
							</tr>
							<tr>
								<th>住所</th>
								<td colspan="3"><c:out value="${emp.address}" /></td>
							</tr>
							<tr>
								<th rowspan="3" class="section-head">在職<br>情報
								</th>

								<th>会社名</th>
								<td>株式会社ヘキサゴンゾーン</td>
								<th>事業者番号</th>
								<td>1234-5648</td>
							</tr>
							<tr>
								<th>部署</th>
								<td><c:out value="${emp.deptName}" /></td>
								<th>役職</th>
								<td><c:out value="${emp.positionName}" /></td>
							</tr>
							<tr>
								<th>入社日</th>
								<td><fmt:formatDate value="${emp.hireDate}"
										pattern="yyyy-MM-dd" /></td>
								<th>勤続期間</th>
								<td id="servicePeriod">-</td>
							</tr>
						</table>

						<div class="usage-row">
							<div class="usage-label">発行用途</div>
							<div class="usage-input-area">
								<select id="issuePurpose" class="cert-select"
									onchange="changeIssuePurpose();">
									<option value="">選択</option>
									<option value="ビザ申請用">ビザ申請用</option>
									<option value="銀行提出用">銀行提出用</option>
									<option value="官公庁提出用">官公庁提出用</option>
									<option value="学校提出用">学校提出用</option>
									<option value="会社提出用">会社提出用</option>
									<option value="兵務庁提出用">兵務庁提出用</option>
									<option value="関連課題提出用">関連課題提出用</option>
									<option value="直接入力">直接入力</option>
								</select> <input type="text" id="directPurpose" class="direct-purpose"
									placeholder="発行用途を入力してください。">

							</div>

						</div>

						<div class="cert-content">上記の者が現在、当社に在職していることを証明します。</div>

						<div class="cert-date">

							<span id="todayYear"></span>年 <span id="todayMonth"></span>月 <span
								id="todayDay"></span>日

						</div>

						<table class="issue-info-table">

							<tr>

								<th>発行部署</th>

								<td class="issue-dept-cell"><select id="issueDepartment">

										<option value="">選択</option>

										<option value="社長室">社長室</option>

										<option value="開発チーム">開発チーム</option>

										<option value="コンテンツチーム">コンテンツチーム</option>

										<option value="業務支援チーム">業務支援チーム</option>

										<option value="デザインチーム">デザインチーム</option>

										<option value="管理チーム">管理チーム</option>

										<option value="企画戦略チーム">企画戦略チーム</option>

								</select></td>

								<th class="contact-label">連絡先</th>

								<td class="contact-value">[全国] 1588-2390</td>

							</tr>

						</table>

						<div class="cert-company-bottom">

							<div class="representative-option">

								<label> <input type="checkbox" id="showRepresentative"
									checked> 代表者表示

								</label>

							</div>

							<div class="company-sign" id="representativeArea">

								<div class="company-name">

									株式会社ヘキサゴンゾーン <br> 代表取締役

								</div>

								<div class="company-seal">

									会社 <br> 印

								</div>

							</div>

						</div>

					</div>

				</div>

				<div class="action-area">

					<button type="button" class="print-btn"
						onclick="printCertificate();">印刷</button>

				</div>

			</section>

		</div>

	</main>

	<layout:footer />

	<c:if test="${retiredEmployee}">

		<!-- 증명서 선택 및 발급 처리 / 証明書選択および発行処理 -->
		<script>
			alert("退職した社員には在職証明書を発行できません。");
		</script>

	</c:if>

	<c:if test="${employeeNotFound}">

		<script>
			alert("社員情報が見つかりません。");
		</script>

	</c:if>

	<script>
		

		function selectEmployee(empId, pageNo) {

			location.href = "${pageContext.request.contextPath}"
					+ "/emp/certificate/working.do" + "?empId="
					+ encodeURIComponent(empId) + "&pageNo="
					+ encodeURIComponent(pageNo);

		}

		

		function searchEmployee() {

			const keyword = document.getElementById("searchKeyword").value
					.trim().toLowerCase();

			const rows = document.querySelectorAll(".employee-table tbody tr");

			rows.forEach(function(row) {

				const name = row.children[1].innerText.trim().toLowerCase();

				if (name.includes(keyword)) {

					row.style.display = "";

				} else {

					row.style.display = "none";

				}

			});

		}

		<!-- 전체보기 처리 / 全件表示処理 -->

		function showAllEmployee() {

			document.getElementById("searchKeyword").value = "";

			const rows = document.querySelectorAll(".employee-table tbody tr");

			rows.forEach(function(row) {

				row.style.display = "";

			});

		}

		<!-- 발급용도 직접입력 처리 / 発行用途直接入力処理 -->

		function changeIssuePurpose() {

			const purpose = document.getElementById("issuePurpose");

			const directInput = document.getElementById("directPurpose");

			if (!purpose || !directInput) {
				return;
			}

			if (purpose.value === "直接入力") {

				directInput.style.display = "inline-block";

				directInput.focus();

			} else {

				directInput.style.display = "none";

				directInput.value = "";

			}

		}

		

		const originalResidentNo = "${emp.residentNo}";

		function toggleResidentNo() {

			const checkbox = document.getElementById("hideResidentNo");

			const cell = document.getElementById("residentNoCell");

			if (!checkbox || !cell) {
				return;
			}

			if (checkbox.checked) {

				if (originalResidentNo && originalResidentNo.includes("-")) {

					const front = originalResidentNo.split("-")[0];

					cell.innerText = front + "-*******";

				} else if (originalResidentNo && originalResidentNo.length >= 6) {

					cell.innerText = originalResidentNo.substring(0, 6)
							+ "-*******";

				} else {

					cell.innerText = "******-*******";

				}

			} else {

				cell.innerText = originalResidentNo;

			}

		}

		

		function setToday() {

			const today = new Date();

			const year = document.getElementById("todayYear");

			const month = document.getElementById("todayMonth");

			const day = document.getElementById("todayDay");

			if (year) {

				year.innerText = today.getFullYear();

			}

			if (month) {

				month.innerText = String(today.getMonth() + 1).padStart(2, "0");

			}

			if (day) {

				day.innerText = String(today.getDate()).padStart(2, "0");

			}

		}

		

		function calculateServicePeriod() {

			const target = document.getElementById("servicePeriod");

			if (!target) {
				return;
			}

			const hireDateValue = "${emp.hireDate}";

			if (!hireDateValue) {

				target.innerText = "-";

				return;
			}

			const hireDate = new Date(hireDateValue);

			const today = new Date();

			let years = today.getFullYear() - hireDate.getFullYear();

			let months = today.getMonth() - hireDate.getMonth();

			if (today.getDate() < hireDate.getDate()) {

				months--;

			}

			if (months < 0) {

				years--;

				months += 12;

			}

			if (years < 0) {

				target.innerText = "-";

				return;
			}

			target.innerText = years + "年 " + months + "か月";

		}

		

		document.addEventListener("change", function(event) {

			if (event.target.id === "showRepresentative") {

				const area = document.getElementById("representativeArea");

				if (!area) {
					return;
				}

				if (event.target.checked) {

					area.style.visibility = "visible";

				} else {

					area.style.visibility = "hidden";

				}

			}

		});

		<!-- 인쇄 처리 / 印刷処理 -->

		function printCertificate() {

			const emptyMessage = document.querySelector(".empty-message");

			if (emptyMessage) {
				alert("先に在職証明書を発行する社員を選択してください。");
				return;
			}

			const issuePurpose = document.getElementById("issuePurpose");

			if (issuePurpose && !issuePurpose.value) {
				alert("発行用途を選択してください。");
				return;
			}

			let purposeValue = issuePurpose.value;

			if (issuePurpose.value === "直接入力") {

				const directPurpose = document.getElementById("directPurpose");

				if (!directPurpose.value.trim()) {
					alert("発行用途を入力してください。");
					directPurpose.focus();
					return;
				}

				purposeValue = directPurpose.value.trim();
			}

			const issueDepartment = document.getElementById("issueDepartment");

			if (issueDepartment && !issueDepartment.value) {
				alert("発行部署を選択してください。");
				return;
			}

			const form = document.createElement("form");

			form.method = "post";
			form.action = "${pageContext.request.contextPath}/emp/certificate/working.do";

			const empIdInput = document.createElement("input");
			empIdInput.type = "hidden";
			empIdInput.name = "empId";
			empIdInput.value = "${emp.empId}";

			const purposeInput = document.createElement("input");
			purposeInput.type = "hidden";
			purposeInput.name = "purpose";
			purposeInput.value = purposeValue;

			const departmentInput = document.createElement("input");
			departmentInput.type = "hidden";
			departmentInput.name = "issueDepartment";
			departmentInput.value = issueDepartment.value;

			form.appendChild(empIdInput);
			form.appendChild(purposeInput);
			form.appendChild(departmentInput);

			document.body.appendChild(form);

			form.submit();
		}

		

		window.addEventListener("DOMContentLoaded", function() {

			setToday();

			calculateServicePeriod();

		});
	</script>
</body>
</html>