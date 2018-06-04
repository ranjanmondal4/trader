package com.trader.security;

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
	/*@Value("${backend.admin.role}")
    private String backendAdminRole;
*/
	/*@Autowired
	private AuthProvider authProvider;
	
	*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                authorizeRequests()
                .anyRequest().authenticated()
                //antMatchers(actuatorEndpoints()).hasRole(backendAdminRole).
                .and()
                .anonymous().disable()
                ;
                //exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());

      http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
      /*            addFilterBefore(new ManagementEndpointAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);*/
    }
    
    // public urls
    @Override
	public void configure(WebSecurity web ) throws Exception{
		web.ignoring().antMatchers("/trader/api/v1/user/login");
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**","/services.app/swagger-resources/configuration/security");

	}
    
    /*@Bean
    public AuthenticationProvider getAuthenticationProvider() {
        return authProvider;
    }*/
    
    
    /*@Bean
	public AuthenticationService tokenService(){
		return new AuthenticationService();
	}
	*/
	
	@Bean
    public AuthenticationProvider usernamePasswordAuthenticationProvider() {
        return new AuthProvider();
    }
	
	@Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	System.out.println(":::::::inside auth builder");
        auth.authenticationProvider(usernamePasswordAuthenticationProvider())
        	.authenticationProvider(tokenAuthenticationProvider());
    }
    
    /*@Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager();
    }*/
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth){
    	auth.authenticationProvider(usernamePasswordAuthenticationProvider())
        	.authenticationProvider(tokenAuthenticationProvider());
   
    }
}
