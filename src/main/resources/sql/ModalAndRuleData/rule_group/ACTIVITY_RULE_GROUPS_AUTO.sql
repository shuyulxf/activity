--------------------------------------------------------
--  DDL for Trigger ACTIVITY_RULE_GROUPS_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "KMS"."ACTIVITY_RULE_GROUPS_AUTO" 
before insert 
	on ACTIVITY_RULE_GROUPS 
	for each row
begin
	select ACTIVITY_RULE_GROUPS_SEQ.nextval into:new.ruleGroupId from dual;
end;
/
ALTER TRIGGER "KMS"."ACTIVITY_RULE_GROUPS_AUTO" ENABLE;
