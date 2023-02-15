package com.superhero.lock.aop.handle;

import com.superhero.lock.aop.anno.Lock;
import com.superhero.lock.aop.anno.MultiLock;
import com.superhero.lock.aop.handle.detail.help.LockHandleHelp;
import com.superhero.lock.aop.handle.detail.help.LockThreadLocalHelp;
import com.superhero.lock.enums.LockTypeEnum;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *抽象类
 *
 *@author weijianxun
 *@date 2023/2/15 10:06
 */
@Component
public abstract class AbstractLockHandle implements LockHandle {

    @Resource
    private LockHandleHelp lockHandleHelp;
    @Resource
    private LockThreadLocalHelp lockThreadLocalHelp;

    @Override
    public void lock(String[] paramNames, Object[] paramValues, Lock lock) {

    }

    @Override
    public void unLock() {
        lockThreadLocalHelp.removeLock();
    }

    @Override
    public void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock) {
        // do nothing
    }


    protected RLock getLockByLockType(String lockName, LockTypeEnum lockType) {
        return lockHandleHelp.getLockByLockType(lockName, lockType);
    }

    protected String getKeyByLock(String[] paramNames, Object[] paramValues, Lock lock) {
        return lockHandleHelp.getKeyByLock(paramNames, paramValues, lock);
    }


    protected void setLockThreadLock(RLock lock) {
        lockThreadLocalHelp.setLock(lock);
    }

    protected void setLockThreadLock(RedissonMultiLock lock) {
        lockThreadLocalHelp.setLock(lock);
    }

    protected void unMultiLock() {
        lockThreadLocalHelp.removeMultiLock();
    }
}
