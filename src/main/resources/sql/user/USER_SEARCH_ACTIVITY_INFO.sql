--Create User search Activity History Info
create table USER_SEARCH_ACTIVITY_INFO
(
	SEARCHID NUMBER NOT NULL 
	, PHONENUMBER VARCHAR2(11 BYTE) NOT NULL 
	, PROVINCE VARCHAR2(20 BYTE) NOT NULL 
	, CITY VARCHAR2(20 BYTE) 
	, APPLYCODE VARCHAR2(100 BYTE) NOT NULL 
	, CREATETIME DATE DEFAULT sysdate NOT NULL 
	, QUERY VARCHAR2(50 BYTE) NOT NULL 
);

--Create User search Activity History Sequence
create sequence USER_SEARCH_ACTIVITY_INFO_SEQ
increment by 1
start with 1      
nomaxvalue         
nocycle
cache 10;  

--Create Trigger for Table  User Search Activity History Before Insert
--create trigger USER_SEARCH_ACTIVITY_INFO_AUTO 
--before insert 
--	on USER_SEARCH_ACTIVITY_INFO 
--	for each row
--begin
--	select USER_SEARCH_ACTIVITY_INFO_SEQ.nextval into:new.searchId from dual;
--end;   

--Create Index for Table  User search Activity History Form Attrs on formAttrNameType and formAttrName
create index USER_SEARCH_INFO_INDEX
  on USER_SEARCH_ACTIVITY_INFO(createTime, phoneNumber);
