/*package so.pickme.authserver;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.ServletContext;

public class Addfilter extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected String getDispatcherWebApplicationContextSuffix() {
		return AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;
	}

 	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		insertFilters(servletContext,new CorsFilter());
	}

	@Override
	protected boolean enableHttpSessionEventPublisher() {
		return true;
	}
}*/