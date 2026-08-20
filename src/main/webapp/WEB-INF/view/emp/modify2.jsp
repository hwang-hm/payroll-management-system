<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>사원 정보 수정 2 - 헥사곤존</title>
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
<style>
html {
	scroll-behavior: smooth;
}

.form-section {
	scroll-margin-top: 20px;
}

.profile-summary {
	width: 100%;
	border-collapse: collapse;
	margin: 12px 0;
	font-size: 12px;
	text-align: left;
}

.profile-summary th, .profile-summary td {
	border: 1px solid var(- -border-color);
	padding: 6px 7px;
}

.profile-summary th {
	width: 68px;
	background: #f8fafc;
	color: #475569;
}

.sub-section {
	margin-top: 24px;
}

.sub-title {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 8px;
}

.sub-title h4 {
	margin: 0;
	color: #1d4ed8;
	font-size: 14px;
}

.money-box {
	display: flex;
	align-items: center;
	gap: 6px;
}

.money-box span {
	white-space: nowrap;
}

.guide-box {
	padding: 12px 14px;
	margin-bottom: 18px;
	border: 1px solid #bfdbfe;
	background: #eff6ff;
	color: #1e40af;
	border-radius: 8px;
	font-size: 13px;
}
</style>
</head>
<body>
	<layout:headerNav />

	<c:set var="savedEmpNo" value="${emp.empId}" />

	<div class="container">
		<div class="page-header">
			<h2>👔 사원 정보 수정 2</h2>
			<p>등록된 사원의 자격·면허, 어학능력, 교육훈련, 상벌, 발령, 추천·보증, 퇴직 정보를 조회하고 수정합니다.</p>
		</div>

		<c:if test="${modifySuccess}">
			<div class="success-message">사원 정보 수정이 완료되었습니다.</div>
		</c:if>

		<form id="empInfo2Form"
			action="${pageContext.request.contextPath}/emp/modify2.do"
			method="post">
			<input type="hidden" id="savedEmpNo" name="empId"
				value="${savedEmpNo}">

			<div class="emp-layout">
				<div class="side-panel">
					<div class="profile-card">
						<div class="profile-img-box">
							<span>📷</span><span>사원사진</span>
						</div>

						<table class="profile-summary">
							<tr>
								<th>사원번호</th>
								<td>${savedEmpNo}</td>
							</tr>
							<tr>
								<th>성명</th>
								<td>${emp.nameKor}</td>
							</tr>
							<tr>
								<th>부서</th>
								<td><c:forEach var="dept" items="${deptList}">
										<c:if test="${dept.deptId == emp.deptId}">
										${dept.deptName}
										</c:if>
									</c:forEach></td>
							</tr>
							<tr>
								<th>직위</th>
								<td><c:forEach var="position" items="${positionList}">
										<c:if test="${position.positionId == emp.positionId}">
										${position.positionName}
										</c:if>
									</c:forEach></td>
							</tr>
							<tr>
								<th>입사일</th>
								<td>${emp.hireDate}</td>
							</tr>
						</table>

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
							<li><a
								href="${pageContext.request.contextPath}/emp/modify.do?empId=${savedEmpNo}#sec-basic">1.
									기본정보</a></li>
							<li><a
								href="${pageContext.request.contextPath}/emp/modify.do?empId=${savedEmpNo}#sec-salary">2.
									급여</a></li>
							<li><a
								href="${pageContext.request.contextPath}/emp/modify.do?empId=${savedEmpNo}#sec-insurance">3.
									4대보험</a></li>
							<li><a
								href="${pageContext.request.contextPath}/emp/modify.do?empId=${savedEmpNo}#sec-family">4.
									부양가족</a></li>
							<li><a
								href="${pageContext.request.contextPath}/emp/modify.do?empId=${savedEmpNo}#sec-edu">5.
									학력</a></li>
							<li><a
								href="${pageContext.request.contextPath}/emp/modify.do?empId=${savedEmpNo}#sec-career">6.
									경력</a></li>
							<li><a
								href="${pageContext.request.contextPath}/emp/modify.do?empId=${savedEmpNo}#sec-military">7.
									병역</a></li>
							<li><a href="#sec-license"
								onclick="return moveSection(event,'sec-license')">8. 자격 면허</a></li>
							<li><a href="#sec-training"
								onclick="return moveSection(event,'sec-training')">9. 교육 훈련</a></li>
							<li><a href="#sec-reward"
								onclick="return moveSection(event,'sec-reward')">10. 상벌</a></li>
							<li><a href="#sec-appointment"
								onclick="return moveSection(event,'sec-appointment')">11. 발령</a></li>
							<li><a href="#sec-guarantee"
								onclick="return moveSection(event,'sec-guarantee')">12. 추천</a></li>
							<li><a href="#sec-retirement"
								onclick="return moveSection(event,'sec-retirement')">13. 퇴직</a></li>
						</ul>
					</div>
				</div>

				<div class="main-form-area">

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

					<div class="form-section" id="sec-license">
						<div class="section-head">
							<h3>🎓 자격 · 면허 & 어학능력</h3>
						</div>

						<div class="sub-section">
							<div class="sub-title">
								<h4>+ 자격 & 면허</h4>
								<div>
									<button type="button" class="btn btn-outline btn-sm"
										onclick="addLicenseRow()">+ 추가</button>
									<button type="button" class="btn btn-outline btn-sm"
										onclick="removeSelectedRows('licenseTable','license-chk')">선택삭제</button>
								</div>
							</div>
							<table class="data-table" id="licenseTable">
								<thead>
									<tr>
										<th width="40"><input type="checkbox"
											onclick="toggleAll(this,'license-chk')"></th>
										<th>자격/면허명</th>
										<th>취득일</th>
										<th>발행기관</th>
										<th>증번호</th>
										<th>비고</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input type="checkbox" class="license-chk"></td>
										<td><input type="text" name="licenseName"></td>
										<td><input type="date" name="acquisitionDate"></td>
										<td><input type="text" name="issuer"></td>
										<td><input type="text" name="licenseNumber"></td>
										<td><input type="text" name="remarks"></td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="sub-section">
							<div class="sub-title">
								<h4>+ 어학능력</h4>
								<div>
									<button type="button" class="btn btn-outline btn-sm"
										onclick="addLanguageRow()">+ 추가</button>
									<button type="button" class="btn btn-outline btn-sm"
										onclick="removeSelectedRows('languageTable','language-chk')">선택삭제</button>
								</div>
							</div>
							<table class="data-table" id="languageTable">
								<thead>
									<tr>
										<th width="40"><input type="checkbox"
											onclick="toggleAll(this,'language-chk')"></th>
										<th>외국어명</th>
										<th>시험</th>
										<th>공인점수</th>
										<th>취득일</th>
										<th>독해</th>
										<th>작문</th>
										<th>회화</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input type="checkbox" class="language-chk"></td>
										<td><input type="text" name="languageName"></td>
										<td><input type="text" name="testName"></td>
										<td><input type="text" name="officialScore"></td>
										<td><input type="date" name="acquisitionDate"></td>
										<td><select name="readingLevel"><option value=""
													selected>선택</option>
												<option value="상">상</option>
												<option value="중">중</option>
												<option value="하">하</option></select></td>
										<td><select name="writingLevel"><option value=""
													selected>선택</option>
												<option value="상">상</option>
												<option value="중">중</option>
												<option value="하">하</option></select></td>
										<td><select name="speakingLevel"><option
													value="" selected>선택</option>
												<option value="상">상</option>
												<option value="중">중</option>
												<option value="하">하</option></select></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>

					<div class="form-section" id="sec-training">
						<div class="section-head">
							<h3>📚 교육훈련</h3>
							<div>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="addTrainingRow()">+ 추가</button>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="removeSelectedRows('trainingTable','training-chk')">선택삭제</button>
							</div>
						</div>
						<table class="data-table" id="trainingTable">
							<thead>
								<tr>
									<th width="40"><input type="checkbox"
										onclick="toggleAll(this,'training-chk')"></th>
									<th>교육구분</th>
									<th>교육명</th>
									<th>교육기간(부터)</th>
									<th>교육기간(까지)</th>
									<th>교육기관</th>
									<th>교육비</th>
									<th>환급교육비</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><input type="checkbox" class="training-chk"></td>
									<td><select name="trainingType"><option value=""
												selected>선택</option>
											<option value="사내직무">사내직무</option>
											<option value="사외직무">사외직무</option>
											<option value="계층교육">계층교육</option>
											<option value="어학교육">어학교육</option>
											<option value="기타교육">기타교육</option></select></td>
									<td><input type="text" name="trainingName"></td>
									<td><input type="date" name="startDate"></td>
									<td><input type="date" name="endDate"></td>
									<td><input type="text" name="trainingCenter"></td>
									<td><div class="money-box">
											<input type="number" name="trainingCost" min="0"><span>원</span>
										</div></td>
									<td><div class="money-box">
											<input type="number" name="refundCost" min="0"><span>원</span>
										</div></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="form-section" id="sec-reward">
						<div class="section-head">
							<h3>🏅 상벌</h3>
							<div>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="addRewardRow()">+ 추가</button>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="removeSelectedRows('rewardTable','reward-chk')">선택삭제</button>
							</div>
						</div>
						<table class="data-table" id="rewardTable">
							<thead>
								<tr>
									<th width="40"><input type="checkbox"
										onclick="toggleAll(this,'reward-chk')"></th>
									<th>구분</th>
									<th>상벌명</th>
									<th>상벌권자</th>
									<th>상벌일자</th>
									<th>상벌내용</th>
									<th>비고</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><input type="checkbox" class="reward-chk"></td>
									<td><select name="rewardType"><option value=""
												selected>선택</option>
											<option value="포상">포상</option>
											<option value="표창">표창</option>
											<option value="시상">시상</option>
											<option value="면직">면직</option>
											<option value="정직">정직</option>
											<option value="감봉">감봉</option>
											<option value="견책">견책</option>
											<option value="주의">주의</option>
											<option value="경고">경고</option>
											<option value="조치불가">조치불가</option>
											<option value="해고">해고</option></select></td>
									<td><input type="text" name="rewardName"></td>
									<td><input type="text" name="manager"></td>
									<td><input type="date" name="rewardDate"></td>
									<td><input type="text" name="rewardContent"></td>
									<td><input type="text" name="remarks"></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="form-section" id="sec-appointment">
						<div class="section-head">
							<h3>📋 발령</h3>
							<div>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="addAppointmentRow()">+ 추가</button>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="removeSelectedRows('appointmentTable','appointment-chk')">선택삭제</button>
							</div>
						</div>
						<table class="data-table" id="appointmentTable">
							<thead>
								<tr>
									<th width="40"><input type="checkbox"
										onclick="toggleAll(this,'appointment-chk')"></th>
									<th>발령구분</th>
									<th>발령일자</th>
									<th>부서</th>
									<th>직위</th>
									<th>직책</th>
									<th>비고</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><input type="checkbox" class="appointment-chk"></td>
									<td><select name="appointmentType"><option
												value="" selected>선택</option>
											<option value="입사">입사</option>
											<option value="부서이동">부서이동</option>
											<option value="승진">승진</option>
											<option value="강등">강등</option>
											<option value="휴직">휴직</option>
											<option value="복직">복직</option>
											<option value="퇴직">퇴직</option>
											<option value="기타">기타</option></select></td>
									<td><input type="date" name="appointmentDate"></td>
									<td><input type="text" name="deptName"></td>
									<td><input type="text" name="positionName"></td>
									<td><input type="text" name="jobTitle"></td>
									<td><input type="text" name="remarks"></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="form-section" id="sec-guarantee">
						<div class="section-head">
							<h3>🤝 추천 & 신원보증</h3>
						</div>

						<div class="sub-section">
							<div class="sub-title">
								<h4>+ 추천인</h4>
							</div>
							<table class="data-table">
								<thead>
									<tr>
										<th>성명</th>
										<th>관계</th>
										<th>회사명</th>
										<th>직위</th>
										<th>전화번호</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input type="text" name="recommenderName"></td>
										<td><input type="text" name="relation"></td>
										<td><input type="text" name="companyName"></td>
										<td><input type="text" name="positionName"></td>
										<td><input type="text" name="phoneNumber"></td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="sub-section">
							<div class="sub-title">
								<h4>+ 보증보험</h4>
							</div>
							<table class="data-table">
								<thead>
									<tr>
										<th>가입기관</th>
										<th>보험번호</th>
										<th>보험금액</th>
										<th>가입일자</th>
										<th>만료일자</th>
										<th>비고</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input type="text" name="insuranceCompany"></td>
										<td><input type="text" name="insuranceNumber"></td>
										<td><div class="money-box">
												<input type="number" name="insuranceAmount" min="0"><span>원</span>
											</div></td>
										<td><input type="date" name="joinDate"></td>
										<td><input type="date" name="expirationDate"></td>
										<td><input type="text" name="remarks"></td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="sub-section">
							<div class="sub-title">
								<h4>+ 보증인</h4>
							</div>
							<table class="data-table">
								<thead>
									<tr>
										<th>성명</th>
										<th>관계</th>
										<th>주민등록번호</th>
										<th>보증금액</th>
										<th>보증일자</th>
										<th>만료일자</th>
										<th>전화번호</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input type="text" name="guarantorName"></td>
										<td><input type="text" name="relation"></td>
										<td><input type="text" name="residentNo"></td>
										<td><div class="money-box">
												<input type="number" name="guaranteeAmt" min="0"><span>원</span>
											</div></td>
										<td><input type="date" name="startDate"></td>
										<td><input type="date" name="endDate"></td>
										<td><input type="text" name="phoneNumber"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>

					<div class="form-section" id="sec-retirement">
						<div class="section-head">
							<h3>🚪 퇴직</h3>
						</div>
						<table class="data-table">
							<thead>
								<tr>
									<th>퇴직구분</th>
									<th>퇴직일자</th>
									<th>퇴직사유</th>
									<th>퇴직 후 연락처</th>
									<th>퇴직금</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><select name="retirementType">

											<option value=""
												${empty retirement.retirementType ? 'selected' : ''}>
												선택</option>

											<option value="정년퇴직"
												${retirement.retirementType eq '정년퇴직' ? 'selected' : ''}>
												정년퇴직</option>

											<option value="정리해고"
												${retirement.retirementType eq '정리해고' ? 'selected' : ''}>
												정리해고</option>

											<option value="자발적 퇴직"
												${retirement.retirementType eq '자발적 퇴직' ? 'selected' : ''}>
												자발적 퇴직</option>

											<option value="임원퇴직"
												${retirement.retirementType eq '임원퇴직' ? 'selected' : ''}>
												임원퇴직</option>

											<option value="중간정산"
												${retirement.retirementType eq '중간정산' ? 'selected' : ''}>
												중간정산</option>

											<option value="기타"
												${retirement.retirementType eq '기타' ? 'selected' : ''}>
												기타</option>

									</select></td>

									<td><input type="date" name="retirementDate"
										value="${retirement.retirementDate}"></td>

									<td><input type="text" name="retirementReason"
										value="${retirement.retirementReason}"></td>

									<td><input type="text" name="contactAfterRetirement"
										value="${retirement.contactAfterRetirement}"></td>

									<td>
										<div class="money-box">
											<input type="text" id="netPayment" name="netPayment"
												value="${empty severancePay ? '' : severancePay.netPayment}"
												onclick="showRetirementPayAlert()" style="cursor: pointer;">
											<span>원</span>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="bottom-actions">
						<button type="submit" class="btn btn-primary"
							style="padding: 10px 30px; font-size: 15px;">💾 수정 저장</button>
						<button type="reset" class="btn btn-secondary"
							style="padding: 10px 24px; font-size: 15px;">취소</button>
						<a href="${pageContext.request.contextPath}/emp/list.do"
							class="btn btn-outline"
							style="padding: 10px 24px; font-size: 15px;">📋 리스트</a>
					</div>
				</div>
			</div>
		</form>
	</div>

	<layout:footer />

	<script>
