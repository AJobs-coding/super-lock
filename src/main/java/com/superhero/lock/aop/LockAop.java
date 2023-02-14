package com.superhero.lock.aop;

import com.superhero.lock.aop.anno.Lock;
import com.superhero.lock.aop.handle.LockHandle;
import com.superhero.lock.aop.handle.LockHandleFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *锁切面操作
 *
 *@author weijianxun
 *@date 2023/2/14 14:05
 */
@Aspect
@Order(-1)
@Component
public class LockAop {

    @Resource
    private LockHandleFactory lockHandleFactory;

    @Before("@annotation(lock)")
    public void before(JoinPoint joinPoint, Lock lock) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();

        LockHandle lockHandle = lockHandleFactory.getLockHandle(lock.lockHandle().getType());
        lockHandle.lock(parameterNames, args, lock);
    }


    @After("@annotation(lock)")
    public void after(JoinPoint joinPoint, Lock lock) {
        LockHandle lockHandle = lockHandleFactory.getLockHandle(lock.lockHandle().getType());
        lockHandle.unLock();
    }

}
