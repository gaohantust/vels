package com.cnooc.platform.config;

import java.util.ArrayList;
import java.util.List;

import com.cnooc.platform.interceptor.WebInterceptor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
@ComponentScan(basePackages = {"com.cnooc.**"})
public class MvcConfig extends WebMvcConfigurationSupport {

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 1.定义一个converters转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverterExtension();
		// 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		// 3.在converter中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);
		// 4.将converter赋值给HttpMessageConverter
		HttpMessageConverter<?> converter = fastConverter;
		// 5.返回HttpMessageConverters对象
		return new HttpMessageConverters(converter);
	}

	public class FastJsonHttpMessageConverterExtension extends FastJsonHttpMessageConverter {
		FastJsonHttpMessageConverterExtension() {
			List<MediaType> mediaTypes = new ArrayList<>();
			mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"));
			mediaTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
			setSupportedMediaTypes(mediaTypes);
		}
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**");//拦截所有请求
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
