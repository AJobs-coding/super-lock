package com.superhero.lock.anno;

import com.superhero.lock.enums.ReadWriteLockTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *读写锁
 *
 *@author weijianxun
 *@date 2023/2/19 20:42
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadWriteLock{
    ReadWriteLockTypeEnum lockType();
    Lock lock();
}
