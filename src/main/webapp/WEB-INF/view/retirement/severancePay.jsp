<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">

<head>

<meta charset="UTF-8">

<title>ヘキサゴンゾーン - 退職給付入力・管理</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/severancePay.css" />

</head>

<body>

	<layout:headerNav />

	<div class="container">

		<!-- 페이지 상단 제목 및 설명 / ページ上部タイトルおよび説明 -->
		<div class="page-header">

			<h2>🧮 退職給付入力・管理</h2>

			<p>退職社員の退職給付情報を入力・計算・保存するメニューです。</p>

		</div>

		<div class="section-card">

			<div class="top-control">

				<div class="year-area">

					<span>* 支給年度</span> <select name="payYear">

						<c:forEach var="year" begin="2020" end="2030">

							<option value="${year}" ${year == 2026 ? 'selected' : ''}>
								${year}</option>

						</c:forEach>

					</select>

				</div>

				<button type="button" class="btn btn-primary"
					onclick="openEmployeeModal();">＋ 新規追加</button>

			</div>

			<table class="data-table">

				<thead>
					<tr>
						<th>支給日</th>
						<th>区分</th>
						<th>氏名</th>
						<th>役職</th>
						<th>部署</th>
						<th>算定期間</th>
						<th>勤続日数</th>
						<th>実支給額</th>
						<th>支給方法</th>
					</tr>
				</thead>

				<tbody>

					<c:if test="${not empty selectedEmp}">
						<tr class="selected-pay-row">
							<td><c:choose>
									<c:when test="${not empty severanceRequest.paymentDate}">
									${severanceRequest.paymentDate}
								</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose></td>
							<td>${selectedEmp.employmentType}</td>
							<td class="selected-name">${selectedEmp.nameKor}</td>
							<td><c:choose>
									<c:when test="${not empty selectedEmp.positionName}">
									${selectedEmp.positionName}
								</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${not empty selectedEmp.deptName}">
									${selectedEmp.deptName}
								</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${not empty salaryResult.salaryList}">

										<c:forEach var="salary" items="${salaryResult.salaryList}"
											varStatus="status">

											<c:if test="${status.first}">
           										 ${salary.calculationStartDate}
       										</c:if>

											<c:if test="${status.last}">
            									~ ${salary.calculationEndDate}
        								    </c:if>

										</c:forEach>

									</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose></td>
							<td>${serviceDays}日</td>
							<td><c:choose>
									<c:when test="${not empty severanceResult}">
										<fmt:formatNumber value="${severanceResult.netPayment}"
											pattern="#,###" />ウォン
								</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${not empty paymentMethod}">
									${paymentMethod}
								</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose></td>
						</tr>
					</c:if>

					<c:forEach var="pay" items="${severancePayList}">
						<tr>
							<td>${pay.paymentDate}</td>
							<td>${pay.retirementType}</td>
							<td>${pay.nameKor}</td>
							<td>${pay.positionName}</td>
							<td>${pay.deptName}</td>
							<td>${pay.calculationPeriod}</td>
							<td>${pay.serviceDays}</td>
							<td><fmt:formatNumber value="${pay.netPayment}"
									pattern="#,###" /></td>
							<td>${pay.paymentMethod}</td>
						</tr>
					</c:forEach>

					<c:if test="${empty selectedEmp && empty severancePayList}">
						<tr>
							<td colspan="9" style="height: 55px; color: #94a3b8;">保存された退職給付履歴がありません。</td>
						</tr>
					</c:if>

				</tbody>

			</table>

		</div>

		<c:if test="${not empty selectedEmp}">

			<div class="employee-bar">

				<div class="employee-name">${selectedEmp.nameKor}
					${selectedEmp.positionName} 退職計算</div>

				<!-- 선택 사원 정보 / 選択社員情報 -->
				<div class="employee-info">
					<strong>区分</strong> <span class="employee-value"> 退職精算 </span>
				</div>

				<div class="employee-info">
					<strong>入社日</strong> <span class="employee-value">
						${selectedEmp.hireDate} </span>
				</div>

				<div class="employee-info">
					<strong>退職日</strong> <span class="employee-value"> <c:choose>
							<c:when test="${not empty selectedEmp.retireDate}">
                            ${selectedEmp.retireDate}
                        </c:when>
							<c:otherwise>
                            -
                        </c:otherwise>
						</c:choose>

					</span>
				</div>

				<div class="employee-info">
					<strong>勤続年数</strong> <span class="employee-highlight">
						${serviceYears}年 </span>
				</div>

				<div class="employee-info">
					<strong>勤続日数</strong> <span class="employee-highlight">
						${serviceDays}日 </span>
				</div>

			</div>

		</c:if>

		<!-- 퇴직급여 입력 폼 / 退職給付入力フォーム -->
		<form id="severanceForm" method="post">

			<input type="hidden" name="empId" id="selectedEmpId"
				value="${selectedEmp.empId}">

			<div class="two-column">

				<div class="section-card">

					<div class="section-title">

						<div>
							<h3>給与明細</h3>
							<span class="description"> 事由発生日以前の直近3か月支給合計額 </span>
						</div>

						<button type="button" class="btn btn-primary"
							onclick="loadSalary();">給与明細を読み込む</button>

					</div>

					<table class="data-table">

						<thead>
							<tr>
								<th style="width: 48%;">算定期間</th>
								<th style="width: 22%;">算定日数</th>
								<th style="width: 30%;">給与総額</th>
							</tr>
						</thead>

						<tbody>

							<c:choose>

								<c:when test="${not empty salaryResult.salaryList}">

									<c:forEach var="salary" items="${salaryResult.salaryList}">

										<tr>

											<td>
												<div class="period-range">
													<input type="date" value="${salary.calculationStartDate}"
														readonly> <span class="period-separator">~</span>
													<input type="date" value="${salary.calculationEndDate}"
														readonly>
												</div>
											</td>

											<td class="result-cell">${salary.calculationDays}</td>

											<td><fmt:formatNumber value="${salary.calculatedPay}"
													pattern="#,###" /></td>

										</tr>

									</c:forEach>

								</c:when>

								<c:otherwise>

									<c:forEach begin="1" end="4">

										<tr>

											<td>
												<div class="period-range">
													<input type="date" readonly> <span
														class="period-separator">~</span> <input type="date"
														readonly>
												</div>
											</td>

											<td class="result-cell">0</td>

											<td>0</td>

										</tr>

									</c:forEach>

								</c:otherwise>

							</c:choose>

							<tr>

								<th>合計</th>

								<td class="result-cell">${empty salaryResult
                                    ? 0
                                    : salaryResult.totalCalculationDays}
								</td>

								<td class="result-cell"><fmt:formatNumber
										value="${empty salaryResult
                                        ? 0
                                        : salaryResult.salaryTotal}"
										pattern="#,###" /></td>

							</tr>

						</tbody>

					</table>

					<div class="notice">ただし、月途中の日付で計算する場合は、該当月の支給合計額を日数で按分した金額を基準に表示します。</div>

					<table class="data-table input-summary">

						<thead>
							<tr>
								<th>退職慰労金</th>
								<th>解雇予告手当</th>
							</tr>
						</thead>

						<tbody>

							<tr>

								<td><input type="number" name="retirementBonus"
									value="${severanceRequest.retirementBonus}" min="0"></td>

								<td><input type="number" name="dismissalAllowance"
									value="${severanceRequest.dismissalAllowance}" min="0">
								</td>

							</tr>

						</tbody>

					</table>

				</div>

				<div class="section-card">

					<div class="section-title">

						<div>
							<h3>その他課税所得</h3>
							<span class="description"> 事由発生日以前1年間の金額を入力 </span>
						</div>

					</div>

					<table class="data-table">

						<thead>

							<tr>
								<th>支給年月</th>
								<th>支給項目</th>
								<th>金額</th>
								<th>3か月分</th>
							</tr>

						</thead>

						<tbody>

							<c:choose>

								<c:when test="${not empty severanceRequest.otherTaxableIncomes}">

									<c:forEach var="income"
										items="${severanceRequest.otherTaxableIncomes}">

										<tr>

											<td><input type="date" name="otherPaymentMonth"
												value="${income.paymentMonth}"></td>

											<td><input type="text" name="otherPaymentItem"
												value="${income.paymentItem}"></td>

											<td><input type="number" name="otherAmount"
												class="otherAmount" value="${income.amount}" min="0"
												oninput="calculateThreeMonthIncome(this);"></td>

											<td class="result-cell otherThreeMonth"><c:choose>
													<c:when test="${not empty income.amount}">
                                ${income.amount * 3 / 12}
                            </c:when>

													<c:otherwise>
                                0
                            </c:otherwise>
												</c:choose></td>

										</tr>

									</c:forEach>

								</c:when>

								<c:otherwise>

									<c:forEach begin="1" end="5">

										<tr>

											<td><input type="date" name="otherPaymentMonth">
											</td>

											<td><input type="text" name="otherPaymentItem">
											</td>

											<td><input type="number" name="otherAmount"
												class="otherAmount" min="0"
												oninput="calculateThreeMonthIncome(this);"></td>

											<td class="result-cell otherThreeMonth">0</td>

										</tr>

									</c:forEach>

								</c:otherwise>

							</c:choose>

						</tbody>

					</table>

					<table class="data-table input-summary">

						<thead>

							<tr>
								<th>非課税退職給付</th>
								<th>既納付（または既課税繰延）税額</th>
								<th>税額控除</th>
							</tr>

						</thead>

						<tbody>

							<tr>

								<td><input type="number" name="nonTaxableRetirementPay"
									value="${severanceRequest.nonTaxableRetirementPay}" min="0">
								</td>

								<td><input type="number" name="prepaidTax"
									value="${severanceRequest.prepaidTax}" min="0"></td>

								<td><input type="number" name="taxCredit"
									value="${severanceRequest.taxCredit}" min="0"></td>

							</tr>

						</tbody>

					</table>

				</div>

			</div>

			<div class="section-card">

				<div class="section-title">

					<div>
						<h3>課税繰延口座</h3>
						<span class="description"> 該当事項がない場合は入力しません。 </span>
					</div>

				</div>

				<table class="data-table">

					<thead>

						<tr>
							<th>退職年金事業者名</th>
							<th>事業者登録番号</th>
							<th>口座番号</th>
							<th>入金（振込）日</th>
							<th>口座入金額</th>
						</tr>

					</thead>

					<tbody>

						<c:choose>

							<c:when test="${not empty severanceRequest.taxDeferredAccounts}">

								<c:forEach var="account"
									items="${severanceRequest.taxDeferredAccounts}">

									<tr>

										<td><input type="text" name="pensionProviderName"
											value="${account.pensionProviderName}"></td>

										<td><input type="text" name="businessRegistrationNo"
											value="${account.businessRegistrationNo}"></td>

										<td><input type="text" name="accountNo"
											value="${account.accountNo}"></td>

										<td><input type="date" name="depositDate"
											value="${account.depositDate}"></td>

										<td><input type="number" name="depositAmount"
											value="${account.depositAmount}" min="0"></td>

									</tr>

								</c:forEach>

							</c:when>

							<c:otherwise>

								<c:forEach begin="1" end="2">

									<tr>

										<td><input type="text" name="pensionProviderName">
										</td>

										<td><input type="text" name="businessRegistrationNo">
										</td>

										<td><input type="text" name="accountNo"></td>

										<td><input type="date" name="depositDate"></td>

										<td><input type="number" name="depositAmount" min="0">
										</td>

									</tr>

								</c:forEach>

							</c:otherwise>

						</c:choose>

					</tbody>

				</table>

				<div class="notice">課税繰延口座がある場合のみ入力してください。</div>

			</div>

			<div class="calculate-area">

				<button type="button" class="btn btn-calculate"
					onclick="calculateSeverance();">退職金を計算</button>

			</div>

			<c:if test="${not empty severanceResult}">

				<div class="section-card">

					<table class="data-table result-table">

						<thead>

							<tr>
								<th>3か月合計</th>
								<th>1日平均賃金</th>
								<th>1日通常賃金</th>
								<th>退職所得</th>
								<th>退職日が属する課税年度</th>
								<th>算出税額</th>
							</tr>

						</thead>

						<tbody>

							<tr>

								<td class="result-cell result-value"><fmt:formatNumber
										value="${severanceResult.threeMonthTotal}" pattern="#,###" />
								</td>

								<td class="result-cell result-value"><fmt:formatNumber
										value="${severanceResult.dailyAverageWage}" pattern="#,###" />
								</td>

								<td><input type="number" name="dailyOrdinaryWage"
									value="${severanceRequest.dailyOrdinaryWage}" min="0">
								</td>

								<td class="result-cell result-value"><fmt:formatNumber
										value="${severanceResult.retirementIncome}" pattern="#,###" />
								</td>

								<td class="result-cell result-value">
									${severanceResult.retirementTaxYear}</td>

								<td class="result-cell result-value"><fmt:formatNumber
										value="${severanceResult.calculatedTax}" pattern="#,###" /></td>

							</tr>

						</tbody>

					</table>

					<table class="data-table result-table">

						<thead>

							<tr>
								<th>退職所得税</th>
								<th>地方所得税</th>
								<th>繰延退職所得税</th>
								<th>繰延地方所得税</th>
								<th>農漁村特別税</th>
								<th>その他控除</th>
							</tr>

						</thead>

						<tbody>

							<tr>

								<td class="result-cell"><fmt:formatNumber
										value="${severanceResult.retirementIncomeTax}" pattern="#,###" />
								</td>

								<td class="result-cell"><fmt:formatNumber
										value="${severanceResult.localIncomeTax}" pattern="#,###" />
								</td>

								<td class="result-cell"><fmt:formatNumber
										value="${severanceResult.deferredRetirementTax}"
										pattern="#,###" /></td>

								<td class="result-cell"><fmt:formatNumber
										value="${severanceResult.deferredLocalTax}" pattern="#,###" />
								</td>

								<td><input type="number" name="ruralSpecialTax"
									value="${severanceRequest.ruralSpecialTax}" min="0"></td>

								<td><input type="number" name="otherDeduction"
									value="${severanceRequest.otherDeduction}" min="0"></td>

							</tr>

						</tbody>

					</table>

					<table class="data-table final-result-table result-table">

						<thead>

							<tr>
								<th>課税対象退職給付</th>
								<th>差引源泉徴収税額</th>
								<th>実受取額</th>
								<th>支給方法</th>
								<th>支給日</th>
							</tr>

						</thead>

						<tbody>

							<tr>

								<td><span class="final-price"> <fmt:formatNumber
											value="${severanceResult.taxableRetirementPay}"
											pattern="#,###" /> ウォン

								</span></td>

								<td><span class="final-price"> <fmt:formatNumber
											value="${severanceResult.withholdingTax}" pattern="#,###" />

										ウォン

								</span></td>

								<td><span class="final-price"> <fmt:formatNumber
											value="${severanceResult.netPayment}" pattern="#,###" /> ウォン

								</span></td>

								<td><c:choose>

										<c:when
											test="${not empty empInfoSalary.salaryBank
                        							&& not empty empInfoSalary.salaryAccount}">

											<input type="text" name="paymentMethod"
												value="${empInfoSalary.salaryBank} ${empInfoSalary.salaryAccount}"
												readonly>

										</c:when>

										<c:otherwise>

											<input type="text" name="paymentMethod" placeholder="支給方法">

										</c:otherwise>

									</c:choose></td>

								<td><input type="date" name="paymentDate"
									value="${severanceRequest.paymentDate}"></td>

							</tr>

						</tbody>

					</table>

					<input type="hidden" name="threeMonthTotal"
						value="${severanceResult.threeMonthTotal}"> <input
						type="hidden" name="dailyAverageWage"
						value="${severanceResult.dailyAverageWage}"> <input
						type="hidden" name="retirementTaxYear"
						value="${severanceResult.retirementTaxYear}"> <input
						type="hidden" name="retirementIncome"
						value="${severanceResult.retirementIncome}"> <input
						type="hidden" name="calculatedTax"
						value="${severanceResult.calculatedTax}"> <input
						type="hidden" name="retirementIncomeTax"
						value="${severanceResult.retirementIncomeTax}"> <input
						type="hidden" name="localIncomeTax"
						value="${severanceResult.localIncomeTax}"> <input
						type="hidden" name="deferredRetirementTax"
						value="${severanceResult.deferredRetirementTax}"> <input
						type="hidden" name="deferredLocalTax"
						value="${severanceResult.deferredLocalTax}"> <input
						type="hidden" name="taxableRetirementPay"
						value="${severanceResult.taxableRetirementPay}"> <input
						type="hidden" name="withholdingTax"
						value="${severanceResult.withholdingTax}"> <input
						type="hidden" name="netPayment"
						value="${severanceResult.netPayment}">

					<div class="bottom-buttons">

						<button type="button" class="btn btn-primary btn-save"
							onclick="saveSeverance();">保存</button>

						<button type="button" class="btn btn-cancel"
							onclick="location.reload();">キャンセル</button>

					</div>

				</div>

			</c:if>

		</form>

	</div>

	<!-- 사원 선택 모달 / 社員選択モーダル -->
	<div id="employeeModal" class="employee-modal">

		<div class="employee-modal-content">

			<h2>退職給付支給 社員選択</h2>

			<div class="modal-search">

				<input type="text" id="employeeSearchKeyword" placeholder="社員検索"
					onkeyup="filterEmployees();">

				<button type="button" class="btn btn-outline"
					onclick="clearEmployeeSearch();">▣ 全件表示</button>

			</div>

			<div class="modal-table-wrap">

				<table class="modal-table" id="employeeModalTable">

					<thead>

						<tr>
							<th style="width: 45px;"></th>
							<th>区分</th>
							<th>社員番号</th>
							<th>氏名</th>
							<th>部署</th>
							<th>役職</th>
							<th>状態</th>
						</tr>

					</thead>

					<tbody>

						<c:forEach var="emp" items="${employeeList}">

							<tr onclick="selectEmployeeRow(this);">

								<td><input type="radio" name="modalEmp"
									value="${emp.empId}" data-name="${emp.nameKor}"
									data-dept="${emp.deptName}" data-position="${emp.positionName}"
									data-hire-date="${emp.hireDate}"
									data-retire-date="${emp.retireDate}"></td>

								<td>${emp.employmentType}</td>

								<td>No-${emp.empId}</td>

								<td class="employee-search-name">${emp.nameKor}</td>

								<td>${emp.deptName}</td>

								<td>${emp.positionName}</td>

								<td><c:choose>

										<c:when test="${emp.status == 'Y'}">
                                        在職
                                    </c:when>

										<c:otherwise>
                                        退職
                                    </c:otherwise>

									</c:choose></td>

							</tr>

						</c:forEach>

					</tbody>

				</table>

			</div>

			<div class="modal-button-area">

				<button type="button" class="btn btn-primary"
					onclick="confirmEmployeeSelection();">社員選択</button>

				<button type="button" class="btn btn-outline"
					onclick="closeEmployeeModal();">選択キャンセル</button>

			</div>

		</div>

	</div>

	<layout:footer />

	<!-- 사원 선택 및 퇴직급여 계산·저장 처리 / 社員選択および退職給付計算・保存処理 -->
	<script>
		function openEmployeeModal() {

			document.getElementById("employeeModal").style.display = "flex";
		}

		function closeEmployeeModal() {

			document.getElementById("employeeModal").style.display = "none";
		}

		function selectEmployeeRow(row) {

			const radio = row.querySelector('input[name="modalEmp"]');

			if (!radio) {
				return;
			}

			radio.checked = true;

			document.querySelectorAll("#employeeModalTable tbody tr").forEach(
					function(tr) {

						tr.classList.remove("selected");
					});

			row.classList.add("selected");
		}

		function confirmEmployeeSelection() {

			const selected = document
					.querySelector('input[name="modalEmp"]:checked');

			if (!selected) {

				alert("社員を選択してください。");

				return;
			}

			const empId = selected.value;

			location.href = "${pageContext.request.contextPath}"
					+ "/retirement/severance.do?empId="
					+ encodeURIComponent(empId);
		}

		function filterEmployees() {

			const keyword = document.getElementById("employeeSearchKeyword").value
					.toLowerCase();

			const rows = document
					.querySelectorAll("#employeeModalTable tbody tr");

			rows.forEach(function(row) {

				const text = row.innerText.toLowerCase();

				row.style.display = text.includes(keyword) ? "" : "none";
			});
		}

		function clearEmployeeSearch() {

			document.getElementById("employeeSearchKeyword").value = "";

			filterEmployees();
		}

		function loadSalary() {

			const empId = document.getElementById("selectedEmpId").value;

			if (!empId) {

				alert("先に新規追加から社員を選択してください。");

				return;
			}

			const form = document.getElementById("severanceForm");

			form.action = "${pageContext.request.contextPath}"
					+ "/retirement/severance.do";
			form.method = "post";

			form.submit();
		}

		function calculateThreeMonthIncome(input) {

			const amount = Number(input.value || 0);

			const threeMonthAmount = Math.floor(amount * 3 / 12);

			const row = input.closest("tr");

			const resultCell = row.querySelector(".otherThreeMonth");

			resultCell.innerText = threeMonthAmount.toLocaleString();
		}

		function calculateSeverance() {

			const empId = document.getElementById("selectedEmpId").value;

			if (!empId) {

				alert("先に社員を選択してください。");

				return;
			}

			const form = document.getElementById("severanceForm");

			form.action = "${pageContext.request.contextPath}"
					+ "/retirement/severance/calculate.do";
			form.method = "post";

			form.submit();
		}

		function saveSeverance() {

			if (!confirm("計算された退職給付情報を保存しますか？")) {
				return;
			}

			const form = document.getElementById("severanceForm");

			form.action = "${pageContext.request.contextPath}"
					+ "/retirement/severance/save.do";
			form.method = "post";

			form.submit();
		}

		window.addEventListener("click", function(event) {

			const modal = document.getElementById("employeeModal");

			if (event.target === modal) {
				closeEmployeeModal();
			}
		});

		document.addEventListener("keydown", function(event) {

			if (event.key === "Escape") {
				closeEmployeeModal();
			}
		});

		<c:if test="${saveSuccess}">
		alert("退職給付が保存されました。");
		</c:if>

		<c:if test="${not empty salaryLoadError}">
		alert("${salaryLoadError}");
		</c:if>
	</script>

</body>
</html>