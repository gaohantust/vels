package com.cnooc.platform.system.login.service;
/**
 * @ClassName LoginProperties.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年01月08日 14:12:00
 */

import com.cnooc.platform.exception.bean.ServiceException;
import com.cnooc.platform.system.login.bean.LoginCodeEnum;
import com.cnooc.platform.util.StringUtils;
import com.mchange.io.BadConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;

import java.awt.*;
import java.util.Objects;

/**
 * @program: vels
 * @description: 用户登录配置
 * @author: TONG
 * @create: 2021-01-08 14:12
 **/
@Service
public class LoginService {
    private LoginCode loginCode;
    /**
     * 获取验证码生产类
     *
     * @return /
     */
    public Captcha getCaptcha() {
        if (Objects.isNull(loginCode)) {
            loginCode = new LoginCode();
            if (Objects.isNull(loginCode.getCodeType())) {
                loginCode.setCodeType(LoginCodeEnum.arithmetic);
            }
        }
        return switchCaptcha(loginCode);
    }

    /**
     * 依据配置信息生产验证码
     *
     * @param loginCode 验证码配置信息
     * @return /
     */
    private Captcha switchCaptcha(LoginCode loginCode) {
        Captcha captcha;
        synchronized (this) {
            switch (loginCode.getCodeType()) {
                case arithmetic:
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new ArithmeticCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    // 几位数运算，默认是两位
                    captcha.setLen(loginCode.getLength());
                    break;
                case chinese:
                    captcha = new ChineseCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case chinese_gif:
                    captcha = new ChineseGifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case gif:
                    captcha = new GifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case spec:
                    captcha = new SpecCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                default:
                    throw new ServiceException("loginCode","验证码配置信息错误！正确配置查看 LoginCodeEnum ");
            }
        }
        if(StringUtils.isEmpty(loginCode.getFontName())){
            captcha.setFont(new Font(loginCode.getFontName(), Font.PLAIN, loginCode.getFontSize()));
        }
        return captcha;
    }
}
