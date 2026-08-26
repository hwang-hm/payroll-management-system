<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>ヘキサゴンゾーン - 人事・給与管理システム</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
  
  <style>
    /* メインページ専用スタイル */
    .dashboard-container {
      max-width: 1200px;
      margin: 0 auto 50px auto;
      padding: 0 20px;
      font-family: 'Pretendard Variable', Pretendard, -apple-system, sans-serif;
      color: #334155;
    }

    /* 1. ヒーローメインバナー */
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

    /* 2. ダッシュボード要約カード */
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

    /* 3. クイックメニュー (主要リンク) */
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

    /* 4. 下段2列情報レイアウト */
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

  <!-- ヘッダータグ -->
  <layout:headerNav />

  <!-- 本文エリア開始 -->
  <main class="dashboard-container">
    
    <!-- 1. メインヒーローエリア -->
    <div class="hero-section">
      <div class="hero-text">
        <h1>スマートな人事・給与管理の始まり 🚀</h1>
        <h1>こちらは new_update Gitテスト中です</h1>
        <p>ヘキサゴンゾーンでスピーディーかつ正確に業務を処理しましょう。</p>
      </div>
      <div class="team-badge">
        <strong>第2チーム</strong> (チームリーダー : イ・ギジョン)<br/>
        キム・ユンジ · アン・ビョンジュン · イ・イェビン · ファン・ヒョンミン
      </div>
    </div>

    <!-- 2. 要約指標カード -->
    <div class="stats-grid">
      <div class="stat-card">
        <div class="title">総在職社員数</div>
        <div class="value">48名</div>
        <div class="sub-text">↑ 先月比 +2名</div>
      </div>
      <div class="stat-card">
        <div class="title">当月給与支給額</div>
        <div class="value">₩ 142.5M</div>
        <div class="sub-text">定期支給完了</div>
      </div>
      <div class="stat-card">
        <div class="title">今月の勤怠処理率</div>
        <div class="value">98.2%</div>
        <div class="sub-text">正常出退勤記録</div>
      </div>
      <div class="stat-card">
        <div class="title">退職/精算申請</div>
        <div class="value">1件</div>
        <div class="sub-text" style="color: #f59e0b;">処理待ち</div>
      </div>
    </div>

    <!-- 3. よく使うメニュー (Quick Links) -->
    <h2 class="section-title">クイックメニュー</h2>
    <div class="quick-menu-grid">
      <a href="${pageContext.request.contextPath}/config/register.do" class="quick-btn">
        <span class="icon">👤</span>
        <span>新規社員登録</span>
      </a>
      <a href="${pageContext.request.contextPath}/emp/list.do" class="quick-btn">
        <span class="icon">📋</span>
        <span>社員一覧照会</span>
      </a>
      <a href="${pageContext.request.contextPath}/attendance/record/list.do" class="quick-btn">
        <span class="icon">⏰</span>
        <span>勤怠管理</span>
      </a>
      <a href="${pageContext.request.contextPath}/salary/entry.do" class="quick-btn">
        <span class="icon">💳</span>
        <span>給与計算・支給</span>
      </a>
      <a href="${pageContext.request.contextPath}/salary/stats/yearlyTotal.do" class="quick-btn">
        <span class="icon">📊</span>
        <span>給与統計分析</span>
      </a>
      <a href="${pageContext.request.contextPath}/retirement/list.do" class="quick-btn">
        <span class="icon">🏖️</span>
        <span>退職管理</span>
      </a>
    </div>

    <!-- 4. お知らせ / システム情報 -->
    <div class="content-split">
      <div class="panel">
        <div class="panel-header">
          <h3>システムお知らせ</h3>
        </div>
        <ul class="notice-list">
          <li class="notice-item">
            <a href="#">[案内] 今月の定期給与計算基準および変更事項のご案内</a>
            <span class="date">2026.07.25</span>
          </li>
          <li class="notice-item">
            <a href="#">[アップデート] 社員管理システムUI改善および速度向上適用</a>
            <span class="date">2026.07.18</span>
          </li>
          <li class="notice-item">
            <a href="#">[必読] 下半期有給休暇取得促進制度の実施に関するご案内</a>
            <span class="date">2026.07.10</span>
          </li>
        </ul>
      </div>

      <div class="panel" style="background-color: #f8fafc;">
        <div class="panel-header">
          <h3>チーム情報</h3>
        </div>
        <p style="font-size: 13px; color: #64748b; line-height: 1.6;">
          <strong>プロジェクト担当:</strong> 第2チーム<br/>
          <strong>チームリーダー:</strong> イ・ギジョン<br/>
          <strong>チームメンバー:</strong> キム・ユンジ、アン・ビョンジュン、イ・イェビン、ファン・ヒョンミン
        </p>
      </div>
    </div>

  </main>
  <!-- 本文エリア終了 -->

  <!-- フッタータグ -->
  <layout:footer />

</body>
</html>