<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
.custom-table {
	width: 100%;
	table-layout: fixed;
	border-collapse: collapse;
}

.custom-table th, .custom-table td {
	vertical-align: middle;
}

.detail-split>div:first-child {
	flex: 1.2;
}

.detail-split>div:last-child {
	flex: 0.8;
}
</style>

<div class="panel">
	<div class="detail-split">

		<!-- 우측-좌: 일자별 근무 및 지급액 -->
		<div>
			<div class="panel-header">
				<h3 class="panel-title" id="selectedEmpName">
					[<span id="targetEmpName">선택된 사원 없음</span>] 급여 상세
				</h3>
			</div>
			<table class="custom-table">
				<thead>
					<tr>
						<th style="width: 22%; text-align: center;">일자</th>
						<th style="width: 12%; text-align: center;">지급율</th>
						<th style="width: 22%; text-align: center;">지급액</th>
						<th style="width: 22%; text-align: center;">소득세</th>
						<th style="width: 22%; text-align: center;">지방소득세</th>
					</tr>
				</thead>
				<tbody id="payDetailBody">
					<tr>
						<td colspan="5" class="text-center" style="padding: 20px;">좌측에서
							사원을 선택해 주세요.</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- 우측-우: 공제 항목 세부 -->
		<div>
			<div class="panel-header">
				<h3 class="panel-title">공제항목</h3>
			</div>

			<table class="deduction-table">
				<tbody id="deductDetailBody">
					<tr>
						<td colspan="2" class="text-center">사원을 선택해 주세요.</td>
					</tr>
				</tbody>
			</table>
		</div>

	</div>

	<!-- 요약 및 합계 바 -->
	<div class="summary-bar">
		<div>
			지급총액 : <span id="totalPayDisplay"
				style="color: #2563eb; font-weight: 700;">0</span> 원
		</div>
		<div>
			공제총액 : <span id="totalDeductDisplay"
				style="color: #ef4444; font-weight: 700;">0</span> 원
		</div>
	</div>

	<!-- 최종 실지급액 배너 -->
	<div class="total-hero-bar">
		실지급액 : <span id="netPayDisplay">0</span>원
	</div>

	<!-- 제출 버튼 -->
	<div class="bottom-actions">
		<button type="submit" class="btn btn-primary">저장</button>
		<button type="reset" class="btn btn-secondary">내용 지우기</button>
	</div>

</div>

<script>
// 사원 선택 시 실행되는 메인 함수
async function loadEmployeeSalaryDetail(empId, empName) {
	const year = document.getElementById('payYear').value;
	const month = document.getElementById('payMonth').value;
	const degree = document.getElementById('paySeq').value;

	if (!year || !month || !degree) {
		alert("귀속연월과 급여차수를 먼저 선택해 주세요.");
		return;
	}

	const hasWork = await loadWorkHistory(empId, year, month);

	if (!hasWork) {
		alert("해당 월의 근무 내역이 존재하지 않습니다.");
	}

	await loadDeductionDetail(empId, year, month, degree, hasWork);
	setSaveButtonStatus(hasWork);
}

// 1. 일자별 근무 내역 로드
async function loadWorkHistory(empId, year, month) {
	const payBody = document.getElementById('payDetailBody');
	
	try {
		const res = await fetch('${pageContext.request.contextPath}/salary/dayworker/getWorkLog.do?empId=' + empId + '&payYear=' + year + '&payMonth=' + month);
		const html = await res.text();
		
		payBody.innerHTML = html;
		calculateTotal();
		
		const hasWork = payBody.querySelectorAll('.pay-amount-val').length > 0;
		return hasWork;
		
	} catch (err) {
		console.error("근무 내역 로드 실패:", err);
		payBody.innerHTML = '<tr><td colspan="5" class="text-center">데이터를 불러오는 중 오류가 발생했습니다.</td></tr>';
		return false;
	}
}

