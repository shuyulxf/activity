--Create Activity info table
create table ACTIVITY_ATTRIBUTES
(
	activityId NUMBER NOT NULL primary key,
	activityName VARCHAR2(100) NOT NULL UNIQUE,
	activityType VARCHAR2(100) NOT NULL,
	activityAwardLeftNumber NUMBER NOT NULL,
	activityAwardTotalNumber NUMBER NOT NULL,
	activityGeneralSetting BLOB NOT NULL,
	activityAwardSetting BLOB NOT NULL,
	activityReplySetting BLOB NOT NULL,
	activityExtendSetting BLOB,
	hasReplyForFormAttr NUMBER(1) NOT NULL,
	createTime TIMESTAMP not null,
	createUser  VARCHAR2(50) no null
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
