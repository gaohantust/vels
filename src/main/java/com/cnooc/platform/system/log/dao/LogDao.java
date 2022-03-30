package com.cnooc.platform.system.log.dao;/**
 * @ClassName LogDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月30日 14:07:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.system.log.domain.Log;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: vels
 * @description: 操作日志Dao
 * @author: TONG
 * @create: 2020-12-30 14:07
 **/
@Component
public class LogDao extends BaseDao<Log> {
    @Override
    public Page<Map<String, Object>> findPage(QueryCondition condition) {
        String sql="select * from sys_log where res_code is not null order by create_date desc";
        condition.setSQL(sql);
        return super.findPage(condition);
    }
}
