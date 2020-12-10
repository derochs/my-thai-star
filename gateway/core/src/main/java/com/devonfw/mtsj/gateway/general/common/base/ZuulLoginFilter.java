package com.devonfw.mtsj.gateway.general.common.base;

import com.devonfw.module.security.common.api.authentication.AdvancedAuthentication;
import com.devonfw.mtsj.gateway.general.common.api.security.BasicAccountCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Named
@Component
public class ZuulLoginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }
    @Override
    public int filterOrder() {
        //return PRE_DECORATION_FILTER_ORDER - 1;
        return 7;
    }
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //return !ctx.containsKey(FORWARD_TO_KEY) && !ctx.containsKey(SERVICE_ID_KEY); // a filter has already determined serviceId
        HttpServletRequest request = ctx.getRequest();
        String contextPath = request.getServletPath();
        return contextPath.matches("/api/.*/login");
        //return contextPath.matches("/api/.*");
    }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();
        try {
            BasicAccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), BasicAccountCredentials.class);
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/auth/realms/mts/protocol/openid-connect/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("client_id", "mts")
                    .field("username", creds.getUsername())
                    .field("password", creds.getPassword())
                    .field("grant_type", "password")
                    // client secret in Keycloak -> Clients -> Mts -> Credentials
                    .field("client_secret", "c9735d5e-c888-4152-8c6a-8883cc851e7d")
                    .asJson();
            if(response.getStatus() == 200) {
                String jwt = response.getBody().getObject().get("access_token").toString();
                ctx.addZuulResponseHeader("hello", "test");
                ctx.addZuulResponseHeader(HttpHeaders.AUTHORIZATION, "Bearer" + " " + jwt);
            }
        } catch (IOException | UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }
}