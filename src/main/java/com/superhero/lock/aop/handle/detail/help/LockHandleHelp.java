package com.superhero.lock.aop.handle.detail.help;

import com.superhero.lock.aop.anno.Lock;
import com.superhero.lock.enums.LockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.StringJoiner;

/**
 *锁工具类
 *
 *@author weijianxun
 *@date 2023/2/14 14:37
 */
@Slf4j
@Component
public class LockHandleHelp {

    @Autowired
    private RedissonClient redissonClient;

    public RLock getLockByLockType(String lockName, LockTypeEnum lockType) {
        switch (lockType) {
            case NO_FAIR:
                return redissonClient.getLock(lockName);
            case FAIR:
                return redissonClient.getFairLock(lockName);
            default:
                break;
        }
        return null;
    }

    public String getKeyByLock(String[] paramNames, Object[] paramValues, Lock lock) {
        // spel 设置参数
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], paramValues[i]);
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
        } else if (!StringUtils.isEmpty(constantKey)) {
            keyJoiner.add(constantKey);
        } else {
            throw new RuntimeException("指定一下com.superhero.lock.aop.anno.Lock中的key，别瞎搞");
        }

        String key = keyJoiner.toString();

        return key;
    }

    private String objToString(Object obj) {
        if (Objects.isNull(obj)) {
            return "null";
        }
        return obj.toString();
    }
}
