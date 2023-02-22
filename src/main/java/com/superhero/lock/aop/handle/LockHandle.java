package com.superhero.lock.aop.handle;

import com.superhero.lock.anno.Lock;
import com.superhero.lock.anno.MultiLock;
import com.superhero.lock.anno.ReadWriteLock;
import com.superhero.lock.anno.RedLock;
import com.superhero.lock.anno.SuperLock;

import java.util.List;

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
    List<Integer> lockHandleType();

    void superLock(String[] paramNames, Object[] paramValues, SuperLock lock);


    void lock(String[] paramNames, Object[] paramValues, Lock lock);

    void unLock();


    void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock);

    void redLock(String[] paramNames, Object[] paramValues, RedLock redLock);

    void readWriteLock(String[] paramNames, Object[] paramValues, ReadWriteLock readWriteLock);

}
