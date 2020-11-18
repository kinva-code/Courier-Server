package com.example.xiangfengtea.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {
    @Value("${cookie.max-age}")
    private int cookieMaxAge;

    public int getCookieMaxAge(){return cookieMaxAge;}

    public String getCookieValue(HttpServletRequest request,String key){
        Cookie cookie=getCookie(request,key);
        if(cookie!=null){
            return cookie.getValue();
        }
        return null;
    }

    public Cookie getCookie(HttpServletRequest request,String key){
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(key)){
                    return cookie;
                }
            }
        }
        return null;
    }

    public void removeCookie(String key,HttpServletResponse response){
        Cookie cookie=new Cookie(key,null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public void setCookie(String key,String value,int maxAge,HttpServletResponse response){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public void setCookieForThisTime(String key,String value,HttpServletResponse response){
        Cookie cookie = new Cookie(key, value);
        response.addCookie(cookie);
    }
}
