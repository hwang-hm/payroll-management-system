<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>休暇項目設定</title>
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
				<th>使用可否<td>
				<label>
					<input type="radio" name="useYn" value="Y" checked> 使用    
				</label>
				<label>
					<input type="radio" name="useYn" value="N"> 未使用 
				</label>
			</td>
		</tr>
	</table>

	<br>

	<button type="submit">登録</button>
	<button type="reset">初期化</button>
</form>

<br>

<table border="1">
    <thead>
        <tr>
            <th>番号</th>
            <th>休暇項目名</th>
            <th>適用期間</th>
            <th>使用可否</th>
            <th>管理</th>
        </tr>
    </thead>

    <tbody>
        <tr>
            <td colspan="5">登録された休暇項目はありません。</td>
        </tr>
    </tbody>
</table>


</body>
</html>