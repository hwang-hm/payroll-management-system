-- ============================================================
-- 2026년 8월 급여입력/관리 테스트용 통합 더미 데이터
-- ============================================================
-- 포함 테이블: department, position, attendance_type, employee,
--              salary_item, salary_date, salary, salary_detail
--
-- 선행 조건: team2.sql 스키마 및 시퀀스 생성 완료
-- 재실행: 아래 DELETE 구문이 동일 PK 범위를 먼저 정리합니다.
-- ============================================================

-- ------------------------------------------------------------
-- 0. 기존 더미 데이터 정리 (FK 역순)
-- ------------------------------------------------------------
DELETE FROM salary_detail
 WHERE salary_id BETWEEN 20260801 AND 20260899;

DELETE FROM salary
 WHERE salary_date_id = 202608;

DELETE FROM salary_date
 WHERE salary_date_id = 202608;

DELETE FROM salary_item
 WHERE salary_item_id BETWEEN 1 AND 17;

DELETE FROM employee
 WHERE emp_id BETWEEN 1 AND 10;

DELETE FROM position
 WHERE position_id BETWEEN 1 AND 6;

DELETE FROM department
 WHERE dept_id BETWEEN 1 AND 6;

DELETE FROM attendance_type
 WHERE attendance_type_id IN (1, 2, 3);

COMMIT;

-- ------------------------------------------------------------
-- 1. 부서
-- ------------------------------------------------------------
INSERT INTO department (dept_id, dept_name, sort_order, use_yn) VALUES (1, '대표실',     1, 'Y');
INSERT INTO department (dept_id, dept_name, sort_order, use_yn) VALUES (2, '콘텐츠팀',   2, 'Y');
INSERT INTO department (dept_id, dept_name, sort_order, use_yn) VALUES (3, '개발팀',     3, 'Y');
INSERT INTO department (dept_id, dept_name, sort_order, use_yn) VALUES (4, '인사팀',     4, 'Y');
INSERT INTO department (dept_id, dept_name, sort_order, use_yn) VALUES (5, '경영지원팀', 5, 'Y');
INSERT INTO department (dept_id, dept_name, sort_order, use_yn) VALUES (6, '마케팅팀',   6, 'Y');

-- ------------------------------------------------------------
-- 2. 직책
-- ------------------------------------------------------------
INSERT INTO position (position_id, position_name, sort_order, use_yn) VALUES (1, '대표', 1, 'Y');
INSERT INTO position (position_id, position_name, sort_order, use_yn) VALUES (2, '이사', 2, 'Y');
INSERT INTO position (position_id, position_name, sort_order, use_yn) VALUES (3, '팀장', 3, 'Y');
INSERT INTO position (position_id, position_name, sort_order, use_yn) VALUES (4, '과장', 4, 'Y');
INSERT INTO position (position_id, position_name, sort_order, use_yn) VALUES (5, '대리', 5, 'Y');
INSERT INTO position (position_id, position_name, sort_order, use_yn) VALUES (6, '사원', 6, 'Y');

-- ------------------------------------------------------------
-- 3. 근태유형 (salary_item FK / 당직·휴일수당 연결용)
-- ------------------------------------------------------------
INSERT INTO attendance_type (attendance_type_id, attendance_type, unit, group_name, use_yn, vacation_type_id)
VALUES (1, '일반',     'DAY',  '기본', 'Y', NULL);

INSERT INTO attendance_type (attendance_type_id, attendance_type, unit, group_name, use_yn, vacation_type_id)
VALUES (2, '연장근무', 'HOUR', '근무', 'Y', NULL);

INSERT INTO attendance_type (attendance_type_id, attendance_type, unit, group_name, use_yn, vacation_type_id)
VALUES (3, '휴일근무', 'HOUR', '근무', 'Y', NULL);

-- ------------------------------------------------------------
-- 4. 사원
-- ------------------------------------------------------------
INSERT INTO employee (emp_id, name_kor, hire_date, dept_id, employment_type, position_id, mobile_num, email, status)
VALUES (1, '김태희', DATE '2018-03-02', 1, '정규직', 1, '010-1111-0001', 'kimth@hexazon.co.kr', 'Y');

