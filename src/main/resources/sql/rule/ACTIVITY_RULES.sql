--Create User Earn Award Info
create table ACTIVITY_RULES
(
	RULEID NUMBER NOT NULL, 
	RULENAMECN VARCHAR2(100 BYTE) NOT NULL, 
	RULEGROUPID NUMBER NOT NULL, 
	RULE CLOB NOT NULL, 
	RULEWEIGHT NUMBER(2, 0) DEFAULT 0 NOT NULL,  
	CREATETIME TIMESTAMP(6) NOT NULL, 
	CREATEUSER VARCHAR2(50 BYTE) NOT NULL,  
	RULENAMEEN VARCHAR2(100 BYTE) NOT NULL , 
	EXRULEGROUPID NUMBER , 
	FUNCTIONS CLOB
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
