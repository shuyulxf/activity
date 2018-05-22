package indi.shuyu.common.fileoperations;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class FileOperation {
	
	public static BufferedReader readFileToMemoryUseClassPath(String dataFilePath) throws FileNotFoundException, IOException {
		
		Resource resource = new ClassPathResource(dataFilePath);
		FileReader fr = new FileReader(resource.getFile());
		
		return new BufferedReader(fr);
	}
	
	public static void closeBufferReader(BufferedReader br) throws IOException {
		br.close();
	}
	
	public static HashMap<String, String> readFileToMap(String dataFilePath) throws FileNotFoundException, IOException{
		
		BufferedReader br = FileOperation.readFileToMemoryUseClassPath(dataFilePath);
		
		String readLine = "";
		
		HashMap<String, String> datas = new HashMap<String, String>();
		
		while ((readLine = br.readLine()) != null) {
            
			String[] keyAndValueArr = readLine.split(" ");
			
			if (keyAndValueArr != null && keyAndValueArr.length == 2) {
				datas.put(keyAndValueArr[0], keyAndValueArr[1]);
			}
        }
		
		FileOperation.closeBufferReader(br);
		
		return datas;
	}
	
}
