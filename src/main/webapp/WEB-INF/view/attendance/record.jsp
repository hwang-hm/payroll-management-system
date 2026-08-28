<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<%-- 수정할 근태기록 선택 / 修正する勤怠記録を選択 --%>
<c:set var="formItem" value="${selectedAttendance}" />

<%-- 수정 폼 날짜값 생성 / 修正フォームの日付値を生成 --%>
<fmt:formatDate var="startDateValue"
	value="${formItem.attendanceStartDate}" pattern="yyyy-MM-dd" />

<fmt:formatDate var="endDateValue"
	value="${formItem.attendanceEndDate}" pattern="yyyy-MM-dd" />

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ヘキサゾーン - 勤怠記録管理</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<style>
.record-container {
	max-width: 1400px;
	margin: 0 auto 50px;
	padding: 0 20px;
	color: #333;
}

.record-title {
	border-bottom: 2px solid #3b78c8;
	padding-bottom: 12px;
	margin-bottom: 22px;
}

.record-description {
	margin: -10px 0 22px;
	color: #666;
	font-size: 14px;
}

.record-layout {
	display: grid;
	grid-template-columns: minmax(700px, 1.6fr) minmax(420px, 1fr);
	gap: 26px;
	align-items: start;
}

.record-panel {
	background: #fff;
	border: 1px solid #d9d9d9;
	border-radius: 6px;
	padding: 18px;
}

.record-panel h3 {
	margin: 0 0 15px;
}

.record-table {
	width: 100%;
	border-collapse: collapse;
}

.record-table th,
.record-table td {
	border: 1px solid #d9d9d9;
	padding: 9px 7px;
	text-align: center;
	font-size: 13px;
}

.record-table th {
	background-color: #f3f7fc;
	white-space: nowrap;
}

.record-table tbody tr:hover {
	background-color: #f8fbff;
}

.selected-row {
	background-color: #eaf3ff;
}

.form-table th {
	width: 135px;
}

.form-table td {
	text-align: left;
}

.form-table input[type="date"],
.form-table input[type="number"],
.form-table input[type="text"],
.form-table select {
	box-sizing: border-box;
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 3px;
	background-color: #fff;
}

.date-area {
	display: grid;
	grid-template-columns: 1fr auto 1fr;
	gap: 7px;
	align-items: center;
}

.button-area {
	display: flex;
	gap: 8px;
	margin-top: 16px;
	flex-wrap: wrap;
}

.btn {
	display: inline-block;
	border: 0;
	border-radius: 3px;
	padding: 8px 14px;
	color: #fff;
	background-color: #3b78c8;
	text-decoration: none;
	cursor: pointer;
	font-size: 13px;
}

.btn-secondary {
	background-color: #777;
}

.btn-danger {
	background-color: #d9534f;
}

.btn-small {
	padding: 5px 9px;
	font-size: 12px;
}

.message {
	padding: 11px 13px;
	margin-bottom: 16px;
	border-radius: 4px;
}

.message-success {
	background: #e8f6ec;
	color: #236b35;
}

.message-error {
	background: #fdecec;
	color: #a12622;
}

.notice {
	padding: 12px;
	background: #fff9dd;
	border: 1px solid #eadb8b;
	color: #665b25;
	margin-bottom: 14px;
}

.no-data {
	color: #777;
	padding: 20px !important;
}

.pagination {
	display: flex;
	justify-content: center;
	gap: 5px;
	margin-top: 15px;
}

.pagination a,
.pagination strong {
	display: inline-block;
	min-width: 28px;
	padding: 6px;
	border: 1px solid #ccc;
	text-align: center;
	text-decoration: none;
	color: #333;
}

.pagination strong {
	color: #fff;
	background: #3b78c8;
	border-color: #3b78c8;
}

.employee-record-panel {
	margin-top: 26px;
}

.amount-guide {
	color: #d9534f;
	font-size: 12px;
	margin-top: 5px;
}

@media (max-width: 1050px) {
	.record-layout {
		grid-template-columns: 1fr;
	}
}
</style>
</head>

