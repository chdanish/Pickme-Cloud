/**
 * 
 */
package so.pickme.authserver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.springframework.util.Assert;
/**
 * @author danix
 *
 */
@Configuration
public class LocaleInterceptor implements HandlerInterceptor {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private final CsrfTokenRepository csrfTokenRepository;
	
	

	/**
	 * Creates a new instance
	 * @param csrfTokenRepository the {@link CsrfTokenRepository} to use
	 */
	public LocaleInterceptor(CsrfTokenRepository csrfTokenRepository) {
		Assert.notNull(csrfTokenRepository, "csrfTokenRepository cannot be null");
		this.csrfTokenRepository = csrfTokenRepository;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		HttpServletRequest request = arg0;	
		boolean containsToken = this.csrfTokenRepository.loadToken(request) != null;
		if (containsToken) {
			CsrfToken loadedcsrftoken =this.csrfTokenRepository.loadToken(request);
			System.out.println("My loaded token is COMPLETE : "+loadedcsrftoken.getToken().toString());
		}
		
		CsrfToken csrftoken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		
		
		System.out.println("My req query string COMPLETE : "+request.getQueryString());
		System.out.println("My session ID COMPLETE : "+request.getSession().getId());
		if(csrftoken != null){
		System.out.println("My CSRF COMPLETE : "+csrftoken.getToken().toString());
		}
		Cookie cookie = WebUtils.getCookie(request, "MYSESSION");
		String cookievalue = cookie != null ? cookie.getValue() : "";
		System.out.println("My JSESSIONID PRE"+ cookievalue);
		
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		HttpServletRequest request = arg0;
		
		boolean containsToken = this.csrfTokenRepository.loadToken(request) != null;
		if (containsToken) {
			CsrfToken loadedcsrftoken =this.csrfTokenRepository.loadToken(request);
			System.out.println("My loaded token is POST : "+loadedcsrftoken.getToken().toString());
		}
		
		CsrfToken csrftoken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		
		
		System.out.println("My req query string POST : "+request.getQueryString());
		System.out.println("My session ID POST : "+request.getSession().getId());
		if(csrftoken != null){
		System.out.println("My CSRF POST : "+csrftoken.getToken().toString());
		}
		Cookie cookie = WebUtils.getCookie(request, "MYSESSION");
		String cookievalue = cookie != null ? cookie.getValue() : "";
		System.out.println("My JSESSIONID PRE"+ cookievalue);
		
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = arg0;		
		boolean containsToken = this.csrfTokenRepository.loadToken(request) != null;
		if (containsToken) {
			CsrfToken loadedcsrftoken =this.csrfTokenRepository.loadToken(request);
			System.out.println("My loaded token is PRE : "+loadedcsrftoken.getToken().toString());
		}
		
		CsrfToken csrftoken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		
		
		System.out.println("My req query string PRE : "+request.getQueryString());
		System.out.println("My session ID PRE : "+request.getSession().getId());
		String sessionid = request.getSession().getId();
		
		if(csrftoken != null){
			System.out.println("My CSRF PRE : "+csrftoken.getToken().toString());
		}
		
		
		Cookie cookie = WebUtils.getCookie(request, "MYSESSION");
		String cookievalue = cookie != null ? cookie.getValue() : "";
		System.out.println("My JSESSIONID PRE"+ cookievalue);
		
		return true;
		
	}

}
