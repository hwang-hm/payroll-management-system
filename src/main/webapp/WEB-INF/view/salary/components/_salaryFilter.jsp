<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 날짜 기본값 계산 로직 -->
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />
<fmt:formatDate var="nowMonth" value="${now}" pattern="M" />

<c:set var="defaultYear" value="${nowMonth == 1 ? nowYear - 1 : nowYear}" />
<c:set var="defaultMonth" value="${nowMonth == 1 ? 12 : nowMonth - 1}" />

<div class="filter-card">
	<div class="filter-left-group">
		<!-- 귀속연도 드롭다운 -->
		<span class="filter-label">* 귀속연월</span> 
		<select name="belongYear" id="belongYear" class="filter-select" onchange="checkFilterSelect(this)">
			<option value="">선택</option>
			<c:forEach var="y" begin="2020" end="${nowYear + 1}">
				<option value="${y}" ${y == defaultYear ? 'selected' : ''}>${y} 년</option>
			</c:forEach>
		</select>

		<!-- 귀속월 드롭다운 -->
		<select name="belongMonth" id="belongMonth" class="filter-select" onchange="checkFilterSelect(this)">
			<option value="">선택</option>
			<c:forEach var="m" begin="1" end="12">
				<fmt:formatNumber var="formattedMonth" value="${m}" pattern="00" />
				<option value="${m}" ${m == defaultMonth ? 'selected' : ''}>${formattedMonth} 월</option>
			</c:forEach>
		</select>

		<!-- 급여차수 드롭다운 -->
		<span class="filter-label" style="margin-left: 10px;">* 급여차수</span>
		<select name="payDegree" id="payDegree" class="filter-select" onchange="checkFilterSelect(this)">
			<option value="">차수 선택</option>
			<c:forEach var="d" begin="1" end="10">
				<fmt:formatNumber var="formattedDegree" value="${d}" pattern="00" />
				<option value="${d}" ${d == 1 ? 'selected' : ''}>급여-${formattedDegree}차</option>
			</c:forEach>
		</select>
	</div>

	<div class="filter-right-group">
		<div class="date-input-group">
			<label>* 정산기간</label> 
			<input type="date" name="startDate" value="2026-08-01" class="input-text" /> 
			<span>~</span> 
			<input type="date" name="endDate" value="2026-08-31" class="input-text" />
		</div>

		<div class="date-input-group">
			<label>* 급여지급일</label> 
			<input type="date" name="payDate" value="2026-09-05" class="input-text" />
			<button type="button" class="btn btn-secondary">수정</button>
		</div>
	</div>
</div>