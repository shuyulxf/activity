package indi.shuyu.common.encrypt;

import java.util.HashMap;

public class DESEncryptAlgorithm {
	public String getResult() {
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("key1", 1);
		map.put("key2", 2);
		return map.get("key1").toString();
	}
}
