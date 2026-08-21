<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">

<title>헥사곤존 - 퇴직증명서 발급</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/certificate.css"/>

</head>
<body>


	<layout:headerNav />


	<main class="certificate-container">


		<!-- ==================================================
	     상단
	================================================== -->

		<div class="page-hero">

			<h1>📄 제증명서 발급</h1>

			<p>사원을 선택하면 해당 사원의 퇴직증명서를 확인하고 발급할 수 있습니다.</p>

		</div>


		<div class="certificate-layout">


			<!-- ==================================================
		     왼쪽 사원 목록
		================================================== -->

			<section class="employee-panel">


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

							<th>직위</th>

							<th>상태</th>

						</tr>

					</thead>


					<tbody>


						<c:forEach var="employee" items="${empListPage.empListData}">


							<tr class="${selectedEmpId == employee.empId ? 'selected' : ''}"
								onclick="selectEmployee(
								'${employee.empId}',
								'${empListPage.currentPage}'
							);">


								<td><c:out value="${employee.employmentType}" /></td>


								<td><c:out value="${employee.nameKor}" /></td>


								<td><c:out value="${employee.deptName}" /></td>


								<td><c:out value="${employee.positionName}" /></td>


								<td><c:choose>


										<c:when test="${employee.status == 'Y'}">

											<span class="status-working"> 재직 </span>

										</c:when>


										<c:otherwise>

											<span class="status-retired"> 퇴직 </span>

										</c:otherwise>


									</c:choose></td>


							</tr>


						</c:forEach>


					</tbody>

				</table>


				<!-- ==================================================
			     페이징
			================================================== -->

				<c:if test="${empListPage.totalPage > 1}">

					<div class="pagination">


						<c:forEach var="page" begin="${empListPage.startPage}"
							end="${empListPage.endPage}">


							<a
								href="${pageContext.request.contextPath}/emp/certificate/retirement.do?pageNo=${page}"
								class="${empListPage.currentPage == page ? 'active' : ''}">

								${page} </a>


						</c:forEach>


					</div>

				</c:if>


			</section>



			<!-- ==================================================
		     오른쪽 증명서
		================================================== -->

			<section class="preview-panel">


				<!-- ==================================================
			     증명서 탭
			================================================== -->

				<div class="certificate-tabs">


					<a
						href="${pageContext.request.contextPath}/emp/certificate/working.do"
						class="certificate-tab"> 재직증명서 </a> <a
						href="${pageContext.request.contextPath}/emp/certificate/retirement.do"
						class="certificate-tab active"> 퇴직증명서 </a>


				</div>


				<div class="certificate-preview-area">


					<div class="certificate-paper">


						<!-- ==================================================
					     증명서 상단
					================================================== -->

						<div class="cert-top">


							<div class="cert-logo">HEXAGON ZONE</div>


							<div class="cert-title">퇴직증명서</div>


							<div class="cert-option">


								<label> <input type="checkbox" id="hideResidentNo"
									onchange="toggleResidentNo();"> 주민등록번호 숨김

								</label>


							</div>


						</div>



						<!-- ==================================================
					     사원 정보
					================================================== -->

						<table class="cert-table">


							<!-- 인적사항 -->
							<tr>


								<th rowspan="2" class="section-head">인적<br>사항

								</th>


								<th>성명</th>


								<td><c:out value="${emp.nameKor}" /></td>


								<th>주민등록번호</th>


								<td id="residentNoCell"><c:out value="${emp.residentNo}" />

								</td>


							</tr>


							<tr>


								<th>주소</th>


								<td colspan="3"><c:out value="${emp.address}" /></td>


							</tr>



							<!-- 재직사항 -->
							<tr>


								<th rowspan="3" class="section-head">재직<br>사항

								</th>


								<th>회사명</th>


								<td>(주)헥사곤존</td>


								<th>사업자번호</th>


								<td>1234-5678</td>


							</tr>


							<tr>


								<th>부서</th>


								<td><c:out value="${emp.deptName}" /></td>


								<th>직위</th>


								<td><c:out value="${emp.positionName}" /></td>


							</tr>


							<tr>


								<th>재직기간</th>


								<td class="work-period"><c:if
										test="${not empty emp.hireDate}">

										<fmt:formatDate value="${emp.hireDate}" pattern="yyyy-MM-dd" />

									</c:if> <c:if
										test="${not empty emp.hireDate && not empty emp.retireDate}">
									&nbsp;~&nbsp;
								</c:if> <c:if test="${not empty emp.retireDate}">

										<fmt:formatDate value="${emp.retireDate}" pattern="yyyy-MM-dd" />

									</c:if></td>


								<th>근속기간</th>


								<td id="servicePeriod">-</td>


							</tr>


						</table>



						<!-- ==================================================
					     발급용도
					================================================== -->

						<div class="usage-row">


							<div class="usage-label">발급용도</div>


							<div class="usage-input-area">


								<select id="issuePurpose" class="cert-select"
									onchange="changeIssuePurpose();">


									<option value="">선택</option>


									<option value="비자 신청용">비자 신청용</option>


									<option value="은행 제출용">은행 제출용</option>


									<option value="관공서 제출용">관공서 제출용</option>


									<option value="학교 제출용">학교 제출용</option>


									<option value="회사 제출용">회사 제출용</option>


									<option value="병무청 제출용">병무청 제출용</option>


									<option value="관련과제 제출용">관련과제 제출용</option>


									<option value="직접입력">직접입력</option>


								</select> <input type="text" id="directPurpose" class="direct-purpose"
									placeholder="발급용도를 입력해주세요.">


							</div>


						</div>



						<!-- ==================================================
					     증명 문구
					================================================== -->

						<div class="cert-content">상기인은 당사에 위와 같이 재직하였음을 증명합니다.</div>



						<!-- ==================================================
					     발급 날짜
					================================================== -->

						<div class="cert-date">


							<span id="todayYear"></span>년 <span id="todayMonth"></span>월 <span
								id="todayDay"></span>일


						</div>



						<!-- ==================================================
					     발급부서
					================================================== -->

						<table class="issue-info-table">


							<tr>


								<th>발급부서</th>


								<td class="issue-dept-cell"><select id="issueDepartment">


										<option value="">선택</option>


										<option value="사장실">사장실</option>


										<option value="개발팀">개발팀</option>


										<option value="콘텐츠팀">콘텐츠팀</option>


										<option value="업무지원팀">업무지원팀</option>


										<option value="디자인팀">디자인팀</option>


										<option value="관리팀">관리팀</option>


										<option value="기획전략팀">기획전략팀</option>


								</select></td>


								<th class="contact-label">연락처</th>


								<td class="contact-value">[전국] 1588-2390</td>


							</tr>


						</table>



						<!-- ==================================================
					     대표자 / 직인
					================================================== -->

						<div class="cert-company-bottom">


							<div class="representative-option">


								<label> <input type="checkbox" id="showRepresentative"
									checked> 대표자 표기

								</label>


							</div>


							<div class="company-sign" id="representativeArea">


								<div class="company-name">

									(주)헥사곤존 <br> 대표이사

								</div>


								<div class="company-seal">

									회사 <br> 직인

								</div>


							</div>


						</div>


					</div>


				</div>



				<!-- ==================================================
			     인쇄 버튼
			================================================== -->

				<div class="action-area">


					<button type="button" class="print-btn"
						onclick="printCertificate();">인쇄</button>


				</div>


			</section>


		</div>


	</main>


	<layout:footer />



	<!-- ==================================================
     재직 사원 선택
