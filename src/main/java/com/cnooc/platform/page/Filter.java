package com.cnooc.platform.page;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.cnooc.platform.util.StringUtils;

/**
 * @ClassName: Filter
 * @Description: TODO 查询筛选器
 * @author LZ.T
 * @date 2016-11-27 上午12:12:17
 * @version V2.0
 */
public class Filter implements Serializable {

	private static final long serialVersionUID = -8712382358441065075L;
	private static final SimpleDateFormat fmt = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	private static final SimpleDateFormat valueFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 运算符
	 */
	public enum Operator
	{

		/** 等于 */
		eq,

		/** 不等于 */
		ne,

		/** 大于 */
		gt,

		/** 小于 */
		lt,

		/** 大于等于 */
		ge,

		/** 小于等于 */
		le,

		/** 相似 */
		like,

		/** 包含 */
		in,

		/** 为Null */
		isNull,

		/** 不为Null */
		isNotNull;

		/**
		 * @Title: fromString @Description:从String中获取Operator @param @author
		 *         CST-TONGLZ @return Operator @throws
		 */
		public static Operator fromString(String value) {
			return Operator.valueOf(value.toLowerCase());
		}
	}

	/** 默认是否忽略大小写 */
	private static final boolean DEFAULT_IGNORE_CASE = false;

	/** 属性 */
	private String property;

	/** 运算符 */
	private Operator operator;

	/** 值 */
	private Object value;
	/** 类型 */
	private String type;

	/** 是否忽略大小写 */
	private Boolean ignoreCase = DEFAULT_IGNORE_CASE;

	/**
	 * 初始化一个新创建的Filter对象
	 */
	public Filter()
		{
		}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:初始化一个新创建的Filter对象
	 * </p>
	 * 
	 * @param property
	 * @param operator
	 * @param value
	 */
	public Filter(String property, Operator operator, Object value)
		{
			this.property = property;
			this.operator = operator;
			this.value = value;
		}

	/**
	 * 初始化一个新创建的Filter对象
	 * 
	 * @param property
	 *            属性
	 * @param operator
	 *            运算符
	 * @param value
	 *            值
	 * @param ignoreCase
	 *            忽略大小写
	 */
	public Filter(String property, Operator operator, Object value, boolean ignoreCase)
		{
			this.property = property;
			this.operator = operator;
			this.value = value;
			this.ignoreCase = ignoreCase;
		}

	/**
	 * 返回等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 等于筛选
	 */
	public static Filter eq(String property, Object value) {
		return new Filter(property, Operator.eq, value);
	}

	/**
	 * 返回等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 等于筛选
	 */
	public static Filter eq(String property, Object value, boolean ignoreCase) {
		return new Filter(property, Operator.eq, value, ignoreCase);
	}

	/**
	 * 返回不等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 不等于筛选
	 */
	public static Filter ne(String property, Object value) {
		return new Filter(property, Operator.ne, value);
	}

	/**
	 * 返回不等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 不等于筛选
	 */
	public static Filter ne(String property, Object value, boolean ignoreCase) {
		return new Filter(property, Operator.ne, value, ignoreCase);
	}

	/**
	 * 返回大于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 大于筛选
	 */
	public static Filter gt(String property, Object value) {
		return new Filter(property, Operator.gt, value);
	}

	/**
	 * 返回小于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 小于筛选
	 */
	public static Filter lt(String property, Object value) {
		return new Filter(property, Operator.lt, value);
	}

	/**
	 * 返回大于等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 大于等于筛选
	 */
	public static Filter ge(String property, Object value) {
		return new Filter(property, Operator.ge, value);
	}

	/**
	 * 返回小于等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 小于等于筛选
	 */
	public static Filter le(String property, Object value) {
		return new Filter(property, Operator.le, value);
	}

	/**
	 * 返回相似筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 相似筛选
	 */
	public static Filter like(String property, Object value) {
		return new Filter(property, Operator.like, value);
	}

	/**
	 * 返回包含筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 包含筛选
	 */
	public static Filter in(String property, Object value) {
		return new Filter(property, Operator.in, value);
	}

	/**
	 * 返回为Null筛选
	 * 
	 * @param property
	 *            属性
	 * @return 为Null筛选
	 */
	public static Filter isNull(String property) {
		return new Filter(property, Operator.isNull, null);
	}

	/**
	 * 返回不为Null筛选
	 * 
	 * @param property
	 *            属性
	 * @return 不为Null筛选
	 */
	public static Filter isNotNull(String property) {
		return new Filter(property, Operator.isNotNull, null);
	}

	/**
	 * 返回忽略大小写筛选
	 * 
	 * @return 忽略大小写筛选
	 */
	public Filter ignoreCase() {
		this.ignoreCase = true;
		return this;
	}

