package com.cnooc.platform.system.seq.ctrl;

import com.cnooc.platform.core.BaseCtrl;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.seq.service.SeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author TONG
 * @Version v1.0
 * @date 2020/11/9 13:00
 */
@RestController
@RequestMapping("seq")
public class SeqCtrl extends BaseCtrl {
    @Autowired
    private SeqService seqService;
    @Override
    public BaseService getService() {
        return seqService;
    }
    @RequestMapping("test")
    public String testSeq(){
        String seq=seqService.getSeqValue("SYS_USER");
        return seq;
    }
}
