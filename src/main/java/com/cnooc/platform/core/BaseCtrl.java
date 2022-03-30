package com.cnooc.platform.core;

import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.util.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: BaseController
 * @Description: TODO Controller 基类，实现抽象方法可以继承CRUD操作
 * @author LZ.T
 * @date 2017年8月15日 上午10:23:48
 * @version V1.0
 */
public abstract class BaseCtrl<T extends BaseEntity> {
	public abstract BaseService<T> getService();
	/**
	 * @Title: save
	 * @Description: TODO 通过URL映射保存操作，调用服务层保存方法
	 * @param @param entity
	 * @param @return
	 * @return String
	 * @author LZ.T
	 * @throws
	 */
	@RequestMapping("/save.do")
	@ResponseBody
	@ApiOperation(value="保存信息",httpMethod = "POST")
	public String save(@RequestBody T entity) {
		getService().save(entity);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", entity.getId());
		return JSONUtil.getJson(map);
	}

	/**
	 * @Title: save
	 * @Description: TODO 根据ID获取当前实体
	 * @param @param id
	 * @param @return
	 * @return String
	 * @author LZ.T
	 * @throws
	 */
	@RequestMapping("/get.do")
	@ResponseBody
	@ApiOperation(value="根据ID获取实体",httpMethod = "POST")
	public String get(@RequestBody String id) {
		return getService().findByIDToJson(id);
	}

	/**
	 * @Title: delete
	 * @Description: TODO 根据ID删除
	 * @param @param id
	 * @param @return
	 * @return String
	 * @author LZ.T
	 * @throws
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	@ApiOperation(value="根据ID删除",httpMethod = "POST")
	public String delete(@RequestBody String id) {
		getService().delete(id);
		return "";
	}
	@RequestMapping("/query.do")
	@ResponseBody
	@ApiOperation(value="根据查询条件分页查询",httpMethod = "POST")
	public String findPage(@RequestBody QueryCondition condition) {
		return getService().findPage(condition);
	}

}
