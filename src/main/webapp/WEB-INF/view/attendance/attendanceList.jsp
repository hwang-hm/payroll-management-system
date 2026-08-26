<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>근태조회</title>
<style>
/* Layout Base */
.attendance-container { max-width: 1600px; margin: 20px auto 50px; padding: 0 20px; font-family: 'Malgun Gothic', Arial, sans-serif; color: #333; }
.page-title { border-bottom: 2px solid #3b78c8; padding-bottom: 10px; margin-bottom: 8px; font-size: 22px; }
.description { margin-bottom: 20px; color: #666; font-size: 13px; }

/* Filter Section */
.filter-panel { display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px; }
.filter-left, .filter-right { display: flex; align-items: center; gap: 8px; }
.filter-panel select, .filter-panel input { padding: 4px 8px; border: 1px solid #cbd2dc; border-radius: 3px; font-size: 13px; }
.btn-search { padding: 4px 12px; border: 0; border-radius: 3px; background: #3b78c8; color: #fff; font-weight: bold; cursor: pointer; font-size: 13px; }

/* Main Table */
.calendar-table-container { border: 1px solid #d7dce3; background: #fff; overflow-x: auto; }
.calendar-table { width: 100%; border-collapse: collapse; font-size: 12px; text-align: center; table-layout: fixed; }
.calendar-table th, .calendar-table td { border: 1px solid #e0e6ed; padding: 0; height: 32px; vertical-align: middle; }

/* Fixed Columns Header Color */
.calendar-table th.col-info { background: #f8fafc; color: #3b78c8; font-weight: bold; }
.calendar-table th.col-meta { background: #f8fafc; color: #333; font-weight: bold; }

/* Width Definitions */
.w-type { width: 70px; }
.w-empno { width: 90px; }
.w-name { width: 80px; }
.w-dept { width: 80px; }
.w-pos { width: 60px; }
.w-summary { width: 140px; text-align: left; padding-left: 8px !important; color: #444; }
.w-deduct { width: 70px; font-weight: bold; }

/* Calendar Grid Section */
.calendar-grid-cell { padding: 0 !important; width: 416px; } /* 16 cols * 26px */
.sub-grid-table { width: 100%; border-collapse: collapse; table-layout: fixed; }
.sub-grid-table td { height: 32px; border: 1px solid #e0e6ed; border-top: 0; border-left: 0; text-align: center; }
.sub-grid-table tr:last-child td { border-bottom: 0; }
.sub-grid-table td:last-child { border-right: 0; }

/* Header Days Color */
.day-num { font-weight: bold; font-size: 12px; }
.day-sat { color: #2b6cb0; background-color: #f0f7ff !important; } /* 토요일 */
.day-sun { color: #e53e3e; background-color: #fff5f5 !important; } /* 일요일 */

/* Attendance Red Dot */
.attendance-dot { display: inline-block; width: 8px; height: 8px; background-color: #ef4444; border-radius: 50%; }

/* Pagination */
.pagination { margin-top: 20px; display: flex; justify-content: center; gap: 4px; }
.pagination a { padding: 5px 10px; border: 1px solid #cbd2dc; text-decoration: none; color: #333; font-size: 12px; }
.pagination a.current { color: #fff; background: #3b78c8; border-color: #3b78c8; font-weight: bold; }
</style>
</head>
<body>
	<layout:headerNav />
	<main class="attendance-container">
		<h2 class="page-title">근태조회</h2>
		<p class="description">사원별 근태현황을 한 번에 보실 수 있습니다. 월별, 상세 근태내역도 확인합니다.</p>

		<!-- 상단 검색 및 필터 바 -->
		<div class="filter-panel">
			<form class="filter-left" method="get" action="${pageContext.request.contextPath}/attendance/list.do">
				<select name="year">
					<c:forEach var="y" begin="2020" end="2030">
						<option value="${y}" ${selectedYear == y ? 'selected' : ''}>${y} 년</option>
					</c:forEach>
				</select>
				<select name="month">
					<c:forEach var="m" begin="1" end="12">
						<option value="${m}" ${selectedMonth == m ? 'selected' : ''}>
							<fmt:formatNumber value="${m}" pattern="00" /> 월
						</option>
					</c:forEach>
				</select>
				<button class="btn-search" type="submit">조회</button>
			</form>
			<div class="filter-right">
				<select disabled><option>재직</option></select>
				<select disabled><option>구분별</option></select>
				<select disabled><option>부서별</option></select>
				<select disabled><option>직급별</option></select>
				<button disabled style="padding: 4px 8px; font-size: 12px; cursor: pointer;">⚡ 정렬기준 설정하기</button>
			</div>
		</div>

		<!-- 달력 Grid 테이블 -->
		<div class="calendar-table-container">
			<table class="calendar-table">
				<thead>
					<tr>
						<th class="col-meta w-type">구분</th>
						<th class="col-info w-empno">사원번호</th>
						<th class="col-info w-name">성명</th>
						<th class="col-info w-dept">부서</th>
						<th class="col-info w-pos">직위</th>
						<!-- 2행 헤더 달력 (1~16 / 17~31일) -->
						<th class="calendar-grid-cell">
							<table class="sub-grid-table">
								<tr>
									<c:forEach var="d" begin="1" end="16">
										<c:set var="dow" value="${dayOfWeekMap[d]}" />
										<td class="day-num ${dow == 7 ? 'day-sat' : (dow == 1 ? 'day-sun' : '')}">${d}</td>
									</c:forEach>
								</tr>
								<tr>
									<c:forEach var="d" begin="17" end="31">
										<c:choose>
											<c:when test="${d <= daysInMonth}">
												<c:set var="dow" value="${dayOfWeekMap[d]}" />
												<td class="day-num ${dow == 7 ? 'day-sat' : (dow == 1 ? 'day-sun' : '')}">${d}</td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<!-- 31일이 없는 달인 경우 빈 칸 채움 -->
									<c:if test="${daysInMonth < 31}">
										<c:forEach begin="${daysInMonth + 1}" end="31">
											<td></td>
										</c:forEach>
									</c:if>
								</tr>
							</table>
						</th>
						<th class="col-meta w-summary">합계</th>
						<th class="col-meta w-deduct">휴가공제</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty empListPage.empListData}">
							<tr>
								<td colspan="8" style="padding: 20px;">등록된 사원이 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="employee" items="${empListPage.empListData}">
								<tr>
									<td class="w-type">정규직</td>
									<td class="w-empno">No-${employee.empId}</td>
									<td class="w-name"><c:out value="${employee.nameKor}" /></td>
									<td class="w-dept"><c:out value="${employee.deptName}" /></td>
									<td class="w-pos"><c:out value="${employee.positionName}" /></td>

									<!-- 2행 31일 Cell Grid -->
									<td class="calendar-grid-cell">
										<table class="sub-grid-table">
											<!-- 상단: 1일 ~ 16일 -->
											<tr>
												<c:forEach var="d" begin="1" end="16">
													<c:set var="dow" value="${dayOfWeekMap[d]}" />
													<c:set var="hasAtt" value="${not empty empAttendanceDaysMap[employee.empId] && empAttendanceDaysMap[employee.empId].contains(d)}" />
													<td class="${dow == 7 ? 'day-sat' : (dow == 1 ? 'day-sun' : '')}">
														<c:if test="${hasAtt}">
															<span class="attendance-dot"></span>
														</c:if>
													</td>
												</c:forEach>
											</tr>
											<!-- 하단: 17일 ~ 31일 -->
											<tr>
												<c:forEach var="d" begin="17" end="31">
													<c:choose>
														<c:when test="${d <= daysInMonth}">
															<c:set var="dow" value="${dayOfWeekMap[d]}" />
															<c:set var="hasAtt" value="${not empty empAttendanceDaysMap[employee.empId] && empAttendanceDaysMap[employee.empId].contains(d)}" />
															<td class="${dow == 7 ? 'day-sat' : (dow == 1 ? 'day-sun' : '')}">
																<c:if test="${hasAtt}">
																	<span class="attendance-dot"></span>
																</c:if>
															</td>
														</c:when>
														<c:otherwise>
															<td></td>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</tr>
										</table>
									</td>

									<!-- 합계 및 휴가공제 -->
									<td class="w-summary">
										<c:if test="${not empty empAttendanceDaysMap[employee.empId] && empAttendanceDaysMap[employee.empId].size() > 0}">
											• 조회: ${empAttendanceDaysMap[employee.empId].size()}건
										</c:if>
									</td>
									<td class="w-deduct">0</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table> 
		</div>

		<!-- Pagination -->
		<c:if test="${empListPage.totalPage > 0}">
			<div class="pagination">
				<c:forEach var="page" begin="${empListPage.startPage}" end="${empListPage.endPage}">
					<a class="${page == empListPage.currentPage ? 'current' : ''}"
						href="${pageContext.request.contextPath}/attendance/list.do?pageNo=${page}&year=${selectedYear}&month=${selectedMonth}">${page}</a>
				</c:forEach>
			</div>
		</c:if>
	</main>
	<layout:footer />
</body>
</html>
