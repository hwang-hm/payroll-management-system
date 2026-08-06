-- ------------------------------------------------------------
-- 1. SEQUENCE (NUMBER형 PK만 생성)
--    emp_id / company_id 를 PK로 쓰는 1:1 확장 테이블은
--    부모 시퀀스 값을 그대로 사용하므로 별도 시퀀스 없음
-- ------------------------------------------------------------
CREATE SEQUENCE seq_transfer_form_item START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_education_training START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_employee          START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_transfer_history  START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_daily_work        START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_company_information START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_license           START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_attendance        START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_dependent         START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_salary_date       START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_salary            START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_vacation_type     START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_transfer_form     START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_personal_history  START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_attendance_type   START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_department        START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_salary_item       START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_reward_punishment START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_appointment       START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_language_skill    START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_education         START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_position          START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_salary_detail     START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_severance_pay     START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_other_taxable_income START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_tax_deferred_account START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- ------------------------------------------------------------
-- 2. TABLE
-- ------------------------------------------------------------

-- 부서
CREATE TABLE department (
    dept_id     NUMBER          NOT NULL,
    dept_name   VARCHAR2(50)    NOT NULL,
    sort_order  NUMBER          NOT NULL,
    use_yn      VARCHAR2(1)     DEFAULT 'Y' NOT NULL,
    created_at  TIMESTAMP       DEFAULT SYSTIMESTAMP NULL,
    updated_at  TIMESTAMP       NULL,
    CONSTRAINT pk_department PRIMARY KEY (dept_id),
    CONSTRAINT ck_department_use_yn CHECK (use_yn IN ('Y', 'N'))
);

COMMENT ON COLUMN department.dept_id    IS 'PK, 부서 식별번호';
COMMENT ON COLUMN department.sort_order IS '드래그로 변경한 표시 순서';
COMMENT ON COLUMN department.use_yn     IS 'Y: 표시, N: 미표시';

-- 직책
CREATE TABLE position (
    position_id   NUMBER        NOT NULL,
    position_name VARCHAR2(30)  NOT NULL,
    sort_order    NUMBER        NOT NULL,
    use_yn        VARCHAR2(1)   DEFAULT 'Y' NOT NULL,
    created_at    TIMESTAMP     DEFAULT SYSTIMESTAMP NULL,
    updated_at    TIMESTAMP     NULL,
    CONSTRAINT pk_position PRIMARY KEY (position_id),
    CONSTRAINT ck_position_use_yn CHECK (use_yn IN ('Y', 'N'))
);

COMMENT ON COLUMN position.position_id IS 'PK, 직책 식별번호';
COMMENT ON COLUMN position.use_yn      IS 'Y: 표시, N: 미표시';

-- 회사 정보
CREATE TABLE company_information (
    company_id          NUMBER          NOT NULL,
    company_name        VARCHAR2(100)   NULL,
    business_number     VARCHAR2(20)    NULL,
    corporate_number    VARCHAR2(20)    NULL,
    establishment_date  DATE            NULL,
    homepage            VARCHAR2(200)   NULL,
    zip_code            VARCHAR2(10)    NULL,
    address             VARCHAR2(300)   NULL,
    phone_number        VARCHAR2(30)    NULL,
    fax_number          VARCHAR2(30)    NULL,
    business_type       VARCHAR2(100)   NULL,
    business_item       VARCHAR2(100)   NULL,
    created_at          TIMESTAMP       DEFAULT SYSTIMESTAMP NULL,
    updated_at          TIMESTAMP       NULL,
    CONSTRAINT pk_company_information PRIMARY KEY (company_id)
);

-- 사원
CREATE TABLE employee (
    emp_id          NUMBER          NOT NULL,
    name_kor        VARCHAR2(50)    NOT NULL,
    hire_date       DATE            NOT NULL,
    dept_id         NUMBER          NULL,
    nationality     VARCHAR2(3)     NULL,
    employment_type VARCHAR2(10)    NOT NULL,
    name_eng        VARCHAR2(50)    NULL,
    retire_date     DATE            NULL,
    position_id     NUMBER          NULL,
    resident_no     VARCHAR2(15)    NULL,
    zip_code        VARCHAR2(10)    NULL,
    address         VARCHAR2(200)   NULL,
    phone_num       VARCHAR2(20)    NULL,
    mobile_num      VARCHAR2(20)    NULL,
    email           VARCHAR2(100)   NULL,
    sns             VARCHAR2(50)    NULL,
    remarks         VARCHAR2(500)   NULL,
    status          VARCHAR2(1)     DEFAULT 'Y' NULL,
    photo_path      VARCHAR2(300)   NULL,
    created_at      TIMESTAMP       DEFAULT SYSTIMESTAMP NULL,
    updated_at      TIMESTAMP       NULL,
    CONSTRAINT pk_employee PRIMARY KEY (emp_id),
    CONSTRAINT ck_employee_status CHECK (status IN ('Y', 'N'))
);

