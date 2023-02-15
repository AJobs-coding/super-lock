package com.superhero.lock.aop.handle.detail;

import com.superhero.lock.aop.anno.Lock;
import com.superhero.lock.aop.anno.MultiLock;
import com.superhero.lock.aop.handle.AbstractLockHandle;
import com.superhero.lock.enums.LockHandleTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *联合锁
 *
 *@author weijianxun
 *@date 2023/2/15 10:11
 */
@Slf4j
@Component
public class MultiRLockHandle extends AbstractLockHandle {

    @Override
    public Integer lockHandleType() {
        return LockHandleTypeEnum.MULTI_LOCK.getType();
    }

    @Override
    public void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock) {
        List<RLock> rLocks = new ArrayList<>(multiLock.value().length);
        for (Lock lock : multiLock.value()) {
            String keyByLock = getKeyByLock(paramNames, paramValues, lock);
            RLock rLock = getLockByLockType(keyByLock, multiLock.lockType());
            rLocks.add(rLock);
        }
        RedissonMultiLock lock = new RedissonMultiLock(rLocks.toArray(new RLock[0]));

        long waitTime = multiLock.waitTime();
        long leaseTime = multiLock.leaseTime();
        TimeUnit timeUnit = multiLock.timeUnit();

        try {
            // todo 未获取到锁的操作
            lock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("获取联合锁失败");
        }

        setLockThreadLock(lock);
    }

    @Override
    public void unLock() {
        unMultiLock();
    }
}
