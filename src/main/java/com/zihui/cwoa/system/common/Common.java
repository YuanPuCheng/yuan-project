package com.zihui.cwoa.system.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 公共类获取 ip 设置cookie
 *
 * */
public class Common {
    /**
     * @param request
     * @return 用户访问ip
     * */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    /**
     * @param response  name value maxAge
     * 設置 cookie
     * @throws UnsupportedEncodingException
     * */
    public static void addCookie(HttpServletResponse response,String name,String value,int maxAge) throws UnsupportedEncodingException{

        Cookie cookie=new Cookie(name,URLEncoder.encode(value, "UTF-8"));

        cookie.setPath("/");

        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    /**
     * @param request
     * 遍历所有cookie  存放在map里
     * @return map
     * @throws UnsupportedEncodingException
     * */
    public static Map ReadCookieMap(HttpServletRequest request) throws UnsupportedEncodingException{
        Map cookieMap=new HashMap();
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                cookieMap.put(cookie.getName(), URLDecoder.decode(cookie.getValue(), "UTF-8"));
            }
        }
        return cookieMap;
    }

    public static String setz(String code){
        if(code.length()==1){
            code="00"+code;
        }else if(code.length()==2){
            code="0"+code;
        }else{
            code=code;
        }

        return code;
    }
}
