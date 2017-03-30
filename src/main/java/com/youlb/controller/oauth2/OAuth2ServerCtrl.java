package com.youlb.controller.oauth2;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/o/oauth2")
public class OAuth2ServerCtrl {
     private String code;
     
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@RequestMapping(value="/doGet",method=RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		OAuthAuthzRequest oauthRequest = null;
		try {
			oauthRequest = new OAuthAuthzRequest(request);
			String redirectUri = oauthRequest.getRedirectURI();
//			 oauthRequest = new OAuthAuthzRequest(request);
			 //验证client_id=wdwe12efds&response_type=code&state=11111&scope=http://baidu.com&redirect_uri=http://192.168.1.231:8080/o/oauth2/doPost
			 
	         //build OAuth response
				 OAuthResponse resp =OAuthASResponse
	             .authorizationResponse( request, HttpServletResponse.SC_FOUND)
	             .setCode(oauthIssuerImpl.authorizationCode())
	             .location(redirectUri)
	             .buildQueryMessage();
	         response.sendRedirect(resp.getLocationUri());
	 
	         //if something goes wrong
	    } catch(OAuthProblemException ex) {
	         
			try {
				oauthRequest = new OAuthAuthzRequest(request);
				OAuthResponse resp = OAuthASResponse
				     .errorResponse(HttpServletResponse.SC_FOUND)
				     .error(ex)
				     .location(ex.getRedirectUri())
				     .buildQueryMessage();
				response.sendRedirect(resp.getLocationUri());
			} catch (OAuthSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthProblemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
	    } catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/doPost",method=RequestMethod.GET)
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
//		OAuthTokenRequest oauthRequest = null;
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		 
		    try {
//		           oauthRequest = new OAuthTokenRequest(request);
//		           oauthRequest.getRedirectURI();
//		           validateClient(oauthRequest);
		 
//		           String authzCode = oauthRequest.getCode();
		           // some code
		           String accessToken = oauthIssuerImpl.accessToken();
		           String refreshToken = oauthIssuerImpl.refreshToken();
		 
		           // some code
		            OAuthResponse r = OAuthASResponse
		                .tokenResponse(HttpServletResponse.SC_OK)
		                .setAccessToken(accessToken)
		                .setExpiresIn("3600")
		                .setRefreshToken(refreshToken)
		                .buildJSONMessage();
		 
		        response.setStatus(r.getResponseStatus());
		        PrintWriter pw = response.getWriter();
		        pw.print(r.getBody());
		        pw.flush();
		        pw.close();
		         //if something goes wrong
		    } catch (OAuthSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
