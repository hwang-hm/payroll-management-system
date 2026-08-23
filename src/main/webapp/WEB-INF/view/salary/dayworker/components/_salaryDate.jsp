<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- 현재 날짜 및 기본년월 계산 --%>
<%-- 現在の日付およびデフォルト年月計算 --%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />
<fmt:formatDate var="nowMonth" value="${now}" pattern="M" />

<c:set var="defaultYear"
	value="${nowMonth == 1 ? nowYear - 1 : nowYear}" />
<c:set var="defaultMonth" value="${nowMonth == 1 ? 12 : nowMonth - 1}" />

<div class="filter-card">
	<input type="hidden" id="salaryDateId" name="salaryDateId" value="" />
	<div class="filter-left-group">
		<span class="filter-label">* 帰属年月</span>
		<!-- 귀속연도 드롭다운 / 帰属年ドロップダウン -->
		<select name="payYear" id="payYear" class="filter-select"
			onchange="loadOrSetSalaryDate()">
			<option value="">選択</option>
			<c:forEach var="y" begin="2020" end="${nowYear + 1}">
				<option value="${y}" ${y == defaultYear ? 'selected' : ''}>${y}
					年</option>
			</c:forEach>
		</select>

		<!-- 귀속월 드롭다운 / 帰属月ドロップダウン -->
		<select name="payMonth" id="payMonth" class="filter-select"
			onchange="loadOrSetSalaryDate()">
			<option value="">選択</option>
			<c:forEach var="m" begin="1" end="12">
				<fmt:formatNumber var="formattedMonth" value="${m}" pattern="00" />
				<option value="${m}" ${m == defaultMonth ? 'selected' : ''}>${formattedMonth}
					月</option>
			</c:forEach>
		</select> <span class="filter-label" style="margin-left: 10px;">* 給与回数</span>
		<!-- 급여차수 드롭다운 / 給与回数ドロップダウン -->
		<select name="paySeq" id="paySeq" class="filter-select"
			onchange="loadOrSetSalaryDate()">
			<option value="">回数選択</option>
			<c:forEach var="d" begin="1" end="10">
				<fmt:formatNumber var="formattedDegree" value="${d}" pattern="00" />
				<option value="${d}" ${d == 1 ? 'selected' : ''}>給与-${formattedDegree}回</option>
			</c:forEach>
		</select>
	</div>

	<div class="filter-right-group">
		<div class="date-input-group">
			<label>* 精算期間</label> <input type="date" name="startDate"
				id="startDate" class="input-text" /> <span>~</span> <input
				type="date" name="endDate" id="endDate" class="input-text" />
		</div>

		<div class="date-input-group">
			<label>* 給与支給日</label> <input type="date" name="payDate" id="payDate"
				class="input-text" />
			<button type="button" class="btn btn-secondary"
				onclick="saveSalaryDate()">修正</button>
		</div>
	</div>
</div>

<script>
// 페이지 초기 로딩 시 기본 설정값으로 날짜 자동 세팅
// ページ初期ロード時にデフォルト設定値で日付を自動セット
document.addEventListener("DOMContentLoaded", function() {
	loadOrSetSalaryDate();
});

