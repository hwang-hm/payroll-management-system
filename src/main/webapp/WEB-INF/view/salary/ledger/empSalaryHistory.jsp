<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>社員別給与内訳</title>
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
    .EmpSalaryHistory-container { max-width: 1400px; margin: 0 auto 60px; padding: 0 20px; }
    .EmpSalaryHistory-header { margin-bottom: 16px; border-bottom: 2px solid var(--border); padding-bottom: 14px; }
    .EmpSalaryHistory-header h2 { font-size: 24px; font-weight: 700; margin: 0 0 4px 0; }
    .EmpSalaryHistory-header p { margin: 0; font-size: 13px; color: var(--text-muted); }

    .EmpSalaryHistory-filter {
      background: #2b3648; color: #fff; border-radius: 8px; padding: 14px 18px;
      display: flex; flex-wrap: wrap; align-items: center; gap: 14px; margin-bottom: 16px;
    }
    .EmpSalaryHistory-filter label { font-size: 13px; font-weight: 600; }
    .EmpSalaryHistory-filter select, .EmpSalaryHistory-filter input {
      padding: 6px 10px; border-radius: 6px; border: 1px solid #cbd5e1; font-size: 13px;
    }
    .EmpSalaryHistory-emp-wrap { display: flex; align-items: center; gap: 4px; }
    .EmpSalaryHistory-emp-wrap input { width: 140px; cursor: pointer; background: #fff; }

    .btn {
      display: inline-flex; align-items: center; justify-content: center; gap: 4px;
      padding: 7px 14px; font-size: 13px; font-weight: 600; border-radius: 6px;
      cursor: pointer; border: none; text-decoration: none;
    }
    .btn-primary { background: var(--primary); color: #fff; }
    .btn-primary:hover { background: var(--primary-hover); }
    .btn-outline { background: #fff; border: 1px solid #cbd5e1; color: #334155; }
    .btn-muted { background: #64748b; color: #fff; }
    .btn-sm { padding: 5px 10px; font-size: 12px; }

    .EmpSalaryHistory-panel { background: #fff; border: 1px solid var(--border); border-radius: 8px; overflow: hidden; }
    .EmpSalaryHistory-panel-head {
      padding: 10px 14px; background: #f8fafc; border-bottom: 1px solid var(--border);
      display: flex; justify-content: space-between; align-items: center;
    }
    .EmpSalaryHistory-panel-head h3 { margin: 0; font-size: 14px; font-weight: 700; }
    .EmpSalaryHistory-panel-body { padding: 12px; overflow-x: auto; }

    .EmpSalaryHistory-table { width: 100%; border-collapse: collapse; font-size: 12px; min-width: 1100px; color: #1e293b; }
    .EmpSalaryHistory-table th {
      font-weight: 700; padding: 8px 6px;
      border: 1px solid #c5d0dc; text-align: center;
    }
    .EmpSalaryHistory-table td {
      padding: 8px 6px; border: 1px solid var(--border); text-align: center; color: #1e293b;
    }
    .EmpSalaryHistory-table thead tr:first-child th.EmpSalaryHistory-group-pay {
      background: #5b8fc7; color: #fff; font-size: 13px; padding: 10px 6px;
    }
    .EmpSalaryHistory-table thead tr:first-child th.EmpSalaryHistory-group-deduct {
      background: #d96b6b; color: #fff; font-size: 13px; padding: 10px 6px;
    }
    .EmpSalaryHistory-table thead tr:last-child th.EmpSalaryHistory-col-pay {
      background: #e8f1fa; color: #334155;
    }
    .EmpSalaryHistory-table thead tr:last-child th.EmpSalaryHistory-col-deduct {
      background: #fdeaea; color: #334155;
    }
    .EmpSalaryHistory-table tbody tr:nth-child(even) { background: #f8fafc; }
    .EmpSalaryHistory-table tbody tr:hover { background: #f1f5f9; }
    .EmpSalaryHistory-table tfoot td {
      background: #fef9c3; font-weight: 700; padding: 9px 6px;
      border-top: 2px solid #fde047; color: #1e293b;
    }
    .empty-msg { text-align: center; padding: 40px; color: #94a3b8; font-size: 14px; }
    .EmpSalaryHistory-actions { margin-top: 16px; display: flex; justify-content: flex-end; }

    .EmpSalaryHistory-modal-overlay {
      position: fixed; inset: 0; background: rgba(15, 23, 42, 0.4);
      display: none; align-items: center; justify-content: center; z-index: 2000;
    }
    .EmpSalaryHistory-modal {
      background: #fff; border-radius: 10px; width: 760px; max-width: 95%;
      box-shadow: 0 10px 30px rgba(15, 23, 42, 0.2); padding: 18px 20px;
    }
    .EmpSalaryHistory-modal h3 { margin: 0 0 12px 0; font-size: 18px; }
    .EmpSalaryHistory-modal-filter {
      display: flex; justify-content: space-between; gap: 10px; flex-wrap: wrap; margin-bottom: 10px;
    }
    .EmpSalaryHistory-modal-filter .left, .EmpSalaryHistory-modal-filter .right {
      display: flex; align-items: center; gap: 6px;
    }
    .EmpSalaryHistory-modal-table-wrap {
      max-height: 320px; overflow-y: auto; border: 1px solid var(--border); border-radius: 6px;
    }
    .EmpSalaryHistory-modal table { width: 100%; border-collapse: collapse; font-size: 13px; }
    .EmpSalaryHistory-modal th {
      position: sticky; top: 0; background: #f8fafc; padding: 8px; border-bottom: 2px solid var(--border);
    }
    .EmpSalaryHistory-modal td { padding: 8px; border-bottom: 1px solid var(--border); text-align: center; }
    .EmpSalaryHistory-modal tbody tr { cursor: pointer; }
    .EmpSalaryHistory-modal tbody tr:hover { background: #eff6ff; }
    .EmpSalaryHistory-modal tbody tr.selected { background: #dbeafe; outline: 2px solid #93c5fd; outline-offset: -2px; }
    .EmpSalaryHistory-modal-footer { margin-top: 16px; display: flex; justify-content: center; gap: 10px; }
  </style>
</head>
<body>

<layout:headerNav />

<div class="EmpSalaryHistory-container">
  <div class="EmpSalaryHistory-header">
    <h2>社員別給与内訳</h2>
    <p>照会期間と社員を選択すると、該当社員の月別給与内訳を一覧で確認できます。</p>
  </div>

  <form id="EmpSalaryHistoryFilterForm" action="${pageContext.request.contextPath}/salary/empHistory.do" method="get">
    <input type="hidden" id="empId" name="empId" value="${historyData.empId > 0 ? historyData.empId : ''}">
    <div class="EmpSalaryHistory-filter">
      <label>期間選択</label>
      <select name="startYear">
        <c:forEach var="y" begin="2017" end="2027">
          <option value="${y}" ${y eq historyData.startYear ? 'selected' : ''}>${y}年</option>
        </c:forEach>
      </select>
      <select name="startMonth">
        <c:forEach var="m" begin="1" end="12">
          <option value="${m}" ${m eq historyData.startMonth ? 'selected' : ''}>
            <fmt:formatNumber value="${m}" minIntegerDigits="2"/>月
          </option>
        </c:forEach>
      </select>
      <span>~</span>
      <select name="endYear">
        <c:forEach var="y" begin="2017" end="2027">
          <option value="${y}" ${y eq historyData.endYear ? 'selected' : ''}>${y}年</option>
        </c:forEach>
      </select>
      <select name="endMonth">
        <c:forEach var="m" begin="1" end="12">
          <option value="${m}" ${m eq historyData.endMonth ? 'selected' : ''}>
            <fmt:formatNumber value="${m}" minIntegerDigits="2"/>月
          </option>
        </c:forEach>
      </select>

      <label>社員選択</label>
      <div class="EmpSalaryHistory-emp-wrap">
        <input type="text" id="empName" name="empName" value="${historyData.empName}"
               placeholder="社員選択" readonly onclick="openEmpSalaryHistoryModal()">
        <button type="button" class="btn btn-primary btn-sm" onclick="openEmpSalaryHistoryModal()">検索</button>
      </div>
      <button type="button" class="btn btn-primary" onclick="submitEmpSalaryHistory()">給与内訳照会</button>
    </div>
  </form>

  <div class="EmpSalaryHistory-panel">
    <div class="EmpSalaryHistory-panel-head">
      <h3>月別給与内訳</h3>
      <span style="font-size:12px;color:#64748b;">${historyData.rows.size()}件</span>
    </div>
    <div class="EmpSalaryHistory-panel-body">
      <table class="EmpSalaryHistory-table">
        <thead>
          <tr>
            <th colspan="5" class="EmpSalaryHistory-group-pay">月別給与内訳</th>
            <th colspan="6" class="EmpSalaryHistory-group-deduct">社会保険及び所得税内訳</th>
          </tr>
          <tr>
            <th class="EmpSalaryHistory-col-pay">給与月（回数）</th>
            <th class="EmpSalaryHistory-col-pay">報酬月額</th>
            <th class="EmpSalaryHistory-col-pay">支給合計</th>
            <th class="EmpSalaryHistory-col-pay">控除合計</th>
            <th class="EmpSalaryHistory-col-pay">実支給額</th>
            <th class="EmpSalaryHistory-col-deduct">国民年金</th>
            <th class="EmpSalaryHistory-col-deduct">健康保険</th>
            <th class="EmpSalaryHistory-col-deduct">介護保険</th>
            <th class="EmpSalaryHistory-col-deduct">雇用保険</th>
            <th class="EmpSalaryHistory-col-deduct">所得税</th>
            <th class="EmpSalaryHistory-col-deduct">住民税</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="row" items="${historyData.rows}">
            <tr>
              <td>
                ${row.payYear}.<fmt:formatNumber value="${row.payMonth}" minIntegerDigits="2"/>
                (<fmt:formatNumber value="${row.paySeq}" minIntegerDigits="2"/>)
              </td>
              <td><fmt:formatNumber value="${row.basicPay}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.totalPay}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.totalDeduct}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.netPay}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.nationalPension}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.healthInsurance}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.longTermCare}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.employmentInsurance}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.incomeTax}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${row.residentTax}" pattern="#,###"/></td>
            </tr>
          </c:forEach>
          <c:if test="${empty historyData.rows}">
            <tr>
              <td colspan="11" class="empty-msg">
                <c:choose>
                  <c:when test="${historyData.empId <= 0}">社員を選択してから照会してください。</c:when>
                  <c:otherwise>該当期間に登録された給与内訳がありません。</c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:if>
        </tbody>
        <c:if test="${not empty historyData.rows}">
          <tfoot>
            <tr>
              <td>合計</td>
              <td><fmt:formatNumber value="${historyData.summary.basicPay}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${historyData.summary.totalPay}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${historyData.summary.totalDeduct}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${historyData.summary.netPay}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${historyData.summary.nationalPension}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${historyData.summary.healthInsurance}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${historyData.summary.longTermCare}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${historyData.summary.employmentInsurance}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${historyData.summary.incomeTax}" pattern="#,###"/></td>
              <td><fmt:formatNumber value="${historyData.summary.residentTax}" pattern="#,###"/></td>
            </tr>
          </tfoot>
        </c:if>
      </table>
    </div>
  </div>

  <div class="EmpSalaryHistory-actions">
    <a class="btn btn-muted" href="${pageContext.request.contextPath}/salary/ledger.do">給与台帳一覧</a>
  </div>
</div>

<div id="EmpSalaryHistoryModal" class="EmpSalaryHistory-modal-overlay">
  <div class="EmpSalaryHistory-modal">
    <h3>給与内訳照会 社員選択</h3>
    <div class="EmpSalaryHistory-modal-filter">
      <div class="left">
        <input type="text" id="modalKeyword" placeholder="社員検索" onkeyup="if (event.key === 'Enter') searchEmpSalaryHistoryEmployees()">
        <button type="button" class="btn btn-primary btn-sm" onclick="searchEmpSalaryHistoryEmployees()">検索</button>
        <button type="button" class="btn btn-outline btn-sm" onclick="resetEmpSalaryHistoryModal()">全体表示</button>
      </div>
      <div class="right">
        <select id="modalDeptId" onchange="searchEmpSalaryHistoryEmployees()">
          <option value="">部署別</option>
          <c:forEach var="dept" items="${historyData.departments}">
            <option value="${dept.deptId}">${dept.deptName}</option>
          </c:forEach>
        </select>
        <select id="modalStatus" onchange="searchEmpSalaryHistoryEmployees()">
          <option value="">状態別</option>
          <option value="Y">在職</option>
          <option value="N">退職</option>
        </select>
      </div>
    </div>
    <div class="EmpSalaryHistory-modal-table-wrap">
      <table>
        <thead>
          <tr>
            <th>区分</th>
            <th>社員番号</th>
            <th>氏名</th>
            <th>部署</th>
            <th>役職</th>
            <th>状態</th>
          </tr>
        </thead>
        <tbody id="EmpSalaryHistoryModalBody"></tbody>
      </table>
    </div>
    <div class="EmpSalaryHistory-modal-footer">
      <button type="button" class="btn btn-primary" onclick="confirmEmpSalaryHistory()">社員選択</button>
      <button type="button" class="btn btn-muted" onclick="closeEmpSalaryHistoryModal()">選択取消</button>
    </div>
  </div>
</div>

<layout:footer />

<script>
  const ctx = '${pageContext.request.contextPath}';
  let selectedModalEmpId = '';
  let selectedModalEmpName = '';

  function submitEmpSalaryHistory() {
    const empId = document.getElementById('empId').value;
    if (!empId) {
      alert('社員を選択してください。');
      openEmpSalaryHistoryModal();
      return;
    }
    document.getElementById('EmpSalaryHistoryFilterForm').submit();
  }

  function openEmpSalaryHistoryModal() {
    selectedModalEmpId = '';
    selectedModalEmpName = '';
    document.getElementById('EmpSalaryHistoryModal').style.display = 'flex';
    searchEmpSalaryHistoryEmployees();
  }

  function closeEmpSalaryHistoryModal() {
    document.getElementById('EmpSalaryHistoryModal').style.display = 'none';
  }

  function resetEmpSalaryHistoryModal() {
    document.getElementById('modalKeyword').value = '';
    document.getElementById('modalDeptId').value = '';
    document.getElementById('modalStatus').value = '';
    searchEmpSalaryHistoryEmployees();
  }

  function searchEmpSalaryHistoryEmployees() {
    const keyword = encodeURIComponent(document.getElementById('modalKeyword').value.trim());
    const deptId = encodeURIComponent(document.getElementById('modalDeptId').value);
    const status = encodeURIComponent(document.getElementById('modalStatus').value);
    const tbody = document.getElementById('EmpSalaryHistoryModalBody');
    tbody.innerHTML = '<tr><td colspan="6" class="empty-msg">一覧を読み込み中...</td></tr>';

    fetch(ctx + '/salary/empHistory/searchEmployee.do?keyword=' + keyword + '&deptId=' + deptId + '&status=' + status)
      .then(function(res) { return res.text(); })
      .then(function(html) {
        tbody.innerHTML = html;
        selectedModalEmpId = '';
        selectedModalEmpName = '';
      })
      .catch(function() {
        tbody.innerHTML = '<tr><td colspan="6" class="empty-msg">社員一覧を読み込めませんでした。</td></tr>';
      });
  }

  function selectEmpSalaryHistoryRow(row) {
    document.querySelectorAll('.EmpSalaryHistory-modal-row').forEach(function(el) {
      el.classList.remove('selected');
    });
    row.classList.add('selected');
    selectedModalEmpId = row.getAttribute('data-emp-id');
    selectedModalEmpName = row.getAttribute('data-emp-name');
  }

  function confirmEmpSalaryHistory() {
    if (!selectedModalEmpId) {
      alert('社員を選択してください。');
      return;
    }
    document.getElementById('empId').value = selectedModalEmpId;
    document.getElementById('empName').value = selectedModalEmpName;
    document.getElementById('EmpSalaryHistoryFilterForm').submit();
  }

  document.getElementById('EmpSalaryHistoryModal').addEventListener('click', function(e) {
    if (e.target === this) {
      closeEmpSalaryHistoryModal();
    }
  });
</script>

</body>
</html>
