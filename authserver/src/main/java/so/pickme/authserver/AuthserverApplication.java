package so.pickme.authserver;


import java.security.KeyPair;
import java.security.Principal;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/*import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;*/
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import so.pickme.utils.Propertiesimport;



//@EnableRedisHttpSession
@SpringBootApplication
@Controller
@SessionAttributes("authorizationRequest")
@EnableResourceServer
@ComponentScan({"so.pickme"})
@RestController
public class AuthserverApplication extends WebMvcConfigurerAdapter {
	
	@Autowired
	private ApplicationContext appContext;
	
/*	@Bean
	public ServletContextInitializer servletContextInitializer() {
	    return new ServletContextInitializer() {

			@Override
			public void onStartup(ServletContext sc) throws ServletException {
				sc.addServlet("mvc-dispatcher", org.springframework.web.servlet.DispatcherServlet.class).addMapping("/mxx");
				sc.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.URL));
				sc.addListener(new SessionListener());
				//we need session event listner to set session logging and session timeout
				
			}

	    };

	}*/	
	

	
	
	
/*	@Bean
	public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
		return new HttpSessionSecurityContextRepository() {			
			@Override
			public void setDisableUrlRewriting(boolean disableUrlRewriting) {
				 TO Disable JSESSIONID where jsessionid is specific to ServletContext
				 * Use SESSIONID instead as we are using REdIS session which replace 
				 * container(e.g tomcat) session with Spring Session
				 * we should use Spring session as default across all apps
				
				super.setDisableUrlRewriting(true);
			}
		};
		
	}*/
	
	
	    @Bean
	    public LocaleInterceptor localeInterceptor() {
	        return new LocaleInterceptor(csrfTokenRepository());
	    }
	    
//	    @Bean
//	    public static CorsFilter corsfilter() {
//			return new CorsFilter();
//	    	
//	    }
	    
	    @Bean
	    public static CsrfTokenRepository csrfTokenRepository() {
	    	HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	        repository.setHeaderName("X-XSRF-TOKEN");
	        return repository;
	    }

	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor( localeInterceptor() );
	    }
	    
	
	private final static String SESSION_SERIALIZATION_ID = "6847625548492548146L";

/*	@Bean
	public String overwriteSerializationId() {
	    BeanFactory beanFactory = appContext.getAutowireCapableBeanFactory();
	    ((DefaultListableBeanFactory) beanFactory).setSerializationId(SESSION_SERIALIZATION_ID);
	    return "overwritten";
	}*/
	
	/*@Bean
	public static RefreshScope refreshScope() {
	    RefreshScope refresh = new RefreshScope();
	    refresh.setId("pickme:1"); // setup in application.properties
	    return refresh;
	}*/
	
/*	@Primary
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
        SessionRepository<? extends ExpiringSession> repository =
                new RedisOperationsSessionRepository(jedisConnectionFactory);
        
        jedisConnectionFactory.setHostName("pub-redis-16210.us-east-1-3.6.ec2.redislabs.com");
        jedisConnectionFactory.setPort(16210);
        jedisConnectionFactory.setPassword("pickme");
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }*/

	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/oauth/confirm_access").setViewName("authorize");
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}

	@Configuration
	@Order(-20)
	protected static class LoginConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Autowired
		private Propertiesimport pimport;

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			System.out.println("Fetching proxy address... :"+pimport.getProxy());
			pimport.setProxy(pimport.getProxy());
			System.out.println("Fixing proxy address... :" + Propertiesimport.getPrx());
			
			// @formatter:off
			http
			//.addFilterBefore(corsfilter(), WebAsyncManagerIntegrationFilter.class)
			//.csrf().ignoringAntMatchers("/login").and()
			.exceptionHandling()
			.authenticationEntryPoint(new UnauthorizedEntryPoint())
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logoutall")).invalidateHttpSession(true)
			.and()
				.formLogin().loginPage("/login").permitAll()
				//.successHandler(new CustomAuthenticationHandler())
				.failureUrl("http://localhost:8080/uaa/login")
			.and()
				.requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
			.and()
				.authorizeRequests().anyRequest().authenticated()
				.and().addFilterAfter(new CsrfFilter(csrfTokenRepository()), CsrfFilter.class)
	            .csrf().csrfTokenRepository(csrfTokenRepository()).and()
				;
			// @formatter:on
		
		}
		
		
		

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.parentAuthenticationManager(authenticationManager);
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2AuthorizationConfig extends
			AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter() {
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			KeyPair keyPair = new KeyStoreKeyFactory(
					new ClassPathResource("keystore.jks"), "foobar".toCharArray())
					.getKeyPair("test");
			converter.setKeyPair(keyPair);
			return converter;
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
			        .withClient("acme")
			            .authorizedGrantTypes("authorization_code", "password")
			            .authorities("ROLE_CLIENT")
			            .scopes("openid","read", "trust")
			            .resourceIds("oauth2-resource","oauth2-resource-mqtt")
			            .redirectUris("http://localhost:8080")
			            .secret("acmesecret");
					
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.authenticationManager(authenticationManager).accessTokenConverter(
					jwtAccessTokenConverter());
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer)
				throws Exception {
			oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
					"isAuthenticated()");
		}

	}
}