COMMENT ON COLUMN employee.dept_id     IS '부서 테이블 FK, NULL 허용';
COMMENT ON COLUMN employee.nationality IS '내국인 / 외국인';
COMMENT ON COLUMN employee.position_id IS '직책 테이블 FK, NULL 허용';
COMMENT ON COLUMN employee.status      IS 'Y : 재직 / N : 퇴직';

-- 이체 양식
CREATE TABLE transfer_form (
    form_id   NUMBER         NOT NULL,
    form_name VARCHAR2(100)  NOT NULL,
    CONSTRAINT pk_transfer_form PRIMARY KEY (form_id)
);

-- 이체 양식 항목
CREATE TABLE transfer_form_item (
    item_id       NUMBER        NOT NULL,
    form_id       NUMBER        NOT NULL,
    item_code     VARCHAR2(30)  NOT NULL,
    display_order NUMBER        NOT NULL,
    CONSTRAINT pk_transfer_form_item PRIMARY KEY (item_id)
);

COMMENT ON COLUMN transfer_form_item.display_order IS '이체 대기 테이블에 출력될 칼럼 순서';

-- 교육/훈련
CREATE TABLE education_training (
    training_id     NUMBER          NOT NULL,
    emp_id          NUMBER          NOT NULL,
    training_type   VARCHAR2(30)    NULL,
    training_name   VARCHAR2(100)   NULL,
    start_date      DATE            NULL,
    end_date        DATE            NULL,
    training_center VARCHAR2(100)   NULL,
    training_cost   NUMBER          NULL,
    refund_cost     NUMBER          NULL,
    created_at      TIMESTAMP       DEFAULT SYSTIMESTAMP NULL,
    updated_at      TIMESTAMP       NULL,
    CONSTRAINT pk_education_training PRIMARY KEY (training_id)
);

-- 귀속연월
CREATE TABLE salary_date (
    salary_date_id NUMBER         NOT NULL,
    pay_year       NUMBER(4)      NOT NULL,
    pay_month      NUMBER(2)      NOT NULL,
    pay_seq        NUMBER(2)      NOT NULL,
    start_date     DATE           NOT NULL,
    end_date       DATE           NOT NULL,
    pay_date       DATE           NOT NULL,
    CONSTRAINT pk_salary_date PRIMARY KEY (salary_date_id)
);

-- 급여
CREATE TABLE salary (
    salary_id      NUMBER         NOT NULL,
    salary_date_id NUMBER         NOT NULL,
    emp_id         NUMBER         NOT NULL,
    total_pay      NUMBER(19)     NOT NULL,
    total_deduct   NUMBER(19)     NOT NULL,
    net_pay        NUMBER(19)     NOT NULL,
    created_at     DATE           DEFAULT SYSDATE NOT NULL,
    updated_at     DATE           NULL,
    CONSTRAINT pk_salary PRIMARY KEY (salary_id)
);

COMMENT ON COLUMN salary.emp_id IS '사원테이블 FK';

-- 이체 이력
CREATE TABLE transfer_history (
    transfer_id      NUMBER         NOT NULL,
    salary_id        NUMBER         NOT NULL,
    transfer_date    DATE           NOT NULL,
    withdraw_bank    VARCHAR2(50)   NOT NULL,
    withdraw_account VARCHAR2(30)   NOT NULL,
    deposit_bank     VARCHAR2(50)   NOT NULL,
    deposit_account  VARCHAR2(30)   NOT NULL,
    depositor_name   VARCHAR2(50)   NOT NULL,
    transfer_amount  NUMBER(19)     NOT NULL,
    deposit_memo     VARCHAR2(100)  NULL,
    transfer_result  VARCHAR2(20)   NOT NULL,
    CONSTRAINT pk_transfer_history PRIMARY KEY (transfer_id)
);

-- 4대보험 정보 (1:1, emp_id = Employee.emp_id)
CREATE TABLE national_pension_information (
    emp_id               NUMBER         NOT NULL,
    national_no          VARCHAR2(11)   NULL,
    national_acq_date    DATE           NULL,
    national_loss_date   DATE           NULL,
    health_no            VARCHAR2(30)   NULL,
    health_acq_date      DATE           NULL,
    health_loss_date     DATE           NULL,
    employment_no        VARCHAR2(30)   NULL,
    employment_acq_date  DATE           NULL,
    employment_loss_date DATE           NULL,
    industrial_no        VARCHAR2(30)   NULL,
    industrial_acq_date  DATE           NULL,
    industrial_loss_date DATE           NULL,
    created_at           TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at           TIMESTAMP      NULL,
    CONSTRAINT pk_national_pension_info PRIMARY KEY (emp_id)
);

