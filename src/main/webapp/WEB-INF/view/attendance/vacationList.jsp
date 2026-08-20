<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>休暇照会</title>
<style>
.vacation-container { max-width: 1400px; margin: 0 auto 50px; padding: 0 20px;
	font-family: Arial, "Malgun Gothic", sans-serif; color: #333; }
.page-title { border-bottom: 2px solid #3b78c8; padding-bottom: 12px; margin-bottom: 10px; }
.description { margin-bottom: 22px; color: #666; }
.search-layout { display: grid; grid-template-columns: minmax(600px, 1.2fr) minmax(400px, .8fr);
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
.pagination { margin-top: 15px; display: flex; justify-content: center; gap: 5px; }
.pagination a { padding: 6px 9px; border: 1px solid #cbd2dc; text-decoration: none; color: #333; }
.pagination a.current { color: #fff; background: #3b78c8; }
.records { margin-top: 25px; }
.empty, .notice { padding: 24px !important; color: #777; text-align: center; }
.summary { display: flex; gap: 16px; margin: 18px 0; }
.summary div { flex: 1; padding: 15px; background: #f4f7fb; border-radius: 5px; text-align: center; }
@media (max-width: 900px) { .search-layout { grid-template-columns: 1fr; } }
</style>
</head>
<body>
	<layout:headerNav />
	<main class="vacation-container">
		<h2 class="page-title">休暇照会</h2>
		<p class="description">社員と年月を選択して、月別の休暇使用履歴を照会します。</p>

		<div class="search-layout">
			<section class="panel">
				<h3>社員一覧</h3>
				<table class="data-table">
					<thead>
						<tr><th>社員番号</th><th>氏名</th><th>部署</th><th>職位</th><th>選択</th></tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty empListPage.empListData}">
								<tr><td colspan="5" class="empty">登録された社員はいません。</td></tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="employee" items="${empListPage.empListData}">
									<tr class="${selectedEmpId == employee.empId ? 'selected' : ''}">
										<td>${employee.empId}</td>
										<td><c:out value="${employee.nameKor}" /></td>
										<td><c:out value="${employee.deptName}" /></td>
										<td><c:out value="${employee.positionName}" /></td>
										<td><a class="btn btn-small"
											href="${pageContext.request.contextPath}/attendance/vacation.do?empId=${employee.empId}&amp;year=${selectedYear}&amp;month=${selectedMonth}&amp;pageNo=${empListPage.currentPage}">選択</a></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<c:if test="${empListPage.totalPage > 0}">
					<div class="pagination">
						<c:forEach var="page" begin="${empListPage.startPage}" end="${empListPage.endPage}">
							<a class="${page == empListPage.currentPage ? 'current' : ''}"
								href="${pageContext.request.contextPath}/attendance/vacation.do?pageNo=${page}&amp;empId=${selectedEmpId}&amp;year=${selectedYear}&amp;month=${selectedMonth}">${page}</a>
						</c:forEach>
					</div>
				</c:if>
			</section>

			<section class="panel">
				<h3>照会条件</h3>
				<form class="filter-form" method="get"
					action="${pageContext.request.contextPath}/attendance/vacation.do">
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
			<c:set var="totalDays" value="0" />
			<c:forEach var="vacation" items="${monthlyVacations}">
				<c:set var="totalDays" value="${totalDays + vacation.attendanceDays}" />
			</c:forEach>
			<div class="summary">
				<div>休暇件数<br><strong>${monthlyVacations.size()}</strong>件</div>
				<div>休暇日数合計<br><strong><fmt:formatNumber value="${totalDays}" pattern="#0.##" /></strong>日</div>
			</div>
			<section class="panel records">
				<h3>${selectedYear}年 ${selectedMonth}月 休暇使用履歴</h3>
				<table class="data-table">
					<thead>
						<tr><th>入力日</th><th>休暇項目</th><th>勤怠項目</th><th>期間</th><th>休暇日数</th><th>摘要</th></tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty monthlyVacations}">
								<tr><td colspan="6" class="empty">該当する休暇使用履歴はありません。</td></tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="vacation" items="${monthlyVacations}">
									<tr>
										<td><fmt:formatDate value="${vacation.inputDate}" pattern="yyyy-MM-dd" /></td>
										<td>
											<c:forEach var="type" items="${attendanceTypes}">
												<c:if test="${type.attendanceTypeId == vacation.attendanceTypeId}">
													<c:forEach var="vacationType" items="${vacationTypes}">
														<c:if test="${vacationType.vacationTypeId == type.vacationTypeId}"><c:out value="${vacationType.vacationName}" /></c:if>
													</c:forEach>
												</c:if>
											</c:forEach>
										</td>
										<td>
											<c:forEach var="type" items="${attendanceTypes}">
												<c:if test="${type.attendanceTypeId == vacation.attendanceTypeId}"><c:out value="${type.attendanceType}" /></c:if>
											</c:forEach>
										</td>
										<td><fmt:formatDate value="${vacation.attendanceStartDate}" pattern="yyyy-MM-dd" /> ～
											<fmt:formatDate value="${vacation.attendanceEndDate}" pattern="yyyy-MM-dd" /></td>
										<td><fmt:formatNumber value="${vacation.attendanceDays}" pattern="#0.##" />日</td>
										<td><c:out value="${vacation.remarks}" /></td>
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
