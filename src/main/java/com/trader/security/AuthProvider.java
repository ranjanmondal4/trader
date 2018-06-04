package com.trader.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.trader.doman.user.User;
import com.trader.repository.AuthTokenRepository;


//@Component
public class AuthProvider implements AuthenticationProvider{
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthProvider.class);
	/*
	@Autowired
	private AuthTokenRepository authTokenRepository;*/
	private AuthenticationService authenticationService;
	/*public AuthProvider(AuthenticationService authenticationService){
		LOGGER.debug("::::::::::::constructor is called");   // findByToken
		this.authenticationService = authenticationService;
	}*/
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LOGGER.debug(":::::::::::::::::::authenticate ::::: "+ (String)authentication.getName()+(String)authentication.getCredentials());
	//	User user=authenticationTokenService.getAuthentication((String)authentication.getPrincipal(),(String)authentication.getCredentials());
		/*User user = new User();
		user.setEmail("mondal@oodlestechnologies.com");
		user.setPassword("ranjan@oodles1");
		UserAuthentication userAuthentication = new UserAuthentication(user);
		AuthenticationWithToken resultOfAuthentication = new AuthenticationWithToken(userAuthentication.getDetails(),null,userAuthentication.getAuthorities());
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userAuthentication.getDetails(), null,
				userAuthentication.getAuthorities();
		*/
		/*	resultOfAuthentication.setToken((String)authentication.getPrincipal());
			return resultOfAuthentication;
		 */
		
		List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>(1);
		AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
	
	    return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
	 
	}

	@Override
	public boolean supports(Class<?> authentication) {
		//return authentication.equals(UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
		//return authentication.equals(PreAuthenticatedAuthenticationToken.class);
		return true;
	}

}
