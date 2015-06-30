package so.pickme.utils;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service
@PropertySources(value = {@PropertySource("classpath:/clients.properties")})
public class Propertiesimport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6369120969172832528L;
	
	@NotNull
	@Value("${proxyhostip}")
    private String proxy;
	
	private static String prx;
	

	public static String getPrx() {
		return prx;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		Propertiesimport.prx = proxy;
	}

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	
	

}