================================================== -->

	<c:if test="${workingEmployee}">

		<script>

		alert(
			"재직 중인 사원은 퇴직증명서를 발급할 수 없습니다."
		);

	</script>

	</c:if>


	<c:if test="${employeeNotFound}">

		<script>

		alert(
			"사원 정보를 찾을 수 없습니다."
		);

	</script>

	</c:if>



	<script>


/* ==================================================
   사원이 선택되어 있는지
================================================== */

const employeeSelected =
	${not empty emp ? 'true' : 'false'};



/* ==================================================
   사원 선택
================================================== */

function selectEmployee(
	empId,
	pageNo
) {


	location.href =
		"${pageContext.request.contextPath}"
		+ "/emp/certificate/retirement.do"
		+ "?empId="
		+ encodeURIComponent(empId)
		+ "&pageNo="
		+ encodeURIComponent(pageNo);


}



/* ==================================================
   사원 검색
================================================== */

function searchEmployee() {


	const keyword =
		document
			.getElementById(
				"searchKeyword"
			)
			.value
			.trim()
			.toLowerCase();


	const rows =
		document.querySelectorAll(
			".employee-table tbody tr"
		);


	rows.forEach(function(row) {


		const name =
			row.children[1]
				.innerText
				.trim()
				.toLowerCase();


		if (
			name.includes(keyword)
		) {

			row.style.display =
				"";

		} else {

			row.style.display =
				"none";

		}


	});


}



/* ==================================================
   전체보기
================================================== */

function showAllEmployee() {


	document.getElementById(
		"searchKeyword"
	).value = "";


	const rows =
		document.querySelectorAll(
			".employee-table tbody tr"
		);


	rows.forEach(function(row) {

		row.style.display = "";

	});


}



/* ==================================================
   발급용도 직접입력
================================================== */

