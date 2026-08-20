<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>인사기록카드 - 헥사곤존</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />

<style>
* {
	box-sizing: border-box;
}

body {
	margin: 0;
	font-family: "Pretendard", "Malgun Gothic", sans-serif;
	background: #f5f6f8;
	color: #222;
}

/* =========================
   전체 영역
========================= */
.page-wrap {
	width: calc(100% - 48px);
	max-width: 1320px;
	margin: 32px auto 60px;
}

.toolbar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 16px;
}

.toolbar h2 {
	margin: 0;
	font-size: 24px;
	font-weight: 700;
}

.toolbar-actions {
	display: flex;
	gap: 8px;
}

.btn {
	border: 0;
	border-radius: 5px;
	padding: 10px 18px;
	cursor: pointer;
	font-family: "Pretendard", sans-serif;
	font-size: 14px;
	font-weight: 700;
}

.btn-primary {
	background: #3f80c9;
	color: #fff;
}

.btn-primary:hover {
	background: #3472b7;
}

.btn-secondary {
	background: #999;
	color: #fff;
}

.btn-secondary:hover {
	background: #888;
}

.btn-print {
	background: #555;
	color: #fff;
}

.btn-print:hover {
	background: #444;
}

/* =========================
   인사기록카드
========================= */
.card-background {
	width: 100%;
	background: #737373;
	padding: 22px;
	border-radius: 5px;
	overflow: hidden;
}

.card-pages {
	display: grid;
	grid-template-columns: repeat(2, minmax(0, 1fr));
	gap: 18px;
	align-items: stretch;
}

.card-page {
	width: 100%;
	min-width: 0;
	min-height: 1000px;
	background: #fff;
	border: 2px solid #444;
}

.card-title {
	height: 64px;
	display: flex;
	align-items: center;
	justify-content: center;
	border-bottom: 1px solid #444;
	font-size: 22px;
	font-weight: 700;
}

/* =========================
   공통 테이블
========================= */
table {
	width: 100%;
	border-collapse: collapse;
	table-layout: fixed;
}

th, td {
	height: 27px;
	padding: 4px 5px;
	border: 1px dotted #777;
	font-size: 12px;
	text-align: center;
	vertical-align: middle;
	word-break: keep-all;
	overflow-wrap: anywhere;
}

th {
	font-weight: 500;
}

.section-header {
	background: #fffed2;
}

.empty-row td {
	height: 27px;
}

/* =========================
   기본정보
========================= */
.left-top {
	display: grid;
	grid-template-columns: 125px minmax(0, 1fr);
}

.photo-cell {
	min-height: 190px;
	border-right: 1px solid #444;
	display: flex;
	align-items: center;
	justify-content: center;
	overflow: hidden;
	background: #fff;
}

.photo-cell img {
	width: 100px;
	height: 135px;
	object-fit: cover;
	border: 1px solid #aaa;
}

.photo-placeholder {
	font-size: 13px;
	color: #888;
}

.basic-info {
	min-width: 0;
}

.basic-info table td, .basic-info table th {
	border-color: #777;
}

.basic-title-row th {
	height: 64px;
	font-size: 20px;
	font-weight: 700;
}

/* =========================
   각 항목 영역
========================= */
.section-block {
	display: grid;
	grid-template-columns: 34px minmax(0, 1fr);
	border-top: 1px solid #444;
}

.section-label {
	width: 34px;
	padding: 0;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 13px;
	font-weight: 700;
	line-height: 1.2;
	background: #fff;
}

.section-content {
	min-width: 0;
}

.retirement-grid td, .retirement-grid th {
	height: 30px;
}

/* =========================
   사원선택 모달
========================= */
.modal-backdrop {
	display: none;
	position: fixed;
	inset: 0;
	z-index: 1000;
	align-items: center;
	justify-content: center;
	background: rgba(0, 0, 0, 0.45);
}

.modal {
	width: 820px;
	max-width: calc(100% - 40px);
	max-height: 78vh;
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 12px 36px rgba(0, 0, 0, 0.25);
	overflow: hidden;
}

.modal-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 18px 24px;
	border-bottom: 1px solid #ddd;
}

.modal-header h3 {
	margin: 0;
	font-size: 21px;
	font-weight: 700;
}

.modal-close {
	background: none;
	border: 0;
	font-size: 28px;
	cursor: pointer;
	color: #555;
}

.modal-body {
	padding: 18px 24px 10px;
}

.employee-table-wrap {
	max-height: 430px;
	overflow-y: auto;
	border: 1px solid #ddd;
}

