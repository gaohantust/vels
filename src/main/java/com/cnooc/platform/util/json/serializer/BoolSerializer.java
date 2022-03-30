package com.cnooc.platform.util.json.serializer;


import java.lang.reflect.Field;

/**
 * @ClassName: BoolSerializer
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:20:59
 * @version V2.0
 */
public class BoolSerializer implements Iserializer {
	public static BoolSerializer instance = new BoolSerializer();

	private BoolSerializer() {
	}

	@Override
	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		return object.toString();
	}
}
