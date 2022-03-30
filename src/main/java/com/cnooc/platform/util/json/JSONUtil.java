package com.cnooc.platform.util.json;


import com.cnooc.platform.page.Page;
import com.cnooc.platform.util.json.serializer.Iserializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
* @ClassName: JSONUtil 
* @Description: TODO 
* @author LZ.T
* @date 2016-11-27 上午12:22:24 
* @version V2.0
 */
public class JSONUtil {
	/**
	 * @Title: getJSONByList
	 * @Description: 将LIST转化为JSON
	 * @param
	 * @author CST-TONGLZ
	 * @return String
	 * @throws
	 */
	private static String getJSONByList(Collection<?> coolCollection) {
		List<Object> list = new ArrayList<Object>(coolCollection);
		if (list.isEmpty())
			return "[]";
		StringBuffer bf = new StringBuffer("[");
		int size = list.size();
		Object obj;
		for (int i = 0; i < size; i++) {
			obj = list.get(i);
			bf.append(getJson(obj));
			if (i == size - 1)
				continue;
			bf.append(",");
		}
		bf.append("]");
		return bf.toString();
	}

	/**
	 * @Title: getJsonByEntity
	 * @Description:Obj To Json
	 * @param
	 * @author CST-TONGLZ
	 * @return String
	 * @throws
	 */
	public static String getJson(Object entity) {
		return getJson(entity,true,null);
	}
	public static String getJson(Object entity, boolean isSeft, Field field) {
		if (entity == null) {
			if(entity instanceof String){
				return "null";
			}
			return "null";
		}
		if(entity instanceof Collection<?>)
			return getJSONByList((Collection<?>)entity);
		if(entity instanceof Page){
			return ((Page<?>) entity).toJson();
		}
		Iserializer ser = SerializerFactory.getSerializer(entity.getClass());
		String result = ser.write(entity, entity.getClass(), isSeft, field);
		return result;
	}

}
