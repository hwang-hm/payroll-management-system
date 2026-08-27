<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- ========================================================= -->
<!-- 1. 메인 패널 영역 (일용직 사원 급여 목록 및 작업 버튼) -->
<!-- 1. メインパネル領域 (日雇い社員給与リストおよび操作ボタン) -->
<!-- ========================================================= -->
<div class="panel">
	<div class="panel-header">
		<!-- 사원추가/전체삭제/선택삭제 제어 버튼 -->
		<!-- 社員追加/全削除/選択削除制御ボタン -->
		<button type="button" class="btn btn-primary" onclick="addWorker()">➕
			新規追加</button>
		<div style="display: flex; gap: 6px;">
			<button type="button" class="btn btn-danger-light"
				onclick="deleteAllWorkers()">一括削除</button>
			<button type="button" class="btn btn-secondary"
				onclick="deleteSelectedWorker()">選択削除</button>
		</div>
	</div>

	<!-- 일용직 사원 급여 요약 테이블 -->
	<!-- 日雇い社員給与サマリーテーブル -->
	<div style="overflow-x: auto;">
		<table class="custom-table">
			<thead>
				<tr>
					<th>区分</th>
					<th>氏名</th>
					<th>部署</th>
					<th>差引支給額</th>
				</tr>
			</thead>
			<tbody id="workerListBody">
				<!-- 페이지 진입 시 DB에 저장된 일용직 급여 목록 출력 -->
				<!-- ページ初期表示時にDBに保存された日雇い給与リストを出力 -->
				<c:choose>
					<c:when test="${not empty savedWorkerList}">
						<c:forEach var="worker" items="${savedWorkerList}">
							<!-- 자바스크립트 합산을 위한 data-total-pay, data-total-deduct, data-net-pay 속성 부여 -->
							<!-- JavaScript合算のための data-total-pay, data-total-deduct, data-net-pay 属性の付与 -->
							<tr data-emp-id="${worker.empId}"
								data-total-pay="${worker.totalPay != null ? worker.totalPay : 0}"
								data-total-deduct="${worker.totalDeduct != null ? worker.totalDeduct : 0}"
								data-net-pay="${worker.netPay != null ? worker.netPay : 0}"
								onclick="selectWorker(this, '${worker.empId}', '${worker.empName}')">
								<td>${worker.workerType != null ? worker.workerType : '-'}</td>
								<td>${worker.empName}</td>
								<td>${worker.deptName}</td>
								<td style="text-align: right; font-weight: 600;"><fmt:formatNumber
										value="${worker.netPay}" pattern="#,##0" /></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<!-- 저장된 데이터가 없는 경우 자바스크립트/모달에서 동적으로 <tr> 추가 -->
						<!-- 保存されたデータがない場合、JavaScript/モーダルから動的に <tr> を追加 -->
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</div>

<!-- ========================================================= -->
<!-- 2. 급여지급 사원선택 모달 영역 -->
<!-- 2. 給与支給社員選択モーダル領域 -->
<!-- ========================================================= -->
<div id="workerSelectModal" class="modal-overlay" style="display: none;">
	<div class="modal-content">
		<div class="modal-header">
			<h3 class="modal-title">給与支給社員選択</h3>
		</div>

		<div class="modal-body">
			<!-- 모달 내 필터 및 검색 바 -->
			<!-- モーダル内のフィルターおよび検索バー -->
			<div class="modal-filter-bar">
				<div class="filter-left">
					<div class="search-input-group">
						<input type="text" id="modalSearchKeyword" placeholder="사원검색"
							onkeyup="filterModalWorkers()" />
						<button type="button" class="btn-search-icon"
							onclick="filterModalWorkers()">🔍</button>
					</div>
					<button type="button" class="btn-reset-filter"
						onclick="resetModalFilter()">すべて表示</button>
				</div>
				<div class="filter-right">
					<select id="filterStatus" onchange="filterModalWorkers()">
						<option value="">ステータス別</option>
						<option value="재직">在職</option>
						<option value="퇴직">退職</option>
					</select>
				</div>
			</div>

			<!-- 모달 내 사원 마스터 테이블 목록 -->
			<!-- モーダル内の社員マスターテーブルリスト -->
			<div class="modal-table-wrapper">
				<table class="erp-modal-table">
					<thead>
						<tr>
							<th style="width: 38px; text-align: center;"><input
								type="checkbox" id="checkAllModalWorkers"
								onclick="toggleAllModalWorkers(this)" /></th>
							<th style="width: 70px;">区分</th>
							<th style="width: 110px;">社員番号</th>
							<th style="width: 90px;">氏名</th>
							<th style="width: 100px;">部署</th>
							<th style="width: 80px;">役職</th>
							<th style="width: 70px;">ステータス</th>
						</tr>
					</thead>
					<tbody id="modalWorkerListBody">
						<!-- 비동기(AJAX) 로드 영역 -->
						<!-- 非同期(AJAX)ロード領域 -->
					</tbody>
				</table>
			</div>
		</div>

		<div class="modal-footer">
			<button type="button" class="btn-erp-blue"
				onclick="applySelectedWorkers()">社員選択</button>
			<button type="button" class="btn-erp-gray"
				onclick="closeWorkerModal()">キャンセル</button>
		</div>
	</div>
