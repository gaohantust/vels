package com.cnooc.platform.util.json.request;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @ClassName: RequestJsonNode 
* @Description: TODO 该注解用于绑定请求参数（JSON字符串）
* @author LZ.T
* @date 2016-11-27 上午12:20:28 
* @version V2.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJsonNode {

	/**
	 * 用于绑定的请求参数名字
	 */
	String value() default "";

	/**
	 * 是否必须，默认是
	 */
	boolean required() default true;

}
