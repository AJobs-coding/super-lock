package com.superhero.lock.aop.handle.detail;

import com.superhero.lock.anno.Lock;
import com.superhero.lock.anno.MultiLock;
import com.superhero.lock.aop.handle.AbstractLockHandle;
import com.superhero.lock.aop.handle.detail.help.LockThreadLocalHelp;
import com.superhero.lock.enums.LockHandleTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    private LockThreadLocalHelp lockThreadLocalHelp;

    @Override
    public Integer lockHandleType() {
        return LockHandleTypeEnum.MULTI_LOCK.getType();
    }

    @Override
    public void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock) {
        if (!paramValid(paramValues)) {
            return;
        }

        Lock lock = multiLock.lock();
        String lockName = getKeyByLock(paramNames, paramValues, lock);
        List<RLock> locks = getLocksByLockType(lockName, lock.lockType());
        RedissonMultiLock redissonMultiLock = new RedissonMultiLock(locks.toArray(new RLock[0]));

        long waitTime = lock.waitTime();
        long leaseTime = lock.leaseTime();
        TimeUnit timeUnit = lock.timeUnit();

        try {
            // todo 未获取到锁的操作
            redissonMultiLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("获取联合锁失败", e);
        }

        lockThreadLocalHelp.setLock(redissonMultiLock, LockHandleTypeEnum.MULTI_LOCK);
    }

    @Override
    public void unLock() {
        lockThreadLocalHelp.removeLock(LockHandleTypeEnum.MULTI_LOCK);
    }
}
