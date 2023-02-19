package com.superhero.lock.aop;

import com.superhero.lock.anno.Lock;
import com.superhero.lock.anno.MultiLock;
import com.superhero.lock.anno.ReadWriteLock;
import com.superhero.lock.anno.RedLock;
import com.superhero.lock.aop.handle.LockHandle;
import com.superhero.lock.factory.LockHandleFactory;
import com.superhero.lock.enums.LockHandleTypeEnum;
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

        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.R_LOCK.getType());
        lockHandle.lock(parameterNames, args, lock);
    }

    @After("@annotation(lock)")
    public void after(JoinPoint joinPoint, Lock lock) {
        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.R_LOCK.getType());
        lockHandle.unLock();
    }

    // ===========================================================

    @Before("@annotation(multiLock)")
    public void beforeMulti(JoinPoint joinPoint, MultiLock multiLock) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();

        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.MULTI_LOCK.getType());
        lockHandle.multiLock(parameterNames, args, multiLock);
    }

    @After("@annotation(multiLock)")
    public void afterMulti(JoinPoint joinPoint, MultiLock multiLock) {
        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.MULTI_LOCK.getType());
        lockHandle.unLock();
    }

    // ==============================================================

    @Before("@annotation(redLock)")
    public void beforeRed(JoinPoint joinPoint, RedLock redLock) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();

        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.RED_LOCK.getType());
        lockHandle.redLock(parameterNames, args, redLock);
    }

    @After("@annotation(redLock)")
    public void afterRed(JoinPoint joinPoint, RedLock redLock) {
        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.RED_LOCK.getType());
        lockHandle.unLock();
    }


    // ===============================================================

    @Before("@annotation(readWriteLock)")
    public void beforeRW(JoinPoint joinPoint, ReadWriteLock readWriteLock) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();

        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.RW_LOCK.getType());
        lockHandle.readWriteLock(parameterNames, args, readWriteLock);
    }

    @After("@annotation(readWriteLock)")
    public void afterRW(JoinPoint joinPoint, ReadWriteLock readWriteLock) {
        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.RW_LOCK.getType());
        lockHandle.unLock();
    }
}
