<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel">
	<div class="detail-split">

		<!-- 우측-좌: 일자별 근무 및 지급액 -->
		<div>
			<div class="panel-header">
				<h3 class="panel-title" id="selectedEmpName">
					[<span id="targetEmpName">선택된 사원 없음</span>] 급여 상세
				</h3>
			</div>
			<table class="custom-table">
				<thead>
					<tr>
						<th>일자</th>
						<th>지급율</th>
						<th>지급액</th>
						<th>소득세</th>
						<th>지방소득세</th>
					</tr>
				</thead>
				<tbody id="dailyDetailBody">
					<tr>
						<td>2026-08-06</td>
						<td>1</td>
						<td>
							<!-- 지급 항목 파라미터 배열 --> <input type="hidden" name="itemType"
							value="PAY" /> <input type="hidden" name="itemName" value="기본급" />
							<input type="hidden" name="taxYn" value="Y" /> <input
							type="text" name="amount" id="dailyPayInput" value="100,000"
							class="input-text" style="width: 80px; text-align: right;"
							oninput="calculateTotal()" /> <input type="hidden" name="memo"
							value="일용직 기본급" />
						</td>
						<td><input type="text" value="0" class="input-highlight"
							readonly /></td>
						<td><input type="text" value="0" class="input-highlight"
							readonly /></td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- 우측-우: 공제 항목 세부 -->
		<div>
			<div class="panel-header">
				<h3 class="panel-title">공제항목</h3>
			</div>

			<table class="deduction-table">
				<tbody>
					<!-- 공제 항목 반복 (모두 배열 형태 name="itemType", name="itemName", name="amount" 로 맞춤) -->
					<tr>
						<td class="label-col">국민연금</td>
						<td class="val-col"><input type="hidden" name="salaryItemId"
							value="10" /> <input type="hidden" name="itemType"
							value="DEDUCT" /> <input type="hidden" name="itemName"
							value="국민연금" /> <input type="hidden" name="taxYn" value="N" />
							<input type="text" name="amount" value="0"
							class="deduction-input" oninput="calculateTotal()" /> <input
							type="hidden" name="memo" value="" /></td>
					</tr>
					<tr>
						<td class="label-col">건강보험</td>
						<td class="val-col"><input type="hidden" name="salaryItemId"
							value="26" /><input type="hidden" name="itemType" value="DEDUCT" />
							<input type="hidden" name="itemName" value="건강보험" /> <input
							type="hidden" name="taxYn" value="N" /> <input type="text"
							name="amount" value="0" class="deduction-input"
							oninput="calculateTotal()" /> <input type="hidden" name="memo"
							value="" /></td>
					</tr>
					<tr>
						<td class="label-col">고용보험</td>
						<td class="val-col"><input type="hidden" name="salaryItemId"
							value="27" /><input type="hidden" name="itemType" value="DEDUCT" />
							<input type="hidden" name="itemName" value="고용보험" /> <input
							type="hidden" name="taxYn" value="N" /> <input type="text"
							name="amount" value="900" class="deduction-input"
							oninput="calculateTotal()" /> <input type="hidden" name="memo"
							value="" /></td>
					</tr>
					<tr>
						<td class="label-col">소득세</td>
						<td class="val-col"><input type="hidden" name="salaryItemId"
							value="29" /><input type="hidden" name="itemType" value="DEDUCT" />
							<input type="hidden" name="itemName" value="소득세" /> <input
							type="hidden" name="taxYn" value="N" /> <input type="text"
							name="amount" value="0" class="deduction-input"
							oninput="calculateTotal()" /> <input type="hidden" name="memo"
							value="" /></td>
					</tr>
					<tr>
						<td class="label-col">지방소득세</td>
						<td class="val-col"><input type="hidden" name="salaryItemId"
							value="30" /><input type="hidden" name="itemType" value="DEDUCT" />
							<input type="hidden" name="itemName" value="지방소득세" /> <input
							type="hidden" name="taxYn" value="N" /> <input type="text"
							name="amount" value="0" class="deduction-input"
							oninput="calculateTotal()" /> <input type="hidden" name="memo"
							value="" /></td>
					</tr>
				</tbody>
			</table>
		</div>

	</div>

	<!-- 요약 및 합계 바 -->
	<div class="summary-bar">
		<div>
			지급총액 : <span id="totalPayDisplay" style="color: #2563eb;">100,000</span>
			원
		</div>
		<div>
			공제총액 : <span id="totalDeductDisplay" style="color: #ef4444;">900</span>
			원
		</div>
	</div>

	<!-- 최종 실지급액 배너 -->
	<div class="total-hero-bar">
		실지급액 : <span id="netPayDisplay">99,100</span>원
	</div>

	<!-- 제출 버튼 -->
	<div class="bottom-actions">
		<button type="submit" class="btn btn-primary">저장</button>
		<button type="reset" class="btn btn-secondary">내용 지우기</button>
	</div>

</div>