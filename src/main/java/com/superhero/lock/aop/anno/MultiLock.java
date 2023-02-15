package com.superhero.lock.aop.anno;

import com.superhero.lock.enums.LockTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiLock {
    Lock[] value();

    /**
     * 锁有效时长
     * -1 表示使用看门狗，自动续约锁时长；
     * @return
     */
    long leaseTime() default -1;

    /**
     * 默认等待时间
     * @return
     */
    long waitTime() default 10;

    /**
     * 时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 锁类型
     * @return
     */
    LockTypeEnum lockType() default LockTypeEnum.NO_FAIR;
}
