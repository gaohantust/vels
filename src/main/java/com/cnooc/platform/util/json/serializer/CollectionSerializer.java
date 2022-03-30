package com.cnooc.platform.util.json.serializer;


import com.cnooc.platform.util.json.SerializerFactory;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;


/**
 * @ClassName: CollectionSerializer
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:21:04
 * @version V2.0
 */
public class CollectionSerializer implements Iserializer {
	public static CollectionSerializer instance = new CollectionSerializer();

	private CollectionSerializer() {
	}

	@Override
	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		StringBuilder result = new StringBuilder();
		result.append("[");
		Type fieldClass = field.getGenericType();
		if (fieldClass == null) {
			throw new RuntimeException("json序列化错误，没有指定列表对应的数据类型");
		}
		ParameterizedType pt = (ParameterizedType) fieldClass;
		Type genType = pt.getActualTypeArguments()[0];
		Collection<?> list = (Collection<?>) object;
		if (list == null || list.size() == 0) {
			return "[]";
		}
		for (Object entity : list) {
			Class<?> entityClass =null;
			if(genType instanceof ParameterizedTypeImpl){
				entityClass=((ParameterizedTypeImpl)genType).getRawType();
			}else{
				entityClass=(Class<?>) genType;
			}
			Iserializer ser = SerializerFactory.getSerializer(entityClass);
			ParameterizedTypeImpl imp;
			result.append(ser.write(entity,entityClass, true, field));
			result.append(",");
		}
		result.deleteCharAt(result.length() - 1);
		result.append("]");

		return result.toString();
	}

}
