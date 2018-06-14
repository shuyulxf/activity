--Create Activity info table
create table ACTIVITY_ATTRIBUTES
(
	activityId NUMBER NOT NULL primary key,
	activityName VARCHAR2(100) NOT NULL UNIQUE,
	activityType VARCHAR2(100) NOT NULL,
	activityCommonSetting BLOB NOT NULL,
	activityGeneralSetting BLOB,
	activityAwardSetting BLOB,
	activityReplySetting BLOB,
	createTime TIMESTAMP not null,
	createUser  VARCHAR2(50) no null,
	ruleIds VARCHAR2(2000),
	activityProvince VARCHAR2(300), 
	activityApplycode  VARCHAR2(1000),
	activityStartTime DATE,
	activityEndTime DATE
);

--Create Activity Info Sequence
create sequence ACTIVITY_ATTRIBUTES_SEQ
increment by 1
start with 1      
nomaxvalue         
nocycle
cache 10;  

--Create Trigger for Table Activity Before Insert
create trigger ACTIVITY_ATTRIBUTES_ADD_AUTO 
before insert 
	on ACTIVITY_ATTRIBUTES 
	for each row
begin
	select ACTIVITY_ATTRIBUTES_SEQ.nextval into:new.activityId from dual;
end;   
