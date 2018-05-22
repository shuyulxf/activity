package indi.shuyu.initialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import indi.shuyu.common.fileoperations.FileOperation;

public class ValidInfoForFormItem {
	private HashMap<String, String> temp = null;
	private HashMap<String, String[]> datas = null;
	private String dataFilePath = "datas/validInfoForFormItems.properties";
	
	public ValidInfoForFormItem() throws IOException {
		
		temp = FileOperation.readFileToMap(dataFilePath);
		
		datas = new HashMap<String, String[]>();
		Iterator<Map.Entry<String, String>> entries = temp.entrySet().iterator();  
		while (entries.hasNext()) {  
		  
		    Map.Entry<String, String> entry = entries.next();  
		    datas.put(entry.getKey(), entry.getValue().split(","));
		}  
	}

	
	public HashMap<String, String[]> getVFI() {
		return datas;
	}
}
