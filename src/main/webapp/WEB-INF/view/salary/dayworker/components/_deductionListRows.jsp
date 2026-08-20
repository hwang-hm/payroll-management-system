<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<%-- 1. 기존에 저장된 급여 상세 내역이 있는 경우 --%>
	<c:when test="${isSaved}">
		<c:forEach var="detail" items="${deductionList}">
			<c:if test="${detail.itemType eq 'DEDUCT'}">
				<tr>
					<td class="label-col">${detail.itemName}</td>
					<td class="val-col"><input type="text"
						name="deductAmount_${detail.salaryItemId}"
						class="deduction-input deduct-amount"
						id="${detail.itemName eq '소득세' ? 'deductIncomeTax' : (detail.itemName eq '지방소득세' ? 'deductLocalTax' : '')}"
						value="${detail.amount}" oninput="calculateTotal()" /></td>
				</tr>
			</c:if>
		</c:forEach>
	</c:when>

	<%-- 2. 저장된 내역이 없어 DB의 활성화된 공제 항목(SalaryItem)을 불러온 경우 --%>
	<c:when test="${not empty itemList}">
		<c:forEach var="item" items="${itemList}">
			<tr>
				<td class="label-col">${item.itemName}</td>
				<td class="val-col"><input type="text"
					name="deductAmount_${item.salaryItemId}"
					class="deduction-input deduct-amount"
					id="${item.itemName eq '소득세' ? 'deductIncomeTax' : (item.itemName eq '지방소득세' ? 'deductLocalTax' : '')}"
					value="0" oninput="calculateTotal()" /></td>
			</tr>
		</c:forEach>
	</c:when>

	<c:otherwise>
		<tr>
			<td colspan="2"
				style="text-align: center; padding: 15px; color: #888;">등록된 공제
				항목이 없습니다.</td>
		</tr>
	</c:otherwise>
</c:choose>