.employee-table th {
	position: sticky;
	top: 0;
	z-index: 1;
	background: #f2f9fb;
	font-weight: 600;
}

.employee-table td, .employee-table th {
	height: 38px;
	font-size: 13px;
	border: 1px solid #ddd;
}

.employee-row {
	cursor: pointer;
}

.employee-row:hover {
	background: #eef5ff;
}

.employee-row.selected {
	background: #dfeeff;
}

.modal-footer {
	display: flex;
	justify-content: center;
	gap: 10px;
	padding: 14px 24px 22px;
}

/* =========================
   작은 화면
========================= */
@media ( max-width : 1100px) {
	.page-wrap {
		width: calc(100% - 30px);
		margin-top: 24px;
	}
	.card-background {
		overflow-x: auto;
	}
	.card-pages {
		min-width: 1050px;
	}
	.toolbar {
		min-width: 1050px;
	}
}

/* =========================
   인쇄
========================= */
@media print {
	.toolbar {
		display: none !important;
	}
	body {
		background: #fff;
	}
	.page-wrap {
		width: 100%;
		max-width: none;
		margin: 0;
	}
	.card-background {
		background: #fff;
		padding: 0;
		overflow: visible;
	}
	.card-pages {
		grid-template-columns: 1fr 1fr;
		gap: 8px;
		min-width: 0;
	}
	.card-page {
		min-height: 0;
		break-inside: avoid;
	}
	.modal-backdrop {
		display: none !important;
	}
}
</style>
</head>

