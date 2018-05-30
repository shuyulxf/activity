-- Insert Into ACTIVITY_FORM_ATTR__INFO table
insert into ACTIVITY_RULES(
			ruleName,
			ruleGroupId,
			ruleCondition,
			ruleAction,
			ruleFuncs) 
            values('4', 1,"1","1",empty_blob()); 
            

-- Drop Index
drop index ACTIVITY_RULES_INDEX;


-- Drop Sequence
drop sequence ACTIVITY_RULES_SEQ;

-- Drop Table
drop table ACTIVITY_RULES;