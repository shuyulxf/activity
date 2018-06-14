--------------------------------------------------------
--  DDL for Trigger ACTIVITY_FORM_ATTR_INFO_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "KMS"."ACTIVITY_FORM_ATTR_INFO_AUTO" 
before insert 
	on ACTIVITY_FORM_ATTR_INFO 
	for each row
begin
	select ACTIVITY_FORM_ATTR_INFO_SEQ.nextval into:new.formAttrInfoId from dual;
end;
/
ALTER TRIGGER "KMS"."ACTIVITY_FORM_ATTR_INFO_AUTO" ENABLE;
