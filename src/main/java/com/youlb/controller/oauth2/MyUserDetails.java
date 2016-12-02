package com.youlb.controller.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1241586584276708246L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		 SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ABCS");
		  list.add(simpleGrantedAuthority);
		return list;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return "abc";
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return "mobile_1";
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
