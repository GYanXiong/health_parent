package com.itheima.health.service;

import com.alibaba.fastjson.JSONObject;
import com.itheima.health.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSeccessLullyHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JedisPool jedisPool;

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        Jedis redis = jedisPool.getResource();
        redis.del(username);
        redis.close();
        // super.onAuthenticationSuccess(request, response, authentication);
        // super.setDefaultTargetUrl("/pages/main.html");
        System.out.println("执行了");
        JSONObject.writeJSONString(response.getWriter(),new Result(true,"登录成功"));
    }
}
