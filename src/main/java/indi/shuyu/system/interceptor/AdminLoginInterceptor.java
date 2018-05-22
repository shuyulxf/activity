package indi.shuyu.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.initialization.GlobalStaticValues;

public class AdminLoginInterceptor implements HandlerInterceptor {
	
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
    	Object userNameRoleFromSession = request.getSession().getAttribute("UserNameRole");
    	
    	String LOGIN_URL = gv.getThirdUrl("login");
    	
    	if (userNameFromUrl != null && userNameFromUrl.length() > 0) {
    		String unt = gv.getAUTbyUser(userNameFromUrl);
    		request.getSession().setAttribute("UserName", userNameFromUrl);
    		
    		if (unt.equals(userNameFromUrl)) response.sendRedirect(LOGIN_URL); 
    		else {
    			request.getSession().setAttribute("UserNameRole", unt);
    		}
    	} else {
    		
    		if (userNameRoleFromSession != null) {
    			String userNameRole;
    			userNameRole = userNameRoleFromSession.toString();
    			if (userNameRole.indexOf("_activity_admin") == -1) response.sendRedirect(LOGIN_URL); 
    		} else {
    			response.sendRedirect(LOGIN_URL); 
    		}
    	
    	}

        return true;  
    }  
}
