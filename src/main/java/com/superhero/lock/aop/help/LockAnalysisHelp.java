package com.superhero.lock.aop.help;

import com.superhero.lock.aop.anno.Lock;
import com.superhero.lock.enums.LockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 *锁解析工具类
 *
 *@author weijianxun
 *@date 2023/2/14 14:37
 */
@Component
@Slf4j
public class LockAnalysisHelp {
    @Resource
    private RedissonClient redissonClient;
    private ThreadLocal<RLock> lockThreadLocal = new ThreadLocal<>();

    /**
     * 锁
     * @param args
     * @param lock
     */
    public void lock(Signature signature, Object[] args, Lock lock) {
        if (!paramValid(args)) {
            return;
        }

        MethodSignature methodSignature = (MethodSignature) signature;

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        String prefix = lock.prefix();
        String singleKey = lock.singleKey();
        String[] combineKey = lock.combineKey();
        String constantKey = lock.constantKey();

        // singleKey > combineKey > constantKey
        StringJoiner keyJoiner = new StringJoiner(":");
        keyJoiner.add(prefix);
        if (!StringUtils.isEmpty(singleKey)) {
            keyJoiner.add(objToString(parser.parseExpression(singleKey).getValue(context)));
        } else if (combineKey.length > 0) {
            for (String ck : combineKey) {
                keyJoiner.add(objToString(parser.parseExpression(ck).getValue(context)));
            }
        } else if(!StringUtils.isEmpty(constantKey)) {
            keyJoiner.add(constantKey);
        }


        String key = keyJoiner.toString();
        RLock rLock = getLockByLock(key, lock);
        if (Objects.isNull(rLock)) {
            return;
        }

        long waitTime = lock.waitTime();
        long leaseTime = lock.leaseTime();
        TimeUnit timeUnit = lock.timeUnit();

        try {
            // todo 未获取到锁的操作
            rLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("获取锁失败");
        }

        lockThreadLocal.set(rLock);
    }

    private String objToString(Object obj) {
        if (Objects.isNull(obj)) {
            return "null";
        }
        return obj.toString();
    }


    public void unLock() {
        RLock rLock = lockThreadLocal.get();
        if (Objects.nonNull(rLock)) {
            rLock.unlockAsync();
        }
    }

    private RLock getLockByLock(String lockName, Lock lock) {
        LockTypeEnum lockTypeEnum = lock.lockType();
        switch (lockTypeEnum) {
            case NO_FAIR:
                return redissonClient.getLock(lockName);
            case FAIR:
                return redissonClient.getFairLock(lockName);
            default:
                break;
        }
        return null;
    }


    private boolean paramValid(Object[] args) {
        return Objects.nonNull(args) && args.length > 0;
    }
}
