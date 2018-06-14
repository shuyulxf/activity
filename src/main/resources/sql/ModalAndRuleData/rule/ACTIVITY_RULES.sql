--------------------------------------------------------
--  DDL for Table ACTIVITY_RULES
--------------------------------------------------------

  CREATE TABLE "KMS"."ACTIVITY_RULES" 
   (	"RULEID" NUMBER, 
	"RULENAMECN" VARCHAR2(100 BYTE), 
	"RULEGROUPID" NUMBER, 
	"RULE" CLOB, 
	"RULEWEIGHT" NUMBER(2,0) DEFAULT 0, 
	"CREATETIME" TIMESTAMP (6), 
	"CREATEUSER" VARCHAR2(50 BYTE), 
	"RULENAMEEN" VARCHAR2(100 BYTE), 
	"EXRULEGROUPID" NUMBER, 
	"FUNCTIONS" CLOB
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" 
 LOB ("RULE") STORE AS BASICFILE (
  TABLESPACE "USERS" ENABLE STORAGE IN ROW CHUNK 8192 RETENTION 
  NOCACHE LOGGING 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) 
 LOB ("FUNCTIONS") STORE AS BASICFILE (
  TABLESPACE "USERS" ENABLE STORAGE IN ROW CHUNK 8192 RETENTION 
  NOCACHE LOGGING 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;
