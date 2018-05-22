package indi.shuyu.common.base;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JacksonUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(JacksonUtils.class);
	private final static ObjectMapper objectMapper = new ObjectMapper();
	
	public static String toJSONString(Object obj) {
		
		String rlt = null; 
		try {
			rlt =  objectMapper.writeValueAsString(obj);
		} catch (JsonMappingException e) {
			logger.info(e.toString());
        } catch (IOException e) {
        	logger.info(e.toString());
        }
		
		return rlt;
	}

	public static <T> T toObject(String str, Class<T> clazz) {  
		
        if (str == null || str.length() == 0) {  
            return null;  
        }  
  
        try {  
            return objectMapper.readValue(str, clazz);  
        } catch (IOException e) {  
            logger.warn("parse json string error:" + str, e);  
            return null;  
        }  
        
    }  
	
	public static <T> T toList(String str,  TypeReference<T> jsonTypeReference) {  
		
        if (str == null || str.length() == 0) {  
            return null;  
        }  
  
      
        try {  
        	return objectMapper.readValue(str, jsonTypeReference);   
        } catch (IOException e) {  
            logger.warn("parse json string error:" + str, e);  
            return null;  
        } 
        
    }  
	
	
}
