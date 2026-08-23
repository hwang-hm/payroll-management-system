<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 급여 목록 데이터 존재 여부 확인 -->
<!-- 給与リストデータの存在有無の確認 -->
<c:choose>
	<c:when test="${not empty salaryList}">
		<!-- 급여 목록 순회 및 테이블 행(tr) 출력 -->
		<!-- 給与リストのループ処理およびテーブル行(tr)の出力 -->
		<c:forEach var="item" items="${salaryList}">
			<!-- JavaScript 합산 및 행 선택 처리를 위한 data 속성 세팅 -->
			<!-- JavaScriptの合算および行選択処理のためのdata属性の設定 -->
			<tr data-emp-id="${item.empId}"
				data-total-pay="${item.totalPay != null ? item.totalPay : 0}"
				data-total-deduct="${item.totalDeduct != null ? item.totalDeduct : 0}"
				data-net-pay="${item.netPay != null ? item.netPay : 0}"
				onclick="selectWorker(this, '${item.empId}', '${item.nameKor}')">
				<td>${item.employmentType}</td>
				<td>${item.nameKor}</td>
				<td>${item.deptName}</td>
				<td style="text-align: right; font-weight: 600;"><fmt:formatNumber
						value="${item.netPay}" pattern="#,##0" />円</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<!-- 저장된 급여 내역이 없는 경우의 예외 처리 -->
		<!-- 保存された給与明細がない場合の例外処理 -->
		<tr>
			<td colspan="4"
				style="text-align: center; padding: 20px; color: #888;">保存された給与明細がありません。</td>
		</tr>
	</c:otherwise>
</c:choose>