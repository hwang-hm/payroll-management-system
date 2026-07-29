<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>사원 등록 - 헥사곤존</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
  
  <style>
    :root {
      --primary-color: #2563eb;
      --primary-hover: #1d4ed8;
      --bg-gray: #f8fafc;
      --border-color: #e2e8f0;
      --text-main: #1e293b;
      --text-muted: #64748b;
    }

    body {
      font-family: 'Pretendard Variable', Pretendard, sans-serif;
      background-color: var(--bg-gray);
      color: var(--text-main);
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
      border-bottom: 2px solid var(--border-color);
      padding-bottom: 16px;
    }
    .page-header h2 {
      font-size: 24px;
      font-weight: 700;
      color: var(--text-main);
      display: flex;
      align-items: center;
      gap: 10px;
      margin: 0 0 6px 0;
    }
    .page-header p {
      margin: 0;
      font-size: 13px;
      color: var(--text-muted);
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
      border: 1px solid var(--border-color);
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
      color: var(--text-muted);
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
      border: 1px solid var(--border-color);
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
      border-bottom: 1px solid var(--border-color);
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
      color: var(--primary-color);
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
      border: 1px solid var(--border-color);
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
      color: var(--text-main);
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
      border: 1px solid var(--border-color);
    }

    .form-table td {
      padding: 8px 12px;
      border: 1px solid var(--border-color);
    }

    /* Inputs & Select Elements */
    input[type="text"], input[type="date"], select, textarea {
      width: 100%;
      padding: 7px 10px;
      font-size: 13px;
      border: 1px solid #cbd5e1;
      border-radius: 6px;
      box-sizing: border-box;
      outline: none;
      transition: border-color 0.2s ease;
    }

    input[type="text"]:focus, input[type="date"]:focus, select:focus, textarea:focus {
      border-color: var(--primary-color);
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
      border: 1px solid var(--border-color);
      color: #334155;
    }

    .data-table td {
      padding: 6px 8px;
      border: 1px solid var(--border-color);
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

    .btn-primary { background-color: var(--primary-color); color: #fff; }
    .btn-primary:hover { background-color: var(--primary-hover); }

    .btn-secondary { background-color: #64748b; color: #fff; }
    .btn-secondary:hover { background-color: #475569; }

    .btn-outline { background-color: #fff; border: 1px solid #cbd5e1; color: #334155; }
    .btn-outline:hover { background-color: #f1f5f9; }

    .btn-sm { padding: 4px 8px; font-size: 12px; }

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
      <h2>👔 사원 등록</h2>
      <p>신규 사원 정보를 등록하는 메뉴입니다. (<span class="required-mark">*</span> 표시는 필수 입력사항입니다)</p>
    </div>

    <form id="empForm" action="${pageContext.request.contextPath}/emp/register.do" method="post">
      <div class="emp-layout">
        
        <!-- 좌측 프로필 및 퀵 링크 패널 -->
        <div class="side-panel">
          <div class="profile-card">
            <div class="profile-img-box">
              <span>📷</span>
              <span>사원사진을</span>
              <span>등록해주세요</span>
            </div>
            <div class="profile-btn-group">
              <button type="button" class="btn btn-outline btn-sm">등록</button>
              <button type="button" class="btn btn-outline btn-sm">삭제</button>
            </div>
            <button type="button" class="btn btn-outline btn-sm" style="width: 100%;">인사기록카드 보기</button>
          </div>

          <div class="side-menu">
            <div class="side-menu-title">바로가기 메뉴</div>
            <ul>
              <li><a href="#sec-basic">1. 기본정보</a></li>
              <li><a href="#sec-salary">2. 급여 & 4대보험</a></li>
              <li><a href="#sec-insurance">3. 4대보험 정보</a></li>
              <li><a href="#sec-family">4. 부양가족</a></li>
              <li><a href="#sec-edu">5. 학력 정보</a></li>
              <li><a href="#sec-career">6. 경력 정보</a></li>
              <li><a href="#sec-military">7. 병역 정보</a></li>
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
                <th>사원번호 <span class="required-mark">*</span></th>
                <td><input type="text" name="emp_no" required placeholder="No-140043"></td>
                <th>고용형태 <span class="required-mark">*</span></th>
                <td>
                  <select name="employment_type" required>
                    <option value="">선택해주세요</option>
                    <option value="정규직">정규직</option>
                    <option value="계약직">계약직</option>
                    <option value="일용직">일용직</option>
                  </select>
                </td>
              </tr>
              <tr>
                <th>성명(한글) <span class="required-mark">*</span></th>
                <td><input type="text" name="name_kor" required placeholder="홍길동"></td>
                <th>성명(영문)</th>
                <td><input type="text" name="name_eng" placeholder="Hong Gil Dong"></td>
              </tr>
              <tr>
                <th>입사일 <span class="required-mark">*</span></th>
                <td><input type="date" name="hire_date" required></td>
                <th>퇴사일</th>
                <td><input type="date" name="retire_date"></td>
              </tr>
              <tr>
                <th>부서</th>
                <td>
                  <div class="inline-inputs">
                    <select name="department">
                      <option value="">선택해주세요</option>
                      <option value="개발팀">개발팀</option>
                      <option value="인사팀">인사팀</option>
                      <option value="영업팀">영업팀</option>
                    </select>
                    <button type="button" class="btn btn-outline btn-sm" style="white-space: nowrap;">관리</button>
                  </div>
                </td>
                <th>직위</th>
                <td>
                  <div class="inline-inputs">
                    <select name="position">
                      <option value="">선택해주세요</option>
                      <option value="사원">사원</option>
                      <option value="대리">대리</option>
                      <option value="과장">과장</option>
                      <option value="부장">부장</option>
                    </select>
                    <button type="button" class="btn btn-outline btn-sm" style="white-space: nowrap;">관리</button>
                  </div>
                </td>
              </tr>
              <tr>
                <th>내/외국인</th>
                <td>
                  <select name="nationality">
                    <option value="내국인">내국인</option>
                    <option value="외국인">외국인</option>
                  </select>
                </td>
                <th>주민번호</th>
                <td><input type="text" name="resident_no" placeholder="000000-0000000"></td>
              </tr>
              <tr>
                <th>주소</th>
                <td colspan="3">
                  <div class="inline-inputs" style="margin-bottom: 6px;">
                    <input type="text" name="zip_code" style="width: 120px;" placeholder="우편번호">
                    <button type="button" class="btn btn-outline btn-sm">우편번호 검색</button>
                  </div>
                  <input type="text" name="address" placeholder="기본 주소 및 상세 주소 입력">
                </td>
              </tr>
              <tr>
                <th>전화번호</th>
                <td><input type="text" name="phone_num" placeholder="02-000-0000"></td>
                <th>휴대폰</th>
                <td><input type="text" name="mobile_num" placeholder="010-0000-0000"></td>
              </tr>
              <tr>
                <th>이메일</th>
                <td><input type="text" name="email" placeholder="example@hexagon.com"></td>
                <th>SNS</th>
                <td><input type="text" name="sns"></td>
              </tr>
              <tr>
                <th>기타사항</th>
                <td colspan="3"><textarea name="remarks" rows="3" placeholder="특이사항 입력"></textarea></td>
              </tr>
            </table>
          </div>

          <!-- 2. 급여 & 4대보험 -->
          <div class="form-section" id="sec-salary">
            <div class="section-head">
              <h3>💳 급여 & 4대보험</h3>
            </div>
            <table class="form-table">
              <tr>
                <th>4대보험 가입</th>
                <td colspan="3">
                  <div class="check-group">
                    <label><input type="checkbox" name="insurance_type" value="N" checked> 국민연금</label>
                    <label><input type="checkbox" name="insurance_type" value="H" checked> 건강보험</label>
                    <label><input type="checkbox" name="insurance_type" value="L" checked> 장기요양보험 포함</label>
                    <label><input type="checkbox" name="insurance_type" value="E" checked> 고용보험</label>
                  </div>
                </td>
              </tr>
              <tr>
                <th>갑근세 세액</th>
                <td colspan="3">
                  <div class="radio-group">
                    <label><input type="radio" name="income_tax" value="100" checked> 근로소득자(100%)</label>
                    <label><input type="radio" name="income_tax" value="80"> 80%</label>
                    <label><input type="radio" name="income_tax" value="120"> 120%</label>
                    <label><input type="radio" name="income_tax" value="3.3"> 사업소득자(3.3%)</label>
                    <label><input type="radio" name="income_tax" value="2.97"> 일용직(2.97%)</label>
                  </div>
                </td>
              </tr>
              <tr>
                <th>두루누리 지원</th>
                <td colspan="3">
                  <div class="radio-group">
                    <label><input type="radio" name="durunuri" value="NONE" checked> 해당없음</label>
                    <label><input type="radio" name="durunuri" value="80"> 신규가입자(80% 지원)</label>
                    <label><input type="radio" name="durunuri" value="90"> 신규가입자(90% 지원)</label>
                  </div>
                </td>
              </tr>
              <tr>
                <th>기본급 / 일급 <span class="required-mark">*</span></th>
                <td><input type="text" name="base_salary" required placeholder="원 (숫자만 입력)"></td>
                <th>급여 수령 계좌</th>
                <td>
                  <div class="inline-inputs">
                    <select name="salary_bank" style="width: 110px;">
                      <option value="">은행선택</option>
                      <option value="국민">국민</option>
                      <option value="신한">신한</option>
                      <option value="우리">우리</option>
                      <option value="하나">하나</option>
                    </select>
                    <input type="text" name="salary_account" placeholder="계좌번호">
                    <button type="button" class="btn btn-outline btn-sm" style="white-space: nowrap;">예금주 조회</button>
                  </div>
                </td>
              </tr>
              <tr>
                <th>국민연금 기준소득월액</th>
                <td><input type="text" name="pension_salary" placeholder="원"></td>
                <th>건강보험 보수월액</th>
                <td><input type="text" name="health_salary" placeholder="원"></td>
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
                  <th>관리 번호</th>
                  <th>취득일자</th>
                  <th>상실일자</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <th>국민연금</th>
                  <td><input type="text" name="national_no"></td>
                  <td><input type="date" name="national_acq"></td>
                  <td><input type="date" name="national_loss"></td>
                </tr>
                <tr>
                  <th>건강보험</th>
                  <td><input type="text" name="health_no"></td>
                  <td><input type="date" name="health_acq"></td>
                  <td><input type="date" name="health_loss"></td>
                </tr>
                <tr>
                  <th>고용보험</th>
                  <td><input type="text" name="employment_no"></td>
                  <td><input type="date" name="employment_acq"></td>
                  <td><input type="date" name="employment_loss"></td>
                </tr>
                <tr>
                  <th>산재보험</th>
                  <td><input type="text" name="industrial_no"></td>
                  <td><input type="date" name="industrial_acq"></td>
                  <td><input type="date" name="industrial_loss"></td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- 4. 부양가족 -->
          <div class="form-section" id="sec-family">
            <div class="section-head">
              <h3>👨‍👩‍👧‍👦 부양가족 정보</h3>
              <div>
                <button type="button" class="btn btn-outline btn-sm" onclick="addFamilyRow()">+ 행 추가</button>
              </div>
            </div>
            <table class="data-table" id="familyTable">
              <thead>
                <tr>
                  <th width="40"><input type="checkbox" onclick="toggleAll(this, 'fam-chk')"></th>
                  <th>관계</th>
                  <th>성명</th>
                  <th>주민등록번호</th>
                  <th>장애여부</th>
                  <th>인적공제</th>
                  <th>20세이하 자녀</th>
                  <th>삭제</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><input type="checkbox" class="fam-chk"></td>
                  <td>
                    <select name="relation">
                      <option value="배우자">배우자</option>
                      <option value="자녀">자녀</option>
                      <option value="부모">부모</option>
                    </select>
                  </td>
                  <td><input type="text" name="family_name"></td>
                  <td><input type="text" name="resident_no"></td>
                  <td><input type="checkbox" name="disability" value="Y"></td>
                  <td><input type="checkbox" name="deduction" value="Y" checked></td>
                  <td><input type="checkbox" name="child_age" value="Y"></td>
                  <td><button type="button" class="btn btn-outline btn-sm" onclick="removeRow(this)">삭제</button></td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- 5. 학력 정보 -->
          <div class="form-section" id="sec-edu">
            <div class="section-head">
              <h3>🎓 학력 정보</h3>
              <div>
                <button type="button" class="btn btn-outline btn-sm" onclick="addEduRow()">+ 행 추가</button>
              </div>
            </div>
            <table class="data-table" id="eduTable">
              <thead>
                <tr>
                  <th>구분</th>
                  <th>입학년월</th>
                  <th>졸업년월</th>
                  <th>학교명</th>
                  <th>전공</th>
                  <th>이수 상태</th>
                  <th>삭제</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                    <select name="education_type">
                      <option value="고등학교">고등학교</option>
                      <option value="대학교(4년)">대학교(4년)</option>
                      <option value="대학원">대학원</option>
                    </select>
                  </td>
                  <td><input type="date" name="admission_date"></td>
                  <td><input type="date" name="graduation_date"></td>
                  <td><input type="text" name="school_name"></td>
                  <td><input type="text" name="major"></td>
                  <td>
                    <select name="status">
                      <option value="졸업">졸업</option>
                      <option value="재학">재학</option>
                      <option value="중퇴">중퇴</option>
                    </select>
                  </td>
                  <td><button type="button" class="btn btn-outline btn-sm" onclick="removeRow(this)">삭제</button></td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- 6. 경력 정보 -->
          <div class="form-section" id="sec-career">
            <div class="section-head">
              <h3>💼 경력 정보</h3>
              <div>
                <button type="button" class="btn btn-outline btn-sm" onclick="addCareerRow()">+ 행 추가</button>
              </div>
            </div>
            <table class="data-table" id="careerTable">
              <thead>
                <tr>
                  <th>회사명</th>
                  <th>입사일자</th>
                  <th>퇴사일자</th>
                  <th>최종직위</th>
                  <th>담당직무</th>
                  <th>퇴직사유</th>
                  <th>삭제</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><input type="text" name="company_name"></td>
                  <td><input type="date" name="hire_date"></td>
                  <td><input type="date" name="retire_date"></td>
                  <td><input type="text" name="last_position"></td>
                  <td><input type="text" name="position"></td>
                  <td><input type="text" name="retire_reason"></td>
                  <td><button type="button" class="btn btn-outline btn-sm" onclick="removeRow(this)">삭제</button></td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- 7. 병역 정보 -->
          <div class="form-section" id="sec-military">
            <div class="section-head">
              <h3>🪖 병역 정보</h3>
            </div>
            <table class="form-table">
              <tr>
                <th>병역구분</th>
                <td>
                  <select name="military_type">
                    <option value="필">군필</option>
                    <option value="미필">미필</option>
                    <option value="면제">면제</option>
                  </select>
                </td>
                <th>군별</th>
                <td>
                  <select name="military_branch">
                    <option value="육군">육군</option>
                    <option value="해군">해군</option>
                    <option value="공군">공군</option>
                    <option value="해병대">해병대</option>
                  </select>
                </td>
              </tr>
              <tr>
                <th>복무기간</th>
                <td colspan="3">
                  <div class="inline-inputs" style="width: 400px;">
                    <input type="date" name="start_date">
                    <span>~</span>
                    <input type="date" name="end_date">
                  </div>
                </td>
              </tr>
              <tr>
                <th>최종계급</th>
                <td><input type="text" name="final_rank" placeholder="예: 병장"></td>
                <th>병과</th>
                <td><input type="text" name="specialty" placeholder="예: 보병"></td>
              </tr>
              <tr>
                <th>미필/면제 사유</th>
                <td colspan="3"><input type="text" name="exemption_reason"></td>
              </tr>
            </table>
          </div>

          <!-- 하단 액션 버튼 -->
          <div class="bottom-actions">
            <button type="submit" class="btn btn-primary" style="padding: 10px 30px; font-size: 15px;">💾 저장하기</button>
            <button type="reset" class="btn btn-secondary" style="padding: 10px 24px; font-size: 15px;">취소</button>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline" style="padding: 10px 24px; font-size: 15px;">📋 목록으로</a>
          </div>

        </div>
      </div>
    </form>
  </div>

  <!-- 푸터 태그 -->
  <layout:footer />

  <!-- 동적 행 추가/삭제 스크립트 -->
  <script>
    function removeRow(btn) {
      const row = btn.closest('tr');
      row.remove();
    }

    function addFamilyRow() {
      const tbody = document.querySelector('#familyTable tbody');
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td><input type="checkbox" class="fam-chk"></td>
        <td>
          <select name="relation">
            <option value="배우자">배우자</option>
            <option value="자녀">자녀</option>
            <option value="부모">부모</option>
          </select>
        </td>
        <td><input type="text" name="family_name"></td>
        <td><input type="text" name="resident_no"></td>
        <td><input type="checkbox" name="disability" value="Y"></td>
        <td><input type="checkbox" name="deduction" value="Y" checked></td>
        <td><input type="checkbox" name="child_age" value="Y"></td>
        <td><button type="button" class="btn btn-outline btn-sm" onclick="removeRow(this)">삭제</button></td>
      `;
      tbody.appendChild(tr);
    }

    function addEduRow() {
      const tbody = document.querySelector('#eduTable tbody');
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>
          <select name="education_type">
            <option value="고등학교">고등학교</option>
            <option value="대학교(4년)">대학교(4년)</option>
            <option value="대학원">대학원</option>
          </select>
        </td>
        <td><input type="date" name="admission_date"></td>
        <td><input type="date" name="graduation_date"></td>
        <td><input type="text" name="school_name"></td>
        <td><input type="text" name="major"></td>
        <td>
          <select name="status">
            <option value="졸업">졸업</option>
            <option value="재학">재학</option>
            <option value="중퇴">중퇴</option>
          </select>
        </td>
        <td><button type="button" class="btn btn-outline btn-sm" onclick="removeRow(this)">삭제</button></td>
      `;
      tbody.appendChild(tr);
    }

    function addCareerRow() {
      const tbody = document.querySelector('#careerTable tbody');
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td><input type="text" name="company_name"></td>
        <td><input type="date" name="hire_date"></td>
        <td><input type="date" name="retire_date"></td>
        <td><input type="text" name="last_position"></td>
        <td><input type="text" name="position"></td>
        <td><input type="text" name="retire_reason"></td>
        <td><button type="button" class="btn btn-outline btn-sm" onclick="removeRow(this)">삭제</button></td>
      `;
      tbody.appendChild(tr);
    }

    function toggleAll(master, chkClass) {
      const checkboxes = document.querySelectorAll('.' + chkClass);
      checkboxes.forEach(cb => cb.checked = master.checked);
    }
  </script>

</body>
</html>