-- Insert Into ACTIVITY_FORM_ATTR__INFO table
insert into USER_JOIN_ACTIVITY_INFO(
			phoneNumber,
			activityName,
			activityProvince,
			activityCity,
			activityApplyCode,
			keyword,
			replyInfo) 
            values('4','1','1','1','1','1','1111'); 
            

-- Drop Table
drop table USER_JOIN_ACTIVITY_INFO;

-- Drop Sequence
drop sequence USER_JOIN_ACTIVITY_INFO_SEQ;
          
-- Drop Triger
drop trigger USER_JOIN_ACTIVITY_INFO_AUTO;

-- Drop Index
drop index USER_JOIN_ACTIVITY_INFO_INDEX;