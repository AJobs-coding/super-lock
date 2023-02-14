package com.superhero.lock.config;

import com.superhero.lock.aop.handle.LockHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *@author weijianxun
 *@date 2023/2/14 19:17
 */
public class LockHandleConfiguration {

    @Autowired
    private Map<String, LockHandle> lockHandleMap;


    @Bean("lockHandleMap")
    public Map<Integer,LockHandle> lockHandleMap() {
        Map<Integer, LockHandle> map = new HashMap<>(lockHandleMap.size());
        lockHandleMap.forEach((k,v)->{
            map.put(v.lockHandleType(), v);
        });
        return map;
    }
}
