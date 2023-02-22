package com.superhero.lock.lock;

import com.superhero.lock.lock.test.TestService;
import com.superhero.lock.lock.test.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class LockApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private TestService testService;

    @Test
    void lock() {
        new Thread(() -> {
            User user = new User();
            user.setName("ajobs");
            user.setAge(27);
            testService.lock(user, "001");
        }).start();
        new Thread(() -> {
            User user = new User();
            user.setName("ajobs");
            user.setAge(27);
            testService.lock(user, "001");
        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            User user = new User();
            user.setName("ajobs");
            user.setAge(27);
            testService.lock_01(user, "001");
        }).start();


        try {
            TimeUnit.SECONDS.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        User user = new User();
//        user.setName("ajobs");
//        user.setAge(27);
//        testService.test01(user, "001");
    }

    @Test
    void multiLock() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.multiLock(user, "002");
    }

    @Test
    void redLock() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.redLock(user, "003");
    }

    @Test
    void rwLock_write() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.rwLock_write(user, "004");
    }


    @Test
    void rwLock_read() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.rwLock_read(user, "004");
    }

    @Test
    void superLock_lock() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.superLock_lock(user, "004");
    }

    @Test
    void superLock_multi() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.superLock_multi(user, "004");
    }

    @Test
    void superLock_red() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.superLock_red(user, "004");
    }

    @Test
    void superLock_read() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.superLock_read(user, "004");
    }

    @Test
    void superLock_write() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.superLock_write(user, "004");
    }
}
