package com.trader.security;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean{
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
	private AuthenticationManager authenticationManager;

	// auto-wired spring authentication manager
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
    	//LOGGER.info(">>>>>>>>>>>>>>>>>>>>>Inside Authentication Filter " + authenticationManager);
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        setHttpResponseHeader(httpResponse);
		
		if (("OPTIONS").equals(httpRequest.getMethod())) {
			httpResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}

		Optional<String>  token = Optional.ofNullable(httpRequest.getHeader("Authorization")) ;
		//LOGGER.info("Authentication token :: "+token);
	
		try {
			if (token.isPresent()) {
				validateAuthToken(token.get());
			}/*else {
				LOGGER.info("Authtoken present : " + token.isPresent());
			}*/
			chain.doFilter(request, response);
		} catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
			//LOGGER.error("Internal authentication service exception", internalAuthenticationServiceException);
			//httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			responseOnError(HttpServletResponse.SC_UNAUTHORIZED, httpResponse, httpRequest);
		} catch (AuthenticationException authenticationException) {
			//LOGGER.error("Unauthorized Exception :: ");
			// return on token not found.
			responseOnError(HttpServletResponse.SC_UNAUTHORIZED, httpResponse, httpRequest);
			//httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} catch (Exception e) {
			//LOGGER.error("Exception: " + e);
			responseOnError(HttpServletResponse.SC_UNAUTHORIZED, httpResponse, httpRequest);
		}
	}

    private void setHttpResponseHeader(HttpServletResponse httpResponse){
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		httpResponse.addHeader("Access-Control-Max-Age", "3600");
		httpResponse.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		httpResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,*");
		httpResponse.addHeader("Connection", "close");
    }
    
	private void validateAuthToken(String token) {
		PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);
		Authentication responseAuthentication = null;
		try {
			responseAuthentication = authenticationManager.authenticate(requestAuthentication);
		} catch (Exception e) {
			//LOGGER.error("Exception : " + e);
			throw new InvalidDataAccessApiUsageException("Invalid token");
		}
		if (Objects.isNull(responseAuthentication) || !responseAuthentication.isAuthenticated()) {
			//LOGGER.info("Response Authentication : is null or authenticated is false");
			throw new InternalAuthenticationServiceException("Unable to Authenticate");
		}
		SecurityContextHolder.getContext().setAuthentication(responseAuthentication);
	}

	private boolean responseOnError(Integer status, HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
		SecurityContextHolder.clearContext();
		LOGGER.info("Response On Error >>>>>");
		httpResponse.setStatus(status);
		httpResponse.addHeader("Content-Type", "application/json");
		try {
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
		} catch (IOException e) {
			LOGGER.error("Exception in Response Object : " + e);
		}
		return false;
	}
}
