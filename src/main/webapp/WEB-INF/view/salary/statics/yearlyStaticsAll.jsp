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
<title>年度別 全体給与統計</title>
<!-- Chart.js CDN -->
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
} /* 양수: 빨간색 / 正の数: 赤色 */
.rate-minus {
	color: #337ab7;
	font-weight: bold;
} /* 음수: 파란색 / 負의数: 青色 */
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
			<span>📊</span> 年度別 全体給与統計
		</div>
		<div class="stat-subtitle">帰属年度を選択すると、最近10年間の給与額と人員状況を確認できます。</div>

		<!-- 1. 귀속년도 선택 필터 / 1. 帰属年度選択フィルター -->
		<div class="filter-box">
			* 帰속年度を選択してください。 &nbsp; <select id="selectYear"
				onchange="changeYear(this.value)">
				<c:forEach var="y" begin="2015" end="${nowYear}">
					<option value="${y}" ${y == selectedYear ? 'selected' : ''}>${y}
						年</option>
				</c:forEach>
			</select>
		</div>

		<!-- 2. 차트 영역 / 2. チャートエリア -->
		<div class="chart-wrapper">
			<canvas id="salaryChart"></canvas>
		</div>

		<!-- 3. 통계 데이터 테이블 / 3. 統計データテーブル -->
		<table class="stat-table">
			<thead>
				<tr id="tableHeader">
					<th style="width: 140px;">区分</th>
					<!-- JS 동적 생성 / JS動的生成 -->
				</tr>
			</thead>
			<tbody>
				<tr id="rowSalary">
					<th>全体給与額 (千円)</th>
					<!-- JS 동적 생성 / JS動的生成 -->
				</tr>
				<tr id="rowSalaryGrowth" class="sub-row">
					<th>└ 増加率</th>
					<!-- JS 동적 생성 / JS動的生成 -->
				</tr>
				<tr id="rowEmpCount">
					<th>人員 (名)</th>
					<!-- JS 동적 생성 / JS動적生成 -->
				</tr>
				<tr id="rowEmpGrowth" class="sub-row">
					<th>└ 増加率</th>
					<!-- JS 동적 생성 / JS動的生成 -->
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
	location.href = '${pageContext.request.contextPath}/salary/stats/yearlyTotal.do?year=' + year;
}

// 화면 로드 완료 시 setAttribute로 전달받은 statList 변환 및 바인딩 / 画面読み込み完了時にsetAttributeで受け取ったstatListの変換およびバ인딩
document.addEventListener('DOMContentLoaded', function() {
	const dataList = [
		<c:forEach var="item" items="${statList}" varStatus="status">
		{
			year: ${item.year},
			totalSalaryThousand: ${item.totalSalaryThousand},
			avgEmpCount: ${item.avgEmpCount},
			salaryGrowthRate: ${item.salaryGrowthRate != null ? item.salaryGrowthRate : 'null'},
			countGrowthRate: ${item.countGrowthRate != null ? item.countGrowthRate : 'null'}
		}${!status.last ? ',' : ''}
		</c:forEach>
	];

	renderChart(dataList);
	renderTable(dataList);
});

// 1. 차트 바인딩 (Chart.js) / 1. チャートバインディング (Chart.js)
let myChart = null;
function renderChart(data) {
	const labels = data.map(item => item.year + '年');
	const salaryData = data.map(item => item.totalSalaryThousand);
	const countData = data.map(item => item.avgEmpCount);

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
					backgroundColor: '#9ccc65',
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

// 2. 테이블 데이터 동적 출력 / 2. テーブルデータ動的出力
function renderTable(data) {
	const headerRow = document.getElementById('tableHeader');
	const rowSalary = document.getElementById('rowSalary');
	const rowSalaryGrowth = document.getElementById('rowSalaryGrowth');
	const rowEmpCount = document.getElementById('rowEmpCount');
	const rowEmpGrowth = document.getElementById('rowEmpGrowth');

	data.forEach(item => {
		// 헤더 (연도) / ヘッダー (年度)
		headerRow.innerHTML += '<th>' + item.year + '年</th>';
		
		// 급여액 (천단위 콤마 포맷팅) / 給与額 (千単位カンマフォーマット)
		rowSalary.innerHTML += '<td>' + item.totalSalaryThousand.toLocaleString() + '</td>';
		
		// 급여 증가율 (양수: 빨강 / 음수: 파랑) / 給与増加率 (正: 赤 / 負: 青)
		rowSalaryGrowth.innerHTML += '<td>' + formatGrowthRate(item.salaryGrowthRate) + '</td>';
		
		// 인원 (소수점 1자리) / 人員 (小数点1桁)
		rowEmpCount.innerHTML += '<td>' + item.avgEmpCount.toFixed(1) + '</td>';
		
		// 인원 증가율 (양수: 빨강 / 음수: 파랑) / 人員増加率 (正: 赤 / 負: 青)
		rowEmpGrowth.innerHTML += '<td>' + formatGrowthRate(item.countGrowthRate) + '</td>';
	});
}

// 증가율 포맷 및 색상 처리 헬퍼 함수 / 増加率フォーマットおよびカラー処理ヘルパー関数
function formatGrowthRate(rate) {
	if (rate === null || rate === undefined) return '-';
	
	let className = 'rate-zero';
	if (rate > 0) {
		className = 'rate-plus';
	} else if (rate < 0) {
		className = 'rate-minus';
	}
	
	return '<span class="' + className + '">' + rate.toFixed(1) + '%</span>';
}
</script>

</body>
</html>