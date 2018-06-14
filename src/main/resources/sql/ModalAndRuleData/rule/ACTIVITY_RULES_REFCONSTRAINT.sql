--------------------------------------------------------
--  Ref Constraints for Table ACTIVITY_RULES
--------------------------------------------------------

  ALTER TABLE "KMS"."ACTIVITY_RULES" ADD CONSTRAINT "FK_RULEGROUPID" FOREIGN KEY ("RULEGROUPID")
	  REFERENCES "KMS"."ACTIVITY_RULE_GROUPS" ("RULEGROUPID") ENABLE;
