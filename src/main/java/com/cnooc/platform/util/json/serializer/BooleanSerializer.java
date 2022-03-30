package com.cnooc.platform.util.json.serializer;


import java.lang.reflect.Field;
/**
* @ClassName: BooleanSerializer 
* @Description: TODO 
* @author LZ.T
* @date 2016-11-27 上午12:20:53 
* @version V2.0
 */
public class BooleanSerializer implements Iserializer{
    public static BooleanSerializer instance = new BooleanSerializer();
	private BooleanSerializer(){		
	}
	@Override
	public String write(Object object,Class<?> clz, boolean isSelf,Field field) {
		if(object == null){
			return "false";
		}else{
			return object.toString();
		}		
	}
}
