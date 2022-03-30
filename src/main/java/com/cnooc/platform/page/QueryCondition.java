package com.cnooc.platform.page;

import com.cnooc.platform.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: QueryCondition
 * @Description: TODO 查询条件对象
 * @author LZ.T
 * @date 2020-11-27 上午12:13:11
 * @version V2.0
 */
public class QueryCondition implements Serializable {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	/** 默认页码 */
	private static final int DEFAULT_PAGE_NUMBER = 1;

	/** 默认每页记录数 */
	private static final int DEFAULT_PAGE_SIZE = 10;

	/** 最大每页记录数 */
	private static final int MAX_PAGE_SIZE = 1000;

	/** 页码 */
	private int currentPage = DEFAULT_PAGE_NUMBER;

	/** 每页记录数 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/** 排序属性 */
	private String orderProperty;

	/** 排序方向 */
	private Order.Direction orderDirection;

	/** 筛选 */
	private List<Filter> filters = new ArrayList<Filter>();

	/** 排序 */
	private List<Order> orders = new ArrayList<Order>();
	/** 字典替换 */
	private List<DictionaryReplace> replaceList = new ArrayList<DictionaryReplace>();

	private String SQL;

	/**
	 * 初始化一个新创建的QueryCondition对象
	 */
	public QueryCondition() {
	}

