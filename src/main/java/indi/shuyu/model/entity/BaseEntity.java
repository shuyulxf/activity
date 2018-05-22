package indi.shuyu.model.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseEntity {
public String toString() {
		
		String rlt = null;
		try {
			rlt =  (new ObjectMapper()).writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
		
		return rlt;
	}
}
