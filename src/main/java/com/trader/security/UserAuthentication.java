package com.trader.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.trader.doman.user.User;

public class UserAuthentication implements Authentication {
	
	private static final long serialVersionUID = 1L;
	private final User user;
	private boolean authenticated=true;
	
	public UserAuthentication(User user) {
		this.user = user;
	}
	

	@Override
	public String getName() {
		String email = Objects.nonNull(user) ? user.getEmail() : "No Name";
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList=new ArrayList<>();
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authList;
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public Object getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user.getEmail();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
		this.authenticated=authenticated;
	}
	public User getCurrentUser(){
		return user;
	}
}