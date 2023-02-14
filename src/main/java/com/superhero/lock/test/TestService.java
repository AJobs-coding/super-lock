package com.superhero.lock.test;

import com.superhero.lock.aop.anno.Lock;
import org.springframework.stereotype.Component;

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

    }

}
