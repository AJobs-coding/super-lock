package com.superhero.lock.lock.test;

import com.superhero.lock.anno.Lock;
import com.superhero.lock.anno.MultiLock;
import com.superhero.lock.anno.ReadWriteLock;
import com.superhero.lock.anno.RedLock;
import com.superhero.lock.anno.SuperLock;
import com.superhero.lock.enums.LockHandleTypeEnum;
import com.superhero.lock.enums.ReadWriteLockTypeEnum;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *Description
 *
 *@author weijianxun
 *@date 2023/2/14 15:50
 */
@Component
public class TestService {


    @Lock(prefix = "lock", combineKey = {"#user.name", "#user.age", "#name"})
    public void lock(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(20L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Lock(prefix = "lock", combineKey = {"#user.name", "#user.age", "#name"}, waitTime = 25)
    public void lock_01(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(20L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @MultiLock(lock = @Lock(prefix = "multi", combineKey = {"#user.name", "#user.age", "#name"}))
    public void multiLock(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @RedLock(lock = @Lock(prefix = "red", combineKey = {"#user.name", "#user.age", "#name"}))
    public void redLock(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @ReadWriteLock(lockType = ReadWriteLockTypeEnum.WRITE,lock = @Lock(prefix = "rw", combineKey = {"#user.name", "#user.age", "#name"}))
    public void rwLock_write(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @ReadWriteLock(lockType = ReadWriteLockTypeEnum.READ,lock = @Lock(prefix = "rw", combineKey = {"#user.name", "#user.age", "#name"}))
    public void rwLock_read(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SuperLock(useLock = LockHandleTypeEnum.R_LOCK, lock = @Lock(prefix = "lock", combineKey = {"#user.name", "#user.age", "#name"}))
    public void superLock_lock(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SuperLock(useLock = LockHandleTypeEnum.MULTI_LOCK, lock = @Lock(prefix = "multi", combineKey = {"#user.name", "#user.age", "#name"}))
    public void superLock_multi(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SuperLock(useLock = LockHandleTypeEnum.RED_LOCK, lock = @Lock(prefix = "red", combineKey = {"#user.name", "#user.age", "#name"}))
    public void superLock_red(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SuperLock(useLock = LockHandleTypeEnum.WRITE, lock = @Lock(prefix = "rw", combineKey = {"#user.name", "#user.age", "#name"}))
    public void superLock_write(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SuperLock(useLock = LockHandleTypeEnum.READ, lock = @Lock(prefix = "rw", combineKey = {"#user.name", "#user.age", "#name"}))
    public void superLock_read(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
