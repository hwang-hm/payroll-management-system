<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ヘキサゾーン - 日雇い給与入力・管理</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />

<style>
/* 메인 컨테이너 / メインコンテナ */
.page-container {
	max-width: 1300px;
	margin: 0 auto 50px auto;
	padding: 0 20px;
	font-family: 'Pretendard Variable', Pretendard, -apple-system,
		sans-serif;
	color: #334155;
}

/* 페이지 헤더 / ページヘッダー */
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

/* 1. 상단 필터 & 정산기간 설정 바 / 1. 上部フィルター＆精算期間設定バー */
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

/* 버튼 공통 스타일 / ボタン共通スタイル */
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

/* 2. 메인 2열 그리드 레이아웃 / 2. メイン2列グリッドレイアウト */
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

/* 테이블 공통 스타일 / テーブル共通スタイル */
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

/* 우측 상세 계산 영역 내 2분할 / 右側詳細計算エリア内2分割 */
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

/* 공제항목 테이블 / 控除項目テーブル */
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

/* 합계 요약 영역 및 최종 배너 / 合計サマリーエリアおよび最終バナー */
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

/* 급여 종합정보 스타일 / 給与総合情報スタイル */
.grand-summary-section {
	margin-top: 25px;
	font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, system-ui,
		Roboto, sans-serif;
}

.grand-summary-title {
	font-size: 16px;
	font-weight: 700;
	color: #333;
	margin-bottom: 12px;
}

.grand-summary-container {
	display: flex;
	gap: 12px;
	width: 100%;
}

