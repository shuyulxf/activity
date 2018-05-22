package indi.shuyu.core.rules;

import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KieUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(KieUtils.class);
	
	private static KieHelper kh = new KieHelper();
	private static KieContainer kc = null;
	public static KieSession getKS(String[] fileList) {
		
		addResource(fileList);
		KieContainer kc = kh.getKieContainer();
		KieSession kSession = kc.newKieSession();
		
    	return kSession;
	}
	
	public static KieSession getKS(String fileName) {
		addResource(fileName);
		KieContainer kc = kh.getKieContainer();
		KieSession kSession = kc.newKieSession();
		
    	return kSession;
	}
	
	public static KieSession getKS() {

		KieContainer kc = kh.getKieContainer();
		KieSession kSession = kc.newKieSession();
		
    	return kSession;
	}
	
	
	public static boolean addResource(String fileName) {
		
		String file = getResourcePath(fileName);
		kh.addResource(ResourceFactory.newClassPathResource(file, "UTF-8"), ResourceType.DRL);
    	Results rlt = kh.verify();
    	if (rlt.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
    		logger.error("Load rule " + file + " resource fail!");
    		return false;
    	}
    	
		return true;
	}
	
	public static boolean addResource(String[] fileList) {
		
		int len = fileList.length;
		Results rlt = null;
		
		for(int i = 0; i < len; i++) {
			
			String file = getResourcePath(fileList[i]);
			kh.addResource(ResourceFactory.newClassPathResource(file), ResourceType.DRL);
			rlt = kh.verify();
			
			if (rlt.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
	    		logger.error("Load rule " + file + " resource fail!");
	    		return false;
	    	}
		}
		
		return true;
	}
	
	public static String getResourcePath(String fileName) {
		return "rules/" + fileName + ".drl";
	}
}
