package com.cnooc.platform.util.json.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: RefJsonWrite
 * @Description: TODO 当一个实体被另一个实体引用，被引用的实体定义需要JSON输出的字段
 *               例如订单引用部门，订单JSON序列化时，需要序列化部门的字段
 * @author LZ.T
 * @date 2016-11-27 上午12:18:54
 * @version V2.0
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RefJsonWrite {
	boolean value() default true;
}
