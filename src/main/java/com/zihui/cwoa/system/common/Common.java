package com.zihui.cwoa.system.common;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 公共类获取 ip 设置cookie
 *
 * */
@Component
public class Common {

    public static final String IMG_PATH="C:/upload/";

    /**
     * @param request
     * @return 用户访问ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * @param response name value maxAge
     *                 設置 cookie
     * @throws UnsupportedEncodingException
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) throws UnsupportedEncodingException {

        Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));

        cookie.setPath("/");

        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * @param request 遍历所有cookie  存放在map里
     * @return map
     * @throws UnsupportedEncodingException
     */
    public static Map ReadCookieMap(HttpServletRequest request) throws UnsupportedEncodingException {
        Map cookieMap = new HashMap();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), URLDecoder.decode(cookie.getValue(), "UTF-8"));
            }
        }
        return cookieMap;
    }

    public static String setz(String code) {
        if (code.length() == 1) {
            code = "00" + code;
        } else if (code.length() == 2) {
            code = "0" + code;
        } else {
            code = code;
        }

        return code;
    }

    //文件上传
    public static String upload(MultipartFile file) {
        // 如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();//文件名
            String suffixName = filename.substring(filename.lastIndexOf("."));  // 后缀名
            filename = UUID.randomUUID() + suffixName; // 新文件名
            File filepath = new File(IMG_PATH, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            // 将上传文件保存到一个目标文件当中

            try {
                file.transferTo(new File(IMG_PATH + File.separator + filename));
            } catch (IOException e) {

                e.printStackTrace();
            }
            return filename;
        }
        return null;
    }
}