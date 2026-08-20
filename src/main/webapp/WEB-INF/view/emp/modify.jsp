<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>사원 수정 - 헥사곤존</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />

<style>
:root { -
	-primary-color: #2563eb; -
	-primary-hover: #1d4ed8; -
	-bg-gray: #f8fafc; -
	-border-color: #e2e8f0; -
	-text-main: #1e293b; -
	-text-muted: #64748b;
}

body {
	font-family: 'Pretendard Variable', Pretendard, sans-serif;
	background-color: var(- -bg-gray);
	color: var(- -text-main);
	margin: 0;
	padding: 0;
}

.container {
	max-width: 1300px;
	margin: 0 auto 60px auto;
	padding: 0 20px;
}

/* Page Header */
.page-header {
	margin-bottom: 24px;
	border-bottom: 2px solid var(- -border-color);
	padding-bottom: 16px;
}

.page-header h2 {
	font-size: 24px;
	font-weight: 700;
	color: var(- -text-main);
	display: flex;
	align-items: center;
	gap: 10px;
	margin: 0 0 6px 0;
}

.page-header p {
	margin: 0;
	font-size: 13px;
	color: var(- -text-muted);
}

.page-header .required-mark {
	color: #ef4444;
	font-weight: bold;
}

/* Layout Main Flex */
.emp-layout {
	display: flex;
	gap: 24px;
	align-items: flex-start;
}

/* Left Sidebar Navigation & Photo Profile */
.side-panel {
	width: 260px;
	flex-shrink: 0;
	position: sticky;
	top: 20px;
}

.profile-card {
	background: #ffffff;
	border: 1px solid var(- -border-color);
	border-radius: 12px;
	padding: 20px;
	text-align: center;
	box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
	margin-bottom: 20px;
}

.profile-img-box {
	width: 120px;
	height: 150px;
	background-color: #f1f5f9;
	border: 2px dashed #cbd5e1;
	border-radius: 8px;
	margin: 0 auto 12px auto;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	color: var(- -text-muted);
	font-size: 12px;
}

.profile-btn-group {
	display: flex;
	gap: 6px;
	justify-content: center;
	margin-bottom: 12px;
}

.side-menu {
	background: #ffffff;
	border: 1px solid var(- -border-color);
	border-radius: 12px;
	overflow: hidden;
	box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
}

.side-menu-title {
	background: #f1f5f9;
	padding: 12px 16px;
	font-size: 13px;
	font-weight: 700;
	color: #475569;
	border-bottom: 1px solid var(- -border-color);
}

.side-menu ul {
	list-style: none;
	padding: 0;
	margin: 0;
}

.side-menu li a {
	display: block;
	padding: 12px 16px;
	font-size: 14px;
	color: #334155;
	text-decoration: none;
	border-bottom: 1px solid #f1f5f9;
	transition: all 0.2s ease;
}

.side-menu li a:hover {
	background-color: #eff6ff;
	color: var(- -primary-color);
	font-weight: 600;
}

/* Right Main Content Form Section */
.main-form-area {
	flex-grow: 1;
	display: flex;
	flex-direction: column;
	gap: 28px;
}

.form-section {
	background: #ffffff;
	border: 1px solid var(- -border-color);
	border-radius: 12px;
	padding: 24px;
	box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
}

.section-head {
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-bottom: 2px solid #f1f5f9;
	padding-bottom: 12px;
	margin-bottom: 20px;
}

.section-head h3 {
	font-size: 17px;
	font-weight: 700;
	color: var(- -text-main);
	margin: 0;
	display: flex;
	align-items: center;
	gap: 8px;
}

/* Common Form Grid Table Design */
.form-table {
	width: 100%;
	border-collapse: collapse;
	font-size: 13px;
}

.form-table th {
	background-color: #f8fafc;
	width: 130px;
	padding: 10px 12px;
	text-align: left;
	font-weight: 600;
	color: #475569;
	border: 1px solid var(- -border-color);
}

.form-table td {
	padding: 8px 12px;
	border: 1px solid var(- -border-color);
}

/* Inputs & Select Elements */
input[type="text"], input[type="date"], input[type="number"], select,
	textarea {
	width: 100%;
	padding: 7px 10px;
	font-size: 13px;
	border: 1px solid #cbd5e1;
	border-radius: 6px;
	box-sizing: border-box;
	outline: none;
	transition: border-color 0.2s ease;
}

input[type="text"]:focus, input[type="date"]:focus, input[type="number"]:focus,
	select:focus, textarea:focus {
	border-color: var(- -primary-color);
	box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
}

.inline-inputs {
	display: flex;
	gap: 6px;
	align-items: center;
}

.radio-group, .check-group {
	display: flex;
	gap: 14px;
	align-items: center;
	flex-wrap: wrap;
}

.radio-group label, .check-group label {
	cursor: pointer;
	display: flex;
	align-items: center;
	gap: 4px;
}

/* Dynamic Data Tables */
.data-table {
	width: 100%;
	border-collapse: collapse;
	font-size: 13px;
	text-align: center;
}

.data-table th {
	background-color: #f1f5f9;
	padding: 10px;
	border: 1px solid var(- -border-color);
	color: #334155;
}

.data-table td {
	padding: 6px 8px;
	border: 1px solid var(- -border-color);
}

/* Custom Buttons */
.btn {
	display: inline-flex;
	align-items: center;
	justify-content: center;
	padding: 7px 14px;
	font-size: 13px;
	font-weight: 600;
	border-radius: 6px;
	cursor: pointer;
	border: none;
	transition: all 0.2s ease;
	text-decoration: none;
}

