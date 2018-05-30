--Create Activity Rule Group
create table ACTIVITY_PRIZE
(
	prizeId NUMBER NOT NULL primary key,
	prizeName VARCHAR2(11) NOT NULL unique,
	createTime TIMESTAMP NOT NULL,
	createUser VARCHAR2(50) NOT NULL
);

--Create Activity Rule Group Sequence
create sequence ACTIVITY_PRIZE_SEQ
increment by 1
start with 1      
nomaxvalue         
nocycle
cache 10;  

--Create Trigger for Table  Activity Rule Group Before Insert
create trigger ACTIVITY_PRIZE_AUTO 
before insert 
	on ACTIVITY_PRIZE
	for each row
begin
	select ACTIVITY_PRIZE_SEQ.nextval into:new.prizeId from dual;
end;   

