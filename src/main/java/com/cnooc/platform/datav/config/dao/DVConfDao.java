package com.cnooc.platform.datav.config.dao;
/**
 * @ClassName DVConfDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月31日 09:50:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: vels
 * @description: 可视化配置Dao
 * @author: TONG
 * @create: 2021-03-31 09:50
 **/
@Component
public class DVConfDao extends BaseDao<DVConf> {
    @Override
    public Page<Map<String, Object>> findPage(QueryCondition condition) {
        String userid=getCurrentUser().getId();
        String sql="select a.id,a.code,a.name,a.type,b.name as dataset_name,a.status from dv_config a left join dv_dataset b on(a.dataset=b.id) where a.sys_user='"+userid+"'";
        condition.addDicReplace("report_type","type","type_str");
        condition.setSQL(sql);
        return super.findPage(condition);
    }
}
