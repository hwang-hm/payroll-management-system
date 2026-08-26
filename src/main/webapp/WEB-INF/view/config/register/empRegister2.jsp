<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ヘキサゴンゾーン - 社員情報2</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/empRegister2.css" />
</head>
<body>

	<layout:headerNav />

	<c:set var="savedEmpNo" value="${emp.empId}" />

	<div class="container">
		<!-- 페이지 안내 / ページ案内 -->
		<div class="page-header">
			<h2>👔 社員情報2</h2>
			<p>基本情報を保存した後、資格・免許、語学能力、教育訓練、賞罰、発令、推薦・保証、退職情報を入力します。</p>
		</div>

		<c:if test="${empty emp}">
			<div class="guide-box">社員の基本情報が保存されていません。基本情報を保存してからご利用ください。</div>
		</c:if>

		<c:if test="${not empty emp}">
			<form id="empInfo2Form"
				action="${pageContext.request.contextPath}/config/register2.do"
				method="post">
				<input type="hidden" id="savedEmpNo" name="empId"
					value="${savedEmpNo}">

				<div class="emp-layout">
					<!-- 좌측 사원 요약 및 바로가기 / 左側社員概要・ショートカット -->
					<div class="side-panel">
						<div class="profile-card">
							<div class="profile-img-box">
								<span>📷</span><span>社員写真</span>
							</div>

							<table class="profile-summary">
								<tr>
									<th>社員番号</th>
									<td>${savedEmpNo}</td>
								</tr>
								<tr>
									<th>氏名</th>
									<td>${emp.nameKor}</td>
								</tr>
								<tr>
									<th>部署</th>
									<td><c:forEach var="dept" items="${deptList}">
											<c:if test="${dept.deptId == emp.deptId}">
										${dept.deptName}
										</c:if>
										</c:forEach></td>
								</tr>
								<tr>
									<th>役職</th>
									<td><c:forEach var="position" items="${positionList}">
											<c:if test="${position.positionId == emp.positionId}">
										${position.positionName}
										</c:if>
										</c:forEach></td>
								</tr>
								<tr>
									<th>入社日</th>
									<td>${emp.hireDate}</td>
								</tr>
							</table>

							<div class="profile-btn-group">
								<button type="button" class="btn btn-outline btn-sm">登録</button>
								<button type="button" class="btn btn-outline btn-sm">削除</button>
							</div>
							<button type="button" class="btn btn-outline btn-sm"
								style="width: 100%;"
								onclick="location.href='${pageContext.request.contextPath}/emp/empCard.do?empId=${emp.empId}'">
								人事記録カードを見る</button>
						</div>

						<div class="side-menu">
							<div class="side-menu-title">ショートカットメニュー</div>
							<ul>
								<li><a
									href="${pageContext.request.contextPath}/emp/register.do?empId=${savedEmpNo}#sec-basic">1.
										基本情報</a></li>
								<li><a
									href="${pageContext.request.contextPath}/emp/register.do?empId=${savedEmpNo}#sec-salary">2.
										給与</a></li>
								<li><a
									href="${pageContext.request.contextPath}/emp/register.do?empId=${savedEmpNo}#sec-insurance">3.
										4大保険</a></li>
								<li><a
									href="${pageContext.request.contextPath}/emp/register.do?empId=${savedEmpNo}#sec-family">4.
										扶養家族</a></li>
								<li><a
									href="${pageContext.request.contextPath}/emp/register.do?empId=${savedEmpNo}#sec-edu">5.
										学歴</a></li>
								<li><a
									href="${pageContext.request.contextPath}/emp/register.do?empId=${savedEmpNo}#sec-career">6.
										経歴</a></li>
								<li><a
									href="${pageContext.request.contextPath}/emp/register.do?empId=${savedEmpNo}#sec-military">7.
										兵役</a></li>
								<li><a href="#sec-license"
									onclick="return moveSection(event,'sec-license')">8. 資格・免許</a></li>
								<li><a href="#sec-training"
									onclick="return moveSection(event,'sec-training')">9. 教育訓練</a></li>
								<li><a href="#sec-reward"
									onclick="return moveSection(event,'sec-reward')">10. 賞罰</a></li>
								<li><a href="#sec-appointment"
									onclick="return moveSection(event,'sec-appointment')">11.
										発令</a></li>
								<li><a href="#sec-guarantee"
									onclick="return moveSection(event,'sec-guarantee')">12. 推薦</a></li>
								<li><a href="#sec-retirement"
									onclick="return moveSection(event,'sec-retirement')">13. 退職</a></li>
							</ul>
						</div>
					</div>

					<!-- 사원정보2 입력 영역 / 社員情報2入力エリア -->
					<div class="main-form-area">

						<!-- 기본정보 조회 / 基本情報照会 -->
						<div class="form-section" id="sec-basic">
							<div class="section-head">
								<h3>📌 基本情報</h3>
							</div>

							<table class="form-table">
								<tr>
									<th>社員番号</th>
									<td><input type="text" value="${emp.empId}" readonly>
									</td>

									<th>雇用形態</th>
									<td><select disabled>
											<option value="">選択</option>
											<option value="正社員"
												${emp.employmentType == '正社員' ? 'selected' : ''}>正社員</option>
											<option value="契約社員"
												${emp.employmentType == '契約社員' ? 'selected' : ''}>契約社員</option>
											<option value="臨時職"
												${emp.employmentType == '臨時職' ? 'selected' : ''}>臨時職</option>
											<option value="派遣社員"
												${emp.employmentType == '派遣社員' ? 'selected' : ''}>派遣社員</option>
											<option value="委嘱職"
												${emp.employmentType == '委嘱職' ? 'selected' : ''}>委嘱職</option>
											<option value="日雇い"
												${emp.employmentType == '日雇い' ? 'selected' : ''}>日雇い</option>
									</select></td>
								</tr>

								<tr>
									<th>氏名（韓国語）</th>
									<td><input type="text" value="${emp.nameKor}" readonly>
									</td>

									<th>氏名（英語）</th>
									<td><input type="text" value="${emp.nameEng}" readonly>
									</td>
								</tr>

								<tr>
									<th>入社日</th>
									<td><input type="date" value="${emp.hireDate}" readonly>
									</td>

									<th>退職日</th>
									<td><input type="date" value="${emp.retireDate}" readonly>
									</td>
								</tr>

								<tr>
									<th>部署</th>
									<td>
										<div class="inline-inputs">
											<select disabled>
												<option value="">選択</option>
												<option value="1" ${emp.deptId == 1 ? 'selected' : ''}>社長室</option>
												<option value="2" ${emp.deptId == 2 ? 'selected' : ''}>開発チーム</option>
												<option value="3" ${emp.deptId == 3 ? 'selected' : ''}>コンテンツチーム</option>
												<option value="4" ${emp.deptId == 4 ? 'selected' : ''}>業務支援チーム</option>
												<option value="5" ${emp.deptId == 5 ? 'selected' : ''}>デザインチーム</option>
												<option value="6" ${emp.deptId == 6 ? 'selected' : ''}>管理チーム</option>
												<option value="7" ${emp.deptId == 7 ? 'selected' : ''}>企画戦略チーム</option>
											</select>
											<button type="button" class="btn btn-outline btn-sm"
												style="white-space: nowrap;" disabled>管理</button>
										</div>
									</td>

									<th>役職</th>
									<td>
										<div class="inline-inputs">
											<select disabled>
												<option value="">選択</option>
												<option value="1" ${emp.positionId == 1 ? 'selected' : ''}>取締役</option>
												<option value="2" ${emp.positionId == 2 ? 'selected' : ''}>社長</option>
												<option value="3" ${emp.positionId == 3 ? 'selected' : ''}>部長</option>
												<option value="4" ${emp.positionId == 4 ? 'selected' : ''}>次長</option>
												<option value="5" ${emp.positionId == 5 ? 'selected' : ''}>課長</option>
												<option value="6" ${emp.positionId == 6 ? 'selected' : ''}>代理</option>
												<option value="7" ${emp.positionId == 7 ? 'selected' : ''}>主任</option>
												<option value="8" ${emp.positionId == 8 ? 'selected' : ''}>社員</option>
											</select>
											<button type="button" class="btn btn-outline btn-sm"
												style="white-space: nowrap;" disabled>管理</button>
										</div>
									</td>
								</tr>

								<tr>
									<th>内国人／外国人</th>
									<td><select disabled>
											<option value="">選択</option>
											<option value="内国人"
												${emp.nationality == '内国人' ? 'selected' : ''}>内国人</option>
											<option value="外国人"
												${emp.nationality == '外国人' ? 'selected' : ''}>外国人</option>
									</select></td>

									<th>住民登録番号</th>
									<td><input type="text" value="${emp.residentNo}" readonly>
									</td>
								</tr>

								<tr>
									<th>住所</th>
									<td colspan="3">
										<div class="inline-inputs" style="margin-bottom: 6px;">
											<input type="text" value="${emp.zipCode}"
												style="width: 150px;" readonly>
											<button type="button" class="btn btn-outline btn-sm" disabled>
												郵便番号検索</button>
										</div> <input type="text" value="${emp.address}" readonly>
									</td>
								</tr>

								<tr>
									<th>電話番号</th>
									<td><input type="text" value="${emp.phoneNum}" readonly>
									</td>

									<th>携帯電話</th>
									<td><input type="text" value="${emp.mobileNum}" readonly>
									</td>
								</tr>

								<tr>
									<th>メール</th>
									<td><input type="text" value="${emp.email}" readonly>
									</td>

									<th>SNS</th>
									<td><input type="text" value="${emp.sns}" readonly>
									</td>
								</tr>

								<tr>
									<th>その他事項</th>
									<td colspan="3"><textarea rows="3" readonly>${emp.remarks}</textarea>
									</td>
								</tr>
							</table>
						</div>

						<!-- 자격·면허 및 어학능력 / 資格・免許・語学能力 -->
						<div class="form-section" id="sec-license">
							<div class="section-head">
								<h3>🎓 資格・免許 & 語学能力</h3>
							</div>

							<div class="sub-section">
								<div class="sub-title">
									<h4>+ 資格・免許</h4>
									<div>
										<button type="button" class="btn btn-outline btn-sm"
											onclick="addLicenseRow()">+ 追加</button>
										<button type="button" class="btn btn-outline btn-sm"
											onclick="removeSelectedRows('licenseTable','license-chk')">選択削除</button>
									</div>
								</div>
								<table class="data-table" id="licenseTable">
									<thead>
										<tr>
											<th width="40"><input type="checkbox"
												onclick="toggleAll(this,'license-chk')"></th>
											<th>資格・免許名</th>
											<th>取得日</th>
											<th>発行機関</th>
											<th>証明番号</th>
											<th>備考</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="checkbox" class="license-chk"></td>
											<td><input type="text" name="licenseName"></td>
											<td><input type="date" name="acquisitionDate"></td>
											<td><input type="text" name="issuer"></td>
											<td><input type="text" name="licenseNumber"></td>
											<td><input type="text" name="remarks"></td>
										</tr>
									</tbody>
								</table>
							</div>

							<div class="sub-section">
								<div class="sub-title">
									<h4>+ 語学能力</h4>
									<div>
										<button type="button" class="btn btn-outline btn-sm"
											onclick="addLanguageRow()">+ 追加</button>
										<button type="button" class="btn btn-outline btn-sm"
											onclick="removeSelectedRows('languageTable','language-chk')">選択削除</button>
									</div>
								</div>
								<table class="data-table" id="languageTable">
									<thead>
										<tr>
											<th width="40"><input type="checkbox"
												onclick="toggleAll(this,'language-chk')"></th>
											<th>外国語名</th>
											<th>試験</th>
											<th>公式スコア</th>
											<th>取得日</th>
											<th>読解</th>
											<th>作文</th>
											<th>会話</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="checkbox" class="language-chk"></td>
											<td><input type="text" name="languageName"></td>
											<td><input type="text" name="testName"></td>
											<td><input type="text" name="officialScore"></td>
											<td><input type="date" name="acquisitionDate"></td>
											<td><select name="readingLevel"><option
														value="" selected>選択</option>
													<option value="上">上</option>
													<option value="中">中</option>
													<option value="下">下</option></select></td>
											<td><select name="writingLevel"><option
														value="" selected>選択</option>
													<option value="上">上</option>
													<option value="中">中</option>
													<option value="下">下</option></select></td>
											<td><select name="speakingLevel"><option
														value="" selected>選択</option>
													<option value="上">上</option>
													<option value="中">中</option>
													<option value="下">下</option></select></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

						<!-- 교육훈련 / 教育訓練 -->
						<div class="form-section" id="sec-training">
							<div class="section-head">
								<h3>📚 教育訓練</h3>
								<div>
									<button type="button" class="btn btn-outline btn-sm"
										onclick="addTrainingRow()">+ 追加</button>
									<button type="button" class="btn btn-outline btn-sm"
										onclick="removeSelectedRows('trainingTable','training-chk')">選択削除</button>
								</div>
							</div>
							<table class="data-table" id="trainingTable">
								<thead>
									<tr>
										<th width="40"><input type="checkbox"
											onclick="toggleAll(this,'training-chk')"></th>
										<th>教育区分</th>
										<th>教育名</th>
										<th>教育期間（開始）</th>
										<th>教育期間（終了）</th>
										<th>教育機関</th>
										<th>教育費</th>
										<th>還付教育費</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input type="checkbox" class="training-chk"></td>
										<td><select name="trainingType"><option value=""
													selected>選択</option>
												<option value="社内職務">社内職務</option>
												<option value="社外職務">社外職務</option>
												<option value="階層別教育">階層別教育</option>
												<option value="語学教育">語学教育</option>
												<option value="その他教育">その他教育</option></select></td>
										<td><input type="text" name="trainingName"></td>
										<td><input type="date" name="startDate"></td>
										<td><input type="date" name="endDate"></td>
										<td><input type="text" name="trainingCenter"></td>
										<td><div class="money-box">
												<input type="number" name="trainingCost" min="0"><span>ウォン</span>
											</div></td>
										<td><div class="money-box">
												<input type="number" name="refundCost" min="0"><span>ウォン</span>
											</div></td>
									</tr>
								</tbody>
							</table>
						</div>

						<!-- 상벌 / 賞罰 -->
						<div class="form-section" id="sec-reward">
							<div class="section-head">
								<h3>🏅 賞罰</h3>
								<div>
									<button type="button" class="btn btn-outline btn-sm"
										onclick="addRewardRow()">+ 追加</button>
									<button type="button" class="btn btn-outline btn-sm"
										onclick="removeSelectedRows('rewardTable','reward-chk')">選択削除</button>
								</div>
							</div>
							<table class="data-table" id="rewardTable">
								<thead>
									<tr>
										<th width="40"><input type="checkbox"
											onclick="toggleAll(this,'reward-chk')"></th>
										<th>区分</th>
										<th>賞罰名</th>
										<th>賞罰権者</th>
										<th>賞罰日</th>
										<th>賞罰内容</th>
										<th>備考</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input type="checkbox" class="reward-chk"></td>
										<td><select name="rewardType"><option value=""
													selected>選択</option>
												<option value="褒賞">褒賞</option>
												<option value="表彰">表彰</option>
												<option value="授賞">授賞</option>
												<option value="免職">免職</option>
												<option value="停職">停職</option>
												<option value="減給">減給</option>
												<option value="譴責">譴責</option>
												<option value="注意">注意</option>
												<option value="警告">警告</option>
												<option value="措置不可">措置不可</option>
												<option value="解雇">解雇</option></select></td>
										<td><input type="text" name="rewardName"></td>
										<td><input type="text" name="manager"></td>
										<td><input type="date" name="rewardDate"></td>
										<td><input type="text" name="rewardContent"></td>
										<td><input type="text" name="remarks"></td>
									</tr>
								</tbody>
							</table>
						</div>

						<!-- 인사발령 / 人事発令 -->
						<div class="form-section" id="sec-appointment">
							<div class="section-head">
								<h3>📋 発令</h3>
								<div>
									<button type="button" class="btn btn-outline btn-sm"
										onclick="addAppointmentRow()">+ 追加</button>
									<button type="button" class="btn btn-outline btn-sm"
										onclick="removeSelectedRows('appointmentTable','appointment-chk')">選択削除</button>
								</div>
							</div>
							<table class="data-table" id="appointmentTable">
								<thead>
									<tr>
										<th width="40"><input type="checkbox"
											onclick="toggleAll(this,'appointment-chk')"></th>
										<th>発令区分</th>
										<th>発令日</th>
										<th>部署</th>
										<th>役職</th>
										<th>職責</th>
										<th>備考</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input type="checkbox" class="appointment-chk"></td>
										<td><select name="appointmentType"><option
													value="" selected>選択</option>
												<option value="入社">入社</option>
												<option value="部署異動">部署異動</option>
												<option value="昇進">昇進</option>
												<option value="降格">降格</option>
												<option value="休職">休職</option>
												<option value="復職">復職</option>
												<option value="退職">退職</option>
												<option value="その他">その他</option></select></td>
										<td><input type="date" name="appointmentDate"></td>
										<td><input type="text" name="deptName"></td>
										<td><input type="text" name="positionName"></td>
										<td><input type="text" name="jobTitle"></td>
										<td><input type="text" name="remarks"></td>
									</tr>
								</tbody>
							</table>
						</div>

						<!-- 추천 및 보증 / 推薦・保証 -->
						<div class="form-section" id="sec-guarantee">
							<div class="section-head">
								<h3>🤝 推薦・身元保証</h3>
							</div>

							<div class="sub-section">
								<div class="sub-title">
									<h4>+ 推薦者</h4>
								</div>
								<table class="data-table">
									<thead>
										<tr>
											<th>氏名</th>
											<th>続柄</th>
											<th>会社名</th>
											<th>役職</th>
											<th>電話番号</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="text" name="recommenderName"></td>
											<td><input type="text" name="relation"></td>
											<td><input type="text" name="companyName"></td>
											<td><input type="text" name="positionName"></td>
											<td><input type="text" name="phoneNumber"></td>
										</tr>
									</tbody>
								</table>
							</div>

							<div class="sub-section">
								<div class="sub-title">
									<h4>+ 保証保険</h4>
								</div>
								<table class="data-table">
									<thead>
										<tr>
											<th>加入機関</th>
											<th>保険番号</th>
											<th>保険金額</th>
											<th>加入日</th>
											<th>満了日</th>
											<th>備考</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="text" name="insuranceCompany"></td>
											<td><input type="text" name="insuranceNumber"></td>
											<td><div class="money-box">
													<input type="number" name="insuranceAmount" min="0"><span>ウォン</span>
												</div></td>
											<td><input type="date" name="joinDate"></td>
											<td><input type="date" name="expirationDate"></td>
											<td><input type="text" name="remarks"></td>
										</tr>
									</tbody>
								</table>
							</div>

							<div class="sub-section">
								<div class="sub-title">
									<h4>+ 保証人</h4>
								</div>
								<table class="data-table">
									<thead>
										<tr>
											<th>氏名</th>
											<th>続柄</th>
											<th>住民登録番号</th>
											<th>保証金額</th>
											<th>保証日</th>
											<th>満了日</th>
											<th>電話番号</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="text" name="guarantorName"></td>
											<td><input type="text" name="relation"></td>
											<td><input type="text" name="residentNo"></td>
											<td><div class="money-box">
													<input type="number" name="guaranteeAmt" min="0"><span>ウォン</span>
												</div></td>
											<td><input type="date" name="startDate"></td>
											<td><input type="date" name="endDate"></td>
											<td><input type="text" name="phoneNumber"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

						<!-- 퇴직정보 / 退職情報 -->
						<div class="form-section" id="sec-retirement">
							<div class="section-head">
								<h3>🚪 退職</h3>
							</div>
							<table class="data-table">
								<thead>
									<tr>
										<th>退職区分</th>
										<th>退職日</th>
										<th>退職理由</th>
										<th>退職後連絡先</th>
										<th>退職金</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><select name="retirementType">

												<option value=""
													${empty retirement.retirementType ? 'selected' : ''}>
													選択</option>

												<option value="定年退職"
													${retirement.retirementType eq '定年退職' ? 'selected' : ''}>
													定年退職</option>

												<option value="整理解雇"
													${retirement.retirementType eq '整理解雇' ? 'selected' : ''}>
													整理解雇</option>

												<option value="自己都合退職"
													${retirement.retirementType eq '自己都合退職' ? 'selected' : ''}>
													自己都合退職</option>

												<option value="役員退職"
													${retirement.retirementType eq '役員退職' ? 'selected' : ''}>
													役員退職</option>

												<option value="中間精算"
													${retirement.retirementType eq '中間精算' ? 'selected' : ''}>
													中間精算</option>

												<option value="その他"
													${retirement.retirementType eq 'その他' ? 'selected' : ''}>
													その他</option>

										</select></td>

										<td><input type="date" name="retirementDate"
											value="${retirement.retirementDate}"></td>

										<td><input type="text" name="retirementReason"
											value="${retirement.retirementReason}"></td>

										<td><input type="text" name="contactAfterRetirement"
											value="${retirement.contactAfterRetirement}"></td>

										<td>
											<div class="money-box">
												<input type="text" id="netPayment" name="netPayment"
													value="${empty severancePay ? '' : severancePay.netPayment}"
													onclick="showRetirementPayAlert()" style="cursor: pointer;">
												<span>ウォン</span>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>

						<!-- 저장 및 이동 버튼 / 保存・移動ボタン -->
						<div class="bottom-actions">
							<button type="submit" class="btn btn-primary"
								style="padding: 10px 30px; font-size: 15px;"
								onclick="return checkSavedEmployee();">💾 保存</button>
							<button type="reset" class="btn btn-secondary"
								style="padding: 10px 24px; font-size: 15px;">キャンセル</button>
							<a href="${pageContext.request.contextPath}/emp/list.do"
								class="btn btn-outline"
								style="padding: 10px 24px; font-size: 15px;">📋 一覧</a>
						</div>
					</div>
				</div>
			</form>
		</c:if>
	</div>

	<layout:footer />

	<!-- 화면 제어 스크립트 / 画面制御スクリプト -->
	<script>