-- 일용직 근무
CREATE TABLE daily_work (
    daily_work_id    NUMBER         NOT NULL,
    work_date        DATE           NULL,
    project_name     VARCHAR2(50)   NULL,
    daily_wage       NUMBER(12,0)   NULL,
    payment_rate     NUMBER(5,2)    NULL,
    income_tax       NUMBER(12,0)   NULL,
    local_income_tax NUMBER(12,0)   NULL,
    net_pay          NUMBER(12,0)   NULL,
    emp_id           NUMBER         NOT NULL,
    CONSTRAINT pk_daily_work PRIMARY KEY (daily_work_id)
);

-- 자격증
CREATE TABLE license (
    license_id       NUMBER         NOT NULL,
    emp_id           NUMBER         NOT NULL,
    license_name     VARCHAR2(100)  NULL,
    acquisition_date DATE           NULL,
    issuer           VARCHAR2(100)  NULL,
    license_number   VARCHAR2(50)   NULL,
    remarks          VARCHAR2(200)  NULL,
    created_at       TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at       TIMESTAMP      NULL,
    CONSTRAINT pk_license PRIMARY KEY (license_id)
);

-- 휴가 유형
CREATE TABLE vacation_type (
    vacation_type_id NUMBER         NOT NULL,
    vacation_name    VARCHAR2(50)   NULL,
    apply_start_date DATE           NULL,
    apply_end_date   DATE           NULL,
    vacation_days    VARCHAR2(10)   NULL,
    use_yn           CHAR(1)        DEFAULT 'Y' NULL,
    CONSTRAINT pk_vacation_type PRIMARY KEY (vacation_type_id),
    CONSTRAINT ck_vacation_type_use_yn CHECK (use_yn IN ('Y', 'N'))
);

-- 근태 유형
CREATE TABLE attendance_type (
    attendance_type_id NUMBER         NOT NULL,
    attendance_type    VARCHAR2(50)   NULL,
    unit               VARCHAR2(10)   NULL,
    group_name         VARCHAR2(30)   NULL,
    use_yn             CHAR(1)        DEFAULT 'Y' NULL,
    vacation_type_id   NUMBER         NULL,
    CONSTRAINT pk_attendance_type PRIMARY KEY (attendance_type_id),
    CONSTRAINT ck_attendance_type_use_yn CHECK (use_yn IN ('Y', 'N'))
);

COMMENT ON COLUMN attendance_type.use_yn IS 'CHECK (use_yn IN (''Y'',''N''))';

-- 근태
CREATE TABLE attendance (
    attendance_id         NUMBER         NOT NULL,
    input_date            DATE           NULL,
    attendance_start_date DATE           NOT NULL,
    attendance_end_date   DATE           NULL,
    attendance_days       NUMBER(5,2)    NULL,
    allowance_amount      NUMBER(12,0)   NULL,
    remarks               VARCHAR2(500)  NULL,
    emp_id                NUMBER         NOT NULL,
    attendance_type_id    NUMBER         NOT NULL,
    CONSTRAINT pk_attendance PRIMARY KEY (attendance_id)
);

-- 부양가족
CREATE TABLE dependent (
    dependent_id         NUMBER         NOT NULL,
    emp_id               NUMBER         NOT NULL,
    relation             VARCHAR2(30)   NOT NULL,
    family_name          VARCHAR2(50)   NOT NULL,
    nationality          VARCHAR2(3)    NULL,
    resident_no          VARCHAR2(15)   NULL,
    disability_yn        VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    deduction_yn         VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    health_insurance_yn  VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    cohabitation_yn      VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    income_tax_yn        VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    child_age_yn         VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    created_at           TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at           TIMESTAMP      NULL,
    CONSTRAINT pk_dependent PRIMARY KEY (dependent_id),
    CONSTRAINT ck_dependent_disability_yn CHECK (disability_yn IN ('Y', 'N')),
    CONSTRAINT ck_dependent_deduction_yn CHECK (deduction_yn IN ('Y', 'N')),
    CONSTRAINT ck_dependent_health_yn CHECK (health_insurance_yn IN ('Y', 'N')),
    CONSTRAINT ck_dependent_cohabitation_yn CHECK (cohabitation_yn IN ('Y', 'N')),
    CONSTRAINT ck_dependent_income_tax_yn CHECK (income_tax_yn IN ('Y', 'N')),
    CONSTRAINT ck_dependent_child_age_yn CHECK (child_age_yn IN ('Y', 'N'))
);