<body>

	<layout:headerNav />

	<div class="page-wrap">

		<!-- 상단 제목 및 버튼 -->
		<div class="toolbar">

			<h2>인사기록카드</h2>

			<div class="toolbar-actions">

				<button type="button" class="btn btn-primary"
					onclick="openEmployeeModal()">사원선택</button>

				<button type="button" class="btn btn-print" onclick="window.print()">
					인쇄</button>

			</div>

		</div>

		<!-- 인사기록카드 배경 -->
		<div class="card-background">

			<div class="card-pages">

				<!-- =====================================================
				     왼쪽 페이지
				===================================================== -->

				<section class="card-page">

					<!-- 기본정보 -->
					<div class="left-top">

						<!-- 사진 -->
						<div class="photo-cell">

							<c:choose>

								<c:when test="${not empty empCardData.emp.photoPath}">

									<img
										src="${pageContext.request.contextPath}/${empCardData.emp.photoPath}"
										alt="사원 사진">

								</c:when>

								<c:otherwise>

									<div class="photo-placeholder">사진</div>

								</c:otherwise>

							</c:choose>

						</div>

						<!-- 기본 사원정보 -->
						<div class="basic-info">

							<table>

								<colgroup>
									<col style="width: 23%;">
									<col style="width: 27%;">
									<col style="width: 23%;">
									<col style="width: 27%;">
								</colgroup>

								<tr class="basic-title-row">

									<th colspan="2">인사기록카드</th>

									<th>사원번호</th>

									<td><c:out value="${empCardData.emp.empId}" default="" />
									</td>

								</tr>

								<tr>

									<th>입사일</th>

									<td colspan="3"><fmt:formatDate
											value="${empCardData.emp.hireDate}" pattern="yyyy-MM-dd" />

									</td>

								</tr>

								<tr>

									<th>퇴사일</th>

									<td colspan="3"><fmt:formatDate
											value="${empCardData.emp.retireDate}" pattern="yyyy-MM-dd" />

									</td>

								</tr>

								<tr>

									<th>성명(한글)</th>

									<td><c:out value="${empCardData.emp.nameKor}" default="" />

									</td>

									<th>성명(영문)</th>

									<td><c:out value="${empCardData.emp.nameEng}" default="" />

									</td>

								</tr>

								<tr>

									<th>주민등록번호</th>

									<td><c:out value="${empCardData.emp.residentNo}"
											default="-" /></td>

									<th>사원구분</th>

									<td><c:out value="${empCardData.emp.employmentType}"
											default="" /></td>

								</tr>

								<tr>

									<th>주소</th>

									<td colspan="3" style="text-align: left;"><c:out
											value="${empCardData.emp.address}" default="" /></td>

								</tr>

								<tr>

									<th>휴대전화</th>

									<td><c:out value="${empCardData.emp.mobileNum}" default="" />

									</td>

									<th>연락처</th>

									<td><c:out value="${empCardData.emp.phoneNum}" default="" />

									</td>

								</tr>

								<tr>

									<th>E-Mail</th>

									<td colspan="3" style="text-align: left;"><c:out
											value="${empCardData.emp.email}" default="" /></td>

								</tr>

							</table>

						</div>

					</div>

					<!-- =================================================
					     가족사항
					================================================= -->

					<div class="section-block">

						<div class="section-label">
							가<br>족<br>사<br>항
						</div>

						<div class="section-content">

							<table>

								<colgroup>

									<col style="width: 12%;">
									<col style="width: 15%;">
									<col style="width: 27%;">
									<col style="width: 16%;">
									<col style="width: 15%;">
									<col style="width: 15%;">

								</colgroup>

								<tr class="section-header">

									<th>관계</th>
									<th>성명</th>
									<th>주민등록번호</th>
									<th>동거여부</th>
									<th>국민연금</th>
									<th>비고</th>

								</tr>

								<c:forEach var="dependent" items="${empCardData.dependents}">

									<tr>

										<td><c:out value="${dependent.relation}" /></td>

										<td><c:out value="${dependent.familyName}" /></td>

										<td><c:out value="${dependent.residentNo}" default="-" />
										</td>

										<td><c:out value="${dependent.cohabitationYN}" /></td>

										<td><c:out value="${dependent.deductionYN}" /></td>

										<td></td>

									</tr>

								</c:forEach>

								<c:if test="${empty empCardData.dependents}">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td>-</td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:if>

								<c:forEach begin="1" end="5">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td>-</td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<!-- =================================================
					     학력
					================================================= -->

					<div class="section-block">

						<div class="section-label">
							학<br>력
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>학교명</th>
									<th>입학년월</th>
									<th>졸업년월</th>
									<th>전공</th>
									<th>이수</th>

								</tr>

								<!-- Education 조회 연결 후 교체 -->

								<c:forEach begin="1" end="5">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<!-- =================================================
					     병역
					================================================= -->

					<div class="section-block">

						<div class="section-label">
							병<br>역
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>제대구분</th>
									<th colspan="2">미필사유</th>

								</tr>

								<tr class="section-header">

									<th>군별</th>
									<th>최종계급</th>
									<th>복무기간</th>

								</tr>

								<tr class="empty-row">

									<td></td>
									<td></td>
									<td></td>

								</tr>

							</table>

						</div>

					</div>

					<!-- =================================================
					     경력
					================================================= -->

					<div class="section-block">

						<div class="section-label">
							경<br>력
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>회사명</th>
									<th>입사일자</th>
									<th>퇴사일자</th>
									<th>최종직위</th>
									<th>담당업무</th>

								</tr>

								<!-- PersonalHistory 조회 연결 후 교체 -->

								<c:forEach begin="1" end="5">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<!-- =================================================
					     자격 / 면허
					================================================= -->

					<div class="section-block">

						<div class="section-label">
							자<br>격<br>/<br>면<br>허
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>종류</th>
									<th>취득일</th>
									<th>발행기관</th>
									<th>비고</th>

								</tr>

								<!-- License 조회 연결 후 교체 -->

								<c:forEach begin="1" end="4">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<!-- =================================================
					     어학능력
					================================================= -->

					<div class="section-block">

						<div class="section-label">
							어<br>학<br>능<br>력
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>외국어명</th>
									<th>시험</th>
									<th>공인점수</th>
									<th>취득일</th>
									<th>독해</th>
									<th>작문</th>
									<th>회화</th>

								</tr>

								<!-- LanguageSkill 조회 연결 후 교체 -->

								<c:forEach begin="1" end="3">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

				</section>

				<!-- =====================================================
				     오른쪽 페이지
				===================================================== -->

				<section class="card-page">

					<div class="card-title">인사기록카드</div>

					<!-- =================================================
					     교육사항
					================================================= -->

					<div class="section-block">

						<div class="section-label">
							교<br>육<br>사<br>항
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>교육구분</th>
									<th>교육명</th>
									<th>기간(부터)</th>
									<th>기간(까지)</th>
									<th>교육기관</th>
									<th>교육비</th>
									<th>환급교육비</th>

								</tr>

								<!-- EducationTraining 조회 연결 후 교체 -->

								<c:forEach begin="1" end="10">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<!-- =================================================
					     상벌사항
					================================================= -->

					<div class="section-block">

						<div class="section-label">
							상<br>벌<br>사<br>항
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>구분</th>
									<th>상벌명</th>
									<th>상벌권자</th>
									<th>상벌일자</th>
									<th>상벌내용</th>
									<th>비고</th>

								</tr>

								<!-- RewardPunishment 조회 연결 후 교체 -->

								<c:forEach begin="1" end="8">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<!-- =================================================
					     인사발령
					================================================= -->

					<div class="section-block">

						<div class="section-label">
							인<br>사<br>발<br>령
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>발령구분</th>
									<th>발령일자</th>
									<th>부서</th>
									<th>직위</th>
									<th>직책 및 담당업무</th>
									<th>비고</th>

								</tr>

								<!-- Appointment 조회 연결 후 교체 -->

								<c:forEach begin="1" end="9">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<!-- =================================================
					     퇴직사항
					================================================= -->

					<div class="section-block">

						<div class="section-label">
							퇴<br>직<br>사<br>항
						</div>

						<div class="section-content">

							<table class="retirement-grid">

								<tr class="section-header">

									<th>퇴직구분</th>
									<th>퇴직일자</th>
									<th>퇴직사유</th>
									<th>퇴직금</th>
									<th>퇴직 후 연락처</th>

								</tr>

								<tr>

									<td><c:out
											value="${empCardData.retirement.retirementType}" default="" />

									</td>

									<td><fmt:formatDate
											value="${empCardData.retirement.retirementDate}"
											pattern="yyyy-MM-dd" /></td>

									<td><c:out
											value="${empCardData.retirement.retirementReason}" default="" />

									</td>

									<td><c:if
											test="${not empty empCardData.severancePay.netPayment}">

											<fmt:formatNumber
												value="${empCardData.severancePay.netPayment}"
												pattern="#,##0" />

										</c:if></td>

									<td><c:out
											value="${empCardData.retirement.contactAfterRetirement}"
											default="" /></td>

								</tr>

							</table>

						</div>

					</div>

				</section>

			</div>

		</div>

	</div>

	<!-- =========================================================
	     사원선택 모달
	========================================================= -->

	<div id="employeeModal" class="modal-backdrop">

		<div class="modal">

			<div class="modal-header">

				<h3>인사기록카드 사원선택</h3>

				<button type="button" class="modal-close"
					onclick="closeEmployeeModal()">&times;</button>

			</div>

			<form id="employeeSelectForm" method="post"
				action="${pageContext.request.contextPath}/emp/empCard.do">

				<input type="hidden" id="selectedEmpId" name="empId">

				<div class="modal-body">

					<div class="employee-table-wrap">

						<table class="employee-table">

							<thead>

								<tr>

									<th style="width: 14%;">구분</th>

									<th style="width: 16%;">사원번호</th>

									<th style="width: 18%;">성명</th>

									<th style="width: 20%;">부서</th>

									<th style="width: 16%;">직위</th>

									<th style="width: 16%;">상태</th>

								</tr>

							</thead>

							<tbody>

								<c:forEach var="employee" items="${empList}">

									<tr class="employee-row" data-emp-id="${employee.empId}"
										onclick="selectEmployee(this)">

										<td><c:out value="${employee.employmentType}" /></td>

										<td><c:out value="${employee.empId}" /></td>

										<td><c:out value="${employee.nameKor}" /></td>

										<td><c:out value="${employee.deptName}" /></td>

										<td><c:out value="${employee.positionName}" /></td>

										<td><c:choose>

												<c:when test="${employee.status eq 'N'}">
													퇴직
												</c:when>

												<c:otherwise>
													재직
												</c:otherwise>

											</c:choose></td>

									</tr>

								</c:forEach>

								<c:if test="${empty empList}">

									<tr>

										<td colspan="6" style="height: 100px;">조회된 사원 목록이 없습니다.</td>

									</tr>

								</c:if>

							</tbody>

						</table>

					</div>

				</div>

				<div class="modal-footer">

					<button type="button" class="btn btn-primary"
						onclick="submitSelectedEmployee()">사원선택</button>

					<button type="button" class="btn btn-secondary"
						onclick="closeEmployeeModal()">선택취소</button>

				</div>

			</form>

		</div>

	</div>

	<script>
		function openEmployeeModal() {

			document.getElementById("employeeModal").style.display = "flex";

		}

		function closeEmployeeModal() {

			document.getElementById("employeeModal").style.display = "none";

		}

		function selectEmployee(row) {

			const rows = document.querySelectorAll(".employee-row");

			rows.forEach(function(item) {

				item.classList.remove("selected");

			});

			row.classList.add("selected");

			document.getElementById("selectedEmpId").value = row.dataset.empId;

		}

		function submitSelectedEmployee() {

			const empId = document.getElementById("selectedEmpId").value;

			if (!empId) {

				alert("사원을 선택해주세요.");

				return;

			}

			document.getElementById("employeeSelectForm").submit();

		}

		document.getElementById("employeeModal").addEventListener("click",
				function(event) {

					if (event.target === this) {

						closeEmployeeModal();

					}

				});
	</script>

</body>
</html>