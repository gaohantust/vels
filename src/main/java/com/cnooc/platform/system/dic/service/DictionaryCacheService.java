package com.cnooc.platform.system.dic.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cnooc.platform.system.dic.domain.Dic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @ClassName: DicUtil
 * @Description:字典缓存服务
 * @author CST-TONGLZ
 * @date 2015-10-16 下午4:38:28
 * 
 */
@Component
public class DictionaryCacheService {
	private static DicService dictionaryService;
	private static Map<String, Map<String, String>> dicMap = null;
	private static Map<String, Dic> runMap = new HashMap<String, Dic>();

	@Autowired
	public void setSysLogService(DicService dictionaryService) {
		DictionaryCacheService.dictionaryService = dictionaryService;
	}

	/**
	 * @Title: loadDicMap
	 * @Description:刷新缓存
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public static synchronized void loadDicMap() {
		dicMap = new HashMap<String, Map<String, String>>();
		List<Dic> list = dictionaryService.findAll();
		for (Dic dictionary : list) {
			if (dictionary.getParent()!=null)
				continue;
			runMap.put(dictionary.getId(), dictionary);
			String code = dictionary.getCode();
			dicMap.put(code, new LinkedHashMap<String, String>());
		}
		for (Dic dictionary : list) {
			if (dictionary.getParent()==null)
				continue;
			String pid=dictionary.getParent().getId();
			Dic parentDictionary = runMap.get(pid);
			if (parentDictionary == null)
				continue;
			String group = parentDictionary.getCode();
			String code = dictionary.getCode();
			String name = dictionary.getName();
			if (!dicMap.containsKey(group))
				continue;
			dicMap.get(group).put(code, name);
		}
		runMap.clear();
	}

	/**
	 * @Title: getDicName
	 * @Description:获取名称
	 * @param
	 * @author CST-TONGLZ
	 * @return String
	 * @throws
	 */
	public static String getDicName(String group, String code) {
		if (dicMap == null)
			loadDicMap();
		if (dicMap.containsKey(group)) {
			Map<String, String> map = dicMap.get(group);
			if (map.containsKey(code))
				return map.get(code);
		}
		return "";
	}

	public static Map<String, String> getMapByGroup(String group) {
		if (dicMap == null)
			loadDicMap();
		if (dicMap.containsKey(group))
			return dicMap.get(group);
		return new HashMap<String, String>();
	}

}
