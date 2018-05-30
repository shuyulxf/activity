-- Insert Into ACTIVITY_ATTRIBUTES table
insert into ACTIVITY_ATTRIBUTES(activityName, activityType, activityAwardLeftNumber, activityAwardTotalNumber, activityGeneralSetting, activityAwardSetting, activityReplySetting, activityExtendSetting) 
            values('4','1',1,1,empty_blob(),empty_blob(),empty_blob(),empty_blob()); 
            

-- Drop Triger
--drop trigger ACTIVITY_ATTRIBUTES_ADD_AUTO;

-- Drop Sequence
drop sequence ACTIVITY_ATTRIBUTES_SEQ;

-- Drop Table
drop table ACTIVITY_ATTRIBUTES;


          
