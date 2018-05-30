-- Insert Into ACTIVITY_FORM_ATTR_INFO table
insert into ACTIVITY_RULE_GROUPS(
			ruleName) 
            values('4'); 
            

-- Drop Index
drop index ACTIVITY_RULE_GROUPS_INDEX;

-- Drop Triger
drop trigger ACTIVITY_RULE_GROUPS_AUTO;

-- Drop Sequence
drop sequence ACTIVITY_RULE_GROUPS_SEQ;

-- Drop Table
drop table ACTIVITY_RULE_GROUPS;