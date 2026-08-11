<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />
<fmt:formatDate var="nowMonth" value="${now}" pattern="M" />

<c:set var="defaultYear"
	value="${nowMonth == 1 ? nowYear - 1 : nowYear}" />
<c:set var="defaultMonth" value="${nowMonth == 1 ? 12 : nowMonth - 1}" />

<div class="filter-card">
	<input type="hidden" id="salaryDateId" name="salaryDateId" value="" />
	<div class="filter-left-group">
		<span class="filter-label">* 귀속연월</span>
		<!-- 귀속연도 드롭다운 -->
		<select name="payYear" id="payYear" class="filter-select"
			onchange="loadOrSetSalaryDate()">
			<option value="">선택</option>
			<c:forEach var="y" begin="2020" end="${nowYear + 1}">
				<option value="${y}" ${y == defaultYear ? 'selected' : ''}>${y}
					년</option>
			</c:forEach>
		</select>

		<!-- 귀속월 드롭다운 -->
		<select name="payMonth" id="payMonth" class="filter-select"
			onchange="loadOrSetSalaryDate()">
			<option value="">선택</option>
			<c:forEach var="m" begin="1" end="12">
				<fmt:formatNumber var="formattedMonth" value="${m}" pattern="00" />
				<option value="${m}" ${m == defaultMonth ? 'selected' : ''}>${formattedMonth}
					월</option>
			</c:forEach>
		</select> <span class="filter-label" style="margin-left: 10px;">* 급여차수</span>
		<!-- 급여차수 드롭다운 -->
		<select name="paySeq" id="paySeq" class="filter-select"
			onchange="loadOrSetSalaryDate()">
			<option value="">차수 선택</option>
			<c:forEach var="d" begin="1" end="10">
				<fmt:formatNumber var="formattedDegree" value="${d}" pattern="00" />
				<option value="${d}" ${d == 1 ? 'selected' : ''}>급여-${formattedDegree}차</option>
			</c:forEach>
		</select>
	</div>

	<div class="filter-right-group">
		<div class="date-input-group">
			<label>* 정산기간</label> <input type="date" name="startDate"
				id="startDate" class="input-text" /> <span>~</span> <input
				type="date" name="endDate" id="endDate" class="input-text" />
		</div>

		<div class="date-input-group">
			<label>* 급여지급일</label> <input type="date" name="payDate" id="payDate"
				class="input-text" />
			<button type="button" class="btn btn-secondary"
				onclick="saveSalaryDate()">수정</button>
		</div>
	</div>
</div>

<script>
//페이지 초기 로딩 시 기본 설정값으로 날짜 자동 세팅
document.addEventListener("DOMContentLoaded", function() {
	loadOrSetSalaryDate();
});

// 드롭다운 변경 시 실행되는 메인 로직
function loadOrSetSalaryDate() {
	const year = document.getElementById('payYear').value;
	const month = document.getElementById('payMonth').value;
	const degree = document.getElementById('paySeq').value;

	// 세 항목 중 하나라도 미선택 시 실행 안 함
	if (!year || !month || !degree) return;

	// 1. DB 조회 AJAX 호출
	fetch('${pageContext.request.contextPath}/salary/salaryDate.do?payYear=' + year + '&payMonth=' + month + '&paySeq=' + degree)
		.then(response => response.json())
		.then(data => {
			if (data.status === 'found') {
				// DB에 데이터가 존재하는 경우 : DB값 적용
				document.getElementById('salaryDateId').value = data.salaryDateId || '';
				document.getElementById('startDate').value = data.startDate;
				document.getElementById('endDate').value = data.endDate;
				document.getElementById('payDate').value = data.payDate;
			} else if (data.status === 'notFound') {
				// DB에 데이터가 없는 경우 : 1일~말일, 익월 5일 자동 계산 적용
				document.getElementById('salaryDateId').value = '';
				autoCalculateDates(parseInt(year, 10), parseInt(month, 10));
			}
		})
		.catch(error => {
			console.error('Date fetch error:', error);
			// 통신 에러 발생 시에도 계산 로직으로 셋팅
			document.getElementById('salaryDateId').value = '';
			autoCalculateDates(parseInt(year, 10), parseInt(month, 10));
		});
}

// 자동 날짜 계산 함수 (1일 ~ 말일 / 익월 5일)
function autoCalculateDates(year, month) {
	const monthStr = String(month).padStart(2, '0');
	
	// 1. 시작일: 해당 년월의 01일
	const startDateStr = year + '-' + monthStr + '-01';

	// 2. 종료일: 해당 년월의 마지막 날 (Date 객체의 month는 0-indexed이므로 new Date(year, month, 0) 이용)
	const lastDay = new Date(year, month, 0).getDate();
	const endDateStr = year + '-' + monthStr + '-' + String(lastDay).padStart(2, '0');

	// 3. 지급일: 익월(다음 달) 5일 계산
	let nextYear = year;
	let nextMonth = month + 1;
	if (nextMonth > 12) {
		nextMonth = 1;
		nextYear++;
	}
	const nextMonthStr = String(nextMonth).padStart(2, '0');
	const payDateStr = nextYear + '-' + nextMonthStr + '-05';

	// input 필드에 바인딩
	document.getElementById('startDate').value = startDateStr;
	document.getElementById('endDate').value = endDateStr;
	document.getElementById('payDate').value = payDateStr;
}

// 정산기간 및 급여지급일 비동기 저장 함수
function saveSalaryDate() {
	const payYear = document.getElementById('payYear').value;
	const payMonth = document.getElementById('payMonth').value;
	const paySeq = document.getElementById('paySeq').value;
	const startDate = document.getElementById('startDate').value;
	const endDate = document.getElementById('endDate').value;
	const payDate = document.getElementById('payDate').value;

	if (!payYear || !payMonth || !paySeq) {
		alert("귀속년월과 급여차수를 먼저 선택해 주세요.");
		return;
	}

	if (!startDate || !endDate || !payDate) {
		alert("정산기간과 급여지급일을 모두 입력해 주세요.");
		return;
	}

	// 서버로 전달할 FormData 생성
	const params = new URLSearchParams();
	params.append('payYear', payYear);
	params.append('payMonth', payMonth);
	params.append('paySeq', paySeq);
	params.append('startDate', startDate);
	params.append('endDate', endDate);
	params.append('payDate', payDate);

	// AJAX 요청
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
	})
	.catch(error => {
		console.error('Error:', error);
		alert("정산일자 저장 중 오류가 발생했습니다.");
	});
}
</script>