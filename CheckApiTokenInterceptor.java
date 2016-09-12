package com.mofang.web.support.spring.api;

import com.alibaba.fastjson.JSON;
import com.mofang.common.api.token.TokenHandler;
import com.mofang.web.message.response.JSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CheckApiTokenInterceptor
 *
 * 检查url以api开头的所有访问(除login)的token是否合法
 *
 * @author doob
 * @date 16/9/10
 */
public class CheckApiTokenInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String API_TOKEN_KEY = "api_token_key";
    public static final String API_TOKEN_USER = "api_token_user";

    @Autowired
    TokenHandler th;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (request.getServletPath().contains("api/login")){
            return true;
        }
        String agent = request.getHeader("User-Agent");
        logger.debug(agent);
        String token = request.getHeader(API_TOKEN_KEY);
        String username = request.getHeader(API_TOKEN_USER);
        if (token != null && th.checkToken(username,token)){
            return true;
        }else {
            response.setHeader(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8");
            response.getOutputStream().write(JSON.toJSONBytes(JSONResult.error("auth error")));
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
