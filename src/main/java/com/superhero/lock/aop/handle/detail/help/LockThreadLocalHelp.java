package com.superhero.lock.aop.handle.detail.help;

import com.superhero.lock.enums.LockHandleTypeEnum;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.locks.Lock;

/**
 *
 *@author weijianxun
 *@date 2023/2/15 10:32
 */
@Component
public class LockThreadLocalHelp {
    private final ThreadLocal<RLock> lockThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<RedissonMultiLock> multiLockThreadLocal = new ThreadLocal<>();


    public void setLock(Lock lock, LockHandleTypeEnum lockHandleTypeEnum) {
        switch (lockHandleTypeEnum) {
            case R_LOCK:
            case RW_LOCK:
                lockThreadLocal.set((RLock) lock);
                break;
            case RED_LOCK:
            case MULTI_LOCK:
                multiLockThreadLocal.set((RedissonMultiLock) lock);
                break;
            default:
                break;
        }
    }

    public void removeLock(LockHandleTypeEnum lockHandleTypeEnum) {
        switch (lockHandleTypeEnum) {
            case R_LOCK:
            case RW_LOCK:
                RLock rLock = lockThreadLocal.get();
                if (Objects.nonNull(rLock)) {
                    rLock.unlockAsync();
                    lockThreadLocal.remove();
                }
                break;
            case RED_LOCK:
            case MULTI_LOCK:
                RedissonMultiLock multiLock = multiLockThreadLocal.get();
                if (Objects.nonNull(multiLock)) {
                    multiLock.unlockAsync(Thread.currentThread().getId());
                    multiLockThreadLocal.remove();
                }
                break;
            default:
                break;
        }
    }
}
