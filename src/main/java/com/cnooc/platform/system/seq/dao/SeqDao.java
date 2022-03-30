package com.cnooc.platform.system.seq.dao;

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.system.seq.domain.Seq;
import com.cnooc.platform.system.seq.service.SeqService;
import com.cnooc.platform.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author TONG
 * @Version v1.0
 * @date 2020/11/9 12:04
 */
@Component
public class SeqDao extends BaseDao<Seq> {
    public Seq getSeqByTable(String tableName){
        Seq seq = findByField("table_name", tableName);
        return seq;
    }
    public String getSeqValue(String tableName) {
        flush();
        Seq seq = getSeqByTable(tableName);
        String prefix = seq.getPrefix();
        String prefix_strategy = seq.getPrefix_strategy();
        String sql = "update sys_seq set curr_value=curr_value+inc where table_name=:table_name";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("table_name", tableName);
        executeUpdateSql(sql, param);
        sql = "select curr_value from sys_seq where table_name=:table_name";
        Object currObject = findSingleResultBySql(sql, param);
        long value = Long.parseLong(currObject.toString());
        if (StringUtils.isEmpty(prefix) && StringUtils.isEmpty(prefix_strategy)) {
            return String.valueOf(value);
        }
        if (StringUtils.isEmpty(prefix_strategy)) {
            return prefix + String.valueOf(value);
        }
        SimpleDateFormat sf = new SimpleDateFormat(prefix_strategy);
        String dateStr = sf.format(new Date());
        return dateStr + String.valueOf(value);
    }

    @Override
    public void create(Seq entity) {
        em.persist(entity);
    }
}
