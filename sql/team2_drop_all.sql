-- ============================================================
-- team2 스키마 전체 삭제 (Oracle 11g)
-- team2_post.sql 실행 전 DB 초기화용
--
-- 대상: team2.sql / team2_post.sql에 정의된 테이블·시퀀스
-- 실행 계정: 해당 스키마 OWNER
-- 주의: 모든 데이터가 삭제됩니다. 되돌릴 수 없습니다.
-- ============================================================

-- ------------------------------------------------------------
-- 1. TABLE (자식 → 부모 순서)
-- ------------------------------------------------------------
DROP TABLE tax_deferred_account CASCADE CONSTRAINTS;
DROP TABLE other_taxable_income CASCADE CONSTRAINTS;
DROP TABLE transfer_history CASCADE CONSTRAINTS;
DROP TABLE salary_detail CASCADE CONSTRAINTS;
DROP TABLE salary CASCADE CONSTRAINTS;
DROP TABLE salary_date CASCADE CONSTRAINTS;
DROP TABLE salary_item CASCADE CONSTRAINTS;
DROP TABLE severance_pay CASCADE CONSTRAINTS;
DROP TABLE attendance CASCADE CONSTRAINTS;
DROP TABLE education_training CASCADE CONSTRAINTS;
DROP TABLE license CASCADE CONSTRAINTS;
DROP TABLE dependent CASCADE CONSTRAINTS;
DROP TABLE daily_work CASCADE CONSTRAINTS;
DROP TABLE national_pension_information CASCADE CONSTRAINTS;
DROP TABLE recommender CASCADE CONSTRAINTS;
DROP TABLE military CASCADE CONSTRAINTS;
DROP TABLE personal_history CASCADE CONSTRAINTS;
DROP TABLE retirement CASCADE CONSTRAINTS;
DROP TABLE reward_punishment CASCADE CONSTRAINTS;
DROP TABLE appointment CASCADE CONSTRAINTS;
DROP TABLE language_skill CASCADE CONSTRAINTS;
DROP TABLE education CASCADE CONSTRAINTS;
DROP TABLE employee_information_salary CASCADE CONSTRAINTS;
DROP TABLE guarantee_insurance CASCADE CONSTRAINTS;
DROP TABLE guarantor CASCADE CONSTRAINTS;
DROP TABLE transfer_form_item CASCADE CONSTRAINTS;
DROP TABLE manager_information CASCADE CONSTRAINTS;
DROP TABLE company_stamp CASCADE CONSTRAINTS;
DROP TABLE company_logo CASCADE CONSTRAINTS;
DROP TABLE payroll_information CASCADE CONSTRAINTS;
DROP TABLE employee CASCADE CONSTRAINTS;
DROP TABLE attendance_type CASCADE CONSTRAINTS;
DROP TABLE vacation_type CASCADE CONSTRAINTS;
DROP TABLE transfer_form CASCADE CONSTRAINTS;
DROP TABLE department CASCADE CONSTRAINTS;
DROP TABLE position CASCADE CONSTRAINTS;
DROP TABLE company_information CASCADE CONSTRAINTS;

-- ------------------------------------------------------------
-- 2. SEQUENCE
-- ------------------------------------------------------------
DROP SEQUENCE seq_transfer_form_item;
DROP SEQUENCE seq_education_training;
DROP SEQUENCE seq_employee;
DROP SEQUENCE seq_transfer_history;
DROP SEQUENCE seq_daily_work;
DROP SEQUENCE seq_company_information;
DROP SEQUENCE seq_license;
DROP SEQUENCE seq_attendance;
DROP SEQUENCE seq_dependent;
DROP SEQUENCE seq_salary_date;
DROP SEQUENCE seq_salary;
DROP SEQUENCE seq_vacation_type;
DROP SEQUENCE seq_transfer_form;
DROP SEQUENCE seq_personal_history;
DROP SEQUENCE seq_attendance_type;
DROP SEQUENCE seq_department;
DROP SEQUENCE seq_salary_item;
DROP SEQUENCE seq_reward_punishment;
DROP SEQUENCE seq_appointment;
DROP SEQUENCE seq_language_skill;
DROP SEQUENCE seq_education;
DROP SEQUENCE seq_position;
DROP SEQUENCE seq_salary_detail;
DROP SEQUENCE seq_severance_pay;
DROP SEQUENCE seq_other_taxable_income;
DROP SEQUENCE seq_tax_deferred_account;

COMMIT;
