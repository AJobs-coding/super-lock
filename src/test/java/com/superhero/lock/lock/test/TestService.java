package com.superhero.lock.lock.test;

import com.superhero.lock.anno.Lock;
import com.superhero.lock.anno.MultiLock;
import com.superhero.lock.anno.ReadWriteLock;
import com.superhero.lock.anno.RedLock;
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


    @Lock(prefix = "test01", combineKey = {"#user.name", "#user.age", "#name"})
    public void test01(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(20L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Lock(prefix = "test01", combineKey = {"#user.name", "#user.age", "#name"}, waitTime = 25)
    public void test01_1(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(20L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @MultiLock(lock = @Lock(prefix = "test02", combineKey = {"#user.name", "#user.age", "#name"}))
    public void test02(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @RedLock(lock = @Lock(prefix = "test03", combineKey = {"#user.name", "#user.age", "#name"}))
    public void test03(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @ReadWriteLock(lockType = ReadWriteLockTypeEnum.WRITE,lock = @Lock(prefix = "test03", combineKey = {"#user.name", "#user.age", "#name"}))
    public void test04(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @ReadWriteLock(lockType = ReadWriteLockTypeEnum.READ,lock = @Lock(prefix = "test03", combineKey = {"#user.name", "#user.age", "#name"}))
    public void test05(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
