<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<c:set var="formItem" value="${selectedDailyWork}" />
<fmt:formatDate var="workDateValue" value="${formItem.workDate}"
	pattern="yyyy-MM-dd" />

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>日雇い勤務記録管理</title>
<style>
.daily-container { max-width: 1400px; margin: 0 auto 50px; padding: 0 20px;
	font-family: Arial, "Malgun Gothic", sans-serif; color: #333; }
.daily-title { border-bottom: 2px solid #3b78c8; padding-bottom: 12px; margin-bottom: 12px; }
.daily-description { color: #666; font-size: 14px; margin-bottom: 22px; }
.daily-layout { display: grid; grid-template-columns: minmax(600px, 1.4fr) minmax(380px, 0.8fr);
	gap: 26px; align-items: start; }
.panel { border: 1px solid #ddd; border-radius: 7px; padding: 20px; background: #fff; }
.panel h3 { margin: 0 0 16px; font-size: 20px; }
.data-table { width: 100%; border-collapse: collapse; font-size: 14px; }
.data-table th, .data-table td { border: 1px solid #d7dce3; padding: 10px 8px; text-align: center; }
.data-table th { background: #eef3f9; font-weight: 700; }
.data-table tbody tr.selected { background: #eaf3ff; }
.form-table th { width: 125px; }
.form-table td { text-align: left; }
.form-table input { width: 100%; box-sizing: border-box; padding: 9px;
	border: 1px solid #cbd2dc; border-radius: 3px; }
.btn { display: inline-block; border: 0; border-radius: 3px; padding: 8px 14px;
	background: #3b78c8; color: white; text-decoration: none; cursor: pointer; }
.btn-gray { background: #777; }
.btn-danger { background: #e05252; }
.btn-small { padding: 6px 10px; font-size: 12px; }
.actions { margin-top: 16px; display: flex; gap: 8px; }
.message { padding: 12px; margin-bottom: 18px; border-radius: 4px; }
.message-success { color: #14783a; background: #e8f7ed; }
.message-error { color: #a63232; background: #fff0f0; }
.notice { padding: 15px; background: #fff9dd; border: 1px solid #eadb94; color: #6d5a12; }
.records { margin-top: 28px; }
.number { text-align: right !important; }
.calc-box { margin-top: 12px; padding: 12px; background: #f7f9fc; line-height: 1.8; }
.empty { padding: 24px !important; color: #777; }
@media (max-width: 950px) { .daily-layout { grid-template-columns: 1fr; } }
</style>
</head>
<body>
	<layout:headerNav />
	<main class="daily-container">
		<h2 class="daily-title">日雇い勤務記録／管理</h2>
		<p class="daily-description">
			日雇い社員の勤務日、現場／プロジェクト、日当および支給率を管理します。
		</p>

		<c:if test="${param.result == 'registered'}">
			<div class="message message-success">勤務記録を登録しました。</div>
		</c:if>
		<c:if test="${param.result == 'updated'}">
			<div class="message message-success">勤務記録を修正しました。</div>
		</c:if>
		<c:if test="${param.result == 'deleted'}">
			<div class="message message-success">勤務記録を削除しました。</div>
		</c:if>
		<c:if test="${not empty param.error}">
			<div class="message message-error">入力内容を確認してください。</div>
		</c:if>

		<div class="daily-layout">
			<section class="panel">
				<h3>日雇い社員一覧</h3>
				<table class="data-table">
					<thead>
						<tr><th>区分</th><th>社員番号</th><th>氏名</th><th>部署</th><th>勤務記録</th></tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty dailyWorkers}">
								<tr><td colspan="5" class="empty">登録された日雇い社員はありません。</td></tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="worker" items="${dailyWorkers}">
									<tr class="${selectedEmpId == worker.empId ? 'selected' : ''}">
										<td><c:out value="${worker.employmentType}" /></td>
										<td><c:out value="${worker.empId}" /></td>
										<td><c:out value="${worker.nameKor}" /></td>
										<td><c:out value="${worker.deptName}" /></td>
										<td><a class="btn btn-small"
											href="${pageContext.request.contextPath}/attendance/daily/record.do?empId=${worker.empId}">管理</a></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</section>

			<section class="panel">
				<h3>${not empty formItem ? '勤務記録修正' : '勤務記録登録'}</h3>
				<c:choose>
					<c:when test="${empty selectedEmpId}">
						<div class="notice">勤務記録を入力する社員を選択してください。</div>
					</c:when>
					<c:otherwise>
						<form method="post" id="dailyWorkForm">
							<input type="hidden" name="dailyWorkId" value="${formItem.dailyWorkId}">
							<input type="hidden" name="empId" value="${selectedEmpId}">
							<table class="data-table form-table">
								<tr><th>社員番号</th><td><c:out value="${selectedEmpId}" /></td></tr>
								<tr><th>勤務日</th><td><input type="date" name="workDate" required
									value="${not empty workDateValue ? workDateValue : today}"></td></tr>
								<tr><th>現場／プロジェクト</th><td><input type="text" name="projectName"
									maxlength="50" required value="<c:out value='${formItem.projectName}' />"
									placeholder="現場またはプロジェクト名"></td></tr>
								<tr><th>日当</th><td><input type="number" id="dailyWage" name="dailyWage"
									min="1" max="999999999" required value="${formItem.dailyWage > 0 ? formItem.dailyWage : ''}"></td></tr>
								<tr><th>支給率</th><td><input type="number" id="paymentRate" name="paymentRate"
									min="0.01" max="999.99" step="0.01" required
									value="${formItem.paymentRate > 0 ? formItem.paymentRate : '1.0'}"></td></tr>
							</table>
							<div class="calc-box">
								支給額：<strong id="paymentPreview">0</strong> 円<br>
								所得税：<span id="incomeTaxPreview">0</span> 円 ／
								地方所得税：<span id="localTaxPreview">0</span> 円<br>
								実支給額：<strong id="netPayPreview">0</strong> 円
							</div>
							<div class="actions">
								<c:choose>
									<c:when test="${not empty formItem}">
										<button class="btn" type="submit"
											formaction="${pageContext.request.contextPath}/attendance/daily/update.do">修正</button>
									</c:when>
									<c:otherwise>
										<button class="btn" type="submit"
											formaction="${pageContext.request.contextPath}/attendance/daily/register.do">登録</button>
									</c:otherwise>
								</c:choose>
								<a class="btn btn-gray"
									href="${pageContext.request.contextPath}/attendance/daily/record.do?empId=${selectedEmpId}">初期化</a>
							</div>
						</form>
					</c:otherwise>
				</c:choose>
			</section>
		</div>

		<c:if test="${not empty selectedEmpId}">
			<section class="panel records">
				<h3>社員別勤務記録</h3>
				<table class="data-table">
					<thead><tr><th>番号</th><th>勤務日</th><th>現場／プロジェクト</th>
						<th>日当</th><th>支給率</th><th>支給額</th><th>所得税</th>
						<th>地方所得税</th><th>実支給額</th><th>修正／削除</th></tr></thead>
					<tbody>
						<c:choose>
							<c:when test="${empty employeeDailyWorks}">
								<tr><td colspan="10" class="empty">登録された勤務記録はありません。</td></tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="work" items="${employeeDailyWorks}">
									<c:set var="paymentAmount" value="${work.dailyWage * work.paymentRate}" />
									<tr>
										<td>${work.dailyWorkId}</td>
										<td><fmt:formatDate value="${work.workDate}" pattern="yyyy-MM-dd" /></td>
										<td><c:out value="${work.projectName}" /></td>
										<td class="number"><fmt:formatNumber value="${work.dailyWage}" pattern="#,##0" /></td>
										<td>${work.paymentRate}</td>
										<td class="number"><fmt:formatNumber value="${paymentAmount}" pattern="#,##0" /></td>
										<td class="number"><fmt:formatNumber value="${work.incomeTax}" pattern="#,##0" /></td>
										<td class="number"><fmt:formatNumber value="${work.localIncomeTax}" pattern="#,##0" /></td>
										<td class="number"><fmt:formatNumber value="${work.netPay}" pattern="#,##0" /></td>
										<td>
											<a class="btn btn-small" href="${pageContext.request.contextPath}/attendance/daily/record.do?dailyWorkId=${work.dailyWorkId}">修正</a>
											<form method="post" action="${pageContext.request.contextPath}/attendance/daily/delete.do" style="display:inline;">
												<input type="hidden" name="dailyWorkId" value="${work.dailyWorkId}">
												<button class="btn btn-danger btn-small" type="submit"
													onclick="return confirm('この勤務記録を削除しますか？');">削除</button>
											</form>
										</td>
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
	<script>
	(function () {
		const wage = document.getElementById('dailyWage');
		const rate = document.getElementById('paymentRate');
		if (!wage || !rate) return;

		function calculate() {
			const payment = Math.round((Number(wage.value) || 0) * (Number(rate.value) || 0));
			const incomeTax = Math.round(payment * 0.027);
			const localTax = Math.round(incomeTax * 0.1);
			const netPay = payment - incomeTax - localTax;
			document.getElementById('paymentPreview').textContent = payment.toLocaleString();
			document.getElementById('incomeTaxPreview').textContent = incomeTax.toLocaleString();
			document.getElementById('localTaxPreview').textContent = localTax.toLocaleString();
			document.getElementById('netPayPreview').textContent = netPay.toLocaleString();
		}
		wage.addEventListener('input', calculate);
		rate.addEventListener('input', calculate);
		calculate();
	})();
	</script>
</body>
</html>
