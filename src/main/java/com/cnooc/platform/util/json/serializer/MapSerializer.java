package com.cnooc.platform.util.json.serializer;

import com.cnooc.platform.util.json.JSONUtil;
import com.cnooc.platform.util.json.SerializerFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;



/**
 * @ClassName: MapSerializer
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:21:38
 * @version V2.0
 */
public class MapSerializer implements Iserializer {
	public static MapSerializer instance = new MapSerializer();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		if (object == null)
			return "null";
		Iserializer ser;
		Map<String, Object> map = (Map) object;
		Set<String> set = map.keySet();
		StringBuilder bf = new StringBuilder("{");
		for (String key : set) {
			bf.append("\"").append(key).append("\":");
			Object obj = map.get(key);
			if (obj == null)
				bf.append("\"\"");
			else {
				bf.append(JSONUtil.getJson(obj));
			}
			bf.append(",");
		}
		if (set.size() > 0)
			bf.deleteCharAt(bf.length() - 1);
		bf.append("}");
		return bf.toString();
	}
}
