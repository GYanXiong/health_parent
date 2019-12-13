package com.itheima.health.service;

import com.alibaba.fastjson.JSONObject;
import com.itheima.health.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class LoginFailedHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private JedisPool jedisPool;

    //登录失败处理
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        Jedis jedis = jedisPool.getResource();
        String username = request.getParameter("username");
        String number = jedis.get(username);
        int i = 0;

        if (!StringUtils.isEmpty(number))
            i = Integer.parseInt(number);

        if (exception.getMessage() == "Bad credentials") {
            if (StringUtils.isEmpty(number)) {
                jedis.set(username, "1", "NX", "EX", 60 * 60 * 24);
            } else {
                jedis.set(username, (i + 1) + "");
            }
            JSONObject.writeJSONString(response.getWriter(), new Result(false, "密码错误，还剩机会为：" + (5 - i)));

        } else {
            if (i == 5) {
                JSONObject.writeJSONString(response.getWriter(), new Result(false, "用户已锁定，请24小时后再试"));
            } else {
                JSONObject.writeJSONString(response.getWriter(), new Result(false, "用户名不存在"));
            }
        }
        jedis.close();

    }
}