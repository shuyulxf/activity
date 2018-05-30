--Create User Earn Award Info
create table USER_EARN_AWARD_INFO
(
	awardInfoId NUMBER NOT NULL primary key,
	recordId NUMBER NOT NULL,
	awardType VARCHAR2(50) NOT NULL,
	numberForAward NUMBER NOT NULL,
	isSeriesAwardForJoinActivity NUMBER(1)  DEFAULT 0,
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
