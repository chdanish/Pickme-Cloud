package so.pickme.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Facebookauth {


	protected final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping("/facebookauth")
	public String dashboard(HttpServletRequest request) {
		
		  
		  HttpSession session = request.getSession(false);
		    if (request.isRequestedSessionIdValid() && session != null) {
		    	logger.debug("Invalidate Authserver session from facebookauth controller of UI server ");
		    	session.invalidate();
		    }
		
		return "redirect:/login/facebook";
	}
	
	@RequestMapping("/login")
	public String loginredirect(HttpServletRequest request) {
		
		return "redirect:/";
	}

}