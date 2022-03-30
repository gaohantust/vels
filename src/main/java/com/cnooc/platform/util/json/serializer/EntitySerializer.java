package com.cnooc.platform.util.json.serializer;

import com.cnooc.platform.util.json.FieldMethodRelation;
import com.cnooc.platform.util.json.JSONUtil;
import com.cnooc.platform.util.json.SerializerFactory;
import com.cnooc.platform.util.json.annotation.AnnotatedAnalyzer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @ClassName: EntitySerializer
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:21:18
 * @version V2.0
 */
public class EntitySerializer implements Iserializer {
	public static EntitySerializer instance = new EntitySerializer();

	private EntitySerializer() {

	}

	@Override
	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		if (object == null) {
			return "null";
		}
		StringBuilder result = new StringBuilder();
		result.append("{");
		List<FieldMethodRelation> fields = AnnotatedAnalyzer.getFields(clz,
				isSelf, null);
		Field entityField;
		String fieldName;
		Method method;
		Object value = null;
		Iserializer ser;
		for (FieldMethodRelation fieldMethodRelation : fields) {
			entityField = fieldMethodRelation.getField();
			fieldName = entityField.getName();
			method = fieldMethodRelation.getMethod();
			try {
				value = method.invoke(object);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}

			result.append("\"").append(fieldName).append("\":");
			String str = JSONUtil.getJson(value,false,entityField);
			result.append(str);
			result.append(",");
		}
		if (result.length() > 1)
			result.deleteCharAt(result.length() - 1);
		result.append("}");
		return result.toString();
	}

}