	/**
	 * 初始化一个新创建的Pageable对象
	 * 
	 * @param currentPage
	 *            页码
	 * @param pageSize
	 *            每页记录数
	 */
	public QueryCondition(Integer currentPage, Integer pageSize) {
		if (currentPage != null && currentPage >= 1) {
			this.currentPage = currentPage;
		}
		if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * @Title: addDicReplace
	 * @Description:替换字典设置
	 * @param
	 * @author CST-TONGLZ
	 * @return QueryCondition
	 * @throws
	 */
	public QueryCondition addDicReplace(String dicType, String fromColumn) {
		replaceList.add(new DictionaryReplace(fromColumn, fromColumn, dicType));
		return this;
	}

	/**
	 * @Title: addDicReplace
	 * @Description:替换字典设置
	 * @param
	 * @author CST-TONGLZ
	 * @return QueryCondition
	 * @throws
	 */
	public QueryCondition addDicReplace(String dicType, String fromColumn, String intoColumn) {
		replaceList.add(new DictionaryReplace(intoColumn, fromColumn, dicType));
		return this;
	}

	public List<DictionaryReplace> getReplaceList() {
		return replaceList;
	}

	public void setReplaceList(List<DictionaryReplace> replaceList) {
		this.replaceList = replaceList;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param currentPage
	 */
	public QueryCondition(Integer currentPage) {
		if (currentPage != null && currentPage >= 1) {
			this.currentPage = currentPage;
		}
	}

	/**
	 * @Title: getSQL
	 * @Description:
	 * @param
	 * @author CST-TONGLZ
	 * @return String
	 * @throws
	 */
	public String getSQL() {
		return SQL;
	}

	/**
	 * @Title: setSQL
	 * @Description:
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public void setSQL(String sql) {
		this.SQL = sql;
	}

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置页码
	 * 
	 * @param currentPage
	 *            页码
	 */
	public void setCurrentPage(int currentPage) {
		if (currentPage < 1) {
			currentPage = DEFAULT_PAGE_NUMBER;
		}
		this.currentPage = currentPage;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页记录数
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public void setPageSize(int pageSize) {
		// if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
		// pageSize = DEFAULT_PAGE_SIZE;
		// }
		this.pageSize = pageSize;
	}

	/**
	 * 获取排序属性
	 *
	 * @return 排序属性
	 */
	public String getOrderProperty() {
		return orderProperty;
	}

	/**
	 * 设置排序属性
	 *
	 * @param orderProperty
	 *            排序属性
	 */
	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	/**
	 * 获取排序方向
	 *
	 * @return 排序方向
	 */
	public Order.Direction getOrderDirection() {
		return orderDirection;
	}

	/**
	 * 设置排序方向
	 *
	 * @param orderDirection
	 *            排序方向
	 */
	public void setOrderDirection(Order.Direction orderDirection) {
		this.orderDirection = orderDirection;
	}

	/**
	 * 获取筛选
	 * 
	 * @return 筛选
	 */
	public List<Filter> getFilters() {
		return filters;
	}

	/**
	 * @Title: addFilter
	 * @Description:添加筛选器
	 * @param
	 * @author CST-TONGLZ
	 * @return List<Filter>
	 * @throws
	 */
	public List<Filter> addFilter(Filter filter) {
		filters.add(filter);
		return filters;
	}

	/**
	 * 设置筛选
	 * 
	 * @param filters
	 *            筛选
	 */
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * @Title: addOrders
	 * @Description:添加排序器
	 * @param
	 * @author CST-TONGLZ
	 * @return List<Order>
	 * @throws
	 */
	public List<Order> addOrders(Order order) {
		orders.add(order);
		return orders;
	}

	/**
	 * 设置排序
	 * 
	 * @param orders
	 *            排序
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	/**
	 * @Title: getFullOrder
	 * @Description:获取所有的排序
	 * @param
	 * @author CST-TONGLZ
	 * @return List<Order>
	 * @throws
	 */
	private List<Order> getFullOrder() {
		List<Order> resOrder = new ArrayList<Order>(getOrders());
		String orderProperty = getOrderProperty();
		if (StringUtils.isNotEmpty(orderProperty)) {
			Order order = new Order(orderProperty, getOrderDirection());
			resOrder.add(0, order);
		}
		Map<String, Order> map = new HashMap<String, Order>();
		for (Order order : resOrder) {
			map.put(order.getProperty(), order);
		}
		return new ArrayList<Order>(map.values());
	}

	/**
	 * @Title: getSqlInfo
	 * @Description:获取SQL 以及条件
	 * @param
	 * @author CST-TONGLZ
	 * @return Object[]
	 * @throws
	 */
	public final Object[] getSqlInfo(boolean isOrderBy) {
		if (StringUtils.isEmpty(SQL))
			throw new RuntimeException("SQL 不允许为空");
		String sql = SQL;
		sql = sql.replaceAll("\\s{1,}", " ");
		String orderBy = "";
		String groupBy = "";
		int orderIndex = sql.toUpperCase().lastIndexOf("ORDER BY");
		if (orderIndex > 0) {
			orderBy = sql.substring(orderIndex);
			sql = sql.substring(0, orderIndex);
		}else{
			//orderBy=" order by id ";
			orderBy=" ";
		}
		int groupIndex = sql.toUpperCase().lastIndexOf("GROUP BY");
		if (groupIndex > 0) {
			groupBy = sql.substring(groupIndex);
			sql = sql.substring(0, groupIndex);
		}
		List<Order> orders = getFullOrder();
		if (orders != null && orders.size() > 0) {
			orderBy = "";
			int orderSize = orders.size();
			StringBuffer orderBf = new StringBuffer(orderBy);
			if (orderBy.length() == 0) {
				orderBf.append(" ORDER BY ");
			} else {
				orderBf.append(",");
			}
			for (int i = 0; i < orderSize; i++) {
				Order order = orders.get(i);
				orderBf.append(order.toSql());
				if (i == orderSize - 1)
					continue;
				orderBf.append(",");
			}
			orderBy = orderBf.toString();
		}
		if (!isOrderBy) {// 不拼接ORDER BY
			orderBy = " ";
		}
		if (filters == null || filters.size() == 0) {
			String str = new StringBuffer(sql).append(" ").append(groupBy).append(" ").append(orderBy).toString();
			return new Object[] { str, null };
		}
		boolean isHasWhere = isHasWhere();
		StringBuffer bf = new StringBuffer(sql);
		if (!isHasWhere) {
			bf.append(" WHERE 1=1 ");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (Filter filter : filters) {
			bf.append(filter.getExpression(map));
		}
		bf.append(" ").append(groupBy).append(" ").append(orderBy);
		return new Object[] { bf.toString(), map };
	}

	private boolean isHasWhere() {
		String sqlTemplate = SQL.toUpperCase().replaceAll("\\n","");
		return sqlTemplate.indexOf(" WHERE ") > 0;
	}

	/**
	 * @Title: getCountSql
	 * @Description:count SQL
	 * @param
	 * @author CST-TONGLZ
	 * @return String
	 * @throws
	 */
	public String getCountSql(String sql) {
		int cnt = getFromCount();
		if (cnt > 1) {// 处理子查询
			return new StringBuffer("SELECT COUNT(*) FROM (").append(sql).append(") a").toString();
		}
		String sqlTempLate = sql.toUpperCase();
		int index = sqlTempLate.indexOf("FROM");
		int orderIndex = sqlTempLate.indexOf("ORDER BY");
		if (orderIndex > 0) {
			sql = sql.substring(0, orderIndex);
		}
		return new StringBuffer("SELECT COUNT(*) ").append(sql.substring(index)).toString();
	}

	private int getFromCount() {
		String sqlTempLate = SQL.toUpperCase();
		String from = "FROM";
		int cnt = 0;
		int offset = 0;
		while ((offset = sqlTempLate.indexOf(from, offset)) != -1) {
			offset = offset + from.length();
			cnt++;
		}
		return cnt;
	}

	/**
	 * @Title: getSqlInfo
	 * @Description:
	 * @param
	 * @author CST-TONGLZ
	 * @return Object[]
	 * @throws
	 */
	public Object[] getSqlInfo() {
		return getSqlInfo(true);
	}

	/**
	 * @Title: showDebug
	 * @Description:输出调试信息
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public void showDebug() {
		Object[] info = getSqlInfo();
		String sql = (String) info[0];
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) info[1];
		String paraSQL = "*  ParaSQL=" + sql;
		List<String> printList = new ArrayList<String>();
		printList.add("*  SQL=" + SQL);
		printList.add(paraSQL);
		if (map != null){
			for (String key : map.keySet()) {
				Object obj = map.get(key);
				printList.add("*  参数：" + key + "=" + obj + "(" + obj.getClass().getSimpleName() + ")");
			}
		}
		System.out.println("\n");
		System.out.println("*****************************QueryCondition调试***************************");
		for (String in : printList) {
			System.out.println(in);
		}
		System.out.println("*************************************************************************");
		System.out.println("\n");
	}

}
