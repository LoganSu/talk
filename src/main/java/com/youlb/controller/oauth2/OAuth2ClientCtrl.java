package com.youlb.controller.oauth2;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;


public class OAuth2ClientCtrl {
	
   public void client() throws OAuthSystemException{
	   OAuthClientRequest request = OAuthClientRequest
			   .authorizationProvider(OAuthProviderType.FACEBOOK)
			   .setClientId("your-facebook-application-client-id")
			   .setRedirectURI("http://www.example.com/redirect")
			   .buildQueryMessage();
   }
   
}
