package com.cnooc.platform.core;

import com.alibaba.druid.util.StringUtils;
import com.cnooc.platform.exception.bean.LoginException;
import com.cnooc.platform.exception.bean.ServiceException;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.system.user.domain.LoginInfo;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.util.Global;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BaseService
 * @Description: TODO 服务层基类，实现所有实体服务层的CRUD
 * @author LZ.T
 * @date 2016-11-26 下午11:49:37
 * @version V2.0
 */
@Transactional
public abstract class BaseService<T extends BaseEntity> {
	public abstract BaseDao<T> getDao();

	/**
	* @Title: create 创建对象
	* @Description: TODO
	* @param @param T
	* @return void
	* @author LZ.T
	* @throws
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void create(T T) {
		getDao().create(T);
	}

	/**
	* @Title: delete 
	* @Description: TODO 根据主键删除
	* @param @param id
	* @return void
	* @author LZ.T
	* @throws
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String id) {
		getDao().delete(id);
	}

	/**
	* @Title: batchDelete 
	* @Description: TODO 批量删除
	* @param @param ids
	* @return void
	* @author LZ.T
	* @throws
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void batchDelete(String[] ids) {
		for (String id : ids) {
			getDao().delete(id);
		}
	}

	/**
	* @Title: update 更新对象，设置NULL的属性将被写入到数据库
	* @Description: TODO
	* @param @param t
	* @return void
	* @author LZ.T
	* @throws
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(T t) {
		getDao().update(t);
	}

	

	/**
	* @Title: findAll 
	* @Description: TODO 获取所有实体对象
	* @param @return
	* @return List<T>
	* @author LZ.T
	* @throws
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<T> findAll() {
		List<T> entitys = getDao().findAll();
		return entitys;
	}

	/**
	* @Title: findByCode 根据编码获取实体对象，实体需继承ArchiveEntity，或者具有code属性
	* @Description: TODO
	* @param @param code
	* @param @return
	* @return T
	* @author LZ.T
	* @throws
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public T findByCode(String code) {
		T t = getDao().findByCode(code);
		return t;
	}

	/**
	* @Title: findByCode 根据编码获取实体对象，忽略主键为id的对象，实体需继承ArchiveEntity，或者具有code属性
	* @Description: TODO
	* @param @param code
	* @param @return
	* @return T
	* @author LZ.T
	* @throws
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public T findByCode(Serializable code, Serializable id) {
		T t = getDao().findByCode(code, id);
		return t;
	}

	

	/**
	* @Title: flush 刷新EM缓存
	* @Description: TODO
	* @param 
	* @return void
	* @author LZ.T
	* @throws
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void flush() {
		getDao().flush();
	}

	/**
	* @Title: findByField 
	* @Description: TODO根据fieldName查询
	* @param @param fieldName
	* @param @param fieldValue
	* @param @return
	* @return T
	* @author LZ.T
	* @throws
	 */
	public T findByField(String fieldName, Object fieldValue) {
		return getDao().findByField(fieldName, fieldValue);
	}

	/**
	* @Title: findByField 
	* @Description: TODO 根据fieldName查询 
	* @param @param fieldName
	* @param @param fieldValue
	* @param @param id
	* @param @return
	* @return T
	* @author LZ.T
	* @throws
	 */
	public T findByField(String fieldName, Object fieldValue, Serializable id) {
		return getDao().findByField(fieldName, fieldValue, id);
	}

