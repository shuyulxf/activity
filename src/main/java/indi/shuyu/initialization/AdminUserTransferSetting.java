package indi.shuyu.initialization;

import java.io.IOException;
import java.util.HashMap;

import indi.shuyu.common.fileoperations.FileOperation;

public class AdminUserTransferSetting {
	private HashMap<String, String> datas = null;
	private String dataFilePath = "datas/adminUserTansfer.properties";
	
	public AdminUserTransferSetting() throws IOException {
		
		datas = FileOperation.readFileToMap(dataFilePath);
	}

	
	public HashMap<String, String> getAUTS() {
		return datas;
	}
}
