package com.cnooc.platform.util.json.serializer;

import java.lang.reflect.Field;

/**
 * @ClassName: NumberSerializer
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:21:49
 * @version V2.0
 */
public class NumberSerializer implements Iserializer {
	public static NumberSerializer instance = new NumberSerializer();

	private NumberSerializer() {

	}

	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		StringBuilder result = new StringBuilder();
		if (object == null) {
			result.append("0");
		} else {
			result.append(object.toString());
		}

		return result.toString();

	}

}
