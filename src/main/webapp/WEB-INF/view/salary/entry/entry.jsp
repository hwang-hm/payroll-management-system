<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>ヘキサゴンゾーン - 給与入力/管理</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.css" />
  <style>
    :root {
      --primary: #2563eb;
      --primary-hover: #1d4ed8;
      --pay-color: #2563eb;
      --deduct-color: #e11d48;
      --bg-gray: #f8fafc;
      --border: #e2e8f0;
      --text-main: #1e293b;
      --text-muted: #64748b;
    }
    body { font-family: 'Pretendard Variable', Pretendard, sans-serif; background: var(--bg-gray); color: var(--text-main); margin: 0; }
    .container { max-width: 1400px; margin: 0 auto 60px; padding: 0 20px; }
    .page-header { margin-bottom: 16px; border-bottom: 2px solid var(--border); padding-bottom: 14px; }
    .page-header h2 { font-size: 24px; font-weight: 700; margin: 0 0 4px 0; }
    .page-header p { margin: 0; font-size: 13px; color: var(--text-muted); }

    /* 필터 바 */
    .filter-bar {
      background: #2b3648; color: #fff; border-radius: 8px; padding: 14px 18px;
      display: flex; flex-wrap: wrap; align-items: center; gap: 14px; margin-bottom: 16px;
    }
    .filter-bar label { font-size: 13px; font-weight: 600; }
    .filter-bar select, .filter-bar input[type="text"] {
      padding: 6px 10px; border-radius: 6px; border: 1px solid #cbd5e1; font-size: 13px;
    }
    .filter-info { font-size: 12px; color: #cbd5e1; }
    .filter-info strong { color: #fff; }

    /* 버튼 */
    .btn {
      display: inline-flex; align-items: center; justify-content: center; gap: 4px;
      padding: 7px 14px; font-size: 13px; font-weight: 600; border-radius: 6px;
      cursor: pointer; border: none; text-decoration: none;
    }
    .btn-primary { background: var(--primary); color: #fff; }
    .btn-primary:hover { background: var(--primary-hover); }
    .btn-danger { background: #e11d48; color: #fff; }
    .btn-warning { background: #f59e0b; color: #fff; }
    .btn-outline { background: #fff; border: 1px solid #cbd5e1; color: #334155; }
    .btn-sm { padding: 5px 10px; font-size: 12px; }

    /* 메인 2열 레이아웃 */
    .main-split { display: grid; grid-template-columns: 1fr 1.2fr; gap: 16px; margin-bottom: 20px; }
    .panel { background: #fff; border: 1px solid var(--border); border-radius: 8px; overflow: hidden; }
    .panel-head {
      padding: 10px 14px; background: #f8fafc; border-bottom: 1px solid var(--border);
      display: flex; justify-content: space-between; align-items: center;
    }
    .panel-head h3 { margin: 0; font-size: 14px; font-weight: 700; }
    .panel-body { padding: 12px; }

    /* 좌측 테이블 */
    .action-bar { display: flex; gap: 6px; margin-bottom: 10px; flex-wrap: wrap; }
    .list-table { width: 100%; border-collapse: collapse; font-size: 13px; }
    .list-table th {
      background: #f8fafc; color: #475569; font-weight: 700; padding: 10px 8px;
      border-bottom: 2px solid var(--border); text-align: center;
    }
    .list-table td {
      padding: 9px 8px; border-bottom: 1px solid var(--border); text-align: center;
    }
    .list-table tbody tr { cursor: pointer; }
    .list-table tbody tr:hover { background: #eff6ff; }
    .list-table tbody tr.selected { background: #dbeafe; }
    .amount-pay { color: var(--pay-color); font-weight: 600; }
    .amount-deduct { color: var(--deduct-color); font-weight: 600; }

    /* 우측 상세 폼 */
    .detail-tabs { display: flex; gap: 0; border-bottom: 2px solid var(--border); margin-bottom: 12px; }
    .detail-tab {
      padding: 8px 16px; font-size: 13px; font-weight: 600; cursor: pointer;
      border-bottom: 2px solid transparent; margin-bottom: -2px; color: var(--text-muted);
    }
    .detail-tab.active { color: var(--primary); border-bottom-color: var(--primary); }
    .detail-columns { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
    .detail-section h4 {
      font-size: 13px; font-weight: 700; margin: 0 0 10px 0; padding-bottom: 6px;
      border-bottom: 2px solid var(--border);
    }
    .detail-section.pay h4 { color: var(--pay-color); border-color: var(--pay-color); }
    .detail-section.deduct h4 { color: var(--deduct-color); border-color: var(--deduct-color); }
    .item-row { margin-bottom: 10px; }
    .item-amount-line {
      display: flex; align-items: center; justify-content: space-between; gap: 8px;
    }
    .item-amount-line label {
      flex: 1; font-size: 12px; font-weight: 600; color: #475569; margin: 0;
    }
    .item-amount-line .amount-input {
      width: 110px; flex-shrink: 0; box-sizing: border-box; padding: 7px 10px;
      border: 1px solid #cbd5e1; border-radius: 6px; font-size: 13px; text-align: right;
    }
    .calc-method-row { margin-top: 4px; }
    .calc-method-row.hidden { display: none; }
    .calc-method-input {
      width: 100%; box-sizing: border-box; padding: 6px 10px;
      border: 1px solid #cbd5e1; border-radius: 6px; font-size: 12px; text-align: left;
      color: #475569; background: #f8fafc;
    }
    .calc-method-input::placeholder { color: #94a3b8; }

    .detail-toolbar {
      display: flex; justify-content: space-between; align-items: center; gap: 12px;
      margin-bottom: 12px; padding: 8px 12px;
      background: #f1f5f9; border: 1px solid var(--border); border-radius: 6px;
    }
    .detail-toolbar-actions { display: flex; align-items: center; gap: 8px; }
    .calc-toggle-wrap {
      display: flex; align-items: center; gap: 10px;
      font-size: 13px; font-weight: 600; color: #334155;
    }
    .calc-toggle-wrap .required { color: #e11d48; }
    .toggle-switch {
      position: relative; display: inline-flex; align-items: center;
      width: 52px; height: 26px; cursor: pointer;
    }
    .toggle-switch input { opacity: 0; width: 0; height: 0; position: absolute; }
    .toggle-slider {
      position: absolute; inset: 0; background: #cbd5e1; border-radius: 26px;
      transition: background 0.2s;
    }
    .toggle-slider::before {
      content: ''; position: absolute; width: 20px; height: 20px;
      left: 3px; bottom: 3px; background: #fff; border-radius: 50%;
      transition: transform 0.2s;
    }
    .toggle-switch input:checked + .toggle-slider { background: #22c55e; }
    .toggle-switch input:checked + .toggle-slider::before { transform: translateX(26px); }
    .toggle-label {
      position: absolute; font-size: 10px; font-weight: 700; color: #fff;
      top: 50%; transform: translateY(-50%); pointer-events: none;
    }
    .toggle-label.off { right: 7px; }
    .toggle-label.on { left: 8px; display: none; }
    .toggle-switch input:checked ~ .toggle-label.off { display: none; }
    .toggle-switch input:checked ~ .toggle-label.on { display: block; }

    .net-pay-bar {
      background: linear-gradient(135deg, #2563eb, #1d4ed8); color: #fff;
      padding: 14px 18px; border-radius: 8px; text-align: center;
      font-size: 18px; font-weight: 700; margin: 16px 0 12px 0;
    }
    .detail-actions { display: flex; gap: 8px; justify-content: center; }

    /* 하단 종합정보 */
    .summary-bar {
      background: #fff; border: 1px solid var(--border); border-radius: 8px; padding: 16px 20px;
      display: flex; align-items: center; gap: 16px;
    }
    .summary-bar .summary-label { font-size: 14px; font-weight: 700; min-width: 80px; }
    .summary-box {
      flex: 1; text-align: center; padding: 14px; border-radius: 8px; font-weight: 700;
    }
    .summary-box .label { font-size: 12px; margin-bottom: 4px; opacity: 0.8; }
    .summary-box .value { font-size: 20px; }
    .summary-count { background: #f1f5f9; color: #334155; }
    .summary-pay { background: #dbeafe; color: var(--pay-color); }
    .summary-deduct { background: #ffe4e6; color: var(--deduct-color); }
    .summary-net { background: #f1f5f9; color: #334155; }

    /* 모달 */
    .modal-overlay {
      display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%;
      background: rgba(0,0,0,0.5); z-index: 1000; justify-content: center; align-items: center;
    }
    .modal-overlay.show { display: flex; }
    .modal-box {
      background: #fff; border-radius: 10px; width: 90%; max-width: 700px;
      max-height: 80vh; overflow: hidden; box-shadow: 0 20px 40px rgba(0,0,0,0.2);
    }
    .modal-header {
      background: #2b3648; color: #fff; padding: 14px 18px;
      font-size: 15px; font-weight: 700; display: flex; justify-content: space-between;
    }
    .modal-header .close-btn { background: none; border: none; color: #fff; font-size: 20px; cursor: pointer; }
    .modal-body { padding: 16px 18px; overflow-y: auto; max-height: 60vh; }
    .modal-footer { padding: 12px 18px; border-top: 1px solid var(--border); display: flex; gap: 8px; justify-content: center; }
    .modal-search { display: flex; gap: 8px; margin-bottom: 12px; align-items: center; }
    .modal-search input { flex: 1; padding: 7px 10px; border: 1px solid #cbd5e1; border-radius: 6px; font-size: 13px; }
    .empty-msg { text-align: center; padding: 40px; color: #94a3b8; font-size: 14px; }
  </style>
</head>
<body>

<layout:headerNav />

<div class="container">
  <div class="page-header">
    <h2>💳 給与入力/管理</h2>
    <p>帰属年月ごとに社員の給与を入力・修正・削除できます。</p>
  </div>

  <!-- 필터 바 -->
  <form id="filterForm" action="${pageContext.request.contextPath}/salary/entry.do" method="get">
    <div class="filter-bar">
      <label>帰属年月</label>
      <select name="payYear" onchange="this.form.submit()">
        <c:forEach var="y" begin="2024" end="2027">
          <option value="${y}" ${y eq entryData.payYear ? 'selected' : ''}>${y}年</option>
        </c:forEach>
      </select>
      <select name="payMonth" onchange="this.form.submit()">
        <c:forEach var="m" begin="1" end="12">
          <option value="${m}" ${m eq entryData.payMonth ? 'selected' : ''}>${m}月</option>
        </c:forEach>
      </select>
      <label>給与回数</label>
      <select name="paySeq" onchange="this.form.submit()">
        <option value="1" ${entryData.paySeq eq 1 ? 'selected' : ''}>給与-01</option>
        <option value="2" ${entryData.paySeq eq 2 ? 'selected' : ''}>給与-02</option>
      </select>
      <span class="filter-info">
        精算期間: <strong>${entryData.startDate} ~ ${entryData.endDate}</strong> &nbsp;|&nbsp;
        給与支給日: <strong>${entryData.payDate}</strong>
      </span>
    </div>
  </form>

  <div class="main-split">
    <!-- 좌측: 사원 목록 -->
    <div class="panel">
      <div class="panel-head">
        <h3>給与対象社員</h3>
        <span style="font-size:12px;color:#64748b;">${entryData.summary.count}件</span>
      </div>
      <div class="panel-body">
        <div class="action-bar">
          <button type="button" class="btn btn-outline btn-sm" onclick="openLoadModal()">前回給与を読み込む</button>
          <button type="button" class="btn btn-primary btn-sm" onclick="openEmpModal()">+ 新規追加</button>
          <button type="button" class="btn btn-danger btn-sm" onclick="deleteSelected()">選択削除</button>
          <button type="button" class="btn btn-warning btn-sm" onclick="deleteAll()">一括削除</button>
        </div>
        <table class="list-table">
          <thead>
            <tr>
              <th width="30"><input type="checkbox" id="checkAll"></th>
              <th>区分</th>
              <th>氏名</th>
              <th>部署</th>
              <th>支給総額</th>
              <th>控除総額</th>
              <th>実支給額</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="sal" items="${entryData.salaryList}">
              <tr class="${sal.salaryId eq entryData.selectedSalaryId ? 'selected' : ''}"
                  onclick="selectSalary(${sal.salaryId})">
                <td onclick="event.stopPropagation()">
                  <input type="checkbox" class="salary-chk" value="${sal.salaryId}">
                </td>
                <td>${sal.employmentType}</td>
                <td>${sal.nameKor}</td>
                <td>${sal.deptName}</td>
                <td class="amount-pay"><fmt:formatNumber value="${sal.totalPay}" pattern="#,###"/></td>
                <td class="amount-deduct"><fmt:formatNumber value="${sal.totalDeduct}" pattern="#,###"/></td>
                <td><fmt:formatNumber value="${sal.netPay}" pattern="#,###"/></td>
              </tr>
            </c:forEach>
            <c:if test="${empty entryData.salaryList}">
              <tr><td colspan="7" class="empty-msg">登録された給与対象社員がいません。</td></tr>
            </c:if>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 우측: 급여 상세 입력 -->
    <div class="panel">
      <div class="panel-head">
        <h3>
          <c:choose>
            <c:when test="${not empty entryData.selectedSalary}">${entryData.selectedSalary.nameKor} 給与詳細</c:when>
            <c:otherwise>給与詳細入力</c:otherwise>
          </c:choose>
        </h3>
      </div>
      <div class="panel-body">
        <c:choose>
          <c:when test="${not empty entryData.selectedSalary}">
            <form id="detailForm" action="${pageContext.request.contextPath}/salary/entry/save.do" method="post">
              <input type="hidden" name="salaryId" value="${entryData.selectedSalaryId}">
              <input type="hidden" name="payYear" value="${entryData.payYear}">
              <input type="hidden" name="payMonth" value="${entryData.payMonth}">
              <input type="hidden" name="paySeq" value="${entryData.paySeq}">

              <div class="detail-toolbar">
                <div class="detail-toolbar-actions">
                  <button type="button" class="btn btn-primary btn-sm" onclick="autoCalc()">自動計算</button>
                </div>
                <div class="calc-toggle-wrap">
                  <span class="required">*</span> 計算方法
                  <label class="toggle-switch" title="計算方法の表示/非表示">
                    <input type="checkbox" id="calcMethodToggle" onchange="toggleCalcMethod(this.checked)">
                    <span class="toggle-slider"></span>
                    <span class="toggle-label off">off</span>
                    <span class="toggle-label on">on</span>
                  </label>
                </div>
              </div>

              <div class="detail-columns">
                <!-- 지급항목 -->
                <div class="detail-section pay">
                  <h4>支給項目</h4>
                  <c:forEach var="item" items="${entryData.payDetails}">
                    <div class="item-row">
                      <input type="hidden" name="detailId" value="${item.detailId}">
                      <div class="item-amount-line">
                        <label>${item.itemName}</label>
                        <input type="text" name="amount" class="amount-input pay-input"
                               value="<fmt:formatNumber value='${item.amount}' pattern='#,###'/>"
                               data-type="PAY" oninput="calcTotals()">
                      </div>
                      <div class="calc-method-row hidden">
                        <input type="text" name="calcMethod" class="calc-method-input"
                               value="${item.calcMethod}" placeholder="計算方法">
                      </div>
                    </div>
                  </c:forEach>
                </div>
                <!-- 공제항목 -->
                <div class="detail-section deduct">
                  <h4>控除項目</h4>
                  <c:forEach var="item" items="${entryData.deductDetails}">
                    <div class="item-row">
                      <input type="hidden" name="detailId" value="${item.detailId}">
                      <div class="item-amount-line">
                        <label>${item.itemName}</label>
                        <input type="text" name="amount" class="amount-input deduct-input"
                               value="<fmt:formatNumber value='${item.amount}' pattern='#,###'/>"
                               data-type="DEDUCT" oninput="calcTotals()">
                      </div>
                      <div class="calc-method-row hidden">
                        <input type="text" name="calcMethod" class="calc-method-input"
                               value="${item.calcMethod}" placeholder="計算方法">
                      </div>
                    </div>
                  </c:forEach>
                </div>
              </div>

              <div class="net-pay-bar" id="netPayBar">
                実支給額 : <span id="netPayValue">0</span> 円
              </div>
              <div class="detail-actions">
                <button type="submit" class="btn btn-primary">保存</button>
                <button type="button" class="btn btn-outline" onclick="clearAmounts()">内容をクリア</button>
              </div>
            </form>
          </c:when>
          <c:otherwise>
            <div class="empty-msg">左側で社員を選択するか、新規社員を追加してください。</div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>

  <!-- 하단 종합정보 -->
  <div class="summary-bar">
    <div class="summary-label">給与総合情報</div>
    <div class="summary-box summary-count">
      <div class="label">月合計</div>
      <div class="value">${entryData.summary.count} 件</div>
    </div>
    <div class="summary-box summary-pay">
      <div class="label">支給総額</div>
      <div class="value"><fmt:formatNumber value="${entryData.summary.totalPay}" pattern="#,###"/> 円</div>
    </div>
    <div class="summary-box summary-deduct">
      <div class="label">控除総額</div>
      <div class="value"><fmt:formatNumber value="${entryData.summary.totalDeduct}" pattern="#,###"/> 円</div>
    </div>
    <div class="summary-box summary-net">
      <div class="label">実支給額</div>
      <div class="value"><fmt:formatNumber value="${entryData.summary.netPay}" pattern="#,###"/> 円</div>
    </div>
  </div>
</div>

<!-- 지난급여 불러오기 모달 -->
<div class="modal-overlay" id="loadModal">
  <div class="modal-box">
    <div class="modal-header">
      <span>給与年月選択</span>
      <button class="close-btn" onclick="closeLoadModal()">&times;</button>
    </div>
    <form action="${pageContext.request.contextPath}/salary/entry/loadPrevious.do" method="post">
      <input type="hidden" name="payYear" value="${entryData.payYear}">
      <input type="hidden" name="payMonth" value="${entryData.payMonth}">
      <input type="hidden" name="paySeq" value="${entryData.paySeq}">
      <div class="modal-body">
        <p style="font-size:13px;color:#64748b;margin-bottom:12px;">読み込む給与の帰属年月を選択してください。</p>
        <div style="display:flex;gap:10px;align-items:center;">
          <select name="sourceYear" style="padding:7px;border-radius:6px;border:1px solid #cbd5e1;">
            <c:forEach var="y" begin="2024" end="2027">
              <option value="${y}">${y}年</option>
            </c:forEach>
          </select>
          <select name="sourceMonth" style="padding:7px;border-radius:6px;border:1px solid #cbd5e1;">
            <c:forEach var="m" begin="1" end="12">
              <option value="${m}">${m}月</option>
            </c:forEach>
          </select>
          <select name="sourceSeq" style="padding:7px;border-radius:6px;border:1px solid #cbd5e1;">
            <option value="1">給与-01</option>
            <option value="2">給与-02</option>
          </select>
        </div>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-primary">給与情報を読み込む</button>
        <button type="button" class="btn btn-outline" onclick="closeLoadModal()">キャンセル</button>
      </div>
    </form>
  </div>
</div>

<!-- 사원선택 모달 -->
<div class="modal-overlay" id="empModal">
  <div class="modal-box" style="max-width:800px;">
    <div class="modal-header">
      <span>給与支給 社員選択</span>
      <button class="close-btn" onclick="closeEmpModal()">&times;</button>
    </div>
    <form id="addEmpForm" action="${pageContext.request.contextPath}/salary/entry/addEmployees.do" method="post">
      <input type="hidden" name="salaryDateId" value="${entryData.salaryDateId}">
      <input type="hidden" name="payYear" value="${entryData.payYear}">
      <input type="hidden" name="payMonth" value="${entryData.payMonth}">
      <input type="hidden" name="paySeq" value="${entryData.paySeq}">
      <div class="modal-body" id="empModalBody">
        <div class="modal-search">
          <input type="text" id="empSearchKeyword" placeholder="社員検索（氏名）">
          <button type="button" class="btn btn-primary btn-sm" onclick="searchEmployees()">検索</button>
          <button type="button" class="btn btn-outline btn-sm" onclick="searchEmployees(true)">全体表示</button>
        </div>
        <div id="empSearchResult">
          <p class="empty-msg">検索ボタンを押して社員を照会してください。</p>
        </div>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-primary">社員選択</button>
        <button type="button" class="btn btn-outline" onclick="closeEmpModal()">選択取消</button>
      </div>
    </form>
  </div>
</div>

<layout:footer />

<script>
  const ctx = '${pageContext.request.contextPath}';
  const payYear = ${entryData.payYear};
  const payMonth = ${entryData.payMonth};
  const paySeq = ${entryData.paySeq};
  const salaryDateId = ${entryData.salaryDateId};

  function selectSalary(salaryId) {
    location.href = ctx + '/salary/entry.do?payYear=' + payYear + '&payMonth=' + payMonth
      + '&paySeq=' + paySeq + '&salaryId=' + salaryId;
  }

  function parseAmount(val) {
    return parseInt((val || '0').replace(/,/g, ''), 10) || 0;
  }

  function formatAmount(num) {
    return num.toLocaleString('ja-JP');
  }

  function calcTotals() {
    let totalPay = 0, totalDeduct = 0;
    document.querySelectorAll('.pay-input').forEach(el => totalPay += parseAmount(el.value));
    document.querySelectorAll('.deduct-input').forEach(el => totalDeduct += parseAmount(el.value));
    const net = totalPay - totalDeduct;
    const el = document.getElementById('netPayValue');
    if (el) el.textContent = formatAmount(net);
  }

  function toggleCalcMethod(show) {
    document.querySelectorAll('.calc-method-row').forEach(row => {
      row.classList.toggle('hidden', !show);
    });
    try {
      localStorage.setItem('salaryCalcMethodVisible', show ? '1' : '0');
    } catch (e) { /* ignore */ }
  }

  function initCalcMethodToggle() {
    const toggle = document.getElementById('calcMethodToggle');
    if (!toggle) return;
    let visible = false;
    try {
      visible = localStorage.getItem('salaryCalcMethodVisible') === '1';
    } catch (e) { /* ignore */ }
    toggle.checked = visible;
    toggleCalcMethod(visible);
  }

  function clearAmounts() {
    document.querySelectorAll('.amount-input').forEach(el => { el.value = '0'; });
    calcTotals();
  }

  function autoCalc() {
    const form = document.getElementById('detailForm');
    if (!form) return;
    const salaryId = form.salaryId.value;
    const body = new URLSearchParams();
    body.set('salaryId', salaryId);
    body.set('payYear', payYear);
    body.set('payMonth', payMonth);
    body.set('paySeq', paySeq);
    fetch(ctx + '/salary/entry/autoCalc.do', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
      body: body.toString()
    })
      .then(res => res.json())
      .then(data => {
        if (!data || !data.ok || !data.details) return;
        data.details.forEach(item => {
          const row = form.querySelector('input[name="detailId"][value="' + item.detailId + '"]');
          if (!row) return;
          const amountInput = row.closest('.item-row')?.querySelector('.amount-input');
          if (amountInput) {
            amountInput.value = formatAmount(item.amount);
          }
        });
        calcTotals();
      })
      .catch(() => { /* ignore */ });
  }

  document.getElementById('checkAll')?.addEventListener('change', function() {
    document.querySelectorAll('.salary-chk').forEach(cb => cb.checked = this.checked);
  });

  function deleteSelected() {
    const checked = document.querySelectorAll('.salary-chk:checked');
    if (checked.length === 0) { alert('削除する社員を選択してください。'); return; }
    if (!confirm('選択した ' + checked.length + '名の給与を削除しますか？')) return;
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = ctx + '/salary/entry/delete.do';
    addHidden(form, 'payYear', payYear);
    addHidden(form, 'payMonth', payMonth);
    addHidden(form, 'paySeq', paySeq);
    checked.forEach(cb => addHidden(form, 'salaryIds', cb.value));
    document.body.appendChild(form);
    form.submit();
  }

  function deleteAll() {
    if (!confirm('現在の帰属年月のすべての給与データを削除しますか？')) return;
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = ctx + '/salary/entry/deleteAll.do';
    addHidden(form, 'payYear', payYear);
    addHidden(form, 'payMonth', payMonth);
    addHidden(form, 'paySeq', paySeq);
    document.body.appendChild(form);
    form.submit();
  }

  function addHidden(form, name, value) {
    const input = document.createElement('input');
    input.type = 'hidden'; input.name = name; input.value = value;
    form.appendChild(input);
  }

  function openLoadModal() { document.getElementById('loadModal').classList.add('show'); }
  function closeLoadModal() { document.getElementById('loadModal').classList.remove('show'); }
  function openEmpModal() {
    document.getElementById('empModal').classList.add('show');
    searchEmployees(true);
  }
  function closeEmpModal() { document.getElementById('empModal').classList.remove('show'); }

  function toggleEmpAll(el) {
    document.querySelectorAll('#empSearchResult .emp-chk').forEach(cb => { cb.checked = el.checked; });
  }

  function searchEmployees(showAll) {
    const keyword = showAll ? '' : document.getElementById('empSearchKeyword').value;
    fetch(ctx + '/salary/entry/searchEmployee.do?salaryDateId=' + salaryDateId
      + '&searchKeyword=' + encodeURIComponent(keyword))
      .then(res => res.text())
      .then(html => {
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, 'text/html');
        const table = doc.getElementById('empTableContent');
        document.getElementById('empSearchResult').innerHTML = table ? table.outerHTML
          : '<p class="empty-msg">検索結果がありません。</p>';
      });
  }

  initCalcMethodToggle();
  calcTotals();
</script>
</body>
</html>
