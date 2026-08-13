<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<c:set var="vacationFormItem"
	value="${not empty selectedVacationType ? selectedVacationType : formVacationType}" />
<fmt:formatDate value="${vacationFormItem.applyStartDate}"
	pattern="yyyy-MM-dd" var="formStartDate" />
<fmt:formatDate value="${vacationFormItem.applyEndDate}"
	pattern="yyyy-MM-dd" var="formEndDate" />
<c:set var="attendanceFormItem"
	value="${not empty selectedAttendanceType
		? selectedAttendanceType
		: formAttendanceType}" />


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>休暇項目設定</title>
<style>
.attendance-container {
	max-width: 1180px;
	margin: 0 auto 50px;
	padding: 0 20px;
	font-family: Arial, "Malgun Gothic", sans-serif;
	color: #333;
}

.attendance-title {
	border-bottom: 2px solid #3b78c8;
	padding-bottom: 12px;
	margin-bottom: 24px;
}

.attendance-layout {
	display: grid;
	grid-template-columns: minmax(650px, 1.6fr) minmax(380px, 1fr);
	gap: 28px;
	align-items: start;
}

.attendance-panel {
	background: #fff;
	border: 1px solid #d9d9d9;
	border-radius: 6px;
	padding: 20px;
}

.attendance-panel h3 {
	margin: 0 0 15px;
}

.attendance-table {
	width: 100%;
	border-collapse: collapse;
}

.attendance-table th, .attendance-table td {
	border: 1px solid #d9d9d9;
	padding: 10px 8px;
	text-align: center;
}

.attendance-table th {
	background-color: #f3f7fc;
}

.form-table th {
	width: 130px;
}

.form-table td {
	text-align: left;
}

.form-table input[type="text"], .form-table select {
	box-sizing: border-box;
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 3px;
	background-color: #fff;
}

.radio-area {
	display: flex;
	gap: 14px;
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
	padding: 9px 15px;
	color: #fff;
	background-color: #3b78c8;
	text-decoration: none;
	cursor: pointer;
}

.btn-secondary {
	background-color: #777;
}

.btn-danger {
	background-color: #d9534f;
}

.btn-small {
	padding: 5px 10px;
	font-size: 12px;
}