.summary-card {
	flex: 1;
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 16px 20px;
	border-radius: 8px;
	color: #ffffff;
	font-weight: 700;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

/* 카드별 색상 정의 / カード別カラー定義 */
.card-grey {
	background-color: #82888e;
}

.card-blue {
	background-color: #51b2d6;
}

.card-red {
	background-color: #f16d72;
}

.card-dark {
	background-color: #4a525d;
}

.card-label {
	display: flex;
	align-items: center;
	gap: 6px;
	font-size: 15px;
}

.card-value {
	font-size: 18px;
	letter-spacing: -0.5px;
}

.icon-badge {
	display: inline-flex;
	align-items: center;
	justify-content: center;
	width: 20px;
	height: 20px;
	border-radius: 50%;
	background-color: rgba(255, 255, 255, 0.3);
	font-size: 13px;
	font-weight: bold;
	line-height: 1;
}
</style>
</head>
<body>

	<!-- 헤더 Include / ヘッダー Include -->
	<layout:headerNav />

	<main class="page-container">
		<div class="page-header">
			<div class="page-title-group">
				<span style="font-size: 24px;">🧮</span>
				<h1 class="page-title">給与入力 / 管理 (日雇い)</h1>
			</div>
			<p class="page-subtext">
				日雇い労働者の給与および賞与情報を入力、保存、管理するメニューです。 <span class="highlight">帰属年月と給与回数を先に確認してください！</span>
			</p>
		</div>

		<form id="payrollForm"
			action="${pageContext.request.contextPath}/salary/dayworker/register.do"
			method="post">

			<!-- 사원 선택 및 필터 동기화를 위한 Hidden 필드 -->
			<!-- 社員選択およびフィルター同期のための Hidden フィールド -->
			<input type="hidden" name="empId" id="selectedEmpId" value="" /> <input
				type="hidden" name="totalPay" id="formTotalPay" value="0" /> <input
				type="hidden" name="totalDeduct" id="formTotalDeduct" value="0" />
			<input type="hidden" name="netPay" id="formNetPay" value="0" />

			<!-- 1. 상단 날짜 영역 컴포넌트 Include / 1. 上部日付エリアコンポーネント Include -->
			<jsp:include page="./components/_salaryDate.jsp" />

			<!-- 2. 메인 2열 레이아웃 (좌측: 사원목록 / 우측: 상세 급여 계산) -->
			<!-- 2. メイン2列レイアウト (左側: 社員リスト / 右側: 詳細給与計算) -->
			<div class="payroll-grid">
				<jsp:include page="./components/_employeeList.jsp" />
				<jsp:include page="./components/_salaryDetail.jsp" />
			</div>

		</form>

		<!-- 급여 종합정보 (하단 카드 4개) / 給与総合情報 (下部カード4個) -->
		<div class="grand-summary-section">
			<div class="grand-summary-title">給与総合情報</div>
			<div class="grand-summary-container">
				<!-- 1. 월 합계 / 1. 月合計 -->
				<div class="summary-card card-grey">
					<span class="card-label">月合計</span> <span class="card-value"><span
						id="grandEmpCount">0</span> 件</span>
				</div>
				<!-- 2. 지급 총액 / 2. 支給総額 -->
				<div class="summary-card card-blue">
					<span class="card-label"> <span class="icon-badge">+</span>
						支給総額
					</span> <span class="card-value"><span id="grandTotalPay">0</span>
						円</span>
				</div>
				<!-- 3. 공제 총액 / 3. 控除総額 -->
				<div class="summary-card card-red">
					<span class="card-label"> <span class="icon-badge">-</span>
						控除総額
					</span> <span class="card-value"><span id="grandTotalDeduct">0</span>
						円</span>
				</div>
				<!-- 4. 실지급액 / 4. 差引支給額 -->
				<div class="summary-card card-dark">
					<span class="card-label">差引支給額</span> <span class="card-value"><span
						id="grandNetPay">0</span> 円</span>
				</div>
			</div>
		</div>
	</main>

	<!-- 푸터 Include / フッター Include -->
	<layout:footer />

	<!-- 클라이언트 동작 스크립트 / クライアント動作スクリプト -->
	<script>
		// DOM 로드 완료 후 초기화
		// DOMロード完了後の初期化
		document.addEventListener("DOMContentLoaded", function() {
			
			// 단일 Form 제출 이벤트 연동 (중복 제출 방지 및 AJAX 통합)
			// 単一 Form 送信イベント連動 (重複送信防止および AJAX 統合)
			const payrollForm = document.getElementById('payrollForm');
			if (payrollForm) {
				payrollForm.addEventListener('submit', function(e) {
					e.preventDefault(); // 동기 폼 제출 방지 / 同期フォーム送信の防止
					
					// 1. 유효성 검증 / 1. バリデーション検証
					const year = document.getElementById('payYear')?.value;
					const month = document.getElementById('payMonth')?.value;
					const degree = document.getElementById('paySeq')?.value;
					const empId = document.getElementById('selectedEmpId')?.value;

					if (!year || !month || !degree) {
						alert("帰属年月または回数が有効ではありません。");
						if (!year) document.getElementById('payYear')?.focus();
						else if (!month) document.getElementById('payMonth')?.focus();
						else if (!degree) document.getElementById('paySeq')?.focus();
						return;
					}

					if (!empId) {
						alert("社員を選択してください。");
						return;
					}

					// 2. 유효성 통과 시 AJAX 저장 실행 / 2. 検証通過時に AJAX 保存を実行
					saveSalaryData();
				});
			}
		});

		// 사원 선택 이벤트 (행 클릭 시)
		// 社員選択イベント (行クリック時)
		function selectWorker(element, empId, name) {
			const rows = document.querySelectorAll('#workerListBody tr');
			rows.forEach(r => r.classList.remove('active'));
			
			if (element) {
				element.classList.add('active');
			}

			// hidden input 업데이트 / hidden input の更新
			document.getElementById('selectedEmpId').value = empId;
			
			// 우측 급여 상세 헤더 사원명 변경 / 右側給与詳細ヘッダーの社員名変更
			const targetEmpSpan = document.getElementById('targetEmpName');
			if (targetEmpSpan) {
				targetEmpSpan.innerText = name;
			}

			// 우측 급여/공제 상세 AJAX 조회 호출 / 右側給与・控除詳細 AJAX 照会呼び出し
			if (typeof loadEmployeeSalaryDetail === 'function') {
				loadEmployeeSalaryDetail(empId, name);
			}
		}

		// 급여 데이터 AJAX 전송 함수
		// 給与データ AJAX 送信関数
		function saveSalaryData() {
			const form = document.getElementById('payrollForm');
			const empId = document.getElementById('selectedEmpId')?.value;

			const rawFormData = new FormData(form);
			const params = new URLSearchParams();

			for (const [key, value] of rawFormData.entries()) {
				// 금액 관련 파라미터 천단위 콤마(,) 제거
				// 金額関連パラメータのカンマ(,)除去
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
				// サーバーが 200 OK でない場合 (500, 404, 405 等)
				if (!response.ok) {
					const errorText = await response.text();
					console.error("서버 에러 원문 / サーバーエラー原文:", errorText);
					
					throw new Error(`[HTTP ${response.status}] サーバーエラーが発生しました！\nリクエストURL: ${form.action}\nネットワークタブ(F12)を確認してください。`);
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
		
		// 급여 종합정보(하단 4개 카드) 값 업데이트 함수
		// 給与総合情報 (下部4個のカード) 値更新関数
		function updateGrandSummary(count, totalPay, totalDeduct, netPay) {
			document.getElementById('grandEmpCount').innerText = (count || 0).toLocaleString();
			document.getElementById('grandTotalPay').innerText = (totalPay || 0).toLocaleString();
			document.getElementById('grandTotalDeduct').innerText = (totalDeduct || 0).toLocaleString();
			document.getElementById('grandNetPay').innerText = (netPay || 0).toLocaleString();
		}
		
	</script>

</body>
</html>