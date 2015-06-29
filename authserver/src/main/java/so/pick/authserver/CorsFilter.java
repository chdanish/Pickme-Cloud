package so.pick.authserver;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CorsFilter implements Filter {
	
	protected final Log logger = LogFactory.getLog(getClass());
    
    @Value("${allowedHosts}")
    private String allowedHosts;
    String lookingforheader="Origin";

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			System.out.println("key: "+key+" |value: "+value);
			
			
			
			 if (key=="Origin") {
		            logger.debug("Origin header already set to " + "key: "+key+" |value: "+value);
		            lookingforheader="";
		            
		        } 
		}//end while
		
		if (lookingforheader!="Origin") {
			setOrigin(response);
		}
	    
		chain.doFilter(req, res);
		
	}

	private void setOrigin( HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}
