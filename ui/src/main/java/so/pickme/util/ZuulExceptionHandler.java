package so.pickme.util;

import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.netflix.zuul.exception.ZuulException;

@ControllerAdvice
public class ZuulExceptionHandler {
	Logger logger = LoggerFactory.getLogger(ZuulExceptionHandler.class);
	
	@ExceptionHandler(ZuulException.class)
	public void conflict() {
		logger.error("ZUUL EXCEPTION HANDLER");
		// Nothing to do
	}

	@ExceptionHandler(SocketTimeoutException.class)
	public void timeout() {
		logger.error("ZUUL EXCEPTION HANDLER");
		// Nothing to do
	}
}
