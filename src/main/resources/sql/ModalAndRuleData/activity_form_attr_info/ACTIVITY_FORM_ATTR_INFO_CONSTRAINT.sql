--------------------------------------------------------
--  Constraints for Table ACTIVITY_FORM_ATTR_INFO
--------------------------------------------------------

  ALTER TABLE "KMS"."ACTIVITY_FORM_ATTR_INFO" MODIFY ("FORMATTRINFOID" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_FORM_ATTR_INFO" MODIFY ("FORMATTRNAME" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_FORM_ATTR_INFO" MODIFY ("FORMATTRNAMELABEL" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_FORM_ATTR_INFO" MODIFY ("FORMATTRNAMETYPE" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_FORM_ATTR_INFO" MODIFY ("FORMATTRNAMEFILLTYPE" NOT NULL ENABLE);
 
  ALTER TABLE "KMS"."ACTIVITY_FORM_ATTR_INFO" ADD PRIMARY KEY ("FORMATTRINFOID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "KMS"."ACTIVITY_FORM_ATTR_INFO" ADD UNIQUE ("FORMATTRNAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "KMS"."ACTIVITY_FORM_ATTR_INFO" MODIFY ("HASREPLYFORFORMATTR" NOT NULL ENABLE);