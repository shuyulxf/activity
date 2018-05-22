package indi.shuyu.common.validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidatorItemPair{
	 String formName;
	 String value;
	 List<RuleItemPair> rules;
	
	 public ValidatorItemPair(String fn, String v, List<String> rs) throws JsonParseException, JsonMappingException, IOException{
		 this.formName = fn;
		 this.value = v;
		 rules = new ArrayList<RuleItemPair>();
		 int size = rs.size();
		 for (int i = 0; i < size; i++) {
			 ObjectMapper mapper = new ObjectMapper(); 
			 RuleItemPair rule = mapper.readValue(rs.get(i), RuleItemPair.class); 
			 rules.add(rule);
		 }
	 }
	 
	 public String getFormName() {  
         return value;  
     }  
	 
     public void setFormName(String fn) {  
         this.formName = fn;  
     }  
     
	 public String getValue() {  
         return value;  
     }  
	 
     public void setValue(String v) {  
         this.value = v;  
     }  
     
     public  List<RuleItemPair> getRules() {  
         return rules;  
     }  
     
     public void setType(List<RuleItemPair> rs) {  
         this.rules = rs;  
     }  
     
}
