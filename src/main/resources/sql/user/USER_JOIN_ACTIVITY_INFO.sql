--Create User Join Activity History Info
create table USER_JOIN_ACTIVITY_INFO
(
	 RECORDID NUMBER NOT NULL 
, SEARCHID NUMBER NOT NULL 
, ISFINISHED NUMBER(1, 0) DEFAULT 0 NOT NULL 
, REPLYINFO VARCHAR2(600 BYTE) 
, ACTIVITYNAME VARCHAR2(100 BYTE) NOT NULL 
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
