package com.cnooc.platform.system.dic.service;

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.page.Filter;
import com.cnooc.platform.system.dic.dao.DicDao;
import com.cnooc.platform.system.dic.domain.Dic;
import com.cnooc.platform.util.json.JSONUtil;
import com.cnooc.platform.util.system.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tong
 * @version V1.0
 * @ClassName DicService.java
 * @Description TODO
 * @createTime 2020年12月14日 20:23:00
 */
@Service
public class DicService extends BaseService<Dic> {
    @Autowired
    private DicDao dicDao;
    @Override
    public BaseDao<Dic> getDao() {
        return dicDao;
    }
    public List<Map<String, String>> findByParentCode(String code) {
        Map<String, String> map = DictionaryCacheService.getMapByGroup(code);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> rowMap;
        for (String key : map.keySet()) {
            rowMap = new HashMap<String, String>();
            rowMap.put("code", key);
            rowMap.put("name", map.get(key));
            list.add(rowMap);
        }
        return list;
    }
    public List<Map<String,Object>> findByTable(String table,String query, List<Filter> filters) {
       return dicDao.findByTable(table,query,filters);
    }
    public List<Map<String,Object>> findByParent( String id) {
        return dicDao.findByParent(id);
    }
}
