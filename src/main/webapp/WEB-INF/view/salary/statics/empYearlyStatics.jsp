<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>연도별 개인연봉 통계</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>

<style>
.stat-container {
	width: 1000px;
	margin: 20px auto;
	font-family: 'Malgun Gothic', '맑은 고딕', sans-serif;
	color: #333;
}

.stat-title {
	font-size: 22px;
	font-weight: bold;
	display: flex;
	align-items: center;
	gap: 10px;
}

.stat-subtitle {
	font-size: 13px;
	color: #777;
	margin-top: 6px;
	margin-bottom: 20px;
}

.filter-box {
	background: #f9f9f9;
	border: 1px solid #e2e2e2;
	padding: 12px 20px;
	font-size: 13px;
	margin-bottom: 25px;
	display: flex;
	align-items: center;
	gap: 20px;
}

.filter-box select, .filter-box input {
	padding: 4px 8px;
	font-size: 13px;
	border: 1px solid #ccc;
	outline: none;
}

.search-input-group {
	display: flex;
	align-items: center;
	border: 1px solid #ccc;
	background: #fff;
}

.search-input-group input {
	border: none;
	cursor: pointer;
	width: 120px;
}

.btn-search-icon {
	background: none;
	border: none;
	cursor: pointer;
	padding: 4px 8px;
}

.btn-submit {
	background-color: #337ab7;
	color: #fff;
	border: none;
	padding: 5px 14px;
	font-size: 13px;
	cursor: pointer;
	border-radius: 3px;
}

.btn-submit:hover {
	background-color: #286090;
}

.chart-wrapper {
	position: relative;
	height: 380px;
	margin-bottom: 30px;
}

.stat-table {
	width: 100%;
	border-collapse: collapse;
	font-size: 12px;
	text-align: center;
}

.stat-table th, .stat-table td {
	border: 1px solid #dcdcdc;
	padding: 8px 4px;
}

.stat-table th {
	background-color: #f2f6fa;
	font-weight: bold;
}

.stat-table .sub-row {
	background-color: #fafafa;
}

.rate-plus {
	color: #d9534f;
	font-weight: bold;
}

.rate-minus {
	color: #337ab7;
	font-weight: bold;
}

.rate-zero {
	color: #666;
}

/* === 모달 CSS === */
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

.btn-reset-filter {
	border: 1px solid #ccc;
	background: #f8f9fa;
	cursor: pointer;
	padding: 4px 8px;
	font-size: 12px;
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

.notice-text {
	font-size: 11px;
	color: #888;
	margin-top: 15px;
}
</style>
</head>
<body>
	<!-- 헤더 Include -->
	<layout:headerNav />

	<div class="stat-container">
		<div class="stat-title">
			<span>📈</span> 연도별 개인연봉 통계
		</div>
		<div class="stat-subtitle">귀속년도와 사원을 검색하시면 해당사원의 최근 10개년도 급여현황을
			확인하실 수 있습니다.</div>

		<!-- 검색 폼 -->
		<form id="searchForm"
			action="${pageContext.request.contextPath}/salary/stats/yearlyPersonal.do"
			method="get">
			<!-- 사원 선택 시 채워지는 hidden id -->
			<input type="hidden" id="selectedEmpId" name="empId"
				value="${selectedEmpId}" />

			<div class="filter-box">
				<div>
					* 귀속년도를 선택해 주세요. &nbsp; <select name="year" id="selectedYear">
						<c:forEach var="y" begin="2015" end="${nowYear}">
							<option value="${y}" ${y == selectedYear ? 'selected' : ''}>${y}
								년</option>
						</c:forEach>
					</select>
				</div>

				<div style="display: flex; align-items: center; gap: 6px;">
					대상자를 선택해 주세요.
					<div class="search-input-group">
						<input type="text" id="targetEmpName" name="empName"
							value="${selectedEmpName}" placeholder="사원선택" readonly
							onclick="openWorkerModal()" />
						<button type="button" class="btn-search-icon"
							onclick="openWorkerModal()">🔍</button>
					</div>
				</div>

				<button type="button" class="btn-submit" onclick="submitSearch()">조회</button>
			</div>
		</form>

		<!-- 차트 영역 -->
		<div class="chart-wrapper">
			<canvas id="salaryChart"></canvas>
		</div>

		<!-- 데이터 테이블 -->
		<table class="stat-table">
			<thead>
				<tr>
					<th style="width: 140px;">구분</th>
					<c:forEach var="item" items="${statList}">
						<th>${item.year}년</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>연봉액 (천원)</th>
					<c:forEach var="item" items="${statList}">
						<td><fmt:formatNumber value="${item.totalPayThousand}"
								pattern="#,##0" /></td>
					</c:forEach>
				</tr>
				<tr class="sub-row">
					<th>ㄴ 증가율</th>
					<c:forEach var="item" items="${statList}">
						<td><c:choose>
								<c:when test="${item.salaryGrowthRate > 0}">
									<span class="rate-plus">${item.salaryGrowthRate}%</span>
								</c:when>
								<c:when test="${item.salaryGrowthRate < 0}">
									<span class="rate-minus">${item.salaryGrowthRate}%</span>
								</c:when>
								<c:when test="${item.salaryGrowthRate == 0}">
									<span class="rate-zero">0.0%</span>
								</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose></td>
					</c:forEach>
				</tr>
				<tr>
					<th>공제금액 (천원)</th>
					<c:forEach var="item" items="${statList}">
						<td><fmt:formatNumber value="${item.totalDeductThousand}"
								pattern="#,##0" /></td>
					</c:forEach>
				</tr>
				<tr>
					<th>실지급액 (천원)</th>
					<c:forEach var="item" items="${statList}">
						<td><fmt:formatNumber value="${item.netPayThousand}"
								pattern="#,##0" /></td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
		<div class="notice-text">※ 통계차트는 별도의 인쇄화면을 제공하지 않습니다. 회원님께서
			윈도우즈에서 제공하는 스크린샷(ScreenShot) 기능을 이용하여 인쇄(Ctrl + P)하여 주시길 바랍니다.</div>
	</div>

	<!-- ==================== 사원 선택 모달 영역 ==================== -->
	<div id="workerSelectModal" class="modal-overlay"
		style="display: none;">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title">전체 사원 선택</h3>
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
								<th style="width: 70px;">구분</th>
								<th style="width: 110px;">사원번호</th>
								<th style="width: 90px;">성명</th>
								<th style="width: 100px;">부서</th>
								<th style="width: 80px;">직위</th>
								<th style="width: 70px;">상태</th>
							</tr>
						</thead>
						<tbody id="modalWorkerListBody">
							<!-- 비동기 수신 영역 -->
						</tbody>
					</table>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn-erp-gray"
						onclick="closeWorkerModal()">닫기</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 푸터 Include -->
	<layout:footer />

	<script>
Chart.register(ChartDataLabels);

// 1. 차트 바인딩
document.addEventListener('DOMContentLoaded', function() {
	const dataList = [
		<c:forEach var="item" items="${statList}" varStatus="status">
		{
			year: ${item.year},
			totalPay: ${item.totalPayThousand},
			deduct: ${item.totalDeductThousand},
			netPay: ${item.netPayThousand}
		}${!status.last ? ',' : ''}
		</c:forEach>
	];
	renderChart(dataList);
});

let myChart = null;
function renderChart(data) {
	const labels = data.map(item => item.year + '년');
	const deductData = data.map(item => item.deduct);
	const netPayData = data.map(item => item.netPay);
	const ctx = document.getElementById('salaryChart').getContext('2d');

	if (myChart) myChart.destroy();

	myChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: labels,
			datasets: [
				{ label: '공제금액 (천원)', data: deductData, backgroundColor: '#f8a575', stack: 'stack1' },
				{ label: '실지급액 (천원)', data: netPayData, backgroundColor: '#8da4d0', stack: 'stack1' }
			]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			scales: { x: { stacked: true }, y: { stacked: true } },
			plugins: {
				// ===== 범례(Legend) 위치 설정 추가 =====
	            legend: {
	                display: true,
	                position: 'bottom', // 'top', 'bottom', 'left', 'right' 중 선택
	                labels: {
	                    boxWidth: 15,   // 범례 색상 상자 가로 크기
	                    padding: 15     // 범례와 차트/범례 항목 간 여백
	                }
	            },
				datalabels: {
					color: '#333',
					font: { weight: 'bold', size: 10 },
					formatter: val => val > 0 ? val.toLocaleString() : ''
				}
			}
		}
	});
}

