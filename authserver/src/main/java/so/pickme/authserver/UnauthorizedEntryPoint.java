package so.pickme.authserver;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import so.pickme.utils.Propertiesimport;


/**
 * {@link AuthenticationEntryPoint} rejects requests with appropriate error message 
 * 
 */

public class UnauthorizedEntryPoint implements AuthenticationEntryPoint
{
	protected final Log logger = LogFactory.getLog(getClass());
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	/*private static final String proxyhosturl = Propertiesimport.getPrx();*/

    private static String proxyHosturl2 = "";

	public UnauthorizedEntryPoint() {
		logger.debug("Setting Up Auth failure  Redirect URL to:"+ Propertiesimport.getPrx());
		proxyHosturl2 = Propertiesimport.getPrx();
		logger.debug("Auth failure  Redirect URL set to:"+ proxyHosturl2);		
	}
	
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException
	{
		String targetUrl = proxyHosturl2+"/uaa/login"  ;
		
		System.out.println("redirecting browser to :" + targetUrl );
		
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
	

}
