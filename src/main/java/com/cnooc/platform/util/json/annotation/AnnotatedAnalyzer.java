package com.cnooc.platform.util.json.annotation;


import com.cnooc.platform.util.json.FieldMethodRelation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


/**
* @ClassName: AnnotatedAnalyzer 
* @Description: TODO JSON 序列化处理
* @author LZ.T
* @date 2016-11-27 上午12:17:41 
* @version V2.0
 */
public class AnnotatedAnalyzer {
	public static List<FieldMethodRelation> getFields(Class<?> clz, boolean isSelf, List<FieldMethodRelation> fields){
		if(fields == null) fields = new ArrayList<FieldMethodRelation>();
		Class<?> parentClass = clz.getSuperclass();
		if(parentClass!= null){
			fields = getFields(parentClass, isSelf, fields);
		}
		SelfJsonIngore myself;
		RefJsonWrite ref;
		Method[] methods = clz.getDeclaredMethods();
		Method getter;
		for (Field field : clz.getDeclaredFields()) {
			if(!isEffectiveField(field)) continue;
			if(isSelf == true){
				myself = field.getAnnotation(SelfJsonIngore.class);
				if(myself != null && myself.value()==true) continue; 
			}else{
				ref = field.getAnnotation(RefJsonWrite.class);
				if(ref == null){
					continue;
				} else{
					if(ref.value() == false) continue;
				}
			}
			getter = getMethod(field,methods);
			if(getter != null){
				fields.add(new FieldMethodRelation(field, getter));
			}
			
		}
		
		return fields;
	}

	
	private static Method getMethod(Field field, Method[] methods) {
		String fieldName = field.getName();
		String methodName;
		if(field.getType().getName().equals(boolean.class.getName())){
			methodName = "is"+String.valueOf(fieldName.charAt(0)).toUpperCase()+fieldName.substring(1,fieldName.length());
		}else{
			methodName = "get"+String.valueOf(fieldName.charAt(0)).toUpperCase()+fieldName.substring(1,fieldName.length());
		}
		
		Class<?> rtnType;
		for (Method method : methods) {
			//判断是否是正确的get方法
			if(!method.getName().equals(methodName)) continue;
		    //如果有参数，不是合法的get方法
		    if(method.getParameterTypes().length != 0) continue;
		    //如果没有返回类型，则不合法
		    rtnType = method.getReturnType();
		    if(rtnType == null) continue;
		    //返回类型和属性类型不同，则不合法
		    if(!rtnType.getName().equals(field.getType().getName())) continue;
		    //如果不是public方法，不合法
		    if(!Modifier.isPublic(method.getModifiers())) continue;
		    return method;
		}
		return null;
	}


	private static boolean isEffectiveField(Field field) {
		int modifiers = field.getModifiers();
		if(Modifier.isStatic(modifiers)) return false;
		if(Modifier.isTransient(modifiers)) return false;
		//必须是私有属性，并且存在对应的public的get方法
		if(!Modifier.isPrivate(modifiers)) return false;
		return true;
	}
	
	
}
