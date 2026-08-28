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
<title>ヘキサゾーン - 月別 全体給与統計</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
.stat-container {
	width: 1000px;
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
}

.filter-box select {
	padding: 3px 8px;
	font-size: 13px;
	border: 1px solid #ccc;
	outline: none;
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
	color: #333;
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

.notice-text {
	font-size: 11px;
	color: #888;
	margin-top: 15px;
}
</style>
</head>
<body>
	<!-- 헤더 Include / ヘッダーInclude -->
	<layout:headerNav />

	<div class="stat-container">
		<div class="stat-title">
			<span>📊</span> 月別 全体給与統計
		</div>
		<div class="stat-subtitle">帰属年度を選択すると、月別全体給与状況を確認できます。
		</div>

		<!-- 귀속년도 선택 필터 / 帰属年度選択フィルター -->
		<div class="filter-box">
			* 帰属年度を選択してください。 &nbsp; <select id="selectYear"
				onchange="changeYear(this.value)">
				<c:forEach var="y" begin="2015" end="${nowYear}">
					<option value="${y}" ${y == selectedYear ? 'selected' : ''}>${y}
						年</option>
				</c:forEach>
			</select>
		</div>

		<!-- 차트 영역 / チャートエリア -->
		<div class="chart-wrapper">
			<canvas id="salaryChart"></canvas>
		</div>

		<!-- 통계 데이터 테이블 / 統計データテーブル -->
		<table class="stat-table">
			<thead>
				<tr id="tableHeader">
					<th style="width: 120px;">区分</th>
					<c:forEach var="m" begin="1" end="12">
						<th>${m}月</th>
					</c:forEach>
					<th style="background-color: #e8f0fe;">合計</th>
				</tr>
			</thead>
			<tbody>
				<tr id="rowSalary">
					<th>全体給与額 (千円)</th>
					<c:forEach var="item" items="${statList}">
						<td><fmt:formatNumber value="${item.totalSalaryThousand}"
								pattern="#,##0" /></td>
					</c:forEach>
					<td style="font-weight: bold; background-color: #f9fbfd;"><fmt:formatNumber
							value="${totalSalarySum}" pattern="#,##0" /></td>
				</tr>
				<tr id="rowSalaryGrowth" class="sub-row">
					<th>└ 増加率</th>
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
					<td style="background-color: #fafafa;">-</td>
				</tr>
				<tr id="rowEmpCount">
					<th>人員 (名)</th>
					<c:forEach var="item" items="${statList}">
						<td><fmt:formatNumber value="${item.empCount}"
								pattern="#,##0.0" /></td>
					</c:forEach>
					<td style="font-weight: bold; background-color: #f9fbfd;"><fmt:formatNumber
							value="${avgEmpCount}" pattern="#,##0.0" /></td>
				</tr>
				<tr id="rowEmpGrowth" class="sub-row">
					<th>└ 増加率</th>
					<c:forEach var="item" items="${statList}">
						<td><c:choose>
								<c:when test="${item.countGrowthRate > 0}">
									<span class="rate-plus">${item.countGrowthRate}%</span>
								</c:when>
								<c:when test="${item.countGrowthRate < 0}">
									<span class="rate-minus">${item.countGrowthRate}%</span>
								</c:when>
								<c:when test="${item.countGrowthRate == 0}">
									<span class="rate-zero">0.0%</span>
								</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose></td>
					</c:forEach>
					<td style="background-color: #fafafa;">-</td>
				</tr>
			</tbody>
		</table>

		<div class="notice-text">※ 統計チャートは別途の印刷画面を提供しておりません。Windowsで提供するスクリーンショット(ScreenShot)機能を利用して印刷(Ctrl + P)を行ってください。</div>
	</div>

	<!-- 푸터 Include / フッターInclude -->
	<layout:footer />

	<script>
// 연도 변경 시 페이지 이동 (GET 요청) / 年度変更時にページ移動 (GETリクエスト)
function changeYear(year) {
	location.href = '${pageContext.request.contextPath}/salary/stats/monthlyTotal.do?year=' + year;
}

// 화면 로드 완료 시 statList 변환 및 바인딩 / 画面読み込み完了時にstatListの変換およびバインディング
document.addEventListener('DOMContentLoaded', function() {
	const dataList = [
		<c:forEach var="item" items="${statList}" varStatus="status">
		{
			month: ${item.month},
			totalSalaryThousand: ${item.totalSalaryThousand},
			empCount: ${item.empCount}
		}${!status.last ? ',' : ''}
		</c:forEach>
	];

	renderChart(dataList);
});

// 차트 바인딩 (Chart.js) / チャートバインディング (Chart.js)
let myChart = null;
function renderChart(data) {
	const labels = data.map(item => item.month + '月');
	const salaryData = data.map(item => item.totalSalaryThousand);
	const countData = data.map(item => item.empCount);

	const ctx = document.getElementById('salaryChart').getContext('2d');

	if (myChart) {
		myChart.destroy();
	}

	myChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: labels,
			datasets: [
				{
					label: '全体給与額 (千円)',
					data: salaryData,
					backgroundColor: '#337ab7',
					yAxisID: 'ySalary',
					order: 2
				},
				{
					label: '人員 (名)',
					data: countData,
					type: 'line',
					borderColor: '#ff7043',
					backgroundColor: '#ff7043',
					borderWidth: 2,
					fill: false,
					yAxisID: 'yCount',
					order: 1
				}
			]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			scales: {
				ySalary: {
					type: 'linear',
					position: 'left',
					title: { display: true, text: '全体給与額 (千円)' }
				},
				yCount: {
					type: 'linear',
					position: 'right',
					title: { display: true, text: '人員 (名)' },
					grid: { drawOnChartArea: false }
				}
			}
		}
	});
}
</script>

</body>
</html>