package indi.shuyu.system.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.initialization.GlobalStaticValues;

@Controller
@RequestMapping("/")
public class ErrorController { 
	
	@RequestMapping(value= "errors", method = RequestMethod.GET)
	public ModelAndView returnErrorPage(HttpServletRequest httpRequest) {
		
		GlobalStaticValues GSVS = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		int httpErrorCode = getErrorCode(httpRequest);
	    String errorPagePath =  GSVS.getResponseStatusAndErrorResource(httpErrorCode + "");
	    
	    ModelAndView mv = null;
	    
	    if (errorPagePath == null) {
	    	mv = new ModelAndView();
	    } else {
	    	mv = new ModelAndView(errorPagePath);
	    }
        
        return mv;
    }
	
	private int getErrorCode(HttpServletRequest httpRequest) {
	    return (Integer) httpRequest
	           .getAttribute("javax.servlet.error.status_code");
	}
}
