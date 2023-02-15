package com.superhero.lock.aop.handle;

import com.superhero.lock.aop.anno.Lock;
import com.superhero.lock.aop.handle.detail.AbstractDefaultLockHandle;
import com.superhero.lock.aop.handle.detail.help.LockHandleHelp;
import com.superhero.lock.enums.LockTypeEnum;
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
public abstract class AbstractLockHandle extends AbstractDefaultLockHandle implements LockHandle {

    @Resource
    private LockHandleHelp lockHandleHelp;

    protected RLock getLockByLockType(String lockName, LockTypeEnum lockType) {
        return lockHandleHelp.getLockByLockType(lockName, lockType);
    }

    protected String getKeyByLock(String[] paramNames, Object[] paramValues, Lock lock) {
        return lockHandleHelp.getKeyByLock(paramNames, paramValues, lock);
    }
}