</div>

<!-- ========================================================= -->
<!-- 3. 모달 UI 및 스타일 정의 -->
<!-- 3. モーダルUIおよびスタイル定義 -->
<!-- ========================================================= -->
<style>
.modal-overlay {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.35);
	display: flex;
	justify-content: center;
	align-items: center;
	z-index: 2000;
}

.modal-content {
	background: #ffffff;
	border-radius: 8px;
	width: 680px;
	max-width: 95%;
	box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
	padding: 20px;
	box-sizing: border-box;
}

.modal-header {
	margin-bottom: 15px;
}

.modal-title {
	font-size: 18px;
	font-weight: 700;
	color: #333;
	margin: 0;
}

.modal-filter-bar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 10px;
	font-size: 13px;
}

.filter-left, .filter-right {
	display: flex;
	align-items: center;
	gap: 6px;
}

.search-input-group {
	display: flex;
	align-items: center;
	border: 1px solid #ccc;
	background: #fff;
}

.search-input-group input {
	border: none;
	outline: none;
	padding: 4px 8px;
	font-size: 12px;
	width: 130px;
}

.btn-search-icon {
	background: none;
	border: none;
	cursor: pointer;
	padding: 2px 6px;
	font-size: 12px;
}

.btn-reset-filter {
	border: 1px solid #ccc;
	background: #f8f9fa;
	cursor: pointer;
	padding: 4px 8px;
	font-size: 12px;
}

.filter-right select {
	border: 1px solid #ccc;
	padding: 4px 6px;
	font-size: 12px;
	outline: none;
}

.modal-table-wrapper {
	max-height: 280px;
	overflow-y: auto;
	border: 1px solid #a6c2d7;
}

.erp-modal-table {
	width: 100%;
	border-collapse: collapse;
	font-size: 12px;
	text-align: center;
}

.erp-modal-table th {
	background-color: #f2f7fb;
	color: #333;
	font-weight: 600;
	border: 1px solid #d0dbe5;
	padding: 6px 4px;
	position: sticky;
	top: 0;
	z-index: 10;
}

.erp-modal-table td {
	border: 1px solid #e1e8ee;
	padding: 6px 4px;
	color: #444;
}

.erp-modal-table tr:hover {
	background-color: #f5f9fc;
	cursor: pointer;
}

.modal-footer {
	margin-top: 20px;
	display: flex;
	justify-content: center;
	gap: 10px;
}

.btn-erp-blue {
	background-color: #337ab7;
	color: white;
	border: none;
	padding: 8px 24px;
	font-size: 13px;
	font-weight: 600;
	border-radius: 4px;
	cursor: pointer;
}

.btn-erp-blue:hover {
	background-color: #286090;
}

.btn-erp-gray {
	background-color: #a0a0a0;
	color: white;
	border: none;
	padding: 8px 24px;
	font-size: 13px;
	font-weight: 600;
	border-radius: 4px;
	cursor: pointer;
}

.btn-erp-gray:hover {
	background-color: #8c8c8c;
}

