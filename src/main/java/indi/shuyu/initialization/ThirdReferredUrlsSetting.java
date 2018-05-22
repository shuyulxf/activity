package indi.shuyu.initialization;

import java.io.IOException;
import java.util.HashMap;

import indi.shuyu.common.fileoperations.FileOperation;

public class ThirdReferredUrlsSetting {
	private HashMap<String, String> datas = null;
	private String dataFilePath = "datas/thirdReferredUrls.properties";
	
	public ThirdReferredUrlsSetting() throws IOException {
		
		datas = FileOperation.readFileToMap(dataFilePath);
	}

	public HashMap<String, String> getTRUS() {
		return datas;
	}
}
