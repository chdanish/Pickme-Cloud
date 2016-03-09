package so.pickme.ui.controller;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import so.pickme.ui.Service.Accesstoken;
import so.pickme.ui.proxy.ClientResources;
import so.pickme.ui.proxy.Tokenextractor;

@RestController
public class Checkuser {
	
	@Autowired
	private Accesstoken accesstoken;
	
	@Autowired
	private ApplicationContext appContext;

	@RequestMapping({ "/userx", "/me" })
	public Map<String, Object> user(Principal principal,OAuth2Authentication auth) {
		 
		String clientid = auth.getOAuth2Request().getClientId();
		 Map<String, Object> map = new LinkedHashMap<>();
		
		// facebook client ID is 1850196011872870
		if (clientid.equals("1850196011872870")){
			System.out.println("Found clientID for facebook: "+ clientid);
			Facebook facebook = new FacebookTemplate(accesstoken.extractaccesstoken());
			 User user = facebook.userOperations().getUserProfile();
			
			 if (user != null) {
				 map.put("facebook-user",  user);
					
			}
			
		}		
		 
		 System.out.println("myOauth2-token: "+ accesstoken.extractaccesstoken());		 
		 
		map.put("name", principal);
		
		
		return map;
	}	

}