	/**
	 * 获取属性
	 * 
	 * @return 属性
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * 设置属性
	 * 
	 * @param property
	 *            属性
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 获取运算符
	 * 
	 * @return 运算符
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * 设置运算符
	 * 
	 * @param operator
	 *            运算符
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 *            值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 获取是否忽略大小写
	 * 
	 * @return 是否忽略大小写
	 */
	public Boolean getIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * 设置是否忽略大小写
	 * 
	 * @param ignoreCase
	 *            是否忽略大小写
	 */
	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpression(Map<String, Object> map) {
		Operator op = getOperator();
		if (op != Operator.isNull && op != Operator.isNull && getValue() == null
				&& op != Operator.isNotNull && op != Operator.isNotNull)
			return "";
		if (getValue() instanceof String && getValue().toString().length() == 0)
			return "";
		if (Collection.class.isAssignableFrom(value.getClass())) {
			if (value instanceof ArrayList<?>) {
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>) value;
				if (list.size() == 0)
					return "";
			} else {
				JSONArray arr = (JSONArray) value;
				if (arr.size() == 0)
					return "";
			}
		}
		StringBuffer bf = new StringBuffer();
		Object value = ConvertFilter();// 转化
		String property = getProperty();
		String key = (property + op.toString()).trim();
		String exprKey = getSqlExpr(value);
		bf.append(" and ");
		bf.append("(");
		bf.append(property);
		if (op == Operator.eq) {
			bf.append(" =");
			bf.append(exprKey);
			map.put(key, value);
		} else if (op == Operator.ne) {
			bf.append(" <>");
			bf.append(exprKey);
			map.put(key, value);
		} else if (op == Operator.gt) {
			bf.append(" >");
			bf.append(exprKey);
			map.put(key, value);
		} else if (op == Operator.lt) {
			bf.append(" <");
			bf.append(exprKey);
			map.put(key, value);
		} else if (op == Operator.ge) {
			bf.append(" >=");
			bf.append(exprKey);
			map.put(key, value);
		} else if (op == Operator.le) {
			bf.append(" <=");
			bf.append(exprKey);
			map.put(key, value);
		} else if (op == Operator.like) {
			bf.append(" like ");
			bf.append(exprKey);
			map.put(key, "%" + value + "%");
		} else if (op == Operator.in) {
			bf.append(" in");
			bf.append(exprKey);
			// map.put(key, getValue());
		} else if (op == Operator.isNull) {
			bf.append(" is null");
		} else if (op == Operator.isNotNull) {
			bf.append(" is not null");
		}
		bf.append(")");
		return bf.toString();
	}

	/**
	 * @Title: ConvertFilter @Description: @param @author CST-TONGLZ @return
	 *         Object @throws
	 */
	private Object ConvertFilter() {
		Object value = getValue();
		Class<?> clz = value.getClass();
		String type = getType();
		// 处理boolean
		if (clz == Boolean.class || "boolean".equalsIgnoreCase(type)) {
			boolean tempValue = Boolean.parseBoolean(value.toString());
			value = tempValue ? "Y" : "N";
			type = "boolean";
		}
		if ("date".equalsIgnoreCase(type)||"d".equalsIgnoreCase(type)) {
			try {
				String dateStr = value.toString();
				Date date = fmt.parse(dateStr);
				value = valueFmt.format(date);
			} catch (Exception e) {
				throw new RuntimeException("日期转化失败--》" + value);
			}
		}
		setType(type);
		return value;
	}

	private String getSqlExpr(Object value) {
		String type = getType();
		String key = (getProperty() + getOperator().toString()).trim();
		if (Collection.class.isAssignableFrom(value.getClass())) {
			StringBuffer bf = new StringBuffer("(");
			if (value instanceof ArrayList<?>) {
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>) value;
				int size = list.size();
				for (int i = 0; i < size; i++) {
					bf.append("'").append(list.get(i)).append("'");
					if (i < size - 1)
						bf.append(",");
				}
			} else {
				JSONArray arr = (JSONArray) value;
				int size = arr.size();
				for (int i = 0; i < size; i++) {
					bf.append("'").append(arr.get(i)).append("'");
					if (i < size - 1)
						bf.append(",");
				}
			}
			bf.append(")");
			return bf.toString();
		}
		if (StringUtils.isEmpty(type) || "object".equalsIgnoreCase(type))
			return new StringBuffer(":").append(key).toString();
		if ("date".equalsIgnoreCase(type)||"d".equalsIgnoreCase(type)) {
			// MS SQL
//			return new StringBuffer("convert(nvarchar(30),:").append(key).append(",120)")
//					.toString();
			// ORACLE
			 return new
			 StringBuffer("to_timestamp(:").append(key).append(",'yyyy-mm-dd hh24:mi:ss')").toString();
			// MYSQL
			// return new
			// StringBuffer("str_to_date(:").append(key).append(",'%m-%d-%Y
			// %h:%i:%s')")
			// .toString();
		}
		if ("boolean".equalsIgnoreCase(type) && "N".equals(value)) {
			return new StringBuffer(":").append(key).append(" OR ").append(getProperty())
					.append(" IS NULL ").toString();
		}
		return new StringBuffer(":").append(key).toString();
	}

}