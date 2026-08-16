<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>헥사곤존 - 인사&급여관리 시스템 </title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
  
  <style>
    /* 메인 페이지 전용 스타일 */
    .dashboard-container {
      max-width: 1200px;
      margin: 0 auto 50px auto;
      padding: 0 20px;
      font-family: 'Pretendard Variable', Pretendard, -apple-system, sans-serif;
      color: #334155;
    }

    /* 1. 히어로 메인 히어로 배너 */
    .hero-section {
      background: linear-gradient(135deg, #1e3a8a 0%, #2563eb 100%);
      color: #ffffff;
      border-radius: 16px;
      padding: 36px 40px;
      margin-bottom: 30px;
      box-shadow: 0 10px 25px -5px rgba(37, 99, 235, 0.25);
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .hero-text h1 {
      font-size: 26px;
      font-weight: 700;
      margin-bottom: 8px;
    }

    .hero-text p {
      font-size: 15px;
      color: #bfdbfe;
      margin: 0;
    }

    .team-badge {
      background: rgba(255, 255, 255, 0.15);
      backdrop-filter: blur(8px);
      padding: 12px 20px;
      border-radius: 12px;
      border: 1px solid rgba(255, 255, 255, 0.2);
      font-size: 13px;
    }

    .team-badge strong {
      color: #93c5fd;
    }

    /* 2. 대시보드 요약 카드 */
    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
      gap: 20px;
      margin-bottom: 30px;
    }

    .stat-card {
      background: #ffffff;
      border: 1px solid #e2e8f0;
      border-radius: 14px;
      padding: 24px;
      transition: transform 0.2s ease, box-shadow 0.2s ease;
    }

    .stat-card:hover {
      transform: translateY(-4px);
      box-shadow: 0 12px 20px -5px rgba(0, 0, 0, 0.05);
    }

    .stat-card .title {
      font-size: 13px;
      color: #64748b;
      font-weight: 600;
      margin-bottom: 12px;
    }

    .stat-card .value {
      font-size: 28px;
      font-weight: 800;
      color: #0f172a;
    }

    .stat-card .sub-text {
      font-size: 12px;
      color: #10b981;
      margin-top: 8px;
      font-weight: 600;
    }

    /* 3. 퀵 메뉴 (주요 링크) */
    .section-title {
      font-size: 18px;
      font-weight: 700;
      color: #1e293b;
      margin-bottom: 16px;
    }

    .quick-menu-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
      gap: 16px;
      margin-bottom: 35px;
    }

    .quick-btn {
      background-color: #ffffff;
      border: 1px solid #e2e8f0;
      border-radius: 12px;
      padding: 20px 16px;
      text-align: center;
      text-decoration: none;
      color: #334155;
      font-weight: 600;
      font-size: 14px;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 10px;
      transition: all 0.2s ease;
    }

    .quick-btn:hover {
      border-color: #2563eb;
      background-color: #eff6ff;
      color: #2563eb;
    }

    .quick-btn .icon {
      font-size: 24px;
    }

    /* 4. 하단 2열 정보 레이아웃 */
    .content-split {
      display: grid;
      grid-template-columns: 2fr 1fr;
      gap: 20px;
    }

    .panel {
      background: #ffffff;
      border: 1px solid #e2e8f0;
      border-radius: 14px;
      padding: 24px;
    }

    .panel-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 16px;
    }

    .panel-header h3 {
      font-size: 16px;
      font-weight: 700;
      margin: 0;
    }

    .notice-list {
      list-style: none;
      padding: 0;
      margin: 0;
    }

    .notice-item {
      display: flex;
      justify-content: space-between;
      padding: 12px 0;
      border-bottom: 1px solid #f1f5f9;
      font-size: 14px;
    }

    .notice-item:last-child {
      border-bottom: none;
    }

    .notice-item a {
      color: #475569;
      text-decoration: none;
    }

    .notice-item a:hover {
      color: #2563eb;
    }

    .notice-item .date {
      color: #94a3b8;
      font-size: 12px;
    }
  </style>
