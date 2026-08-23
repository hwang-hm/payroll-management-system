<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 헤더 전용 CSS 스타일 -->
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
	padding: 30px 50px;
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

/* 4. 파란색 GNB 네비게이션 (페이존 스타일 균등 분할 적용) */
.payzon-header .gnb-navbar {
	background-color: #1d4ed8;
	display: flex;
	width: 100%;
	position: relative;
	z-index: 999;
	border-bottom: 2px solid #1e40af;
}

/* 개별 메뉴 항목: flex: 1을 부여해 전체 폭을 동일하게 나누어 가집니다. */
.payzon-header .gnb-item {
	flex: 1;
	position: relative;
	border-right: 1px solid rgba(255, 255, 255, 0.2);
	text-align: center;
}

.payzon-header .gnb-item:last-child {
	border-right: none;
}

/* HOME 전용 약간 좁은 비율 설정 (원할 경우 주석 해제하여 조절 가능) */
.payzon-header .gnb-item.home-item {
	flex: 0.6;
}

.payzon-header .gnb-link {
	display: block;
	padding: 12px 0;
	color: #ffffff;
	text-decoration: none;
	font-size: 14px;
	font-weight: 700;
	width: 100%;
	transition: background 0.2s ease;
}

.payzon-header .gnb-item:hover .gnb-link {
	background-color: #1e40af;
}

/* 5. 마우스 올리면 나오는 서브 드롭다운 (상위 메뉴 너비와 100% 동일하게 맞춰짐) */
.payzon-header .sub-dropdown {
	display: none;
	position: absolute;
	top: 100%;
	left: 0;
	width: 100%;
	background-color: #1e3a8a;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
	border-radius: 0 0 4px 4px;
	overflow: hidden;
}

.payzon-header .sub-dropdown a {
	display: block;
	padding: 10px 8px;
	color: #ffffff;
	text-decoration: none;
	font-size: 13px;
	text-align: center;
	border-bottom: 1px solid rgba(255, 255, 255, 0.1);
	transition: background 0.2s ease, color 0.2s ease;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
}

.payzon-header .sub-dropdown a:hover {
	background-color: #1d4ed8;
	color: #93c5fd;
	font-weight: 600;
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
			헥사존 <span>인사&급여관리</span>
		</div>
		<div class="user-info">
			<span>고객님 무료체험회원 입니다.</span>
		</div>
	</div>

	<!-- 3. 메인 네비게이션 바 -->
	<nav class="gnb-navbar">
		<div class="gnb-item home-item">
			<a href="${pageContext.request.contextPath}/index.jsp"
				class="gnb-link">🏠 HOME</a>
		</div>

		<!-- 기본환경 설정 -->
		<div class="gnb-item">
			<a href="#" class="gnb-link">기본환경 설정 ▾</a>
			<div class="sub-dropdown"> 
				<a href="${pageContext.request.contextPath}/config/register.do">사원 등록</a>
				<a href="${pageContext.request.contextPath}/config/vacation-attendance.do">휴가/근태 설정</a>
				<a href="${pageContext.request.contextPath}/config/salaryItem.do">급여항목 설정</a>
			</div>
		</div>

		<!-- 인사관리 -->
		<div class="gnb-item">
			<a href="#" class="gnb-link">인사관리 ▾</a>
			<div class="sub-dropdown">
				<a href="${pageContext.request.contextPath}/emp/list.do">사원현황/관리</a>
				<a href="${pageContext.request.contextPath}/emp/empCard.do">인사기록 카드</a>
				<a href="${pageContext.request.contextPath}/emp/certificate/working.do">제증명서 발급</a> 
				<a href="${pageContext.request.contextPath}/emp/certificate/list.do">제증명서 발급대장</a>
			</div>
		</div>

		<!-- 근태관리 -->
		<div class="gnb-item">
			<a href="#" class="gnb-link">근태관리 ▾</a>
			<div class="sub-dropdown">
				<a href="${pageContext.request.contextPath}/attendance/record/list.do">근태기록/관리</a>
				<a href="${pageContext.request.contextPath}/attendance/list.do">근태조회</a>
				<a href="${pageContext.request.contextPath}/attendance/vacation.do">휴가조회</a>
				<a href="${pageContext.request.contextPath}/attendance/daily/record.do">일용직근무기록/관리</a> 
				<a href="${pageContext.request.contextPath}/attendance/daily/list.do">일용직근무 조회</a>
			</div>
		</div>

		<!-- 급여관리 -->
		<div class="gnb-item">
			<a href="#" class="gnb-link">급여관리 ▾</a>
			<div class="sub-dropdown">
				<a href="${pageContext.request.contextPath}/salary/entry.do">급여입력/관리</a>
				<a href="${pageContext.request.contextPath}/salary/dayworker/register.do">급여입력/관리(일용직)</a>
				<a href="${pageContext.request.contextPath}/salary/ledger.do">급여대장</a>
				<a href="${pageContext.request.contextPath}/salary/payslip.do">급여명세서</a>
				<a href="${pageContext.request.contextPath}/salary/empHistory.do">사원별급여내역</a> 
				<a href="${pageContext.request.contextPath}/salary/transfer/apply.do">급여이체신청</a> 
				<a href="${pageContext.request.contextPath}/salary/transfer/result.do">급여이체결과조회</a> 
				<a href="${pageContext.request.contextPath}/salary/itemRegister.do">항목별대장</a> 
				<a href="${pageContext.request.contextPath}/salary/socialInsurance.do">4대보험공제내역</a>
			</div>
		</div>

		<!-- 급여통계 -->
		<div class="gnb-item">
			<a href="#" class="gnb-link">급여통계 ▾</a>
			<div class="sub-dropdown">
				<a href="${pageContext.request.contextPath}/salary/stats/yearlyTotal.do">연도별전체급여 통계</a> 
				<a href="${pageContext.request.contextPath}/salary/stats/monthlyTotal.do">월별전체급여 통계</a> 
				<a href="${pageContext.request.contextPath}/salary/stats/yearlyPersonal.do">연도별개인연봉 통계</a> 
				<a href="${pageContext.request.contextPath}/salary/stats/monthlyPersonal.do">월별개인급여 통계</a> 
				<a href="${pageContext.request.contextPath}/salary/stats/itemComposition.do">급여항목구성 통계</a>
			</div>
		</div>

		<!-- 퇴직관리 -->
		<div class="gnb-item">
			<a href="#" class="gnb-link">퇴직관리 ▾</a>
			<div class="sub-dropdown">
				<a href="${pageContext.request.contextPath}/retirement/list.do">사원 퇴직처리</a> 
				<a href="${pageContext.request.contextPath}/retirement/severance.do">퇴직급여 입력/관리</a> 
				<a href="${pageContext.request.contextPath}/retirement/specification.do">퇴직급여명세서</a>
			</div>
		</div>
	</nav>
</header>

<!-- 헤더와 본문 사이 기본 여백 -->
<div style="margin-bottom: 30px;"></div>
