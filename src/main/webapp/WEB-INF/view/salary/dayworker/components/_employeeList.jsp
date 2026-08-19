<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 1. 메인 패널 영역 -->
<div class="panel">
	<div class="panel-header">
		<button type="button" class="btn btn-primary" onclick="addWorker()">➕
			신규추가</button>
		<div style="display: flex; gap: 6px;">
			<button type="button" class="btn btn-danger-light"
				onclick="deleteAllWorkers()">전체삭제</button>
			<button type="button" class="btn btn-secondary"
				onclick="deleteSelectedWorker()">선택삭제</button>
		</div>
	</div>

	<div style="overflow-x: auto;">
		<table class="custom-table">
			<thead>
				<tr>
					<th>구분</th>
					<th>성명</th>
					<th>부서</th>
					<th>실지급액</th>
				</tr>
			</thead>
			<tbody id="workerListBody">
				<!-- 페이지 진입 시 DB에 저장된 일용직 급여 목록 출력 -->
				<c:choose>
					<c:when test="${not empty savedWorkerList}">
						<c:forEach var="worker" items="${savedWorkerList}">
							<tr data-emp-id="${worker.empId}"
								onclick="selectWorker(this, '${worker.empId}', '${worker.empName}')">
								<td>${worker.workerType != null ? worker.workerType : '일용직'}</td>
								<td>${worker.empName}</td>
								<td>${worker.deptName}</td>
								<td style="text-align: right; font-weight: 600;"><fmt:formatNumber
										value="${worker.netPay}" pattern="#,##0" /></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<!-- 저장된 데이터가 없는 경우 자바스크립트/모달에서 동적으로 <tr> 추가 -->
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</div>

<!-- 2. 급여지급 사원선택 모달 영역 -->
<div id="workerSelectModal" class="modal-overlay" style="display: none;">
	<div class="modal-content">
		<div class="modal-header">
			<h3 class="modal-title">급여지급 사원선택</h3>
		</div>

		<div class="modal-body">
			<div class="modal-filter-bar">
				<div class="filter-left">
					<div class="search-input-group">
						<input type="text" id="modalSearchKeyword" placeholder="사원검색"
							onkeyup="filterModalWorkers()" />
						<button type="button" class="btn-search-icon"
							onclick="filterModalWorkers()">🔍</button>
					</div>
					<button type="button" class="btn-reset-filter"
						onclick="resetModalFilter()">전체보기</button>
				</div>
				<div class="filter-right">
					<select id="filterStatus" onchange="filterModalWorkers()">
						<option value="">상태별</option>
						<option value="재직">재직</option>
						<option value="퇴직">퇴직</option>
					</select>
				</div>
			</div>

			<div class="modal-table-wrapper">
				<table class="erp-modal-table">
					<thead>
						<tr>
							<th style="width: 38px; text-align: center;"><input
								type="checkbox" id="checkAllModalWorkers"
								onclick="toggleAllModalWorkers(this)" /></th>
							<th style="width: 70px;">구분</th>
							<th style="width: 110px;">사원번호</th>
							<th style="width: 90px;">성명</th>
							<th style="width: 100px;">부서</th>
							<th style="width: 80px;">직위</th>
							<th style="width: 70px;">상태</th>
						</tr>
					</thead>
					<tbody id="modalWorkerListBody">
						<!-- 비동기 로드 영역 -->
					</tbody>
				</table>
			</div>
		</div>

		<div class="modal-footer">
			<button type="button" class="btn-erp-blue"
				onclick="applySelectedWorkers()">사원선택</button>
			<button type="button" class="btn-erp-gray"
				onclick="closeWorkerModal()">선택취소</button>
		</div>
	</div>
</div>

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

<!-- 4. 스크립트 정의 -->
<script>
//salary_date_id를 받아서 사원 급여 목록 HTML 조각을 비동기로 로드하는 함수
function loadSalaryWorkerList(salaryDateId) {
    const tbody = document.getElementById('workerListBody');
    
    // salaryDateId가 없는 경우 (초기 상태 등) 예외 처리
    if (!salaryDateId) {
        tbody.innerHTML = '<tr><td colspan="4" style="text-align:center; padding:20px; color:#888;">귀속연월 및 급여차수를 선택해주세요.</td></tr>';
        return;
    }

    tbody.innerHTML = '<tr><td colspan="4" style="text-align:center; padding:20px;">목록을 불러오는 중...</td></tr>';

    // Handler 호출 (GET 방식, Query Parameter 전달)
    fetch('${pageContext.request.contextPath}/salary/dayworker/getSalaryList.do?salary_date_id=' + salaryDateId)
        .then(res => {
            if (!res.ok) throw new Error('응답 실패 (' + res.status + ')');
            return res.text(); // employeeList.jsp의 <tr> 조각들을 받음
        })
        .then(html => {
            tbody.innerHTML = html;
            
            // 데이터 로드 완료 후 첫 번째 사원 자동 선택 (상세 영역 연동)
            const firstRow = tbody.querySelector('tr');
            if (firstRow && firstRow.getAttribute('data-emp-id')) {
                firstRow.click();
            }
        })
        .catch(err => {
            console.error("급여 목록 조회 실패:", err);
            tbody.innerHTML = '<tr><td colspan="4" style="text-align:center; padding:20px; color:red;">목록을 가져올 수 없습니다.</td></tr>';
        });
}