INSERT INTO employee (emp_id, name_kor, hire_date, dept_id, employment_type, position_id, mobile_num, email, status)
VALUES (2, '박민수', DATE '2019-07-15', 2, '정규직', 3, '010-1111-0002', 'parkms@hexazon.co.kr', 'Y');

INSERT INTO employee (emp_id, name_kor, hire_date, dept_id, employment_type, position_id, mobile_num, email, status)
VALUES (3, '이수진', DATE '2020-01-10', 2, '정규직', 5, '010-1111-0003', 'leesj@hexazon.co.kr', 'Y');

INSERT INTO employee (emp_id, name_kor, hire_date, dept_id, employment_type, position_id, mobile_num, email, status)
VALUES (4, '이수잔', DATE '2021-05-20', 3, '정규직', 4, '010-1111-0004', 'leesz@hexazon.co.kr', 'Y');

INSERT INTO employee (emp_id, name_kor, hire_date, dept_id, employment_type, position_id, mobile_num, email, status)
VALUES (5, '최영호', DATE '2022-02-01', 3, '정규직', 6, '010-1111-0005', 'choiyh@hexazon.co.kr', 'Y');

INSERT INTO employee (emp_id, name_kor, hire_date, dept_id, employment_type, position_id, mobile_num, email, status)
VALUES (6, '정하늘', DATE '2023-08-14', 4, '정규직', 6, '010-1111-0006', 'junghn@hexazon.co.kr', 'Y');

INSERT INTO employee (emp_id, name_kor, hire_date, dept_id, employment_type, position_id, mobile_num, email, status)
VALUES (7, '한지민', DATE '2024-01-03', 5, '계약직', 6, '010-1111-0007', 'hanjm@hexazon.co.kr', 'Y');

INSERT INTO employee (emp_id, name_kor, hire_date, dept_id, employment_type, position_id, mobile_num, email, status)
VALUES (8, '오세훈', DATE '2024-06-17', 6, '계약직', 5, '010-1111-0008', 'ohsh@hexazon.co.kr', 'Y');

INSERT INTO employee (emp_id, name_kor, hire_date, dept_id, employment_type, position_id, mobile_num, email, status)
VALUES (9, '윤서연', DATE '2025-03-11', 2, '정규직', 6, '010-1111-0009', 'yunsy@hexazon.co.kr', 'Y');

INSERT INTO employee (emp_id, name_kor, hire_date, dept_id, employment_type, position_id, mobile_num, email, status)
VALUES (10, '강동원', DATE '2017-11-30', 1, '정규직', 2, '010-1111-0010', 'kangdw@hexazon.co.kr', 'N');

