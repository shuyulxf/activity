package indi.shuyu.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.initialization.GlobalStaticValues;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Override  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object obj, Exception err)  
            throws Exception {  
    }  
  
    @Override  
    public void postHandle(HttpServletRequest request, HttpServletResponse response,  
            Object obj, ModelAndView mav) throws Exception {  
        
    }  
  
    @Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  
            Object obj) throws Exception {  
    	
    	GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
    	
    	String userNameFromUrl = request.getParameter("userid");
    	Object userNameFromSession = request.getSession().getAttribute("UserName");
    	String LOGIN_URL = gv.getThirdUrl("login");
    	
    	if (userNameFromUrl != null && userNameFromUrl.length() > 0) {
    		String unt = gv.getAUTbyUser(userNameFromUrl);
    		request.getSession().setAttribute("UserName", userNameFromUrl);
    		
    		request.getSession().setAttribute("UserNameRole", unt);
    		
    	} else {
    		
    		if (userNameFromSession == null && (userNameFromUrl == null || userNameFromUrl.length() == 0)) {
    			response.sendRedirect(LOGIN_URL);
    		} 
    	
    	}


        return true;  
    }  
}
