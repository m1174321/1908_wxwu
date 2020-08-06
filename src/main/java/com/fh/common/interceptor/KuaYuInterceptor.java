package com.fh.common.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KuaYuInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置response
        // 允许跨域访问的域名：若有端口需写全（协议+域名+端口），若没有端口末尾不用加'/'
        //response.setHeader("Access-Control-Allow-Origin", "http://www.domain1.com");
        //获取请求的域名
        String yuming = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", yuming);
        //允许session //是否支持cookie跨域
        /*response.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials","true");*/
        String method = request.getMethod();
        if(method.equalsIgnoreCase("OPTIONS")){
            response.setHeader("Access-Control-Allow-Headers","token");
            return false;
        }
        String name = request.getHeader("token");
        return true;
    }
}
