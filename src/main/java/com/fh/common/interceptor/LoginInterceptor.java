package com.fh.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.exception.NologinException;
import com.fh.entity.Vip;
import com.fh.util.JWT;
import com.fh.util.RedisPool;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Map;

//登录的拦截器
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws NologinException {
        String login_user = request.getHeader("token");
        if(login_user == null && login_user == "null" && login_user == ""){
                throw new NologinException("头信息不完整");
        }
        byte[] decode = Base64.getDecoder().decode(login_user);
        String tokenAndSign = new String(decode);
        String[] split = tokenAndSign.split(",");
        if(split.length != 2){
                throw new NologinException("头信息被篡改");
        }
            String phone = split[0];
            String sign = split[1];
            Vip unsign = JWT.unsign(sign, Vip.class);
        if(unsign==null){
            //返回json字符串
                throw new NologinException("没有登录");
        }else{
            String redis = RedisPool.getRedis(unsign.getUserPhone()+"_wxwu");
            if(!login_user.equals(redis)){
                    throw new NologinException("验证码过期");
            }else{
                //给redis续命
                RedisPool.setexRedis(unsign.getUserPhone() + "_wxwu",60*30,login_user);
                //放到请求中 为以后的请求做准备
                request.setAttribute("loginUser_wxwu",unsign);
            }
        }
        return true;
    }
}
