package com.cnooc.platform.system.seq.service;

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.seq.dao.SeqDao;
import com.cnooc.platform.system.seq.domain.Seq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author TONG
 * @date 2020/11/9 16:24
 */
@Service
public class SeqService extends BaseService {
    @Autowired
    private SeqDao dao;
    @Override
    public BaseDao getDao() {
        return dao;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public String getSeqValue(String tableName){
        Seq seq = dao.getSeqByTable(tableName);
        if (seq == null) {
            seq = new Seq();
            seq.setId(tableName);
            seq.setTable_name(tableName);
            seq.setCurr_value(100000L);
            seq.setInc(1);
            seq.setPrefix("");
            seq.setPrefix_strategy("");
            dao.create(seq);
            flush();
        }
        return dao.getSeqValue(tableName);
    }
}
