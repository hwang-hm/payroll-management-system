<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>급여 항목 설정</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />

<style>
/* 기본 리셋 및 폰트 설정 */
* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
	font-family: 'Pretendard', 'Malgun Gothic', '맑은 고딕', sans-serif;
	font-size: 12px;
}

/* 1. body: 전체 배경을 흰색으로 변경하고 상하좌우 적절한 여백 부여 */
body {
	background-color: #fff;
	color: #333;
	padding: 20px 40px; /* 상하 20px, 좌우 40px 여백 (필요시 30px 50px 등으로 조정 가능) */
}

/* 2. main: 카드형 틀을 제거하고 화면에 자연스럽게 채워지도록 설정 */
/* main 영역에 직접 양옆 여백을 크게 부여 */
main {
	width: 100%;
	padding-top: 20px; /* 위 */
	padding-bottom: 20px; /* 아래 */
	padding-left: 40px; /* 왼쪽 여백 */
	padding-right: 100px; /* 오른쪽 여백 */
	box-sizing: border-box;
	padding-left: 40px; /* 왼쪽 여백 */
	padding-right: 100px;
}

/* 좌우 2분할(테이블 vs 폼) 간격을 시원하게 펼침 */
.grid-container {
	display: flex;
	gap: 40px; /* 기존 20px -> 40px로 늘려서 좌우 스페이스 확보 */
}

/* 페이지 상단 타이틀 영역 */
.page-title-area {
	display: flex;
	align-items: center;
	margin-bottom: 25px;
	padding-bottom: 15px;
	border-bottom: 1px solid #e0e0e0;
}

.page-title-icon {
	width: 45px;
	height: 45px;
	background: #eef4fb;
	border-radius: 8px;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 24px;
	margin-right: 15px;
}

.page-title-text h2 {
	font-size: 18px;
	color: #222;
	margin-bottom: 4px;
	font-weight: bold;
}

.page-title-text p {
	font-size: 12px;
	color: #666;
}

/* 섹션 레이아웃 (지급항목/공제항목) */
.section-container {
	margin-bottom: 35px;
}

.section-header {
	display: flex;
	justify-content: space-between;
	align-items: flex-end;
	margin-bottom: 8px;
}

.section-title {
	font-size: 14px;
	font-weight: bold;
	color: #111;
}

.section-title .count {
	font-size: 12px;
	font-weight: normal;
	color: #888;
	margin-left: 5px;
}

.header-btns {
	display: flex;
	gap: 5px;
}

.btn-tip, .btn-calc-ref {
	height: 24px;
	padding: 0 8px;
	background: #5a647d;
	color: #fff;
	border: none;
	border-radius: 3px;
	cursor: pointer;
	font-size: 11px;
	display: flex;
	align-items: center;
	gap: 3px;
}

/* 좌우 2분할 플렉스 박스 */
.grid-container {
	display: flex;
	gap: 20px;
}

.left-table-area {
	flex: 1.2;
}

.right-form-area {
	flex: 0.8;
}

/* 테이블 공통 스타일 */
.data-table {
	width: 100%;
	border-collapse: collapse;
	border-top: 2px solid #2b7de9;
	border-bottom: 1px solid #ccc;
	text-align: center;
}

.data-table th {
	background: #f9fbfd;
	padding: 8px 5px;
	border-bottom: 1px solid #e1e8f0;
	border-right: 1px solid #e1e8f0;
	font-weight: bold;
	color: #333;
}

.data-table td {
	padding: 7px 5px;
	border-bottom: 1px solid #eee;
	border-right: 1px solid #eee;
	height: 32px;
}

.data-table th:last-child, .data-table td:last-child {
	border-right: none;
}

.data-table tbody tr {
	cursor: pointer;
}

.data-table tbody tr:hover {
	background-color: #f0f7ff;
}

.data-table tbody tr.selected {
	background-color: #e2eeff;
}

/* 사용여부 배지 아이콘 */
.icon-use-o {
	display: inline-block;
	width: 16px;
	height: 16px;
	background: #ff6b81;
	color: #fff;
	border-radius: 50%;
	font-size: 10px;
	line-height: 16px;
	text-align: center;
	font-weight: bold;
}

