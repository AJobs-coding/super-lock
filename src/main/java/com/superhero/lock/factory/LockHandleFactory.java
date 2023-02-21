package com.superhero.lock.factory;

import com.superhero.lock.aop.handle.LockHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 *工厂
 *
 *@author weijianxun
 *@date 2023/2/14 20:06
 */
@Component
public class LockHandleFactory {
    @Autowired
    private Map<String, LockHandle> lockHandleMap;

    private Map<Integer, LockHandle> typeToHandleMap;

    @PostConstruct
    public void init() {
        typeToHandleMap = new HashMap<>();
        lockHandleMap.forEach((k, v) -> {
            for (Integer type : v.lockHandleType()) {
                typeToHandleMap.put(type, v);
            }
        });
    }

    public LockHandle getLockHandle(Integer type) {
        return typeToHandleMap.get(type);
    }

}
