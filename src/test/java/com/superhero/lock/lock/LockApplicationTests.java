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
    void test01() {
        new Thread(() -> {
            User user = new User();
            user.setName("ajobs");
            user.setAge(27);
            testService.test01(user, "001");
        }).start();
        new Thread(() -> {
            User user = new User();
            user.setName("ajobs");
            user.setAge(27);
            testService.test01(user, "001");
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
            testService.test01_1(user, "001");
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
    void test02() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.test02(user, "002");
    }

    @Test
    void test03() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.test03(user, "003");
    }

    @Test
    void test04() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.test03(user, "004");
    }


    @Test
    void test05() {
        User user = new User();
        user.setName("ajobs");
        user.setAge(27);
        testService.test03(user, "005");
    }
}
