<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>休暇項目設定</title>
<style>
body {
	font-family: Arial, "Malgun Gothic", sans-serif;
	margin: 30px;
	color: #333;
}

h2 {
	border-bottom: 2px solid #3b78c8;
	padding-bottom: 10px;
	width: 500px;
}

table {
	border-collapse: collapse;
	margin-top: 15px;
}

th, td {
	border: 1px solid #d9d9d9;
	padding: 9px 12px;
	text-align: center;
}

th {
	background-color: #f3f7fc;
}

input[type="text"], input[type="date"], input[type="number"] {
	box-sizing: border-box;
	width: 200px;
	padding: 6px;
}

button {
	border: 0;
	border-radius: 3px;
	padding: 8px 14px;
	color: white;
	background-color: #3b78c8;
	cursor: pointer;
}

button[type="reset"] {
	background-color: #8a8a8a;
}
</style>

</head>
<body>

	<h2>休暇項目設定</h2>

	<form action="#" method="post">
		<table border="1">
			<tr>
				<th>休暇項目名</th>
				<td><input type="text" name="vacationName" maxlength="20">
				</td>
			</tr>

			<tr>
				<th>適用開始日</th>
				<td><input type="date" name="applyStartDate"></td>
			</tr>

			<tr>
				<th>適用終了日</th>
				<td><input type="date" name="applyEndDate"></td>
			</tr>

			<tr>
				<th>休暇日数</th>
				<td><input type="number" name="vacationDays" min="0" step="0.5"
					placeholder="例: 15"></td>
			</tr>

			<tr>
				<th>使用可否</th>
				<td><label> <input type="radio" name="useYn" value="Y"
						checked> 使用
				</label> <label> <input type="radio" name="useYn" value="N">
						未使用
				</label></td>
			</tr>
		</table>

		<br>

		<button type="button">登録</button>
		<button type="reset">初期化</button>
	</form>

	<br>

	<table border="1">
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
			<tr>
				<td colspan="6">登録された休暇項目はありません。</td>
			</tr>
		</tbody>
	</table>


</body>
</html>