<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- ヘッダー専用 CSS スタイル -->
<style>
/* 1. 基本リセットおよびフォント */
.payzon-header * {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
	font-family: 'Pretendard Variable', Pretendard, -apple-system, sans-serif;
}

/* 2. 最上段バナー (赤色) */
.payzon-header .top-banner {
	background-color: #e11d48;
	color: #ffffff;
	font-size: 12px;
	padding: 6px 20px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

/* 3. ヘッダーロゴおよびユーザー情報エリア */
.payzon-header .header-top {
	background-color: #2b3648;
	color: #ffffff;
	padding: 15px 30px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.payzon-header .brand-logo {
	font-size: 22px;
	font-weight: 800;
	color: #ffffff;
	display: flex;
	align-items: center;
	gap: 8px;
}

.payzon-header .brand-logo span {
	color: #38bdf8;
	font-size: 15px;
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

/* 4. ショートカットエリア (横100% 均等分割) */
.payzon-header .shortcut-bar {
	background-color: #ffffff;
	border-bottom: 1px solid #cbd5e1;
	display: flex;
	width: 100%;
}

.payzon-header .shortcut-item {
	flex: 1; /* すべての項目が横幅を 1:1 比率で均等分割 */
	border-right: 1px solid #e2e8f0;
	text-align: center;
}

.payzon-header .shortcut-item:last-child {
	border-right: none;
}

.payzon-header .shortcut-link {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 12px 2px;
	text-decoration: none;
	color: #334155;
	width: 100%;
	transition: background-color 0.15s ease, color 0.15s ease;
}

.payzon-header .shortcut-link:hover {
	background-color: #f1f5f9;
	color: #1d4ed8;
}

.payzon-header .shortcut-icon {
	font-size: 22px;
	margin-bottom: 6px;
	line-height: 1;
}

.payzon-header .shortcut-label {
	font-size: 11px;
	font-weight: 700;
	text-align: center;
	letter-spacing: -0.5px;
	white-space: nowrap;
}

/* 5. 青色 GNB ナビゲーション (横100% 均等分割) */
.payzon-header .gnb-navbar {
	background-color: #1d4ed8;
	display: flex;
	width: 100%;
	position: relative;
	z-index: 999;
	border-bottom: 2px solid #1e40af;
}

.payzon-header .gnb-item {
	flex: 1;
	position: relative;
	border-right: 1px solid rgba(255, 255, 255, 0.2);
	text-align: center;
}

.payzon-header .gnb-item:last-child {
	border-right: none;
}

.payzon-header .gnb-link {
	display: block;
	padding: 11px 0;
	color: #ffffff;
	text-decoration: none;
	font-size: 13px;
	font-weight: 700;
	width: 100%;
	transition: background 0.2s ease;
}

.payzon-header .gnb-item:hover .gnb-link {
	background-color: #1e40af;
}

/* 6. サブドロップダウン */
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
	border-bottom: 1px solid rgba(255, 255, 255, 0.08);
	transition: background 0.2s ease, color 0.2s ease;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
}

/* 홀짝 퐁당퐁당: 같은 블루 계열에서 채도만 살짝 다르게 */
.payzon-header .sub-dropdown a:nth-child(odd) {
	background-color: hsl(224, 70%, 32%);
}

.payzon-header .sub-dropdown a:nth-child(even) {
	background-color: hsl(224, 48%, 38%);
}

.payzon-header .sub-dropdown a:last-child {
	border-bottom: none;
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

<!-- ヘッダー全体を包むタグ -->
<header class="payzon-header">
	<!-- 1. 最上段バナー -->
	<div class="top-banner">
		<span>★ 現在、体験版をご利用中です。</span>
		<span><a href="#" style="color:#fff; text-decoration:none;">会員登録</a> | <a href="#" style="color:#fff; text-decoration:none;">ログアウト</a></span>
	</div>

	<!-- 2. ブランドロゴエリア -->
	<div class="header-top">
		<div class="brand-logo">
			ヘキサゾーン <span>人事・給与管理</span>
		</div>
		<div class="user-info">
			<span>無料体験会員様</span>
		</div>
	</div>

	<!-- 3. アイコンショートカットバー (12個のメニューを均等分割) -->
	<div class="shortcut-bar">
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/index.jsp" class="shortcut-link">
				<span class="shortcut-icon">🏠</span>
				<span class="shortcut-label">HOME</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/config/register.do" class="shortcut-link">
				<span class="shortcut-icon">👤</span>
				<span class="shortcut-label">社員登録</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/emp/list.do" class="shortcut-link">
				<span class="shortcut-icon">🗂️</span>
				<span class="shortcut-label">社員一覧</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/emp/certificate/working.do" class="shortcut-link">
				<span class="shortcut-icon">📜</span>
				<span class="shortcut-label">証明書発行</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/attendance/record/list.do" class="shortcut-link">
				<span class="shortcut-icon">⏱️</span>
				<span class="shortcut-label">勤怠記録・管理</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/attendance/vacation.do" class="shortcut-link">
				<span class="shortcut-icon">📅</span>
				<span class="shortcut-label">休暇照会</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/salary/entry.do" class="shortcut-link">
				<span class="shortcut-icon">💵</span>
				<span class="shortcut-label">給与入力・管理</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/salary/dayworker/register.do" class="shortcut-link">
				<span class="shortcut-icon">📆</span>
				<span class="shortcut-label">給与入力(日雇い)</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/salary/ledger.do" class="shortcut-link">
				<span class="shortcut-icon">📋</span>
				<span class="shortcut-label">賃金台帳</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/salary/payslip.do" class="shortcut-link">
				<span class="shortcut-icon">🧾</span>
				<span class="shortcut-label">給与明細書</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/salary/empHistory.do" class="shortcut-link">
				<span class="shortcut-icon">👥</span>
				<span class="shortcut-label">社員別給与履歴</span>
			</a>
		</div>
		<div class="shortcut-item">
			<a href="${pageContext.request.contextPath}/retirement/list.do" class="shortcut-link">
				<span class="shortcut-icon">💼</span>
				<span class="shortcut-label">退職管理</span>
			</a>
		</div>
	</div>

	<!-- 4. メインナビゲーションバー (GNB) -->
	<nav class="gnb-navbar">
		<div class="gnb-item">
			<a href="#" class="gnb-link">基本環境設定 ▾</a>
			<div class="sub-dropdown"> 
				<a href="${pageContext.request.contextPath}/config/register.do">社員登録</a>
				<a href="${pageContext.request.contextPath}/config/vacation-attendance.do">休暇・勤怠設定</a>
				<a href="${pageContext.request.contextPath}/config/salaryItem.do">給与項目設定</a>
			</div>
		</div>

		<div class="gnb-item">
			<a href="#" class="gnb-link">人事管理 ▾</a>
			<div class="sub-dropdown">
				<a href="${pageContext.request.contextPath}/emp/list.do">社員現況・管理</a>
				<a href="${pageContext.request.contextPath}/emp/empCard.do">人事記録カード</a>
				<a href="${pageContext.request.contextPath}/emp/certificate/working.do">各種証明書発行</a> 
				<a href="${pageContext.request.contextPath}/emp/certificate/list.do">証明書発行台帳</a>
			</div>
		</div>

		<div class="gnb-item">
			<a href="#" class="gnb-link">勤怠管理 ▾</a>
			<div class="sub-dropdown">
				<a href="${pageContext.request.contextPath}/attendance/record/list.do">勤怠記録管理</a>
				<a href="${pageContext.request.contextPath}/attendance/list.do">勤怠照会</a>
				<a href="${pageContext.request.contextPath}/attendance/vacation.do">休暇照会</a>
				<a href="${pageContext.request.contextPath}/attendance/daily/record.do">日雇い勤務記録／管理</a> 
				<a href="${pageContext.request.contextPath}/attendance/daily/list.do">日雇い勤務照会</a>
			</div>
		</div>

		<div class="gnb-item">
			<a href="#" class="gnb-link">給与管理 ▾</a>
			<div class="sub-dropdown">
				<a href="${pageContext.request.contextPath}/salary/entry.do">給与入力/管理</a>
				<a href="${pageContext.request.contextPath}/salary/dayworker/register.do">給与入力 / 管理 (日雇い)</a>
				<a href="${pageContext.request.contextPath}/salary/ledger.do">給与台帳</a>
				<a href="${pageContext.request.contextPath}/salary/payslip.do">給与明細書</a>
				<a href="${pageContext.request.contextPath}/salary/empHistory.do">社員別給与内訳</a> 
				<a href="${pageContext.request.contextPath}/salary/transfer/apply.do">給与振込申請</a> 
				<a href="${pageContext.request.contextPath}/salary/transfer/result.do">給与振込結果照会</a> 
				<a href="${pageContext.request.contextPath}/salary/itemRegister.do">項目別台帳</a> 
				<a href="${pageContext.request.contextPath}/salary/socialInsurance.do">4大保険控除明細</a>
			</div>
		</div>

		<div class="gnb-item">
			<a href="#" class="gnb-link">給与統計 ▾</a>
			<div class="sub-dropdown">
				<a href="${pageContext.request.contextPath}/salary/stats/yearlyTotal.do">年度別 全体給与統計</a> 
				<a href="${pageContext.request.contextPath}/salary/stats/monthlyTotal.do">月別 全体給与統計</a> 
				<a href="${pageContext.request.contextPath}/salary/stats/yearlyPersonal.do">年度別 個人年収統計</a> 
				<a href="${pageContext.request.contextPath}/salary/stats/monthlyPersonal.do">月別 個人給与統計</a> 
				<a href="${pageContext.request.contextPath}/salary/stats/itemComposition.do">給与項目構成統計</a>
			</div>
		</div>

		<div class="gnb-item">
			<a href="#" class="gnb-link">退職管理 ▾</a>
			<div class="sub-dropdown">
				<a href="${pageContext.request.contextPath}/retirement/list.do">社員退職管理</a> 
				<a href="${pageContext.request.contextPath}/retirement/severance.do">退職給付入力・管理</a> 
				<a href="${pageContext.request.contextPath}/retirement/specification.do">退職給付明細書</a>
			</div>
		</div>
	</nav>
</header>

<div style="margin-bottom: 30px;"></div>