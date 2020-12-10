package com.devonfw.mtsj.gateway.general.common.base;

import com.devonfw.module.security.common.api.authentication.AdvancedAuthentication;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Named
@Component
public class ZuulHeaderFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }
    @Override
    public int filterOrder() {
        //return PRE_DECORATION_FILTER_ORDER - 1;
        return 10;
    }
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //return !ctx.containsKey(FORWARD_TO_KEY) && !ctx.containsKey(SERVICE_ID_KEY); // a filter has already determined serviceId
        // RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String contextPath = request.getServletPath();
        //return contextPath.matches("/api/.*") && !contextPath.matches("/api/mythaistar/login");
        return false;
    }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        AdvancedAuthentication authentication = AdvancedAuthentication.get();
        if (authentication != null) {
            String jwt = authentication.getCredentials().toString();
            ctx.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, "Bearer:" + " " + jwt);
        }
        return null;
    }
}