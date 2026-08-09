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
				<!-- selectWorker(this, 실제_emp_id, '사원이름') 형식으로 통일 -->
				<tr onclick="selectWorker(this, 1, '김철수')">
					<td>일용직</td>
					<td>김철수</td>
					<td>관리팀</td>
					<td style="text-align: right; font-weight: 600;">98,766</td>
				</tr>
				<tr onclick="selectWorker(this, 2, '박영희')">
					<td>일용직</td>
					<td>박영희</td>
					<td>관리팀</td>
					<td style="text-align: right; font-weight: 600;">99,100</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<script>
// 사원 행 클릭 시 실행되는 함수
function selectWorker(element, empId, empName) {
	// 1. 이전 선택된 행의 active 클래스 제거 후, 클릭된 행에 active 추가
	const rows = document.querySelectorAll('#workerListBody tr');
	rows.forEach(r => r.classList.remove('active'));
	element.classList.add('active');

	// 2. 우측 패널 헤더의 [선택된 사원 이름] 변경
	const targetEmpName = document.getElementById('targetEmpName');
	if (targetEmpName) {
		targetEmpName.innerText = empName;
	}

	// 3. 메인 저장 폼(hidden input)의 empId 설정
	const selectedEmpIdInput = document.getElementById('selectedEmpId');
	if (selectedEmpIdInput) {
		selectedEmpIdInput.value = empId;
	}

	// 4. 우측 급여 상세 및 공제 항목 DB 동적 로드 함수 호출
	if (typeof loadEmployeeSalaryDetail === 'function') {
		loadEmployeeSalaryDetail(empId, empName);
	} else {
		console.error("loadEmployeeSalaryDetail 함수가 정의되지 않았습니다.");
	}
}
</script>