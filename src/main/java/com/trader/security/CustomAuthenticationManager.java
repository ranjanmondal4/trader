package com.trader.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.trader.domain.AuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.trader.domain.user.User;
import com.trader.enums.Role;
import com.trader.repository.AuthTokenRepository;
import com.trader.repository.RolesRepository;
import com.trader.repository.UserRepository;

public class CustomAuthenticationManager implements AuthenticationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationManager.class);
    private static final String HASH_FUNCTION = "HmacSHA256";
    private static final String SECRET_KEY = "security1bel4";

    private AuthTokenRepository authTokenRepository;

    private RolesRepository rolesRepository;

    private UserRepository userRepository;

    public CustomAuthenticationManager(AuthTokenRepository authTokenRepository,
                                       RolesRepository rolesRepository, UserRepository userRepository){
        this.authTokenRepository = authTokenRepository;
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        if(isApiKeyAndHmacPresent(authentication)){
            String apiKey = (String) authentication.getPrincipal();
            String hmac = (String) authentication.getCredentials();

            User user = userRepository.findByApiKey(apiKey);
            if(Objects.isNull(user)){
                LOGGER.info("user is null");
                return null;
            }


            LOGGER.info("ApiKey : " + apiKey + ",  hmac : " + hmac);
            String generatedHmac = generateHMAC(apiKey, SECRET_KEY);
            LOGGER.info("Hmac generated by app : " + generatedHmac);
            if(generatedHmac.equals(hmac)){
                LOGGER.info("generated hmac == hmac");
                List<Role> roles = rolesRepository.findRoleByUser(user);
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(1);
                roles.stream().forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role.name()));
                });
                return new UsernamePasswordAuthenticationToken(apiKey, hmac, authorities);
            }
            LOGGER.info("generated hmac != hmac");
            AuthToken authToken = authTokenRepository.findByToken(apiKey);
            if(authToken != null){
                return new UsernamePasswordAuthenticationToken(authToken.getUser().getEmail(), authToken.getToken(),  null);
            }
        }
            LOGGER.info("Come here -------------");
            return null;
        //return null;
    }

    public boolean isApiKeyAndHmacPresent(Authentication authentication){
        return Optional.ofNullable(authentication.getPrincipal()).isPresent();
    }

    public String generateHMAC(String data, String secretKey) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance(HASH_FUNCTION);
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), HASH_FUNCTION);
            sha256_HMAC.init(secret_key);
           // hash = Base64.encodeBase64String(sha256_HMAC.doFinal(data.getBytes()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return hash;
    }
}