	/**
	* @Title: save
	* @Description: TODO 保存对象，对为持久化的对象，进行创建，否则则进行修改
	* @param @param entity
	* @return void
	* @author LZ.T
	* @throws
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(T entity) {
		setDef(entity);
		validate(entity);
		if(entity instanceof TreeEntity){
			TreeEntity parent=  (TreeEntity)((TreeEntity<?>) entity).getParent();
			if(parent!=null&&parent.isNew()){
				((TreeEntity<?>) entity).setParent(null);
			}
		}
		if (entity.isNew()) {
			create(entity);
		} else {
			update(entity);
		}
	}
	/**
	 * @Title: findByID 根据ID获取实体
	 * @Description: TODO
	 * @param @param ID
	 * @param @return
	 * @return T
	 * @author LZ.T
	 * @throws
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public T findByID(String ID) {
		T t = getDao().findByID(ID);
//		if (t instanceof IAtta) {
//			setAtta(t);
//		}
		return t;
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public String findByIDToJson(String ID) {
		T t = findByID(ID);
		return t.toJSON();
	}

	/**
	* @Title: validate 
	* @Description: TODO 保存对象前进行验证，如父类不满足验证条件，在子类进行重写此方法
	* @param @param entity
	* @return void
	* @author LZ.T
	* @throws
	 */
	public void validate(T entity) {
		if(entity instanceof  ArchiveEntity){
			if(entity.isNew()){
				T other=findByField("code",((ArchiveEntity) entity).getCode());
				if(other!=null){
					throw new ServiceException("code","您输入的编码信息已存在，请重新填写");
				}
			}else {
				T other=findByField("code",((ArchiveEntity) entity).getCode(),entity.getId());
				if(other!=null){
					throw new ServiceException("code","您输入的编码信息已存在，请重新填写");
				}
			}
		}
	}

	/**
	* @Title: setDef 
	* @Description: TODO 保存对象前进行设置默认值，一般会重新此方法来设置对象的默认属性
	* @param @param entity
	* @return void
	* @author LZ.T
	* @throws
	 */
	public void setDef(T entity) {

	}
	protected User getCurrentUser(){
		HttpSession session = getSession();
		LoginInfo loginInfo=(LoginInfo)session.getAttribute(Global.CUR_USER);
		if(loginInfo==null){
			throw new LoginException("","Session失效");
		}
		return loginInfo.getUser();
	}
	

	public HttpSession getSession() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		return request.getSession();
	}
	public HttpServletRequest getRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		return request;
	}

	/**
	* @Title: getRemoteAddress 
	* @Description: TODO  获取IP
	* @param @return
	* @return String
	* @author LZ.T
	* @throws
	 */
	public String getRemoteAddress() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		String ip = request.getHeader("X-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	protected String getString(Map<String,Object> map, String key){
		if(!map.containsKey(key)){
			return "";
		}
		Object value=map.get(key);
		if(value==null){
			return "";
		}
		return String.valueOf(value);
	}
	protected double getDouble(Map<String,Object> map, String key){
		if(!map.containsKey(key)){
			return 0d;
		}
		Object value=map.get(key);
		if(value==null||value.toString().length()==0){
			return 0d;
		}
		return Double.parseDouble(value.toString());
	}
	protected Integer getInt(Map<String,Object> map, String key){
		if(!map.containsKey(key)){
			return null;
		}
		Object value=map.get(key);
		if(value==null){
			return null;
		}
		return Integer.valueOf(getString(map,key));
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public String findPage(QueryCondition condition) {
		Page<?> page = getDao().findPage(condition);
		return page.toJson();
	}
	/**
	 * 获取操作系统,浏览器及浏览器版本信息
	 *
	 * @param
	 * @return
	 */
	protected Map<String, String> getOsAndBrowserInfo() {
		HttpServletRequest request=getRequest();
		String browserDetails = request.getHeader("User-Agent");
		String userAgent = browserDetails;
		String user = userAgent.toLowerCase();

		String os = "";
		String browser = "";

		//=================OS Info=======================
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		//===============Browser===========================
		if (user.contains("edge")) {
			browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
					+ "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera")) {
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
						+ "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			} else if (user.contains("opr")) {
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
						.replace("OPR", "Opera");
			}

		} else if (user.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) ||
				(user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) ||
				(user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
			browser = "Netscape-?";

		} else if (user.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
			browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
		} else {
			browser = "UnKnown, More-Info: " + userAgent;
		}
		Map<String, String> map = new HashMap<>();
		map.put("os", os);
		map.put("browser", browser);
		return map;
	}
}
