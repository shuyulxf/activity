-- Insert Into ACTIVITY_FORM_ATTR__INFO table
insert into USER_EARN_AWARD_INFO(
			phoneNumber,
			activityName,
			activityProvince,
			activityCity,
			activityApplyCode,
			keyword,
			replyInfo) 
            values('4','1','1','1','1','1','1111'); 
            

-- Drop Index
drop index USER_EARN_AWARD_INFO_INDEX;

-- Drop Triger
drop trigger USER_EARN_AWARD_INFO_AUTO;

-- Drop Sequence
drop sequence USER_EARN_AWARD_INFO_SEQ;

-- Drop Table
drop table USER_EARN_AWARD_INFO;