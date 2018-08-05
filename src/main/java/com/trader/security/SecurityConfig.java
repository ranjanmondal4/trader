package com.trader.security;

import com.trader.repository.AuthTokenRepository;
import com.trader.repository.RolesRepository;
import com.trader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthTokenRepository authTokenRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                authorizeRequests().
                anyRequest().authenticated().
                and().
                anonymous().disable();

        http.addFilterBefore(new AuthenticationFilter(authenticationManagerBean()),
                BasicAuthenticationFilter.class);
    }

    /**
     * configure public urls here
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web ) throws Exception{
        web.ignoring().antMatchers("/trader/api/v1/user/login");
        web.ignoring().antMatchers("/trader/api/v1/marketOrder");

    }


    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new CustomAuthenticationManager(authTokenRepository, rolesRepository,
                userRepository);
    }
}
