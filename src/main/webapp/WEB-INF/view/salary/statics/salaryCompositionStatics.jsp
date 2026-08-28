<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<c:set var="now" value="<%=new java.util.Date()%>" />
<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ヘキサゾーン - 給与項目構成統計</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>

<style>
.stat-container {
	width: 1060px;
	margin: 20px auto;
	font-family: 'Pretendard', 'Malgun Gothic', '맑은 고딕', sans-serif;
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

.charts-flex-wrapper {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 30px;
	gap: 15px;
}

.chart-card {
	width: 330px;
	height: 310px;
	position: relative;
}

/* 테이블 레이아웃 고정 및 라인 정렬 / テーブルレイアウト固定およびライン整列 */
.stat-table-wrapper {
	width: 100%;
	margin-bottom: 20px;
}

.stat-table {
	width: 100%;
	border-collapse: collapse;
	font-size: 12px;
	text-align: center;
	table-layout: fixed; /* 동일 분할 폭 고정 / 均等分割幅固定 */
}

.stat-table th, .stat-table td {
	border: 1px solid #dcdcdc;
	padding: 7px 2px;
	word-break: break-all;
	height: 28px;
	box-sizing: border-box;
}

.stat-table th {
	background-color: #f2f6fa;
	font-weight: bold;
}

.stat-table .sub-row {
	background-color: #fafafa;
}

.highlight-bg {
	background-color: #e6f0fa;
}

.net-pay-header {
	background-color: #5b9bd5 !important;
	color: #fff;
}

.net-pay-cell {
	background-color: #f2f7fc;
	font-weight: bold;
}

.notice-text {
	font-size: 11px;
	color: #888;
	margin-top: 15px;
}

/* 모달 CSS / モーダルCSS */
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
</style>
</head>
<body>
	<layout:headerNav />

	<div class="stat-container">
		<div class="stat-title">
			<span>📊</span> 給与項目構成統計
		</div>
		<div class="stat-subtitle">帰属年月と社員を検索すると、該当社員の給与項目構成表を確認できます。</div>

		<!-- 검색 폼 / 検索フォーム -->
		<form id="searchForm"
			action="${pageContext.request.contextPath}/salary/stats/itemComposition.do"
			method="get">
			<input type="hidden" id="selectedEmpId" name="empId"
				value="${selectedEmpId}" />

			<div class="filter-box">
				<div>
					* 帰属年月を選択してください。 &nbsp; <select name="year" id="selectedYear">
						<c:forEach var="y" begin="2015" end="${nowYear}">
							<option value="${y}" ${y == selectedYear ? 'selected' : ''}>${y}
								年</option>
						</c:forEach>
					</select> <select name="month" id="selectedMonth">
						<c:forEach var="m" begin="1" end="12">
							<fmt:formatNumber var="mm" value="${m}" pattern="00" />
							<option value="${m}" ${m == selectedMonth ? 'selected' : ''}>${mm}
								月</option>
						</c:forEach>
					</select>
				</div>

				<div style="display: flex; align-items: center; gap: 6px;">
					対象者を選択してください。
					<div class="search-input-group">
						<input type="text" id="targetEmpName" name="empName"
							value="${selectedEmpName}" placeholder="社員選択" readonly
							onclick="openWorkerModal()" />
						<button type="button" class="btn-search-icon"
							onclick="openWorkerModal()">🔍</button>
					</div>
				</div>

				<button type="button" class="btn-submit" onclick="submitSearch()">検索</button>
			</div>
		</form>

		<!-- 차트 3개 영역 / チャート3個エリア -->
		<div class="charts-flex-wrapper">
			<div class="chart-card">
				<canvas id="chartTotal"></canvas>
			</div>
			<div class="chart-card">
				<canvas id="chartPay"></canvas>
			</div>
			<div class="chart-card">
				<canvas id="chartDeduct"></canvas>
			</div>
		</div>

		<!-- 동적 테이블 영역 / 動的テーブルエリア -->
		<div class="stat-table-wrapper">
			<!-- 지급항목 테이블 / 支給項目テーブル -->
			<table class="stat-table">
				<colgroup>
					<col style="width: 110px;" />
					<c:forEach begin="1" end="11">
						<col />
					</c:forEach>
					<col style="width: 90px;" />
					<col style="width: 90px;" />
				</colgroup>
				<thead>
					<tr>
						<th>支給項目</th>
						<c:choose>
							<c:when
								test="${not empty statData and not empty statData.payList}">
								<c:forEach var="item" items="${statData.payList}">
									<th>${item.itemName}</th>
								</c:forEach>
								<c:if test="${statData.payList.size() < 11}">
									<c:forEach begin="1" end="${11 - statData.payList.size()}">
										<th></th>
									</c:forEach>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:forEach begin="1" end="11">
									<th></th>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						<th class="highlight-bg">合計</th>
						<th
							style="background: #fff; border-top: none; border-right: none; border-bottom: none;"></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>└ 金額 (円)</th>
						<c:choose>
							<c:when
								test="${not empty statData and not empty statData.payList}">
								<c:forEach var="item" items="${statData.payList}">
									<td><fmt:formatNumber value="${item.amount}"
											pattern="#,##0" /></td>
								</c:forEach>
								<c:if test="${statData.payList.size() < 11}">
									<c:forEach begin="1" end="${11 - statData.payList.size()}">
										<td></td>
									</c:forEach>
								</c:if>
								<td class="highlight-bg"><b><fmt:formatNumber
											value="${statData.totalPay}" pattern="#,##0" /></b></td>
							</c:when>
							<c:otherwise>
								<c:forEach begin="1" end="11">
									<td></td>
								</c:forEach>
								<td class="highlight-bg">0</td>
							</c:otherwise>
						</c:choose>
						<td
							style="background: #fff; border-top: none; border-right: none; border-bottom: none;"></td>
					</tr>
					<tr class="sub-row">
						<th>└ 構成比率</th>
						<c:choose>
							<c:when
								test="${not empty statData and not empty statData.payList}">
								<c:forEach var="item" items="${statData.payList}">
									<td>${item.rate}%</td>
								</c:forEach>
								<c:if test="${statData.payList.size() < 11}">
									<c:forEach begin="1" end="${11 - statData.payList.size()}">
										<td></td>
									</c:forEach>
								</c:if>
								<td class="highlight-bg"><b>100%</b></td>
							</c:when>
							<c:otherwise>
								<c:forEach begin="1" end="11">
									<td></td>
								</c:forEach>
								<td class="highlight-bg">0%</td>
							</c:otherwise>
						</c:choose>
						<td
							style="background: #fff; border-top: none; border-right: none; border-bottom: none;"></td>
					</tr>
				</tbody>
			</table>

			<div style="height: 15px;"></div>

			<!-- 공제항목 테이블 / 控除項目テーブル -->
			<table class="stat-table">
				<colgroup>
					<col style="width: 110px;" />
					<c:forEach begin="1" end="11">
						<col />
					</c:forEach>
					<col style="width: 90px;" />
					<col style="width: 90px;" />
				</colgroup>
				<thead>
					<tr>
						<th>控除項目</th>
						<c:choose>
							<c:when
								test="${not empty statData and not empty statData.deductList}">
								<c:forEach var="item" items="${statData.deductList}">
									<th>${item.itemName}</th>
								</c:forEach>
								<c:if test="${statData.deductList.size() < 11}">
									<c:forEach begin="1" end="${11 - statData.deductList.size()}">
										<th></th>
									</c:forEach>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:forEach begin="1" end="11">
									<th></th>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						<th class="highlight-bg">合計</th>
						<th class="net-pay-header">手取り額</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>└ 金額 (円)</th>
						<c:choose>
							<c:when
								test="${not empty statData and not empty statData.deductList}">
								<c:forEach var="item" items="${statData.deductList}">
									<td><fmt:formatNumber value="${item.amount}"
											pattern="#,##0" /></td>
								</c:forEach>
								<c:if test="${statData.deductList.size() < 11}">
									<c:forEach begin="1" end="${11 - statData.deductList.size()}">
										<td></td>
									</c:forEach>
								</c:if>
								<td class="highlight-bg"><b><fmt:formatNumber
											value="${statData.totalDeduct}" pattern="#,##0" /></b></td>
								<td class="net-pay-cell"><fmt:formatNumber
										value="${statData.netPay}" pattern="#,##0" /></td>
							</c:when>
							<c:otherwise>
								<c:forEach begin="1" end="11">
									<td></td>
								</c:forEach>
								<td class="highlight-bg">0</td>
								<td class="net-pay-cell">0</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr class="sub-row">
						<th>└ 構成比率</th>
						<c:choose>
							<c:when
								test="${not empty statData and not empty statData.deductList}">
								<c:forEach var="item" items="${statData.deductList}">
									<td>${item.rate}%</td>
								</c:forEach>
								<c:if test="${statData.deductList.size() < 11}">
									<c:forEach begin="1" end="${11 - statData.deductList.size()}">
										<td></td>
									</c:forEach>
								</c:if>
								<td class="highlight-bg"><b>100%</b></td>
								<td class="net-pay-cell"></td>
							</c:when>
							<c:otherwise>
								<c:forEach begin="1" end="11">
									<td></td>
								</c:forEach>
								<td class="highlight-bg">0%</td>
								<td class="net-pay-cell"></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="notice-text">※ 統計チャートは別途の印刷画面を提供しておりません。Windowsで提供するスクリーンショット(ScreenShot)機能を利用して印刷(Ctrl + P)を行ってください。</div>
	</div>

	<!-- 사원 선택 모달 / 社員選択モーダル -->
	<div id="workerSelectModal" class="modal-overlay"
		style="display: none;">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title">全社員選択</h3>
			</div>
			<div class="modal-body">
				<div class="modal-filter-bar">
					<div class="filter-left">
						<div class="search-input-group">
							<input type="text" id="modalSearchKeyword" placeholder="社員検索"
								onkeyup="filterModalWorkers()" />
							<button type="button" class="btn-search-icon"
								onclick="filterModalWorkers()">🔍</button>
						</div>
						<button type="button" class="btn-reset-filter"
							onclick="resetModalFilter()">全体表示</button>
					</div>
					<div class="filter-right">
						<select id="filterStatus" onchange="filterModalWorkers()">
							<option value="">ステータス別</option>
							<option value="재직">在職</option>
							<option value="퇴직">退職</option>
						</select>
					</div>
				</div>
				<div class="modal-table-wrapper">
					<table class="erp-modal-table">
						<thead>
							<tr>
								<th style="width: 70px;">区分</th>
								<th style="width: 110px;">社員番号</th>
								<th style="width: 90px;">氏名</th>
								<th style="width: 100px;">部署</th>
								<th style="width: 80px;">役職</th>
								<th style="width: 70px;">ステータス</th>
							</tr>
						</thead>
						<tbody id="modalWorkerListBody"></tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn-erp-gray"
						onclick="closeWorkerModal()">閉じる</button>
				</div>
			</div>
		</div>
	</div>

	<layout:footer />

	<script>
Chart.register(ChartDataLabels);

// 도넛 중앙 텍스트 플러그인 / ドーナツ中央テキストプラグイン
const centerTextPlugin = {
	id: 'centerText',
	beforeDraw(chart) {
		if (!chart.config.options.plugins.centerText) return;
		const { ctx, chartArea: { top, bottom, left, right } } = chart;
		const textLines = chart.config.options.plugins.centerText.text || [];
		ctx.save();
		ctx.font = 'bold 13px "Pretendard", sans-serif';
		ctx.fillStyle = '#333';
		ctx.textAlign = 'center';
		ctx.textBaseline = 'middle';

		const centerX = (left + right) / 2;
		const centerY = (top + bottom) / 2;

		if (textLines.length === 1) {
			ctx.fillText(textLines[0], centerX, centerY);
		} else if (textLines.length === 2) {
			ctx.fillText(textLines[0], centerX, centerY - 8);
			ctx.fillText(textLines[1], centerX, centerY + 10);
		} else if (textLines.length === 3) {
			ctx.fillText(textLines[0], centerX, centerY - 16);
			ctx.fillText(textLines[1], centerX, centerY);
			ctx.fillText(textLines[2], centerX, centerY + 16);
		}
		ctx.restore();
	}
};

document.addEventListener('DOMContentLoaded', function() {
	renderAllCharts();
});

function renderAllCharts() {
	// 데이터 존재 여부 동적 체크 / 데이터 존재 여부 동적 체크
	const hasPayData = <c:out value="${not empty statData and not empty statData.payList}" default="false"/>;
	const hasDeductData = <c:out value="${not empty statData and not empty statData.deductList}" default="false"/>;
	const hasTotalData = <c:out value="${not empty statData and (statData.totalPay > 0 or statData.totalDeduct > 0)}" default="false"/>;

	// 1. 전체 비율 차트 (데이터 없을 시 50%:50% 표시) / 1. 全体比率チャート (データがない場合は50%:50%表示)
	const totalPay = hasTotalData ? ${empty statData.totalPay ? 0 : statData.totalPay} : 50;
	const totalDeduct = hasTotalData ? ${empty statData.totalDeduct ? 0 : statData.totalDeduct} : 50;

	new Chart(document.getElementById('chartTotal').getContext('2d'), {
		type: 'doughnut',
		data: {
			labels: ['支給項目', '控除項目'],
			datasets: [{
				data: [totalPay, totalDeduct],
				backgroundColor: ['#0077cc', '#f37021']
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				centerText: { text: ['支給項目', '+', '控除項目'] },
				legend: { position: 'bottom', labels: { boxWidth: 12, padding: 15 } },
				datalabels: {
					color: '#fff',
					font: { weight: 'bold', size: 10 },
					formatter: (val, ctx) => {
						if (!hasTotalData) return '50.0%'; // 기본 50:50 표시 / 基本50:50表示
						let sum = ctx.dataset.data.reduce((a, b) => a + b, 0);
						return sum > 0 ? (val / sum * 100).toFixed(1) + '%' : '';
					}
				}
			}
		},
		plugins: [centerTextPlugin]
	});

	// 2. 지급 세부항목 차트 (데이터 없을 시 100% 단일 파란링) / 2. 支給詳細項目チャート (データがない場合は100%単一ブルーリング)
	let payLabels = ['保存された給与がありません。'];
	let payData = [1];
	let payColors = ['#0077cc'];

	if (hasPayData) {
		payLabels = [<c:forEach items="${statData.payList}" var="item" varStatus="st">'${item.itemName}'${!st.last ? ',' : ''}</c:forEach>];
		payData = [<c:forEach items="${statData.payList}" var="item" varStatus="st">${item.amount}${!st.last ? ',' : ''}</c:forEach>];
		payColors = ['#004488', '#0077cc', '#00aabb', '#33bbcc', '#66ccdd', '#88ddee'];
	}

	new Chart(document.getElementById('chartPay').getContext('2d'), {
		type: 'doughnut',
		data: {
			labels: payLabels,
			datasets: [{
				data: payData,
				backgroundColor: payColors
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				centerText: { text: ['支給', '詳細項目'] },
				legend: { position: 'bottom', labels: { boxWidth: 10, padding: 8, font: { size: 11 } } },
				datalabels: {
					color: '#fff',
					font: { weight: 'bold', size: 10 },
					formatter: (val, ctx) => {
						if (!hasPayData) return '100%'; // 데이터 없을 때 100% 표기 / データがないとき100%表記
						let sum = ctx.dataset.data.reduce((a, b) => a + b, 0);
						let pct = sum > 0 ? (val / sum * 100).toFixed(1) : 0;
						return pct >= 3 ? pct + '%' : '';
					}
				}
			}
		},
		plugins: [centerTextPlugin]
	});

	// 3. 공제 세부항목 차트 (데이터 없을 시 100% 단일 주황링) / 3. 控除詳細項目チャート (データがない場合は100%単一オレンジリング)
	let deductLabels = ['保存された給与가ありません。'];
	let deductData = [1];
	let deductColors = ['#f37021'];

	if (hasDeductData) {
		deductLabels = [<c:forEach items="${statData.deductList}" var="item" varStatus="st">'${item.itemName}'${!st.last ? ',' : ''}</c:forEach>];
		deductData = [<c:forEach items="${statData.deductList}" var="item" varStatus="st">${item.amount}${!st.last ? ',' : ''}</c:forEach>];
		deductColors = ['#d9534f', '#f0ad4e', '#5cb85c', '#5bc0de', '#0275d8'];
	}

	new Chart(document.getElementById('chartDeduct').getContext('2d'), {
		type: 'doughnut',
		data: {
			labels: deductLabels,
			datasets: [{
				data: deductData,
				backgroundColor: deductColors
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				centerText: { text: ['控除', '詳細項目'] },
				legend: { position: 'bottom', labels: { boxWidth: 10, padding: 8, font: { size: 11 } } },
				datalabels: {
					color: '#fff',
					font: { weight: 'bold', size: 10 },
					formatter: (val, ctx) => {
						if (!hasDeductData) return '100%'; // 데이터 없을 때 100% 표기 / 데이터がないとき100%表記
						let sum = ctx.dataset.data.reduce((a, b) => a + b, 0);
						let pct = sum > 0 ? (val / sum * 100).toFixed(1) : 0;
						return pct >= 3 ? pct + '%' : '';
					}
				}
			}
		},
		plugins: [centerTextPlugin]
	});
}

// 모달 및 비동기 검색 스크립트 / モーダルおよび非同期検索スクリプト
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

function fetchWorkerMasterList() {
	const tbody = document.getElementById('modalWorkerListBody');
	tbody.innerHTML = '<tr><td colspan="6" style="padding:20px; text-align:center;">一覧を読み込んでいます...</td></tr>';

	fetch('${pageContext.request.contextPath}/emp/getAllList.do')
		.then(res => {
			if (!res.ok) throw new Error('応答失敗');
			return res.text();
		})
		.then(html => { tbody.innerHTML = html; })
		.catch(err => {
			console.error(err);
			tbody.innerHTML = '<tr><td colspan="6" style="color:red; padding:20px; text-align:center;">社員一覧を読み込めませんでした。</td></tr>';
		});
}

function selectModalEmp(empId, empName) {
	document.getElementById('selectedEmpId').value = empId;
	document.getElementById('targetEmpName').value = empName;
	closeWorkerModal();
}

function submitSearch() {
	const empId = document.getElementById('selectedEmpId').value;
	if (!empId) {
		alert('対象社員を選択してください。');
		openWorkerModal();
		return;
	}
	document.getElementById('searchForm').submit();
}

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