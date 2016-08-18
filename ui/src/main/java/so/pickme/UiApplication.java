package so.pickme;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
/*import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;*/
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.zuul.constants.ZuulConstants;

import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.SimpleHostRoutingFilter;

//@EnableRedisHttpSession
@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
@EnableZuulProxy
public class UiApplication {
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
	
/*	@Bean
	public EmbeddedServletContainerFactory servletContainerFactory() {
	    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();

	    factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
	        @Override
	        public void customize(Connector connector) {
	        	ProtocolHandler handler = connector.getProtocolHandler();
				if (handler instanceof AbstractProtocol) {
					@SuppressWarnings("rawtypes")
					AbstractProtocol protocol =  (AbstractProtocol) handler;
					protocol.setConnectionTimeout(100000);

				}
	        }
	    });

	    // configure some more properties

	    return factory;
	}*/
	
/*	@Autowired
	private ApplicationContext appContext;
	
	private final static String SESSION_SERIALIZATION_ID = "6847625548492548146L";

	@Bean
	public String overwriteSerializationId() {
	    BeanFactory beanFactory = appContext.getAutowireCapableBeanFactory();
	    ((DefaultListableBeanFactory) beanFactory).setSerializationId(SESSION_SERIALIZATION_ID);
	    return "overwritten";
	}
	
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
        SessionRepository<? extends ExpiringSession> repository =
                new RedisOperationsSessionRepository(jedisConnectionFactory);

        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
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
		

		/*@Bean
	    SessionAuthenticationStrategy sas() {
	        return new SessionFixationProtectionStrategy();
	    }*/

		private Filter ssoFilter(ClientResources client, String path) {
			
			OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
					path);
			OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(client.getClient(),
					oauth2ClientContext);
			facebookFilter.setRestTemplate(facebookTemplate);
			facebookFilter.setTokenServices(new UserInfoTokenServices(
					client.getResource().getUserInfoUri(), client.getClient().getClientId()));
			//facebookFilter.setSessionAuthenticationStrategy(sas());
			return facebookFilter;
		}
		

		

	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}

	@Configuration
	@EnableOAuth2Sso
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		AuthenticationEntryPoint authentry = (req,res,auth)->{res.sendRedirect("http://localhost:8080/login");return;};
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.exceptionHandling()
				.authenticationEntryPoint(authentry)
			.and()
				.authorizeRequests()
					.antMatchers("/index.html", "/home.html").permitAll()
					.anyRequest().authenticated()
			.and()
				.csrf().csrfTokenRepository(csrfTokenRepository())
			.and()
				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
			    .addFilterBefore(corsfilter(), CsrfFilter.class)
				;
			// @formatter:on
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
		
		private void setOrigin( HttpServletResponse response) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		}
		
		private Filter corsfilter() {			
			return new OncePerRequestFilter() {
				@Override
				protected void doFilterInternal(HttpServletRequest request,
						HttpServletResponse response, FilterChain filterChain)
						throws ServletException, IOException {
					
					setOrigin(response);
				
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

}