.icon-use-x {
	display: inline-block;
	width: 16px;
	height: 16px;
	background: #70a1ff;
	color: #fff;
	border-radius: 2px;
	font-size: 10px;
	line-height: 16px;
	text-align: center;
	font-weight: bold;
}

/* 상세 입력 폼 테이블 */
.form-table {
	width: 100%;
	border-collapse: collapse;
	border-top: 1px solid #333;
}

.form-table th {
	width: 30%;
	background: #fff;
	text-align: left;
	padding: 8px;
	border-bottom: 1px solid #e9e9e9;
	color: #444;
	font-weight: normal;
}

.form-table td {
	width: 70%;
	padding: 6px 8px;
	border-bottom: 1px solid #e9e9e9;
}

/* Form 컨트롤 스타일 */
.input-text, .select-box {
	width: 100%;
	height: 26px;
	border: 1px solid #ccc;
	padding: 0 6px;
	border-radius: 2px;
}

.input-text:focus, .select-box:focus {
	border-color: #2b7de9;
	outline: none;
}

.input-text:disabled {
	background: #f2f2f2;
}

.input-amount {
	text-align: right;
	width: 80%;
	display: inline-block;
}

.unit-text {
	margin-left: 4px;
}

.radio-label {
	margin-right: 12px;
	cursor: pointer;
}

/* 하단 버튼 그룹 */
.btn-group {
	margin-top: 15px;
	display: flex;
	justify-content: flex-end;
	gap: 4px;
}

.btn {
	height: 28px;
	padding: 0 14px;
	border: 1px solid transparent;
	border-radius: 2px;
	font-weight: bold;
	cursor: pointer;
}

.btn-action {
	background: #3b82f6;
	color: #fff;
}

.btn-action:hover {
	background: #2563eb;
}

.btn-delete {
	background: #888;
	color: #fff;
}

.btn-delete:hover {
	background: #666;
}

.btn-clear {
	background: #94a3b8;
	color: #fff;
}

