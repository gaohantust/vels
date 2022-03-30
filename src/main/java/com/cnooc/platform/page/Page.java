package com.cnooc.platform.page;

import com.cnooc.platform.system.dic.service.DictionaryCacheService;
import com.cnooc.platform.util.json.JSONUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * @ClassName: Page
 * @Description: TODO 分页组件
 * @author LZ.T
 * @date 2016-11-27 上午12:13:00
 * @version V2.0
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = -2053800594583879853L;
	/** 内容 */
	private final List<T> content = new ArrayList<T>();

	/** 总记录数 */
	private final long total;

	/** 分页信息 */
	private final QueryCondition queryCondition;

	/**
	 * 初始化一个新创建的Page对象
	 */
	public Page() {
		this.total = 0L;
		this.queryCondition = new QueryCondition();
	}

	/**
	 *
	 * @param data
	 * @param total
	 * @param queryCondition
	 * @author TONG
	 * @date 2020/12/14 20:23
	 * @return 
	 */
	public Page(List<T> data, long total, QueryCondition queryCondition) {
		this.content.addAll(data);
		this.total = total;
		this.queryCondition = queryCondition;
		relaceDicData();
	}

	@SuppressWarnings("unchecked")
	private void relaceDicData() {
		if (queryCondition == null)
			return;
		List<DictionaryReplace> list = queryCondition.getReplaceList();
		if (list == null || list.size() == 0)
			return;
		if (content == null || content.size() == 0)
			return;
		for (Map<String, Object> map : (List<Map<String, Object>>) content) {
			for (DictionaryReplace dr : list) {
				String dicType = dr.getDicType();
				Object value = map.get(dr.getFromColumn());
				String name = "";
				if (value != null) {
					String code = value.toString();
					name = DictionaryCacheService.getDicName(dicType, code);
				}
				map.put(dr.getColumn(), name);
			}
		}
	}

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	public int getCurrentPage() {
		return queryCondition.getCurrentPage();
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public int getPageSize() {
		return queryCondition.getPageSize();
	}

	/**
	 * 获取排序属性
	 * 
	 * @return 排序属性
	 */
	public String getOrderProperty() {
		return queryCondition.getOrderProperty();
	}

	/**
	 * 获取排序方向
	 * 
	 * @return 排序方向
	 */
	public Order.Direction getOrderDirection() {
		return queryCondition.getOrderDirection();
	}

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public List<Order> getOrders() {
		return queryCondition.getOrders();
	}

	/**
	 * 获取筛选
	 * 
	 * @return 筛选
	 */
	public List<Filter> getFilters() {
		return queryCondition.getFilters();
	}

	public QueryCondition getQueryCondition() {
		return queryCondition;
	}

	/**
	 * 获取总页数
	 * 
	 * @return 总页数
	 */
	public int getTotalPages() {
		return (int) (getTotal() + getPageSize() - 1) / getPageSize();
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public List<T> getData() {
		return content;
	}

	/**
	 * 获取总记录数
	 * 
	 * @return 总记录数
	 */
	public long getTotal() {
		return total;
	}

	public String toJson() {
		StringBuffer bf = new StringBuffer("{\"currentPage\":");
		bf.append(getQueryCondition().getCurrentPage());
		bf.append(",");
		bf.append("\"totalPage\":");
		bf.append(getTotalPages());
		bf.append(",");
		if(getOrderProperty()!=null){
			bf.append("\"orderProperty\":");
			bf.append("\"" + getOrderProperty() + "\"");
			bf.append(",");
			bf.append("\"orderDirection\":");
			bf.append("\"" + getOrderDirection() + "\"");
			bf.append(",");
		}
		bf.append("\"total\":");
		bf.append(getTotal());
		bf.append(",");
		bf.append("\"pageSize\":");
		bf.append(getPageSize());
		bf.append(",");
		bf.append("\"rows\":");
		//
		bf.append(JSONUtil.getJson(getData()));
		bf.append("}");
		return bf.toString();
	}

}