<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 화면 바닥 고정(Sticky Footer) 적용 CSS -->
<style>
  html, body {
    margin: 0;
    padding: 0;
    min-height: 100vh;
  }

  .payzon-footer {
    position: sticky;
    top: 100vh; /* 내용이 적어도 화면 맨 밑 바닥에 밀착 고정 */
    width: 100%;
    background-color: #f8fafc;
    border-top: 1px solid #e2e8f0;
    padding: 20px 0;
    margin-top: 50px;
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
    <img src="${pageContext.request.contextPath}/images/FO.png" alt="PAYZON 하단 푸터 정보" />
  </div>
</footer>