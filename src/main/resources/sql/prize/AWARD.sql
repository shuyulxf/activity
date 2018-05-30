--Create Activity Rule Group
create table ACTIVITY_AWARD
(
	awardId NUMBER NOT NULL primary key,
	prizeId NUMBER NOT NULL,
	activityId NUMBER NOT NULL,
	awardNum NUMBER NOT NULL,
	createTime TIMESTAMP NOT NULL,
	constraint fk_activityId foreign key (activityId) references ACTIVITY_ATTRIBUTES(activityId),
	constraint fk_prizeId foreign key (prizeId) references ACTIVITY_PRIZE(prizeId)
);

--Create Activity Rule Group Sequence
create sequence ACTIVITY_AWARD_SEQ
increment by 1
start with 1      
nomaxvalue         
nocycle
cache 10;  

--Create Trigger for Table  ACTIVITY_AWARD_AUTO Before Insert
create trigger ACTIVITY_AWARD_AUTO 
before insert 
	on ACTIVITY_AWARD
	for each row
begin
	select ACTIVITY_AWARD_SEQ.nextval into:new.awardId from dual;
end;   

--Create Index for Table  User Earn Award Info on activityId and prizeId
create index ACTIVITY_AWARD_INDEX
  on ACTIVITY_AWARD(activityId, prizeId);
