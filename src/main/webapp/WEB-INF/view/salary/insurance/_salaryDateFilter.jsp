<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />
<fmt:formatDate var="nowMonth" value="${now}" pattern="M" />

<!-- 기본값: 지난달 (1월인 경우 전년도 12월) -->
<c:set var="defaultYear"
	value="${nowMonth == 1 ? nowYear - 1 : nowYear}" />
<c:set var="defaultMonth" value="${nowMonth == 1 ? 12 : nowMonth - 1}" />

<style>
/* 1. 외곽 흰색 카드 박스 */
.filter-card {
	background-color: #ffffff;
	border: 1px solid #e2e8f0;
	border-radius: 12px;
	padding: 16px 20px;
	margin: 15px 0 20px 0;
	display: flex;
	align-items: center;
	justify-content: space-between;
	flex-wrap: wrap;
	gap: 16px;
	box-shadow: 0 1px 4px rgba(0, 0, 0, 0.03);
}

/* 2. 좌측 영역 (남색 박스 + 바깥 조회 버튼) */
.filter-left-group {
	display: flex;
	align-items: center;
	gap: 10px;
}

/* 귀속연월 남색 박스 */
.filter-blue-box {
	background-color: #213b82;
	border-radius: 8px;
	padding: 6px 14px;
	display: flex;
	align-items: center;
	gap: 10px;
}

.filter-blue-box .filter-label {
	color: #ffffff;
	font-weight: bold;
	font-size: 13px;
	white-space: nowrap;
}

.filter-blue-box .filter-select {
	background-color: #ffffff;
	border: 1px solid #ffffff;
	border-radius: 6px;
	padding: 3px 8px;
	font-size: 12px;
	font-weight: bold;
	color: #1e293b;
	height: 30px;
	outline: none;
	cursor: pointer;
}

/* 남색 박스 바깥의 축소된 공제내역조회 버튼 */
.btn-search {
	background-color: #0f172a;
	color: #ffffff;
	border: none;
	padding: 0 10px;
	height: 30px;
	font-size: 12px;
	font-weight: bold;
	border-radius: 6px;
	cursor: pointer;
	white-space: nowrap;
	transition: background-color 0.2s;
}

.btn-search:hover {
	background-color: #334155;
}

/* 3. 우측 정산기간 / 지급일 영역 */
.filter-right-group {
	display: flex;
	align-items: center;
	gap: 16px;
	flex-wrap: wrap;
}

.date-input-group {
	display: flex;
	align-items: center;
	gap: 8px;
}

.filter-right-group .filter-label {
	font-weight: bold;
	font-size: 13px;
	color: #334155;
	white-space: nowrap;
}

.input-date {
	padding: 3px 10px;
	font-size: 12px;
	border: 1px solid #cbd5e1;
	border-radius: 6px;
	background-color: #ffffff;
	text-align: center;
	outline: none;
	height: 30px;
	color: #1e293b;
	box-sizing: border-box;
}
</style>

<div class="filter-card">
	<input type="hidden" id="salaryDateId" name="salaryDateId" value="" />

	<!-- 좌측 그룹 (남색 박스 + 바깥 공제내역조회 버튼) -->
	<div class="filter-left-group">
		<div class="filter-blue-box">
			<span class="filter-label">* 귀속연월</span> <select name="payYear"
				id="payYear" class="filter-select">
				<option value="">선택</option>
				<c:forEach var="y" begin="2020" end="${nowYear + 1}">
					<option value="${y}" ${y == defaultYear ? 'selected' : ''}>${y}
						년</option>
				</c:forEach>
			</select> <select name="payMonth" id="payMonth" class="filter-select">
				<option value="">선택</option>
				<c:forEach var="m" begin="1" end="12">
					<fmt:formatNumber var="formattedMonth" value="${m}" pattern="00" />
					<option value="${m}" ${m == defaultMonth ? 'selected' : ''}>${formattedMonth}
						월</option>
				</c:forEach>
			</select> <span class="filter-label" style="margin-left: 4px;">* 급여차수</span> <select
				name="paySeq" id="paySeq" class="filter-select">
				<option value="">차수 선택</option>
				<c:forEach var="d" begin="1" end="10">
					<fmt:formatNumber var="formattedDegree" value="${d}" pattern="00" />
					<option value="${d}" ${d == 1 ? 'selected' : ''}>급여-${formattedDegree}차</option>
				</c:forEach>
			</select>
		</div>

		<!-- 남색 박스 바깥에 위치한 공제내역조회 버튼 -->
		<button type="button" class="btn-search" onclick="searchInsurance()">공제내역조회</button>
	</div>

	<!-- 우측 날짜 영역 -->
	<div class="filter-right-group">
		<div class="date-input-group">
			<label class="filter-label">* 정산기간</label> <input type="date"
				id="startDate" class="input-date" readonly /> <span>~</span> <input
				type="date" id="endDate" class="input-date" readonly />
		</div>

		<div class="date-input-group">
			<label class="filter-label">* 급여지급일</label> <input type="date"
				id="payDate" class="input-date" readonly />
		</div>
	</div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function() {
	searchInsurance();
});

function searchInsurance() {
	const year = document.getElementById('payYear').value;
	const month = document.getElementById('payMonth').value;
	const degree = document.getElementById('paySeq').value;

	if (!year || !month || !degree) {
		alert("귀속연도, 귀속월, 급여차수를 모두 선택해 주세요.");
		clearFields();
		if (typeof loadSocialInsuranceList === 'function') {
			loadSocialInsuranceList('');
		}
		return;
	}

	fetch('${pageContext.request.contextPath}/salary/salaryDate.do?payYear=' + year + '&payMonth=' + month + '&paySeq=' + degree)
		.then(response => response.json())
		.then(data => {
			let currentSalaryDateId = '';

			if (data.status === 'found') {
				currentSalaryDateId = data.salaryDateId || '';
				document.getElementById('salaryDateId').value = currentSalaryDateId;
				document.getElementById('startDate').value = data.startDate || '';
				document.getElementById('endDate').value = data.endDate || '';
				document.getElementById('payDate').value = data.payDate || '';
			} else {
				clearFields();
			}

			if (typeof loadSocialInsuranceList === 'function') {
				loadSocialInsuranceList(currentSalaryDateId);
			}
		})
		.catch(error => {
			console.error('SalaryDate fetch error:', error);
			clearFields();
			if (typeof loadSocialInsuranceList === 'function') {
				loadSocialInsuranceList('');
			}
		});
}

function clearFields() {
	document.getElementById('salaryDateId').value = '';
	document.getElementById('startDate').value = '';
	document.getElementById('endDate').value = '';
	document.getElementById('payDate').value = '';
}
</script>