.btn-primary {
	background-color: var(- -primary-color);
	color: #fff;
}

.btn-primary:hover {
	background-color: var(- -primary-hover);
}

.btn-secondary {
	background-color: #64748b;
	color: #fff;
}

.btn-secondary:hover {
	background-color: #475569;
}

.btn-outline {
	background-color: #fff;
	border: 1px solid #cbd5e1;
	color: #334155;
}

.btn-outline:hover {
	background-color: #f1f5f9;
}

.btn-sm {
	padding: 4px 8px;
	font-size: 12px;
}

.success-message {
	margin-bottom: 20px;
	padding: 12px 16px;
	border: 1px solid #bbf7d0;
	background: #f0fdf4;
	color: #166534;
	border-radius: 8px;
	font-size: 13px;
	font-weight: 600;
}

/* Bottom Sticky Action Bar */
.bottom-actions {
	display: flex;
	justify-content: center;
	gap: 12px;
	margin-top: 30px;
	padding: 20px 0;
}
</style>
</head>
<body>

	<!-- 헤더 태그 -->
	<layout:headerNav />

	<div class="container">
		<!-- 페이지 상단 타투 및 설명 -->
		<div class="page-header">
			<h2>👔 사원 수정</h2>
			<p>
				사원 정보를 조회하고 수정하는 메뉴입니다. (<span class="required-mark">*</span> 표시는 필수
				입력사항입니다)
			</p>
		</div>

		<c:if test="${ModifySuccess}">
			<div class="success-message">사원 정보 수정이 완료되었습니다.</div>
		</c:if>

		<form id="empForm"
			action="${pageContext.request.contextPath}/emp/modify.do"
			method="post">
			<input type="hidden" name="empId" value="${emp.empId}">
			<div class="emp-layout">

				<!-- 좌측 프로필 및 퀵 링크 패널 -->
				<div class="side-panel">
					<div class="profile-card">
						<div class="profile-img-box">
							<span>📷</span> <span>사원사진을</span> <span>등록해주세요</span>
						</div>
						<div class="profile-btn-group">
							<button type="button" class="btn btn-outline btn-sm">등록</button>
							<button type="button" class="btn btn-outline btn-sm">삭제</button>
						</div>
						<button type="button" class="btn btn-outline btn-sm"
							style="width: 100%;">인사기록카드 보기</button>
					</div>

					<div class="side-menu">
						<div class="side-menu-title">바로가기 메뉴</div>
						<ul>
							<li><a href="#sec-basic">1. 기본정보</a></li>
							<li><a href="#sec-salary">2. 급여</a></li>
							<li><a href="#sec-insurance">3. 4대보험</a></li>
							<li><a href="#sec-family">4. 부양가족</a></li>
							<li><a href="#sec-edu">5. 학력</a></li>
							<li><a href="#sec-career">6. 경력</a></li>
							<li><a href="#sec-military">7. 병역</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-license');">8.
									자격 면허</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-training');">9.
									교육 훈련</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-reward');">10.
									상벌</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-appointment');">11.
									발령</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-guarantee');">12.
									추천</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-retirement');">13.
									퇴직</a></li>
						</ul>
					</div>
				</div>

				<!-- 우측 입력 폼 영역 -->
				<div class="main-form-area">

					<!-- 1. 기본정보 -->
					<div class="form-section" id="sec-basic">
						<div class="section-head">
							<h3>📌 기본정보</h3>
						</div>
						<table class="form-table">
							<tr>
								<th>사원번호</th>
								<td><input type="text" value="${emp.empId}" readonly></td>
								<th>고용형태 <span class="required-mark">*</span></th>
								<td><select name="employmentType" required>
										<option value="">선택</option>
										<option value="정규직"
											${emp.employmentType == '정규직' || param.employmentType == '정규직' ? 'selected' : ''}>정규직</option>
										<option value="계약직"
											${emp.employmentType == '계약직' || param.employmentType == '계약직' ? 'selected' : ''}>계약직</option>
										<option value="임시직"
											${emp.employmentType == '임시직' || param.employmentType == '임시직' ? 'selected' : ''}>임시직</option>
										<option value="파견직"
											${emp.employmentType == '파견직' || param.employmentType == '파견직' ? 'selected' : ''}>파견직</option>
										<option value="위촉직"
											${emp.employmentType == '위촉직' || param.employmentType == '위촉직' ? 'selected' : ''}>위촉직</option>
										<option value="일용직"
											${emp.employmentType == '일용직' || param.employmentType == '일용직' ? 'selected' : ''}>일용직</option>
								</select></td>
							</tr>
							<tr>
								<th>성명(한글) <span class="required-mark">*</span></th>
								<td><input type="text" name="nameKor"
									value="${not empty emp ? emp.nameKor : param.nameKor}" required></td>
								<th>성명(영문)</th>
								<td><input type="text" name="nameEng"
									value="${not empty emp ? emp.nameEng : param.nameEng}"></td>
							</tr>
							<tr>
								<th>입사일 <span class="required-mark">*</span></th>
								<td><input type="date" name="hireDate"
									value="${not empty emp ? emp.hireDate : param.hireDate}"
									required></td>
								<th>퇴사일</th>
								<td><input type="date" name="retireDate"
									value="${not empty param.retireDate ? param.retireDate : emp.retireDate}">
								</td>
							</tr>
							<tr>
								<th>부서</th>
								<td>
									<div class="inline-inputs">
										<select name="deptId">
											<option value="">선택</option>
											<option value="1"
												${emp.deptId == 1 || param.deptId == '1' ? 'selected' : ''}>사장실</option>
											<option value="2"
												${emp.deptId == 2 || param.deptId == '2' ? 'selected' : ''}>개발팀</option>
											<option value="3"
												${emp.deptId == 3 || param.deptId == '3' ? 'selected' : ''}>콘텐츠팀</option>
											<option value="4"
												${emp.deptId == 4 || param.deptId == '4' ? 'selected' : ''}>업무지원팀</option>
											<option value="5"
												${emp.deptId == 5 || param.deptId == '5' ? 'selected' : ''}>디자인팀</option>
											<option value="6"
												${emp.deptId == 6 || param.deptId == '6' ? 'selected' : ''}>관리팀</option>
											<option value="7"
												${emp.deptId == 7 || param.deptId == '7' ? 'selected' : ''}>기획전략팀</option>
										</select>
										<button type="button" class="btn btn-outline btn-sm"
											style="white-space: nowrap;">관리</button>
									</div>
								</td>
								<th>직위</th>
								<td>
									<div class="inline-inputs">
										<select name="positionId">
											<option value="">선택</option>
											<option value="1"
												${emp.positionId == 1 || param.positionId == '1' ? 'selected' : ''}>이사</option>
											<option value="2"
												${emp.positionId == 2 || param.positionId == '2' ? 'selected' : ''}>사장</option>
											<option value="3"
												${emp.positionId == 3 || param.positionId == '3' ? 'selected' : ''}>부장</option>
											<option value="4"
												${emp.positionId == 4 || param.positionId == '4' ? 'selected' : ''}>차장</option>
											<option value="5"
												${emp.positionId == 5 || param.positionId == '5' ? 'selected' : ''}>과장</option>
											<option value="6"
												${emp.positionId == 6 || param.positionId == '6' ? 'selected' : ''}>대리</option>
											<option value="7"
												${emp.positionId == 7 || param.positionId == '7' ? 'selected' : ''}>주임</option>
											<option value="8"
												${emp.positionId == 8 || param.positionId == '8' ? 'selected' : ''}>사원</option>
										</select>
										<button type="button" class="btn btn-outline btn-sm"
											style="white-space: nowrap;">관리</button>
									</div>
								</td>
							</tr>
							<tr>
								<th>내/외국인</th>
								<td><select name="nationality">
										<option value="">선택</option>
										<option value="내국인"
											${emp.nationality == '내국인' || param.nationality == '내국인' ? 'selected' : ''}>내국인</option>
										<option value="외국인"
											${emp.nationality == '외국인' || param.nationality == '외국인' ? 'selected' : ''}>외국인</option>
								</select></td>
								<th>주민번호</th>
								<td><input type="text" name="residentNo"
									value="${not empty emp ? emp.residentNo : param.residentNo}"></td>
							</tr>
							<tr>
								<th>주소</th>
								<td colspan="3">
									<div class="inline-inputs" style="margin-bottom: 6px;">
										<input type="text" name="zipCode"
											value="${not empty emp ? emp.zipCode : param.zipCode}"
											style="width: 120px;">
										<button type="button" class="btn btn-outline btn-sm">우편번호
											검색</button>
									</div> <input type="text" name="address"
									value="${not empty emp ? emp.address : param.address}">
								</td>
							</tr>
							<tr>
								<th>전화번호</th>
								<td><input type="text" name="phoneNum"
									value="${not empty emp ? emp.phoneNum : param.phoneNum}"></td>
								<th>휴대폰</th>
								<td><input type="text" name="mobileNum"
									value="${not empty emp ? emp.mobileNum : param.mobileNum}"></td>
							</tr>
							<tr>
								<th>이메일</th>
								<td><input type="text" name="email"
									value="${not empty emp ? emp.email : param.email}"></td>
								<th>SNS</th>
								<td><input type="text" name="sns"
									value="${not empty emp ? emp.sns : param.sns}"></td>
							</tr>
							<tr>
								<th>기타사항</th>
								<td colspan="3"><textarea name="remarks" rows="3">${not empty emp ? emp.remarks : param.remarks}</textarea></td>
							</tr>
						</table>
						<input type="hidden" name="photoPath"
							value="${not empty emp ? emp.photoPath : param.photoPath}">
					</div>

					<!-- 2. 급여 & 4대보험 -->
					<div class="form-section" id="sec-salary">
						<div class="section-head">
							<h3>💳 급여 & 4대보험</h3>
						</div>
						<table class="form-table">
							<tr>
								<th>4대보험</th>
								<td colspan="3">
									<div class="check-group">
										<label><input type="checkbox" id="nationalPensionYN"
											name="nationalPensionYN" value="Y"
											${empInfoSalary.nationalPensionYN == 'Y' || param.nationalPensionYN == 'Y' ? 'checked' : ''}>
											국민연금</label> <label><input type="checkbox"
											id="healthInsuranceYN" name="healthInsuranceYN" value="Y"
											${empInfoSalary.healthInsuranceYN == 'Y' || param.healthInsuranceYN == 'Y' ? 'checked' : ''}>
											건강보험</label> <span>(감면: <select name="healthDiscountRate"
											style="width: 90px;">
												<option value="0"
													${empInfoSalary.healthDiscountRate == 0 || param.healthDiscountRate == '0' ? 'selected' : ''}>선택</option>
												<option value="10"
													${empInfoSalary.healthDiscountRate == 10 || param.healthDiscountRate == '10' ? 'selected' : ''}>10%</option>
												<option value="30"
													${empInfoSalary.healthDiscountRate == 30 || param.healthDiscountRate == '30' ? 'selected' : ''}>30%</option>
												<option value="50"
													${empInfoSalary.healthDiscountRate == 50 || param.healthDiscountRate == '50' ? 'selected' : ''}>50%</option>
												<option value="60"
													${empInfoSalary.healthDiscountRate == 60 || param.healthDiscountRate == '60' ? 'selected' : ''}>60%</option>
										</select>)
										</span> <span>/</span> <label><input type="checkbox"
											id="longtermCareYN" name="longtermCareYN" value="Y"
											${empInfoSalary.longtermCareYN == 'Y' || param.longtermCareYN == 'Y' ? 'checked' : ''}>
											노인장기요양보험 포함</label> <span>(감면: <select
											name="longtermCareDiscountRate" style="width: 90px;">
												<option value="0"
													${empInfoSalary.longtermCareDiscountRate == 0 || param.longtermCareDiscountRate == '0' ? 'selected' : ''}>선택</option>
												<option value="30"
													${empInfoSalary.longtermCareDiscountRate == 30 || param.longtermCareDiscountRate == '30' ? 'selected' : ''}>30%</option>
										</select>)
										</span> <label><input type="checkbox"
											id="employmentInsuranceYN" name="employmentInsuranceYN"
											value="Y"
											${empInfoSalary.employmentInsuranceYN == 'Y' || param.employmentInsuranceYN == 'Y' ? 'checked' : ''}>
											고용보험</label>
									</div>
								</td>
							</tr>
							<tr>
								<th>갑근세 <span class="required-mark">*</span></th>
								<td colspan="3">
									<div class="radio-group">
										<label><input type="radio" id="incomeTaxWorker"
											name="incomeTaxType" value="WORKER"
											${empInfoSalary.incomeTaxType == 'WORKER' || param.incomeTaxType == 'WORKER' ? 'checked' : ''}>
											근로소득자(근로소득간이세액표)</label> <span>세액: <select id="incomeTaxRate"
											name="incomeTaxRate" style="width: 110px;">
												<option value="">선택</option>
												<option value="80">80%</option>
												<option value="100">100%</option>
												<option value="120">120%</option>
										</select>
										</span> <label><input type="checkbox"
											name="youthTaxReductionYN" value="Y"
											${empInfoSalary.youthTaxReductionYN == 'Y' || param.youthTaxReductionYN == 'Y' ? 'checked' : ''}>
											중소기업 청년 소득세 감면</label> <select name="youthTaxReductionRate"
											style="width: 90px;">
											<option value="0"
												${empInfoSalary.youthTaxReductionRate == 0 || param.youthTaxReductionRate == '0' ? 'selected' : ''}>선택</option>
											<option value="50"
												${empInfoSalary.youthTaxReductionRate == 50 || param.youthTaxReductionRate == '50' ? 'selected' : ''}>50%</option>
											<option value="70"
												${empInfoSalary.youthTaxReductionRate == 70 || param.youthTaxReductionRate == '70' ? 'selected' : ''}>70%</option>
											<option value="90"
												${empInfoSalary.youthTaxReductionRate == 90 || param.youthTaxReductionRate == '90' ? 'selected' : ''}>90%</option>
											<option value="100"
												${empInfoSalary.youthTaxReductionRate == 100 || param.youthTaxReductionRate == '100' ? 'selected' : ''}>100%</option>
										</select>
									</div>
									<div class="radio-group" style="margin-top: 8px;">
										<label><input type="radio" name="incomeTaxType"
											value="BUSINESS"
											${empInfoSalary.incomeTaxType == 'BUSINESS' || param.incomeTaxType == 'BUSINESS' ? 'checked' : ''}>
											사업소득자(3.3%)</label> <label><input type="radio"
											name="incomeTaxType" value="DAILY"
											${empInfoSalary.incomeTaxType == 'DAILY' || param.incomeTaxType == 'DAILY' ? 'checked' : ''}>
											일용직(2.97%)</label> <label><input type="radio"
											name="incomeTaxType" value="OTHER"
											${empInfoSalary.incomeTaxType == 'OTHER' || param.incomeTaxType == 'OTHER' ? 'checked' : ''}>
											기타소득자(8.8%)</label> <label><input type="radio"
											name="incomeTaxType" value="WORK_BUSINESS"
											${empInfoSalary.incomeTaxType == 'WORK_BUSINESS' || param.incomeTaxType == 'WORK_BUSINESS' ? 'checked' : ''}>
											근로/사업소득자</label> <label><input type="radio"
											name="incomeTaxType" value="EXEMPT"
											${empInfoSalary.incomeTaxType == 'EXEMPT' || param.incomeTaxType == 'EXEMPT' ? 'checked' : ''}>
											면제</label>
									</div>
								</td>
							</tr>
							<tr>
								<th>두루누리<br>사회보험 지원 <span class="required-mark">*</span></th>
								<td colspan="3">
									<div class="radio-group">
										<label><input type="radio" name="durunuriType"
											value="NONE"
											${empty empInfoSalary.durunuriType && empty param.durunuriType || empInfoSalary.durunuriType == 'NONE' || param.durunuriType == 'NONE' ? 'checked' : ''}>
											해당 없음</label> <label><input type="radio" name="durunuriType"
											value="NEW80"
											${empInfoSalary.durunuriType == 'NEW80' || param.durunuriType == 'NEW80' ? 'checked' : ''}>
											신규가입자(80% 지원)</label> <label><input type="radio"
											name="durunuriType" value="NEW90"
											${empInfoSalary.durunuriType == 'NEW90' || param.durunuriType == 'NEW90' ? 'checked' : ''}>
											신규가입자(90% 지원)</label>
									</div>
									<div style="margin-top: 8px;">
										<label><input type="checkbox" name="durunuriSplitYN"
											value="Y"
											${empInfoSalary.durunuriSplitYN == 'Y' || param.durunuriSplitYN == 'Y' ? 'checked' : ''}>
											분리설정</label> <span style="margin-left: 15px;">국민연금 지원율 <input
											type="number" name="durunuriPensionRate"
											value="${not empty empInfoSalary ? empInfoSalary.durunuriPensionRate : (empty param.durunuriPensionRate ? 0 : param.durunuriPensionRate)}"
											min="0" max="100" style="width: 65px;"> %
										</span> <span style="margin-left: 15px;">고용보험 지원율 <input
											type="number" name="durunuriEmploymentRate"
											value="${not empty empInfoSalary ? empInfoSalary.durunuriEmploymentRate : (empty param.durunuriEmploymentRate ? 0 : param.durunuriEmploymentRate)}"
											min="0" max="100" style="width: 65px;"> %
										</span>
									</div>
								</td>
							</tr>
							<tr>
								<th>기본급 / 일급 <span class="required-mark">*</span></th>
								<td><input type="text" name="baseSalary"
									value="${not empty empInfoSalary ? empInfoSalary.baseSalary : param.baseSalary}"
									required></td>
								<td colspan="2">월급제의 경우 월 기본급, 일용직 근로자의 경우 일급을 입력합니다.</td>
							</tr>
							<tr>
								<th>국민연금 기준소득월액</th>
								<td><input type="text" name="pensionSalary"
									value="${not empty empInfoSalary ? empInfoSalary.pensionSalary : param.pensionSalary}"></td>
								<td colspan="2" rowspan="3">입력 시 4대보험 공제시 우선 적용되며,<br>미입력
									시 해당 근로일의 비과세를 제외한 과세합계로 적용됩니다.
								</td>
							</tr>
							<tr>
								<th>건강보험 보수월액</th>
								<td><input type="text" name="healthSalary"
									value="${not empty empInfoSalary ? empInfoSalary.healthSalary : param.healthSalary}"></td>
							</tr>
							<tr>
								<th>고용보험 보수월액</th>
								<td><input type="text" name="employmentSalary"
									value="${not empty empInfoSalary ? empInfoSalary.employmentSalary : param.employmentSalary}"></td>
							</tr>
							<tr>
								<th>급여계좌</th>
								<td colspan="3"><div class="inline-inputs">
										<select name="salaryBank" style="width: 160px;">
											<option value="">선택</option>
											<option value="국민"
												${empInfoSalary.salaryBank == '국민' || param.salaryBank == '국민' ? 'selected' : ''}>국민은행</option>
											<option value="신한"
												${empInfoSalary.salaryBank == '신한' || param.salaryBank == '신한' ? 'selected' : ''}>신한은행</option>
											<option value="우리"
												${empInfoSalary.salaryBank == '우리' || param.salaryBank == '우리' ? 'selected' : ''}>우리은행</option>
											<option value="하나"
												${empInfoSalary.salaryBank == '하나' || param.salaryBank == '하나' ? 'selected' : ''}>하나은행</option>
										</select> <input type="text" name="salaryAccount"
											value="${not empty empInfoSalary ? empInfoSalary.salaryAccount : param.salaryAccount}">
										<button type="button" class="btn btn-outline btn-sm"
											style="white-space: nowrap;">예금주 조회</button>
									</div></td>
							</tr>
						</table>
					</div>

					<!-- 3. 4대보험 상세 정보 -->
					<div class="form-section" id="sec-insurance">
						<div class="section-head">
							<h3>🛡️ 4대보험 취득/상실 정보</h3>
						</div>
						<table class="data-table">
							<thead>
								<tr>
									<th>구분</th>
									<th>기호번호</th>
									<th>취득일자</th>
									<th>상실일자</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>국민연금</th>
									<td><input type="text" name="nationalNo"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.nationalNo : param.nationalNo}"></td>
									<td><input type="date" name="nationalAcqDate"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.nationalAcqDate : param.nationalAcqDate}"></td>
									<td><input type="date" name="nationalLossDate"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.nationalLossDate : param.nationalLossDate}"></td>
								</tr>
								<tr>
									<th>건강보험</th>
									<td><input type="text" name="healthNo"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.healthNo : param.healthNo}"></td>
									<td><input type="date" name="healthAcqDate"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.healthAcqDate : param.healthAcqDate}"></td>
									<td><input type="date" name="healthLossDate"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.healthLossDate : param.healthLossDate}"></td>
								</tr>
								<tr>
									<th>고용보험</th>
									<td><input type="text" name="employmentNo"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.employmentNo : param.employmentNo}"></td>
									<td><input type="date" name="employmentAcqDate"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.employmentAcqDate : param.employmentAcqDate}"></td>
									<td><input type="date" name="employmentLossDate"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.employmentLossDate : param.employmentLossDate}"></td>
								</tr>
								<tr>
									<th>산재보험</th>
									<td><input type="text" name="industrialNo"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.industrialNo : param.industrialNo}"></td>
									<td><input type="date" name="industrialAcqDate"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.industrialAcqDate : param.industrialAcqDate}"></td>
									<td><input type="date" name="industrialLossDate"
										value="${not empty nationalPensionInfo ? nationalPensionInfo.industrialLossDate : param.industrialLossDate}"></td>
								</tr>
							</tbody>
						</table>
					</div>

					<!-- 4. 부양가족 -->
					<div class="form-section" id="sec-family">
						<div class="section-head">
							<h3>👨‍👩‍👧‍👦 부양가족 정보</h3>
							<div>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="addFamilyRow()">+ 행 추가</button>

								<button type="button" class="btn btn-outline btn-sm"
									onclick="removeSelectedRows('familyTable', 'fam-chk')">
									선택 행 삭제</button>
							</div>
						</div>

						<table class="data-table" id="familyTable">

							<thead>
								<tr>
									<th width="40"><input type="checkbox"
										onclick="toggleAll(this,'fam-chk')"></th>

									<th>관계 <span class="required-mark">*</span></th>
									<th>성명 <span class="required-mark">*</span></th>
									<th>구분</th>
									<th>주민등록번호</th>
									<th>장애여부</th>
									<th>인적공제</th>
									<th>건강보험</th>
									<th>동거</th>
									<th>소득세</th>
									<th>20세이하 자녀</th>
								</tr>
							</thead>

							<tbody>

								<c:choose>

									<c:when test="${not empty dependent}">

										<c:forEach var="dep" items="${dependent}" varStatus="st">
											<tr>
												<td><input type="checkbox" class="fam-chk"> <input
													type="hidden" name="dependentId" value="${dep.dependentId}">
												</td>
												<!-- 관계 -->
												<td><select name="relation">
														<option value="" ${empty dep.relation ? 'selected' : ''}>선택</option>
														<option value="배우자"
															${dep.relation == '배우자' ? 'selected' : ''}>배우자</option>
														<option value="아들"
															${dep.relation == '아들' ? 'selected' : ''}>아들</option>
														<option value="딸" ${dep.relation == '딸' ? 'selected' : ''}>딸</option>
														<option value="부" ${dep.relation == '부' ? 'selected' : ''}>부</option>
														<option value="모" ${dep.relation == '모' ? 'selected' : ''}>모</option>
														<option value="형제"
															${dep.relation == '형제' ? 'selected' : ''}>형제</option>
														<option value="자매"
															${dep.relation == '자매' ? 'selected' : ''}>자매</option>
														<option value="조부"
															${dep.relation == '조부' ? 'selected' : ''}>조부</option>
														<option value="조모"
															${dep.relation == '조모' ? 'selected' : ''}>조모</option>
														<option value="손자"
															${dep.relation == '손자' ? 'selected' : ''}>손자</option>
														<option value="손녀"
															${dep.relation == '손녀' ? 'selected' : ''}>손녀</option>
														<option value="기타"
															${dep.relation == '기타' ? 'selected' : ''}>기타</option>
												</select></td>

												<!-- 성명 -->
												<td><input type="text" name="familyName"
													value="${dep.familyName}"></td>


												<!-- 구분 -->
												<td><select name="nationality">

														<option value=""
															${empty dep.nationality ? 'selected' : ''}>
															선택해주세요</option>

														<option value="내국인"
															${dep.nationality == '내국인' ? 'selected' : ''}>
															내국인</option>

														<option value="외국인"
															${dep.nationality == '외국인' ? 'selected' : ''}>
															외국인</option>

												</select></td>


												<!-- 주민등록번호 -->
												<td><input type="text" name="residentNo"
													value="${dep.residentNo}"></td>


												<!-- 체크박스는 행 번호를 붙임 -->

												<td><input type="checkbox"
													name="disabilityYN_${st.index}" value="Y"
													${dep.disabilityYN == 'Y' ? 'checked' : ''}></td>

												<td><input type="checkbox"
													name="deductionYN_${st.index}" value="Y"
													${dep.deductionYN == 'Y' ? 'checked' : ''}></td>

												<td><input type="checkbox"
													name="healthInsuranceYN_${st.index}" value="Y"
													${dep.healthInsuranceYN == 'Y' ? 'checked' : ''}></td>

												<td><input type="checkbox"
													name="cohabitationYN_${st.index}" value="Y"
													${dep.cohabitationYN == 'Y' ? 'checked' : ''}></td>

												<td><input type="checkbox"
													name="incomeTaxYN_${st.index}" value="Y"
													${dep.incomeTaxYN == 'Y' ? 'checked' : ''}></td>

												<td><input type="checkbox"
													name="childAgeYN_${st.index}" value="Y"
													${dep.childAgeYN == 'Y' ? 'checked' : ''}></td>

											</tr>

										</c:forEach>

									</c:when>



									<c:otherwise>

										<tr>

											<td><input type="checkbox" class="fam-chk"></td>


											<td><select name="relation">
													<option value="" selected>선택</option>
													<option value="배우자">배우자</option>
													<option value="아들">아들</option>
													<option value="딸">딸</option>
													<option value="부">부</option>
													<option value="모">모</option>
													<option value="형제">형제</option>
													<option value="자매">자매</option>
													<option value="조부">조부</option>
													<option value="조모">조모</option>
													<option value="손자">손자</option>
													<option value="손녀">손녀</option>
													<option value="기타">기타</option>
											</select></td>
											<td><input type="text" name="familyName"></td>
											<td><select name="nationality">
													<option value="" selected>선택해주세요</option>
													<option value="내국인">내국인</option>
													<option value="외국인">외국인</option>
											</select></td>
											<td><input type="text" name="residentNo"></td>

											<!-- 처음 행은 index 0 -->
											<td><input type="checkbox" name="disabilityYN_0"
												value="Y"></td>

											<td><input type="checkbox" name="deductionYN_0"
												value="Y"></td>

											<td><input type="checkbox" name="healthInsuranceYN_0"
												value="Y"></td>

											<td><input type="checkbox" name="cohabitationYN_0"
												value="Y"></td>

											<td><input type="checkbox" name="incomeTaxYN_0"
												value="Y"></td>

											<td><input type="checkbox" name="childAgeYN_0" value="Y">
											</td>

										</tr>

									</c:otherwise>

								</c:choose>

							</tbody>

						</table>
					</div>

					<!-- 6. 경력 -->
					<div class="form-section" id="sec-career">
						<div class="section-head">
							<h3>💼 경력 정보</h3>
							<div>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="addCareerRow()">+ 행 추가</button>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="removeSelectedRows('careerTable','career-chk')">선택
									행 삭제</button>
							</div>
						</div>
						<table class="data-table" id="careerTable">
							<thead>
								<tr>
									<th width="40"><input type="checkbox"
										onclick="toggleAll(this,'career-chk')"></th>
									<th>회사명</th>
									<th>입사일자</th>
									<th>퇴사일자</th>
									<th>근무기간</th>
									<th>최종직위</th>
									<th>담당직무</th>
									<th>퇴직사유</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty careerReqs}">
										<c:forEach var="career" items="${careerReqs}">
											<tr>
												<td><input type="checkbox" class="career-chk"></td>
												<td><input type="text" name="companyName"
													value="${career.companyName}"></td>
												<td><input type="date" name="careerHireDate"
													value="${career.hireDate}"></td>
												<td><input type="date" name="careerRetireDate"
													value="${career.retireDate}"></td>
												<td><div class="inline-inputs">
														<input type="number" name="workYear"
															value="${career.workYear}" min="0" style="width: 60px;"><span>년</span><input
															type="number" name="workMonth"
															value="${career.workMonth}" min="0" max="11"
															style="width: 60px;"><span>개월</span>
													</div></td>
												<td><input type="text" name="lastPosition"
													value="${career.lastPosition}"></td>
												<td><input type="text" name="careerPosition"
													value="${career.position}"></td>
												<td><input type="text" name="retireReason"
													value="${career.retireReason}"></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td><input type="checkbox" class="career-chk"></td>
											<td><input type="text" name="companyName"></td>
											<td><input type="date" name="careerHireDate"></td>
											<td><input type="date" name="careerRetireDate"></td>
											<td><div class="inline-inputs">
													<input type="number" name="workYear" min="0"
														style="width: 60px;"><span>년</span><input
														type="number" name="workMonth" min="0" max="11"
														style="width: 60px;"><span>개월</span>
												</div></td>
											<td><input type="text" name="lastPosition"></td>
											<td><input type="text" name="careerPosition"></td>
											<td><input type="text" name="retireReason"></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>

					<!-- 7. 병역 -->
					<div class="form-section" id="sec-military">
						<div class="section-head">
							<h3>🪖 병역 정보</h3>
						</div>
						<table class="form-table">
							<tr>
								<th>병역구분</th>
								<td><select name="militaryType"><option value="">선택해주세요</option>
										<option value="필"
											${militaryReq.militaryType == '필' || param.militaryType == '필' ? 'selected' : ''}>군필</option>
										<option value="미필"
											${militaryReq.militaryType == '미필' || param.militaryType == '미필' ? 'selected' : ''}>미필</option>
										<option value="면제"
											${militaryReq.militaryType == '면제' || param.militaryType == '면제' ? 'selected' : ''}>면제</option></select></td>
								<th>군별</th>
								<td><select name="militaryBranch"><option value="">선택해주세요</option>
										<option value="육군"
											${militaryReq.militaryBranch == '육군' || param.militaryBranch == '육군' ? 'selected' : ''}>육군</option>
										<option value="해군"
											${militaryReq.militaryBranch == '해군' || param.militaryBranch == '해군' ? 'selected' : ''}>해군</option>
										<option value="공군"
											${militaryReq.militaryBranch == '공군' || param.militaryBranch == '공군' ? 'selected' : ''}>공군</option>
										<option value="해병대"
											${militaryReq.militaryBranch == '해병대' || param.militaryBranch == '해병대' ? 'selected' : ''}>해병대</option></select></td>
							</tr>
							<tr>
								<th>복무기간</th>
								<td colspan="3"><div class="inline-inputs"
										style="width: 400px;">
										<input type="date" name="startDate"
											value="${not empty militaryReq ? militaryReq.startDate : param.startDate}"><span>~</span><input
											type="date" name="endDate"
											value="${not empty militaryReq ? militaryReq.endDate : param.endDate}">
									</div></td>
							</tr>
							<tr>
								<th>최종계급</th>
								<td><input type="text" name="finalRank"
									value="${not empty militaryReq ? militaryReq.finalRank : param.finalRank}"
									placeholder="예: 병장"></td>
								<th>병과</th>
								<td><input type="text" name="specialty"
									value="${not empty militaryReq ? militaryReq.specialty : param.specialty}"
									placeholder="예: 보병"></td>
							</tr>
							<tr>
								<th>미필/면제 사유</th>
								<td colspan="3"><input type="text" name="exemptionReason"
									value="${not empty militaryReq ? militaryReq.exemptionReason : param.exemptionReason}"></td>
							</tr>
						</table>
					</div>

					<!-- 하단 액션 버튼 -->
					<div class="bottom-actions">
						<button type="submit" class="btn btn-primary"
							style="padding: 10px 30px; font-size: 15px;">💾 수정 저장</button>
						<button type="reset" class="btn btn-secondary"
							style="padding: 10px 24px; font-size: 15px;">취소</button>
						<a href="${pageContext.request.contextPath}/emp/list.do"
							class="btn btn-outline"
							style="padding: 10px 24px; font-size: 15px;">📋 목록으로</a>
					</div>

				</div>
			</div>
		</form>
	</div>

	<!-- 푸터 태그 -->
	<layout:footer />

	<!-- 동적 행 추가/삭제 스크립트 -->
	<script>
    function removeSelectedRows(tableId, chkClass) {
      const rows = document.querySelectorAll('#' + tableId + ' tbody tr');
      rows.forEach(row => {
        const chk = row.querySelector('.' + chkClass);
        if (chk && chk.checked) row.remove();
      });
    }

    function addFamilyRow() {
        const tbody = document.querySelector("#familyTable tbody");
        const index = tbody.rows.length;

        const row = document.createElement("tr");

        row.innerHTML = `
          <td><input type="checkbox" class="fam-chk"></td>

          <td>
            <select name="relation">
              <option value="">선택해주세요</option>
              <option value="배우자">배우자</option>
              <option value="아들">아들</option>
              <option value="딸">딸</option>
              <option value="부">부</option>
              <option value="모">모</option>
              <option value="형제">형제</option>
              <option value="자매">자매</option>
              <option value="기타">기타</option>
            </select>
          </td>

          <td><input type="text" name="familyName"></td>

          <td>
            <select name="nationality">
              <option value="">선택해주세요</option>
              <option value="내국인">내국인</option>
              <option value="외국인">외국인</option>
            </select>
          </td>

          <td><input type="text" name="residentNo"></td>

          <td><input type="checkbox" name="disabilityYN_${index}" value="Y"></td>
          <td><input type="checkbox" name="deductionYN_${index}" value="Y"></td>
          <td><input type="checkbox" name="healthInsuranceYN_${index}" value="Y"></td>
          <td><input type="checkbox" name="cohabitationYN_${index}" value="Y"></td>
          <td><input type="checkbox" name="incomeTaxYN_${index}" value="Y"></td>
          <td><input type="checkbox" name="childAgeYN_${index}" value="Y"></td>
        `;

        tbody.appendChild(row);
    }

    function addEduRow() {
      const tbody = document.querySelector('#eduTable tbody');
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td><input type="checkbox" class="edu-chk"></td>
        <td><select name="educationType"><option value="" selected>선택</option><option value="초등학교">초등학교</option><option value="중학교">중학교</option><option value="고등학교">고등학교</option><option value="대학교">대학교</option><option value="석사">석사</option><option value="박사">박사</option></select></td>
        <td><input type="date" name="admissionDate"></td><td><input type="date" name="graduationDate"></td>
        <td><input type="text" name="schoolName"></td><td><input type="text" name="major"></td>
        <td><select name="educationStatus"><option value="" selected>선택해주세요</option><option value="졸업">졸업</option><option value="수료">수료</option><option value="자퇴">자퇴</option><option value="재학중">재학중</option></select></td>`;
      tbody.appendChild(tr);
    }

    function addCareerRow() {
      const tbody = document.querySelector('#careerTable tbody');
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td><input type="checkbox" class="career-chk"></td>
        <td><input type="text" name="companyName"></td><td><input type="date" name="careerHireDate"></td><td><input type="date" name="careerRetireDate"></td>
        <td><div class="inline-inputs"><input type="number" name="workYear" min="0" style="width:60px;"><span>년</span><input type="number" name="workMonth" min="0" max="11" style="width:60px;"><span>개월</span></div></td>
        <td><input type="text" name="lastPosition"></td><td><input type="text" name="careerPosition"></td><td><input type="text" name="retireReason"></td>`;
      tbody.appendChild(tr);
    }

    function toggleAll(master, chkClass) {
      document.querySelectorAll('.' + chkClass).forEach(cb => cb.checked = master.checked);
    }

    const healthInsurance = document.getElementById('healthInsuranceYN');
    const longtermCare = document.getElementById('longtermCareYN');
    if (healthInsurance && longtermCare) {
      healthInsurance.addEventListener('change', function() {
        if (this.checked) longtermCare.checked = true;
      });
    }

    const workerTax = document.getElementById('incomeTaxWorker');
    if (workerTax) {
      workerTax.addEventListener('change', function() {
        if (this.checked) {
          document.getElementById('nationalPensionYN').checked = true;
          document.getElementById('healthInsuranceYN').checked = true;
          document.getElementById('longtermCareYN').checked = true;
          document.getElementById('employmentInsuranceYN').checked = true;
        }
      });
    }
    
    function moveRegister2Section(event, sectionId) {
      event.preventDefault();

      const empNo = "${emp.empId}";
      if (empNo == null || empNo.trim() === "") {
        alert("사원등록 후 입력가능합니다.");
        return false;
      }

      location.href = "${pageContext.request.contextPath}/emp/modify2.do?empId="
          + encodeURIComponent(empNo) + "#" + sectionId;
      return false;
    }

    function showRetireAlert() {
        alert("퇴사일 설정은\n\n사원정보 저장 후 이용해 주세요.");
    }
  </script>

</body>
</html>