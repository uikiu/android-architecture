package com.android001.net.db;

import java.util.List;

/**
* class design：
* @author android001
* created at 2017/9/16 下午12:09
 *
 * 对数据库中的表的操作：增删改查，查分为多种
*/

public interface IBaseDao<T> {

    /**
     * 插入数据
     * @param entry
     * @return
     */
    Long insert(T entry);

    /**
     * 更新数据
     * @param entry
     * @param where
     * @return
     */
    int update(T entry,T where);


    /**
     * 删除数据
     * @param where
     * @return
     */
    int delete(T where);

    /**
     * 查询数据
     * @param where
     * @return
     */
    List<T> query(T where);

    List<T> query (T where,String orderBy,Integer startIndex,Integer limit);

    List<T> query(String sql);
}
