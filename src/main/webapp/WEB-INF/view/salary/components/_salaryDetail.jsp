<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
						<th>구분</th>
						<th>지급항목명</th>
						<th>과세</th>
						<th>지급액</th>
					</tr>
				</thead>
				<tbody id="payDetailBody">
					<!-- 사원 선택 시 AJAX로 기본급 항목이 동적 렌더링됩니다 -->
					<tr>
						<td colspan="4" class="text-center">좌측에서 사원을 선택해 주세요.</td>
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
					<!-- 사원 선택 시 salary_item 기준 동적 공제항목 렌더링 -->
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
			지급총액 : <span id="totalPayDisplay" style="color: #2563eb;">0</span> 원
		</div>
		<div>
			공제총액 : <span id="totalDeductDisplay" style="color: #ef4444;">0</span>
			원
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
// 사원 선택 시 백엔드에서 사원의 기존 급여 상세 또는 active salary_item 항목 조회
function loadEmployeeSalaryDetail(empId, empName) {
	const year = document.getElementById('belongYear').value;
	const month = document.getElementById('belongMonth').value;
	const degree = document.getElementById('payDegree').value;

	if (!year || !month || !degree) {
		alert("귀속연월과 급여차수를 먼저 선택해 주세요.");
		return;
	}

	fetch('${pageContext.request.contextPath}/salary/getEmpDetail.do?empId=' + empId + '&belongYear=' + year + '&belongMonth=' + month + '&payDegree=' + degree)
		.then(res => res.json())
		.then(data => {
			renderSalaryTables(data);
		})
		.catch(err => {
			console.error("Detail load error:", err);
		});
}

// 테이블 동적 렌더링
function renderSalaryTables(data) {
	const payBody = document.getElementById('payDetailBody');
	const deductBody = document.getElementById('deductDetailBody');

	payBody.innerHTML = '';
	deductBody.innerHTML = '';

	let hasPay = false;

	if (data.items && data.items.length > 0) {
		data.items.forEach(item => {
			if (item.itemType === 'PAY') {
				hasPay = true;
				payBody.insertAdjacentHTML('beforeend', `
					<tr>
						<td><span class="badge bg-primary">지급</span></td>
						<td>
							\${item.itemName}
							<input type="hidden" name="salaryItemId" value="\${item.salaryItemId || ''}" />
							<input type="hidden" name="itemType" value="PAY" />
							<input type="hidden" name="itemName" value="\${item.itemName}" />
							<input type="hidden" name="taxYn" value="\${item.taxYn}" />
						</td>
						<td>\${item.taxYn}</td>
						<td>
							<input type="text" name="amount" value="\${item.amount}" class="input-text pay-amount" style="width: 90px; text-align: right;" oninput="calculateTotal()" />
						</td>
					</tr>
				`);
			} else if (item.itemType === 'DEDUCT') {
				deductBody.insertAdjacentHTML('beforeend', `
					<tr>
						<td class="label-col">\${item.itemName}</td>
						<td class="val-col">
							<input type="hidden" name="salaryItemId" value="\${item.salaryItemId || ''}" />
							<input type="hidden" name="itemType" value="DEDUCT" />
							<input type="hidden" name="itemName" value="\${item.itemName}" />
							<input type="hidden" name="taxYn" value="\${item.taxYn}" />
							<input type="text" name="amount" value="\${item.amount}" class="deduction-input deduct-amount" oninput="calculateTotal()" />
							<input type="hidden" name="memo" value="" />
						</td>
					</tr>
				`);
			}
		});
	}

	// 기존 저장된 PAY가 없으면 일용직 기본급 입력 칸 기본 추가
	if (!hasPay) {
		payBody.innerHTML = `
			<tr>
				<td><span class="badge bg-primary">지급</span></td>
				<td>
					기본급
					<input type="hidden" name="salaryItemId" value="" />
					<input type="hidden" name="itemType" value="PAY" />
					<input type="hidden" name="itemName" value="기본급" />
					<input type="hidden" name="taxYn" value="Y" />
				</td>
				<td>Y</td>
				<td>
					<input type="text" name="amount" value="100000" class="input-text pay-amount" style="width: 90px; text-align: right;" oninput="calculateTotal()" />
				</td>
			</tr>
		`;
	}

	calculateTotal();
}

// 실시간 정산 수식
function calculateTotal() {
	let totalPay = 0;
	let totalDeduct = 0;

	document.querySelectorAll('.pay-amount').forEach(el => {
		totalPay += parseInt(el.value.replace(/,/g, '') || 0, 10);
	});

	document.querySelectorAll('.deduct-amount').forEach(el => {
		totalDeduct += parseInt(el.value.replace(/,/g, '') || 0, 10);
	});

	const netPay = totalPay - totalDeduct;

	document.getElementById('totalPayDisplay').innerText = totalPay.toLocaleString();
	document.getElementById('totalDeductDisplay').innerText = totalDeduct.toLocaleString();
	document.getElementById('netPayDisplay').innerText = netPay.toLocaleString();

	document.getElementById('formTotalPay').value = totalPay;
	document.getElementById('formTotalDeduct').value = totalDeduct;
	document.getElementById('formNetPay').value = netPay;
}
</script>