.btn-clear:hover {
	background: #64748b;
}
</style>
</head>
<body>
	<!-- 헤더 Include -->
	<layout:headerNav />
	<main>
		<!-- 1. 페이지 헤더 -->
		<div class="page-title-area">
			<div class="page-title-icon">💰</div>
			<div class="page-title-text">
				<h2>급여 항목 설정</h2>
				<p>급여와 연관된 지급 및 공제 항목을 설정하는 메뉴입니다. 회사실정에 맞추어 설정하실 수 있습니다.</p>
			</div>
		</div>

		<!-- 2. 지급항목 설정 섹션 -->
		<div class="section-container">
			<div class="section-header">
				<div class="section-title">
					지급항목 설정 <span class="count">(항목 수: ${empty payList ? 0 : payList.size()}개)</span>
				</div>
				<div class="header-btns">
					<button type="button" class="btn-tip">❓ Tip</button>
				</div>
			</div>

			<div class="grid-container">
				<!-- 좌측: 지급항목 목록 테이블 -->
				<div class="left-table-area">
					<table class="data-table" id="payItemTable">
						<thead>
							<tr>
								<th>지급항목</th>
								<th>과세여부</th>
								<th>비과수한도액</th>
								<th>절사단위</th>
								<th>근태연결/일괄지급</th>
								<th>사용여부</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty payList}">
									<tr>
										<td colspan="6" style="padding: 20px; color: #999;">등록된
											지급항목이 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="item" items="${payList}">
										<tr onclick="selectPayRow(this)"
											data-id="${item.salaryItemId}"
											data-attendance-type-id="${item.attendanceTypeId}"
											data-name="${item.itemName}" data-tax="${item.taxYn}"
											data-taxfree-name="${item.taxFreeName}"
											data-taxfree-limit="${item.taxFreeLimit}"
											data-calc="${item.calcMethod}" data-round="${item.roundUnit}"
											data-default-amount="${item.defaultAmount}"
											data-remark="${item.remark}" data-use="${item.useYn}">
											<td>${item.itemName}</td>
											<td><c:choose>
													<c:when test="${item.taxYn eq 'N'}">비과세(${item.taxFreeName})</c:when>
													<c:otherwise>전체과세</c:otherwise>
												</c:choose></td>
											<td><c:if test="${not empty item.taxFreeLimit}">
													<fmt:formatNumber value="${item.taxFreeLimit}"
														pattern="#,##0" />
												</c:if></td>
											<td><c:choose>
													<c:when test="${item.roundUnit eq '1'}">1원 단위</c:when>
													<c:when test="${item.roundUnit eq '10'}">10원 단위</option>
													</c:when>
													<c:when test="${item.roundUnit eq '100'}">100원 단위</c:when>
													<c:otherwise>없음</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${not empty item.defaultAmount}">일괄지급_<fmt:formatNumber
															value="${item.defaultAmount}" pattern="#,##0" />
													</c:when>
													<c:when test="${not empty item.attendanceTypeId}">
														<c:forEach var="att" items="${attendanceList}">
															<c:if
																test="${att.attendanceTypeId eq item.attendanceTypeId}">
																${att.attendanceType}
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise></c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${item.useYn eq 'Y'}">
														<span class="icon-use-o">O</span>
													</c:when>
													<c:otherwise>
														<span class="icon-use-x">X</span>
													</c:otherwise>
												</c:choose></td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>

				<!-- 우측: 지급항목 상세 입력/수정 폼 -->
				<div class="right-form-area">
					<form id="payItemForm"
						action="${pageContext.request.contextPath}/config/salaryItem.do"
						method="post">
						<!-- 구분 파라미터 (지급: PAY) -->
						<input type="hidden" name="itemType" value="PAY" />
						<!-- 선택된 항목 PK (없으면 신규 등록) -->
						<input type="hidden" id="payItemId" name="salaryItemId" value="" />

						<table class="form-table">
							<tr>
								<th>지급항목</th>
								<td><input type="text" id="payItemName" name="itemName"
									class="input-text" placeholder="지급 항목을 입력해주세요." required /> <c:if
										test="${errors.itemName}">
										<span class="error-text">항목명을 입력해주세요.</span>
									</c:if></td>
							</tr>
							<tr>
								<th>과세여부</th>
								<td><label class="radio-label"><input type="radio"
										name="taxYn" value="Y" checked
										onclick="toggleTaxFreeInputs(false)"> 전체과세</label> <label
									class="radio-label"><input type="radio" name="taxYn"
										value="N" onclick="toggleTaxFreeInputs(true)"> 비과세</label></td>
							</tr>
							<tr>
								<th>비과세명</th>
								<td><input type="text" id="taxFreeName" name="taxFreeName"
									class="input-text" disabled /></td>
							</tr>
							<tr>
								<th>비과세 한도액</th>
								<td><input type="text" id="taxFreeLimit"
									name="taxFreeLimit" class="input-text input-amount" value="0"
									disabled /> <span class="unit-text">원</span></td>
							</tr>
							<tr>
								<th>계산방법</th>
								<td><input type="text" id="payCalcMethod" name="calcMethod"
									class="input-text" placeholder="계산방법을 입력해주세요." /></td>
							</tr>
							<tr>
								<th>절사단위</th>
								<td><select id="payRoundUnit" name="roundUnit"
									class="select-box">
										<option value="NONE">없음</option>
										<option value="1">1원 단위</option>
										<option value="10">10원 단위</option>
										<option value="100">100원 단위</option>
								</select></td>
							</tr>
							<tr>
								<th>근태연결/일괄지급</th>
								<td><select id="payAttendanceTypeId"
									name="attendanceTypeId" class="select-box"
									onchange="toggleLumpAmountInput(this.value)">
										<option value="">선택하세요.</option>
										<c:forEach var="type" items="${attendanceList}">
											<option value="${type.attendanceTypeId}">
												${type.attendanceType}</option>
										</c:forEach>
										<option value="-1">일괄지급</option>
								</select></td>
							</tr>
							<tr id="lumpAmountRow" style="display: none;">
								<th>일괄지급액</th>
								<td><input type="text" id="payLumpAmount"
									name="defaultAmount" class="input-text input-amount" value="0"
									placeholder="0" /> <span class="unit-text">원</span></td>
							</tr>
							<tr>
								<th>사용여부</th>
								<td><label class="radio-label"><input type="radio"
										name="useYn" value="Y" checked> 사용</label> <label
									class="radio-label"><input type="radio" name="useYn"
										value="N"> 사용안함</label></td>
							</tr>
						</table>

						<div class="btn-group">
							<button type="submit" class="btn btn-action">저장 (추가/수정)</button>
							<button type="button" class="btn btn-delete"
								onclick="deletePayItem()">삭제</button>
							<button type="button" class="btn btn-clear"
								onclick="clearPayForm()">내용 지우기 (초기화)</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<!-- 3. 공제항목 설정 섹션 -->
		<div class="section-container">
			<div class="section-header">
				<div class="section-title">
					공제항목 설정 <span class="count">(항목 수: ${empty deductList ? 0 : deductList.size()}개)</span>
				</div>
				<div class="header-btns">
					<button type="button" class="btn-calc-ref">📊 공제항목 산정기준</button>
					<button type="button" class="btn-tip">❓ Tip</button>
				</div>
			</div>

			<div class="grid-container">
				<!-- 좌측: 공제항목 목록 테이블 -->
				<div class="left-table-area">
					<table class="data-table" id="deductItemTable">
						<thead>
							<tr>
								<th>공제항목</th>
								<th>절사단위</th>
								<th>사용여부</th>
								<th>비고</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty deductList}">
									<tr>
										<td colspan="4" style="padding: 20px; color: #999;">등록된
											공제항목이 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="item" items="${deductList}">
										<tr onclick="selectDeductRow(this)"
											data-id="${item.salaryItemId}" data-name="${item.itemName}"
											data-calc="${item.calcMethod}" data-round="${item.roundUnit}"
											data-remark="${item.remark}" data-use="${item.useYn}">
											<td>${item.itemName}</td>
											<td><c:choose>
													<c:when test="${item.roundUnit eq '1'}">1원 단위</c:when>
													<c:when test="${item.roundUnit eq '10'}">10원 단위</c:when>
													<c:when test="${item.roundUnit eq '100'}">100원 단위</c:when>
													<c:otherwise>없음</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${item.useYn eq 'Y'}">
														<span class="icon-use-o">O</span>
													</c:when>
													<c:otherwise>
														<span class="icon-use-x">X</span>
													</c:otherwise>
												</c:choose></td>
											<td>${item.remark}</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>

				<!-- 우측: 공제항목 상세 입력/수정 폼 -->
				<div class="right-form-area">
					<form id="deductItemForm"
						action="${pageContext.request.contextPath}/config/salaryItem.do"
						method="post">
						<!-- 구분 파라미터 (공제: DEDUCT) -->
						<input type="hidden" name="itemType" value="DEDUCT" />
						<!-- 선택된 항목 PK -->
						<input type="hidden" id="deductItemId" name="salaryItemId"
							value="" />

						<table class="form-table">
							<tr>
								<th>공제항목</th>
								<td><input type="text" id="deductItemName" name="itemName"
									class="input-text" placeholder="공제항목을 입력해주세요." required /></td>
							</tr>
							<tr>
								<th>계산방법</th>
								<td><input type="text" id="deductCalcMethod"
									name="calcMethod" class="input-text"
									placeholder="계산방법을 입력해주세요." /></td>
							</tr>
							<tr>
								<th>절사단위</th>
								<td><select id="deductRoundUnit" name="roundUnit"
									class="select-box">
										<option value="NONE">없음</option>
										<option value="1">1원 단위</option>
										<option value="10">10원 단위</option>
										<option value="100">100원 단위</option>
								</select></td>
							</tr>
							<tr>
								<th>비고</th>
								<td><input type="text" id="deductRemark" name="remark"
									class="input-text" placeholder="비고 입력" /></td>
							</tr>
							<tr>
								<th>사용여부</th>
								<td><label class="radio-label"><input type="radio"
										name="useYn" value="Y" checked> 사용</label> <label
									class="radio-label"><input type="radio" name="useYn"
										value="N"> 사용안함</label></td>
							</tr>
						</table>

						<div class="btn-group">
							<button type="submit" class="btn btn-action">저장 (추가/수정)</button>
							<button type="button" class="btn btn-delete"
								onclick="deleteDeductItem()">삭제</button>
							<button type="button" class="btn btn-clear"
								onclick="clearDeductForm()">내용 지우기 (초기화)</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>
	<!-- 푸터 Include -->
	<layout:footer />

	<!-- 스크립트 영역 -->
	<script>
	// 페이지 로딩 완료 시 이벤트 등록
	document.addEventListener('DOMContentLoaded', function() {
		// 1. 금액 입력란 숫자 전용 입력 제한 등록
		initAmountInputRestriction();

		// 2. 폼 전송(submit) 직전 데이터 정돈 이벤트
		initFormSubmitHandler();
	});

	// ----------------------------------------------------
	// [신규] 1. 금액 입력란 숫자만 입력 가능하도록 제한
	// ----------------------------------------------------
	function initAmountInputRestriction() {
		const amountInputs = document.querySelectorAll('.input-amount');
		
		amountInputs.forEach(input => {
			// 키 입력 시 숫자 이외의 문자 즉시 제거
			input.addEventListener('input', function() {
				this.value = this.value.replace(/[^0-9]/g, '');
			});

			// 포커스 아웃 시 빈 값이면 '0'으로 초기화
			input.addEventListener('blur', function() {
				if (this.value.trim() === '') {
					this.value = '0';
				}
			});
		});
	}

	// ----------------------------------------------------
	// [신규] 2. 폼 전송 전 데이터 클리닝 (Submit Handler)
	// ----------------------------------------------------
	function initFormSubmitHandler() {
		const payForm = document.getElementById('payItemForm');
		
		if (payForm) {
			payForm.addEventListener('submit', function(e) {
				const linkSelect = document.getElementById('payAttendanceTypeId');
				const lumpInput = document.getElementById('payLumpAmount');

				if (linkSelect.value === '-1') {
					// [일괄지급] 선택 시: DB의 attendance_type_id는 NULL이 되어야 하므로 빈값 처리
					linkSelect.value = '';
				} else {
					// [근태연결] 또는 [미선택] 시: 일괄지급액 defaultAmount는 NULL 처리
					lumpInput.value = '';
				}
			});
		}
	}

	// ----------------------------------------------------
	// 3. 지급항목 스크립트
	// ----------------------------------------------------
	
	// 테이블 행 클릭 시 단일 항목 데이터 폼에 채우기
	function selectPayRow(row) {
		const rows = document.querySelectorAll('#payItemTable tbody tr');
		rows.forEach(r => r.classList.remove('selected'));
		row.classList.add('selected');

		// dataset 속성을 통한 데이터 추출
		document.getElementById('payItemId').value = row.dataset.id || '';
		document.getElementById('payItemName').value = row.dataset.name || '';
		
		const taxYn = row.dataset.tax;
		const radioTax = document.querySelectorAll('#payItemForm input[name="taxYn"]');
		if(taxYn === 'N') {
			radioTax[1].checked = true;
			toggleTaxFreeInputs(true);
		} else {
			radioTax[0].checked = true;
			toggleTaxFreeInputs(false);
		}

		document.getElementById('taxFreeName').value = row.dataset.taxfreeName || '';
		document.getElementById('taxFreeLimit').value = row.dataset.taxfreeLimit || '0';
		document.getElementById('payCalcMethod').value = row.dataset.calc || '';
		document.getElementById('payRoundUnit').value = row.dataset.round || 'NONE';
		
		// 일괄지급 / 근태연결 바인딩
		const defaultAmt = row.dataset.defaultAmount;
		const attendanceTypeId = row.dataset.attendanceTypeId;
		const linkSelect = document.getElementById('payAttendanceTypeId');

		if (defaultAmt && defaultAmt !== 'null' && defaultAmt !== '' && parseFloat(defaultAmt) > 0) {
			// 1. 일괄지급인 경우
			linkSelect.value = '-1'; 
			toggleLumpAmountInput('-1');
			document.getElementById('payLumpAmount').value = defaultAmt;
		} else if (attendanceTypeId && attendanceTypeId !== 'null' && attendanceTypeId !== '') {
			// 2. 근태연결인 경우 (해당 옵션 자동 선택됨)
			linkSelect.value = attendanceTypeId;
			toggleLumpAmountInput(attendanceTypeId);
		} else {
			// 3. 미선택인 경우
			linkSelect.value = '';
			toggleLumpAmountInput('');
		}

		document.getElementById('payRemark').value = row.dataset.remark || '';

		const useYn = row.dataset.use;
		const radioUse = document.querySelectorAll('#payItemForm input[name="useYn"]');
		if(useYn === 'N') radioUse[1].checked = true;
		else radioUse[0].checked = true;
	}

	// 비과세 옵션 토글
	function toggleTaxFreeInputs(isFree) {
		const nameInput = document.getElementById('taxFreeName');
		const limitInput = document.getElementById('taxFreeLimit');
		
		nameInput.disabled = !isFree;
		limitInput.disabled = !isFree;

		if(!isFree) {
			nameInput.value = '';
			limitInput.value = '0';
		}
	}

	// 일괄지급 입력칸 토글
	function toggleLumpAmountInput(value) {
		const lumpRow = document.getElementById('lumpAmountRow');
		const lumpInput = document.getElementById('payLumpAmount');

		if (value === '-1') { // -1을 일괄지급 선택값으로 활용
			lumpRow.style.display = '';
		} else {
			lumpRow.style.display = 'none';
			lumpInput.value = '0';
		}
	}

	// 지급항목 내용 지우기 (신규 추가 모드로 전환)
	function clearPayForm() {
		document.getElementById('payItemId').value = '';
		document.getElementById('payItemForm').reset();
		toggleTaxFreeInputs(false);
		toggleLumpAmountInput('');
		
		const rows = document.querySelectorAll('#payItemTable tbody tr');
		rows.forEach(r => r.classList.remove('selected'));
	}

	// ----------------------------------------------------
	// 4. 공제항목 스크립트
	// ----------------------------------------------------

	// 공제항목 행 클릭 시 데이터 채우기
	function selectDeductRow(row) {
		const rows = document.querySelectorAll('#deductItemTable tbody tr');
		rows.forEach(r => r.classList.remove('selected'));
		row.classList.add('selected');

		document.getElementById('deductItemId').value = row.dataset.id || '';
		document.getElementById('deductItemName').value = row.dataset.name || '';
		document.getElementById('deductCalcMethod').value = row.dataset.calc || '';
		document.getElementById('deductRoundUnit').value = row.dataset.round || 'NONE';
		document.getElementById('deductRemark').value = row.dataset.remark || '';

		const useYn = row.dataset.use;
		const radioUse = document.querySelectorAll('#deductItemForm input[name="useYn"]');
		if(useYn === 'N') radioUse[1].checked = true;
		else radioUse[0].checked = true;
	}

	// 공제항목 내용 지우기 (신규 추가 모드로 전환)
	function clearDeductForm() {
		document.getElementById('deductItemId').value = '';
		document.getElementById('deductItemForm').reset();
		
		const rows = document.querySelectorAll('#deductItemTable tbody tr');
		rows.forEach(r => r.classList.remove('selected'));
	}
	
	// 지급항목 삭제 함수
	function deletePayItem() {
		const id = document.getElementById('payItemId').value;
		if (!id) {
			alert('삭제할 지급항목을 목록에서 선택해 주세요.');
			return;
		}

		if (confirm('선택한 지급항목을 삭제하시겠습니까?')) {
			const form = document.getElementById('payItemForm');
			form.action = '${pageContext.request.contextPath}/salary/item/delete.do';
			form.submit();
		}
	}

	// 공제항목 삭제 함수
	function deleteDeductItem() {
		const id = document.getElementById('deductItemId').value;
		if (!id) {
			alert('삭제할 공제항목을 목록에서 선택해 주세요.');
			return;
		}

		if (confirm('선택한 공제항목을 삭제하시겠습니까?')) {
			const form = document.getElementById('deductItemForm');
			form.action = '${pageContext.request.contextPath}/salary/item/delete.do';
			form.submit();
		}
	}
</script>
</body>
</html>