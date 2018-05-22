package indi.shuyu.common.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHelper implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext cxt) throws BeansException { 
		applicationContext  = cxt;  
	}

	public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
	
	/** 
     * Get Bean Object
     * @param name 
     * @return Object
     * @throws BeansException 
     */  
    public static Object getBean(String name) throws BeansException {  
        return applicationContext.getBean(name);  
    }  
}