COMMENT ON COLUMN dependent.nationality         IS '내국인/외국인';
COMMENT ON COLUMN dependent.disability_yn       IS 'Y/N';
COMMENT ON COLUMN dependent.deduction_yn         IS 'Y/N';
COMMENT ON COLUMN dependent.health_insurance_yn IS 'Y/N';
COMMENT ON COLUMN dependent.cohabitation_yn     IS 'Y/N';
COMMENT ON COLUMN dependent.income_tax_yn       IS 'Y/N';
COMMENT ON COLUMN dependent.child_age_yn        IS 'Y/N';

-- 회사 직인
CREATE TABLE company_stamp (
    company_id NUMBER         NOT NULL,
    stamp_path VARCHAR2(300)  NULL,
    created_at TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at TIMESTAMP      NULL,
    CONSTRAINT pk_company_stamp PRIMARY KEY (company_id)
);

-- 추천인
CREATE TABLE recommender (
    emp_id           NUMBER         NOT NULL,
    recommender_name VARCHAR2(30)   NULL,
    relation         VARCHAR2(30)   NULL,
    company_name     VARCHAR2(100)  NULL,
    position_name    VARCHAR2(50)   NULL,
    phone_number     VARCHAR2(20)   NULL,
    created_at       TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at       TIMESTAMP      NULL,
    CONSTRAINT pk_recommender PRIMARY KEY (emp_id)
);

-- 담당자 정보
CREATE TABLE manager_information (
    company_id     NUMBER         NOT NULL,
    manager_name   VARCHAR2(30)   NULL,
    dept_id        NUMBER         NOT NULL,
    position_id    NUMBER         NOT NULL,
    manager_phone  VARCHAR2(30)   NULL,
    manager_mobile VARCHAR2(30)   NULL,
    manager_email  VARCHAR2(100)  NULL,
    created_at     TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at     TIMESTAMP      NULL,
    CONSTRAINT pk_manager_information PRIMARY KEY (company_id)
);

COMMENT ON COLUMN manager_information.dept_id     IS '부서 식별번호';
COMMENT ON COLUMN manager_information.position_id IS '직책 식별번호';

-- 병역
CREATE TABLE military (
    emp_id           NUMBER         NOT NULL,
    military_type    VARCHAR2(10)   NULL,
    military_branch  VARCHAR2(20)   NULL,
    start_date       DATE           NULL,
    end_date         DATE           NULL,
    final_rank       VARCHAR2(20)   NULL,
    specialty        VARCHAR2(50)   NULL,
    exemption_reason VARCHAR2(200)  NULL,
    created_at       TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at       TIMESTAMP      NULL,
    CONSTRAINT pk_military PRIMARY KEY (emp_id)
);

-- 경력
CREATE TABLE personal_history (
    career_id     NUMBER         NOT NULL,
    emp_id        NUMBER         NOT NULL,
    company_name  VARCHAR2(100)  NULL,
    hire_date     DATE           NULL,
    retire_date   DATE           NULL,
    last_position VARCHAR2(50)   NULL,
    position      VARCHAR2(100)  NULL,
    retire_reason VARCHAR2(200)  NULL,
    work_year     NUMBER(2)      NULL,
    work_month    NUMBER(2)      NULL,
    created_at    TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at    TIMESTAMP      NULL,
    CONSTRAINT pk_personal_history PRIMARY KEY (career_id)
);

-- 급여 항목
CREATE TABLE salary_item (
    salary_item_id      NUMBER         NOT NULL,
    attendance_type_id  NUMBER         NULL,
    item_type           VARCHAR2(10)   NOT NULL,
    item_name           VARCHAR2(100)  NOT NULL,
    tax_yn              CHAR(1)        NOT NULL,
    tax_free_name       VARCHAR2(100)  NULL,
    tax_free_limit      NUMBER         DEFAULT 0 NULL,
    calc_method         VARCHAR2(100)  NULL,
    round_unit          VARCHAR2(10)   NOT NULL,
    default_amount      NUMBER         DEFAULT 0 NULL,
    remark              VARCHAR2(300)  NULL,
    use_yn              CHAR(1)        NOT NULL,
    created_at          DATE           DEFAULT SYSDATE NOT NULL,
    updated_at          DATE           NULL,
    CONSTRAINT pk_salary_item PRIMARY KEY (salary_item_id),
    CONSTRAINT ck_salary_item_type CHECK (item_type IN ('PAY', 'DEDUCT')),
    CONSTRAINT ck_salary_item_tax_yn CHECK (tax_yn IN ('Y', 'N')),
    CONSTRAINT ck_salary_item_use_yn CHECK (use_yn IN ('Y', 'N'))
);