function moveSection(event, sectionId) {
  event.preventDefault();
  const target = document.getElementById(sectionId);
  if (target) {
    target.scrollIntoView({ behavior:"smooth", block:"start" });
  }
  return false;
}

function showRetirementPayAlert() {
  alert("퇴직금 입력은\n\n[퇴직관리] - [퇴직급여 입력 & 관리] 메뉴를 이용해 주세요.");
}

function downloadRetirementStatement() {
}

function removeSelectedRows(tableId, chkClass) {
  document.querySelectorAll("#" + tableId + " tbody tr").forEach(row => {
    const chk = row.querySelector("." + chkClass);
    if (chk && chk.checked) row.remove();
  });
}

function toggleAll(master, chkClass) {
  document.querySelectorAll("." + chkClass).forEach(cb => cb.checked = master.checked);
}

function addLicenseRow() {
  document.querySelector("#licenseTable tbody").insertAdjacentHTML("beforeend",
    `<tr><td><input type="checkbox" class="license-chk"></td><td><input type="text" name="licenseName"></td><td><input type="date" name="acquisitionDate"></td><td><input type="text" name="issuer"></td><td><input type="text" name="licenseNumber"></td><td><input type="text" name="remarks"></td></tr>`);
}

function addLanguageRow() {
  document.querySelector("#languageTable tbody").insertAdjacentHTML("beforeend",
    `<tr><td><input type="checkbox" class="language-chk"></td><td><input type="text" name="languageName"></td><td><input type="text" name="testName"></td><td><input type="text" name="officialScore"></td><td><input type="date" name="acquisitionDate"></td><td><select name="readingLevel"><option value="" selected>선택</option><option value="상">상</option><option value="중">중</option><option value="하">하</option></select></td><td><select name="writingLevel"><option value="" selected>선택</option><option value="상">상</option><option value="중">중</option><option value="하">하</option></select></td><td><select name="speakingLevel"><option value="" selected>선택</option><option value="상">상</option><option value="중">중</option><option value="하">하</option></select></td></tr>`);
}

