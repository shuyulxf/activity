package indi.shuyu.initialization;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import indi.shuyu.common.fileoperations.FileOperation;

public class ResponseStatusAndErrorResourceName {
	
	private HashMap<String, String> datas = null;
	private String dataFilePath = "datas/responseStatusAndErrorResourceNameMap.properties";
	
	public ResponseStatusAndErrorResourceName() throws IOException {
		
		datas = FileOperation.readFileToMap(dataFilePath);
	}

	
	public HashMap<String, String> getRSRN() {
		return datas;
	}
}
