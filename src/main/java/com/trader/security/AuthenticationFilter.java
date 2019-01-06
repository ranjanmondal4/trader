package com.trader.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trader.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

public class AuthenticationFilter extends GenericFilterBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
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

        Optional<String> apiKey = Optional.ofNullable(httpRequest.getHeader("Authorization"));
        Optional<String> hmac = Optional.ofNullable(httpRequest.getHeader("hmac"));
        LOGGER.info("Api key :: " + apiKey);
        try {
            if (apiKey.isPresent()) {
                validateAuthToken(apiKey.get(), hmac.get());
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            responseOnError(HttpServletResponse.SC_UNAUTHORIZED, httpResponse, e);
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

    private void validateAuthToken(String apiKey, String hmac) {
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(apiKey, hmac);
        Authentication responseAuthentication = null;
        try {
            responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        } catch (Exception e) {
            //LOGGER.error("Exception : " + e);
            throw new InvalidDataAccessApiUsageException("Invalid Token");
        }
        if (Objects.isNull(responseAuthentication) || !responseAuthentication.isAuthenticated()) {
            //LOGGER.info("Response Authentication : is null or authenticated is false");
            throw new InternalAuthenticationServiceException("Unable to Authenticate");
        }
        SecurityContextHolder.getContext().setAuthentication(responseAuthentication);
    }

    private void responseOnError(int status, HttpServletResponse httpResponse, Exception e) {
        SecurityContextHolder.clearContext();
        httpResponse.setStatus(status);
        httpResponse.addHeader("Content-Type", "application/json");
        Response<Object> customResponse = new Response<Object>(false, "Unauthorized Request", null, e);
        customResponse.setStatus(status);
        ObjectMapper mapper = new ObjectMapper();
        try {
            PrintWriter writer = httpResponse.getWriter();
            writer.write(mapper.writeValueAsString(customResponse));
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            LOGGER.error("Exception in Response Object : " + exception);
        }
    }
}
