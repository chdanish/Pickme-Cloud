package so.pickme.authserver.SessionConfig;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
 

@Configuration
public class SessionListener implements HttpSessionListener {
 
	
	protected final Log logger = LogFactory.getLog(getClass());
	
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("==== Session is created ====");
        logger.debug("==== Session is created ====");
        event.getSession().setMaxInactiveInterval(5*60);
    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    	 logger.debug("==== Session is destroyed ====");
        System.out.println("==== Session is destroyed ====");
    }
}
