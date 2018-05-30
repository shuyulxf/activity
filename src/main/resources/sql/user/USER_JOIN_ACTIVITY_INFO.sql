--Create User Join Activity History Info
create table USER_JOIN_ACTIVITY_INFO
(
	recordId NUMBER NOT NULL primary key,
	searchId NUMBER NOT NULL,
	activityName VARCHAR2(100) NOT NULL,
	keyword VARCHAR2(50) NOT NULL,
	isFinished NUMBER(1) DEFAULT 0,
	replyInfo VARCHAR2(600),
	constraint fk_searchId foreign key (searchId) references USER_SEARCH_ACTIVITY_INFO(searchId)
);

--Create User Join Activity History Sequence
create sequence USER_JOIN_ACTIVITY_INFO_SEQ
increment by 1
start with 1      
nomaxvalue         
nocycle
cache 10;  

--Create Trigger for Table  User Join Activity History Before Insert
--create trigger USER_JOIN_ACTIVITY_INFO_AUTO 
--before insert 
--	on USER_JOIN_ACTIVITY_INFO 
--	for each row
--begin
--	select USER_JOIN_ACTIVITY_INFO_SEQ.nextval into:new.recordId from dual;
--end;   

--Create Index for Table  User Join Activity History Form Attrs on formAttrNameType and formAttrName
create index USER_JOIN_ACTIVITY_INFO_INDEX
  on USER_JOIN_ACTIVITY_INFO(searchId);
