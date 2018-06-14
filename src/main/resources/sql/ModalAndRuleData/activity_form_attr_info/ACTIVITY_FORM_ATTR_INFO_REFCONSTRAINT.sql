--------------------------------------------------------
--  Ref Constraints for Table ACTIVITY_FORM_ATTR_INFO
--------------------------------------------------------

  ALTER TABLE "KMS"."ACTIVITY_FORM_ATTR_INFO" ADD CONSTRAINT "FK_EXRULEGROUPID_ID" FOREIGN KEY ("EXRULEGROUPID")
	  REFERENCES "KMS"."ACTIVITY_RULE_GROUPS" ("RULEGROUPID") ENABLE;