// 2. 모달 열기 및 목록 비동기 조회
function openWorkerModal() {
	const modal = document.getElementById('workerSelectModal');
	if (modal) {
		modal.style.display = 'flex';
		document.getElementById('modalSearchKeyword').value = '';
		fetchWorkerMasterList();
	}
}

function closeWorkerModal() {
	const modal = document.getElementById('workerSelectModal');
	if (modal) {
		modal.style.display = 'none';
		document.getElementById('modalWorkerListBody').innerHTML = '';
	}
}

// 3. 비동기 HTML 수신
function fetchWorkerMasterList() {
	const tbody = document.getElementById('modalWorkerListBody');
	tbody.innerHTML = '<tr><td colspan="6" style="padding:20px; text-align:center;">목록을 불러오는 중...</td></tr>';

	fetch('${pageContext.request.contextPath}/emp/getAllList.do')
		.then(res => {
			if (!res.ok) throw new Error('응답 실패');
			return res.text();
		})
		.then(html => {
			tbody.innerHTML = html;
		})
		.catch(err => {
			console.error(err);
			tbody.innerHTML = '<tr><td colspan="6" style="color:red; padding:20px; text-align:center;">사원 목록을 불러올 수 없습니다.</td></tr>';
		});
}

// 4. 모달에서 사원 행 클릭 시 메인 창 전달
function selectModalEmp(empId, empName) {
	document.getElementById('selectedEmpId').value = empId;
	document.getElementById('targetEmpName').value = empName;
	closeWorkerModal();
}

// 5. 검색 버튼 클릭 시 메인 폼 전송 (귀속년도 + empId로 급여 데이터 조회)
function submitSearch() {
	const empId = document.getElementById('selectedEmpId').value;
	if (!empId) {
		alert('대상 사원을 선택해 주세요.');
		openWorkerModal();
		return;
	}
	document.getElementById('searchForm').submit();
}

// 6. 모달 내부 클라이언트 필터링
function filterModalWorkers() {
	const keyword = document.getElementById('modalSearchKeyword').value.toLowerCase().trim();
	const statusFilter = document.getElementById('filterStatus').value;
	const rows = document.querySelectorAll('#modalWorkerListBody tr');

	rows.forEach(row => {
		const text = row.innerText.toLowerCase();
		const matchKeyword = text.includes(keyword);
		const matchStatus = !statusFilter || text.includes(statusFilter);

		row.style.display = (matchKeyword && matchStatus) ? '' : 'none';
	});
}

function resetModalFilter() {
	document.getElementById('modalSearchKeyword').value = '';
	document.getElementById('filterStatus').value = '';
	filterModalWorkers();
}
</script>
</body>
</html>