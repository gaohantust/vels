package com.cnooc.platform.system.user.service;

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.exception.bean.AuthException;
import com.cnooc.platform.exception.bean.LoginException;
import com.cnooc.platform.exception.bean.ServiceException;
import com.cnooc.platform.system.auth.service.UserRoleService;
import com.cnooc.platform.system.resource.service.ResourceService;
import com.cnooc.platform.system.role.domain.Role;
import com.cnooc.platform.system.role.service.RoleService;
import com.cnooc.platform.system.user.dao.UserDao;
import com.cnooc.platform.system.user.domain.LoginInfo;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.system.user.domain.UserPart;
import com.cnooc.platform.util.EdsUtil;
import com.cnooc.platform.util.Global;
import com.cnooc.platform.util.StringUtils;
import com.cnooc.platform.util.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 描述：
 *
 * @author TONG
 * @Version v1.0
 * @date 2020/11/9 9:38
 */
@Service
public class UserService extends BaseService<User> {
    @Autowired
    private UserDao dao;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserPartService userPartService;

    @Override
    public BaseDao<User> getDao() {
        return dao;
    }

    /**
     * 登录验证
     *
     * @param user
     * @return java.lang.String
     * @author TONG
     * @date 2020/12/1 17:22
     */
    public String login(User user) {
        String password = user.getPassword();
        String code=user.getCode();
        String type=user.getType();
        String vels_password="";
        String max_password="";
        try {
            password = EdsUtil.decryption(password,Global.PASSWORD_KEY);
            code=EdsUtil.decryption(code,Global.PASSWORD_KEY);
            type=EdsUtil.decryption(type,Global.PASSWORD_KEY);
            vels_password = StringUtils.getMD5(password);
            max_password = StringUtils.maxDES(password);
        } catch (Exception e) {
            throw new ServiceException("password","密码证书认证失败");
        }
        Object loginCodeObj=getSession().getAttribute(Global.LOGIN_CODE_KEY);
        if(loginCodeObj==null){
            throw new ServiceException("loginCode", "无效的验证码！");
        }
        if(!type.equals(loginCodeObj.toString())){
            throw new ServiceException("loginCode", "验证码错误！");
        }
        User dbUser = dao.findByField("code", code);
        if (dbUser == null) {
            //getMaxUser
            Map<String,Object> maxUser=dao.getMaxUser(code);
            if(maxUser==null){
                throw new ServiceException("user", "用户名或密码错误！");
            }
            dbUser=syncMaxUser(maxUser);
        }
        if (!dbUser.getValid()) {
            throw new ServiceException("valid", "用户已禁用，请联系管理员！");
        }
        if (!dbUser.getPassword().equals(vels_password)&&!dbUser.getMax_password().equals(max_password)) {
            throw new ServiceException("user", "用户名或密码错误！");
        }
        LoginInfo loginInfo = new LoginInfo(dbUser);
        resourceService.setPermsion(loginInfo);
        Map<String,String> osInfo=getOsAndBrowserInfo();
        loginInfo.getClient().put("ip",getRemoteAddress());
        loginInfo.getClient().put("os",osInfo.get("os"));
        loginInfo.getClient().put("browser",osInfo.get("browser"));
        loginInfo.getClient().put("loginDate",new Date());
        HttpSession session = getSession();
        loginInfo.setToken(session.getId());
        session.setAttribute(Global.CUR_USER, loginInfo);
        return JSONUtil.getJson(loginInfo);
    }
    public String logout(){
        getSession().removeAttribute(Global.CUR_USER);
        getSession().invalidate();
        return "";
    }
    public String getPermsion() {
        HttpSession session = getSession();
        LoginInfo loginInfo=(LoginInfo)session.getAttribute(Global.CUR_USER);
        if(loginInfo==null){
            throw new LoginException("","Session失效");
        }
        return JSONUtil.getJson(loginInfo);
    }
    public Map<String, Object> getParts() {
        User user=getCurrentUser();
        Map<String, Object> returnMap=new HashMap<>();
        List<Map<String, Object>> parts= dao.getParts(user.getId());
        List<UserPart> user_parts=userPartService.getByUser(user);
        returnMap.put("parts",parts);
        returnMap.put("user_parts",user_parts);
        return returnMap;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveParts(List<UserPart> parts) {
        User user=getCurrentUser();
        userPartService.delByUser(user);
        for(UserPart userPart:parts){
            userPart.setUser(user);
            userPartService.save(userPart);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User syncMaxUser(Map<String,Object> maxUser){
        byte[] bs=(byte[])maxUser.get("password");
        String userId=getString(maxUser,"userid");
        String code=getString(maxUser,"loginid");
        String email=getString(maxUser,"emailaddress");
        String password=getMaxPassword(bs);
        User user=new User();
        user.setPassword(password);
        user.setMax_password(password);
        user.setCode(code);
        user.setName(userId);
        user.setSex("2");
        user.setType("maximo");
        user.setEmail(email);
        create(user);
        flush();
        //指定默认角色
        Role role=roleService.findByCode("DEF");
        List<String> roles=new ArrayList<>();
        roles.add(role.getId());
        userRoleService.saveUserRole(user.getId(),roles);
        return user;
    }
    private String getMaxPassword(byte[] bs){
        StringBuffer raw = new StringBuffer();
        for (int i = 0; i < bs.length; i++) {
            //调用工具类，字节进行解析后，拼接到字符串中
            raw.append(StringUtils.decToHex(bs[i]));
        }
        return raw.toString();
    }

    @Override
    public void setDef(User user) {
        if(user.isNew()){
            String defPassword=Global.USER_DEF_PASSWORD;
            try {
                defPassword=StringUtils.getMD5(defPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setPassword(defPassword);
        }else{
           User db= findByID(user.getId());
           user.setPassword(db.getPassword());
        }
    }
    public List<LoginInfo> getOnlineUsers(){
        HttpSession session=getSession();
        ServletContext application = session.getServletContext();
        Object obj=application.getAttribute(Global.ONLINE_USER_KEY);
        Map<String,LoginInfo> users=new HashMap<>();
        if(obj!=null){
            users=(Map<String,LoginInfo>)obj;
        }
        List<LoginInfo> list=new ArrayList<>();
        for(LoginInfo loginInfo:users.values()){
            list.add(loginInfo);
        }
        return list;
    }
}
