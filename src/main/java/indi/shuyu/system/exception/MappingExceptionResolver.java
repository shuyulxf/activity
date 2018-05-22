package indi.shuyu.system.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.http.SpringHttpHelper;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.system.logger.LogDash;

public class MappingExceptionResolver implements HandlerExceptionResolver{
	
	private final static Logger logger = LoggerFactory.getLogger(MappingExceptionResolver.class);
	private final String errorType500 = "500";
	private final String otherErrors  = "others";
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
		GlobalStaticValues GSVS = (GlobalStaticValues)SpringContextHelper.getApplicationContext().getBean("initialization"); 
		ModelAndView mv = null;
		String errorPagePath = "";
		LogDash ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		try {
			
			SpringHttpHelper ajaxHelper = (SpringHttpHelper)SpringContextHelper.getBean("httpHelper");
			boolean isAjax = ajaxHelper.isAjax(request);
			
			if (!isAjax) {
				
				if (ex instanceof NestedServletException) {
					errorPagePath = GSVS.getResponseStatusAndErrorResource(errorType500);
				} else {
					errorPagePath = GSVS.getResponseStatusAndErrorResource(otherErrors);
				}
				
				mv = new ModelAndView(errorPagePath);
			}
			
			ld.addLog(ex);
			
		} catch(Exception  e) {
			mv = new ModelAndView(GSVS.getResponseStatusAndErrorResource());
			ld.addLog(e);
		}
		
		logger.info(ld.toString());
		
	    return mv;
	}
	
	

}