COMMENT ON COLUMN salary_item.attendance_type_id IS '근태항목 jk';
COMMENT ON COLUMN salary_item.item_type           IS 'PAY(지급), DEDUCT(공제)';
COMMENT ON COLUMN salary_item.item_name           IS '항목명(기본급, 식대 등)';
COMMENT ON COLUMN salary_item.tax_yn              IS 'Y(전체과세)/N(비과세)';
COMMENT ON COLUMN salary_item.round_unit          IS 'NONE 등 반올림 단위';
COMMENT ON COLUMN salary_item.default_amount      IS '일괄지급일 때만 사용';
COMMENT ON COLUMN salary_item.remark              IS '공제항목용';
COMMENT ON COLUMN salary_item.use_yn              IS 'Y(사용)/N(미사용)';

-- 급여 상세
CREATE TABLE salary_detail (
    detail_id          NUMBER         NOT NULL,
    salary_id          NUMBER         NOT NULL,
    salary_item_id     NUMBER         NULL,
    attendance_type_id NUMBER         NULL,
    item_type          VARCHAR2(10)   NOT NULL,
    item_name          VARCHAR2(100)  NOT NULL,
    tax_yn             CHAR(1)        NOT NULL,
    tax_free_name      VARCHAR2(100)  NULL,
    tax_free_limit     NUMBER         DEFAULT 0 NULL,
    calc_method        VARCHAR2(100)  NULL,
    round_unit         VARCHAR2(10)   NOT NULL,
    default_amount     NUMBER         DEFAULT 0 NULL,
    memo               VARCHAR2(300)  NULL,
    amount             NUMBER(19)     DEFAULT 0 NULL,
    CONSTRAINT pk_salary_detail PRIMARY KEY (detail_id),
    CONSTRAINT ck_salary_detail_type CHECK (item_type IN ('PAY', 'DEDUCT')),
    CONSTRAINT ck_salary_detail_tax_yn CHECK (tax_yn IN ('Y', 'N'))
);

COMMENT ON COLUMN salary_detail.attendance_type_id IS '근태항목 jk';
COMMENT ON COLUMN salary_detail.item_type          IS 'PAY(지급), DEDUCT(공제)';
COMMENT ON COLUMN salary_detail.item_name          IS '항목명(기본급, 식대 등)';
COMMENT ON COLUMN salary_detail.tax_yn             IS 'Y(전체과세)/N(비과세)';
COMMENT ON COLUMN salary_detail.tax_free_limit     IS 'DEFAULT 0';
COMMENT ON COLUMN salary_detail.default_amount     IS '일괄지급일 때만 나타난다 DEFAULT 0';
COMMENT ON COLUMN salary_detail.memo               IS '공제항목용';
COMMENT ON COLUMN salary_detail.amount             IS 'DEFAULT 0';

-- 급여 지급 정보 (회사 1:1)
CREATE TABLE payroll_information (
    company_id              NUMBER         NOT NULL,
    salary_start_month_type VARCHAR2(10)   NULL,
    salary_start_day        VARCHAR2(10)   NULL,
    salary_end_month_type   VARCHAR2(10)   NULL,
    salary_end_day_type     VARCHAR2(10)   NULL,
    payment_month_type      VARCHAR2(10)   NULL,
    payment_day             VARCHAR2(10)   NULL,
    salary_bank             VARCHAR2(50)   NULL,
    salary_account          VARCHAR2(50)   NULL,
    account_holder          VARCHAR2(100)  NULL,
    created_at              TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at              TIMESTAMP      NULL,
    CONSTRAINT pk_payroll_information PRIMARY KEY (company_id)
);

-- 퇴직
CREATE TABLE retirement (
    emp_id                  NUMBER         NOT NULL,
    retirement_type         VARCHAR2(20)   NULL,
    retirement_date         DATE           NULL,
    retirement_reason       VARCHAR2(500)  NULL,
    contact_after_retirement VARCHAR2(500) NULL,
    retirement_pay          NUMBER         NULL,
    created_at              TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at              TIMESTAMP      NULL,
    CONSTRAINT pk_retirement PRIMARY KEY (emp_id)
);

