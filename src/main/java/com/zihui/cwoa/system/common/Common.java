package com.zihui.cwoa.system.common;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.security.GeneralSecurityException;
import java.util.*;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.mail.internet.MimeMultipart;
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


    /****
     * 邮件发送通用
     * @param sendmail
     * @param title
     * @param content
     */
    public static boolean sendEmail(String sendmail,String title,String content){
        boolean falg = true;
        // 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");// 连接协议
        props.setProperty("mail.smtp.host", "smtp.139.com");// 连接协议
        //props.setProperty("mail.smtp.port", "25");// 连接协议
        // 指定验证为true
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.timeout","1000");
        // 验证账号及密码，密码需要是第三方授权码
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("18279085893@139.com", "yuanpucheng520");
            }
        };
        Session session = Session.getInstance(props, auth);
        // 2.创建一个Message，它相当于是邮件内容
        MimeMessage message = new MimeMessage(session);
        try {
            //防止成为垃圾邮件，披上outlook的马甲
            message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
            // 设置发送者
            message.setFrom(new InternetAddress("18279085893@139.com","江西紫慧科技有限公司"));
            // 设置发送方式与接收者
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(sendmail));
            // 设置主题
            message.setSubject(title);
            //创建消息主体
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(content);
            // 创建多重消息
            Multipart multipart=new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            // 设置邮件消息发送的时间
            //message.setSentDate(Calendar.getInstance().getTime());
            // 设置内容
            message.setContent(multipart, "text/html;charset=utf-8");
            // 3.创建 Transport用于将邮件发送
            Transport.send(message);
            return falg;
        }catch (Exception e){
            falg = false;
            e.printStackTrace();

        }
        return falg;
    }
}