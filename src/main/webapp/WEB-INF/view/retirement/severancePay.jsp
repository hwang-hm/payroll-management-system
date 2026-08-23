<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ko">

<head>

<meta charset="UTF-8">

<title>퇴직급여 입력/관리 - 헥사곤존</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/severancePay.css"/>

</head>

<body>

	<layout:headerNav />

	<div class="container">


		<!-- =========================
         제목
    ========================== -->
		<div class="page-header">

			<h2>🧮 퇴직급여 입력/관리</h2>

			<p>퇴직사원에 대한 퇴직급여 정보를 입력, 계산, 저장하는 메뉴입니다.</p>

		</div>


		<!-- =========================
         지급년도 / 신규추가
    ========================== -->
		<div class="section-card">

			<div class="top-control">

				<div class="year-area">

					<span>* 지급년도</span> <select name="payYear">

						<c:forEach var="year" begin="2020" end="2030">

							<option value="${year}" ${year == 2026 ? 'selected' : ''}>
								${year}</option>

						</c:forEach>

					</select>

				</div>


				<button type="button" class="btn btn-primary"
					onclick="openEmployeeModal();">＋ 신규추가</button>

			</div>


			<!-- 저장된 퇴직급여 목록 영역 -->
			<table class="data-table">

				<thead>
					<tr>
						<th>지급일</th>
						<th>구분</th>
						<th>성명</th>
						<th>직위</th>
						<th>부서</th>
						<th>산정기간</th>
						<th>근속일수</th>
						<th>실지급액</th>
						<th>지급방법</th>
					</tr>
				</thead>

				<tbody>

					<%-- 선택한 사원은 저장 전이라도 상단 표에 표시 --%>
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
							<td>${serviceDays}일</td>
							<td><c:choose>
									<c:when test="${not empty severanceResult}">
										<fmt:formatNumber value="${severanceResult.netPayment}"
											pattern="#,###" />원
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

					<%-- DB에 저장된 퇴직급여 목록 --%>
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
							<td colspan="9" style="height: 55px; color: #94a3b8;">저장된
								퇴직급여 내역이 없습니다.</td>
						</tr>
					</c:if>

				</tbody>

			</table>

		</div>



		<!-- =========================
         선택 사원
    ========================== -->

		<c:if test="${not empty selectedEmp}">

			<div class="employee-bar">

				<div class="employee-name">${selectedEmp.nameKor}
					${selectedEmp.positionName} 퇴직계산</div>

				<div class="employee-info">
					<strong>구분</strong> <span class="employee-value"> 퇴직정산 </span>
				</div>

				<div class="employee-info">
					<strong>입사일</strong> <span class="employee-value">
						${selectedEmp.hireDate} </span>
				</div>

				<div class="employee-info">
					<strong>퇴직일</strong> <span class="employee-value"> <c:choose>
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
					<strong>근속연수</strong> <span class="employee-highlight">
						${serviceYears}년 </span>
				</div>

				<div class="employee-info">
					<strong>근속일수</strong> <span class="employee-highlight">
						${serviceDays}일 </span>
				</div>

			</div>

		</c:if>



		<!-- =========================
         전체 입력 form
    ========================== -->

		<form id="severanceForm" method="post">

			<input type="hidden" name="empId" id="selectedEmpId"
				value="${selectedEmp.empId}">


			<!-- =========================
             급여 + 기타과세소득
        ========================== -->

			<div class="two-column">


				<!-- 급여내역 -->
				<div class="section-card">

					<div class="section-title">

						<div>
							<h3>급여내역</h3>
							<span class="description"> 사유발생일 이전 최근 3개월 지급합계 금액 </span>
						</div>


						<button type="button" class="btn btn-primary"
							onclick="loadSalary();">급여내역 불러오기</button>

					</div>


					<table class="data-table">

						<thead>
							<tr>
								<th style="width: 48%;">산정기간</th>
								<th style="width: 22%;">산정일수</th>
								<th style="width: 30%;">급여총액</th>
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

								<th>총 합계</th>

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


					<div class="notice">단, 중간일자 계산일 경우 해당월의 지급합계 금액에서 일수로 나눈 값을
						기준으로 표시</div>


					<!-- 퇴직위로금 / 해고예고수당 -->
					<table class="data-table input-summary">

						<thead>
							<tr>
								<th>퇴직위로금</th>
								<th>해고예고수당</th>
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



				<!-- 기타 과세소득 -->
				<div class="section-card">

					<div class="section-title">

						<div>
							<h3>기타 과세소득</h3>
							<span class="description"> 사유 발생일 이전 1년간분의 금액 입력 </span>
						</div>

					</div>


					<table class="data-table">

						<thead>

							<tr>
								<th>지급년월</th>
								<th>지급항목</th>
								<th>금액</th>
								<th>3개월분</th>
							</tr>

						</thead>

						<tbody>

							<c:choose>

								<%-- 계산 전에 입력한 값이 있는 경우 --%>
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


								<%-- 처음 화면 --%>
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


					<!-- 비과세 / 기납부 / 세액공제 -->
					<table class="data-table input-summary">

						<thead>

							<tr>
								<th>비과세 퇴직급여</th>
								<th>기납부(또는 기과세이연) 세액</th>
								<th>세액공제</th>
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



			<!-- =========================
             과세이연계좌
        ========================== -->

			<div class="section-card">

				<div class="section-title">

					<div>
						<h3>과세이연계좌</h3>
						<span class="description"> 해당 사항이 없는 경우 입력하지 않습니다. </span>
					</div>

				</div>


				<table class="data-table">

					<thead>

						<tr>
							<th>퇴직연금사업자명</th>
							<th>사업자등록번호</th>
							<th>계좌번호</th>
							<th>입금(이체)일</th>
							<th>계좌입금금액</th>
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


				<div class="notice">과세이연계좌가 있는 경우에만 입력해주세요.</div>

			</div>



			<!-- =========================
             퇴직금 계산하기
        ========================== -->

			<div class="calculate-area">

				<button type="button" class="btn btn-calculate"
					onclick="calculateSeverance();">퇴직금 계산하기</button>

			</div>



			<!-- =========================
             계산 결과
        ========================== -->

			<c:if test="${not empty severanceResult}">

				<div class="section-card">

					<table class="data-table result-table">

						<thead>

							<tr>
								<th>3개월 총계</th>
								<th>1일 평균임금</th>
								<th>1일 통상임금</th>
								<th>퇴직소득</th>
								<th>퇴직일이 속하는 과세연도</th>
								<th>산출세액</th>
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
								<th>퇴직소득세</th>
								<th>지방소득세</th>
								<th>이연 퇴직소득세</th>
								<th>이연 지방소득세</th>
								<th>농어촌특별세</th>
								<th>기타공제</th>
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


					<!-- 최종 결과 -->

					<table class="data-table final-result-table result-table">

						<thead>

							<tr>
								<th>과세대상 퇴직급여</th>
								<th>차감원천징수세액</th>
								<th>실수령액</th>
								<th>지급방법</th>
								<th>지급일</th>
							</tr>

						</thead>

						<tbody>

							<tr>

								<td><span class="final-price"> <fmt:formatNumber
											value="${severanceResult.taxableRetirementPay}"
											pattern="#,###" /> 원

								</span></td>

								<td><span class="final-price"> <fmt:formatNumber
											value="${severanceResult.withholdingTax}" pattern="#,###" />

										원

								</span></td>

								<td><span class="final-price"> <fmt:formatNumber
											value="${severanceResult.netPayment}" pattern="#,###" /> 원

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

											<input type="text" name="paymentMethod" placeholder="지급방법">

										</c:otherwise>

									</c:choose></td>

								<td><input type="date" name="paymentDate"
									value="${severanceRequest.paymentDate}"></td>

							</tr>

						</tbody>

					</table>



					<!-- 저장에 필요한 계산결과 -->
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
							onclick="saveSeverance();">저장</button>

						<button type="button" class="btn btn-cancel"
							onclick="location.reload();">취소</button>

					</div>

				</div>

			</c:if>


		</form>

	</div>



	<!-- =========================================================
     사원 선택 모달
