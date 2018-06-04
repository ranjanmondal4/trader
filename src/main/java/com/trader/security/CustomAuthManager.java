package com.trader.security;

import com.trader.doman.AuthToken;
import com.trader.repository.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomAuthManager implements AuthenticationManager{
    private AuthTokenRepository authTokenRepository;
    public CustomAuthManager(AuthTokenRepository authTokenRepository){
        this.authTokenRepository = authTokenRepository;
    }
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>(1);
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(Optional.ofNullable(authentication.getPrincipal()).isPresent()){
            String token =(String) authentication.getPrincipal();
            AuthToken authToken = authTokenRepository.findByToken(token);
            if(authToken != null){
                return new UsernamePasswordAuthenticationToken(authToken.getUser().getEmail(), authToken.getToken(), AUTHORITIES);
            }
        }
        return null;
    }
}
