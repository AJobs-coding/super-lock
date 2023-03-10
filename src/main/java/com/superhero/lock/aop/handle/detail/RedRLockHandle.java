package com.superhero.lock.aop.handle.detail;

import com.superhero.lock.anno.Lock;
import com.superhero.lock.anno.RedLock;
import com.superhero.lock.anno.SuperLock;
import com.superhero.lock.aop.handle.AbstractLockHandle;
import com.superhero.lock.aop.handle.detail.help.LockThreadLocalHelp;
import com.superhero.lock.enums.LockHandleTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *红锁
 *
 *@author weijianxun
 *@date 2023/2/17 20:34
 */
@Slf4j
@Component
public class RedRLockHandle extends AbstractLockHandle {

    @Resource
    private LockThreadLocalHelp lockThreadLocalHelp;

    @Override
    public List<Integer> lockHandleType() {
        return Arrays.asList(LockHandleTypeEnum.RED_LOCK.getType());
    }

    @Override
    public void superLock(String[] paramNames, Object[] paramValues, SuperLock lock) {
        if (!paramValid(paramValues)) {
            return;
        }
        redLock(paramNames, paramValues, lock.lock());
    }

    @Override
    public void redLock(String[] paramNames, Object[] paramValues, RedLock redLock) {
        if (!paramValid(paramValues)) {
            return;
        }
        Lock lock = redLock.lock();
        redLock(paramNames, paramValues, lock);
    }

    private void redLock(String[] paramNames, Object[] paramValues, Lock lock) {
        String lockName = getKeyByLock(paramNames, paramValues, lock);
        List<RLock> locks = getLocksByLockType(lockName, lock.lockType());
        RedissonRedLock redissonRedLock = new RedissonRedLock(locks.toArray(new RLock[0]));

        long waitTime = lock.waitTime();
        long leaseTime = lock.leaseTime();
        TimeUnit timeUnit = lock.timeUnit();

        boolean getLock = false;
        try {
            // todo 未获取到锁的操作
            getLock = redissonRedLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("获取红锁失败", e);
        }

        if (getLock) {
            lockThreadLocalHelp.setLock(redissonRedLock, LockHandleTypeEnum.RED_LOCK);
        }
    }

    @Override
    public void unLock() {
        lockThreadLocalHelp.removeLock(LockHandleTypeEnum.RED_LOCK);
    }
}