tr.active {
	background-color: #e6f2ff !important;
	font-weight: bold;
}
</style>

<!-- ========================================================= -->
<!-- 4. 스크립트 로직 정의 -->
<!-- 4. スクリプトロジック定義 -->
<!-- ========================================================= -->
<script>
// 1. 목록 전체 데이터 합산 및 종합정보 갱신 함수
// 1. リスト全体のデータ合算および総合情報更新関数
function calculateGrandSummary() {
	const rows = document.querySelectorAll('#workerListBody tr[data-emp-id]');
	const count = rows.length;

	let totalPaySum = 0;
	let totalDeductSum = 0;
	let netPaySum = 0;

	rows.forEach(row => {
		// data- 속성값 추출 및 콤마(,) 제거
		// data- 属性値の抽出およびカンマ(,)の削除
		const rawTotalPay = String(row.dataset.totalPay || '0').replace(/,/g, '');
		const rawTotalDeduct = String(row.dataset.totalDeduct || '0').replace(/,/g, '');
		let rawNetPay = String(row.dataset.netPay || '0').replace(/,/g, '');

		// 정수 변환 (NaN 발생 시 0으로 예외 처리)
		// 整数変換 (NaN発生時は0として例外処理)
		const totalPay = parseInt(rawTotalPay, 10) || 0;
		const totalDeduct = parseInt(rawTotalDeduct, 10) || 0;
		let netPay = parseInt(rawNetPay, 10) || 0;

		// netPay가 dataset에 없는 경우 표의 4번째 열(td) 텍스트에서 보완
		// netPayがdatasetに存在しない場合、テーブルの4番目の列(td)のテキストから補完
		if (netPay === 0 && row.cells[3]) {
			netPay = parseInt(row.cells[3].innerText.replace(/,/g, '') || '0', 10) || 0;
		}

		// 총합 누적
		// 総計の累積
		totalPaySum += totalPay;
		totalDeductSum += totalDeduct;
		netPaySum += netPay;
	});

	// 외부 JSP(_salaryDetail.jsp)의 하단 요약 카드 업데이트 함수 호출
	// 外部JSP(_salaryDetail.jsp)の realized 下部サマリーカード更新関数の呼び出し
	if (typeof updateGrandSummary === 'function') {
		updateGrandSummary(count, totalPaySum, totalDeductSum, netPaySum);
	}
}

// 2. 페이지 최초 로딩 시 전체 합산 계산 실행
// 2. ページ初期ロード時の全体合算計算の実行
document.addEventListener('DOMContentLoaded', function() {
	calculateGrandSummary();
});

// 3. salary_date_id를 전달받아 사원 급여 목록 HTML 조각을 비동기(AJAX)로 로드하는 함수
// 3. salary_date_idを受け取り社員給与リストHTMLスニペットを非同期(AJAX)でロードする関数
function loadSalaryWorkerList(salaryDateId) {
    const tbody = document.getElementById('workerListBody');
    
    if (!salaryDateId) {
        tbody.innerHTML = '<tr><td colspan="4" style="text-align:center; padding:20px; color:#888;">帰属年月および給与回数を選択してください。</td></tr>';
        calculateGrandSummary(); // 데이터가 없을 때 카드값 0으로 초기화 / データがない場合はカード値を0に初期化
        return;
    }

    tbody.innerHTML = '<tr><td colspan="4" style="text-align:center; padding:20px;">リストを読み込み中...</td></tr>';

    fetch('${pageContext.request.contextPath}/salary/dayworker/getSalaryList.do?salary_date_id=' + salaryDateId)
        .then(res => {
            if (!res.ok) throw new Error('応答失敗 (' + res.status + ')');
            return res.text();
        })
        .then(html => {
            tbody.innerHTML = html;
            
            // 데이터 로드 완료 후 하단 종합정보 카드 업데이트
            // データロード完了後、下部総合情報カードを更新
            calculateGrandSummary();
            
            // 첫 번째 사원 자동 선택 클릭 이벤트 처리
            // 最初の社員の自動選択クリックイベント処理
            const firstRow = tbody.querySelector('tr');
            if (firstRow && firstRow.getAttribute('data-emp-id')) {
                firstRow.click();
            }
        })
        .catch(err => {
            console.error("給与リスト照会失敗:", err);
            tbody.innerHTML = '<tr><td colspan="4" style="text-align:center; padding:20px; color:red;">リストを取得できませんでした。</td></tr>';
            calculateGrandSummary();
        });
}

