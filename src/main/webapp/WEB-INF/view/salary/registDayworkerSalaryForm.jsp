<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>헥사곤존 - 일용직 급여입력/관리</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />

<style>
/* 메인 컨테이너 */
.page-container {
	max-width: 1300px;
	margin: 0 auto 50px auto;
	padding: 0 20px;
	font-family: 'Pretendard Variable', Pretendard, -apple-system,
		sans-serif;
	color: #334155;
}

/* 페이지 헤더 */
.page-header {
	margin-bottom: 24px;
}

.page-title-group {
	display: flex;
	align-items: center;
	gap: 12px;
	margin-bottom: 6px;
}

.page-title {
	font-size: 22px;
	font-weight: 700;
	color: #0f172a;
	margin: 0;
}

.page-subtext {
	font-size: 14px;
	color: #64748b;
	margin: 0;
}

.page-subtext .highlight {
	color: #ef4444;
	font-weight: 600;
}

/* 1. 상단 필터 & 정산기간 설정 바 */
.filter-card {
	background: #ffffff;
	border: 1px solid #e2e8f0;
	border-radius: 14px;
	padding: 16px 24px;
	margin-bottom: 24px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.02);
	flex-wrap: wrap;
	gap: 16px;
}

.filter-left-group {
	display: flex;
	align-items: center;
	gap: 12px;
	background-color: #1e3a8a;
	padding: 10px 18px;
	border-radius: 10px;
	color: #ffffff;
}

.filter-label {
	font-size: 13px;
	font-weight: 600;
	color: #bfdbfe;
}

.filter-select {
	background: #ffffff;
	border: 1px solid #cbd5e1;
	border-radius: 6px;
	padding: 6px 10px;
	font-size: 13px;
	font-weight: 600;
	color: #1e293b;
	outline: none;
}

.filter-right-group {
	display: flex;
	align-items: center;
	gap: 20px;
	font-size: 13px;
}

.date-input-group {
	display: flex;
	align-items: center;
	gap: 8px;
}

.date-input-group label {
	font-weight: 600;
	color: #475569;
}

.input-text {
	border: 1px solid #cbd5e1;
	border-radius: 6px;
	padding: 6px 10px;
	font-size: 13px;
	color: #334155;
	outline: none;
	transition: border-color 0.2s;
}

.input-text:focus {
	border-color: #2563eb;
}

/* 버튼 공통 스타일 */
.btn {
	padding: 7px 14px;
	font-size: 13px;
	font-weight: 600;
	border-radius: 8px;
	border: 1px solid transparent;
	cursor: pointer;
	display: inline-flex;
	align-items: center;
	gap: 6px;
	transition: all 0.2s ease;
}

.btn-primary {
	background-color: #2563eb;
	color: #ffffff;
}

.btn-primary:hover {
	background-color: #1d4ed8;
}

.btn-secondary {
	background-color: #f1f5f9;
	color: #475569;
	border-color: #cbd5e1;
}

.btn-secondary:hover {
	background-color: #e2e8f0;
}

.btn-danger-light {
	background-color: #fef2f2;
	color: #ef4444;
	border-color: #fecaca;
}

.btn-danger-light:hover {
	background-color: #fee2e2;
}

/* 2. 메인 2열 그리드 레이아웃 */
.payroll-grid {
	display: grid;
	grid-template-columns: 380px 1fr;
	gap: 20px;
}

.panel {
	background: #ffffff;
	border: 1px solid #e2e8f0;
	border-radius: 14px;
	padding: 20px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.02);
}

.panel-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 14px;
}

.panel-title {
	font-size: 15px;
	font-weight: 700;
	color: #1e293b;
	margin: 0;
}

/* 테이블 공통 스타일 */
.custom-table {
	width: 100%;
	border-collapse: collapse;
	font-size: 13px;
}

.custom-table th {
	background-color: #f8fafc;
	color: #475569;
	font-weight: 600;
	padding: 10px 8px;
	border-bottom: 1px solid #e2e8f0;
	border-top: 1px solid #e2e8f0;
	text-align: center;
}

.custom-table td {
	padding: 10px 8px;
	border-bottom: 1px solid #f1f5f9;
	color: #334155;
	text-align: center;
}

.custom-table tr.active {
	background-color: #eff6ff;
	font-weight: 600;
}

.custom-table tr.active td {
	color: #2563eb;
}

.custom-table tr:hover:not(.active) {
	background-color: #f8fafc;
	cursor: pointer;
}

/* 우측 상세 계산 영역 내 2분할 */
.detail-split {
	display: grid;
	grid-template-columns: 1fr 340px;
	gap: 16px;
	margin-bottom: 20px;
}

.input-highlight {
	background-color: #fef3c7;
	border: 1px solid #fde047;
	text-align: right;
	width: 70px;
	padding: 4px 6px;
	border-radius: 4px;
	font-weight: 600;
}

/* 공제항목 테이블 */
.deduction-table {
	width: 100%;
	border-collapse: collapse;
	font-size: 13px;
}

.deduction-table td {
	padding: 7px 10px;
	border-bottom: 1px solid #f1f5f9;
}

.deduction-table td.label-col {
	color: #64748b;
	background-color: #f8fafc;
	width: 45%;
}

.deduction-table td.val-col {
	text-align: right;
}

.deduction-input {
	width: 90px;
	text-align: right;
	padding: 4px 8px;
	border: 1px solid #cbd5e1;
	border-radius: 4px;
	font-size: 13px;
}

