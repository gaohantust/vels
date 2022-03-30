package com.cnooc.platform.util.json.serializer;

import java.lang.reflect.Field;
/**
* @ClassName: EnumSerializer 
* @Description: TODO 
* @author LZ.T
* @date 2016-11-27 上午12:21:23 
* @version V2.0
 */
public class EnumSerializer implements Iserializer{
    public static EnumSerializer instance = new EnumSerializer();
    private EnumSerializer(){
    	
    }
	@Override
	public String write(Object object,Class<?> clz, boolean isSelf,Field field) {
		if(object == null) return "null";
		return "\""+object.toString()+"\"";
	}

}