function addTrainingRow() {
  document.querySelector("#trainingTable tbody").insertAdjacentHTML("beforeend",
    `<tr><td><input type="checkbox" class="training-chk"></td><td><select name="trainingType"><option value="" selected>선택</option><option value="사내직무">사내직무</option><option value="사외직무">사외직무</option><option value="계층교육">계층교육</option><option value="어학교육">어학교육</option><option value="기타교육">기타교육</option></select></td><td><input type="text" name="trainingName"></td><td><input type="date" name="startDate"></td><td><input type="date" name="endDate"></td><td><input type="text" name="trainingCenter"></td><td><div class="money-box"><input type="number" name="trainingCost" min="0"><span>원</span></div></td><td><div class="money-box"><input type="number" name="refundCost" min="0"><span>원</span></div></td></tr>`);
}

function addRewardRow() {
  document.querySelector("#rewardTable tbody").insertAdjacentHTML("beforeend",
    `<tr><td><input type="checkbox" class="reward-chk"></td><td><select name="rewardType"><option value="" selected>선택</option><option value="포상">포상</option><option value="표창">표창</option><option value="시상">시상</option><option value="면직">면직</option><option value="정직">정직</option><option value="감봉">감봉</option><option value="견책">견책</option><option value="주의">주의</option><option value="경고">경고</option><option value="조치불가">조치불가</option><option value="해고">해고</option></select></td><td><input type="text" name="rewardName"></td><td><input type="text" name="manager"></td><td><input type="date" name="rewardDate"></td><td><input type="text" name="rewardContent"></td><td><input type="text" name="remarks"></td></tr>`);
}

function addAppointmentRow() {
  document.querySelector("#appointmentTable tbody").insertAdjacentHTML("beforeend",
    `<tr><td><input type="checkbox" class="appointment-chk"></td><td><select name="appointmentType"><option value="" selected>선택</option><option value="입사">입사</option><option value="부서이동">부서이동</option><option value="승진">승진</option><option value="강등">강등</option><option value="휴직">휴직</option><option value="복직">복직</option><option value="퇴직">퇴직</option><option value="기타">기타</option></select></td><td><input type="date" name="appointmentDate"></td><td><input type="text" name="deptName"></td><td><input type="text" name="positionName"></td><td><input type="text" name="jobTitle"></td><td><input type="text" name="remarks"></td></tr>`);
}


</script>
</body>
</html>