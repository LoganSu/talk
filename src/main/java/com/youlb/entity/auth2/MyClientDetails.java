package com.youlb.entity.auth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.youlb.entity.common.BaseModel;

@Entity
@Table(name="oauth_client_details")
public class MyClientDetails extends BaseModel implements ClientDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8438947696227080843L;
	
	/**模型编号*/
	@Id
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(length=32,name="client_id")
	private String clientId;
    
    @Column(name="client_secret")
	private String clientSecret;
    
    @Column(name="scope")
	private String scope;

    
	public MyClientDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyClientDetails(String clientId,String clientSecret,String scope){
		this.clientId=clientId;
		this.clientSecret=clientSecret;
		this.scope=scope;
		
	}
	
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Override
	public String getClientId() {
		// TODO Auto-generated method stub
		return this.clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSecretRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getClientSecret() {
		// TODO Auto-generated method stub
		return this.clientSecret;
	}

	@Override
	public boolean isScoped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> getScope() {
		Set<String> scopes = new HashSet<String>();
		scopes.add(this.scope);
		return scopes;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return null;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		 List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		 SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ABCS");
		  list.add(simpleGrantedAuthority);
		return list;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	 

}
