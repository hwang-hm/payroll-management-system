-- ============================================================
-- salary_item / salary_detail 재생성
-- 변경: pay_type(VARCHAR2) → attendance_type_id(NUMBER) FK
-- 주의: salary_detail, salary_item 데이터가 모두 삭제됩니다.
-- 실행 전 attendance_type 테이블에 데이터가 있어야 합니다.
-- ============================================================

-- 1. FK 제거 (기존 스키마 기준)
ALTER TABLE salary_detail DROP CONSTRAINT fk_salary_detail_item;
ALTER TABLE salary_detail DROP CONSTRAINT fk_salary_detail_salary;

-- 2. 테이블 삭제 (자식 → 부모 순서)
DROP TABLE salary_detail;
DROP TABLE salary_item;

-- 3. salary_item 재생성
CREATE TABLE salary_item (
    salary_item_id     NUMBER         NOT NULL,
    item_type          VARCHAR2(10)   NOT NULL,
    item_name          VARCHAR2(100)  NOT NULL,
    tax_yn             CHAR(1)        NOT NULL,
    tax_free_name      VARCHAR2(100)  NULL,
    tax_free_limit     NUMBER         DEFAULT 0 NULL,
    calc_method        VARCHAR2(100)  NULL,
    round_unit         VARCHAR2(10)   NOT NULL,
    default_amount     NUMBER         DEFAULT 0 NULL,
    remark             VARCHAR2(300)  NULL,
    use_yn             CHAR(1)        NOT NULL,
    created_at         DATE           DEFAULT SYSDATE NOT NULL,
    updated_at         DATE           NULL,
    attendance_type_id NUMBER         NOT NULL,
    CONSTRAINT pk_salary_item PRIMARY KEY (salary_item_id),
    CONSTRAINT ck_salary_item_type CHECK (item_type IN ('PAY', 'DEDUCT')),
    CONSTRAINT ck_salary_item_tax_yn CHECK (tax_yn IN ('Y', 'N')),
    CONSTRAINT ck_salary_item_use_yn CHECK (use_yn IN ('Y', 'N'))
);

COMMENT ON COLUMN salary_item.item_type           IS 'PAY(지급), DEDUCT(공제)';
COMMENT ON COLUMN salary_item.item_name           IS '항목명(기본급, 식대 등)';
COMMENT ON COLUMN salary_item.tax_yn              IS 'Y(전체과세)/N(비과세)';
COMMENT ON COLUMN salary_item.round_unit          IS 'NONE 등 반올림 단위';
COMMENT ON COLUMN salary_item.default_amount      IS '일괄지급일 때만 사용';
COMMENT ON COLUMN salary_item.remark              IS '공제항목용';
COMMENT ON COLUMN salary_item.use_yn              IS 'Y(사용)/N(미사용)';
COMMENT ON COLUMN salary_item.attendance_type_id  IS '근태항목 번호 (attendance_type FK)';

-- 4. salary_detail 재생성
CREATE TABLE salary_detail (
    detail_id          NUMBER         NOT NULL,
    salary_id          NUMBER         NOT NULL,
    salary_item_id     NUMBER         NULL,
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
    attendance_type_id NUMBER         NULL,
    CONSTRAINT pk_salary_detail PRIMARY KEY (detail_id),
    CONSTRAINT ck_salary_detail_type CHECK (item_type IN ('PAY', 'DEDUCT')),
    CONSTRAINT ck_salary_detail_tax_yn CHECK (tax_yn IN ('Y', 'N'))
);

COMMENT ON COLUMN salary_detail.item_type           IS 'PAY(지급), DEDUCT(공제)';
COMMENT ON COLUMN salary_detail.item_name           IS '항목명(기본급, 식대 등)';
COMMENT ON COLUMN salary_detail.tax_yn              IS 'Y(전체과세)/N(비과세)';
COMMENT ON COLUMN salary_detail.tax_free_limit      IS 'DEFAULT 0';
COMMENT ON COLUMN salary_detail.default_amount      IS '일괄지급일 때만 나타난다 DEFAULT 0';
COMMENT ON COLUMN salary_detail.memo                IS '공제항목용';
COMMENT ON COLUMN salary_detail.amount              IS 'DEFAULT 0';
COMMENT ON COLUMN salary_detail.attendance_type_id  IS '근태항목 번호 (attendance_type FK)';

-- 5. FK 재등록
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

COMMIT;
