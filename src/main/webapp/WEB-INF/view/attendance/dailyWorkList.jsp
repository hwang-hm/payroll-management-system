<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>日雇い勤務照会</title>
<style>
.inquiry-container { max-width: 1400px; margin: 0 auto 50px; padding: 0 20px;
	font-family: Arial, "Malgun Gothic", sans-serif; color: #333; }
.page-title { border-bottom: 2px solid #3b78c8; padding-bottom: 12px; margin-bottom: 10px; }
.description { margin-bottom: 22px; color: #666; }
.search-panel { display: grid; grid-template-columns: minmax(480px, 1fr) minmax(420px, .8fr);
	gap: 24px; align-items: start; }
.panel { padding: 20px; border: 1px solid #d7dce3; border-radius: 7px; background: #fff; }
.panel h3 { margin: 0 0 15px; }
.data-table { width: 100%; border-collapse: collapse; font-size: 14px; }
.data-table th, .data-table td { padding: 10px 8px; border: 1px solid #d7dce3; text-align: center; }
.data-table th { background: #eef3f9; }
.data-table tr.selected { background: #eaf3ff; }
.filter-form { display: flex; flex-wrap: wrap; gap: 10px; align-items: end; }
.filter-form label { display: flex; flex-direction: column; gap: 5px; font-weight: 700; }
.filter-form select, .filter-form input { min-width: 120px; padding: 8px;
	border: 1px solid #cbd2dc; border-radius: 3px; }
.btn { display: inline-block; padding: 8px 14px; border: 0; border-radius: 3px;
	background: #3b78c8; color: #fff; text-decoration: none; cursor: pointer; }
.btn-small { padding: 6px 10px; font-size: 12px; }
.records { margin-top: 25px; }
.empty, .notice { padding: 24px !important; color: #777; text-align: center; }
.number { text-align: right !important; }
.summary { display: flex; gap: 16px; margin: 18px 0; }
.summary div { flex: 1; padding: 15px; background: #f4f7fb; border-radius: 5px; text-align: center; }
@media (max-width: 900px) { .search-panel { grid-template-columns: 1fr; } }
</style>
</head>
<body>
	<layout:headerNav />
	<main class="inquiry-container">
		<h2 class="page-title">日雇い勤務照会</h2>
		<p class="description">社員と年月を選択して、日雇い勤務内訳を照会します。</p>

		<div class="search-panel">
			<section class="panel">
				<h3>日雇い社員一覧</h3>
				<table class="data-table">
					<thead><tr><th>社員番号</th><th>氏名</th><th>部署</th><th>選択</th></tr></thead>
					<tbody>
						<c:choose>
							<c:when test="${empty dailyWorkers}">
								<tr><td colspan="4" class="empty">照会できる日雇い社員はいません。</td></tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="worker" items="${dailyWorkers}">
									<tr class="${selectedEmpId == worker.empId ? 'selected' : ''}">
										<td><c:out value="${worker.empId}" /></td>
										<td><c:out value="${worker.nameKor}" /></td>
										<td><c:out value="${worker.deptName}" /></td>
										<td><a class="btn btn-small"
											href="${pageContext.request.contextPath}/attendance/daily/list.do?empId=${worker.empId}&year=${selectedYear}&month=${selectedMonth}">選択</a></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</section>

			<section class="panel">
				<h3>照会条件</h3>
				<form class="filter-form" method="get"
					action="${pageContext.request.contextPath}/attendance/daily/list.do">
					<input type="hidden" name="empId" value="${selectedEmpId}">
					<label>年度
						<input type="number" name="year" min="2000" max="2100"
							value="${selectedYear}" required>
					</label>
					<label>月
						<select name="month">
							<c:forEach var="month" begin="1" end="12">
								<option value="${month}" ${selectedMonth == month ? 'selected' : ''}>${month}月</option>
							</c:forEach>
						</select>
					</label>
					<button class="btn" type="submit" ${empty selectedEmpId ? 'disabled' : ''}>照会</button>
				</form>
				<c:if test="${empty selectedEmpId}">
					<p class="notice">左側の一覧から社員を選択してください。</p>
				</c:if>
			</section>
		</div>

		<c:if test="${not empty selectedEmpId}">
			<c:set var="totalPayment" value="0" />
			<c:set var="totalNetPay" value="0" />
			<c:forEach var="work" items="${monthlyWorks}">
				<c:set var="totalPayment" value="${totalPayment + work.dailyWage * work.paymentRate}" />
				<c:set var="totalNetPay" value="${totalNetPay + work.netPay}" />
			</c:forEach>
			<div class="summary">
				<div>勤務件数<br><strong>${monthlyWorks.size()}</strong>件</div>
				<div>支給額合計<br><strong><fmt:formatNumber value="${totalPayment}" pattern="#,##0" /></strong>円</div>
				<div>実支給額合計<br><strong><fmt:formatNumber value="${totalNetPay}" pattern="#,##0" /></strong>円</div>
			</div>
			<section class="panel records">
				<h3>${selectedYear}年 ${selectedMonth}月 勤務内訳</h3>
				<table class="data-table">
					<thead><tr><th>勤務日</th><th>現場／プロジェクト</th><th>日当</th><th>支給率</th>
						<th>支給額</th><th>所得税</th><th>地方所得税</th><th>実支給額</th></tr></thead>
					<tbody>
						<c:choose>
							<c:when test="${empty monthlyWorks}">
								<tr><td colspan="8" class="empty">該当する勤務記録はありません。</td></tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="work" items="${monthlyWorks}">
									<c:set var="paymentAmount" value="${work.dailyWage * work.paymentRate}" />
									<tr>
										<td><fmt:formatDate value="${work.workDate}" pattern="yyyy-MM-dd" /></td>
										<td><c:out value="${work.projectName}" /></td>
										<td class="number"><fmt:formatNumber value="${work.dailyWage}" pattern="#,##0" /></td>
										<td>${work.paymentRate}</td>
										<td class="number"><fmt:formatNumber value="${paymentAmount}" pattern="#,##0" /></td>
										<td class="number"><fmt:formatNumber value="${work.incomeTax}" pattern="#,##0" /></td>
										<td class="number"><fmt:formatNumber value="${work.localIncomeTax}" pattern="#,##0" /></td>
										<td class="number"><fmt:formatNumber value="${work.netPay}" pattern="#,##0" /></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</section>
		</c:if>
	</main>
	<layout:footer />
</body>
</html>
