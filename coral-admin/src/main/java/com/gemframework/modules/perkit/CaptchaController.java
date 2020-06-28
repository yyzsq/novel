package com.gemframework.modules.perkit;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "/captcha")
public class CaptchaController {
    
    @RequestMapping("/code")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Captcha captcha = new ArithmeticCaptcha();

        /**
         * 生成验证码字符串并保存到session中
         */
        String createText = captcha.text();  // 获取运算的结果
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equalsIgnoreCase("JSESSIONID")){
                    request.getSession().setAttribute("CAPTCHA_SESSION_KEY"+":"+cookie.getValue(), createText);
                }
            }
        }

        CaptchaUtil.out(captcha,request,response);
    }
}