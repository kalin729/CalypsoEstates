package com.kalinkrumov.calypsoestates.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class IPBlacklistInterceptor implements HandlerInterceptor {

    private List<String> blacklistedIPAddresses = new ArrayList<>();

    public IPBlacklistInterceptor() {
//        blacklistedIPAddresses.add("0:0:0:0:0:0:0:1");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
//        System.out.println(ipAddress);
        if (blacklistedIPAddresses.contains(ipAddress)){
            response.sendRedirect("/error");
        }
        return true;
    }
}