<body>
	<layout:headerNav />

	<main class="record-container">

		<h2 class="record-title">勤怠記録管理</h2>

		<p class="record-description">
			給与に関連する勤怠記録を管理するメニューです。社員別の勤怠内訳を管理できます。
		</p>

		<%-- 처리 결과 메시지 / 処理結果メッセージ --%>
		<c:if test="${param.result == 'registered'}">
			<div class="message message-success">
				勤怠記録を登録しました。
			</div>
		</c:if>

		<c:if test="${param.result == 'updated'}">
			<div class="message message-success">
				勤怠記録を修正しました。
			</div>
		</c:if>

		<c:if test="${param.result == 'deleted'}">
			<div class="message message-success">
				勤怠記録を削除しました。
			</div>
		</c:if>

		<c:if test="${param.error == 'invalidInput'}">
			<div class="message message-error">
				入力内容を確認してください。
			</div>
		</c:if>

		<c:if test="${param.error == 'invalidDate'}">
			<div class="message message-error">
				終了日は開始日以降の日付を選択してください。
			</div>
		</c:if>

		<c:if test="${param.error == 'invalidDays'}">
			<div class="message message-error">
				勤怠日数は0より大きい値を入力してください。
			</div>
		</c:if>

		<c:if test="${param.error == 'notFound'}">
			<div class="message message-error">
				選択した勤怠記録が見つかりません。
			</div>
		</c:if>

		<c:if test="${param.error == 'registerFailed'}">
			<div class="message message-error">
				勤怠記録を登録できませんでした。
			</div>
		</c:if>

		<c:if test="${param.error == 'updateFailed'}">
			<div class="message message-error">
				勤怠記録を修正できませんでした。
			</div>
		</c:if>

		<c:if test="${param.error == 'deleteFailed'}">
			<div class="message message-error">
				勤怠記録を削除できませんでした。
			</div>
		</c:if>

		<c:if test="${not empty errorMessage}">
			<div class="message message-error">
				<c:out value="${errorMessage}" />
			</div>
		</c:if>

		<div class="record-layout">

			<%-- 사원 목록 / 社員リスト --%>
			<section class="record-panel">
				<h3>社員一覧</h3>

				<table class="record-table">
					<thead>
						<tr>
							<th>区分</th>
							<th>社員番号</th>
							<th>氏名</th>
							<th>部署</th>
							<th>役職</th>
							<th>状態</th>
							<th>勤怠記録</th>
						</tr>
					</thead>

					<tbody>
						<c:choose>
							<c:when test="${empty empListPage.empListData}">
								<tr>
									<td colspan="7" class="no-data">
										登録された社員がありません。
									</td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach var="employee"
									items="${empListPage.empListData}">

									<tr class="${selectedEmpId == employee.empId
											? 'selected-row' : ''}">

										<td>
											<c:choose>
												<c:when test="${employee.employmentType == '정규직'}">
													正規職
												</c:when>
												<c:when test="${employee.employmentType == '계약직'}">
													契約職
												</c:when>
												<c:when test="${employee.employmentType == '임시직'}">
													臨時職
												</c:when>
												<c:when test="${employee.employmentType == '파견직'}">
													派遣職
												</c:when>
												<c:when test="${employee.employmentType == '위촉직'}">
													委嘱職
												</c:when>
												<c:when test="${employee.employmentType == '일용직'}">
													日雇職
												</c:when>
												<c:otherwise>
													<c:out value="${employee.employmentType}" />
												</c:otherwise>
											</c:choose>
										</td>

										<td>
											<c:out value="${employee.empId}" />
										</td>

										<td>
											<c:out value="${employee.nameKor}" />
										</td>

										<td>
											<c:out value="${employee.deptName}" />
										</td>

										<td>
											<c:out value="${employee.positionName}" />
										</td>

										<td>
											${employee.status == 'Y' ? '在職' : '退職'}
										</td>

										<td>
											<a class="btn btn-small"
												href="${pageContext.request.contextPath}/attendance/record/list.do?empId=${employee.empId}&pageNo=${empListPage.currentPage}">
												選択
											</a>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>

				<%-- 사원 목록 페이지 이동 / 社員リストのページ移動 --%>
				<c:if test="${empListPage.totalPage > 0}">
					<div class="pagination">

						<c:forEach var="page"
							begin="${empListPage.startPage}"
							end="${empListPage.endPage}">

							<c:choose>
								<c:when test="${page == empListPage.currentPage}">
									<strong>${page}</strong>
								</c:when>

								<c:otherwise>
									<a href="${pageContext.request.contextPath}/attendance/record/list.do?pageNo=${page}">
										${page}
									</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</c:if>
			</section>

			<%-- 근태기록 등록 및 수정 / 勤怠記録の登録および修正 --%>
			<section class="record-panel">

				<h3>
					${not empty selectedAttendance
							? '勤怠記録修正'
							: '勤怠記録登録'}
				</h3>

				<c:choose>
					<c:when test="${empty selectedEmpId}">
						<div class="notice">
							勤怠記録を入力する社員を選択してください。
						</div>
					</c:when>

					<c:otherwise>
						<form method="post" id="attendanceForm">

							<input type="hidden" name="attendanceId"
								value="<c:out value='${formItem.attendanceId}' />">

							<input type="hidden" name="empId"
								value="<c:out value='${selectedEmpId}' />">

							<table class="record-table form-table">

								<tr>
									<th>社員番号</th>
									<td>
										<c:out value="${selectedEmpId}" />
									</td>
								</tr>

								<tr>
									<th>入力日</th>
									<td>
										<c:choose>
											<c:when test="${not empty formItem.inputDate}">
												<fmt:formatDate
													value="${formItem.inputDate}"
													pattern="yyyy-MM-dd" />
											</c:when>
											<c:otherwise>
												登録時に自動入力
											</c:otherwise>
										</c:choose>
									</td>
								</tr>

								<tr>
									<th>勤怠項目</th>
									<td>
										<select name="attendanceTypeId" required
											oninvalid="this.setCustomValidity('勤怠項目を選択してください。')"
											onchange="this.setCustomValidity('')">

											<option value="">選択してください。</option>

											<c:forEach var="attendanceType"
												items="${attendanceTypes}">

												<c:if test="${attendanceType.useYn == 'Y'}">
													<option
														value="${attendanceType.attendanceTypeId}"
														${formItem.attendanceTypeId
																== attendanceType.attendanceTypeId
																? 'selected' : ''}>

														<c:out
															value="${attendanceType.attendanceType}" />
													</option>
												</c:if>
											</c:forEach>
										</select>
									</td>
								</tr>

								<tr>
									<th>期間</th>
									<td>
										<div class="date-area">
											<input type="date"
												name="attendanceStartDate"
												value="${startDateValue}"
												required
												oninvalid="this.setCustomValidity('開始日を選択してください。')"
												oninput="this.setCustomValidity('')">

											<span>～</span>

											<input type="date"
												name="attendanceEndDate"
												value="${endDateValue}">
										</div>
									</td>
								</tr>

								<tr>
									<th>勤怠数量</th>
									<td>
										<input type="number"
											name="attendanceDays"
											min="0.1"
											step="0.1"
											required
											oninvalid="this.setCustomValidity('勤怠数量を入力してください。')"
											oninput="this.setCustomValidity('')"
											placeholder="例: 1、0.5"
											value="${formItem.attendanceDays > 0
													? formItem.attendanceDays : ''}">
									</td>
								</tr>

								<tr>
									<th>金額（手当）</th>
									<td>
										<input type="number"
											name="allowanceAmount"
											min="0"
											step="1"
											placeholder="支給手当の場合に入力してください。"
											value="${formItem.allowanceAmount > 0
													? formItem.allowanceAmount : ''}">

										<div class="amount-guide">
											※ 勤怠分類が支給手当に該当する場合に入力
										</div>
									</td>
								</tr>

								<tr>
									<th>摘要</th>
									<td>
										<input type="text"
											name="remarks"
											maxlength="200"
											placeholder="摘要がある場合は入力してください。"
											value="<c:out value='${formItem.remarks}' />">
									</td>
								</tr>
							</table>

							<div class="button-area">
								<c:choose>
									<c:when test="${not empty selectedAttendance}">
										<button class="btn" type="submit"
											formaction="${pageContext.request.contextPath}/attendance/record/update.do">
											修正
										</button>
									</c:when>

									<c:otherwise>
										<button class="btn" type="submit"
											formaction="${pageContext.request.contextPath}/attendance/record/register.do">
											登録
										</button>
									</c:otherwise>
								</c:choose>

								<a class="btn btn-secondary"
									href="${pageContext.request.contextPath}/attendance/record/list.do?empId=${selectedEmpId}">
									初期化
								</a>
							</div>
						</form>
					</c:otherwise>
				</c:choose>
			</section>
		</div>

		<%-- 선택한 사원의 근태기록 / 選択した社員の勤怠記録 --%>
		<c:if test="${not empty selectedEmpId}">
			<section class="record-panel employee-record-panel">

				<h3>社員別勤怠記録</h3>

				<table class="record-table">
					<thead>
						<tr>
							<th>番号</th>
							<th>入力日</th>
							<th>勤怠項目</th>
							<th>期間</th>
							<th>勤怠数量</th>
							<th>金額</th>
							<th>摘要</th>
							<th>管理</th>
						</tr>
					</thead>

					<tbody>
						<c:choose>
							<c:when test="${empty employeeAttendances}">
								<tr>
									<td colspan="8" class="no-data">
										登録された勤怠記録はありません。
									</td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach var="attendance"
									items="${employeeAttendances}">
									<c:set var="attendanceUnit" value="日" />
									<c:forEach var="attendanceType"
										items="${attendanceTypes}">
										<c:if test="${attendance.attendanceTypeId
												== attendanceType.attendanceTypeId
												&& (attendanceType.unit == '시간'
														|| attendanceType.unit == '時間')}">
											<c:set var="attendanceUnit" value="時間" />
										</c:if>
									</c:forEach>

									<tr>
										<td>
											<c:out value="${attendance.attendanceId}" />
										</td>

										<td>
											<fmt:formatDate
												value="${attendance.inputDate}"
												pattern="yyyy-MM-dd" />
										</td>

										<td>
											<c:forEach var="attendanceType"
												items="${attendanceTypes}">

												<c:if test="${attendance.attendanceTypeId
														== attendanceType.attendanceTypeId}">

													<c:out
														value="${attendanceType.attendanceType}" />
												</c:if>
											</c:forEach>
										</td>

										<td>
											<fmt:formatDate
												value="${attendance.attendanceStartDate}"
												pattern="yyyy-MM-dd" />

											～

											<fmt:formatDate
												value="${attendance.attendanceEndDate}"
												pattern="yyyy-MM-dd" />
										</td>

									<td>
										<fmt:formatNumber
											value="${attendance.attendanceDays}"
											pattern="#0.##" />${attendanceUnit}
									</td>

										<td>
											<fmt:formatNumber
												value="${attendance.allowanceAmount}"
												pattern="#,##0" />
										</td>

										<td>
											<c:out value="${attendance.remarks}" />
										</td>

										<td>
											<a class="btn btn-small"
												href="${pageContext.request.contextPath}/attendance/record/list.do?empId=${selectedEmpId}&attendanceId=${attendance.attendanceId}">
												修正
											</a>

											<form method="post"
												action="${pageContext.request.contextPath}/attendance/record/delete.do"
												style="display: inline;">

												<input type="hidden"
													name="attendanceId"
													value="${attendance.attendanceId}">

												<button class="btn btn-danger btn-small"
													type="submit"
													onclick="return confirm('この勤怠記録を削除しますか？');">
													削除
												</button>
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
</body>
</html>
