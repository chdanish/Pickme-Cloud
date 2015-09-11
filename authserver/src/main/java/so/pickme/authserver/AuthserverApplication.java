package so.pickme.authserver;

import java.security.KeyPair;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import so.pickme.utils.Propertiesimport;

@SpringBootApplication
@EnableRedisHttpSession
@Controller
@ComponentScan({ "so.pickme" })
@ImportResource({"classpath:trace-context.xml"})
@SessionAttributes("authorizationRequest")
@EnableResourceServer
public class AuthserverApplication extends WebMvcConfigurerAdapter {

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
	@Order(-10)
	protected static class LoginConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;
		
		/*@Autowired
		SampleAuthenticationManager sampleAuthenticationManager;*/
		
		
		@Autowired
		private Propertiesimport pimport;
		

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			System.out.println("Fetching proxy address... :"+pimport.getProxy());
			pimport.setProxy(pimport.getProxy());
			System.out.println("Fixing proxy address... :" + Propertiesimport.getPrx());
			
			// @formatter:off
			http
			.exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint())
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logoutall")).invalidateHttpSession(true)
			.and()
				.formLogin().loginPage("/login").permitAll()
				.successHandler(new CustomAuthenticationHandler())
				.failureUrl("http://localhost:8080/uaa/login")
			.and()
				.requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
			.and()
				.authorizeRequests().anyRequest().authenticated();
			// @formatter:on
		}

		@Autowired
		UserDetailsService userDetailsService;
		
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth
		/*	.parentAuthenticationManager(authentication -> new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
					authentication.getCredentials(), Lists.newArrayList(new SimpleGrantedAuthority("USER_ROLE"))));*/

			.parentAuthenticationManager(authenticationManager)
			/*.userDetailsService(userDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder())*/
			
				;
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
					.secret("acmesecret")
					.authorizedGrantTypes("authorization_code", "refresh_token",
							"password").scopes("openid");
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints
			.authenticationManager(authenticationManager).accessTokenConverter(
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
