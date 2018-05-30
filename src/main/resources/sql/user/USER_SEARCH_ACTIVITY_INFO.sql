--Create User search Activity History Info
create table USER_SEARCH_ACTIVITY_INFO
(
	searchId NUMBER NOT NULL primary key,
	phoneNumber VARCHAR2(11) NOT NULL,
	activityProvince VARCHAR2(20) NOT NULL,
	activityCity VARCHAR2(20) NOT NULL,
	activityApplyCode VARCHAR2(100) NOT NULL,
	createTime DATE DEFAULT sysdate NOT NULL,
	query VARCHAR2(50) NOT NULL
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
