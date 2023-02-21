package com.superhero.lock.aop.handle.detail;

import com.superhero.lock.anno.Lock;
import com.superhero.lock.anno.ReadWriteLock;
import com.superhero.lock.anno.SuperLock;
import com.superhero.lock.aop.handle.AbstractLockHandle;
import com.superhero.lock.aop.handle.detail.help.LockHandleHelp;
import com.superhero.lock.aop.handle.detail.help.LockThreadLocalHelp;
import com.superhero.lock.enums.LockHandleTypeEnum;
import com.superhero.lock.enums.ReadWriteLockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *读写锁
 *
 *@author weijianxun
 *@date 2023/2/19 22:10
 */
@Slf4j
@Component
public class ReadWriteLockHanle extends AbstractLockHandle {

    @Autowired
    private LockThreadLocalHelp lockThreadLocalHelp;
    @Autowired
    private LockHandleHelp lockHandleHelp;

    @Override
    public List<Integer> lockHandleType() {
        return Arrays.asList(LockHandleTypeEnum.RW_LOCK.getType(), LockHandleTypeEnum.READ.getType(), LockHandleTypeEnum.WRITE.getType());
    }


    @Override
    public void superLock(String[] paramNames, Object[] paramValues, SuperLock lock) {
        if (!paramValid(paramValues)) {
            return;
        }
        LockHandleTypeEnum lockHandleTypeEnum = lock.useLock();
        if (Objects.equals(lockHandleTypeEnum, LockHandleTypeEnum.READ)) {
            readWriteLock(paramNames, paramValues, lock.lock(), ReadWriteLockTypeEnum.READ);
        } else if (Objects.equals(lockHandleTypeEnum, LockHandleTypeEnum.WRITE)) {
            readWriteLock(paramNames, paramValues, lock.lock(), ReadWriteLockTypeEnum.WRITE);
        }
    }

    @Override
    public void readWriteLock(String[] paramNames, Object[] paramValues, ReadWriteLock readWriteLock) {
        if (!paramValid(paramValues)) {
            return;
        }

        ReadWriteLockTypeEnum readWriteLockTypeEnum = readWriteLock.lockType();
        Lock lock = readWriteLock.lock();
        readWriteLock(paramNames, paramValues, lock, readWriteLockTypeEnum);
    }

    private void readWriteLock(String[] paramNames, Object[] paramValues, Lock lock, ReadWriteLockTypeEnum readWriteLockTypeEnum) {
        String lockName = getKeyByLock(paramNames, paramValues, lock);
        RReadWriteLock rwLock = lockHandleHelp.getRWLock(lockName);
        boolean write = Objects.equals(readWriteLockTypeEnum, ReadWriteLockTypeEnum.WRITE);
        boolean read = Objects.equals(readWriteLockTypeEnum, ReadWriteLockTypeEnum.READ);
        RLock rLock = null;
        if (write) {
            rLock = rwLock.writeLock();
        } else if (read) {
            rLock = rwLock.readLock();
        } else {
            throw new RuntimeException("读写锁二选一给个值，别瞎整");
        }

        long waitTime = lock.waitTime();
        long leaseTime = lock.leaseTime();
        TimeUnit timeUnit = lock.timeUnit();

        boolean getLock = false;
        try {
            getLock = rLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            if (read) {
                log.error("获取读锁失败", e);
            } else {
                log.error("获取写锁失败", e);
            }
        }

        if(getLock) {
            lockThreadLocalHelp.setLock(rLock, LockHandleTypeEnum.RW_LOCK);
        }
    }

    @Override
    public void unLock() {
        lockThreadLocalHelp.removeLock(LockHandleTypeEnum.RW_LOCK);
    }
}
