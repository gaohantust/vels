package com.cnooc.platform.util.json;


import com.cnooc.platform.util.json.serializer.*;

import java.util.Collection;
import java.util.Date;
import java.util.Map;



/**
 * @ClassName: SerializerFactory
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:22:32
 * @version V2.0
 */
public class SerializerFactory {
	public static Iserializer getSerializer(Class<?> clz)
			throws RuntimeException {
		// 字符串
		if (clz == String.class)
			return StringSerializer.instance;
		if (clz == Character.class)
			return StringSerializer.instance;
		// 布尔
		if (clz == Boolean.class)
			return BooleanSerializer.instance;
		// 布尔
		if (clz == boolean.class)
			return BoolSerializer.instance;
		// 数字
		if (clz == int.class || clz == long.class || clz == short.class
				|| clz == double.class || clz == float.class
				|| clz == Integer.class) {
			return NumberSerializer.instance;
		}
		if (Integer.class.isAssignableFrom(clz))
			return NumberSerializer.instance;

		if (Number.class.isAssignableFrom(clz))
			return NumberSerializer.instance;
		// 枚举
		if (clz.isEnum())
			return EnumSerializer.instance;

		if (Collection.class.isAssignableFrom(clz))
			return CollectionSerializer.instance;

		if (clz.isArray()) {
			throw new RuntimeException("系统不支持对数组进行JSON序列化");
		}
		if (Date.class.isAssignableFrom(clz))
			return DateSerializer.instance;

		if (Map.class.isAssignableFrom(clz)) {
			return MapSerializer.instance;
		}
		return EntitySerializer.instance;
	}

}
