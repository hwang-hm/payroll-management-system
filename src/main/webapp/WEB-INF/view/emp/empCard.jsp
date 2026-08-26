<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ヘキサゴンゾーン - 人事記録カード</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/empCard.css" />

</head>

<body>

	<layout:headerNav />

	<div class="page-wrap">

		<!-- 상단 제목 및 작업 버튼 / 上部タイトル・操作ボタン -->
		<div class="toolbar">

			<h2>人事記録カード</h2>

			<div class="toolbar-actions">

				<button type="button" class="btn btn-primary"
					onclick="openEmployeeModal()">社員選択</button>

				<button type="button" class="btn btn-print" onclick="window.print()">
					印刷</button>

			</div>

		</div>

		<!-- 인사기록카드 본문 / 人事記録カード本文 -->
		<div class="card-background">

			<div class="card-pages">

				<!-- 왼쪽 페이지 / 左ページ -->
				<section class="card-page">

					<div class="left-top">

						<div class="photo-cell">

							<c:choose>

								<c:when test="${not empty empCardData.emp.photoPath}">

									<img
										src="${pageContext.request.contextPath}/${empCardData.emp.photoPath}"
										alt="社員写真">

								</c:when>

								<c:otherwise>

									<div class="photo-placeholder">写真</div>

								</c:otherwise>

							</c:choose>

						</div>

						<div class="basic-info">

							<table>

								<colgroup>
									<col style="width: 23%;">
									<col style="width: 27%;">
									<col style="width: 23%;">
									<col style="width: 27%;">
								</colgroup>

								<tr class="basic-title-row">

									<th colspan="2">人事記録カード</th>

									<th>社員番号</th>

									<td><c:out value="${empCardData.emp.empId}" default="" />
									</td>

								</tr>

								<tr>

									<th>入社日</th>

									<td colspan="3"><fmt:formatDate
											value="${empCardData.emp.hireDate}" pattern="yyyy-MM-dd" />

									</td>

								</tr>

								<tr>

									<th>退職日</th>

									<td colspan="3"><fmt:formatDate
											value="${empCardData.emp.retireDate}" pattern="yyyy-MM-dd" />

									</td>

								</tr>

								<tr>

									<th>氏名（韓国語）</th>

									<td><c:out value="${empCardData.emp.nameKor}" default="" />

									</td>

									<th>氏名（英語）</th>

									<td><c:out value="${empCardData.emp.nameEng}" default="" />

									</td>

								</tr>

								<tr>

									<th>住民登録番号</th>

									<td><c:out value="${empCardData.emp.residentNo}"
											default="-" /></td>

									<th>雇用形態</th>

									<td><c:out value="${empCardData.emp.employmentType}"
											default="" /></td>

								</tr>

								<tr>

									<th>住所</th>

									<td colspan="3" style="text-align: left;"><c:out
											value="${empCardData.emp.address}" default="" /></td>

								</tr>

								<tr>

									<th>携帯電話</th>

									<td><c:out value="${empCardData.emp.mobileNum}" default="" />

									</td>

									<th>連絡先</th>

									<td><c:out value="${empCardData.emp.phoneNum}" default="" />

									</td>

								</tr>

								<tr>

									<th>E-Mail</th>

									<td colspan="3" style="text-align: left;"><c:out
											value="${empCardData.emp.email}" default="" /></td>

								</tr>

							</table>

						</div>

					</div>

					<div class="section-block">

						<!-- 가족사항 / 家族事項 -->
						<div class="section-label">
							家<br>族<br>事<br>項
						</div>

						<div class="section-content">

							<table>

								<colgroup>

									<col style="width: 12%;">
									<col style="width: 15%;">
									<col style="width: 27%;">
									<col style="width: 16%;">
									<col style="width: 15%;">
									<col style="width: 15%;">

								</colgroup>

								<tr class="section-header">

									<th>続柄</th>
									<th>氏名</th>
									<th>住民登録番号</th>
									<th>同居有無</th>
									<th>国民年金</th>
									<th>備考</th>

								</tr>

								<c:forEach var="dependent" items="${empCardData.dependents}">

									<tr>

										<td><c:out value="${dependent.relation}" /></td>

										<td><c:out value="${dependent.familyName}" /></td>

										<td><c:out value="${dependent.residentNo}" default="-" />
										</td>

										<td><c:out value="${dependent.cohabitationYN}" /></td>

										<td><c:out value="${dependent.deductionYN}" /></td>

										<td></td>

									</tr>

								</c:forEach>

								<c:if test="${empty empCardData.dependents}">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td>-</td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:if>

								<c:forEach begin="1" end="5">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td>-</td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<div class="section-block">

						<!-- 학력 / 学歴 -->
						<div class="section-label">
							学<br>歴
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>学校名</th>
									<th>入学年月</th>
									<th>卒業年月</th>
									<th>専攻</th>
									<th>修了区分</th>

								</tr>

								<c:forEach begin="1" end="5">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<div class="section-block">

						<!-- 병역 / 兵役 -->
						<div class="section-label">
							兵<br>役
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>除隊区分</th>
									<th colspan="2">未履行理由</th>

								</tr>

								<tr class="section-header">

									<th>軍種</th>
									<th>最終階級</th>
									<th>服務期間</th>

								</tr>

								<tr class="empty-row">

									<td></td>
									<td></td>
									<td></td>

								</tr>

							</table>

						</div>

					</div>

					<div class="section-block">

						<!-- 경력 / 経歴 -->
						<div class="section-label">
							経<br>歴
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>会社名</th>
									<th>入社日</th>
									<th>退職日</th>
									<th>最終役職</th>
									<th>担当業務</th>

								</tr>

								<c:forEach begin="1" end="5">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<div class="section-block">

						<!-- 자격 및 면허 / 資格・免許 -->
						<div class="section-label">
							資<br>格<br>/<br>免<br>許
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>種類</th>
									<th>取得日</th>
									<th>発行機関</th>
									<th>備考</th>

								</tr>

								<c:forEach begin="1" end="4">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<div class="section-block">

						<!-- 어학능력 / 語学能力 -->
						<div class="section-label">
							語<br>学<br>能<br>力
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>外国語名</th>
									<th>試験</th>
									<th>公認スコア</th>
									<th>取得日</th>
									<th>読解</th>
									<th>作文</th>
									<th>会話</th>

								</tr>

								<c:forEach begin="1" end="3">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

				</section>

				<!-- 오른쪽 페이지 / 右ページ -->
				<section class="card-page">

					<div class="card-title">人事記録カード</div>

					<div class="section-block">

						<!-- 교육사항 / 教育事項 -->
						<div class="section-label">
							教<br>育<br>事<br>項
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>教育区分</th>
									<th>教育名</th>
									<th>期間（開始）</th>
									<th>期間（終了）</th>
									<th>教育機関</th>
									<th>教育費</th>
									<th>還付教育費</th>

								</tr>

								<c:forEach begin="1" end="10">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<div class="section-block">

						<!-- 상벌사항 / 賞罰事項 -->
						<div class="section-label">
							賞<br>罰<br>事<br>項
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>区分</th>
									<th>賞罰名</th>
									<th>賞罰権者</th>
									<th>賞罰日</th>
									<th>賞罰内容</th>
									<th>備考</th>

								</tr>

								<c:forEach begin="1" end="8">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<div class="section-block">

						<!-- 인사이동 / 人事異動 -->
						<div class="section-label">
							人<br>事<br>異<br>動
						</div>

						<div class="section-content">

							<table>

								<tr class="section-header">

									<th>異動区分</th>
									<th>異動日</th>
									<th>部署</th>
									<th>役職</th>
									<th>職責および担当業務</th>
									<th>備考</th>

								</tr>

								<c:forEach begin="1" end="9">

									<tr class="empty-row">

										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>

								</c:forEach>

							</table>

						</div>

					</div>

					<div class="section-block">

						<!-- 퇴직사항 / 退職事項 -->
						<div class="section-label">
							退<br>職<br>事<br>項
						</div>

						<div class="section-content">

							<table class="retirement-grid">

								<tr class="section-header">

									<th>退職区分</th>
									<th>退職日</th>
									<th>退職理由</th>
									<th>退職金</th>
									<th>退職後連絡先</th>

								</tr>

								<tr>

									<td><c:out
											value="${empCardData.retirement.retirementType}" default="" />

									</td>

									<td><fmt:formatDate
											value="${empCardData.retirement.retirementDate}"
											pattern="yyyy-MM-dd" /></td>

									<td><c:out
											value="${empCardData.retirement.retirementReason}" default="" />

									</td>

									<td><c:if
											test="${not empty empCardData.severancePay.netPayment}">

											<fmt:formatNumber
												value="${empCardData.severancePay.netPayment}"
												pattern="#,##0" />

										</c:if></td>

									<td><c:out
											value="${empCardData.retirement.contactAfterRetirement}"
											default="" /></td>

								</tr>

							</table>

						</div>

					</div>

				</section>

			</div>

		</div>

	</div>

	<!-- 사원 선택 모달 / 社員選択モーダル -->
	<div id="employeeModal" class="modal-backdrop">

		<div class="modal">

			<div class="modal-header">

				<h3>人事記録カード 社員選択</h3>

				<button type="button" class="modal-close"
					onclick="closeEmployeeModal()">&times;</button>

			</div>

			<form id="employeeSelectForm" method="post"
				action="${pageContext.request.contextPath}/emp/empCard.do">

				<input type="hidden" id="selectedEmpId" name="empId">

				<div class="modal-body">

					<div class="employee-table-wrap">

						<table class="employee-table">

							<thead>

								<tr>

									<th style="width: 14%;">区分</th>
									<th style="width: 16%;">社員番号</th>
									<th style="width: 18%;">氏名</th>
									<th style="width: 20%;">部署</th>
									<th style="width: 16%;">役職</th>
									<th style="width: 16%;">状態</th>

								</tr>

							</thead>

							<tbody>

								<c:forEach var="employee" items="${empList}">

									<tr class="employee-row" data-emp-id="${employee.empId}"
										onclick="selectEmployee(this)">

										<td><c:out value="${employee.employmentType}" /></td>
										<td><c:out value="${employee.empId}" /></td>
										<td><c:out value="${employee.nameKor}" /></td>
										<td><c:out value="${employee.deptName}" /></td>
										<td><c:out value="${employee.positionName}" /></td>

										<td><c:choose>

												<c:when test="${employee.status eq 'N'}">
													退職
												</c:when>

												<c:otherwise>
													在職
												</c:otherwise>

											</c:choose></td>

									</tr>

								</c:forEach>

								<c:if test="${empty empList}">

									<tr>

										<td colspan="6" style="height: 100px;">該当する社員情報がありません。</td>

									</tr>

								</c:if>

							</tbody>

						</table>

					</div>

				</div>

				<div class="modal-footer">

					<button type="button" class="btn btn-primary"
						onclick="submitSelectedEmployee()">社員選択</button>

					<button type="button" class="btn btn-secondary"
						onclick="closeEmployeeModal()">キャンセル</button>

				</div>

			</form>

		</div>

	</div>

	<!-- 사원 선택 모달 제어 / 社員選択モーダル制御 -->
	<script>
		function openEmployeeModal() {

			document.getElementById("employeeModal").style.display = "flex";

		}

		function closeEmployeeModal() {

			document.getElementById("employeeModal").style.display = "none";

		}

		function selectEmployee(row) {

			const rows = document.querySelectorAll(".employee-row");

			rows.forEach(function(item) {

				item.classList.remove("selected");

			});

			row.classList.add("selected");

			document.getElementById("selectedEmpId").value = row.dataset.empId;

		}

		function submitSelectedEmployee() {

			const empId = document.getElementById("selectedEmpId").value;

			if (!empId) {

				alert("社員を選択してください。");

				return;

			}

			document.getElementById("employeeSelectForm").submit();

		}

		document.getElementById("employeeModal").addEventListener("click",
				function(event) {

					if (event.target === this) {

						closeEmployeeModal();

					}

				});
	</script>

</body>
</html>