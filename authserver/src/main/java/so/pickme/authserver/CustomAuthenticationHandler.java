package so.pickme.authserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import so.pickme.utils.Propertiesimport;

@Component
public class CustomAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 
	protected final Log logger = LogFactory.getLog(this.getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();
    
    String targetUri = "/uaa/oauth/authorize";
    
    
    private static final String proxyhosturl = "http://localhost:8080";
    
  
    
    //http://localhost:7272/uaa/oauth/authorize?client_id=acme&redirect_uri=http://localhost:9999/sso/dashboard/login&response_type=code&state=HanuNa

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }
        String targetUrlParameter = this.getTargetUrlParameter();
        System.out.println("targetUrlParameter:"+targetUrlParameter);
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        
        String targetUrl = savedRequest.getRedirectUrl();

        System.out.println("ORIGNAL URL: "+targetUrl);
        String[] targeturlArray=     targetUrl.split(targetUri);
        System.out.println("After split URL: "+targeturlArray[1]);
		targetUrl= proxyhosturl + targetUri + targeturlArray[1];
		System.out.println("MY LOGIN SUCCESS HANDLER");
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        /*targetUrl=targetUrl.replace("login","");*/
        if (targetUrl.contains("http://localhost:9999")) {
			System.out.println("Local client match found:http://localhost:9999");
			targetUrl=targetUrl.replace("http://localhost:9999",proxyhosturl);
		/*	.replace("http://localhost:9999",proxyhosturl );*/
		}
        System.out.println("Final target URL befor redirect: " + targetUrl );
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
//http://thinkinginsoftware.blogspot".com/2011/07/redirect-after-login-to-requested-page.html
// merge with http://www.baeldung.com/spring_redirect_after_login