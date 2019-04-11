package com.zihui.cwoa.system.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.Common;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/yzm")
public class YZMController {

	public static Logger log = Logger.getLogger(YZMController.class);
	@Autowired
	private DefaultKaptcha defaultKaptcha;

	@RequestMapping("/yzm")
	public void defaultKaptcha(HttpServletResponse httpServletResponse,HttpServletRequest request) throws Exception{
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		HttpSession session = request.getSession();
		String lastYZM = (String) session.getAttribute("vrifyCode");
		System.out.println("*******************上一次的验证码是" + lastYZM+ "**********************");
		try {
			//生产验证码字符串并保存到session中
			String createText = defaultKaptcha.createText();

			session.setAttribute("vrifyCode",createText);
			//session.setMaxInactiveInterval(300);
			log.info("本次的验证码"+createText);
			log.info("session"+session.getAttribute("vrifyCode"));
			//使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = defaultKaptcha.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
		} catch (IllegalArgumentException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		//定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
		// 设置浏览器不要对数据进行缓存
		httpServletResponse.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		httpServletResponse.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		httpServletResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		httpServletResponse.setHeader("Pragma", "no-cache");
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		ServletOutputStream responseOutputStream =
				httpServletResponse.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	@RequestMapping("/emailyzm")
	@ResponseBody()
	public CallbackResult emailYzm(@RequestParam String email, HttpSession session) throws IOException, GeneralSecurityException {
		CallbackResult c = new CallbackResult();

		String code = Basecommon.sendcode();
		log.info(email);
		log.info(code);
		boolean falg = Common.sendEmail(email, "欢迎使用邮箱注册","您本次的验证码为"+code+"，本验证码5分钟之内有效！");
		if(falg){
			session.setAttribute("emailYzm",code);
			session.setMaxInactiveInterval(300);
			c.result=200;
			c.message = "邮箱发送成功";
		}else{
			c.result=400;
			c.message = "发送失败，请检查邮箱是否正确";
		}

		return c;
	}



}