function changeIssuePurpose() {


	const purpose =
		document.getElementById(
			"issuePurpose"
		);


	const directInput =
		document.getElementById(
			"directPurpose"
		);


	if (
		!purpose
		|| !directInput
	) {

		return;

	}


	if (
		purpose.value
		=== "직접입력"
	) {


		directInput.style.display =
			"inline-block";


		directInput.focus();


	} else {


		directInput.style.display =
			"none";


		directInput.value =
			"";


	}


}



/* ==================================================
   주민번호 숨김
================================================== */

const originalResidentNo =
	"${emp.residentNo}";


function toggleResidentNo() {


	const checkbox =
		document.getElementById(
			"hideResidentNo"
		);


	const cell =
		document.getElementById(
			"residentNoCell"
		);


	if (
		!checkbox
		|| !cell
	) {

		return;

	}


	if (
		checkbox.checked
	) {


		if (
			originalResidentNo
			&& originalResidentNo
				.includes("-")
		) {


			const front =
				originalResidentNo
					.split("-")[0];


			cell.innerText =
				front
				+ "-*******";


		} else if (
			originalResidentNo
			&& originalResidentNo.length >= 6
		) {


			cell.innerText =
				originalResidentNo
					.substring(0, 6)
				+ "-*******";


		} else {


			cell.innerText =
				"******-*******";

		}


	} else {


		cell.innerText =
			originalResidentNo;


	}


}



/* ==================================================
   발급 날짜
================================================== */

function setToday() {


	const today =
		new Date();


	const year =
		document.getElementById(
			"todayYear"
		);


	const month =
		document.getElementById(
			"todayMonth"
		);


	const day =
		document.getElementById(
			"todayDay"
		);


	if (year) {

		year.innerText =
			today.getFullYear();

	}


	if (month) {

		month.innerText =
			String(
				today.getMonth() + 1
			).padStart(
				2,
				"0"
			);

	}


	if (day) {

		day.innerText =
			String(
				today.getDate()
			).padStart(
				2,
				"0"
			);

	}


}



/* ==================================================
   퇴직사원 근속기간 계산

   입사일 → 퇴직일
================================================== */

function calculateServicePeriod() {


	const target =
		document.getElementById(
			"servicePeriod"
		);


	if (!target) {

		return;

	}


	const hireDateValue =
		"${emp.hireDate}";


	const retireDateValue =
		"${emp.retireDate}";


	if (
		!hireDateValue
		|| !retireDateValue
	) {


		target.innerText =
			"-";


		return;

	}


	const hireDate =
		new Date(
			hireDateValue
		);


	const retireDate =
		new Date(
			retireDateValue
		);


	let years =
		retireDate.getFullYear()
		- hireDate.getFullYear();


	let months =
		retireDate.getMonth()
		- hireDate.getMonth();


	if (
		retireDate.getDate()
		< hireDate.getDate()
	) {

		months--;

	}


	if (
		months < 0
	) {

		years--;

		months += 12;

	}


	if (
		years < 0
	) {

		target.innerText =
			"-";

		return;

	}


	target.innerText =
		years
		+ "년 "
		+ months
		+ "개월";


}



/* ==================================================
   대표자 표기
================================================== */

document.addEventListener(
	"change",

	function(event) {


		if (
			event.target.id
			=== "showRepresentative"
		) {


			const area =
				document.getElementById(
					"representativeArea"
				);


			if (!area) {

				return;

			}


			if (
				event.target.checked
			) {


				area.style.visibility =
					"visible";


			} else {


				area.style.visibility =
					"hidden";


			}


		}


	}
);



/* ==================================================
   인쇄
================================================== */

function printCertificate() {


	/*
	 * 양식은 항상 보여주지만
	 * 사원을 선택하지 않으면 인쇄 불가
	 */
	if (!employeeSelected) {


		alert(
			"먼저 퇴직증명서를 발급할 사원을 선택해주세요."
		);


		return;

	}


	const issuePurpose =
		document.getElementById(
			"issuePurpose"
		);


	if (
		issuePurpose
		&& !issuePurpose.value
	) {


		alert(
			"발급용도를 선택해주세요."
		);


		return;

	}


	if (
		issuePurpose
		&& issuePurpose.value
			=== "직접입력"
	) {


		const directPurpose =
			document.getElementById(
				"directPurpose"
			);


		if (
			!directPurpose.value
				.trim()
		) {


			alert(
				"발급용도를 입력해주세요."
			);


			directPurpose.focus();


			return;

		}


	}


	const issueDepartment =
		document.getElementById(
			"issueDepartment"
		);


	if (
		issueDepartment
		&& !issueDepartment.value
	) {


		alert(
			"발급부서를 선택해주세요."
		);


		return;

	}


	window.print();


}



/* ==================================================
   최초 실행
================================================== */

window.addEventListener(
	"DOMContentLoaded",

	function() {


		setToday();


		calculateServicePeriod();


	}
);


</script>


</body>
</html>