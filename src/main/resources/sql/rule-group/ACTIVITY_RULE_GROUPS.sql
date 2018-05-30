--Create Activity Rule Group
create table ACTIVITY_RULE_GROUPS
(
	ruleGroupId NUMBER NOT NULL primary key,
	ruleGroupName VARCHAR2(50) NOT NULL unique,
	ruleGroupWeight NUMBER(1) DEFAULT 9 NOT NULL,
	ISEXCLUSIVE NUMBER(1) DEFAULT 0 NOT NULL,
	createTime TIMESTAMP NOT NULL,
	createUser VARCHAR2(50) DEFAULT SYSDATE NOT NULL
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
