package indi.shuyu.common.validator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import indi.shuyu.initialization.GlobalStaticValues;

public class ValidatorHelper {
	
	public static ValidItemPairResult validParams(Object obj,
			GlobalStaticValues gv) throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException 
	{
		HashMap<String, String[]> validItems = gv.getVIFFI();
		List<ValidatorItemPair> pairs = new ArrayList<ValidatorItemPair>();
		
        Field[] fields = obj.getClass().getDeclaredFields();  
        for(int i = 0; i < fields.length; i++){  
        	
            Field f = fields[i];  
            f.setAccessible(true);  

            String[] validType = validItems.get(f.getName());
            if (validType != null) {
            	pairs.add(gv.getValidatorItempair(validType, f.getName(), f.get(obj) != null ? f.get(obj).toString() : ""));
            }
        }   
		
		return gv.validte(pairs);
	}
}
