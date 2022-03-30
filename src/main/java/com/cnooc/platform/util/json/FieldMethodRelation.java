package com.cnooc.platform.util.json;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName: FieldMethodRelation
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:22:01
 * @version V2.0
 */
public class FieldMethodRelation {
	private Field field;
	private Method method;

	public FieldMethodRelation(Field field, Method method) {
		this.field = field;
		this.method = method;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

}
