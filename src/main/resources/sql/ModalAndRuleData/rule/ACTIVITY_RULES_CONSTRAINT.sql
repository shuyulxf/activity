--------------------------------------------------------
--  Constraints for Table ACTIVITY_RULES
--------------------------------------------------------

  ALTER TABLE "KMS"."ACTIVITY_RULES" ADD CONSTRAINT "AR_RULENAMEEN" UNIQUE ("RULENAMEEN")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "KMS"."ACTIVITY_RULES" MODIFY ("RULEID" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_RULES" MODIFY ("RULENAMECN" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_RULES" MODIFY ("RULEGROUPID" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_RULES" MODIFY ("RULE" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_RULES" MODIFY ("CREATETIME" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_RULES" MODIFY ("CREATEUSER" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_RULES" ADD PRIMARY KEY ("RULEID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "KMS"."ACTIVITY_RULES" ADD UNIQUE ("RULENAMECN")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "KMS"."ACTIVITY_RULES" MODIFY ("RULEWEIGHT" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_RULES" MODIFY ("RULENAMEEN" NOT NULL ENABLE);