-- 상벌
CREATE TABLE reward_punishment (
    reward_id      NUMBER         NOT NULL,
    emp_id         NUMBER         NOT NULL,
    reward_type    VARCHAR2(20)   NULL,
    reward_name    VARCHAR2(100)  NULL,
    manager        VARCHAR2(50)   NULL,
    reward_date    DATE           NULL,
    reward_content VARCHAR2(500)  NULL,
    remarks        VARCHAR2(200)  NULL,
    created_at     TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at     TIMESTAMP      NULL,
    CONSTRAINT pk_reward_punishment PRIMARY KEY (reward_id)
);

-- 발령
CREATE TABLE appointment (
    appointment_id   NUMBER         NOT NULL,
    emp_id           NUMBER         NOT NULL,
    appointment_type VARCHAR2(30)   NULL,
    appointment_date DATE           NULL,
    dept_name        VARCHAR2(50)   NULL,
    position_name    VARCHAR2(50)   NULL,
    job_title        VARCHAR2(50)   NULL,
    remarks          VARCHAR2(200)  NULL,
    created_at       TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at       TIMESTAMP      NULL,
    CONSTRAINT pk_appointment PRIMARY KEY (appointment_id)
);

-- 어학
CREATE TABLE language_skill (
    language_id      NUMBER         NOT NULL,
    emp_id           NUMBER         NOT NULL,
    language_name    VARCHAR2(30)   NULL,
    test_name        VARCHAR2(50)   NULL,
    official_score   VARCHAR2(30)   NULL,
    acquisition_date DATE           NULL,
    reading_level    VARCHAR2(10)   NULL,
    writing_level    VARCHAR2(10)   NULL,
    speaking_level   VARCHAR2(10)   NULL,
    created_at       TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at       TIMESTAMP      NULL,
    CONSTRAINT pk_language_skill PRIMARY KEY (language_id)
);

-- 학력
CREATE TABLE education (
    education_id     NUMBER         NOT NULL,
    emp_id           NUMBER         NOT NULL,
    education_type   VARCHAR2(20)   NULL,
    admission_date   DATE           NULL,
    graduation_date  DATE           NULL,
    school_name      VARCHAR2(100)  NULL,
    major            VARCHAR2(100)  NULL,
    education_status VARCHAR2(20)   NULL,
    created_at       TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at       TIMESTAMP      NULL,
    CONSTRAINT pk_education PRIMARY KEY (education_id)
);

-- 회사 로고
CREATE TABLE company_logo (
    company_id NUMBER         NOT NULL,
    logo_path  VARCHAR2(300)  NULL,
    created_at TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at TIMESTAMP      NULL,
    CONSTRAINT pk_company_logo PRIMARY KEY (company_id)
);

-- 사원 급여 정보 (1:1)
CREATE TABLE employee_information_salary (
    emp_id                      NUMBER         NOT NULL,
    national_pension_yn         VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    health_insurance_yn         VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    health_discount_rate        NUMBER         DEFAULT 0 NOT NULL,
    longterm_care_yn            VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    longterm_care_discount_rate NUMBER         DEFAULT 0 NOT NULL,
    employment_insurance_yn     VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    income_tax_type             VARCHAR2(10)   NOT NULL,
    youth_tax_reduction_yn      VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    youth_tax_reduction_rate    NUMBER         DEFAULT 0 NULL,
    durunuri_type               VARCHAR2(10)   NOT NULL,
    durunuri_split_yn           VARCHAR2(1)    DEFAULT 'N' NOT NULL,
    durunuri_pension_rate       NUMBER         DEFAULT 0 NOT NULL,
    durunuri_employment_rate    NUMBER         DEFAULT 0 NOT NULL,
    base_salary                 NUMBER         NOT NULL,
    pension_salary              NUMBER         NULL,
    health_salary               NUMBER         NULL,
    employment_salary           NUMBER         NULL,
    salary_bank                 VARCHAR2(50)   NULL,
    salary_account              VARCHAR2(50)   NULL,
    created_at                  TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at                  TIMESTAMP      NULL,
    CONSTRAINT pk_employee_information_salary PRIMARY KEY (emp_id),
    CONSTRAINT ck_eis_national_pension_yn CHECK (national_pension_yn IN ('Y', 'N')),
    CONSTRAINT ck_eis_health_insurance_yn CHECK (health_insurance_yn IN ('Y', 'N')),
    CONSTRAINT ck_eis_longterm_care_yn CHECK (longterm_care_yn IN ('Y', 'N')),
    CONSTRAINT ck_eis_employment_insurance_yn CHECK (employment_insurance_yn IN ('Y', 'N')),
    CONSTRAINT ck_eis_youth_tax_reduction_yn CHECK (youth_tax_reduction_yn IN ('Y', 'N')),
    CONSTRAINT ck_eis_durunuri_split_yn CHECK (durunuri_split_yn IN ('Y', 'N'))
);

