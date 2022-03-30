package com.cnooc.platform.util.json.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: SelfJsonIngore
 * @Description: TODO 一个实体序列化为JSON对象时，定义忽略序列化输出的字段
 * @author LZ.T
 * @date 2016-11-27 上午12:18:43
 * @version V2.0
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SelfJsonIngore {
	boolean value() default true;
}
