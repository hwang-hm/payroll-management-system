<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 푸터 및 바닥 고정을 위한 글로벌 스타일 설정 -->
<style>
  /* 화면 전체 높이를 확보하여 푸터를 바닥으로 밀어내기 위한 기본 설정 */
  html {
    height: 100%;
  }

  body {
    min-height: 100vh;
    display: flex !important;
    flex-direction: column !important;
    margin: 0;
  }

  /* 푸터 전용 스타일 */
  .payzon-footer {
    width: 100%;
    background-color: #f8fafc;
    border-top: 1px solid #e2e8f0;
    padding: 20px 0;
    margin-top: auto !important; /* 상단 여백을 자동으로 채워 화면 최하단으로 밀어냄 */
    text-align: center;
  }

  .payzon-footer .footer-container {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0 20px;
  }

  .payzon-footer img {
    max-width: 100%;
    height: auto;
    display: block;
  }
</style>

<!-- 푸터 HTML 영역 -->
<footer class="payzon-footer">
  <div class="footer-container">
    <img src="${pageContext.request.contextPath}/images/bottom.gif" alt="PAYZON 하단 푸터 정보" />
  </div>
</footer>