// 2. 급여/공제 항목 상세 불러오기 (PAY 마스터 키 바인딩 추가)
async function loadDeductionDetail(empId, year, month, degree, hasWork) {
	const deductBody = document.getElementById('deductDetailBody');

	try {
		const res = await fetch('${pageContext.request.contextPath}/salary/getEmpDetail.do?empId=' + empId + '&payYear=' + year + '&payMonth=' + month + '&paySeq=' + degree);
		const data = await res.json();
		
		deductBody.innerHTML = '';

		if (data.items && data.items.length > 0) {
			const disabledAttr = !hasWork ? 'disabled style="background-color: #f1f5f9; cursor: not-allowed;"' : '';

			data.items.forEach(item => {
				// 백엔드에서 PAY 항목 설정 값이 넘어올 경우 salaryItemId 바인딩
				if (item.itemType === 'PAY') {
					const paySalaryItemId = document.getElementById('paySalaryItemId');
					if (paySalaryItemId && item.salaryItemId) {
						paySalaryItemId.value = item.salaryItemId;
					}
				} 
				// 공제 항목 테이블 생성
				else if (item.itemType === 'DEDUCT') {
					deductBody.insertAdjacentHTML('beforeend', `
						<tr>
							<td class="label-col">\${item.itemName}</td>
							<td class="val-col">
								<input type="hidden" name="salaryItemId" value="\${item.salaryItemId || ''}" />
								<input type="hidden" name="itemType" value="DEDUCT" />
								<input type="hidden" name="itemName" value="\${item.itemName}" />
								<input type="text" name="amount" value="\${item.amount}" 
									class="deduction-input deduct-amount" 
									oninput="this.value = this.value.replace(/[^0-9]/g, ''); calculateTotal();" 
									\${disabledAttr} />
							</td>
						</tr>
					`);
				}
			});
		} else {
			deductBody.innerHTML = '<tr><td colspan="2" class="text-center">등록된 공제 항목이 없습니다.</td></tr>';
		}

		calculateTotal();
	} catch (err) {
		console.error("공제 내역 로드 실패:", err);
	}
}

// 3. 저장 버튼 상태 제어
function setSaveButtonStatus(hasWork) {
	const saveBtn = document.querySelector('.bottom-actions .btn-primary');
	if (saveBtn) {
		saveBtn.disabled = !hasWork;
		if (!hasWork) {
			saveBtn.style.opacity = '0.5';
			saveBtn.style.cursor = 'not-allowed';
		} else {
			saveBtn.style.opacity = '1';
			saveBtn.style.cursor = 'pointer';
		}
	}
}

// 4. 실시간 정산 수식 (PAY 항목 금액 동기화 추가)
function calculateTotal() {
	let totalPay = 0;
	let totalDeduct = 0;

	// 지급액 합산
	document.querySelectorAll('.pay-amount-val').forEach(el => {
		totalPay += parseInt(el.value.replace(/,/g, '') || 0, 10);
	});

	// 공제액 합산
	document.querySelectorAll('.deduct-amount').forEach(el => {
		totalDeduct += parseInt(el.value.replace(/,/g, '') || 0, 10);
	});

	const netPay = totalPay - totalDeduct;

	// 화면 표시
	document.getElementById('totalPayDisplay').innerText = totalPay.toLocaleString();
	document.getElementById('totalDeductDisplay').innerText = totalDeduct.toLocaleString();
	document.getElementById('netPayDisplay').innerText = netPay.toLocaleString();

	// Form 제출용 단일 Hidden Input 갱신
	const formTotalPay = document.getElementById('formTotalPay');
	const formTotalDeduct = document.getElementById('formTotalDeduct');
	const formNetPay = document.getElementById('formNetPay');
	
	if (formTotalPay) formTotalPay.value = totalPay;
	if (formTotalDeduct) formTotalDeduct.value = totalDeduct;
	if (formNetPay) formNetPay.value = netPay;

	// [핵심] PAY 항목 배열 저장을 위한 amount 값 동기화
	const payItemAmount = document.getElementById('payItemAmount');
	if (payItemAmount) {
		payItemAmount.value = totalPay;
	}
}
</script>