// 4. 메인 테이블의 사원 행 클릭 시 선택 상태 변경 및 상세 정보 로드
// 4. メインテーブルの社員行クリック時の選択状態変更および詳細情報ロード
function selectWorker(element, empId, empName) {
	const rows = document.querySelectorAll('#workerListBody tr');
	rows.forEach(r => r.classList.remove('active'));
	element.classList.add('active');

	const targetEmpName = document.getElementById('targetEmpName');
	if (targetEmpName) {
		targetEmpName.innerText = empName;
	}

	const selectedEmpIdInput = document.getElementById('selectedEmpId');
	if (selectedEmpIdInput) {
		selectedEmpIdInput.value = empId;
	}

	// 상세 급여 내역 로드 함수 호출
	// 詳細給与明細ロード関数の呼び出し
	if (typeof loadEmployeeSalaryDetail === 'function') {
		loadEmployeeSalaryDetail(empId, empName);
	} else {
		console.error("loadEmployeeSalaryDetail関数が定義されていません。");
	}
}

// 5. [신규추가] 버튼 클릭 시 모달 열기 및 사원 마스터 목록 조회
// 5. [新規追加] ボタンクリック時のモーダル表示および社員マスターリスト照会
function addWorker() {
	const modal = document.getElementById('workerSelectModal');
	if (modal) {
		modal.style.display = 'flex';
		document.getElementById('modalSearchKeyword').value = '';
		document.getElementById('checkAllModalWorkers').checked = false;
		
		fetchWorkerMasterList();
	}
}

// 6. 모달 닫기
// 6. モーダルを閉じる
function closeWorkerModal() {
	const modal = document.getElementById('workerSelectModal');
	if (modal) {
		modal.style.display = 'none';
		document.getElementById('modalWorkerListBody').innerHTML = '';
	}
}

// 7. 비동기로 모달 내 사원 마스터 목록 HTML 수신
// 7. 非同期でモーダル内の社員マスターリストHTMLを受信
function fetchWorkerMasterList() {
	const tbody = document.getElementById('modalWorkerListBody');
	tbody.innerHTML = '<tr><td colspan="7" style="padding:20px; text-align:center;">リストを読み込み中...</td></tr>';

	fetch('${pageContext.request.contextPath}/emp/dayworker/list.do')
		.then(res => {
			if (!res.ok) throw new Error('応答失敗 (' + res.status + ')');
			return res.text();
		})
		.then(html => {
			tbody.innerHTML = html;
		})
		.catch(err => {
			console.error("照会失敗:", err);
			tbody.innerHTML = '<tr><td colspan="7" style="color:red; padding:20px; text-align:center;">日雇い社員リストを取得できませんでした。</td></tr>';
		});
}

// 8. 모달 행 클릭 시 체크박스 토글
// 8. モーダル行クリック時のチェックボックス切り替え
function toggleModalRow(tr, event) {
	if (event.target.type !== 'checkbox') {
		const chk = tr.querySelector('.worker-chk');
		if (chk) chk.checked = !chk.checked;
	}
}

// 9. 모달 전체 선택/해제 토글
// 9. モーダル全体の全選択/全解除切り替え
function toggleAllModalWorkers(master) {
	const chks = document.querySelectorAll('#modalWorkerListBody .worker-chk');
	chks.forEach(chk => {
		if (chk.closest('tr').style.display !== 'none') {
			chk.checked = master.checked;
		}
	});
}

