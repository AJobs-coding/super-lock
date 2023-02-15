package com.superhero.lock.aop.handle;

import com.superhero.lock.aop.anno.Lock;
import com.superhero.lock.aop.anno.MultiLock;

/**
 *处理器
 *
 *@author weijianxun
 *@date 2023/2/14 17:25
 */
public interface LockHandle {

    /**
     * 策略
     * @return
     */
    Integer lockHandleType();

    void lock(String[] paramNames, Object[] paramValues, Lock lock);

    void unLock();


    void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock);
}
