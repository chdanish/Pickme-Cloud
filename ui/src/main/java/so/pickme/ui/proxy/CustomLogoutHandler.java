package so.pickme.ui.proxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class CustomLogoutHandler implements LogoutHandler
{
	private final static Logger logger = LoggerFactory.getLogger(CustomLogoutHandler.class);
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
	{
		HttpSession session = request.getSession(false);
		if(session != null)
		{
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			logger.info(session.getId() + " , " + username);
			
			SecurityContextHolder.clearContext();
			
			session.invalidate();
			
			logger.info("User: " + username + " : ----- logout OK -----");
		}
	}

}