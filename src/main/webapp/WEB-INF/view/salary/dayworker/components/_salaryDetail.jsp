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

		<!-- 우측-좌: 일자별 근무 및 지급액 / 右-左: 日付別勤務および支給額 -->
		<div>
			<div class="panel-header">
				<h3 class="panel-title" id="selectedEmpName">
					[<span id="targetEmpName">選択された社員なし</span>] 給与詳細
				</h3>
			</div>
			<table class="custom-table">
				<thead>
					<tr>
						<th style="width: 22%; text-align: center;">日付</th>
						<th style="width: 12%; text-align: center;">支給率</th>
						<th style="width: 22%; text-align: center;">支給額</th>
						<th style="width: 22%; text-align: center;">所得税</th>
						<th style="width: 22%; text-align: center;">地方所得税</th>
					</tr>
				</thead>
				<tbody id="payDetailBody">
					<tr>
						<td colspan="5" class="text-center" style="padding: 20px;">
							左側から社員を選択してください。</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- 우측-우: 공제 항목 세부 / 右-右: 控除項目詳細 -->
		<div>
			<div class="panel-header">
				<h3 class="panel-title">控除項目</h3>
			</div>

			<table class="deduction-table">
				<tbody id="deductDetailBody">
					<tr>
						<td colspan="2" class="text-center">社員を選択してください。</td>
					</tr>
				</tbody>
			</table>
		</div>

	</div>

	<!-- 요약 및 합계 바 / サマリーおよび合計バー -->
	<div class="summary-bar">
		<div>
			支給総額 : <span id="totalPayDisplay"
				style="color: #2563eb; font-weight: 700;">0</span> 円
		</div>
		<div>
			控除総額 : <span id="totalDeductDisplay"
				style="color: #ef4444; font-weight: 700;">0</span> 円
		</div>
	</div>

	<!-- 최종 실지급액 배너 / 最終差引支給額バナー -->
	<div class="total-hero-bar">
		差引支給額 : <span id="netPayDisplay">0</span>円
	</div>

	<!-- 제출 버튼 / 送信ボタン -->
	<div class="bottom-actions">
		<button type="submit" class="btn btn-primary">保存</button>
		<button type="button" class="btn btn-secondary"
			onclick="resetSalaryDetailForm()">クリア</button>
	</div>

</div>

<script>
// 사원 선택 시 실행되는 메인 함수
// 社員選択時に実行されるメイン関数
async function loadEmployeeSalaryDetail(empId, empName) {
	const year = document.getElementById('payYear').value;
	const month = document.getElementById('payMonth').value;
	const degree = document.getElementById('paySeq').value;

	if (!year || !month || !degree) {
		alert("帰属年月と給与回数を先に選択してください。");
		return;
	}

	const hasWork = await loadWorkHistory(empId, year, month);

	if (!hasWork) {
		alert("該当月の勤務履歴が存在しません。");
	}

	await loadDeductionDetail(empId, year, month, degree, hasWork);
	setSaveButtonStatus(hasWork);
}

// 1. 일자별 근무 내역 로드
// 1. 日付別勤務履歴のロード
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
		console.error("근무 내역 로드 실패 / 勤務履歴のロード失敗:", err);
		payBody.innerHTML = '<tr><td colspan="5" class="text-center">データの読み込み中にエラーが発生しました。</td></tr>';
		return false;
	}
}

// 2. 급여/공제 항목 상세 불러오기 (PAY 마스터 키 바인딩 추가)
// 2. 給与/控除項目の詳細取得 (PAYマスターキーバ인딩追加)
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
				// バックエンドから PAY 項目の設定値が渡された場合、salaryItemId をバインディング
				if (item.itemType === 'PAY') {
					const paySalaryItemId = document.getElementById('paySalaryItemId');
					if (paySalaryItemId && item.salaryItemId) {
						paySalaryItemId.value = item.salaryItemId;
					}
				} 
				// 공제 항목 테이블 생성
				// 控除項目テーブルの生成
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
			deductBody.innerHTML = '<tr><td colspan="2" class="text-center">登録された控除項目がありません。</td></tr>';
		}

		calculateTotal();
	} catch (err) {
		console.error("공제 내역 로드 실패 / 控除履歴のロード失敗:", err);
	}
}

// 3. 저장 버튼 상태 제어
// 3. 保存ボタンの状態制御
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
// 4. リアルタイム精算計算式 (PAY項目金額の同期追加)
function calculateTotal() {
	let totalPay = 0;
	let totalDeduct = 0;

	// 지급액 합산 / 支給額の合算
	document.querySelectorAll('.pay-amount-val').forEach(el => {
		totalPay += parseInt(el.value.replace(/,/g, '') || 0, 10);
	});

	// 공제액 합산 / 控除額の合算
	document.querySelectorAll('.deduct-amount').forEach(el => {
		totalDeduct += parseInt(el.value.replace(/,/g, '') || 0, 10);
	});

	const netPay = totalPay - totalDeduct;

	// 화면 표시 / 画面表示
	document.getElementById('totalPayDisplay').innerText = totalPay.toLocaleString();
	document.getElementById('totalDeductDisplay').innerText = totalDeduct.toLocaleString();
	document.getElementById('netPayDisplay').innerText = netPay.toLocaleString();

	// Form 제출용 단일 Hidden Input 갱신
	// Form送信用の単一 Hidden Input の更新
	const formTotalPay = document.getElementById('formTotalPay');
	const formTotalDeduct = document.getElementById('formTotalDeduct');
	const formNetPay = document.getElementById('formNetPay');
	
	if (formTotalPay) formTotalPay.value = totalPay;
	if (formTotalDeduct) formTotalDeduct.value = totalDeduct;
	if (formNetPay) formNetPay.value = netPay;

	// [핵심] PAY 항목 배열 저장을 위한 amount 값 동기화
	// [ポイント] PAY項目の配列保存のための amount 値の同期
	const payItemAmount = document.getElementById('payItemAmount');
	if (payItemAmount) {
		payItemAmount.value = totalPay;
	}
}

//정산일자(salaryDate) 및 공제 항목 목록을 유지하고, 공제 금액만 0으로 초기화하는 함수
//精算日時(salaryDate)および控除項目リストを維持し、控除金額のみ0に初期化する関数
function resetSalaryDetailForm(event) {
	// 폼 기본 제출 동작 방지
	// フォームのデフォルト送信動作を防止
	if (event) {
		event.preventDefault();
	}

	// 1. 공제 항목 입력창의 금액을 모두 0으로 설정
	// 1. 控除項目入力欄の金額をすべて 0 に設定
	const deductInputs = document.querySelectorAll('#deductDetailBody .deduct-amount');
	deductInputs.forEach(input => {
		input.value = '0';
	});

	// 2. 금액 변경에 따른 합계 및 실지급액 재계산 실행
	// 2. 金額変更に伴う合計および差引支給額の再計算実行
	calculateTotal();
}
</script>