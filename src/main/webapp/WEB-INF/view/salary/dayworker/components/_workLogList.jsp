<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<c:when test="${not empty workLogList}">
		<c:forEach var="work" items="${workLogList}" varStatus="status">
			<tr>
				<!-- 1. 일자 (수정 불가, 중앙 정렬) -->
				<td style="text-align: center;">
					<!-- [핵심 추가] PAY(지급) 항목 배치 저장을 위한 Hidden 필드 --> <input
					type="hidden" name="salaryItemId" value="1" /> <input
					type="hidden" name="attendanceTypeId"
					value="${work.dailyWorkId != null ? work.dailyWorkId : work.attendanceTypeId}" />
					<input type="hidden" name="itemType" value="PAY" /> <input
					type="hidden" name="itemName" value="기본급" /> <input type="hidden"
					class="pay-amount-val" name="amount" value="${work.dailyWage}" />

					<fmt:formatDate value="${work.workDate}" pattern="yyyy-MM-dd" /> <input
					type="hidden" name="workList[${status.index}].workDate"
					value="<fmt:formatDate value='${work.workDate}' pattern='yyyy-MM-dd' />" />
				</td>

				<!-- 2. 지급율 (수정 불가, 중앙 정렬) -->
				<td style="text-align: center;">${work.paymentRate}<input
					type="hidden" name="workList[${status.index}].paymentRate"
					value="${work.paymentRate}" />
				</td>

				<!-- 3. 지급액 (수정 불가, 우측 정렬) -->
				<td style="text-align: center; font-weight: 600;"><fmt:formatNumber
						value="${work.dailyWage}" pattern="#,##0" /> <input type="hidden"
					name="workList[${status.index}].dailyWage"
					value="${work.dailyWage}" /></td>

				<!-- 4. 소득세 (readonly 적용, 연한 노란색, box-sizing 추가) -->
				<td style="padding: 4px;"><input type="text"
					name="workList[${status.index}].incomeTax"
					value="${work.incomeTax}" class="input-readonly day-tax"
					style="background-color: #fff9c4; text-align: right; width: 100%; box-sizing: border-box; border: 1px solid #e2e8f0; border-radius: 4px; padding: 4px 8px;"
					readonly /></td>

				<!-- 5. 지방소득세 (readonly 적용, 연한 노란색, box-sizing 추가) -->
				<td style="padding: 4px;"><input type="text"
					name="workList[${status.index}].localIncomeTax"
					value="${work.localIncomeTax}" class="input-readonly day-localtax"
					style="background-color: #fff9c4; text-align: right; width: 100%; box-sizing: border-box; border: 1px solid #e2e8f0; border-radius: 4px; padding: 4px 8px;"
					readonly /></td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="5"
				style="text-align: center; padding: 30px; color: #888;">해당 월의
				근무 내역이 존재하지 않습니다.</td>
		</tr>
	</c:otherwise>
</c:choose>