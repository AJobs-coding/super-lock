package com.superhero.lock.aop.handle.detail.help;

import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 *
 *@author weijianxun
 *@date 2023/2/15 10:32
 */
@Component
public class LockThreadLocalHelp {
    private final ThreadLocal<RLock> lockThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<RedissonMultiLock> multiLockThreadLocal = new ThreadLocal<>();


    public void setLock(RLock lock) {
        lockThreadLocal.set(lock);
    }

    public void removeLock() {
        RLock rLock = lockThreadLocal.get();
        if (Objects.nonNull(rLock)) {
            rLock.unlockAsync();
        }
    }

    public void setLock(RedissonMultiLock lock) {
        multiLockThreadLocal.set(lock);
    }

    public void removeMultiLock() {
        RedissonMultiLock lock = multiLockThreadLocal.get();
        if (Objects.nonNull(lock)) {
            lock.unlockAsync(Thread.currentThread().getId());
        }
    }
}
