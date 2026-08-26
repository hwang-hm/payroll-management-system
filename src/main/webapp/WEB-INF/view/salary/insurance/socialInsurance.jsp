<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<!-- 4대보험 공제내역 / 4大保険控除明細 -->
<title>ヘキサゴンゾーン - 4大保険控除明細</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<style>
body {
	font-family: 'Malgun Gothic', 'Noto Sans KR', 'Meiryo', sans-serif;
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
   [1〜5番目の社員情報列の左側固定 (Sticky)]
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

/* 6번째 열부터 데이터 열 너비 확보 / 6番目の列からデータ列の幅を確保 */
thead tr:nth-child(2) th:nth-child(n+6),
tbody tr td:nth-child(n+6) {
	min-width: 90px;
}

/* 우측 총 합계 헤더 검정색 스타일 / 右側総合計ヘッダーのスタイル */
.th-total-header {
	background-color: #666666 !important;
	color: #ffffff !important;
}

/* 데이터 행 호버 효과 / データ行ホバー効果 */
tbody tr:hover td {
	background-color: #f8fafc;
}

tbody tr:hover td:nth-child(-n+5) {
	background-color: #f8fafc !important;
}

/* ====================================================
   [하단 합계 행 전용 스타일]
   [下部合計行専用スタイル]
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
	<!-- 상단 네비게이션 / 上部ナビゲーション -->
	<layout:headerNav />

	<div class="page-container">
		<!-- 페이지 헤더 타이틀 / ページヘッダータイトル -->
		<div class="page-header-title">
			<span>4大保険控除明細</span>
		</div>
		<!-- 페이지 설명 / ページ説明 -->
		<div class="page-header-desc">給与情報のうち、4大保険に関する労働者分および事業主分の月別明細を検索し、分かりやすく整理して提供するメニューです。</div>

		<!-- 급여 연월 필터 검색 영역 / 給与年月フィルター検索領域 -->
		<jsp:include page="./_salaryDateFilter.jsp" />

		<!-- 4대보험 공제 내역 테이블 / 4大保険控除明細テーブル -->
		<div class="table-wrapper">
			<table>
				<thead>
					<tr>
						<th colspan="5">社員情報</th>
						<th colspan="3">国民年金</th>
						<th colspan="3">健康保険</th>
						<th colspan="3">介護保険</th>
						<th colspan="3">雇用保険</th>
						<th colspan="3" class="th-total-header">総合計</th>
					</tr>
					<tr>
						<th>区分</th>
						<th>氏名</th>
						<th>入社日</th>
						<th>部署</th>
						<th>役職</th>

						<th>事業主</th>
						<th>労働者</th>
						<th>合計</th>

						<th>事業主</th>
						<th>労働者</th>
						<th>合計</th>

						<th>事業主</th>
						<th>労働者</th>
						<th>合計</th>

						<th>事業主</th>
						<th>労働者</th>
						<th>合計</th>

						<th class="th-total-header">事業主</th>
						<th class="th-total-header">労働者</th>
						<th class="th-total-header">合計</th>
					</tr>
				</thead>

				<tbody id="insuranceTableContent">
					<tr>
						<td colspan="20" style="padding: 25px;">データを読み込んでいます...</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<!-- 하단 푸터 / 下部フッター -->
	<layout:footer />

	<script>
		/**
		 * 급여 정산 ID 기준 4대보험 공제 내역 비동기 조회
		 * 給与計算ID基準の4大保険控除明細非同期照会
		 */
		function loadSocialInsuranceList(salaryDateId) {
			const container = document.getElementById('insuranceTableContent');

			// 급여 정산 ID가 없을 경우 안내 문구 표시
			// 給与計算IDがない場合、案内文を表示
			if (!salaryDateId) {
				container.innerHTML = '<tr><td colspan="20" style="padding: 25px; color: #888;">'
				                    + '該当する帰属年月/回数に登録された給与計算情報がありません。'
				                    + '</td></tr>';
				return;
			}

			// AJAX 요청을 통한 4대보험 내역 HTML 바인딩
			// AJAXリクエストによる4大保険明細HTMLバインディング
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
					                    + 'データの読み込み中にエラーが発生しました。'
					                    + '</td></tr>';
				});
		}
	</script>
</body>
</html>