========================================================= -->

	<div id="employeeModal" class="employee-modal">

		<div class="employee-modal-content">

			<h2>퇴직급여지급 사원선택</h2>


			<div class="modal-search">

				<input type="text" id="employeeSearchKeyword" placeholder="사원검색"
					onkeyup="filterEmployees();">

				<button type="button" class="btn btn-outline"
					onclick="clearEmployeeSearch();">▣ 전체보기</button>

			</div>


			<div class="modal-table-wrap">

				<table class="modal-table" id="employeeModalTable">

					<thead>

						<tr>
							<th style="width: 45px;"></th>
							<th>구분</th>
							<th>사원번호</th>
							<th>성명</th>
							<th>부서</th>
							<th>직위</th>
							<th>상태</th>
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
                                        재직
                                    </c:when>

										<c:otherwise>
                                        퇴직
                                    </c:otherwise>

									</c:choose></td>

							</tr>

						</c:forEach>

					</tbody>

				</table>

			</div>


			<div class="modal-button-area">

				<button type="button" class="btn btn-primary"
					onclick="confirmEmployeeSelection();">사원선택</button>

				<button type="button" class="btn btn-outline"
					onclick="closeEmployeeModal();">선택취소</button>

			</div>

		</div>

	</div>


	<layout:footer />


	<script>
		/* =========================
		 신규추가 - 사원선택
		 ========================= */

		function openEmployeeModal() {

			document.getElementById("employeeModal").style.display = "flex";
		}

		function closeEmployeeModal() {

			document.getElementById("employeeModal").style.display = "none";
		}

		/* 사원행 클릭 */
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

		/* 사원선택 완료 */
		function confirmEmployeeSelection() {

			const selected = document
					.querySelector('input[name="modalEmp"]:checked');

			if (!selected) {

				alert("사원을 선택해주세요.");

				return;
			}

			const empId = selected.value;

			/*
			   사원 선택 후 서버로 이동
			   GET Handler에서 selectedEmp를 조회해
			   다시 JSP에 전달
			 */
			location.href = "${pageContext.request.contextPath}"
					+ "/retirement/severance.do?empId="
					+ encodeURIComponent(empId);
		}

		/* 검색 */
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

		/* =========================
		 급여내역 불러오기
		 ========================= */

		function loadSalary() {

			const empId = document.getElementById("selectedEmpId").value;

			if (!empId) {

				alert("먼저 신규추가에서 사원을 선택해주세요.");

				return;
			}

			const form = document.getElementById("severanceForm");

			form.action = "${pageContext.request.contextPath}"
					+ "/retirement/severance.do";
			form.method = "post";

			form.submit();
		}

		/* =========================
		 기타과세소득 3개월분
		 ========================= */

		function calculateThreeMonthIncome(input) {

			const amount = Number(input.value || 0);

			const threeMonthAmount = Math.floor(amount * 3 / 12);

			const row = input.closest("tr");

			const resultCell = row.querySelector(".otherThreeMonth");

			resultCell.innerText = threeMonthAmount.toLocaleString();
		}

		/* =========================
		 퇴직금 계산
		 ========================= */

		function calculateSeverance() {

			const empId = document.getElementById("selectedEmpId").value;

			if (!empId) {

				alert("먼저 사원을 선택해주세요.");

				return;
			}

			const form = document.getElementById("severanceForm");

			form.action = "${pageContext.request.contextPath}"
					+ "/retirement/severance/calculate.do";
			form.method = "post";

			form.submit();
		}

		/* =========================
		 저장
		 ========================= */

		function saveSeverance() {

			if (!confirm("계산된 퇴직급여 정보를 저장하시겠습니까?")) {
				return;
			}

			const form = document.getElementById("severanceForm");

			form.action = "${pageContext.request.contextPath}"
					+ "/retirement/severance/save.do";
			form.method = "post";

			form.submit();
		}

		/* 모달 바깥 클릭 */
		window.addEventListener("click", function(event) {

			const modal = document.getElementById("employeeModal");

			if (event.target === modal) {
				closeEmployeeModal();
			}
		});

		/* ESC */
		document.addEventListener("keydown", function(event) {

			if (event.key === "Escape") {
				closeEmployeeModal();
			}
		});

		/* 저장 성공 */
		<c:if test="${saveSuccess}">
		alert("퇴직급여가 저장되었습니다.");
		</c:if>

		/* 오류 */
		<c:if test="${not empty salaryLoadError}">
		alert("${salaryLoadError}");
		</c:if>
	</script>

</body>
</html>