// 10. 모달에서 선택한 사원을 메인 급여 목록 테이블에 추가
// 10. モーダルで選択した社員をメイン給与リストテーブルに追加
function applySelectedWorkers() {
	const checkedBoxes = document.querySelectorAll('#modalWorkerListBody .worker-chk:checked');
	if (checkedBoxes.length === 0) {
		alert('社員を選択してください。');
		return;
	}

	const mainTbody = document.getElementById('workerListBody');
	let addedCount = 0;

	// 빈 목록 placeholder 행 제거 (data-emp-id 없는 tr)
	// 空リストのプレースホルダー行を削除 (data-emp-id のない tr)
	mainTbody.querySelectorAll('tr:not([data-emp-id])').forEach(r => r.remove());

	checkedBoxes.forEach(chk => {
		const empId = chk.dataset.empId;
		const empName = chk.dataset.empName;
		const deptName = chk.dataset.deptName;
		const workerType = chk.dataset.workerType || '일용직';

		// 중복 추가 방지 검사
		// 重複追加防止チェック
		let exists = false;
		mainTbody.querySelectorAll('tr').forEach(r => {
			if (r.getAttribute('data-emp-id') === empId) exists = true;
		});

		if (!exists) {
			const tr = document.createElement('tr');
			tr.setAttribute('data-emp-id', empId);
			// 기본 합산용 data 속성 세팅
			// 基本合算用data属性の設定
			tr.setAttribute('data-total-pay', '0');
			tr.setAttribute('data-total-deduct', '0');
			tr.setAttribute('data-net-pay', '0');
			tr.onclick = function() { selectWorker(this, empId, empName); };
			tr.innerHTML =
				'<td>' + workerType + '</td>' +
				'<td>' + empName + '</td>' +
				'<td>' + deptName + '</td>' +
				'<td style="text-align: right; font-weight: 600;">0</td>';
			mainTbody.appendChild(tr);
			addedCount++;
		}
	});

	if (addedCount > 0) {
		alert(addedCount + '名の社員を追加しました。');
		calculateGrandSummary(); // 추가 후 종합 카드 업데이트 / 追加後総合カード更新
		
		const lastRow = mainTbody.lastElementChild;
		if (lastRow) lastRow.click();
	} else {
		alert('選択した社員はすでにリストに存在します。');
	}

	closeWorkerModal();
}

// 11. 선택된 사원 삭제
// 11. 選択された社員の削除
function deleteSelectedWorker() {
	const activeRow = document.querySelector('#workerListBody tr.active');
	if (!activeRow) {
		alert('削除する社員を選択してください。');
		return;
	}
	if (confirm('選択した社員をリストから削除しますか？')) {
		activeRow.remove();
		calculateGrandSummary(); // 삭제 후 종합 카드 업데이트 / 削除後総合カード更新

		const firstRow = document.querySelector('#workerListBody tr');
		if (firstRow) {
			firstRow.click();
		} else {
			document.getElementById('targetEmpName').innerText = '-';
			document.getElementById('selectedEmpId').value = '';
		}
	}
}

// 12. 전체 사원 목록 삭제
// 12. 全社員リストの削除
function deleteAllWorkers() {
	if (confirm('全社員リストをクリアしますか？')) {
		document.getElementById('workerListBody').innerHTML = '';
		document.getElementById('targetEmpName').innerText = '-';
		document.getElementById('selectedEmpId').value = '';
		calculateGrandSummary(); // 전체 삭제 후 종합 카드 업데이트 / 全削除後総合カード更新
	}
}

// 13. 모달 사원 검색 및 상태별 필터링
// 13. モーダル社員の検索およびステータス別フィルタリング
function filterModalWorkers() {
	const keyword = document.getElementById('modalSearchKeyword').value.toLowerCase().trim();
	const statusFilter = document.getElementById('filterStatus').value;
	const rows = document.querySelectorAll('#modalWorkerListBody tr');

	rows.forEach(row => {
		const text = row.innerText.toLowerCase();
		const matchKeyword = text.includes(keyword);
		const matchStatus = !statusFilter || text.includes(statusFilter);

		if (matchKeyword && matchStatus) {
			row.style.display = '';
		} else {
			row.style.display = 'none';
		}
	});
}

// 14. 모달 필터 조건 초기화
// 14. モーダルフィルター条件の初期化
function resetModalFilter() {
	document.getElementById('modalSearchKeyword').value = '';
	document.getElementById('filterStatus').value = '';
	filterModalWorkers();
}
</script>