package com.superhero.lock.lock.test;

import com.superhero.lock.aop.anno.Lock;
import com.superhero.lock.aop.anno.MultiLock;
import com.superhero.lock.aop.anno.RedLock;
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
            TimeUnit.SECONDS.sleep(50L);
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
}
