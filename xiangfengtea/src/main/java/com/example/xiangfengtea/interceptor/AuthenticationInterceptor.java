package com.example.xiangfengtea.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.xiangfengtea.annotation.UserLoginToken;
import com.example.xiangfengtea.entity.User;
import com.example.xiangfengtea.service.UserService;
import com.example.xiangfengtea.util.CookieUtil;
import com.example.xiangfengtea.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 拦截器
 * @author qiaoyn
 * @date 2019/06/14
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    CookieUtil cookieUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        //String token = request.getHeader("token");// 从 http 请求头中取出 token
        String token= cookieUtil.getCookieValue(request,"token");
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        /*if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }*/
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            System.out.println("UserLoginToken");
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                try{
                    if (token == null) {
                        throw new RuntimeException("无token，请重新登录");
                    }
                    // 获取 token 中的 user id
                    String userId;
                    try {
                        userId = JWT.decode(token).getAudience().get(0);
                    } catch (JWTDecodeException j) {
                        throw new RuntimeException("token haven't userId");
                    }
                    //缓存中有匹配的token则直接返回
                    if(redisUtil.hasKey(userId)){
                        if(redisUtil.get(userId).equals(token)){
                            System.out.println("redis have token");
                            return true;
                        }else {
                            throw new RuntimeException("token匹配错误，请重新登录");
                        }
                    }
                    //缓存中没有匹配的token（已过期）,从数据库查询id和password是否匹对
                    System.out.println("redis don't have token");
                    System.out.println("userService search from datebase");
                    User user = userService.findUserByUserId(Long.parseLong(userId));
                    if (user == null) {
                        throw new RuntimeException("用户不存在，请重新登录");
                    }
                    // 验证 token
                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserPassword())).build();
                    try {
                        jwtVerifier.verify(token);
                        //重新在redis中保存token
                        System.out.println("save token to redis");
                        redisUtil.set(user.getUserId().toString(),token,redisUtil.getRedisTokenTimeout());
                    } catch (JWTVerificationException e) {
                        throw new RuntimeException("401");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("/login");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

