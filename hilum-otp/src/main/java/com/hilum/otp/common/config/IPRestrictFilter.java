package com.hilum.otp.common.config;

import com.hilum.otp.common.utils.IPUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IPRestrictFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        boolean isPrivateIp = IPUtils.isPrivateIP(httpServletRequest);
        if(!isPrivateIp) {
            httpServletResponse.setStatus(403);
            httpServletResponse.sendError(403, "ip不在允许范围之内");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
