package com.cnooc.platform.util.system;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

/**
 * @author LZ.T
 * @version V2.0
 * @ClassName: QueryConditionTransformTuple
 * @Description: TODO 原生SQL列名转化类，用于多数据库的支持，如oracle放回的列名为大写
 * @date 2016-11-26 下午11:54:14
 */
public class QueryConditionTransformTuple extends
        AliasedTupleSubsetResultTransformer {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    public static final AliasedTupleSubsetResultTransformer INSTANCE = new QueryConditionTransformTuple();

    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map<String, Object> result = new HashMap<String, Object>(tuple.length);
        for (int i = 0; i < tuple.length; i++) {
            String alias = aliases[i].toLowerCase();
            if (alias != null) {
                result.put(alias, tuple[i]);
            }
        }
        return result;
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] paramArrayOfString,
                                                   int paramInt) {
        return false;
    }

}
