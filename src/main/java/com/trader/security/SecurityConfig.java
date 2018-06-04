package com.trader.security;

import com.trader.repository.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private AuthTokenRepository authTokenRepository;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                authorizeRequests().
                anyRequest().authenticated().
                //antMatchers(actuatorEndpoints()).hasRole(backendAdminRole).
                and().
                anonymous().disable()
                ;
                //exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());

      http.addFilterBefore(new AuthenticationFilter(authenticationManagerBean()), BasicAuthenticationFilter.class);
    }
    
    // public urls
    @Override
	public void configure(WebSecurity web ) throws Exception{
		web.ignoring().antMatchers("/trader/api/v1/user/login");
		// enabling for swagger
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
                "/swagger-resources", "/swagger-resources/configuration/security",
                "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui");

	}


    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new CustomAuthManager(authTokenRepository);
    }
}
