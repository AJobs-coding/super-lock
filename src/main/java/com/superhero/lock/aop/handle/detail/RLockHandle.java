package com.superhero.lock.aop.handle.detail;

import com.superhero.lock.aop.anno.Lock;
import com.superhero.lock.aop.handle.LockHandle;
import com.superhero.lock.enums.LockHandleTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *Description
 *
 *@author weijianxun
 *@date 2023/2/14 17:27
 */
@Slf4j
@Component
public class RLockHandle implements LockHandle {
    @Autowired
    private LockHandleHelp lockHandleHelp;

    @Override
    public Integer lockHandleType() {
        return LockHandleTypeEnum.R_LOCK.getType();
    }

    @Override
    public void lock(String[] paramNames, Object[] paramValues, Lock lock) {
        if (!paramValid(paramValues)) {
            return;
        }

        String key = lockHandleHelp.getKeyByLock(paramNames, paramValues, lock);
        RLock rLock = lockHandleHelp.getLockByLock(key, lock);
        if (Objects.isNull(rLock)) {
            return;
        }

        long waitTime = lock.waitTime();
        long leaseTime = lock.leaseTime();
        TimeUnit timeUnit = lock.timeUnit();

        try {
            // todo 未获取到锁的操作
            rLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("获取锁失败");
        }

        lockHandleHelp.setLock(rLock);
    }

    @Override
    public void unLock() {
       lockHandleHelp.removeLock();
    }

    private boolean paramValid(Object[] args) {
        return Objects.nonNull(args) && args.length > 0;
    }
}