</head>
<body>

  <!-- 헤더 태그 -->
  <layout:headerNav />

  <!-- 본문 영역 시작 -->
  <main class="dashboard-container">
    
    <!-- 1. 메인 히어로 영역 -->
    <div class="hero-section">
      <div class="hero-text">
        <h1>스마트한 인사·급여 관리의 시작 🚀</h1>
        <h1>여기는 new_update 깃 테스트중입니다</h1>
        <p>헥사곤존에서 빠르고 정확하게 업무를 처리하세요.</p>
      </div>
      <div class="team-badge">
        <strong>2팀</strong> (팀장 : 이기정)<br/>
        김윤지 · 안병준 · 이예빈 · 황현민
      </div>
    </div>

    <!-- 2. 요약 지표 카드 -->
    <div class="stats-grid">
      <div class="stat-card">
        <div class="title">총 재직 사원</div>
        <div class="value">48명</div>
        <div class="sub-text">↑ 지난달 대비 +2명</div>
      </div>
      <div class="stat-card">
        <div class="title">당월 급여 집행액</div>
        <div class="value">₩ 142.5M</div>
        <div class="sub-text">정기 지급 완료</div>
      </div>
      <div class="stat-card">
        <div class="title">이번 달 근태 처리</div>
        <div class="value">98.2%</div>
        <div class="sub-text">정상 출퇴근 기록</div>
      </div>
      <div class="stat-card">
        <div class="title">퇴직/정산 신청</div>
        <div class="value">1건</div>
        <div class="sub-text" style="color: #f59e0b;">처리 대기 중</div>
      </div>
    </div>

    <!-- 3. 자주 찾는 메뉴 (Quick Links) -->
    <h2 class="section-title">빠른 실행 메뉴</h2>
    <div class="quick-menu-grid">
      <a href="${pageContext.request.contextPath}/config/register.do" class="quick-btn">
        <span class="icon">👤</span>
        <span>신규 사원 등록</span>
      </a>
      <a href="${pageContext.request.contextPath}/emp/list.do" class="quick-btn">
        <span class="icon">📋</span>
        <span>사원 현황 조회</span>
      </a>
      <a href="${pageContext.request.contextPath}/attendance/list.do" class="quick-btn">
        <span class="icon">⏰</span>
        <span>근태 관리</span>
      </a>
      <a href="${pageContext.request.contextPath}/salary/list.do" class="quick-btn">
        <span class="icon">💳</span>
        <span>급여 계산/지급</span>
      </a>
      <a href="${pageContext.request.contextPath}/stats/salary.do" class="quick-btn">
        <span class="icon">📊</span>
        <span>급여 통계 분석</span>
      </a>
      <a href="${pageContext.request.contextPath}/retirement/list.do" class="quick-btn">
        <span class="icon">🏖️</span>
        <span>퇴직 관리</span>
      </a>
    </div>

    <!-- 4. 공지사항 / 시스템 정보 -->
    <div class="content-split">
      <div class="panel">
        <div class="panel-header">
          <h3>시스템 공지사항</h3>
        </div>
        <ul class="notice-list">
          <li class="notice-item">
            <a href="#">[안내] 이번 달 정기 급여 계산 기준 및 변경 사항 안내</a>
            <span class="date">2026.07.25</span>
          </li>
          <li class="notice-item">
            <a href="#">[업데이트] 사원 관리 시스템 UI 개선 및 속도 향상 적용</a>
            <span class="date">2026.07.18</span>
          </li>
          <li class="notice-item">
            <a href="#">[필독] 하반기 연차 사용 촉진 제도 실행 관련 안내</a>
            <span class="date">2026.07.10</span>
          </li>
        </ul>
      </div>

      <div class="panel" style="background-color: #f8fafc;">
        <div class="panel-header">
          <h3>팀 정보</h3>
        </div>
        <p style="font-size: 13px; color: #64748b; line-height: 1.6;">
          <strong>프로젝트 담당:</strong> 2팀<br/>
          <strong>팀장:</strong> 이기정<br/>
          <strong>팀원:</strong> 김윤지, 안병준, 이예빈, 황현민
        </p>
      </div>
    </div>

  </main>
  <!-- 본문 영역 끝 -->

  <!-- 푸터 태그 -->
  <layout:footer />

</body>
</html>
