<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 헤더 전용 CSS 스타일을 태그 파일 안에 포함 -->
<style>
  /* 1. 기본 리셋 및 폰트 */
  .payzon-header * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    font-family: 'Pretendard Variable', Pretendard, -apple-system, sans-serif;
  }

  /* 2. 최상단 배너 (붉은색) */
  .payzon-header .top-banner {
    background-color: #e11d48;
    color: #ffffff;
    font-size: 12px;
    padding: 6px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  /* 3. 헤더 로고 영역 (다크블루) */
  .payzon-header .header-top {
    background-color: #2b3648;
    color: #ffffff;
    padding: 12px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .payzon-header .brand-logo {
    font-size: 20px;
    font-weight: 800;
    color: #ffffff;
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .payzon-header .brand-logo span {
    color: #38bdf8;
    font-size: 14px;
    font-weight: 600;
  }

  .payzon-header .user-info {
    font-size: 12px;
    color: #cbd5e1;
    display: flex;
    gap: 12px;
    align-items: center;
  }

  .payzon-header .user-info a {
    color: #ffffff;
    text-decoration: none;
  }

  /* 4. 빠른 실행 메뉴 */
  .payzon-header .quick-menu {
    background-color: #ffffff;
    border-bottom: 1px solid #cbd5e1;
    padding: 10px 20px;
    display: flex;
    gap: 24px;
    overflow-x: auto;
  }

  .payzon-header .quick-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-decoration: none;
    color: #334155;
    font-size: 12px;
    font-weight: 600;
    gap: 4px;
    cursor: pointer;
  }

  .payzon-header .quick-icon {
    width: 32px;
    height: 32px;
    background-color: #f1f5f9;
    border-radius: 6px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 16px;
  }

  /* 5. 파란색 GNB 네비게이션 */
  .payzon-header .gnb-navbar {
    background-color: #2563eb;
    display: flex;
    padding: 0 10px;
    position: relative;
    z-index: 999;
  }

  .payzon-header .gnb-item {
    position: relative;
  }

  .payzon-header .gnb-link {
    display: block;
    padding: 12px 22px;
    color: #ffffff;
    text-decoration: none;
    font-size: 14px;
    font-weight: 600;
    transition: background 0.2s ease;
  }

  .payzon-header .gnb-item:hover .gnb-link {
    background-color: #1d4ed8;
  }

  /* 6. 마우스 올리면 나오는 서브 드롭다운 */
  .payzon-header .sub-dropdown {
    display: none;
    position: absolute;
    top: 100%;
    left: 0;
    min-width: 160px;
    background-color: #1e40af;
    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
    border-radius: 0 0 6px 6px;
    overflow: hidden;
  }

  .payzon-header .sub-dropdown a {
    display: block;
    padding: 10px 16px;
    color: #ffffff;
    text-decoration: none;
    font-size: 13px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    transition: background 0.2s ease, color 0.2s ease;
  }

  .payzon-header .sub-dropdown a:hover {
    background-color: #1e3a8a;
    color: #93c5fd;
  }

  .payzon-header .gnb-item:hover .sub-dropdown {
    display: block;
  }
</style>

<!-- 헤더 전체 감싸는 태그 -->
<header class="payzon-header">
  <!-- 1. 최상단 배너 -->
  <div class="top-banner">
    <span>★ 현재 고객님은 체험판 이용중 이십니다.</span>
  </div>

  <!-- 2. 브랜드 로고 영역 -->
  <div class="header-top">
    <div class="brand-logo">
      헥사곤존 <span>인사&급여관리</span>
    </div>
    <div class="user-info">
      <span>고객님 무료체험회원 입니다.</span>
    </div>
  </div>


  <!-- 3. 메인 네비게이션 바 -->
  <nav class="gnb-navbar">
  
    <!-- 0)  -->
    <div class="gnb-item">
      <a href="#" class="gnb-link">🏠 HOME</a>
    </div>  
  
    <!-- 1) 인사관리 (사원등록, 사원현황 서브메뉴) -->
    <div class="gnb-item">
      <a href="#" class="gnb-link">인사관리 ▾</a>
      <div class="sub-dropdown">
        <a href="${pageContext.request.contextPath}/emp/register.do">사원등록</a>
        <a href="${pageContext.request.contextPath}/emp/list.do">사원현황</a>
      </div>
    </div>

    <!-- 2) 근태관리 -->
    <div class="gnb-item">
      <a href="${pageContext.request.contextPath}/attendance/list.do" class="gnb-link">근태관리 ▾</a>
    </div>

    <!-- 3) 급여관리 -->
    <div class="gnb-item">
      <a href="${pageContext.request.contextPath}/salary/list.do" class="gnb-link">급여관리 ▾</a>
    </div>

    <!-- 4) 급여통계 -->
    <div class="gnb-item">
      <a href="${pageContext.request.contextPath}/stats/salary.do" class="gnb-link">급여통계 ▾</a>
    </div>

    <!-- 5) 퇴직관리 -->
    <div class="gnb-item">
      <a href="${pageContext.request.contextPath}/retire/list.do" class="gnb-link">퇴직관리 ▾</a>
    </div>
  </nav>
</header>

<!-- ★ 이 줄을 headerNav.tag 맨 밑에 추가해 주세요! (헤더와 본문 사이 여백 확보) -->
<div style="margin-bottom: 30px;"></div>