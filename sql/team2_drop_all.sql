-- ============================================================
-- team2 스키마 전체 초기화 (Oracle)
-- team2.sql 실행 전 DB 초기화용
--
-- 대상 (현재 접속 계정 OWNER 소유 객체):
--   - 모든 TABLE  (CASCADE CONSTRAINTS + PURGE)
--   - 모든 SEQUENCE
--   - 모든 VIEW
--
-- 주의:
--   - 해당 스키마의 모든 데이터가 삭제됩니다. 되돌릴 수 없습니다.
--   - 다른 스키마 객체는 삭제되지 않습니다.
--   - PROCEDURE / PACKAGE / TRIGGER 등은 이 스크립트에 포함되지 않습니다.
-- ============================================================

BEGIN
    -- 테이블 삭제 (외래키 제약조건 포함 삭제)
    FOR c IN (SELECT table_name FROM user_tables) LOOP
        EXECUTE IMMEDIATE 'DROP TABLE "' || c.table_name || '" CASCADE CONSTRAINTS PURGE';
    END LOOP;

    -- 시퀀스 삭제
    FOR c IN (SELECT sequence_name FROM user_sequences) LOOP
        EXECUTE IMMEDIATE 'DROP SEQUENCE "' || c.sequence_name || '"';
    END LOOP;

    -- 뷰 삭제
    FOR c IN (SELECT view_name FROM user_views) LOOP
        EXECUTE IMMEDIATE 'DROP VIEW "' || c.view_name || '"';
    END LOOP;
END;
/

COMMIT;
