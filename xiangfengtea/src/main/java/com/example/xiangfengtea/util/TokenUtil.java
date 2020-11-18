package com.example.xiangfengtea.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.xiangfengtea.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/*
 * @author qiaoyn
 * @date 2019/06/14
 * @version 1.0
 */
@Component
public class TokenUtil {
    /**
     * 获取token
     * @param user
     * @return
     */
    public String getToken(User user) {
        Date start = new Date();
        Long timeout=60*60*24*90*1000L;//90天有效时间
        long currentTime = System.currentTimeMillis() + timeout;
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getUserId().toString()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getUserPassword()));
        return token;
    }

    public String getTokenUserId(String token) {
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }

    /**
     * 获取request
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}