.message {
	padding: 10px 12px;
	margin-bottom: 15px;
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

.no-data {
	color: #777;
}

@media ( max-width : 950px) {
	.attendance-layout {
		grid-template-columns: 1fr;
	}
}

.vacation-container {
	max-width: 1180px;
	margin: 0 auto 50px;
	padding: 0 20px;
	font-family: Arial, "Malgun Gothic", sans-serif;
	color: #333;
}

.vacation-title {
	border-bottom: 2px solid #3b78c8;
	padding-bottom: 12px;
	margin-bottom: 24px;
}

.vacation-layout {
	display: grid;
	grid-template-columns: minmax(620px, 1.5fr) minmax(360px, 1fr);
	gap: 28px;
	align-items: start;
}

.vacation-panel {
	background: #fff;
	border: 1px solid #d9d9d9;
	border-radius: 6px;
	padding: 20px;
}

.vacation-panel h3 {
	margin: 0 0 15px;
}

.vacation-table {
	width: 100%;
	border-collapse: collapse;
}

.vacation-table th, .vacation-table td {
	border: 1px solid #d9d9d9;
	padding: 10px 8px;
	text-align: center;
}

.vacation-table th {
	background-color: #f3f7fc;
}

.form-table th {
	width: 130px;
}

.form-table td {
	text-align: left;
}

.form-table input[type="text"], .form-table input[type="date"],
	.form-table input[type="number"] {
	box-sizing: border-box;
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 3px;
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
	padding: 9px 15px;
	color: #fff;
	background-color: #3b78c8;
	text-decoration: none;
	cursor: pointer;
}

.btn-secondary {
	background-color: #777;
}

.btn-danger {
	background-color: #d9534f;
}

.btn-small {
	padding: 5px 10px;
	font-size: 12px;
}

.message {
	padding: 10px 12px;
	margin-bottom: 15px;
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

@media ( max-width : 900px) {
	.vacation-layout {
		grid-template-columns: 1fr;
	}
}
</style>
</head>
<body>
	<layout:headerNav />

	<main id="vacation" class="vacation-container">
		<h2 class="vacation-title">休暇項目設定</h2>

		<c:if test="${param.result == 'vacationRegistered'}">
			<div class="message message-success">休暇項目を登録しました。</div>
		</c:if>
		<c:if test="${param.result == 'vacationUpdated'}">
			<div class="message message-success">休暇項目を修正しました。</div>
		</c:if>
		<c:if test="${param.result == 'vacationDeleted'}">
			<div class="message message-success">休暇項目を削除しました。</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="message message-error">
				<c:out value="${errorMessage}" />
			</div>
		</c:if>

		<div class="vacation-layout">
			<section class="vacation-panel">
				<h3>休暇項目一覧</h3>
				<table class="vacation-table">
					<thead>
						<tr>
							<th>番号</th>
							<th>休暇項目名</th>
							<th>適用期間</th>
							<th>休暇日数</th>
							<th>使用可否</th>
							<th>管理</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty vacationTypes}">
								<tr>
									<td colspan="6">登録された休暇項目はありません。</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="vacationType" items="${vacationTypes}">
									<tr>
										<td><c:out value="${vacationType.vacationTypeId}" /></td>
										<td><c:out value="${vacationType.vacationName}" /></td>
										<td><fmt:formatDate
												value="${vacationType.applyStartDate}" pattern="yyyy-MM-dd" />
											～ <fmt:formatDate value="${vacationType.applyEndDate}"
												pattern="yyyy-MM-dd" /></td>
										<td><c:out value="${vacationType.vacationDays}" /></td>
										<td>${vacationType.useYn == 'Y' ? '使用' : '未使用'}</td>
										<td><a class="btn btn-small"
											href="${pageContext.request.contextPath}/config/vacation-attendance.do?vacationTypeId=${vacationType.vacationTypeId}#vacation">選択</a></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</section>

			<section class="vacation-panel">
				<h3>${not empty selectedVacationType ? '休暇項目修正' : '休暇項目登録'}</h3>
				<form method="post" id="vacationForm">
					<input type="hidden" name="vacationTypeId"
						value="<c:out value='${vacationFormItem.vacationTypeId}' />">
					<table class="vacation-table form-table">
						<tr>
							<th>休暇項目名</th>
							<td><input type="text" name="vacationName" maxlength="50"
								required
								value="<c:out value='${vacationFormItem.vacationName}' />"></td>
						</tr>
						<tr>
							<th>適用開始日</th>
							<td><input type="date" name="applyStartDate" required
								value="${formStartDate}"></td>
						</tr>
						<tr>
							<th>適用終了日</th>
							<td><input type="date" name="applyEndDate" required
								value="${formEndDate}"></td>
						</tr>
						<tr>
							<th>休暇日数</th>
							<td><input type="number" name="vacationDays" min="0"
								step="0.5" placeholder="例: 15"
								value="<c:out value='${vacationFormItem.vacationDays}' />"></td>
						</tr>
						<tr>
							<th>使用可否</th>
							<td><label><input type="radio" name="useYn"
									value="Y"
									${empty vacationFormItem.useYn || vacationFormItem.useYn == 'Y' ? 'checked' : ''}>
									使用</label> <label><input type="radio" name="useYn" value="N"
									${vacationFormItem.useYn == 'N' ? 'checked' : ''}> 未使用</label></td>
						</tr>
					</table>

					<div class="button-area">
						<c:choose>
							<c:when test="${not empty selectedVacationType}">
								<button class="btn" type="submit"
									formaction="${pageContext.request.contextPath}/vacation/type/update.do">修正</button>
								<button class="btn btn-danger" type="submit"
									formaction="${pageContext.request.contextPath}/vacation/type/delete.do"
									formnovalidate onclick="return confirm('この休暇項目を削除しますか？');">削除</button>
							</c:when>
							<c:otherwise>
								<button class="btn" type="submit"
									formaction="${pageContext.request.contextPath}/vacation/type/register.do">登録</button>
							</c:otherwise>
						</c:choose>
						<a class="btn btn-secondary"
							href="${pageContext.request.contextPath}/config/vacation-attendance.do#vacation">初期化</a>
					</div>
				</form>
			</section>
		</div>
	</main>

	<main id="attendance" class="attendance-container">

		<h2 class="attendance-title">勤怠項目設定</h2>

		<%-- 처리 결과 메시지 / 処理結果メッセージ --%>
		<c:if test="${param.result == 'attendanceRegistered'}">
			<div class="message message-success">勤怠項目を登録しました。</div>
		</c:if>

		<c:if test="${param.result == 'attendanceUpdated'}">
			<div class="message message-success">勤怠項目を修正しました。</div>
		</c:if>

		<c:if test="${param.result == 'attendanceDeleted'}">
			<div class="message message-success">勤怠項目を削除しました。</div>
		</c:if>

		<c:if test="${not empty errorMessage}">
			<div class="message message-error">
				<c:out value="${errorMessage}" />
			</div>
		</c:if>

		<div class="attendance-layout">

			<%-- 근태항목 목록 / 勤怠項目リスト --%>
			<section class="attendance-panel">
				<h3>勤怠項目一覧</h3>

				<table class="attendance-table">
					<thead>
						<tr>
							<th>番号</th>
							<th>勤怠項目</th>
							<th>単位</th>
							<th>グループ管理</th>
							<th>休暇控除</th>
							<th>使用可否</th>
							<th>管理</th>
						</tr>
					</thead>

					<tbody>
						<c:choose>
							<c:when test="${empty attendanceTypes}">
								<tr>
									<td colspan="7" class="no-data">登録された勤怠項目はありません。</td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach var="attendanceType" items="${attendanceTypes}">

									<tr>
										<td><c:out value="${attendanceType.attendanceTypeId}" />
										</td>

										<td><c:out value="${attendanceType.attendanceType}" /></td>

										<td><c:out value="${attendanceType.unit}" /></td>

										<td><c:out value="${attendanceType.groupName}" /></td>

										<td><c:choose>
												<c:when test="${attendanceType.vacationTypeId > 0}">

													<c:forEach var="vacationType" items="${vacationTypes}">

														<c:if
															test="${vacationType.vacationTypeId
                                                            == attendanceType.vacationTypeId}">

															<c:out value="${vacationType.vacationName}" />
														</c:if>
													</c:forEach>
												</c:when>

												<c:otherwise>
                                                    -
                                                </c:otherwise>
											</c:choose></td>

										<td>${attendanceType.useYn == 'Y'
                                                ? '使用'
                                                : '未使用'}
										</td>

										<td><a class="btn btn-small"
											href="${pageContext.request.contextPath}/config/vacation-attendance.do?attendanceTypeId=${attendanceType.attendanceTypeId}#attendance">選択</a></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</section>

			<%-- 등록 및 수정 폼 / 登録および修正フォーム --%>
			<section class="attendance-panel">

				<h3>${not empty selectedAttendanceType
                        ? '勤怠項目修正'
                        : '勤怠項目登録'}
				</h3>

				<form method="post" id="attendanceTypeForm">

					<input type="hidden" name="attendanceTypeId"
						value="<c:out value=
                           '${attendanceFormItem.attendanceTypeId}' />">

					<table class="attendance-table form-table">

						<tr>
							<th>勤怠項目</th>
							<td><input type="text" name="attendanceType" maxlength="50"
								required oninvalid="this.setCustomValidity('勤怠項目を入力してください。')"
								oninput="this.setCustomValidity('')"
								placeholder="勤怠項目を入力してください。"
								value="<c:out value=
                                       '${attendanceFormItem.attendanceType}' />">
							</td>
						</tr>

						<tr>
							<th>単位</th>
							<td><select name="unit" required
								oninvalid="this.setCustomValidity('単位を選択してください。')"
								onchange="this.setCustomValidity('')">
									<option value="">選択してください。</option>

									<option value="日"
										${attendanceFormItem.unit == '日'
                                            ? 'selected' : ''}>
										日</option>

									<option value="時間"
										${attendanceFormItem.unit == '時間'
                                            ? 'selected' : ''}>
										時間</option>
							</select></td>
						</tr>

						<tr>
							<th>勤怠グループ</th>
							<td><select name="groupName" required
								oninvalid="this.setCustomValidity('勤怠グループを選択してください。')"
								onchange="this.setCustomValidity('')">
									<option value="">選択してください。</option>

									<option value="休暇"
										${attendanceFormItem.groupName == '休暇'
                                            ? 'selected' : ''}>
										休暇</option>

									<option value="遅刻早退"
										${attendanceFormItem.groupName == '遅刻早退'
                                            ? 'selected' : ''}>
										遅刻早退</option>

									<option value="その他"
										${attendanceFormItem.groupName == 'その他'
                                            ? 'selected' : ''}>
										その他</option>

									<option value="延長勤務"
										${attendanceFormItem.groupName == '延長勤務'
                                            ? 'selected' : ''}>
										延長勤務</option>
							</select></td>
						</tr>

						<tr>
							<th>休暇控除</th>
							<td><select name="vacationTypeId">
									<option value="0">選択なし</option>

									<c:forEach var="vacationType" items="${vacationTypes}">

										<option value="${vacationType.vacationTypeId}"
											${attendanceFormItem.vacationTypeId
                                                == vacationType.vacationTypeId
                                                ? 'selected' : ''}>

											<c:out value="${vacationType.vacationName}" />
										</option>
									</c:forEach>
							</select></td>
						</tr>

						<tr>
							<th>使用可否</th>
							<td>
								<div class="radio-area">
									<label> <input type="radio" name="useYn" value="Y"
										${empty attendanceFormItem.useYn
                                                   || attendanceFormItem.useYn == 'Y'
                                                   ? 'checked' : ''}>
										使用
									</label> <label> <input type="radio" name="useYn" value="N"
										${attendanceFormItem.useYn == 'N'
                                                   ? 'checked' : ''}>
										未使用
									</label>
								</div>
							</td>
						</tr>
					</table>

					<div class="button-area">
						<c:choose>
							<c:when test="${not empty selectedAttendanceType}">

								<button class="btn" type="submit"
									formaction="${pageContext.request.contextPath}/attendance/type/update.do">修正</button>

								<button class="btn btn-danger" type="submit"
									formaction="${pageContext.request.contextPath}/attendance/type/delete.do"
									formnovalidate onclick="return confirm('この勤怠項目を削除しますか？');">削除</button>
							</c:when>

							<c:otherwise>
								<button class="btn" type="submit"
									formaction="${pageContext.request.contextPath}/attendance/type/register.do">登録</button>
							</c:otherwise>
						</c:choose>

						<a class="btn btn-secondary"
							href="${pageContext.request.contextPath}/config/vacation-attendance.do#attendance">初期化</a>
					</div>
				</form>
			</section>
		</div>
	</main>

	<layout:footer />
</body>
</html>