// 메인 테이블의 사원 행 클릭 시 실행
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

	if (typeof loadEmployeeSalaryDetail === 'function') {
		loadEmployeeSalaryDetail(empId, empName);
	} else {
		console.error("loadEmployeeSalaryDetail 함수가 정의되지 않았습니다.");
	}
}

// 1. [신규추가] 버튼 클릭 시 모달 열기 및 데이터 로드
function addWorker() {
	const modal = document.getElementById('workerSelectModal');
	if (modal) {
		modal.style.display = 'flex';
		document.getElementById('modalSearchKeyword').value = '';
		document.getElementById('checkAllModalWorkers').checked = false;
		
		fetchWorkerMasterList();
	}
}

// 2. 모달 닫기
function closeWorkerModal() {
	const modal = document.getElementById('workerSelectModal');
	if (modal) {
		modal.style.display = 'none';
		document.getElementById('modalWorkerListBody').innerHTML = '';
	}
}

// 3. 비동기로 모달 목록 HTML 수신
function fetchWorkerMasterList() {
	const tbody = document.getElementById('modalWorkerListBody');
	tbody.innerHTML = '<tr><td colspan="7" style="padding:20px; text-align:center;">목록을 불러오는 중...</td></tr>';

	fetch('${pageContext.request.contextPath}/emp/dayworker/list.do')
		.then(res => {
			if (!res.ok) throw new Error('응답 실패 (' + res.status + ')');
			return res.text();
		})
		.then(html => {
			tbody.innerHTML = html;
		})
		.catch(err => {
			console.error("조회 실패:", err);
			tbody.innerHTML = '<tr><td colspan="7" style="color:red; padding:20px; text-align:center;">일용직 목록을 가져올 수 없습니다.</td></tr>';
		});
}

// 4. 모달 행 클릭 시 체크박스 토글
function toggleModalRow(tr, event) {
	if (event.target.type !== 'checkbox') {
		const chk = tr.querySelector('.worker-chk');
		if (chk) chk.checked = !chk.checked;
	}
}

// 5. 모달 전체 선택/해제 토글
function toggleAllModalWorkers(master) {
	const chks = document.querySelectorAll('#modalWorkerListBody .worker-chk');
	chks.forEach(chk => {
		if (chk.closest('tr').style.display !== 'none') {
			chk.checked = master.checked;
		}
	});
}

// 6. [사원선택] 버튼 클릭 시 메인 목록에 추가
function applySelectedWorkers() {
	const checkedBoxes = document.querySelectorAll('#modalWorkerListBody .worker-chk:checked');
	if (checkedBoxes.length === 0) {
		alert('사원을 선택해주세요.');
		return;
	}

	const mainTbody = document.getElementById('workerListBody');
	let addedCount = 0;

	checkedBoxes.forEach(chk => {
		const empId = chk.dataset.empId;
		const empName = chk.dataset.empName;
		const deptName = chk.dataset.deptName;
		const workerType = chk.dataset.workerType || '일용직';

		let exists = false;
		mainTbody.querySelectorAll('tr').forEach(r => {
			if (r.getAttribute('data-emp-id') === empId) exists = true;
		});

		if (!exists) {
			const tr = document.createElement('tr');
			tr.setAttribute('data-emp-id', empId);
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
		alert(addedCount + '명의 사원이 추가되었습니다.');
		// 새로 추가된 사원 중 첫 번째 사원 자동 선택
		const lastRow = mainTbody.lastElementChild;
		if (lastRow) lastRow.click();
	} else {
		alert('선택한 사원이 이미 목록에 있습니다.');
	}

	closeWorkerModal();
}

// 7. 삭제 관련 기능
function deleteSelectedWorker() {
	const activeRow = document.querySelector('#workerListBody tr.active');
	if (!activeRow) {
		alert('삭제할 사원을 선택해주세요.');
		return;
	}
	if (confirm('선택한 사원을 목록에서 삭제하시겠습니까?')) {
		activeRow.remove();
		// 첫 번째 행으로 재선택
		const firstRow = document.querySelector('#workerListBody tr');
		if (firstRow) {
			firstRow.click();
		} else {
			document.getElementById('targetEmpName').innerText = '-';
			document.getElementById('selectedEmpId').value = '';
		}
	}
}

function deleteAllWorkers() {
	if (confirm('전체 사원 목록을 비우시겠습니까?')) {
		document.getElementById('workerListBody').innerHTML = '';
		document.getElementById('targetEmpName').innerText = '-';
		document.getElementById('selectedEmpId').value = '';
	}
}

// 8. 검색 및 필터링 기능
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

function resetModalFilter() {
	document.getElementById('modalSearchKeyword').value = '';
	document.getElementById('filterStatus').value = '';
	filterModalWorkers();
}
</script>