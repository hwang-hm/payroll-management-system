<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">

<title>ヘキサゾーン - 退職給付明細書</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/certificate.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/specification.css" />

</head>

<body>

	<layout:headerNav />

	<main class="certificate-container">

		<div class="page-hero">
			<h1>📄 退職給付明細書</h1>
			<p>社員を選択すると、該当社員の退職給付明細書を確認できます。</p>
		</div>

		<div class="certificate-layout">

			<section class="employee-panel no-print">

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
							<th>状態</th>
						</tr>
					</thead>

					<tbody>

						<c:forEach var="employee" items="${empListPage.empListData}">

							<c:choose>

								<c:when test="${employee.status == 'N'}">

									<tr
										class="${selectedEmpId == employee.empId ? 'selected' : ''}"
										onclick="selectEmployee('${employee.empId}', '${empListPage.currentPage}');"
										style="cursor: pointer;">
								</c:when>

								<c:otherwise>

									<tr class="disabled-employee"
										onclick="alert('退職した社員のみ退職給付明細書を照会できます。');">
								</c:otherwise>

							</c:choose>

							<td><c:out value="${employee.employmentType}" /></td>
							<td><c:out value="${employee.nameKor}" /></td>
							<td><c:out value="${employee.deptName}" /></td>>

							<td><c:choose>

									<c:when test="${employee.status == 'Y'}">
										<span class="status-working">在職</span>
									</c:when>

									<c:otherwise>
										<span class="status-retired">退職</span>
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
								href="${pageContext.request.contextPath}/retirement/specification.do?pageNo=${page}"
								class="${empListPage.currentPage == page ? 'active' : ''}">
								${page} </a>

						</c:forEach>

					</div>

				</c:if>

			</section>

			<section class="preview-panel">

				<div class="certificate-preview-area">

					<c:choose>

						<c:when test="${not empty specificationData}">

							<div class="specification-paper">

								<div class="spec-title">退職給付明細書</div>

								<div class="spec-section">

									<!-- 사원정보 / 社員情報 -->
									<div class="spec-section-title">社員情報</div>

									<table class="spec-table">

										<tr>

											<th>氏名</th>

											<td><c:out value="${specificationData.emp.nameKor}" />
											</td>

											<th>入社日</th>

											<td><fmt:formatDate
													value="${specificationData.emp.hireDate}"
													pattern="yyyy-MM-dd" /></td>

										</tr>

										<tr>

											<th>部署</th>

											<td><c:out value="${specificationData.emp.deptName}" />
											</td>

											<th>退職日</th>

											<td><fmt:formatDate
													value="${specificationData.emp.retireDate}"
													pattern="yyyy-MM-dd" /></td>

										</tr>

										<tr>

											<th>役職</th>

											<td><c:out value="${specificationData.emp.positionName}" />
											</td>

											<th>勤続日数</th>

											<td id="serviceDays">-</td>

										</tr>

									</table>

								</div>

								<div class="spec-section">

									<!-- 급여내역 / 給与明細 -->
									<div class="spec-section-title">給与明細</div>

									<table class="spec-table">

										<tr>

											<th class="label">算定期間</th>

											<c:forEach var="salary"
												items="${specificationData.salaryList}">

												<td class="salary-period">

													<div>
														<fmt:formatDate value="${salary.startDate}"
															pattern="yyyy-MM-dd" />
													</div>

													<div>
														<fmt:formatDate value="${salary.endDate}"
															pattern="yyyy-MM-dd" />
													</div>

												</td>

											</c:forEach>

											<th>計</th>

										</tr>

										<tr>

											<th>算定日数</th>

											<c:forEach var="salary"
												items="${specificationData.salaryList}">

												<td><fmt:formatNumber value="${salary.calculationDays}"
														pattern="#,###" /></td>

											</c:forEach>

											<td class="result"><fmt:formatNumber
													value="${specificationData.totalCalculationDays}"
													pattern="#,###" /></td>

										</tr>

										<tr>

											<th>給与総額</th>

											<c:forEach var="salary"
												items="${specificationData.salaryList}">

												<td><fmt:formatNumber value="${salary.calculatedPay}"
														pattern="#,###" /></td>

											</c:forEach>

											<td class="result"><fmt:formatNumber
													value="${specificationData.totalSalary}" pattern="#,###" />

											</td>

										</tr>

									</table>

								</div>

								<div class="spec-section">

									<!-- 기타소득 / その他所得 -->
									<div class="spec-section-title">その他所得</div>

									<table class="spec-table extra-income-table">

										<thead>

											<tr>
												<th>支給項目</th>
												<th>1年間支給額</th>
												<th>3か月分</th>
											</tr>

										</thead>

										<tbody>

											<c:forEach var="income"
												items="${specificationData.otherTaxableIncome}">

												<tr>

													<td><c:out value="${income.paymentItem}" /></td>

													<td><fmt:formatNumber value="${income.amount}"
															pattern="#,###" /></td>

													<td class="result"><fmt:formatNumber
															value="${income.amount * 3 / 12}" pattern="#,###" /></td>

												</tr>

											</c:forEach>

											<c:if test="${empty specificationData.otherTaxableIncome}">

												<tr>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td class="result">&nbsp;</td>
												</tr>

												<tr>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td class="result">&nbsp;</td>
												</tr>

												<tr>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td class="result">&nbsp;</td>
												</tr>

											</c:if>

										</tbody>

									</table>

								</div>

								<div class="spec-section">

									<table class="spec-table allowance-table">

										<tr>

											<th>退職慰労金</th>

											<td><c:choose>

													<c:when test="${not empty specificationData.severancePay}">

														<fmt:formatNumber
															value="${specificationData.severancePay.retirementBonus}"
															pattern="#,###" />

													</c:when>

													<c:otherwise>
														0
													</c:otherwise>

												</c:choose></td>

											<th>解雇予告手当</th>

											<td><c:choose>

													<c:when test="${not empty specificationData.severancePay}">

														<fmt:formatNumber
															value="${specificationData.severancePay.dismissalAllowance}"
															pattern="#,###" />

													</c:when>

													<c:otherwise>
														0
													</c:otherwise>

												</c:choose></td>

										</tr>

									</table>

								</div>

								<div class="spec-section">

									<!-- 퇴직소득 / 退職所得 -->
									<div class="spec-section-title">退職所得</div>

									<table class="spec-table retirement-income-table">

										<tr>

											<th rowspan="2">1日平均賃金</th>

											<th>3か月合計</th>

											<td class="formula">給与総額計 + 3か月分その他所得計</td>

											<td class="result"><fmt:formatNumber
													value="${specificationData.totalSalary + specificationData.totalOtherIncomeThreeMonths}"
													pattern="#,###" /></td>

										</tr>

										<tr>

											<th>1日平均賃金</th>

											<td class="formula">3か月合計 / 算定日数</td>

											<td class="result"><fmt:formatNumber
													value="${specificationData.dailyAverageWage}"
													pattern="#,###" /></td>

										</tr>

										<tr>

											<th>1日通常賃金</th>

											<td colspan="2" class="formula">1日通常賃金が1日平均賃金より高い場合
												1日通常賃金を適用</td>

											<td class="result"><c:choose>

													<c:when test="${not empty specificationData.severancePay}">

														<fmt:formatNumber
															value="${specificationData.severancePay.dailyOrdinaryWage}"
															pattern="#,###" />

													</c:when>

													<c:otherwise>
														0
													</c:otherwise>

												</c:choose></td>

										</tr>

										<tr>

											<th>退職所得</th>

											<td colspan="2" class="formula">（適用1日賃金 × 30日 × 勤続日数 /
												365) + 退職慰労金 + 解雇予告手当</td>

											<td class="result"><c:choose>

													<c:when test="${not empty specificationData.severancePay}">

														<fmt:formatNumber
															value="${specificationData.severancePay.retirementIncome}"
															pattern="#,###" />

													</c:when>

													<c:otherwise>
														0
													</c:otherwise>

												</c:choose></td>

										</tr>

									</table>

								</div>

								<div class="spec-section">

									<!-- 공제내역 / 控除内訳 -->
									<div class="spec-section-title">控除内訳</div>

									<table class="spec-table deduction-table">

										<tr>

											<th>退職所得税</th>
											<th>地方所得税</th>
											<th>その他控除</th>
											<th>控除総額</th>

										</tr>

										<tr>

											<td><c:choose>

													<c:when test="${not empty specificationData.severancePay}">

														<fmt:formatNumber
															value="${specificationData.severancePay.retirementIncomeTax}"
															pattern="#,###" />

													</c:when>

													<c:otherwise>
														0
													</c:otherwise>

												</c:choose></td>

											<td><c:choose>

													<c:when test="${not empty specificationData.severancePay}">

														<fmt:formatNumber
															value="${specificationData.severancePay.localIncomeTax}"
															pattern="#,###" />

													</c:when>

													<c:otherwise>
														0
													</c:otherwise>

												</c:choose></td>

											<td><c:choose>

													<c:when test="${not empty specificationData.severancePay}">

														<fmt:formatNumber
															value="${specificationData.severancePay.otherDeduction}"
															pattern="#,###" />

													</c:when>

													<c:otherwise>
														0
													</c:otherwise>

												</c:choose></td>

											<td class="result"><fmt:formatNumber
													value="${specificationData.totalDeduction}" pattern="#,###" />

											</td>

										</tr>

									</table>

								</div>

								<div class="spec-section">

									<table class="spec-table net-payment-table">

										<tr>

											<th>実受取額</th>

											<td class="description">退職給付 - 控除総額</td>

											<td class="result"><c:choose>

													<c:when test="${not empty specificationData.severancePay}">

														<fmt:formatNumber
															value="${specificationData.severancePay.netPayment}"
															pattern="#,###" />

													</c:when>

													<c:otherwise>
														0
													</c:otherwise>

												</c:choose></td>

										</tr>

									</table>

								</div>

								<div class="spec-footer-message">上記金額を当該者の退職金精算額として受領しました。</div>

								<div class="spec-date">

									<span id="todayYear"></span>年 <span id="todayMonth"></span>月 <span
										id="todayDay"></span>日

								</div>

								<div class="spec-company">

									株式会社ヘキサゴンゾーン<br> 代表取締役

								</div>

							</div>

						</c:when>

						<c:otherwise>

							<div class="specification-paper empty-specification">

								退職給付明細書を確認する社員を選択してください。</div>

						</c:otherwise>

					</c:choose>

				</div>

				<div class="action-area no-print">

					<button type="button" class="print-btn"
						onclick="printSpecification();">印刷</button>

				</div>

			</section>

		</div>

	</main>

	<layout:footer />

	<!-- 사원 선택 및 명세서 출력 처리 / 社員選択および明細書印刷処理 -->
	<script>
		function selectEmployee(empId, pageNo) {

			location.href = "${pageContext.request.contextPath}"
					+ "/retirement/specification.do" + "?empId="
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

		function showAllEmployee() {

			document.getElementById("searchKeyword").value = "";

			const rows = document.querySelectorAll(".employee-table tbody tr");

			rows.forEach(function(row) {

				row.style.display = "";

			});

		}

		function calculateServiceDays() {

			const target = document.getElementById("serviceDays");

			if (!target) {
				return;
			}

			const hireDateValue = "${specificationData.emp.hireDate}";

			const retireDateValue = "${specificationData.emp.retireDate}";

			if (!hireDateValue || !retireDateValue) {

				target.innerText = "-";
				return;

			}

			const hireDate = new Date(hireDateValue);
			const retireDate = new Date(retireDateValue);

			const oneDay = 1000 * 60 * 60 * 24;

			const diffTime = retireDate.getTime() - hireDate.getTime();

			const serviceDays = Math.floor(diffTime / oneDay) + 1;

			target.innerText = serviceDays.toLocaleString() + "日";

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

		function printSpecification() {

			const paper = document.querySelector(".specification-paper");

			const empty = document.querySelector(".empty-specification");

			if (!paper || empty) {

				alert("先に退職給付明細書を確認する社員を選択してください。");

				return;

			}

			window.print();

		}

		window.addEventListener("DOMContentLoaded", function() {

			setToday();
			calculateServiceDays();

		});
	</script>

</body>
</html>