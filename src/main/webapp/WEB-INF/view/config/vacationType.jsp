<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<c:set var="formItem"
	value="${not empty selectedVacationType ? selectedVacationType : formVacationType}" />
<fmt:formatDate value="${formItem.applyStartDate}" pattern="yyyy-MM-dd"
	var="formStartDate" />
<fmt:formatDate value="${formItem.applyEndDate}" pattern="yyyy-MM-dd"
	var="formEndDate" />

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ヘキサゴンゾーン - 休暇項目設定</title>
<style>
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

@media (max-width: 900px) {
	.vacation-layout {
		grid-template-columns: 1fr;
	}
}
</style>
</head>
<body>
	<layout:headerNav />

	<main class="vacation-container">
		<h2 class="vacation-title">休暇項目設定</h2>

		<c:if test="${param.result == 'registered'}">
			<div class="message message-success">休暇項目を登録しました。</div>
		</c:if>
		<c:if test="${param.result == 'updated'}">
			<div class="message message-success">休暇項目を修正しました。</div>
		</c:if>
		<c:if test="${param.result == 'deleted'}">
			<div class="message message-success">休暇項目を削除しました。</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="message message-error"><c:out value="${errorMessage}" /></div>
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
										<td><fmt:formatDate value="${vacationType.applyStartDate}"
												pattern="yyyy-MM-dd" /> ～ <fmt:formatDate
												value="${vacationType.applyEndDate}" pattern="yyyy-MM-dd" /></td>
										<td><c:out value="${vacationType.vacationDays}" /></td>
										<td>${vacationType.useYn == 'Y' ? '使用' : '未使用'}</td>
										<td><a class="btn btn-small"
											href="${pageContext.request.contextPath}/vacation/type/list.do?vacationTypeId=${vacationType.vacationTypeId}">選択</a></td>
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
						value="<c:out value='${formItem.vacationTypeId}' />">
					<table class="vacation-table form-table">
						<tr>
							<th>休暇項目名</th>
							<td><input type="text" name="vacationName" maxlength="50"
								required value="<c:out value='${formItem.vacationName}' />"></td>
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
								value="<c:out value='${formItem.vacationDays}' />"></td>
						</tr>
						<tr>
							<th>使用可否</th>
							<td><label><input type="radio" name="useYn" value="Y"
								${empty formItem.useYn || formItem.useYn == 'Y' ? 'checked' : ''}>
									使用</label> <label><input type="radio" name="useYn" value="N"
								${formItem.useYn == 'N' ? 'checked' : ''}> 未使用</label></td>
						</tr>
					</table>

					<div class="button-area">
						<c:choose>
							<c:when test="${not empty selectedVacationType}">
								<button class="btn" type="submit"
									formaction="${pageContext.request.contextPath}/vacation/type/update.do">修正</button>
								<button class="btn btn-danger" type="submit"
									formaction="${pageContext.request.contextPath}/vacation/type/delete.do"
									formnovalidate
									onclick="return confirm('この休暇項目を削除しますか？');">削除</button>
							</c:when>
							<c:otherwise>
								<button class="btn" type="submit"
									formaction="${pageContext.request.contextPath}/vacation/type/register.do">登録</button>
							</c:otherwise>
						</c:choose>
						<a class="btn btn-secondary"
							href="${pageContext.request.contextPath}/vacation/type/list.do">初期化</a>
					</div>
				</form>
			</section>
		</div>
	</main>

	<layout:footer />
</body>
</html>
