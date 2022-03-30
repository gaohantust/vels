package com.cnooc.platform.util.json.serializer;


import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: DateSerializer
 * @Description: TODO
 * @author LZ.T
 * @date 2016-11-27 上午12:21:11
 * @version V2.0
 */
public class DateSerializer implements Iserializer {
	public static DateSerializer instance = new DateSerializer();
	private final String style = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	private DateSerializer() {

	}

	@Override
	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		if (object == null) {
			return "null";
		}
		Date date = (Date) object;
		SimpleDateFormat format = new SimpleDateFormat(style);
		return "\"" + format.format(date) + "\"";
	}

}
