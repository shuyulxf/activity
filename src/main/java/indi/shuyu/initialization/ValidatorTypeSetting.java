package indi.shuyu.initialization;

import java.io.IOException;
import java.util.HashMap;

import indi.shuyu.common.fileoperations.FileOperation;

public class ValidatorTypeSetting {
	private HashMap<String, String> datas = null;
	private String dataFilePath = "datas/validateTypeSetting.properties";
	
	public ValidatorTypeSetting() throws IOException {
		
		datas = FileOperation.readFileToMap(dataFilePath);
	}

	
	public HashMap<String, String> getVTS() {
		return datas;
	}
}