// 드롭다운 변경 시 실행되는 메인 로직
// ドロップダウン変更時に実行されるメインロジック
function loadOrSetSalaryDate() {
	const year = document.getElementById('payYear').value;
	const month = document.getElementById('payMonth').value;
	const degree = document.getElementById('paySeq').value;

	// 세 항목 중 하나라도 미선택 시 실행 안 함
	// 3つの項目のうち1つでも未選択の場合は実行しない
	if (!year || !month || !degree) return;

	// DB 조회 AJAX 호출
	// DB照会 AJAX呼び出し
	fetch('${pageContext.request.contextPath}/salary/salaryDate.do?payYear=' + year + '&payMonth=' + month + '&paySeq=' + degree)
		.then(response => response.json())
		.then(data => {
			let currentSalaryDateId = '';

			if (data.status === 'found') {
				// DB에 데이터가 존재하는 경우 : DB값 적용
				// DBにデータが存在する場合 : DB値を適用
				currentSalaryDateId = data.salaryDateId || '';
				document.getElementById('salaryDateId').value = currentSalaryDateId;
				document.getElementById('startDate').value = data.startDate;
				document.getElementById('endDate').value = data.endDate;
				document.getElementById('payDate').value = data.payDate;
			} else if (data.status === 'notFound') {
				// DB에 데이터가 없는 경우 : 1일~말일, 익월 5일 자동 계산 적용
				// DBにデータがない場合 : 1日〜末日、翌月5日の自動計算を適用
				document.getElementById('salaryDateId').value = '';
				autoCalculateDates(parseInt(year, 10), parseInt(month, 10));
			}

			// ★ [수정 핵심] DB 조회가 끝난 뒤, 구해온 salaryDateId로 목록 조회 함수 호출
			// ★ [修正のポイント] DB照会が終了した後、取得した salaryDateId でリスト照会関数を呼び出し
			if (typeof loadSalaryWorkerList === 'function') {
				loadSalaryWorkerList(currentSalaryDateId);
			}
		})
		.catch(error => {
			console.error('Date fetch error:', error);
			document.getElementById('salaryDateId').value = '';
			autoCalculateDates(parseInt(year, 10), parseInt(month, 10));

			// 예외 발생 시 빈 값 전달하여 목록 초기화
			// 例外発生時は空の値を渡してリストを初期化
			if (typeof loadSalaryWorkerList === 'function') {
				loadSalaryWorkerList('');
			}
		});
}

// 자동 날짜 계산 함수 (1일 ~ 말일 / 익월 5일)
// 日付自動計算関数 (1日 〜 末日 / 翌月5日)
function autoCalculateDates(year, month) {
	const monthStr = String(month).padStart(2, '0');
	
	// 1. 시작일: 해당 년월의 01일
	// 1. 開始日: 該当年月の01日
	const startDateStr = year + '-' + monthStr + '-01';

	// 2. 종료일: 해당 년월의 마지막 날
	// 2. 終了日: 該当年月の最終日
	const lastDay = new Date(year, month, 0).getDate();
	const endDateStr = year + '-' + monthStr + '-' + String(lastDay).padStart(2, '0');

	// 3. 지급일: 익월(다음 달) 5일 계산
	// 3. 支給日: 翌月5日の計算
	let nextYear = year;
	let nextMonth = month + 1;
	if (nextMonth > 12) {
		nextMonth = 1;
		nextYear++;
	}
	const nextMonthStr = String(nextMonth).padStart(2, '0');
	const payDateStr = nextYear + '-' + nextMonthStr + '-05';

	// input 필드에 바인딩
	// inputフィールドにバインディング
	document.getElementById('startDate').value = startDateStr;
	document.getElementById('endDate').value = endDateStr;
	document.getElementById('payDate').value = payDateStr;
}

// 정산기간 및 급여지급일 비동기 저장 함수
// 精算期間および給与支給日の非同期保存関数
function saveSalaryDate() {
	const payYear = document.getElementById('payYear').value;
	const payMonth = document.getElementById('payMonth').value;
	const paySeq = document.getElementById('paySeq').value;
	const startDate = document.getElementById('startDate').value;
	const endDate = document.getElementById('endDate').value;
	const payDate = document.getElementById('payDate').value;

	if (!payYear || !payMonth || !paySeq) {
		alert("帰属年月と給与回数を先に選択してください。");
		return;
	}

	if (!startDate || !endDate || !payDate) {
		alert("精算期間と給与支給日をすべて入力してください。");
		return;
	}

	const params = new URLSearchParams();
	params.append('payYear', payYear);
	params.append('payMonth', payMonth);
	params.append('paySeq', paySeq);
	params.append('startDate', startDate);
	params.append('endDate', endDate);
	params.append('payDate', payDate);

	const salaryDateId = document.getElementById('salaryDateId').value;
	if (salaryDateId) {
		params.append('salaryDateId', salaryDateId);
	}

	fetch('${pageContext.request.contextPath}/salary/salaryDate.do', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
		},
		body: params.toString()
	})
	.then(response => response.json())
	.then(data => {
		alert(data.message);
		// ★ 신규 생성/수정 완료 후 DB에서 발급된 salaryDateId를 다시 적용하기 위해 재호출
		// ★ 新規作成・更新完了後、DBから発行された salaryDateId を再適用するために再呼び出し
		loadOrSetSalaryDate();
	})
	.catch(error => {
		console.error('Error:', error);
		alert("精算日時の保存中にエラーが発生しました。");
	});
}
</script>