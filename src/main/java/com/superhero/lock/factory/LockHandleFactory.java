package com.superhero.lock.factory;

import com.superhero.lock.aop.handle.LockHandle;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 *工厂
 *
 *@author weijianxun
 *@date 2023/2/14 20:06
 */
@Component
public class LockHandleFactory {

    @Resource
    @Lazy
    private Map<Integer, LockHandle> lockHandleMap;

    public LockHandle getLockHandle(Integer type) {
        return lockHandleMap.get(type);
    }

}
