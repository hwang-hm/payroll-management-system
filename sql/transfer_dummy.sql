-- ============================================================
-- 급여이체 1단계용 더미 데이터
-- 대상: payroll_information, employee_information_salary,
--       transfer_form, transfer_form_item
-- 선행: salary_dummy_202608.sql (emp 1~6, salary 2026-08 차수1)
-- 재실행 가능
-- ============================================================

-- ------------------------------------------------------------
-- 1. 회사 정보 (payroll FK)
-- ------------------------------------------------------------
MERGE INTO company_information t
USING (SELECT 1 AS company_id FROM dual) s
ON (t.company_id = s.company_id)
WHEN NOT MATCHED THEN
  INSERT (company_id, company_name, business_number, phone_number)
  VALUES (1, '헥사존', '123-45-67890', '02-1234-5678');

-- ------------------------------------------------------------
-- 2. 회사 출금계좌 (payroll_information)
-- ------------------------------------------------------------
MERGE INTO payroll_information t
USING (SELECT 1 AS company_id FROM dual) s
ON (t.company_id = s.company_id)
WHEN MATCHED THEN
  UPDATE SET
    salary_bank = '국민은행',
    salary_account = '1002-000-123456',
    account_holder = '헥사존',
    updated_at = SYSTIMESTAMP
WHEN NOT MATCHED THEN
  INSERT (company_id, salary_bank, salary_account, account_holder)
  VALUES (1, '국민은행', '1002-000-123456', '헥사존');

-- ------------------------------------------------------------
-- 3. 사원 입금계좌 (employee_information_salary)
--    2026-08 급여 대상 emp_id 1~6 + 여분 7~9
-- ------------------------------------------------------------
MERGE INTO employee_information_salary t
USING (
  SELECT 1 AS emp_id, '국민은행' AS bank, '110-111-000001' AS acct, 5000000 AS base_salary FROM dual UNION ALL
  SELECT 2, '신한은행', '110-222-000002', 3800000 FROM dual UNION ALL
  SELECT 3, '우리은행', '1002-333-000003', 3000000 FROM dual UNION ALL
  SELECT 4, '하나은행', '352-444-000004', 3200000 FROM dual UNION ALL
  SELECT 5, '카카오뱅크', '3333-05-000005', 2800000 FROM dual UNION ALL
  SELECT 6, '토스뱅크', '1000-06-000006', 2700000 FROM dual UNION ALL
  SELECT 7, '국민은행', '110-777-000007', 2500000 FROM dual UNION ALL
  SELECT 8, '신한은행', '110-888-000008', 2400000 FROM dual UNION ALL
  SELECT 9, '우리은행', '1002-999-000009', 2300000 FROM dual
) s
ON (t.emp_id = s.emp_id)
WHEN MATCHED THEN
  UPDATE SET
    salary_bank = s.bank,
    salary_account = s.acct,
    updated_at = SYSTIMESTAMP
WHEN NOT MATCHED THEN
  INSERT (
    emp_id, national_pension_yn, health_insurance_yn, health_discount_rate,
    longterm_care_yn, longterm_care_discount_rate, employment_insurance_yn,
    income_tax_type, youth_tax_reduction_yn, youth_tax_reduction_rate,
    durunuri_type, durunuri_split_yn, durunuri_pension_rate, durunuri_employment_rate,
    base_salary, salary_bank, salary_account
  )
  VALUES (
    s.emp_id, 'Y', 'Y', 0,
    'Y', 0, 'Y',
    'WORKER', 'N', 0,
    'NONE', 'N', 0, 0,
    s.base_salary, s.bank, s.acct
  );

-- ------------------------------------------------------------
-- 4. 기본 이체양식
-- ------------------------------------------------------------
DELETE FROM transfer_form_item
 WHERE form_id IN (SELECT form_id FROM transfer_form WHERE form_name = '기본 이체양식');

DELETE FROM transfer_form WHERE form_name = '기본 이체양식';

INSERT INTO transfer_form (form_id, form_name)
VALUES (seq_transfer_form.NEXTVAL, '기본 이체양식');

INSERT INTO transfer_form_item (item_id, form_id, item_code, display_order)
SELECT seq_transfer_form_item.NEXTVAL, f.form_id, v.item_code, v.display_order
  FROM transfer_form f
  CROSS JOIN (
    SELECT 'EMP_TYPE' AS item_code, 1 AS display_order FROM dual UNION ALL
    SELECT 'EMP_ID', 2 FROM dual UNION ALL
    SELECT 'NAME_KOR', 3 FROM dual UNION ALL
    SELECT 'DEPT', 4 FROM dual UNION ALL
    SELECT 'BANK', 5 FROM dual UNION ALL
    SELECT 'ACCOUNT', 6 FROM dual UNION ALL
    SELECT 'TOTAL_PAY', 7 FROM dual UNION ALL
    SELECT 'TOTAL_DEDUCT', 8 FROM dual UNION ALL
    SELECT 'NET_PAY', 9 FROM dual
  ) v
 WHERE f.form_name = '기본 이체양식';

COMMIT;

PROMPT === payroll_information ===
SELECT company_id, salary_bank, salary_account, account_holder FROM payroll_information;

PROMPT === employee_information_salary (1-9) ===
SELECT emp_id, salary_bank, salary_account FROM employee_information_salary WHERE emp_id BETWEEN 1 AND 9 ORDER BY emp_id;

PROMPT === transfer_form ===
SELECT form_id, form_name FROM transfer_form ORDER BY form_id;

PROMPT === transfer_form_item ===
SELECT i.item_id, i.form_id, i.item_code, i.display_order
  FROM transfer_form_item i
  JOIN transfer_form f ON f.form_id = i.form_id
 WHERE f.form_name = '기본 이체양식'
 ORDER BY i.display_order;

EXIT;
