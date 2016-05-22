package so.pickme.ui.Service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import so.pickme.ui.Service.Accesstoken;

@Service
public class AccesstokenImpl implements Accesstoken{
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
    private OAuth2ClientContext oAuth2ClientContext;
	
	
	
	public String extractaccesstoken (){
		try {
		
		OAuth2AccessToken existingToken = oAuth2ClientContext
				.getAccessToken();
		
		return existingToken.getValue();
		}
		catch (Exception ex) {
			this.logger.info("Could not fetch facebook user details: " + ex.getClass() + ", "
					+ ex.getMessage());
			return null;
		}
	}



}
