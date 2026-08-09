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
			<input type="hidden" name="empId" id="selectedEmpId" value="" /> <input
				type="hidden" name="totalPay" id="formTotalPay" value="0" /> <input
				type="hidden" name="totalDeduct" id="formTotalDeduct" value="0" />
			<input type="hidden" name="netPay" id="formNetPay" value="0" />

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
		// DOM 로드 완료 후 초기화
		document.addEventListener("DOMContentLoaded", function() {
			
			// 단일 Form 제출 이벤트 연동 (중복 제출 방지 및 AJAX 통합)
			const payrollForm = document.getElementById('payrollForm');
			if (payrollForm) {
				payrollForm.addEventListener('submit', function(e) {
					e.preventDefault(); // 동기 폼 제출 방지
					
					// 1. 유효성 검증
					const year = document.getElementById('payYear')?.value;
					const month = document.getElementById('payMonth')?.value;
					const degree = document.getElementById('paySeq')?.value;
					const empId = document.getElementById('selectedEmpId')?.value;

					if (!year || !month || !degree) {
						alert("귀속년월 또는 차수가 유효하지 않습니다.");
						if (!year) document.getElementById('payYear')?.focus();
						else if (!month) document.getElementById('payMonth')?.focus();
						else if (!degree) document.getElementById('paySeq')?.focus();
						return;
					}

					if (!empId) {
						alert("사원을 선택해 주세요.");
						return;
					}

					// 2. 유효성 통과 시 AJAX 저장 실행
					saveSalaryData();
				});
			}
		});

		// 사원 선택 이벤트 (행 클릭 시)
		function selectWorker(element, empId, name) {
			const rows = document.querySelectorAll('#workerListBody tr');
			rows.forEach(r => r.classList.remove('active'));
			
			if (element) {
				element.classList.add('active');
			}

			// hidden input 업데이트
			document.getElementById('selectedEmpId').value = empId;
			
			// 우측 급여 상세 헤더 사원명 변경
			const targetEmpSpan = document.getElementById('targetEmpName');
			if (targetEmpSpan) {
				targetEmpSpan.innerText = name;
			}

			// 우측 급여/공제 상세 AJAX 조회 호출
			if (typeof loadEmployeeSalaryDetail === 'function') {
				loadEmployeeSalaryDetail(empId, name);
			}
		}

		// 급여 데이터 AJAX 전송 함수
		function saveSalaryData() {
			const form = document.getElementById('payrollForm');
			const empId = document.getElementById('selectedEmpId')?.value;

			const rawFormData = new FormData(form);
			const params = new URLSearchParams();

			for (const [key, value] of rawFormData.entries()) {
				// 금액 관련 파라미터 천단위 콤마(,) 제거
				if (['amount', 'totalPay', 'totalDeduct', 'netPay'].includes(key)) {
					params.append(key, value.replace(/,/g, ''));
				} else {
					params.append(key, value);
				}
			}

			fetch(form.action, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
				},
				body: params.toString()
			})
			.then(async response => {
			    // 서버가 200 OK가 아닐 때 (500, 404, 405 등)
			    if (!response.ok) {
			        // 서버가 보낸 HTML 에러 창 또는 텍스트 읽어오기
			        const errorText = await response.text();
			        console.error("서버 에러 원문:", errorText);
			        
			        // alert으로 에러 내용 일부 출력
			        throw new Error(`[HTTP ${response.status}] 서버 오류 발생!\n요청 URL: ${form.action}\n네트워크 탭(F12)을 확인하세요.`);
			    }
			    return response.json();
			})
			.then(data => {
			    alert(data.message);
			    if (data.status === 'success') {
			        location.reload();
			    }
			})
			.catch(error => {
			    console.error('AJAX Error:', error);
			    alert(error.message);
			});
		}

		// 신규 추가 알림
		function addWorker() {
			alert("신규 일용직 근로자 추가 팝업/모달을 호출합니다.");
		}
	</script>

</body>
</html>