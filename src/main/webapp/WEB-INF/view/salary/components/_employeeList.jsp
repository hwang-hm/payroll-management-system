<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel">
	<div class="panel-header">
		<button type="button" class="btn btn-primary" onclick="addWorker()">➕
			신규추가</button>
		<div style="display: flex; gap: 6px;">
			<button type="button" class="btn btn-danger-light">전체삭제</button>
			<button type="button" class="btn btn-secondary">선택삭제</button>
		</div>
	</div>

	<div style="overflow-x: auto;">
		<table class="custom-table">
			<thead>
				<tr>
					<th>구분</th>
					<th>성명</th>
					<th>부서</th>
					<th>실지급액</th>
				</tr>
			</thead>
			<tbody id="workerListBody">
				<!-- onclick 시 empId와 사원이름을 함께 전달 -->
				<tr onclick="selectWorker(this, 1, '김철수', 98766)">
					<td>일용직</td>
					<td>김철수</td>
					<td>관리팀</td>
					<td style="text-align: right; font-weight: 600;">98,766</td>
				</tr>
				<tr class="active" onclick="selectWorker(2, 99100, '박영희', 99100)">
					<td>일용직</td>
					<td>박영희</td>
					<td>관리팀</td>
					<td style="text-align: right; font-weight: 600;">99,100</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>