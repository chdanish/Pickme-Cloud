/*package so.pickme.utils;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

*//**
 * Extends {@link CustomizableTraceInterceptor} to provide custom logging levels
 *//*

@Configuration
@ImportResource("classpath:/trace-context.xml")
public class TraceInterceptor extends CustomizableTraceInterceptor  {


	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1439758774010083992L;
	protected static Logger logger4J = Logger.getLogger("aop");

	@Override
	protected void writeToLog(Log logger, String message, Throwable ex) {
		if (ex != null) {
			logger4J.debug(message, ex);
		} else {
			logger4J.debug(message);
		}
	}

	@Override
	protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
		return true;
	}
}
*/