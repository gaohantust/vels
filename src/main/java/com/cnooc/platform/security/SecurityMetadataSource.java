package com.cnooc.platform.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnooc.platform.interceptor.WebInterceptor;
import com.cnooc.platform.system.log.service.LogService;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.resource.service.ResourceService;
import com.cnooc.platform.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import oshi.util.StringUtil;


/**
 *安全资源加载器
 * @author TONG
 * @date 2020/12/30 11:09 
 * @return 
 */
@Component
public class SecurityMetadataSource {
	private static Logger logger = LoggerFactory.getLogger(SecurityMetadataSource.class);

	private static ResourceService resourceService;
	public static LogService logService;
	private static Map<String, Resource> resMap;
	@Autowired
	public SecurityMetadataSource(ResourceService resourceService,LogService logService){
		this.resourceService=resourceService;
		this.logService=logService;
		loadAuth();
	}
	private static synchronized void loadAuth() {
		List<Resource> authorities = resourceService.findAll();
		resMap = new HashMap<String, Resource>();
		for (Resource auth : authorities) {
			if (StringUtils.isEmpty(auth.getUrl()))
				continue;
			resMap.put(auth.getUrl(), auth);
			logger.info("受保护的URL为:" + auth.getUrl());
		}
	}

	public static List<Resource> getResource(String url) {
		List<Resource> list=new ArrayList<>();
		if (StringUtils.isEmpty(url))
			return list;
		for (Map.Entry<String, Resource> row : resMap.entrySet()) {
			Resource res=row.getValue();
			if("1".equals(res.getType())){
				continue;
			}
			if (url.contains(row.getKey())){
				list.add(res);
			}
		}
		return list;
	}
}
