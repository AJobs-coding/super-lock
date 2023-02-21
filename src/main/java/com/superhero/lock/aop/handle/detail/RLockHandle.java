package com.superhero.lock.aop.handle.detail;

import com.superhero.lock.anno.Lock;
import com.superhero.lock.anno.SuperLock;
import com.superhero.lock.aop.handle.AbstractLockHandle;
import com.superhero.lock.aop.handle.detail.help.LockThreadLocalHelp;
import com.superhero.lock.enums.LockHandleTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
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
public class RLockHandle extends AbstractLockHandle {
    @Autowired
    private LockThreadLocalHelp lockThreadLocalHelp;

    @Override
    public List<Integer> lockHandleType() {
        return Arrays.asList(LockHandleTypeEnum.R_LOCK.getType());
    }


    @Override
    public void superLock(String[] paramNames, Object[] paramValues, SuperLock lock) {
        lock(paramNames, paramValues, lock.lock());
    }

    @Override
    public void lock(String[] paramNames, Object[] paramValues, Lock lock) {
        if (!paramValid(paramValues)) {
            return;
        }

        String key = getKeyByLock(paramNames, paramValues, lock);
        RLock rLock = getLockByLockType(key, lock.lockType());
        if (Objects.isNull(rLock)) {
            return;
        }

        long waitTime = lock.waitTime();
        long leaseTime = lock.leaseTime();
        TimeUnit timeUnit = lock.timeUnit();

        boolean getLock = false;
        try {
            // todo 未获取到锁的操作
            getLock = rLock.tryLock(waitTime, leaseTime, timeUnit);
//            System.out.println("获取到锁了" + b + ",  waitTime:" + waitTime);
//            if (!b) {
//                System.out.println("获取失败" + Thread.currentThread().getId());
//            }
        } catch (InterruptedException e) {
            log.error("获取锁失败", e);
        }

        // 记得释放锁
        if (getLock) {
            lockThreadLocalHelp.setLock(rLock, LockHandleTypeEnum.R_LOCK);
        }
    }

    @Override
    public void unLock() {
        lockThreadLocalHelp.removeLock(LockHandleTypeEnum.R_LOCK);
    }
}
