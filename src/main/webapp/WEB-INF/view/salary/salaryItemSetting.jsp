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
	padding-bottom: 20px; /* 아래 */ padding-left : 40px; /* 왼쪽 여백 */
	padding-right : 100px; /* 오른쪽 여백 */
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
					지급항목 설정 <span class="count">(항목 수: 9개)</span>
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
							<tr onclick="selectPayRow(this)" data-id="1" data-name="기본급"
								data-tax="TAX" data-taxfree-name="" data-taxfree-limit="0"
								data-calc="기본급" data-round="NONE" data-link="" data-use="Y">
								<td>기본급</td>
								<td>전체과세</td>
								<td></td>
								<td>없음</td>
								<td></td>
								<td><span class="icon-use-o">O</span></td>
							</tr>
							<tr onclick="selectPayRow(this)" data-id="2" data-name="식대"
								data-tax="FREE" data-taxfree-name="식사대(월 20만원 이하)"
								data-taxfree-limit="200000" data-calc="일괄지급_200,000"
								data-round="NONE" data-link="LUMP" data-use="Y">
								<td>식비</td>
								<td>비과세_비과세 식사대(월 20만원 이하)</td>
								<td>200,000</td>
								<td>없음</td>
								<td>일괄지급_200,000</td>
								<td><span class="icon-use-o">O</span></td>
							</tr>
							<tr onclick="selectPayRow(this)" data-id="3" data-name="보육수당"
								data-tax="TAX" data-taxfree-name="" data-taxfree-limit="0"
								data-calc="" data-round="NONE" data-link="" data-use="Y">
								<td>보육수당</td>
								<td>전체과세</td>
								<td></td>
								<td>없음</td>
								<td></td>
								<td><span class="icon-use-o">O</span></td>
							</tr>
							<tr onclick="selectPayRow(this)" data-id="4" data-name="직책수당"
								data-tax="TAX" data-taxfree-name="" data-taxfree-limit="0"
								data-calc="" data-round="NONE" data-link="" data-use="Y">
								<td>직책수당</td>
								<td>전체과세</td>
								<td></td>
								<td>없음</td>
								<td></td>
								<td><span class="icon-use-o">O</span></td>
							</tr>
							<tr onclick="selectPayRow(this)" data-id="5" data-name="차량유지비"
								data-tax="FREE" data-taxfree-name="자가운전보조금"
								data-taxfree-limit="200000" data-calc="" data-round="NONE"
								data-link="" data-use="Y">
								<td>차량유지비</td>
								<td>비과세_소령§12 3(자가운전보조금)</td>
								<td>200,000</td>
								<td>없음</td>
								<td></td>
								<td><span class="icon-use-o">O</span></td>
							</tr>
							<tr onclick="selectPayRow(this)" data-id="6" data-name="당직수당"
								data-tax="TAX" data-taxfree-name="" data-taxfree-limit="0"
								data-calc="연장근무" data-round="NONE" data-link="OVERTIME"
								data-use="Y">
								<td>당직수당</td>
								<td>전체과세</td>
								<td></td>
								<td>없음</td>
								<td>연장근무</td>
								<td><span class="icon-use-o">O</span></td>
							</tr>
							<tr onclick="selectPayRow(this)" data-id="7" data-name="휴일수당"
								data-tax="TAX" data-taxfree-name="" data-taxfree-limit="0"
								data-calc="휴일근무" data-round="NONE" data-link="HOLIDAY"
								data-use="Y">
								<td>휴일수당</td>
								<td>전체과세</td>
								<td></td>
								<td>없음</td>
								<td>휴일근무</td>
								<td><span class="icon-use-o">O</span></td>
							</tr>
						</tbody>
					</table>
				</div>

				<!-- 우측: 지급항목 상세 입력/수정 폼 -->
				<div class="right-form-area">
					<form id="payItemForm">
						<input type="hidden" id="payItemId" name="payItemId" value="" />
						<table class="form-table">
							<tr>
								<th>지급항목</th>
								<td><input type="text" id="payItemName" name="itemName"
									class="input-text" placeholder="지급 항목을 입력해주세요." required /></td>
							</tr>
							<tr>
								<th>과세여부</th>
								<td><label class="radio-label"><input type="radio"
										name="taxYn" value="TAX" checked
										onclick="toggleTaxFreeInputs(false)"> 전체과세</label> <label
									class="radio-label"><input type="radio" name="taxYn"
										value="FREE" onclick="toggleTaxFreeInputs(true)"> 비과세</label></td>
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
							<!-- 근태연결/일괄지급 -->
							<tr>
								<th>근태연결/일괄지급</th>
								<td>
									<!-- 💡 onchange 이벤트 추가 --> <select id="payAttendanceLink"
									name="attendanceLink" class="select-box"
									onchange="toggleLumpAmountInput(this.value)">
										<option value="">선택하세요.</option>
										<option value="OVERTIME">연장근무</option>
										<option value="HOLIDAY">휴일근무</option>
										<option value="LUMP">일괄지급</option>
								</select>
								</td>
							</tr>

							<!-- 💡 [신규 추가] 일괄지급 선택 시에만 표시되는 일괄지급액 행 -->
							<tr id="lumpAmountRow" style="display: none;">
								<th>일괄지급액</th>
								<td><input type="text" id="payLumpAmount" name="lumpAmount"
									class="input-text input-amount" value="0" placeholder="0" /> <span
									class="unit-text">원</span></td>
							</tr>
							<tr>
								<th>사용여부</th>
								<td><label class="radio-label"><input type="radio"
										name="payUseYn" value="Y" checked> 사용</label> <label
									class="radio-label"><input type="radio" name="payUseYn"
										value="N"> 사용안함</label></td>
							</tr>
						</table>

						<div class="btn-group">
							<button type="button" class="btn btn-action"
								onclick="savePayItem('add')">추가</button>
							<button type="button" class="btn btn-action"
								onclick="savePayItem('update')">수정</button>
							<button type="button" class="btn btn-delete"
								onclick="deletePayItem()">삭제</button>
							<button type="button" class="btn btn-clear"
								onclick="clearPayForm()">내용 지우기</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<!-- 3. 공제항목 설정 섹션 -->
		<div class="section-container">
			<div class="section-header">
				<div class="section-title">
					공제항목 설정 <span class="count">(항목 수: 8개)</span>
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
							<tr onclick="selectDeductRow(this)" data-id="101"
								data-name="국민연금" data-calc="국민연금 산식 적용" data-round="NONE"
								data-note="기본항목" data-use="Y">
								<td>국민연금</td>
								<td>없음</td>
								<td><span class="icon-use-o">O</span></td>
								<td>기본항목</td>
							</tr>
							<tr onclick="selectDeductRow(this)" data-id="102"
								data-name="건강보험" data-calc="건강보험 산식 적용" data-round="1"
								data-note="기본항목" data-use="Y">
								<td>건강보험</td>
								<td>1원 단위</td>
								<td><span class="icon-use-o">O</span></td>
								<td>기본항목</td>
							</tr>
							<tr onclick="selectDeductRow(this)" data-id="103"
								data-name="장기요양보험" data-calc="장기요양 산식 적용" data-round="1"
								data-note="기본항목" data-use="Y">
								<td>장기요양보험</td>
								<td>1원 단위</td>
								<td><span class="icon-use-o">O</span></td>
								<td>기본항목</td>
							</tr>
							<tr onclick="selectDeductRow(this)" data-id="104"
								data-name="고용보험" data-calc="고용보험 산식 적용" data-round="1"
								data-note="기본항목" data-use="Y">
								<td>고용보험</td>
								<td>1원 단위</td>
								<td><span class="icon-use-o">O</span></td>
								<td>기본항목</td>
							</tr>
							<tr onclick="selectDeductRow(this)" data-id="105" data-name="소득세"
								data-calc="간이세액표 적용" data-round="1" data-note="기본항목"
								data-use="Y">
								<td>소득세</td>
								<td>1원 단위</td>
								<td><span class="icon-use-o">O</span></td>
								<td>기본항목</td>
							</tr>
							<tr onclick="selectDeductRow(this)" data-id="106"
								data-name="지방소득세" data-calc="소득세의 10%" data-round="1"
								data-note="기본항목" data-use="Y">
								<td>지방소득세</td>
								<td>1원 단위</td>
								<td><span class="icon-use-o">O</span></td>
								<td>기본항목</td>
							</tr>
							<tr onclick="selectDeductRow(this)" data-id="107"
								data-name="상조회비" data-calc="임의공제" data-round="NONE"
								data-note="사용자 추가항목" data-use="Y">
								<td>상조회비</td>
								<td>없음</td>
								<td><span class="icon-use-o">O</span></td>
								<td>사용자 추가항목</td>
							</tr>
							<tr onclick="selectDeductRow(this)" data-id="108"
								data-name="연말정산" data-calc="연말정산 결과 반영" data-round="1"
								data-note="사용자 추가항목" data-use="N">
								<td>연말정산</td>
								<td>1원 단위</td>
								<td><span class="icon-use-x">X</span></td>
								<td>사용자 추가항목</td>
							</tr>
						</tbody>
					</table>
				</div>

				<!-- 우측: 공제항목 상세 입력/수정 폼 -->
				<div class="right-form-area">
					<form id="deductItemForm">
						<input type="hidden" id="deductItemId" name="deductItemId"
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
								<td><input type="text" id="deductNote" name="note"
									class="input-text" placeholder="비고 입력" /></td>
							</tr>
							<tr>
								<th>사용여부</th>
								<td><label class="radio-label"><input type="radio"
										name="deductUseYn" value="Y" checked> 사용</label> <label
									class="radio-label"><input type="radio"
										name="deductUseYn" value="N"> 사용안함</label></td>
							</tr>
						</table>

						<div class="btn-group">
							<button type="button" class="btn btn-action"
								onclick="saveDeductItem('add')">추가</button>
							<button type="button" class="btn btn-action"
								onclick="saveDeductItem('update')">수정</button>
							<button type="button" class="btn btn-delete"
								onclick="deleteDeductItem()">삭제</button>
							<button type="button" class="btn btn-clear"
								onclick="clearDeductForm()">내용 지우기</button>
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
        // ----------------------------------------------------
        // 1. 지급항목 스크립트 로직
        // ----------------------------------------------------
        
        // 지급항목 테이블 행 클릭시 Form으로 데이터 세팅
        function selectPayRow(row) {
            // 테이블 선택 표시 (하이라이트)
            const rows = document.querySelectorAll('#payItemTable tbody tr');
            rows.forEach(r => r.classList.remove('selected'));
            row.classList.add('selected');

            // data 속성 읽기
            document.getElementById('payItemId').value = row.dataset.id;
            document.getElementById('payItemName').value = row.dataset.name;
            
            const taxYn = row.dataset.tax;
            const radioTax = document.querySelectorAll('input[name="taxYn"]');
            if(taxYn === 'FREE') {
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
            document.getElementById('payAttendanceLink').value = row.dataset.link || '';

            const useYn = row.dataset.use;
            const radioUse = document.querySelectorAll('input[name="payUseYn"]');
            if(useYn === 'N') radioUse[1].checked = true;
            else radioUse[0].checked = true;
        }

        // 비과세 선택 시 비과세 전용 입력창 활성화
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

        // 지급항목 입력폼 리셋
        function clearPayForm() {
            document.getElementById('payItemId').value = '';
            document.getElementById('payItemForm').reset();
            toggleTaxFreeInputs(false);
            
            const rows = document.querySelectorAll('#payItemTable tbody tr');
            rows.forEach(r => r.classList.remove('selected'));
        }

        // 지급항목 추가/수정 AJAX
        function savePayItem(mode) {
            const name = document.getElementById('payItemName').value.trim();
            if(!name) {
                alert('지급항목명을 입력해 주세요.');
                document.getElementById('payItemName').focus();
                return;
            }

            const formData = new URLSearchParams(new FormData(document.getElementById('payItemForm')));
            formData.append('mode', mode);

            fetch('${pageContext.request.contextPath}/salary/payItemSave.do', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
                body: formData.toString()
            })
            .then(res => res.json())
            .then(data => {
                alert(data.message);
                if(data.status === 'success') location.reload();
            })
            .catch(err => console.error(err));
        }

        // 지급항목 삭제
        function deletePayItem() {
            const id = document.getElementById('payItemId').value;
            if(!id) {
                alert('삭제할 항목을 먼저 목록에서 선택해 주세요.');
                return;
            }

            if(confirm('해당 지급항목을 삭제하시겠습니까?')) {
                fetch('${pageContext.request.contextPath}/salary/payItemDelete.do?id=' + id, { method: 'POST' })
                .then(res => res.json())
                .then(data => {
                    alert(data.message);
                    if(data.status === 'success') location.reload();
                });
            }
        }
        
     // 일괄지급 선택 여부에 따라 입력칸 표시/숨김
        function toggleLumpAmountInput(value) {
            const lumpRow = document.getElementById('lumpAmountRow');
            const lumpInput = document.getElementById('payLumpAmount');

            if (value === 'LUMP') {
                lumpRow.style.display = ''; // 표시
            } else {
                lumpRow.style.display = 'none'; // 숨김
                lumpInput.value = '0'; // 값 초기화
            }
        }

        // ----------------------------------------------------
        // 2. 공제항목 스크립트 로직
        // ----------------------------------------------------

        // 공제항목 테이블 행 클릭시 Form으로 데이터 세팅
        function selectDeductRow(row) {
            const rows = document.querySelectorAll('#deductItemTable tbody tr');
            rows.forEach(r => r.classList.remove('selected'));
            row.classList.add('selected');

            document.getElementById('deductItemId').value = row.dataset.id;
            document.getElementById('deductItemName').value = row.dataset.name;
            document.getElementById('deductCalcMethod').value = row.dataset.calc || '';
            document.getElementById('deductRoundUnit').value = row.dataset.round || 'NONE';
            document.getElementById('deductNote').value = row.dataset.note || '';

            const useYn = row.dataset.use;
            const radioUse = document.querySelectorAll('input[name="deductUseYn"]');
            if(useYn === 'N') radioUse[1].checked = true;
            else radioUse[0].checked = true;
        }

        // 공제항목 입력폼 리셋
        function clearDeductForm() {
            document.getElementById('deductItemId').value = '';
            document.getElementById('deductItemForm').reset();
            
            const rows = document.querySelectorAll('#deductItemTable tbody tr');
            rows.forEach(r => r.classList.remove('selected'));
        }

        // 공제항목 추가/수정 AJAX
        function saveDeductItem(mode) {
            const name = document.getElementById('deductItemName').value.trim();
            if(!name) {
                alert('공제항목명을 입력해 주세요.');
                document.getElementById('deductItemName').focus();
                return;
            }

            const formData = new URLSearchParams(new FormData(document.getElementById('deductItemForm')));
            formData.append('mode', mode);

            fetch('${pageContext.request.contextPath}/salary/deductItemSave.do', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
                body: formData.toString()
            })
            .then(res => res.json())
            .then(data => {
                alert(data.message);
                if(data.status === 'success') location.reload();
            })
            .catch(err => console.error(err));
        }

        // 공제항목 삭제
        function deleteDeductItem() {
            const id = document.getElementById('deductItemId').value;
            if(!id) {
                alert('삭제할 항목을 먼저 목록에서 선택해 주세요.');
                return;
            }

            if(confirm('해당 공제항목을 삭제하시겠습니까?')) {
                fetch('${pageContext.request.contextPath}/salary/deductItemDelete.do?id=' + id, { method: 'POST' })
                .then(res => res.json())
                .then(data => {
                    alert(data.message);
                    if(data.status === 'success') location.reload();
                });
            }
        }
    </script>
</body>
</html>