package com.cnooc.platform.util.json.serializer;

import java.lang.reflect.Field;

/**
 * @ClassName: Iserializer
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:21:32
 * @version V2.0
 */
public interface Iserializer {
	String write(Object object, Class<?> clz, boolean isSelf, Field field);
}