COMMENT ON COLUMN employee_information_salary.national_pension_yn     IS 'Y/N';
COMMENT ON COLUMN employee_information_salary.health_insurance_yn     IS 'Y/N';
COMMENT ON COLUMN employee_information_salary.longterm_care_yn        IS 'Y/N';
COMMENT ON COLUMN employee_information_salary.employment_insurance_yn IS 'Y/N';
COMMENT ON COLUMN employee_information_salary.youth_tax_reduction_yn  IS 'Y/N';
COMMENT ON COLUMN employee_information_salary.durunuri_split_yn       IS 'Y/N';

-- 보증보험
CREATE TABLE guarantee_insurance (
    emp_id            NUMBER         NOT NULL,
    insurance_company VARCHAR2(100)  NULL,
    insurance_number  VARCHAR2(50)   NULL,
    insurance_amount  NUMBER         NULL,
    join_date         DATE           NULL,
    expiration_date   DATE           NULL,
    remarks           VARCHAR2(200)  NULL,
    created_at        TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at        TIMESTAMP      NULL,
    CONSTRAINT pk_guarantee_insurance PRIMARY KEY (emp_id)
);

-- 보증인 (원본 Field 컬럼명 정리)
CREATE TABLE guarantor (
    emp_id         NUMBER         NOT NULL,
    guarantor_name VARCHAR2(30)   NULL,
    relation       VARCHAR2(30)   NULL,
    resident_no    VARCHAR2(15)   NULL,
    guarantee_amt  NUMBER         NULL,
    start_date     DATE           NULL,
    end_date       DATE           NULL,
    phone_number   VARCHAR2(20)   NULL,
    created_at     TIMESTAMP      DEFAULT SYSTIMESTAMP NULL,
    updated_at     TIMESTAMP      NULL,
    CONSTRAINT pk_guarantor PRIMARY KEY (emp_id)
);


-- 퇴직금

CREATE TABLE severance_pay (
    severance_pay_id             NUMBER          NOT NULL,
    emp_id                       NUMBER          NOT NULL,
    retirement_bonus             NUMBER          NULL,
    dismissal_allowance          NUMBER          NULL,
    non_taxable_retirement_pay   NUMBER          NULL,
    prepaid_tax                  NUMBER          NULL,
    tax_credit                   NUMBER          NULL,
    daily_ordinary_wage          NUMBER          NULL,
    retirement_income            NUMBER          NOT NULL,
    calculated_tax               NUMBER          NOT NULL,
    retirement_income_tax        NUMBER          NOT NULL,
    local_income_tax             NUMBER          NOT NULL,
    deferred_retirement_tax      NUMBER          NOT NULL,
    deferred_local_tax           NUMBER          NOT NULL,
    rural_special_tax            NUMBER          NULL,
    other_deduction              NUMBER          NULL,
    taxable_retirement_pay       NUMBER          NOT NULL,
    withholding_tax              NUMBER          NOT NULL,
    net_payment                  NUMBER          NOT NULL,
    payment_date                 DATE            NULL,
    created_at                   TIMESTAMP       NULL,
    updated_at                   TIMESTAMP       NULL,

    CONSTRAINT pk_severance_pay PRIMARY KEY (severance_pay_id),
    CONSTRAINT uq_severance_pay_emp UNIQUE (emp_id)
);

-- 기타 과세소득

CREATE TABLE other_taxable_income (
    other_taxable_income_id      NUMBER          NOT NULL,
    severance_pay_id             NUMBER          NOT NULL,
    payment_month                DATE            NULL,
    payment_item                 VARCHAR2(100)   NULL,
    amount                       NUMBER          NULL,
    created_at                   TIMESTAMP       NULL,
    updated_at                   TIMESTAMP       NULL,

    CONSTRAINT pk_other_taxable_income PRIMARY KEY (other_taxable_income_id)
);

-- 과세이연계좌

CREATE TABLE tax_deferred_account (
    tax_deferred_account_id      NUMBER          NOT NULL,
    severance_pay_id             NUMBER          NOT NULL,
    pension_provider_name        VARCHAR2(100)   NULL,
    business_registration_no     VARCHAR2(20)    NULL,
    account_no                   VARCHAR2(50)    NULL,
    deposit_date                 DATE            NULL,
    deposit_amount               NUMBER          NULL,
    created_at                   TIMESTAMP       NULL,
    updated_at                   TIMESTAMP       NULL,

    CONSTRAINT pk_tax_deferred_account PRIMARY KEY (tax_deferred_account_id)
);


ALTER TABLE employee
    ADD CONSTRAINT fk_employee_dept
    FOREIGN KEY (dept_id) REFERENCES department (dept_id);

