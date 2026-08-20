<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 컨트롤러 전달 변수명 호환 처리 (insuranceList 또는 list 모두 대응) -->
<c:set var="targetList" value="${not empty insuranceList ? insuranceList : list}" />

<!-- 1. 위에서 아래로 누적할 합계 변수 초기화 -->
<c:set var="totNpBiz" value="0" />
<c:set var="totNpEmp" value="0" />
<c:set var="totNpTotal" value="0" />

<c:set var="totHiBiz" value="0" />
<c:set var="totHiEmp" value="0" />
<c:set var="totHiTotal" value="0" />

<c:set var="totLtcBiz" value="0" />
<c:set var="totLtcEmp" value="0" />
<c:set var="totLtcTotal" value="0" />

<c:set var="totEiBiz" value="0" />
<c:set var="totEiEmp" value="0" />
<c:set var="totEiTotal" value="0" />

<c:set var="totTotalBiz" value="0" />
<c:set var="totTotalEmp" value="0" />
<c:set var="totTotalSum" value="0" />

<!-- 2. 목록 순회 및 열별 금액 누적 -->
<c:forEach var="item" items="${targetList}">
	<c:set var="totNpBiz" value="${totNpBiz + (empty item.npBiz ? 0 : item.npBiz)}" />
	<c:set var="totNpEmp" value="${totNpEmp + (empty item.npEmp ? 0 : item.npEmp)}" />
	<c:set var="totNpTotal" value="${totNpTotal + (empty item.npTotal ? 0 : item.npTotal)}" />

	<c:set var="totHiBiz" value="${totHiBiz + (empty item.hiBiz ? 0 : item.hiBiz)}" />
	<c:set var="totHiEmp" value="${totHiEmp + (empty item.hiEmp ? 0 : item.hiEmp)}" />
	<c:set var="totHiTotal" value="${totHiTotal + (empty item.hiTotal ? 0 : item.hiTotal)}" />

	<c:set var="totLtcBiz" value="${totLtcBiz + (empty item.ltcBiz ? 0 : item.ltcBiz)}" />
	<c:set var="totLtcEmp" value="${totLtcEmp + (empty item.ltcEmp ? 0 : item.ltcEmp)}" />
	<c:set var="totLtcTotal" value="${totLtcTotal + (empty item.ltcTotal ? 0 : item.ltcTotal)}" />

	<c:set var="totEiBiz" value="${totEiBiz + (empty item.eiBiz ? 0 : item.eiBiz)}" />
	<c:set var="totEiEmp" value="${totEiEmp + (empty item.eiEmp ? 0 : item.eiEmp)}" />
	<c:set var="totEiTotal" value="${totEiTotal + (empty item.eiTotal ? 0 : item.eiTotal)}" />

	<c:set var="totTotalBiz" value="${totTotalBiz + (empty item.totalBiz ? 0 : item.totalBiz)}" />
	<c:set var="totTotalEmp" value="${totTotalEmp + (empty item.totalEmp ? 0 : item.totalEmp)}" />
	<c:set var="totTotalSum" value="${totTotalSum + (empty item.totalSum ? 0 : item.totalSum)}" />

	<tr>
		<td>${item.employmentType}</td>
		<td>${item.nameKor}</td>
		<td><fmt:formatDate value="${item.hireDate}" pattern="yyyy-MM-dd" /></td>
		<td>${item.deptName}</td>
		<td>${item.positionName}</td>

		<!-- 국민연금 -->
		<td class="text-right"><fmt:formatNumber value="${item.npBiz}" pattern="#,##0" /></td>
		<td class="text-right"><fmt:formatNumber value="${item.npEmp}" pattern="#,##0" /></td>
		<td class="text-right col-subtotal"><fmt:formatNumber value="${item.npTotal}" pattern="#,##0" /></td>

		<!-- 건강보험 -->
		<td class="text-right"><fmt:formatNumber value="${item.hiBiz}" pattern="#,##0" /></td>
		<td class="text-right"><fmt:formatNumber value="${item.hiEmp}" pattern="#,##0" /></td>
		<td class="text-right col-subtotal"><fmt:formatNumber value="${item.hiTotal}" pattern="#,##0" /></td>

		<!-- 노인장기요양보험 -->
		<td class="text-right"><fmt:formatNumber value="${item.ltcBiz}" pattern="#,##0" /></td>
		<td class="text-right"><fmt:formatNumber value="${item.ltcEmp}" pattern="#,##0" /></td>
		<td class="text-right col-subtotal"><fmt:formatNumber value="${item.ltcTotal}" pattern="#,##0" /></td>

		<!-- 고용보험 -->
		<td class="text-right"><fmt:formatNumber value="${item.eiBiz}" pattern="#,##0" /></td>
		<td class="text-right"><fmt:formatNumber value="${item.eiEmp}" pattern="#,##0" /></td>
		<td class="text-right col-subtotal"><fmt:formatNumber value="${item.eiTotal}" pattern="#,##0" /></td>

		<!-- 총 합계 -->
		<td class="text-right"><fmt:formatNumber value="${item.totalBiz}" pattern="#,##0" /></td>
		<td class="text-right"><fmt:formatNumber value="${item.totalEmp}" pattern="#,##0" /></td>
		<td class="text-right col-grandtotal"><fmt:formatNumber value="${item.totalSum}" pattern="#,##0" /></td>
	</tr>
</c:forEach>

<!-- 3. 맨 밑 하단 전체 합계 행 -->
<c:if test="${not empty targetList}">
	<tr class="bg-total">
		<td>합계</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>

		<!-- 국민연금 전체합 -->
		<td class="text-right"><fmt:formatNumber value="${totNpBiz}" pattern="#,##0" /></td>
		<td class="text-right"><fmt:formatNumber value="${totNpEmp}" pattern="#,##0" /></td>
		<td class="text-right font-bold"><fmt:formatNumber value="${totNpTotal}" pattern="#,##0" /></td>

		<!-- 건강보험 전체합 -->
		<td class="text-right"><fmt:formatNumber value="${totHiBiz}" pattern="#,##0" /></td>
		<td class="text-right"><fmt:formatNumber value="${totHiEmp}" pattern="#,##0" /></td>
		<td class="text-right font-bold"><fmt:formatNumber value="${totHiTotal}" pattern="#,##0" /></td>

		<!-- 노인장기요양보험 전체합 -->
		<td class="text-right"><fmt:formatNumber value="${totLtcBiz}" pattern="#,##0" /></td>
		<td class="text-right"><fmt:formatNumber value="${totLtcEmp}" pattern="#,##0" /></td>
		<td class="text-right font-bold"><fmt:formatNumber value="${totLtcTotal}" pattern="#,##0" /></td>

		<!-- 고용보험 전체합 -->
		<td class="text-right"><fmt:formatNumber value="${totEiBiz}" pattern="#,##0" /></td>
		<td class="text-right"><fmt:formatNumber value="${totEiEmp}" pattern="#,##0" /></td>
		<td class="text-right font-bold"><fmt:formatNumber value="${totEiTotal}" pattern="#,##0" /></td>

		<!-- 전체 총 합계 -->
		<td class="text-right"><fmt:formatNumber value="${totTotalBiz}" pattern="#,##0" /></td>
		<td class="text-right"><fmt:formatNumber value="${totTotalEmp}" pattern="#,##0" /></td>
		<td class="text-right font-bold"><fmt:formatNumber value="${totTotalSum}" pattern="#,##0" /></td>
	</tr>
</c:if>