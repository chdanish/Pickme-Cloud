/*package so.pickme.ui.proxy;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;


@Component
public class Logouthdlr implements LogoutHandler{
	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			System.out.println("key: "+key+" |value: "+value);
			
			
			
			 if (key=="Origin") {
		            logger.debug("Origin header already set to " + "key: "+key+" |value: "+value);
		       
		            
		        } 
		}//end while


		 HttpSession session = request.getSession(false);
		    if (request.isRequestedSessionIdValid() && session != null) {

		      session.invalidate();
		    }
		    handleLogOutResponse(request, response);
		  }
		  
		  private void handleLogOutResponse(HttpServletRequest request, HttpServletResponse response) {
		    Cookie[] cookies = request.getCookies();
		    for (Cookie cookie : cookies) {
		      cookie.setMaxAge(0);
		      cookie.setValue("");
		      cookie.setPath("/");
		      response.addCookie(cookie);
		    }

		
	}

}
*/