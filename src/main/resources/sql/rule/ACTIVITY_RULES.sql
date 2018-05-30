--Create User Earn Award Info
create table ACTIVITY_RULES
(
	ruleId NUMBER NOT NULL primary key,
	ruleName VARCHAR2(100) unique NOT NULL,
	ruleGroupId NUMBER NOT NULL,
	rule CLOB NOT NULL,
	ruleWeight NUMBER(1) DEFAULT 9,
	createTime Timestamp NOT NULL,
	createUser VARCHAR2(50) NOT NULL,
	constraint fk_ruleGroupId foreign key (ruleGroupId) references ACTIVITY_RULE_GROUPS(ruleGroupId)
);

--Create User Earn Award Info Sequence
create sequence ACTIVITY_RULES_SEQ
increment by 1
start with 1      
nomaxvalue         
nocycle
cache 10;  


--Create Index for Table  User Earn Award Info on ruleGroupId and ruleWeight
create index ACTIVITY_RULES_INDEX
  on ACTIVITY_RULES(ruleGroupId, ruleWeight);