ALTER TABLE employee
    ADD CONSTRAINT fk_employee_position
    FOREIGN KEY (position_id) REFERENCES position (position_id);

ALTER TABLE transfer_form_item
    ADD CONSTRAINT fk_transfer_form_item_form
    FOREIGN KEY (form_id) REFERENCES transfer_form (form_id);

ALTER TABLE education_training
    ADD CONSTRAINT fk_education_training_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE salary
    ADD CONSTRAINT fk_salary_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE salary
    ADD CONSTRAINT fk_salary_salary_date
    FOREIGN KEY (salary_date_id) REFERENCES salary_date (salary_date_id);

ALTER TABLE transfer_history
    ADD CONSTRAINT fk_transfer_history_salary
    FOREIGN KEY (salary_id) REFERENCES salary (salary_id);

ALTER TABLE national_pension_information
    ADD CONSTRAINT fk_npi_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE daily_work
    ADD CONSTRAINT fk_daily_work_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE license
    ADD CONSTRAINT fk_license_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE attendance_type
    ADD CONSTRAINT fk_attendance_type_vacation
    FOREIGN KEY (vacation_type_id) REFERENCES vacation_type (vacation_type_id);

ALTER TABLE attendance
    ADD CONSTRAINT fk_attendance_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE attendance
    ADD CONSTRAINT fk_attendance_type
    FOREIGN KEY (attendance_type_id) REFERENCES attendance_type (attendance_type_id);

ALTER TABLE dependent
    ADD CONSTRAINT fk_dependent_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE company_stamp
    ADD CONSTRAINT fk_company_stamp_company
    FOREIGN KEY (company_id) REFERENCES company_information (company_id);

ALTER TABLE recommender
    ADD CONSTRAINT fk_recommender_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE manager_information
    ADD CONSTRAINT fk_manager_info_company
    FOREIGN KEY (company_id) REFERENCES company_information (company_id);

ALTER TABLE manager_information
    ADD CONSTRAINT fk_manager_info_dept
    FOREIGN KEY (dept_id) REFERENCES department (dept_id);

ALTER TABLE manager_information
    ADD CONSTRAINT fk_manager_info_position
    FOREIGN KEY (position_id) REFERENCES position (position_id);

ALTER TABLE military
    ADD CONSTRAINT fk_military_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE personal_history
    ADD CONSTRAINT fk_personal_history_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE salary_detail
    ADD CONSTRAINT fk_salary_detail_salary
    FOREIGN KEY (salary_id) REFERENCES salary (salary_id);

ALTER TABLE salary_detail
    ADD CONSTRAINT fk_salary_detail_item
    FOREIGN KEY (salary_item_id) REFERENCES salary_item (salary_item_id);

ALTER TABLE salary_item
    ADD CONSTRAINT fk_salary_item_attendance_type
    FOREIGN KEY (attendance_type_id) REFERENCES attendance_type (attendance_type_id);

ALTER TABLE salary_detail
    ADD CONSTRAINT fk_salary_detail_attendance_type
    FOREIGN KEY (attendance_type_id) REFERENCES attendance_type (attendance_type_id);

ALTER TABLE payroll_information
    ADD CONSTRAINT fk_payroll_info_company
    FOREIGN KEY (company_id) REFERENCES company_information (company_id);

ALTER TABLE retirement
    ADD CONSTRAINT fk_retirement_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE reward_punishment
    ADD CONSTRAINT fk_reward_punishment_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE appointment
    ADD CONSTRAINT fk_appointment_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE language_skill
    ADD CONSTRAINT fk_language_skill_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE education
    ADD CONSTRAINT fk_education_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE company_logo
    ADD CONSTRAINT fk_company_logo_company
    FOREIGN KEY (company_id) REFERENCES company_information (company_id);

ALTER TABLE employee_information_salary
    ADD CONSTRAINT fk_eis_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE guarantee_insurance
    ADD CONSTRAINT fk_guarantee_insurance_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE guarantor
    ADD CONSTRAINT fk_guarantor_emp
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE severance_pay
    ADD CONSTRAINT fk_severance_pay_employee
    FOREIGN KEY (emp_id)
    REFERENCES employee (emp_id);

ALTER TABLE other_taxable_income
    ADD CONSTRAINT fk_oti_sev
    FOREIGN KEY (severance_pay_id)
    REFERENCES severance_pay (severance_pay_id);

ALTER TABLE tax_deferred_account
    ADD CONSTRAINT fk_tda_sev
    FOREIGN KEY (severance_pay_id)
    REFERENCES severance_pay (severance_pay_id);

