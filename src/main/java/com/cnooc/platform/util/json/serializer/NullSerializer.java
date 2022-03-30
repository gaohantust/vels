package com.cnooc.platform.util.json.serializer;

import java.lang.reflect.Field;

/**
 * @ClassName: NullSerializer
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:21:43
 * @version V2.0
 */
public class NullSerializer implements Iserializer {

	@Override
	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		// TODO Auto-generated method stub
		return null;
	}

}
