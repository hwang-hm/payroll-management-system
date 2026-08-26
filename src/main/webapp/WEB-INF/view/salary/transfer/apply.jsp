<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>ヘキサゴンゾーン - 給与振込申請</title>
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
    .container { max-width: 1480px; margin: 0 auto 60px; padding: 0 20px; }
    .page-header { margin-bottom: 16px; border-bottom: 2px solid var(--border); padding-bottom: 14px; }
    .page-header h2 { font-size: 24px; font-weight: 700; margin: 0 0 4px 0; }
    .page-header p { margin: 0; font-size: 13px; color: var(--text-muted); }

    .filter-bar {
      background: #2b3648; color: #fff; border-radius: 8px; padding: 14px 18px;
      display: flex; flex-wrap: wrap; align-items: center; gap: 14px; margin-bottom: 16px;
    }
    .filter-bar label { font-size: 13px; font-weight: 600; }
    .filter-bar select {
      padding: 6px 10px; border-radius: 6px; border: 1px solid #cbd5e1; font-size: 13px;
    }

    .btn {
      display: inline-flex; align-items: center; justify-content: center; gap: 4px;
      padding: 7px 14px; font-size: 13px; font-weight: 600; border-radius: 6px;
      cursor: pointer; border: none; text-decoration: none;
    }
    .btn-primary { background: var(--primary); color: #fff; }
    .btn-primary:hover { background: var(--primary-hover); }
    .btn-danger { background: #e11d48; color: #fff; }
    .btn-outline { background: #fff; border: 1px solid #cbd5e1; color: #334155; }
    .btn-sm { padding: 5px 10px; font-size: 12px; }

    .alert {
      background: #ecfdf5; border: 1px solid #a7f3d0; color: #065f46;
      padding: 10px 14px; border-radius: 8px; margin-bottom: 14px; font-size: 13px;
    }

    .main-split { display: grid; grid-template-columns: 320px 1fr; gap: 16px; margin-bottom: 16px; }
    .panel { background: #fff; border: 1px solid var(--border); border-radius: 8px; overflow: hidden; }
    .panel-head {
      padding: 10px 14px; background: #f8fafc; border-bottom: 1px solid var(--border);
      display: flex; justify-content: space-between; align-items: center;
    }
    .panel-head h3 { margin: 0; font-size: 14px; font-weight: 700; }
    .panel-body { padding: 12px; }

    .form-row { margin-bottom: 10px; }
    .form-row label { display: block; font-size: 12px; font-weight: 600; color: #475569; margin-bottom: 4px; }
    .form-row input, .form-row select {
      width: 100%; box-sizing: border-box; padding: 7px 10px;
      border: 1px solid #cbd5e1; border-radius: 6px; font-size: 13px;
    }
    .item-tools { display: flex; gap: 6px; margin-bottom: 10px; }
    .item-tools select { flex: 1; }
    .selected-list {
      list-style: none; margin: 0; padding: 0; border: 1px solid var(--border); border-radius: 6px;
      min-height: 220px; max-height: 360px; overflow-y: auto;
    }
    .selected-list li {
      display: flex; align-items: center; justify-content: space-between; gap: 8px;
      padding: 8px 10px; border-bottom: 1px solid var(--border); font-size: 13px;
    }
    .selected-list li:last-child { border-bottom: none; }
    .selected-list .name { flex: 1; font-weight: 600; }
    .selected-list .ops { display: flex; gap: 4px; }
    .form-actions { display: flex; gap: 8px; margin-top: 12px; }

    .list-table { width: 100%; border-collapse: collapse; font-size: 13px; }
    .list-table th {
      background: #f8fafc; color: #475569; font-weight: 700; padding: 10px 8px;
      border-bottom: 2px solid var(--border); text-align: center; white-space: nowrap;
    }
    .list-table td {
      padding: 9px 8px; border-bottom: 1px solid var(--border); text-align: center; white-space: nowrap;
    }
    .list-table tbody tr:hover { background: #eff6ff; }
    .list-table tfoot td {
      background: #fef9c3; font-weight: 700; padding: 10px 8px;
      border-top: 2px solid #fde047; border-bottom: none;
    }
    .amount-pay { color: var(--pay-color); font-weight: 600; }
    .amount-deduct { color: var(--deduct-color); font-weight: 600; }
    .empty-msg { text-align: center; padding: 40px; color: #94a3b8; font-size: 14px; }
    .table-wrap { overflow-x: auto; }
    .execute-bar { display: flex; justify-content: flex-end; margin-top: 12px; }
  </style>
</head>
<body>

<layout:headerNav />

<div class="container">
  <div class="page-header">
    <h2>給与振込申請</h2>
    <p>帰属年月・回数の対象者を確認し、給与振込を実行します。</p>
  </div>

  <c:if test="${not empty applyData.message}">
    <div class="alert">${applyData.message}</div>
  </c:if>

  <form id="filterForm" action="${pageContext.request.contextPath}/salary/transfer/apply.do" method="get">
    <div class="filter-bar">
      <label>帰属年度</label>
      <select name="payYear" onchange="this.form.submit()">
        <c:forEach var="y" items="${applyData.yearOptions}">
          <option value="${y}" ${y eq applyData.payYear ? 'selected' : ''}>${y}年</option>
        </c:forEach>
      </select>
      <label>月</label>
      <select name="payMonth" onchange="this.form.submit()">
        <c:forEach var="m" begin="1" end="12">
          <option value="${m}" ${m eq applyData.payMonth ? 'selected' : ''}>${m}月</option>
        </c:forEach>
      </select>
      <label>回数</label>
      <select name="paySeq" onchange="this.form.submit()">
        <c:forEach var="s" begin="1" end="5">
          <option value="${s}" ${s eq applyData.paySeq ? 'selected' : ''}>給与-${s}</option>
        </c:forEach>
      </select>
      <label>振込様式</label>
      <select name="formId" onchange="this.form.submit()">
        <c:if test="${empty applyData.forms}">
          <option value="">様式なし</option>
        </c:if>
        <c:forEach var="f" items="${applyData.forms}">
          <option value="${f.formId}" ${f.formId eq applyData.selectedFormId ? 'selected' : ''}>${f.formName}</option>
        </c:forEach>
      </select>
    </div>
  </form>

  <div class="main-split">
    <div class="panel">
      <div class="panel-head">
        <h3>振込様式設定</h3>
      </div>
      <div class="panel-body">
        <form id="formSaveForm" action="${pageContext.request.contextPath}/salary/transfer/form/save.do" method="post">
          <input type="hidden" name="payYear" value="${applyData.payYear}">
          <input type="hidden" name="payMonth" value="${applyData.payMonth}">
          <input type="hidden" name="paySeq" value="${applyData.paySeq}">
          <input type="hidden" name="formId" id="editFormId" value="${applyData.selectedFormId}">
          <div id="itemHiddenFields"></div>

          <div class="form-row">
            <label>様式名</label>
            <input type="text" name="formName" id="formName" value="${applyData.selectedFormName}" maxlength="100" placeholder="例: 基本振込様式">
          </div>

          <div class="item-tools">
            <select id="addColumnSelect">
              <c:forEach var="col" items="${applyData.allColumns}">
                <option value="${col.code}">${col.label}</option>
              </c:forEach>
            </select>
            <button type="button" class="btn btn-outline btn-sm" onclick="addSelectedColumn()">追加</button>
          </div>

          <ul class="selected-list" id="selectedList"></ul>

          <div class="form-actions">
            <button type="button" class="btn btn-outline btn-sm" onclick="startNewForm()">新規</button>
            <button type="submit" class="btn btn-primary btn-sm">様式保存</button>
          </div>
        </form>
      </div>
    </div>

    <div class="panel">
      <div class="panel-head">
        <h3>振込対象一覧</h3>
        <span style="font-size:12px;color:#64748b;">${applyData.rows.size()}名</span>
      </div>
      <div class="panel-body">
        <div class="table-wrap">
          <c:choose>
            <c:when test="${empty applyData.rows}">
              <div class="empty-msg">該当年月・回数の給与対象がありません。</div>
            </c:when>
            <c:otherwise>
              <table class="list-table">
                <thead>
                  <tr>
                    <c:forEach var="col" items="${applyData.visibleColumns}">
                      <th>${col.label}</th>
                    </c:forEach>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="row" items="${applyData.rows}">
                    <tr>
                      <c:forEach var="col" items="${applyData.visibleColumns}">
                        <td class="${col.code eq 'TOTAL_PAY' or col.code eq 'NET_PAY' ? 'amount-pay' : (col.code eq 'TOTAL_DEDUCT' ? 'amount-deduct' : '')}">
                          ${row.getValueByCode(col.code)}
                        </td>
                      </c:forEach>
                    </tr>
                  </c:forEach>
                </tbody>
                <tfoot>
                  <tr>
                    <c:forEach var="col" items="${applyData.visibleColumns}" varStatus="st">
                      <td>
                        <c:choose>
                          <c:when test="${st.first}">合計</c:when>
                          <c:when test="${col.code eq 'TOTAL_PAY'}"><span class="amount-pay"><fmt:formatNumber value="${applyData.sumTotalPay}" pattern="#,###"/></span></c:when>
                          <c:when test="${col.code eq 'TOTAL_DEDUCT'}"><span class="amount-deduct"><fmt:formatNumber value="${applyData.sumTotalDeduct}" pattern="#,###"/></span></c:when>
                          <c:when test="${col.code eq 'NET_PAY'}"><span class="amount-pay"><fmt:formatNumber value="${applyData.sumNetPay}" pattern="#,###"/></span></c:when>
                          <c:otherwise></c:otherwise>
                        </c:choose>
                      </td>
                    </c:forEach>
                  </tr>
                </tfoot>
              </table>
            </c:otherwise>
          </c:choose>
        </div>

        <div class="execute-bar">
          <form action="${pageContext.request.contextPath}/salary/transfer/execute.do" method="post"
                onsubmit="return confirm('選択した帰属年月・回数の対象で給与振込を実行しますか？');">
            <input type="hidden" name="payYear" value="${applyData.payYear}">
            <input type="hidden" name="payMonth" value="${applyData.payMonth}">
            <input type="hidden" name="paySeq" value="${applyData.paySeq}">
            <input type="hidden" name="formId" value="${applyData.selectedFormId}">
            <button type="submit" class="btn btn-danger" ${empty applyData.rows ? 'disabled' : ''}>給与振込する</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<layout:footer />

<script>
  const columnLabelMap = {
    <c:forEach var="col" items="${applyData.allColumns}" varStatus="st">
    "${col.code}": "${col.label}"<c:if test="${!st.last}">,</c:if>
    </c:forEach>
  };

  let selectedCodes = [
    <c:forEach var="col" items="${applyData.visibleColumns}" varStatus="st">
    "${col.code}"<c:if test="${!st.last}">,</c:if>
    </c:forEach>
  ];

  function renderSelectedList() {
    const list = document.getElementById('selectedList');
    const hidden = document.getElementById('itemHiddenFields');
    list.innerHTML = '';
    hidden.innerHTML = '';

    selectedCodes.forEach(function (code, index) {
      const li = document.createElement('li');
      li.innerHTML =
        '<span class="name">' + (columnLabelMap[code] || code) + '</span>' +
        '<span class="ops">' +
        '<button type="button" class="btn btn-outline btn-sm" onclick="moveItem(' + index + ', -1)">▲</button>' +
        '<button type="button" class="btn btn-outline btn-sm" onclick="moveItem(' + index + ', 1)">▼</button>' +
        '<button type="button" class="btn btn-outline btn-sm" onclick="removeItem(' + index + ')">削除</button>' +
        '</span>';
      list.appendChild(li);

      const input = document.createElement('input');
      input.type = 'hidden';
      input.name = 'itemCodes';
      input.value = code;
      hidden.appendChild(input);
    });
  }

  function addSelectedColumn() {
    const select = document.getElementById('addColumnSelect');
    const code = select.value;
    if (!code) return;
    if (selectedCodes.indexOf(code) >= 0) {
      alert('すでに追加された項目です。');
      return;
    }
    selectedCodes.push(code);
    renderSelectedList();
  }

  function removeItem(index) {
    selectedCodes.splice(index, 1);
    renderSelectedList();
  }

  function moveItem(index, delta) {
    const next = index + delta;
    if (next < 0 || next >= selectedCodes.length) return;
    const tmp = selectedCodes[index];
    selectedCodes[index] = selectedCodes[next];
    selectedCodes[next] = tmp;
    renderSelectedList();
  }

  function startNewForm() {
    document.getElementById('editFormId').value = '';
    document.getElementById('formName').value = '';
    selectedCodes = [];
    renderSelectedList();
  }

  document.getElementById('formSaveForm').addEventListener('submit', function (e) {
    if (!document.getElementById('formName').value.trim()) {
      alert('様式名を入力してください。');
      e.preventDefault();
      return;
    }
    if (selectedCodes.length === 0) {
      alert('表示する項目を1つ以上追加してください。');
      e.preventDefault();
    }
  });

  renderSelectedList();
</script>
</body>
</html>
