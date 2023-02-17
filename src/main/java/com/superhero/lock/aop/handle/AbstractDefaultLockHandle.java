package com.superhero.lock.aop.handle;

import com.superhero.lock.aop.anno.Lock;
import com.superhero.lock.aop.anno.MultiLock;
import com.superhero.lock.aop.anno.RedLock;

/**
 *默认实现中间层
 *
 *@author weijianxun
 *@date 2023/2/15 13:46
 */
public abstract class AbstractDefaultLockHandle implements LockHandle {
    @Override
    public void lock(String[] paramNames, Object[] paramValues, Lock lock) {

    }

    @Override
    public void unLock() {
        throw new RuntimeException("记得把锁关闭一下");
    }

    @Override
    public void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock) {

    }
    @Override
    public void redLock(String[] paramNames, Object[] paramValues, RedLock redLock) {

    }
}