function hasSavedEmployee() {
  const empNo = document.getElementById("savedEmpNo").value;
  return empNo != null && empNo.trim() !== "";
}

function checkSavedEmployee() {
  if (!hasSavedEmployee()) {
    alert("社員の基本情報を保存した後にご利用ください。");
    return false;
  }
  return true;
}

function moveSection(event, sectionId) {
  if (!hasSavedEmployee()) {
    event.preventDefault();
    alert("社員の基本情報が保存されていません。\n\n必須項目を入力して保存した後にご利用ください。");
    return false;
  }

  event.preventDefault();
  const target = document.getElementById(sectionId);
  if (target) {
    target.scrollIntoView({ behavior:"smooth", block:"start" });
  }
  return false;
}

function showRetirementPayAlert() {
  alert("退職金の入力は\n\n[退職管理] - [退職給付入力・管理] メニューをご利用ください。");
}

function downloadRetirementStatement() {
  if (!hasSavedEmployee()) {
    alert("社員の基本情報を保存した後にご利用ください。");
    return;
  }
}

function toggleAll(master, chkClass) {
  document.querySelectorAll("." + chkClass).forEach(cb => cb.checked = master.checked);
}

<c:if test="${empty savedEmpNo}">
window.addEventListener("load", function() {
  alert("社員の基本情報が保存されていません。\n\n必須項目を入力して保存した後、社員情報2をご利用ください。");
  location.href = "${pageContext.request.contextPath}/config/register.do";
});
</c:if>
</script>
</body>
</html>