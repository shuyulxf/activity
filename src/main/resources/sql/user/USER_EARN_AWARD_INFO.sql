--Create User Earn Award Info
create table USER_EARN_AWARD_INFO
(
	AWARDINFOID NUMBER NOT NULL 
	, RECORDID NUMBER NOT NULL 
	, AWARDTYPE VARCHAR2(50 BYTE) DEFAULT NULL NOT NULL 
	, NUMBERFORAWARD NUMBER DEFAULT 0 NOT NULL 
	, ISSERIESAWARDFORJOINACTIVITY NUMBER(1, 0) DEFAULT 0 NOT NULL 
	, EARNTIME DATE NOT NULL 
	, REPLYINFFORAWARD VARCHAR2(500 BYTE) 
	constraint fk_recordId foreign key (recordId) references USER_JOIN_ACTIVITY_INFO(recordId)
);

--Create User Earn Award Info Sequence
create sequence USER_EARN_AWARD_INFO_SEQ
increment by 1
start with 1      
nomaxvalue         
nocycle
cache 10;  

--Create Trigger for Table User Earn Award Info Before Insert
create trigger USER_EARN_AWARD_INFO_AUTO 
before insert 
	on USER_EARN_AWARD_INFO 
	for each row
begin
	select USER_EARN_AWARD_INFO_SEQ.nextval into:new.awardInfoId from dual;
end;   

--Create Index for Table  User Earn Award Info on recordId and awardType
create index USER_EARN_AWARD_INFO_INDEX
  on USER_EARN_AWARD_INFO(recordId, awardType);
