package com.superhero.lock.anno;


import com.superhero.lock.enums.LockHandleTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SuperLock {
    LockHandleTypeEnum useLock();
    Lock lock();
}
