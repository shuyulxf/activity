--Create Activity Rule Group
create table ACTIVITY_RULE_GROUPS
(
	RULEGROUPID NUMBER NOT NULL 
	, RULEGROUPNAMECN VARCHAR2(50 BYTE) NOT NULL 
	, RULEGROUPWEIGHT NUMBER(*, 0) DEFAULT 0 NOT NULL 
	, CREATEUSER VARCHAR2(50 BYTE) NOT NULL 
	, CREATETIME TIMESTAMP(6) DEFAULT NULL NOT NULL 
	, ISEXCLUSIVE NUMBER DEFAULT 0 NOT NULL 
	, RULEGROUPNAMEEN VARCHAR2(50 BYTE) NOT NULL 
	, ISCOMMONGROUP NUMBER(1, 0) NOT NULL 
);

--Create Activity Rule Group Sequence
create sequence ACTIVITY_RULE_GROUPS_SEQ
increment by 1
start with 1      
nomaxvalue         
nocycle
cache 10;  

--Create Trigger for Table  Activity Rule Group Before Insert
create trigger ACTIVITY_RULE_GROUPS_AUTO 
before insert 
	on ACTIVITY_RULE_GROUPS 
	for each row
begin
	select ACTIVITY_RULE_GROUPS_SEQ.nextval into:new.ruleGroupId from dual;
end;   

--Create Index for Table  Activity Rule Group on ruleGroupWeight
create index ACTIVITY_RULE_GROUPS_INDEX
  on ACTIVITY_RULE_GROUPS(ruleGroupWeight);
