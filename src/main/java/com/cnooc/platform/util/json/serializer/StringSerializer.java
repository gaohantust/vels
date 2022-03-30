package com.cnooc.platform.util.json.serializer;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @ClassName: StringSerializer
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:21:55
 * @version V2.0
 */
public class StringSerializer implements Iserializer {
	public static StringSerializer instance = new StringSerializer();

	private StringSerializer() {

	}

	@Override
	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		StringBuilder result = new StringBuilder();
		if (object == null) {
			result.append("\"\"");
		} else {
			result.append("\"").append(StringEscapeUtils.escapeJson(object.toString())).append("\"");
		}

		return result.toString();
	}

}
