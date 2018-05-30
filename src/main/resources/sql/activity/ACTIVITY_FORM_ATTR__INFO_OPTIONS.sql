-- Insert Into ACTIVITY_FORM_ATTR__INFO table
insert into ACTIVITY_FORM_ATTR_INFO(
			formAttrName,
			formAttrNameLabel,
			formAttrNameDefaultValue,
			formAttrNameType,
			formAttrNameFillType) 
            values('4','1','1',1,1); 
            
-- Drop Index
drop index  ACTIVITY_FORM_ATTR_INFO_INDEX;

          
-- Drop Triger
drop trigger ACTIVITY_FORM_ATTR_INFO_AUTO;

-- Drop Sequence
drop sequence ACTIVITY_FORM_ATTR_INFO_SEQ;

-- Drop Table
drop table ACTIVITY_FORM_ATTR_INFO;