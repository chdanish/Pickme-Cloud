package so.pickme.ui.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.util.WebUtils;

@EnableRedisHttpSession
@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
@ComponentScan({ "so.pickme.ui" })
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
	
	/*public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/facebookauth").setViewName("facebookauth");
	}*/
	
	
	
	
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
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(
			OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	/*@Bean
	@ConfigurationProperties("github")
	ClientResources github() {
		return new ClientResources();
	}*/

	@Bean
	@ConfigurationProperties("facebook")
	ClientResources facebook() {
		return new ClientResources();
	}

	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(facebook(), "/login/facebook"));
		/*filters.add(ssoFilter(github(), "/login/github"));*/
		filter.setFilters(filters);
		return filter;
	}
	

	@Bean
    SessionAuthenticationStrategy sas() {
        return new SessionFixationProtectionStrategy();
    }

	private Filter ssoFilter(ClientResources client, String path) {
		
		OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
				path);
		OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(client.getClient(),
				oauth2ClientContext);
		facebookFilter.setRestTemplate(facebookTemplate);
		facebookFilter.setTokenServices(new UserInfoTokenServices(
				client.getResource().getUserInfoUri(), client.getClient().getClientId()));
		facebookFilter.setSessionAuthenticationStrategy(sas());
		return facebookFilter;
	}

	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		
		
		
		
		http.logout().deleteCookies("JSESSIONID","SESSION","XSRF-TOKEN").addLogoutHandler(new CustomLogoutHandler()).logoutSuccessUrl("/login").invalidateHttpSession(true)
		.and().antMatcher("/**").authorizeRequests()
				.antMatchers("/index.html", "/home.html", "/", "/css/**","/js/**","/login","/facebookauth",
						"/uaa","/uaa/**","/oauth","/oauth/**","/token","/token/**","/authorize","/authorize/**","/uaa/login","/uaa/login/**").permitAll()
				.anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
				.and().csrf()
				.csrfTokenRepository(csrfTokenRepository()).and()
				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
				;
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
