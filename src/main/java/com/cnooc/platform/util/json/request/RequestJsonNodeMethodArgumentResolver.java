package com.cnooc.platform.util.json.request;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: RequestJsonNodeMethodArgumentResolver
 * @Description: TODO 注解@RequestJsonNode解析器
 * @author LZ.T
 * @date 2016-11-27 上午12:20:38
 * @version V2.0
 */
public class RequestJsonNodeMethodArgumentResolver implements HandlerMethodArgumentResolver {
	private static final String JSONBODYATTRIBUTE = "JSON_REQUEST_BODY";
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	public RequestJsonNodeMethodArgumentResolver() {
		System.out.println("============注解@RequestJsonNode解析器启动============");
	}

	/**
	 * 是否是RequestJsonNode注解
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(RequestJsonNode.class)) {
			return true;
		}
		return false;
	}

	/**
	 * 解析方法
	 */
	public final Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest request,
			WebDataBinderFactory binderFactory) throws Exception {
		if (!supportsParameter(parameter)) {
			return WebArgumentResolver.UNRESOLVED;
		}
		String jsonBody = getRequestBody(request);
		RequestJsonNode annot = parameter.getParameterAnnotation(RequestJsonNode.class);
		String name = annot.value();
		Object target = (mavContainer.containsAttribute(name)) ? mavContainer.getModel().get(name)
				: createAttribute(name, parameter, binderFactory, request, jsonBody);
		WebDataBinder binder = binderFactory.createBinder(request, target, name);
		target = binder.getTarget();
		target = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType());
		mavContainer.addAttribute(name, target);
		return target;
	}

	/**
	 * @Title: getRequestBody
	 * @Description:获取JSON数据
	 * @param
	 * @author CST-TONGLZ
	 * @return String
	 * @throws
	 */
	private String getRequestBody(NativeWebRequest webRequest) {
		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		String jsonBody = (String) webRequest.getAttribute(JSONBODYATTRIBUTE,
				NativeWebRequest.SCOPE_REQUEST);
		if (jsonBody == null) {// 缓存处理，提高效率
			try {
				jsonBody = IOUtils.toString(servletRequest.getInputStream(), "utf-8");
				webRequest
						.setAttribute(JSONBODYATTRIBUTE, jsonBody, NativeWebRequest.SCOPE_REQUEST);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return jsonBody;
	}

	/**
	 * @Title: createAttribute
	 * @Description:创建参数对象
	 * @param
	 * @author CST-TONGLZ
	 * @return Object
	 * @throws
	 */
	protected Object createAttribute(String attributeName, MethodParameter parameter,
			WebDataBinderFactory binderFactory, NativeWebRequest request, String body)
			throws Exception {
		JSONObject rootJson = JSON.parseObject(body);
		Class<?> parameterType = parameter.getParameterType();
		if (parameterType.isArray() || List.class.isAssignableFrom(parameterType)) {// 集合类型数组
			return getListByJson(rootJson, parameter, attributeName);
		}
		if (Set.class.isAssignableFrom(parameterType)) {// Set 集合
			List<?> list = getListByJson(rootJson, parameter, attributeName);
			return new HashSet<Object>(list);
		}
		if (String.class.isAssignableFrom(parameterType)) {// String
			return rootJson.getString(attributeName);
		}
		if (int.class.isAssignableFrom(parameterType)
				|| Integer.class.isAssignableFrom(parameterType)) {
			return rootJson.getInteger(attributeName);
		}
		if (Boolean.class.isAssignableFrom(parameterType)||boolean.class.isAssignableFrom(parameterType)) {
			return rootJson.getBoolean(attributeName);
		}
		if (Date.class.isAssignableFrom(parameterType)) {// java.util.Date
			if (rootJson.getString(attributeName) == null)
				return null;
			return format.parse(rootJson.getString(attributeName));
		}
		// 普通类型
		JSONObject object = rootJson.getJSONObject(attributeName);
		return JSON.parseObject(object.toJSONString(), parameterType);
	}

	/**
	 * @Title: getListByJson
	 * @Description:
	 * @param
	 * @author CST-TONGLZ
	 * @return List<?>
	 * @throws
	 */
	private List<?> getListByJson(JSONObject rootJson, MethodParameter parameter,
			String attributeName) {
		Type type = parameter.getGenericParameterType();
		Class<?> componentType = Object.class;
		if (type instanceof ParameterizedType) {// 集合泛型
			componentType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
		}
		if (parameter.getParameterType().isArray()) {// 数组类型
			componentType = parameter.getParameterType().getComponentType();
		}
		JSONArray jsonList = rootJson.getJSONArray(attributeName);
		return JSON.parseArray(jsonList.toJSONString(), componentType);
	}
}
