<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>社員 登録 - ヘキサゴンゾーン</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/empRegister.css" />
<style>
</style>
</head>
<body>

	<layout:headerNav />

	<div class="container">
		<!-- 페이지 상단 제목 및 설명 / ページ上部タイトルおよび説明 -->
		<div class="page-header">
			<h2>👔 社員 登録</h2>
			<p>
				新規社員情報を登録するメニューです。 (<span class="required-mark">*</span> は必須入力項目です)
			</p>
		</div>

		<c:if test="${registerSuccess}">
			<div class="success-message">社員登録が完了しました。社員番号: ${newEmpNo}</div>
		</c:if>

		<form id="empForm"
			action="${pageContext.request.contextPath}/config/register.do"
			method="post">
			<div class="emp-layout">

				<!-- 왼쪽 프로필 및 바로가기 패널 / 左側プロフィールおよびクイックリンクパネル -->
				<div class="side-panel">
					<div class="profile-card">
						<div class="profile-img-box">
							<span>📷</span> <span>社員写真を</span> <span>登録してください</span>
						</div>
						<div class="profile-btn-group">
							<button type="button" class="btn btn-outline btn-sm">登録</button>
							<button type="button" class="btn btn-outline btn-sm">削除</button>
						</div>
						<button type="button" class="btn btn-outline btn-sm"
							style="width: 100%;">人事記録カードを見る</button>
					</div>

					<div class="side-menu">
						<div class="side-menu-title">ショートカットメニュー</div>
						<ul>
							<li><a href="#sec-basic">1. 基本情報</a></li>
							<li><a href="#sec-salary">2. 給与</a></li>
							<li><a href="#sec-insurance">3. 4大保険</a></li>
							<li><a href="#sec-family">4. 扶養家族</a></li>
							<li><a href="#sec-edu">5. 学歴</a></li>
							<li><a href="#sec-career">6. 経歴</a></li>
							<li><a href="#sec-military">7. 兵役</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-license');">8.
									資格・免許</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-training');">9.
									教育訓練</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-reward');">10.
									賞罰</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-appointment');">11.
									発令</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-guarantee');">12.
									推薦</a></li>
							<li><a href="#"
								onclick="return moveRegister2Section(event, 'sec-retirement');">13.
									退職</a></li>
						</ul>
					</div>
				</div>

				<!-- 오른쪽 입력 폼 영역 / 右側入力フォーム領域 -->
				<div class="main-form-area">

					<!-- 1. 기본정보 / 基本情報 -->
					<div class="form-section" id="sec-basic">
						<div class="section-head">
							<h3>📌 基本情報</h3>
						</div>
						<table class="form-table">
							<tr>
								<th>社員番号</th>
								<td><input type="text" value="${newEmpNo}" readonly
									placeholder="保存時に自動生成"></td>
								<th>雇用形態 <span class="required-mark">*</span></th>
								<td><select name="employmentType" required>
										<option value="">選択</option>
										<option value="正社員"
											${empReq.employmentType == '正社員' || param.employmentType == '正社員' ? 'selected' : ''}>正社員</option>
										<option value="契約社員"
											${empReq.employmentType == '契約社員' || param.employmentType == '契約社員' ? 'selected' : ''}>契約社員</option>
										<option value="臨時職"
											${empReq.employmentType == '臨時職' || param.employmentType == '臨時職' ? 'selected' : ''}>臨時職</option>
										<option value="派遣社員"
											${empReq.employmentType == '派遣社員' || param.employmentType == '派遣社員' ? 'selected' : ''}>派遣社員</option>
										<option value="委嘱職"
											${empReq.employmentType == '委嘱職' || param.employmentType == '委嘱職' ? 'selected' : ''}>委嘱職</option>
										<option value="日雇い"
											${empReq.employmentType == '日雇い' || param.employmentType == '日雇い' ? 'selected' : ''}>日雇い</option>
								</select></td>
							</tr>
							<tr>
								<th>氏名（韓国語） <span class="required-mark">*</span></th>
								<td><input type="text" name="nameKor"
									value="${not empty empReq ? empReq.nameKor : param.nameKor}"
									required></td>
								<th>氏名（英語）</th>
								<td><input type="text" name="nameEng"
									value="${not empty empReq ? empReq.nameEng : param.nameEng}"></td>
							</tr>
							<tr>
								<th>入社日 <span class="required-mark">*</span></th>
								<td><input type="date" name="hireDate"
									value="${not empty empReq ? empReq.hireDate : param.hireDate}"
									required></td>
								<th>退職日</th>
								<td onclick="${empty newEmpNo ? 'showRetireAlert()' : ''}">
									<input type="date" name="retireDate"
									value="${not empty empReq ? empReq.retireDate : param.retireDate}"
									${empty newEmpNo ? 'disabled' : ''}>
								</td>
							</tr>
							<tr>
								<th>部署</th>
								<td>
									<div class="inline-inputs">
										<select name="deptId">
											<option value="">選択</option>
											<option value="1"
												${empReq.deptId == 1 || param.deptId == '1' ? 'selected' : ''}>社長室</option>
											<option value="2"
												${empReq.deptId == 2 || param.deptId == '2' ? 'selected' : ''}>開発チーム</option>
											<option value="3"
												${empReq.deptId == 3 || param.deptId == '3' ? 'selected' : ''}>コンテンツチーム</option>
											<option value="4"
												${empReq.deptId == 4 || param.deptId == '4' ? 'selected' : ''}>業務支援チーム</option>
											<option value="5"
												${empReq.deptId == 5 || param.deptId == '5' ? 'selected' : ''}>デザインチーム</option>
											<option value="6"
												${empReq.deptId == 6 || param.deptId == '6' ? 'selected' : ''}>管理チーム</option>
											<option value="7"
												${empReq.deptId == 7 || param.deptId == '7' ? 'selected' : ''}>企画戦略チーム</option>
										</select>
										<button type="button" class="btn btn-outline btn-sm"
											style="white-space: nowrap;">管理</button>
									</div>
								</td>
								<th>役職</th>
								<td>
									<div class="inline-inputs">
										<select name="positionId">
											<option value="">選択</option>
											<option value="1"
												${empReq.positionId == 1 || param.positionId == '1' ? 'selected' : ''}>取締役</option>
											<option value="2"
												${empReq.positionId == 2 || param.positionId == '2' ? 'selected' : ''}>社長</option>
											<option value="3"
												${empReq.positionId == 3 || param.positionId == '3' ? 'selected' : ''}>部長</option>
											<option value="4"
												${empReq.positionId == 4 || param.positionId == '4' ? 'selected' : ''}>次長</option>
											<option value="5"
												${empReq.positionId == 5 || param.positionId == '5' ? 'selected' : ''}>課長</option>
											<option value="6"
												${empReq.positionId == 6 || param.positionId == '6' ? 'selected' : ''}>代理</option>
											<option value="7"
												${empReq.positionId == 7 || param.positionId == '7' ? 'selected' : ''}>主任</option>
											<option value="8"
												${empReq.positionId == 8 || param.positionId == '8' ? 'selected' : ''}>社員</option>
										</select>
										<button type="button" class="btn btn-outline btn-sm"
											style="white-space: nowrap;">管理</button>
									</div>
								</td>
							</tr>
							<tr>
								<th>内国人／外国人</th>
								<td><select name="nationality">
										<option value="">選択</option>
										<option value="内国人"
											${empReq.nationality == '内国人' || param.nationality == '内国人' ? 'selected' : ''}>内国人</option>
										<option value="外国人"
											${empReq.nationality == '外国人' || param.nationality == '外国人' ? 'selected' : ''}>外国人</option>
								</select></td>
								<th>住民登録番号</th>
								<td><input type="text" name="residentNo"
									value="${not empty empReq ? empReq.residentNo : param.residentNo}"></td>
							</tr>
							<tr>
								<th>住所</th>
								<td colspan="3">
									<div class="inline-inputs" style="margin-bottom: 6px;">
										<input type="text" name="zipCode"
											value="${not empty empReq ? empReq.zipCode : param.zipCode}"
											style="width: 120px;">
										<button type="button" class="btn btn-outline btn-sm">郵便番号
											検索</button>
									</div> <input type="text" name="address"
									value="${not empty empReq ? empReq.address : param.address}">
								</td>
							</tr>
							<tr>
								<th>電話番号</th>
								<td><input type="text" name="phoneNum"
									value="${not empty empReq ? empReq.phoneNum : param.phoneNum}"></td>
								<th>携帯電話</th>
								<td><input type="text" name="mobileNum"
									value="${not empty empReq ? empReq.mobileNum : param.mobileNum}"></td>
							</tr>
							<tr>
								<th>メール</th>
								<td><input type="text" name="email"
									value="${not empty empReq ? empReq.email : param.email}"></td>
								<th>SNS</th>
								<td><input type="text" name="sns"
									value="${not empty empReq ? empReq.sns : param.sns}"></td>
							</tr>
							<tr>
								<th>その他事項</th>
								<td colspan="3"><textarea name="remarks" rows="3">${not empty empReq ? empReq.remarks : param.remarks}</textarea></td>
							</tr>
						</table>
						<input type="hidden" name="photoPath"
							value="${not empty empReq ? empReq.photoPath : param.photoPath}">
					</div>

					<!-- 2. 급여 & 4대보험 / 給与 & 4大保険 -->
					<div class="form-section" id="sec-salary">
						<div class="section-head">
							<h3>💳 給与 & 4大保険</h3>
						</div>
						<table class="form-table">
							<tr>
								<th>4大保険</th>
								<td colspan="3">
									<div class="check-group">
										<label><input type="checkbox" id="nationalPensionYN"
											name="nationalPensionYN" value="Y"
											${empInfoSalaryReq.nationalPensionYN == 'Y' || param.nationalPensionYN == 'Y' ? 'checked' : ''}>
											国民年金</label> <label><input type="checkbox"
											id="healthInsuranceYN" name="healthInsuranceYN" value="Y"
											${empInfoSalaryReq.healthInsuranceYN == 'Y' || param.healthInsuranceYN == 'Y' ? 'checked' : ''}>
											健康保険</label> <span>(減免: <select name="healthDiscountRate"
											style="width: 90px;">
												<option value="0"
													${empInfoSalaryReq.healthDiscountRate == 0 || param.healthDiscountRate == '0' ? 'selected' : ''}>選択</option>
												<option value="10"
													${empInfoSalaryReq.healthDiscountRate == 10 || param.healthDiscountRate == '10' ? 'selected' : ''}>10%</option>
												<option value="30"
													${empInfoSalaryReq.healthDiscountRate == 30 || param.healthDiscountRate == '30' ? 'selected' : ''}>30%</option>
												<option value="50"
													${empInfoSalaryReq.healthDiscountRate == 50 || param.healthDiscountRate == '50' ? 'selected' : ''}>50%</option>
												<option value="60"
													${empInfoSalaryReq.healthDiscountRate == 60 || param.healthDiscountRate == '60' ? 'selected' : ''}>60%</option>
										</select>)
										</span> <span>/</span> <label><input type="checkbox"
											id="longtermCareYN" name="longtermCareYN" value="Y"
											${empInfoSalaryReq.longtermCareYN == 'Y' || param.longtermCareYN == 'Y' ? 'checked' : ''}>
											老人長期療養保険を含む</label> <span>(減免: <select
											name="longtermCareDiscountRate" style="width: 90px;">
												<option value="0"
													${empInfoSalaryReq.longtermCareDiscountRate == 0 || param.longtermCareDiscountRate == '0' ? 'selected' : ''}>選択</option>
												<option value="30"
													${empInfoSalaryReq.longtermCareDiscountRate == 30 || param.longtermCareDiscountRate == '30' ? 'selected' : ''}>30%</option>
										</select>)
										</span> <label><input type="checkbox"
											id="employmentInsuranceYN" name="employmentInsuranceYN"
											value="Y"
											${empInfoSalaryReq.employmentInsuranceYN == 'Y' || param.employmentInsuranceYN == 'Y' ? 'checked' : ''}>
											雇用保険</label>
									</div>
								</td>
							</tr>
							<tr>
								<th>源泉所得税 <span class="required-mark">*</span></th>
								<td colspan="3">
									<div class="radio-group">
										<label><input type="radio" id="incomeTaxWorker"
											name="incomeTaxType" value="WORKER"
											${empInfoSalaryReq.incomeTaxType == 'WORKER' || param.incomeTaxType == 'WORKER' ? 'checked' : ''}>
											給与所得者（簡易税額表）</label> <span>税額: <select id="incomeTaxRate"
											name="incomeTaxRate" style="width: 110px;">
												<option value="">選択</option>
												<option value="80">80%</option>
												<option value="100">100%</option>
												<option value="120">120%</option>
										</select>
										</span> <label><input type="checkbox"
											name="youthTaxReductionYN" value="Y"
											${empInfoSalaryReq.youthTaxReductionYN == 'Y' || param.youthTaxReductionYN == 'Y' ? 'checked' : ''}>
											中小企業若年者所得税減免</label> <select name="youthTaxReductionRate"
											style="width: 90px;">
											<option value="0"
												${empInfoSalaryReq.youthTaxReductionRate == 0 || param.youthTaxReductionRate == '0' ? 'selected' : ''}>選択</option>
											<option value="50"
												${empInfoSalaryReq.youthTaxReductionRate == 50 || param.youthTaxReductionRate == '50' ? 'selected' : ''}>50%</option>
											<option value="70"
												${empInfoSalaryReq.youthTaxReductionRate == 70 || param.youthTaxReductionRate == '70' ? 'selected' : ''}>70%</option>
											<option value="90"
												${empInfoSalaryReq.youthTaxReductionRate == 90 || param.youthTaxReductionRate == '90' ? 'selected' : ''}>90%</option>
											<option value="100"
												${empInfoSalaryReq.youthTaxReductionRate == 100 || param.youthTaxReductionRate == '100' ? 'selected' : ''}>100%</option>
										</select>
									</div>
									<div class="radio-group" style="margin-top: 8px;">
										<label><input type="radio" name="incomeTaxType"
											value="BUSINESS"
											${empInfoSalaryReq.incomeTaxType == 'BUSINESS' || param.incomeTaxType == 'BUSINESS' ? 'checked' : ''}>
											事業所得者（3.3%）</label> <label><input type="radio"
											name="incomeTaxType" value="DAILY"
											${empInfoSalaryReq.incomeTaxType == 'DAILY' || param.incomeTaxType == 'DAILY' ? 'checked' : ''}>
											日雇い（2.97%）</label> <label><input type="radio"
											name="incomeTaxType" value="OTHER"
											${empInfoSalaryReq.incomeTaxType == 'OTHER' || param.incomeTaxType == 'OTHER' ? 'checked' : ''}>
											その他所得者（8.8%）</label> <label><input type="radio"
											name="incomeTaxType" value="WORK_BUSINESS"
											${empInfoSalaryReq.incomeTaxType == 'WORK_BUSINESS' || param.incomeTaxType == 'WORK_BUSINESS' ? 'checked' : ''}>
											給与・事業所得者</label> <label><input type="radio"
											name="incomeTaxType" value="EXEMPT"
											${empInfoSalaryReq.incomeTaxType == 'EXEMPT' || param.incomeTaxType == 'EXEMPT' ? 'checked' : ''}>
											免除</label>
									</div>
								</td>
							</tr>
							<tr>
								<th>ドゥルヌリ<br>社会保険支援 <span class="required-mark">*</span></th>
								<td colspan="3">
									<div class="radio-group">
										<label><input type="radio" name="durunuriType"
											value="NONE"
											${empty empInfoSalaryReq.durunuriType && empty param.durunuriType || empInfoSalaryReq.durunuriType == 'NONE' || param.durunuriType == 'NONE' ? 'checked' : ''}>
											該当なし</label> <label><input type="radio" name="durunuriType"
											value="NEW80"
											${empInfoSalaryReq.durunuriType == 'NEW80' || param.durunuriType == 'NEW80' ? 'checked' : ''}>
											新規加入者（80%支援）</label> <label><input type="radio"
											name="durunuriType" value="NEW90"
											${empInfoSalaryReq.durunuriType == 'NEW90' || param.durunuriType == 'NEW90' ? 'checked' : ''}>
											新規加入者（90%支援）</label>
									</div>
									<div style="margin-top: 8px;">
										<label><input type="checkbox" name="durunuriSplitYN"
											value="Y"
											${empInfoSalaryReq.durunuriSplitYN == 'Y' || param.durunuriSplitYN == 'Y' ? 'checked' : ''}>
											個別設定</label> <span style="margin-left: 15px;">国民年金 支援率 <input
											type="number" name="durunuriPensionRate"
											value="${not empty empInfoSalaryReq ? empInfoSalaryReq.durunuriPensionRate : (empty param.durunuriPensionRate ? 0 : param.durunuriPensionRate)}"
											min="0" max="100" style="width: 65px;"> %
										</span> <span style="margin-left: 15px;">雇用保険 支援率 <input
											type="number" name="durunuriEmploymentRate"
											value="${not empty empInfoSalaryReq ? empInfoSalaryReq.durunuriEmploymentRate : (empty param.durunuriEmploymentRate ? 0 : param.durunuriEmploymentRate)}"
											min="0" max="100" style="width: 65px;"> %
										</span>
									</div>
								</td>
							</tr>
							<tr>
								<th>基本給／日給 <span class="required-mark">*</span></th>
								<td><input type="text" name="baseSalary"
									value="${not empty empInfoSalaryReq ? empInfoSalaryReq.baseSalary : param.baseSalary}"
									required></td>
								<td colspan="2">月給制の場合は月額基本給、日雇い労働者の場合は日給を入力します。</td>
							</tr>
							<tr>
								<th>国民年金 基準所得月額</th>
								<td><input type="text" name="pensionSalary"
									value="${not empty empInfoSalaryReq ? empInfoSalaryReq.pensionSalary : param.pensionSalary}"></td>
								<td colspan="2" rowspan="3">入力した場合、4大保険控除時に優先適用され、<br>未入力
									の場合は該当勤務日の非課税分を除いた課税合計額が適用されます。
								</td>
							</tr>
							<tr>
								<th>健康保険 報酬月額</th>
								<td><input type="text" name="healthSalary"
									value="${not empty empInfoSalaryReq ? empInfoSalaryReq.healthSalary : param.healthSalary}"></td>
							</tr>
							<tr>
								<th>雇用保険 報酬月額</th>
								<td><input type="text" name="employmentSalary"
									value="${not empty empInfoSalaryReq ? empInfoSalaryReq.employmentSalary : param.employmentSalary}"></td>
							</tr>
							<tr>
								<th>給与口座</th>
								<td colspan="3"><div class="inline-inputs">
										<select name="salaryBank" style="width: 160px;">
											<option value="">選択</option>
											<option value="KB国民"
												${empInfoSalaryReq.salaryBank == 'KB国民' || param.salaryBank == 'KB国民' ? 'selected' : ''}>KB国民銀行</option>
											<option value="新韓"
												${empInfoSalaryReq.salaryBank == '新韓' || param.salaryBank == '新韓' ? 'selected' : ''}>新韓銀行</option>
											<option value="ウリ"
												${empInfoSalaryReq.salaryBank == 'ウリ' || param.salaryBank == 'ウリ' ? 'selected' : ''}>ウリ銀行</option>
											<option value="ハナ"
												${empInfoSalaryReq.salaryBank == 'ハナ' || param.salaryBank == 'ハナ' ? 'selected' : ''}>ハナ銀行</option>
										</select> <input type="text" name="salaryAccount"
											value="${not empty empInfoSalaryReq ? empInfoSalaryReq.salaryAccount : param.salaryAccount}">
										<button type="button" class="btn btn-outline btn-sm"
											style="white-space: nowrap;">口座名義人照会</button>
									</div></td>
							</tr>
						</table>
					</div>

					<!-- 3. 4대보험 상세정보 / 4大保険 詳細情報 -->
					<div class="form-section" id="sec-insurance">
						<div class="section-head">
							<h3>🛡️ 4大保険 取得・喪失情報</h3>
						</div>
						<table class="data-table">
							<thead>
								<tr>
									<th>区分</th>
									<th>記号番号</th>
									<th>取得日</th>
									<th>喪失日</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>国民年金</th>
									<td><input type="text" name="nationalNo"
										value="${not empty insuranceReq ? insuranceReq.nationalNo : param.nationalNo}"></td>
									<td><input type="date" name="nationalAcqDate"
										value="${not empty insuranceReq ? insuranceReq.nationalAcqDate : param.nationalAcqDate}"></td>
									<td><input type="date" name="nationalLossDate"
										value="${not empty insuranceReq ? insuranceReq.nationalLossDate : param.nationalLossDate}"></td>
								</tr>
								<tr>
									<th>健康保険</th>
									<td><input type="text" name="healthNo"
										value="${not empty insuranceReq ? insuranceReq.healthNo : param.healthNo}"></td>
									<td><input type="date" name="healthAcqDate"
										value="${not empty insuranceReq ? insuranceReq.healthAcqDate : param.healthAcqDate}"></td>
									<td><input type="date" name="healthLossDate"
										value="${not empty insuranceReq ? insuranceReq.healthLossDate : param.healthLossDate}"></td>
								</tr>
								<tr>
									<th>雇用保険</th>
									<td><input type="text" name="employmentNo"
										value="${not empty insuranceReq ? insuranceReq.employmentNo : param.employmentNo}"></td>
									<td><input type="date" name="employmentAcqDate"
										value="${not empty insuranceReq ? insuranceReq.employmentAcqDate : param.employmentAcqDate}"></td>
									<td><input type="date" name="employmentLossDate"
										value="${not empty insuranceReq ? insuranceReq.employmentLossDate : param.employmentLossDate}"></td>
								</tr>
								<tr>
									<th>労災保険</th>
									<td><input type="text" name="industrialNo"
										value="${not empty insuranceReq ? insuranceReq.industrialNo : param.industrialNo}"></td>
									<td><input type="date" name="industrialAcqDate"
										value="${not empty insuranceReq ? insuranceReq.industrialAcqDate : param.industrialAcqDate}"></td>
									<td><input type="date" name="industrialLossDate"
										value="${not empty insuranceReq ? insuranceReq.industrialLossDate : param.industrialLossDate}"></td>
								</tr>
							</tbody>
						</table>
					</div>

					<!-- 4. 부양가족 / 扶養家族 -->
					<div class="form-section" id="sec-family">
						<div class="section-head">
							<h3>👨‍👩‍👧‍👦 扶養家族情報</h3>
							<div>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="addFamilyRow()">+ 行追加</button>

								<button type="button" class="btn btn-outline btn-sm"
									onclick="removeSelectedRows('familyTable', 'fam-chk')">
									選択行削除</button>
							</div>
						</div>

						<table class="data-table" id="familyTable">

							<thead>
								<tr>
									<th width="40"><input type="checkbox"
										onclick="toggleAll(this,'fam-chk')"></th>

									<th>続柄 <span class="required-mark">*</span></th>
									<th>氏名 <span class="required-mark">*</span></th>
									<th>区分</th>
									<th>住民登録番号</th>
									<th>障害有無</th>
									<th>人的控除</th>
									<th>健康保険</th>
									<th>同居</th>
									<th>所得税</th>
									<th>20歳以下の子</th>
								</tr>
							</thead>

							<tbody>

								<c:choose>

									<c:when test="${not empty dependentReqs}">

										<c:forEach var="dep" items="${dependentReqs}" varStatus="st">
											<tr>
												<td><input type="checkbox" class="fam-chk"></td>

												<td><select name="relation">
														<option value="" ${empty dep.relation ? 'selected' : ''}>選択</option>
														<option value="配偶者"
															${dep.relation == '配偶者' ? 'selected' : ''}>配偶者</option>
														<option value="息子"
															${dep.relation == '息子' ? 'selected' : ''}>息子</option>
														<option value="娘" ${dep.relation == '娘' ? 'selected' : ''}>娘</option>
														<option value="父" ${dep.relation == '父' ? 'selected' : ''}>父</option>
														<option value="母" ${dep.relation == '母' ? 'selected' : ''}>母</option>
														<option value="兄弟"
															${dep.relation == '兄弟' ? 'selected' : ''}>兄弟</option>
														<option value="姉妹"
															${dep.relation == '姉妹' ? 'selected' : ''}>姉妹</option>
														<option value="祖父"
															${dep.relation == '祖父' ? 'selected' : ''}>祖父</option>
														<option value="祖母"
															${dep.relation == '祖母' ? 'selected' : ''}>祖母</option>
														<option value="孫" ${dep.relation == '孫' ? 'selected' : ''}>孫</option>
														<option value="孫娘"
															${dep.relation == '孫娘' ? 'selected' : ''}>孫娘</option>
														<option value="その他"
															${dep.relation == 'その他' ? 'selected' : ''}>その他</option>
												</select></td>


												<td><input type="text" name="familyName"
													value="${dep.familyName}"></td>



												<td><select name="nationality">

														<option value=""
															${empty dep.nationality ? 'selected' : ''}>
															選択してください</option>

														<option value="内国人"
															${dep.nationality == '内国人' ? 'selected' : ''}>
															内国人</option>

														<option value="外国人"
															${dep.nationality == '外国人' ? 'selected' : ''}>
															外国人</option>

												</select></td>



												<td><input type="text" name="residentNo"
													value="${dep.residentNo}"></td>




												<td><input type="checkbox"
													name="disabilityYN_${st.index}" value="Y"
													${dep.disabilityYN == 'Y' ? 'checked' : ''}></td>

												<td><input type="checkbox"
													name="deductionYN_${st.index}" value="Y"
													${dep.deductionYN == 'Y' ? 'checked' : ''}></td>

												<td><input type="checkbox"
													name="healthInsuranceYN_${st.index}" value="Y"
													${dep.healthInsuranceYN == 'Y' ? 'checked' : ''}></td>

												<td><input type="checkbox"
													name="cohabitationYN_${st.index}" value="Y"
													${dep.cohabitationYN == 'Y' ? 'checked' : ''}></td>

												<td><input type="checkbox"
													name="incomeTaxYN_${st.index}" value="Y"
													${dep.incomeTaxYN == 'Y' ? 'checked' : ''}></td>

												<td><input type="checkbox"
													name="childAgeYN_${st.index}" value="Y"
													${dep.childAgeYN == 'Y' ? 'checked' : ''}></td>

											</tr>

										</c:forEach>

									</c:when>



									<c:otherwise>

										<tr>

											<td><input type="checkbox" class="fam-chk"></td>


											<td><select name="relation">
													<option value="" selected>選択</option>
													<option value="配偶者">配偶者</option>
													<option value="息子">息子</option>
													<option value="娘">娘</option>
													<option value="父">父</option>
													<option value="母">母</option>
													<option value="兄弟">兄弟</option>
													<option value="姉妹">姉妹</option>
													<option value="祖父">祖父</option>
													<option value="祖母">祖母</option>
													<option value="孫">孫</option>
													<option value="孫娘">孫娘</option>
													<option value="その他">その他</option>
											</select></td>
											<td><input type="text" name="familyName"></td>
											<td><select name="nationality">
													<option value="" selected>選択してください</option>
													<option value="内国人">内国人</option>
													<option value="外国人">外国人</option>
											</select></td>
											<td><input type="text" name="residentNo"></td>


											<td><input type="checkbox" name="disabilityYN_0"
												value="Y"></td>

											<td><input type="checkbox" name="deductionYN_0"
												value="Y"></td>

											<td><input type="checkbox" name="healthInsuranceYN_0"
												value="Y"></td>

											<td><input type="checkbox" name="cohabitationYN_0"
												value="Y"></td>

											<td><input type="checkbox" name="incomeTaxYN_0"
												value="Y"></td>

											<td><input type="checkbox" name="childAgeYN_0" value="Y">
											</td>

										</tr>

									</c:otherwise>

								</c:choose>

							</tbody>

						</table>
					</div>

					<!-- 6. 경력 / 経歴 -->
					<div class="form-section" id="sec-career">
						<div class="section-head">
							<h3>💼 経歴情報</h3>
							<div>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="addCareerRow()">+ 行追加</button>
								<button type="button" class="btn btn-outline btn-sm"
									onclick="removeSelectedRows('careerTable','career-chk')">選択
									行削除</button>
							</div>
						</div>
						<table class="data-table" id="careerTable">
							<thead>
								<tr>
									<th width="40"><input type="checkbox"
										onclick="toggleAll(this,'career-chk')"></th>
									<th>会社名</th>
									<th>入社日</th>
									<th>退職日</th>
									<th>勤務期間</th>
									<th>最終役職</th>
									<th>担当業務</th>
									<th>退職理由</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty careerReqs}">
										<c:forEach var="career" items="${careerReqs}">
											<tr>
												<td><input type="checkbox" class="career-chk"></td>
												<td><input type="text" name="companyName"
													value="${career.companyName}"></td>
												<td><input type="date" name="careerHireDate"
													value="${career.hireDate}"></td>
												<td><input type="date" name="careerRetireDate"
													value="${career.retireDate}"></td>
												<td><div class="inline-inputs">
														<input type="number" name="workYear"
															value="${career.workYear}" min="0" style="width: 60px;"><span>年</span><input
															type="number" name="workMonth"
															value="${career.workMonth}" min="0" max="11"
															style="width: 60px;"><span>か月</span>
													</div></td>
												<td><input type="text" name="lastPosition"
													value="${career.lastPosition}"></td>
												<td><input type="text" name="careerPosition"
													value="${career.position}"></td>
												<td><input type="text" name="retireReason"
													value="${career.retireReason}"></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td><input type="checkbox" class="career-chk"></td>
											<td><input type="text" name="companyName"></td>
											<td><input type="date" name="careerHireDate"></td>
											<td><input type="date" name="careerRetireDate"></td>
											<td><div class="inline-inputs">
													<input type="number" name="workYear" min="0"
														style="width: 60px;"><span>年</span><input
														type="number" name="workMonth" min="0" max="11"
														style="width: 60px;"><span>か月</span>
												</div></td>
											<td><input type="text" name="lastPosition"></td>
											<td><input type="text" name="careerPosition"></td>
											<td><input type="text" name="retireReason"></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>

					<!-- 7. 병역 / 兵役 -->
					<div class="form-section" id="sec-military">
						<div class="section-head">
							<h3>🪖 兵役情報</h3>
						</div>
						<table class="form-table">
							<tr>
								<th>兵役区分</th>
								<td><select name="militaryType"><option value="">選択してください</option>
										<option value="履行済"
											${militaryReq.militaryType == '履行済' || param.militaryType == '履行済' ? 'selected' : ''}>兵役済</option>
										<option value="未履行"
											${militaryReq.militaryType == '未履行' || param.militaryType == '未履行' ? 'selected' : ''}>未履行</option>
										<option value="免除"
											${militaryReq.militaryType == '免除' || param.militaryType == '免除' ? 'selected' : ''}>免除</option></select></td>
								<th>軍種</th>
								<td><select name="militaryBranch"><option value="">選択してください</option>
										<option value="陸軍"
											${militaryReq.militaryBranch == '陸軍' || param.militaryBranch == '陸軍' ? 'selected' : ''}>陸軍</option>
										<option value="海軍"
											${militaryReq.militaryBranch == '海軍' || param.militaryBranch == '海軍' ? 'selected' : ''}>海軍</option>
										<option value="空軍"
											${militaryReq.militaryBranch == '空軍' || param.militaryBranch == '空軍' ? 'selected' : ''}>空軍</option>
										<option value="海兵隊"
											${militaryReq.militaryBranch == '海兵隊' || param.militaryBranch == '海兵隊' ? 'selected' : ''}>海兵隊</option></select></td>
							</tr>
							<tr>
								<th>服務期間</th>
								<td colspan="3"><div class="inline-inputs"
										style="width: 400px;">
										<input type="date" name="startDate"
											value="${not empty militaryReq ? militaryReq.startDate : param.startDate}"><span>~</span><input
											type="date" name="endDate"
											value="${not empty militaryReq ? militaryReq.endDate : param.endDate}">
									</div></td>
							</tr>
							<tr>
								<th>最終階級</th>
								<td><input type="text" name="finalRank"
									value="${not empty militaryReq ? militaryReq.finalRank : param.finalRank}"
									placeholder="例：兵長"></td>
								<th>兵科</th>
								<td><input type="text" name="specialty"
									value="${not empty militaryReq ? militaryReq.specialty : param.specialty}"
									placeholder="例：歩兵"></td>
							</tr>
							<tr>
								<th>未履行・免除理由</th>
								<td colspan="3"><input type="text" name="exemptionReason"
									value="${not empty militaryReq ? militaryReq.exemptionReason : param.exemptionReason}"></td>
							</tr>
						</table>
					</div>

					<!-- 하단 작업 버튼 / 下部アクションボタン -->
					<div class="bottom-actions">
						<button type="submit" class="btn btn-primary"
							style="padding: 10px 30px; font-size: 15px;">💾 保存</button>
						<button type="reset" class="btn btn-secondary"
							style="padding: 10px 24px; font-size: 15px;">キャンセル</button>
						<a href="${pageContext.request.contextPath}/emp/list.do"
							class="btn btn-outline"
							style="padding: 10px 24px; font-size: 15px;">📋 一覧へ</a>
					</div>

				</div>
			</div>
		</form>
	</div>

	<layout:footer />

	<!-- 동적 행 추가/삭제 스크립트 / 動的な行追加・削除スクリプト -->
	<script>
    function removeSelectedRows(tableId, chkClass) {
      const rows = document.querySelectorAll('#' + tableId + ' tbody tr');
      rows.forEach(row => {
        const chk = row.querySelector('.' + chkClass);
        if (chk && chk.checked) row.remove();
      });
    }

    function addFamilyRow() {
        const tbody = document.querySelector("#familyTable tbody");
        const index = tbody.rows.length;

        const row = document.createElement("tr");

        row.innerHTML = `
          <td><input type="checkbox" class="fam-chk"></td>

          <td>
            <select name="relation">
              <option value="">選択</option>
              <option value="配偶者">配偶者</option>
              <option value="息子">息子</option>
              <option value="娘">娘</option>
              <option value="父">父</option>
              <option value="母">母</option>
              <option value="兄弟">兄弟</option>
              <option value="姉妹">姉妹</option>
              <option value="その他">その他</option>
            </select>
          </td>

          <td><input type="text" name="familyName"></td>

          <td>
            <select name="nationality">
              <option value="">選択してください</option>
              <option value="内国人">内国人</option>
              <option value="外国人">外国人</option>
            </select>
          </td>

          <td><input type="text" name="residentNo"></td>

          <td><input type="checkbox" name="disabilityYN_${index}" value="Y"></td>
          <td><input type="checkbox" name="deductionYN_${index}" value="Y"></td>
          <td><input type="checkbox" name="healthInsuranceYN_${index}" value="Y"></td>
          <td><input type="checkbox" name="cohabitationYN_${index}" value="Y"></td>
          <td><input type="checkbox" name="incomeTaxYN_${index}" value="Y"></td>
          <td><input type="checkbox" name="childAgeYN_${index}" value="Y"></td>
        `;

        tbody.appendChild(row);
    }

    function toggleAll(master, chkClass) {
      document.querySelectorAll('.' + chkClass).forEach(cb => cb.checked = master.checked);
    }

    const healthInsurance = document.getElementById('healthInsuranceYN');
    const longtermCare = document.getElementById('longtermCareYN');
    if (healthInsurance && longtermCare) {
      healthInsurance.addEventListener('change', function() {
        if (this.checked) longtermCare.checked = true;
      });
    }

    const workerTax = document.getElementById('incomeTaxWorker');
    if (workerTax) {
      workerTax.addEventListener('change', function() {
        if (this.checked) {
          document.getElementById('nationalPensionYN').checked = true;
          document.getElementById('healthInsuranceYN').checked = true;
          document.getElementById('longtermCareYN').checked = true;
          document.getElementById('employmentInsuranceYN').checked = true;
        }
      });
    }
    
    function moveRegister2Section(event, sectionId) {
      event.preventDefault();

      const empNo = "${newEmpNo}";
      if (empNo == null || empNo.trim() === "") {
        alert("社員登録後に入力できます。");
        return false;
      }

      location.href = "${pageContext.request.contextPath}/config/register2.do?empId="
          + encodeURIComponent(empNo) + "#" + sectionId;
      return false;
    }

    function showRetireAlert() {
        alert("退職日の設定は\n\n社員情報を保存した後にご利用ください。");
    }
  </script>

</body>
</html>