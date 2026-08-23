<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">

<title>헥사곤존 - 퇴직급여명세서</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/certificate.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/specification.css"/>

</head>

<body>

	<layout:headerNav />

	<main class="certificate-container">

		<div class="page-hero">
			<h1>📄 퇴직급여명세서</h1>
			<p>사원을 선택하면 해당 사원의 퇴직급여명세서를 확인할 수 있습니다.</p>
		</div>

		<div class="certificate-layout">

			<!-- ==================================================
				사원 목록
			================================================== -->
			<section class="employee-panel no-print">

				<div class="panel-title">사원 목록</div>

				<div class="search-area">

					<input type="text" id="searchKeyword" placeholder="사원명 검색"
						onkeyup="if(event.key === 'Enter') searchEmployee();">

					<button type="button" class="btn btn-search"
						onclick="searchEmployee();">검색</button>

					<button type="button" class="btn btn-all"
						onclick="showAllEmployee();">전체보기</button>

				</div>

				<table class="employee-table">

					<thead>
						<tr>
							<th>구분</th>
							<th>성명</th>
							<th>부서</th>
							<th>상태</th>
						</tr>
					</thead>

					<tbody>

						<c:forEach var="employee" items="${empListPage.empListData}">

							<c:choose>

								<%-- 퇴직 사원: 선택 가능 --%>
								<c:when test="${employee.status == 'N'}">

									<tr
										class="${selectedEmpId == employee.empId ? 'selected' : ''}"
										onclick="selectEmployee('${employee.empId}', '${empListPage.currentPage}');"
										style="cursor: pointer;">
								</c:when>

								<%-- 재직 사원: 선택 불가 --%>
								<c:otherwise>

									<tr class="disabled-employee"
										onclick="alert('퇴직한 사원만 퇴직급여명세서를 조회할 수 있습니다.');">
								</c:otherwise>

							</c:choose>

							<td><c:out value="${employee.employmentType}" /></td>
							<td><c:out value="${employee.nameKor}" /></td>
							<td><c:out value="${employee.deptName}" /></td>>

							<td><c:choose>

									<c:when test="${employee.status == 'Y'}">
										<span class="status-working">재직</span>
									</c:when>

									<c:otherwise>
										<span class="status-retired">퇴직</span>
									</c:otherwise>

								</c:choose></td>

							</tr>

						</c:forEach>

					</tbody>

				</table>

				<!-- 페이징 -->
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


			<!-- ==================================================
				명세서 미리보기
			================================================== -->
			<section class="preview-panel">

				<div class="certificate-preview-area">

					<c:choose>

						<c:when test="${not empty specificationData}">

							<div class="specification-paper">

								<div class="spec-title">퇴직급여명세서</div>


								<!-- ==================================================
									사원정보
								================================================== -->
								<div class="spec-section">

									<div class="spec-section-title">사원정보</div>

									<table class="spec-table">

										<tr>

											<th>성명</th>

											<td><c:out value="${specificationData.emp.nameKor}" />
											</td>

											<th>입사일</th>

											<td><fmt:formatDate
													value="${specificationData.emp.hireDate}"
													pattern="yyyy-MM-dd" /></td>

										</tr>

										<tr>

											<th>부서</th>

											<td><c:out value="${specificationData.emp.deptName}" />
											</td>

											<th>퇴직일</th>

											<td><fmt:formatDate
													value="${specificationData.emp.retireDate}"
													pattern="yyyy-MM-dd" /></td>

										</tr>

										<tr>

											<th>직위</th>

											<td><c:out value="${specificationData.emp.positionName}" />
											</td>

											<th>근속일수</th>

											<td id="serviceDays">-</td>

										</tr>

									</table>

								</div>


								<!-- ==================================================
									급여내역
								================================================== -->
								<div class="spec-section">

									<div class="spec-section-title">급여내역</div>

									<table class="spec-table">

										<tr>

											<th class="label">산정기간</th>

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

											<th>계</th>

										</tr>


										<tr>

											<th>산정일수</th>

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

											<th>급여총액</th>

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


								<!-- ==================================================
									기타소득
								================================================== -->
								<div class="spec-section">

									<div class="spec-section-title">기타소득</div>

									<table class="spec-table extra-income-table">

										<thead>

											<tr>
												<th>지급항목</th>
												<th>1년간 지급액</th>
												<th>3개월분</th>
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


											<!-- 데이터가 없을 경우 빈 칸 유지 -->
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


								<!-- ==================================================
									퇴직위로금 / 해고예고수당
								================================================== -->
								<div class="spec-section">

									<table class="spec-table allowance-table">

										<tr>

											<th>퇴직위로금</th>

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


											<th>해고예고수당</th>

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


								<!-- ==================================================
									퇴직소득
								================================================== -->
								<div class="spec-section">

									<div class="spec-section-title">퇴직소득</div>

									<table class="spec-table retirement-income-table">

										<tr>

											<th rowspan="2">1일 평균임금</th>

											<th>3개월 총계</th>

											<td class="formula">급여총액 계 + 3개월분 기타소득 계</td>

											<td class="result"><fmt:formatNumber
													value="${specificationData.totalSalary + specificationData.totalOtherIncomeThreeMonths}"
													pattern="#,###" /></td>

										</tr>


										<tr>

											<th>1일 평균임금</th>

											<td class="formula">3개월 총계 / 산정일수</td>

											<td class="result"><fmt:formatNumber
													value="${specificationData.dailyAverageWage}"
													pattern="#,###" /></td>

										</tr>


										<tr>

											<th>1일 통상임금</th>

											<td colspan="2" class="formula">1일 통상임금이 1일 평균임금보다 높은 경우
												1일 통상임금으로 적용</td>

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

											<th>퇴직소득</th>

											<td colspan="2" class="formula">(적용 1일 임금 × 30일 × 근속일수 /
												365) + 퇴직위로금 + 해고예고수당</td>

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


								<!-- ==================================================
									공제내역
								================================================== -->
								<div class="spec-section">

									<div class="spec-section-title">공제내역</div>

									<table class="spec-table deduction-table">

										<tr>

											<th>퇴직소득세</th>
											<th>지방소득세</th>
											<th>기타공제</th>
											<th>공제총액</th>

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


								<!-- ==================================================
									실수령액
								================================================== -->
								<div class="spec-section">

									<table class="spec-table net-payment-table">

										<tr>

											<th>실수령액</th>

											<td class="description">퇴직급여 - 공제총액</td>

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


								<div class="spec-footer-message">위 금액을 해당자의 퇴직금 정산액으로 정히
									영수함</div>


								<div class="spec-date">

									<span id="todayYear"></span>년 <span id="todayMonth"></span>월 <span
										id="todayDay"></span>일

								</div>


								<div class="spec-company">

									(주)헥사곤존<br> 대표이사

								</div>

							</div>

						</c:when>


						<c:otherwise>

							<div class="specification-paper empty-specification">

								퇴직급여명세서를 확인할 사원을 선택해주세요.</div>

						</c:otherwise>

					</c:choose>

				</div>


				<div class="action-area no-print">

					<button type="button" class="print-btn"
						onclick="printSpecification();">인쇄</button>

				</div>

			</section>

		</div>

	</main>


	<layout:footer />


	<script>
		/* ==================================================
		 사원 선택
		 ================================================== */
		function selectEmployee(empId, pageNo) {

			location.href = "${pageContext.request.contextPath}"
					+ "/retirement/specification.do" + "?empId="
					+ encodeURIComponent(empId) + "&pageNo="
					+ encodeURIComponent(pageNo);

		}

		/* ==================================================
		 사원 검색
		 ================================================== */
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

		/* ==================================================
		 전체보기
		 ================================================== */
		function showAllEmployee() {

			document.getElementById("searchKeyword").value = "";

			const rows = document.querySelectorAll(".employee-table tbody tr");

			rows.forEach(function(row) {

				row.style.display = "";

			});

		}

		/* ==================================================
		 근속일수
		 ================================================== */
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

			target.innerText = serviceDays.toLocaleString() + "일";

		}

		/* ==================================================
		 오늘 날짜
		 ================================================== */
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

		/* ==================================================
		 인쇄
		 ================================================== */
		function printSpecification() {

			const paper = document.querySelector(".specification-paper");

			const empty = document.querySelector(".empty-specification");

			if (!paper || empty) {

				alert("먼저 퇴직급여명세서를 확인할 사원을 선택해주세요.");

				return;

			}

			window.print();

		}

		/* ==================================================
		 최초 실행
		 ================================================== */
		window.addEventListener("DOMContentLoaded", function() {

			setToday();
			calculateServiceDays();

		});
	</script>

</body>
</html>