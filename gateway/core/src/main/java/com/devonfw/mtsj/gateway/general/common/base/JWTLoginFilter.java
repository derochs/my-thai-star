package com.devonfw.mtsj.gateway.general.common.base;

import com.devonfw.mtsj.gateway.general.common.api.security.BasicAccountCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JWTLoginFilter.class);

    private UserDetailsService userDetailsService;

    public JWTLoginFilter(String url, AuthenticationManager authManager, UserDetailsService userDetailsService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        BasicAccountCredentials creds = new ObjectMapper().readValue(httpServletRequest.getInputStream(), BasicAccountCredentials.class);
        UserDetails user = this.userDetailsService.loadUserByUsername(creds.getUsername());
        ValidationService.validateCredentials(creds);
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), user.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        TokenAuthenticationService.addAllowedHeader(response);
        TokenAuthenticationService.addAuthentication(response, authResult);
        //TokenAuthenticationService.addRequiredAuthentication(response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse res, AuthenticationException failed) {
        LOG.info("Authentication was unsuccessful");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
