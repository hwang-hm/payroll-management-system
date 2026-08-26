<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<%-- 화면에 표시할 근태항목 선택 / 画面に表示する勤怠項目を選択 --%>
<c:set var="formItem"
	value="${not empty selectedAttendanceType
        ? selectedAttendanceType
        : formAttendanceType}" />

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ヘキサゴンゾーン - 勤怠項目設定</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />

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
</style>
</head>

<body>
	<layout:headerNav />

	<main class="attendance-container">

		<h2 class="attendance-title">勤怠項目設定</h2>

		<%-- 처리 결과 메시지 / 処理結果メッセージ --%>
		<c:if test="${param.result == 'registered'}">
			<div class="message message-success">勤怠項目を登録しました。</div>
		</c:if>

		<c:if test="${param.result == 'updated'}">
			<div class="message message-success">勤怠項目を修正しました。</div>
		</c:if>

		<c:if test="${param.result == 'deleted'}">
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
											href="${pageContext.request.contextPath}/attendance/type/list.do?attendanceTypeId=${attendanceType.attendanceTypeId}">選択</a></td>
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
                           '${formItem.attendanceTypeId}' />">

					<table class="attendance-table form-table">

						<tr>
							<th>勤怠項目</th>
							<td><input type="text" name="attendanceType" maxlength="50"
								required
								oninvalid="this.setCustomValidity('勤怠項目を入力してください。')"
								oninput="this.setCustomValidity('')"
								placeholder="勤怠項目を入力してください。"
								value="<c:out value=
                                       '${formItem.attendanceType}' />">
							</td>
						</tr>

						<tr>
							<th>単位</th>
							<td><select name="unit" required
								oninvalid="this.setCustomValidity('単位を選択してください。')"
								onchange="this.setCustomValidity('')">
									<option value="">選択してください。</option>

									<option value="日"
										${formItem.unit == '日'
                                            ? 'selected' : ''}>
										日</option>

									<option value="時間"
										${formItem.unit == '時間'
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
										${formItem.groupName == '休暇'
                                            ? 'selected' : ''}>
										休暇</option>

									<option value="遅刻早退"
										${formItem.groupName == '遅刻早退'
                                            ? 'selected' : ''}>
										遅刻早退</option>

									<option value="その他"
										${formItem.groupName == 'その他'
                                            ? 'selected' : ''}>
										その他</option>

									<option value="延長勤務"
										${formItem.groupName == '延長勤務'
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
											${formItem.vacationTypeId
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
										${empty formItem.useYn
                                                   || formItem.useYn == 'Y'
                                                   ? 'checked' : ''}>
										使用
									</label> <label> <input type="radio" name="useYn" value="N"
										${formItem.useYn == 'N'
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
							href="${pageContext.request.contextPath}/attendance/type/list.do">初期化</a>
					</div>
				</form>
			</section>
		</div>
	</main>

	<layout:footer />
</body>
</html>
