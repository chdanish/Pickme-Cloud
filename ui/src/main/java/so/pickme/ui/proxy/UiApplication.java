package so.pickme.ui.proxy;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@EnableRedisHttpSession
@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
/*@ComponentScan({ "demo" })*/
public class UiApplication extends WebSecurityConfigurerAdapter  {
	


	@Autowired
	private ApplicationContext appContext;
	
	private final static String SESSION_SERIALIZATION_ID = "6847625548492548146L";

	@Bean
	public String overwriteSerializationId() {
	    BeanFactory beanFactory = appContext.getAutowireCapableBeanFactory();
	    ((DefaultListableBeanFactory) beanFactory).setSerializationId(SESSION_SERIALIZATION_ID);
	    return "overwritten";
	}
	
	/*@Bean
    public HttpSessionStrategy httpSessionStrategy() {
            return new HeaderHttpSessionStrategy(); 
    }*/
	
	@Primary
	@Bean
	public StringRedisTemplate redisTemplate() {
	    StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory());
	    // explicitly enable transaction support
	    template.setEnableTransactionSupport(true);
	    template.setEnableDefaultSerializer(false);
	    
	    return template;
	  }
	
	@Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        /*jedisConnectionFactory.setHostName("pub-redis-16210.us-east-1-3.6.ec2.redislabs.com");
        jedisConnectionFactory.setPort(16210);
        jedisConnectionFactory.setPassword("pickme");*/
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.logout().deleteCookies("JSESSIONID","SESSION","XSRF-TOKEN").addLogoutHandler(new CustomLogoutHandler()).logoutSuccessUrl("/uaa/logoutall").invalidateHttpSession(true)
		.and().antMatcher("/**").authorizeRequests()
				.antMatchers("/index.html", "/home.html", "/", "/css/**","/js/**","/login","/uaa","/uaa/**","/oauth","/oauth/**","/token","/token/**","/authorize","/authorize/**","/uaa/login","/uaa/login/**").permitAll()
				.anyRequest().authenticated().and().csrf()
				.csrfTokenRepository(csrfTokenRepository()).and()
				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
	}

	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request,
					HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
						.getName());
				if (csrf != null) {
					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
					String token = csrf.getToken();
					if (cookie == null || token != null
							&& !token.equals(cookie.getValue())) {
						cookie = new Cookie("XSRF-TOKEN", token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

}