-- ------------------------------------------------------------
-- 5. 급여항목 마스터 (PAYZON 급여항목 설정 기준)
-- ------------------------------------------------------------
INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (1, 1, 'PAY', '기본급', 'Y', NULL, 0, NULL, 'NONE', 0, NULL, 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (2, 1, 'PAY', '식비', 'N', '비과세 식사대', 200000, NULL, 'NONE', 200000, NULL, 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (3, 1, 'PAY', '보육수당', 'Y', NULL, 0, NULL, 'NONE', 0, NULL, 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (4, 1, 'PAY', '직책수당', 'Y', NULL, 0, NULL, 'NONE', 0, NULL, 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (5, 1, 'PAY', '차량유지비', 'N', '비과세한도 200000원 (자가운전보조금)', 200000, NULL, 'NONE', 0, NULL, 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (6, 1, 'PAY', '근속수당', 'Y', NULL, 0, NULL, 'NONE', 0, NULL, 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (7, 2, 'PAY', '당직수당', 'Y', NULL, 0, NULL, 'NONE', 0, NULL, 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (8, 1, 'PAY', '상여금', 'Y', NULL, 0, NULL, 'NONE', 0, NULL, 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (9, 3, 'PAY', '휴일수당', 'Y', NULL, 0, NULL, 'NONE', 0, NULL, 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (10, 1, 'DEDUCT', '국민연금', 'Y', NULL, 0, NULL, 'NONE', 0, '기본항목', 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (11, 1, 'DEDUCT', '건강보험', 'Y', NULL, 0, NULL, '1원단위', 0, '기본항목', 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (12, 1, 'DEDUCT', '장기요양보험', 'Y', NULL, 0, NULL, '1원단위', 0, '기본항목', 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (13, 1, 'DEDUCT', '고용보험', 'Y', NULL, 0, NULL, '1원단위', 0, '기본항목', 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (14, 1, 'DEDUCT', '소득세', 'Y', NULL, 0, NULL, '10원', 0, '기본항목', 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (15, 1, 'DEDUCT', '지방소득세', 'Y', NULL, 0, NULL, '10원', 0, '기본항목', 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (16, 1, 'DEDUCT', '상조회비', 'Y', NULL, 0, NULL, 'NONE', 0, NULL, 'Y');

INSERT INTO salary_item (salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn)
VALUES (17, 1, 'DEDUCT', '연말정산', 'Y', NULL, 0, NULL, 'NONE', 0, '기본항목', 'N');

-- ------------------------------------------------------------
-- 6. 귀속연월 (2026-08, 급여-01)
-- ------------------------------------------------------------
INSERT INTO salary_date (salary_date_id, pay_year, pay_month, pay_seq, start_date, end_date, pay_date)
VALUES (202608, 2026, 8, 1, DATE '2026-08-01', DATE '2026-08-31', DATE '2026-09-05');

-- ------------------------------------------------------------
-- 7. 급여 헤더 (재직 사원 6명, 퇴직자 제외)
--    total_pay / total_deduct / net_pay = 상세 합계와 일치
-- ------------------------------------------------------------
INSERT INTO salary (salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay) VALUES (20260801, 202608, 1, 6700000, 1033470, 5666530); -- 김태희
INSERT INTO salary (salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay) VALUES (20260802, 202608, 2, 4530000,  594180, 3935820); -- 박민수
INSERT INTO salary (salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay) VALUES (20260803, 202608, 3, 3700000,  455890, 3244110); -- 이수진
INSERT INTO salary (salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay) VALUES (20260804, 202608, 4, 4020000,  515620, 3504380); -- 이수잔
INSERT INTO salary (salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay) VALUES (20260805, 202608, 5, 3300000,  399510, 2900490); -- 최영호
INSERT INTO salary (salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay) VALUES (20260806, 202608, 6, 3180000,  377160, 2802840); -- 정하늘

-- ------------------------------------------------------------
-- 8. 급여 상세 (PAYZON 항목 구성 기준, 사원별 차등)
-- ------------------------------------------------------------

-- [김태희] 대표 - 기본급·직책·차량·근속·8월 상여
INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, memo, amount)
SELECT seq_salary_detail.NEXTVAL, v.salary_id, si.salary_item_id, si.attendance_type_id, si.item_type, si.item_name, si.tax_yn, si.tax_free_name, NVL(si.tax_free_limit, 0), si.calc_method, si.round_unit, NVL(si.default_amount, 0), si.remark, v.amount
FROM (
    SELECT 20260801 AS salary_id, '기본급'       AS item_name, 5000000 AS amount FROM dual UNION ALL
    SELECT 20260801, '식비',         200000 FROM dual UNION ALL
    SELECT 20260801, '직책수당',     500000 FROM dual UNION ALL
    SELECT 20260801, '차량유지비',   200000 FROM dual UNION ALL
    SELECT 20260801, '근속수당',     300000 FROM dual UNION ALL
    SELECT 20260801, '상여금',       500000 FROM dual UNION ALL
    SELECT 20260801, '국민연금',     270000 FROM dual UNION ALL
    SELECT 20260801, '건강보험',     210600 FROM dual UNION ALL
    SELECT 20260801, '장기요양보험',  27270 FROM dual UNION ALL
    SELECT 20260801, '고용보험',      53600 FROM dual UNION ALL
    SELECT 20260801, '소득세',       420000 FROM dual UNION ALL
    SELECT 20260801, '지방소득세',    42000 FROM dual UNION ALL
    SELECT 20260801, '상조회비',      10000 FROM dual
) v
JOIN salary_item si ON si.item_name = v.item_name;

-- [박민수] 팀장 - 당직(연장근무)·휴일수당 포함
INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, memo, amount)
SELECT seq_salary_detail.NEXTVAL, v.salary_id, si.salary_item_id, si.attendance_type_id, si.item_type, si.item_name, si.tax_yn, si.tax_free_name, NVL(si.tax_free_limit, 0), si.calc_method, si.round_unit, NVL(si.default_amount, 0), si.remark, v.amount
FROM (
    SELECT 20260802 AS salary_id, '기본급'       AS item_name, 3800000 AS amount FROM dual UNION ALL
    SELECT 20260802, '식비',         200000 FROM dual UNION ALL
    SELECT 20260802, '직책수당',     300000 FROM dual UNION ALL
    SELECT 20260802, '당직수당',     150000 FROM dual UNION ALL
    SELECT 20260802, '휴일수당',      80000 FROM dual UNION ALL
    SELECT 20260802, '국민연금',     183150 FROM dual UNION ALL
    SELECT 20260802, '건강보험',     142800 FROM dual UNION ALL
    SELECT 20260802, '장기요양보험',  18490 FROM dual UNION ALL
    SELECT 20260802, '고용보험',      36240 FROM dual UNION ALL
    SELECT 20260802, '소득세',       185000 FROM dual UNION ALL
    SELECT 20260802, '지방소득세',    18500 FROM dual UNION ALL
    SELECT 20260802, '상조회비',      10000 FROM dual
) v
JOIN salary_item si ON si.item_name = v.item_name;

-- [이수진] 대리 - 보육수당
INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, memo, amount)
SELECT seq_salary_detail.NEXTVAL, v.salary_id, si.salary_item_id, si.attendance_type_id, si.item_type, si.item_name, si.tax_yn, si.tax_free_name, NVL(si.tax_free_limit, 0), si.calc_method, si.round_unit, NVL(si.default_amount, 0), si.remark, v.amount
FROM (
    SELECT 20260803 AS salary_id, '기본급'       AS item_name, 3400000 AS amount FROM dual UNION ALL
    SELECT 20260803, '식비',         200000 FROM dual UNION ALL
    SELECT 20260803, '보육수당',     100000 FROM dual UNION ALL
    SELECT 20260803, '국민연금',     153000 FROM dual UNION ALL
    SELECT 20260803, '건강보험',     119340 FROM dual UNION ALL
    SELECT 20260803, '장기요양보험',  15450 FROM dual UNION ALL
    SELECT 20260803, '고용보험',      29600 FROM dual UNION ALL
    SELECT 20260803, '소득세',       125000 FROM dual UNION ALL
    SELECT 20260803, '지방소득세',    12500 FROM dual UNION ALL
    SELECT 20260803, '상조회비',      10000 FROM dual
) v
JOIN salary_item si ON si.item_name = v.item_name;

-- [이수잔] 과장 - 휴일근무 수당
INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, memo, amount)
SELECT seq_salary_detail.NEXTVAL, v.salary_id, si.salary_item_id, si.attendance_type_id, si.item_type, si.item_name, si.tax_yn, si.tax_free_name, NVL(si.tax_free_limit, 0), si.calc_method, si.round_unit, NVL(si.default_amount, 0), si.remark, v.amount
FROM (
    SELECT 20260804 AS salary_id, '기본급'       AS item_name, 3600000 AS amount FROM dual UNION ALL
    SELECT 20260804, '식비',         200000 FROM dual UNION ALL
    SELECT 20260804, '직책수당',     100000 FROM dual UNION ALL
    SELECT 20260804, '휴일수당',     120000 FROM dual UNION ALL
    SELECT 20260804, '국민연금',     166500 FROM dual UNION ALL
    SELECT 20260804, '건강보험',     129780 FROM dual UNION ALL
    SELECT 20260804, '장기요양보험',  16780 FROM dual UNION ALL
    SELECT 20260804, '고용보험',      33060 FROM dual UNION ALL
    SELECT 20260804, '소득세',       145000 FROM dual UNION ALL
    SELECT 20260804, '지방소득세',    14500 FROM dual UNION ALL
    SELECT 20260804, '상조회비',      10000 FROM dual
) v
JOIN salary_item si ON si.item_name = v.item_name;

-- [최영호] 사원 - 근속수당
INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, memo, amount)
SELECT seq_salary_detail.NEXTVAL, v.salary_id, si.salary_item_id, si.attendance_type_id, si.item_type, si.item_name, si.tax_yn, si.tax_free_name, NVL(si.tax_free_limit, 0), si.calc_method, si.round_unit, NVL(si.default_amount, 0), si.remark, v.amount
FROM (
    SELECT 20260805 AS salary_id, '기본급'       AS item_name, 3000000 AS amount FROM dual UNION ALL
    SELECT 20260805, '식비',         200000 FROM dual UNION ALL
    SELECT 20260805, '근속수당',     100000 FROM dual UNION ALL
    SELECT 20260805, '국민연금',     135000 FROM dual UNION ALL
    SELECT 20260805, '건강보험',     105300 FROM dual UNION ALL
    SELECT 20260805, '장기요양보험',  13610 FROM dual UNION ALL
    SELECT 20260805, '고용보험',      26800 FROM dual UNION ALL
    SELECT 20260805, '소득세',        98000 FROM dual UNION ALL
    SELECT 20260805, '지방소득세',     9800 FROM dual UNION ALL
    SELECT 20260805, '상조회비',      10000 FROM dual
) v
JOIN salary_item si ON si.item_name = v.item_name;

-- [정하늘] 사원 - 당직(연장근무)
INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, default_amount, memo, amount)
SELECT seq_salary_detail.NEXTVAL, v.salary_id, si.salary_item_id, si.attendance_type_id, si.item_type, si.item_name, si.tax_yn, si.tax_free_name, NVL(si.tax_free_limit, 0), si.calc_method, si.round_unit, NVL(si.default_amount, 0), si.remark, v.amount
FROM (
    SELECT 20260806 AS salary_id, '기본급'       AS item_name, 2900000 AS amount FROM dual UNION ALL
    SELECT 20260806, '식비',         200000 FROM dual UNION ALL
    SELECT 20260806, '당직수당',      80000 FROM dual UNION ALL
    SELECT 20260806, '국민연금',     130050 FROM dual UNION ALL
    SELECT 20260806, '건강보험',     101430 FROM dual UNION ALL
    SELECT 20260806, '장기요양보험',  13100 FROM dual UNION ALL
    SELECT 20260806, '고용보험',      25780 FROM dual UNION ALL
    SELECT 20260806, '소득세',        88000 FROM dual UNION ALL
    SELECT 20260806, '지방소득세',     8800 FROM dual UNION ALL
    SELECT 20260806, '상조회비',      10000 FROM dual
) v
JOIN salary_item si ON si.item_name = v.item_name;

COMMIT;

-- ------------------------------------------------------------
-- 확인용
-- ------------------------------------------------------------
-- SELECT * FROM salary_date WHERE pay_year = 2026 AND pay_month = 8 AND pay_seq = 1;
-- SELECT s.salary_id, e.name_kor, d.dept_name, s.total_pay, s.net_pay
--   FROM salary s
--   JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id
--   JOIN employee e ON s.emp_id = e.emp_id
--   LEFT JOIN department d ON e.dept_id = d.dept_id
--  WHERE sd.pay_year = 2026 AND sd.pay_month = 8 AND sd.pay_seq = 1;