/* 합계 요약 영역 및 최종 배너 */
.summary-bar {
	display: flex;
	justify-content: space-between;
	background-color: #f8fafc;
	border: 1px solid #e2e8f0;
	border-radius: 10px;
	padding: 12px 20px;
	font-size: 14px;
	font-weight: 600;
	margin-bottom: 16px;
}

.total-hero-bar {
	background: linear-gradient(135deg, #1e3a8a 0%, #2563eb 100%);
	color: #ffffff;
	border-radius: 12px;
	padding: 18px 24px;
	text-align: center;
	font-size: 20px;
	font-weight: 700;
	box-shadow: 0 4px 12px rgba(37, 99, 235, 0.2);
	margin-bottom: 20px;
}

.total-hero-bar span {
	color: #fef08a;
	font-size: 24px;
	margin-left: 8px;
}

.bottom-actions {
	display: flex;
	justify-content: center;
	gap: 12px;
}

.bottom-actions .btn {
	padding: 10px 32px;
	font-size: 14px;
}
</style>
</head>
<body>

	<!-- 헤더 Include -->
	<layout:headerNav />

	<main class="page-container">
		<div class="page-header">
			<div class="page-title-group">
				<span style="font-size: 24px;">🧮</span>
				<h1 class="page-title">급여입력 / 관리 (일용직)</h1>
			</div>
			<p class="page-subtext">
				일용직 근로자의 급여 및 상여금 정보를 입력, 저장, 관리하는 메뉴입니다. <span class="highlight">귀속연월과
					급여차수를 먼저 확인하세요!</span>
			</p>
		</div>

		<form id="payrollForm"
			action="${pageContext.request.contextPath}/salary/dayworker/register.do"
			method="post">

			<!-- 사원 선택 및 필터 동기화를 위한 Hidden 필드 -->
			<input type="hidden" name="empId" id="selectedEmpId" value="98766" />
			<input type="hidden" name="totalPay" id="formTotalPay" value="100000" />
			<input type="hidden" name="totalDeduct" id="formTotalDeduct"
				value="900" /> <input type="hidden" name="netPay" id="formNetPay"
				value="99100" />

			<!-- 1. 상단 필터 영역 컴포넌트 Include -->
			<jsp:include page="./components/_salaryFilter.jsp" />

			<!-- 2. 메인 2열 레이아웃 (좌측: 사원목록 / 우측: 상세 급여 계산) -->
			<div class="payroll-grid">
				<jsp:include page="./components/_employeeList.jsp" />
				<jsp:include page="./components/_salaryDetail.jsp" />
			</div>

		</form>
	</main>

	<!-- 푸터 Include -->
	<layout:footer />

	<!-- 클라이언트 동작 스크립트 -->
	<script>
		// 필터 선택 검증
		function checkFilterSelect(selectElement) {
			if (selectElement.value === "") {
				alert("귀속년월/차수를 선택해 주세요.");
			}
		}

		// 폼 제출 전 최종 검증
		document.getElementById('payrollForm').addEventListener('submit', function(e) {
			const year = document.getElementById('belongYear').value;
			const month = document.getElementById('belongMonth').value;
			const degree = document.getElementById('payDegree').value;
			const empId = document.getElementById('selectedEmpId').value;

			if (!year || !month || !degree) {
				e.preventDefault();
				alert("귀속년월 또는 차수가 유효하지 않습니다.");
				if (!year) document.getElementById('belongYear').focus();
				else if (!month) document.getElementById('belongMonth').focus();
				else if (!degree) document.getElementById('payDegree').focus();
				return;
			}

			if (!empId) {
				e.preventDefault();
				alert("사원을 선택해 주세요.");
			}
		});

		// 사원 선택 동적 효과 및 데이터 변경
		function selectWorker(element, empId, name, netPay) {
			const rows = document.querySelectorAll('#workerListBody tr');
			rows.forEach(r => r.classList.remove('active'));
			element.classList.add('active');

			document.getElementById('selectedEmpId').value = empId;
			// 3. 우측 급여 상세 헤더의 사원명 변경
			  const targetEmpSpan = document.getElementById('targetEmpName');
			  if (targetEmpSpan) {
			    targetEmpSpan.innerText = name;
			  }
		}

		// 실시간 급여 및 공제 총액 계산 로직
		function calculateTotal() {
			// 지급총액 계산 (일자별 근무 내역 입력값 반영)
			const payInput = document.getElementById('dailyPayInput').value.replace(/,/g, '') || 0;
			const totalPay = parseInt(payInput, 10);

			// 공제총액 계산 (공제항목 입력값들의 합산)
			const deductionInputs = document.querySelectorAll('.deduction-input');
			let totalDeduct = 0;
			deductionInputs.forEach(input => {
				const val = input.value.replace(/,/g, '') || 0;
				totalDeduct += parseInt(val, 10);
			});

			const netPay = totalPay - totalDeduct;

			// 화면 반영
			document.getElementById('totalPayDisplay').innerText = totalPay.toLocaleString();
			document.getElementById('totalDeductDisplay').innerText = totalDeduct.toLocaleString();
			document.getElementById('netPayDisplay').innerText = netPay.toLocaleString();

			// Hidden 전송값 반영
			document.getElementById('formTotalPay').value = totalPay;
			document.getElementById('formTotalDeduct').value = totalDeduct;
			document.getElementById('formNetPay').value = netPay;
		}

		function addWorker() {
			alert("신규 일용직 근로자 추가 팝업/모달을 호출합니다.");
		}
	</script>

</body>
</html>