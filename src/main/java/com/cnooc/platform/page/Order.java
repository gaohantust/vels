package com.cnooc.platform.page;


import java.io.Serializable;

/**
 * @ClassName: Order
 * @Description: TODO 查询排序器
 * @author LZ.T
 * @date 2016-11-27 上午12:12:38
 * @version V2.0
 */
public class Order implements Serializable {

	private static final long serialVersionUID = -3078342809727773232L;

	/**
	 * 方向
	 */
	public enum Direction {

		/** 递增 */
		asc,

		/** 递减 */
		desc;

		/**
		 * 从String中获取Direction
		 * 
		 * @param value
		 *            值
		 * @return String对应的Direction
		 */
		public static Direction fromString(String value) {
			return Direction.valueOf(value.toLowerCase());
		}

	}

	/** 默认方向 */
	private static final Direction DEFAULT_DIRECTION = Direction.desc;

	/** 属性 */
	private String property;

	/** 方向 */
	private Direction direction = DEFAULT_DIRECTION;

	/**
	 * 初始化一个新创建的Order对象
	 */
	public Order() {
	}

	/**
	 * @param property
	 *            属性
	 * @param direction
	 *            方向
	 */
	public Order(String property, Direction direction) {
		this.property = property;
		this.direction = direction;
	}

	public Order(String property, String direction) {
		this.property = property;
		this.direction = Direction.fromString(direction);
	}

	/**
	 * 返回递增排序
	 * 
	 * @param property
	 *            属性
	 * @return 递增排序
	 */
	public static Order asc(String property) {
		return new Order(property, Direction.asc);
	}

	/**
	 * 返回递减排序
	 * 
	 * @param property
	 *            属性
	 * @return 递减排序
	 */
	public static Order desc(String property) {
		return new Order(property, Direction.desc);
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
	 * 获取方向
	 * 
	 * @return 方向
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * 设置方向
	 * 
	 * @param direction
	 *            方向
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String toSql() {
		return new StringBuffer().append(" ").append(property).append(" ")
				.append(direction.toString()).toString();
	}
}