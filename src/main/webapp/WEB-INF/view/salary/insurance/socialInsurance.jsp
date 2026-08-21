<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>4대보험 공제내역</title>
<style>
body {
	font-family: 'Malgun Gothic', 'Noto Sans KR', sans-serif;
	font-size: 12px;
	color: #333;
	margin: 0;
	padding: 0;
	background-color: #f8fafc;
}

.page-container {
	padding: 25px 35px;
	max-width: 1650px;
	margin: 0 auto;
}

.page-header-title {
	font-size: 20px;
	font-weight: bold;
	color: #1e293b;
	display: flex;
	align-items: center;
	gap: 8px;
}

.page-header-desc {
	font-size: 12px;
	color: #64748b;
	margin-top: 6px;
	margin-bottom: 18px;
}

.table-wrapper {
	width: 100%;
	overflow-x: auto;
	background-color: #ffffff;
	border: 1px solid #cbd5e1;
	border-radius: 4px;
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.04);
	margin-top: 15px;
}

table {
	width: max-content;
	min-width: 100%;
	border-collapse: separate;
	border-spacing: 0;
	text-align: center;
}

th, td {
	border-bottom: 1px solid #e2e8f0;
	border-right: 1px solid #e2e8f0;
	padding: 7px 8px;
	white-space: nowrap;
	box-sizing: border-box;
	font-size: 12px;
}

th {
	background-color: #f1f5f9;
	font-weight: bold;
	color: #334155;
}

.text-right {
	text-align: right !important;
}

.font-bold {
	font-weight: bold !important;
}

.col-subtotal {
	background-color: #fafafa;
	font-weight: bold;
}

.col-grandtotal {
	font-weight: bold;
}

/* ====================================================
   [1~5번째 사원정보 열 좌측 고정 (Sticky)]
   ==================================================== */
thead tr:nth-child(1) th:first-child {
	position: sticky;
	left: 0px;
	z-index: 10;
	background-color: #e2e8f0;
	border-right: 2px solid #94a3b8 !important;
}

thead tr:nth-child(2) th:nth-child(1),
tbody tr td:nth-child(1) {
	position: sticky;
	left: 0px;
	width: 65px;
	min-width: 65px;
}

thead tr:nth-child(2) th:nth-child(2),
tbody tr td:nth-child(2) {
	position: sticky;
	left: 65px;
	width: 80px;
	min-width: 80px;
}

thead tr:nth-child(2) th:nth-child(3),
tbody tr td:nth-child(3) {
	position: sticky;
	left: 145px;
	width: 90px;
	min-width: 90px;
}

thead tr:nth-child(2) th:nth-child(4),
tbody tr td:nth-child(4) {
	position: sticky;
	left: 235px;
	width: 100px;
	min-width: 100px;
}

thead tr:nth-child(2) th:nth-child(5),
tbody tr td:nth-child(5) {
	position: sticky;
	left: 335px;
	width: 75px;
	min-width: 75px;
	border-right: 2px solid #94a3b8 !important;
}

thead tr:nth-child(2) th:nth-child(-n+5) {
	z-index: 9;
	background-color: #f1f5f9;
}

tbody tr td:nth-child(-n+5) {
	z-index: 5;
	background-color: #ffffff;
}

/* 6번째 열부터 데이터 열 너비 확보 */
thead tr:nth-child(2) th:nth-child(n+6),
tbody tr td:nth-child(n+6) {
	min-width: 90px;
}

/* 우측 총 합계 헤더 검정색 스타일 */
.th-total-header {
	background-color: #666666 !important; /* 회색계열 (#475569 또는 #666666 가능) */
	color: #ffffff !important;
}

/* 데이터 행 호버 효과 */
tbody tr:hover td {
	background-color: #f8fafc;
}

tbody tr:hover td:nth-child(-n+5) {
	background-color: #f8fafc !important;
}

/* ====================================================
   [하단 합계 행 전용 스타일]
   ==================================================== */
tbody tr.bg-total {
	background-color: #fffde7 !important;
	font-weight: bold;
}

tbody tr.bg-total td {
	background-color: #fffde7 !important;
	border-top: 2px solid #cbd5e1;
	border-bottom: 2px solid #cbd5e1;
	color: #1e293b;
}

tbody tr.bg-total td:nth-child(-n+5) {
	position: sticky;
	z-index: 6 !important;
	background-color: #fffde7 !important;
}

tbody tr.bg-total:hover td {
	background-color: #fffde7 !important;
}
</style>
</head>
<body>
	<layout:headerNav />

	<div class="page-container">
		<div class="page-header-title">
			<span>4대보험 공제내역</span>
		</div>
		<div class="page-header-desc">급여정보 중 4대보험에 대한 근로자 &amp; 사업주분에 대한 월별 내역 검색 및 보기 쉽게 정리하여 제공되는 메뉴입니다.</div>

		<jsp:include page="./_salaryDateFilter.jsp" />

		<div class="table-wrapper">
			<table>
				<thead>
					<tr>
						<th colspan="5">사원정보</th>
						<th colspan="3">국민연금</th>
						<th colspan="3">건강보험</th>
						<th colspan="3">노인장기요양보험</th>
						<th colspan="3">고용보험</th>
						<th colspan="3" class="th-total-header">총 합계</th>
					</tr>
					<tr>
						<th>구분</th>
						<th>성명</th>
						<th>입사일</th>
						<th>부서</th>
						<th>직위</th>

						<th>사업주</th>
						<th>근로자</th>
						<th>합계</th>

						<th>사업주</th>
						<th>근로자</th>
						<th>합계</th>

						<th>사업주</th>
						<th>근로자</th>
						<th>합계</th>

						<th>사업주</th>
						<th>근로자</th>
						<th>합계</th>

						<th class="th-total-header">사업주</th>
						<th class="th-total-header">근로자</th>
						<th class="th-total-header">합계</th>
					</tr>
				</thead>

				<tbody id="insuranceTableContent">
					<tr>
						<td colspan="20" style="padding: 25px;">데이터를 불러오는 중입니다...</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<layout:footer />

	<script>
		function loadSocialInsuranceList(salaryDateId) {
			const container = document.getElementById('insuranceTableContent');

			if (!salaryDateId) {
				container.innerHTML = '<tr><td colspan="20" style="padding: 25px; color: #888;">'
				                    + '해당 귀속년월/차수에 등록된 급여정산 정보가 없습니다.'
				                    + '</td></tr>';
				return;
			}

			fetch('${pageContext.request.contextPath}/salary/socialInsurance.do?salaryDateId=' + salaryDateId)
				.then(response => {
					if (!response.ok) throw new Error("Network response was not ok");
					return response.text();
				})
				.then(html => {
					container.innerHTML = html;
				})
				.catch(error => {
					console.error('Error loading social insurance list:', error);
					container.innerHTML = '<tr><td colspan="20" style="padding: 25px; color: red;">'
				                        + '데이터를 불러오는 중 오류가 발생했습니다.'
				                        + '</td></tr>';
				});
		}
	</script>
</body>
</html>