package com.cnooc.platform.system.user.ctrl;

import com.alibaba.fastjson.JSON;
import com.cnooc.platform.core.BaseCtrl;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.auth.service.UserRoleService;
import com.cnooc.platform.system.login.bean.LoginCodeEnum;
import com.cnooc.platform.system.login.service.LoginService;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.system.user.domain.UserPart;
import com.cnooc.platform.system.user.service.UserService;
import com.cnooc.platform.util.Global;
import com.cnooc.platform.util.json.JSONUtil;
import com.cnooc.platform.util.os.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wf.captcha.base.Captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Api(tags = "用户信息API")
public class UserCtrl extends BaseCtrl<User> {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private LoginService loginService;
    @Override
    public BaseService<User> getService() {
        return userService;
    }

    @RequestMapping("login.do")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }
    @RequestMapping("logout.do")
    public String logout() {
        return userService.logout();
    }

    @RequestMapping("getPermsion.do")
    @ApiOperation(value="获取权限",httpMethod = "POST")
    public String getPermsion() {
        return userService.getPermsion();
    }
    @RequestMapping("getParts.do")
    public String getParts() {
        return JSONUtil.getJson(userService.getParts());
    }
    @RequestMapping("saveParts.do")
    public String saveParts(@RequestBody List<UserPart> parts) {
        userService.saveParts(parts);
        return "";
    }

    @RequestMapping("saveUserRole/{user}.do")
    public String saveUserRole(@PathVariable("user") String user, @RequestBody List<String> roleId){
        userRoleService.saveUserRole(user,roleId);
        return  "{}";
    }
    @RequestMapping("getOnlineUser.do")
    public String getOnlineUser() {
        return JSONUtil.getJson(userService.getOnlineUsers());
    }
    @RequestMapping("outUser.do")
    public String outUser(@RequestBody String token) {
        return "token";
    }

    @RequestMapping("getCode.do")
    public String getCode() {
        // 获取运算的结果
        Captcha captcha = loginService.getCaptcha();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        String uuid =captchaValue;
        getService().getSession().setAttribute(Global.LOGIN_CODE_KEY,uuid);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return JSONUtil.getJson(imgResult);
    }

    @RequestMapping("/sysInfo.do")
    public String sysInfo() {
        try {
            Server server = new Server();
            server.copyTo();
            return JSON.toJSONString(server);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

}
