package com.superhero.lock.enums;

import lombok.Getter;

import java.util.Objects;

/**
 *分布式锁类别
 * @see <a href="https://github.com/redisson/redisson/wiki/8.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%92%8C%E5%90%8C%E6%AD%A5%E5%99%A8">分布式锁和同步器</a>
 *
 *@author weijianxun
 *@date 2023/2/14 18:58
 */
@Getter
public enum LockHandleTypeEnum {
    R_LOCK(1, "可重入锁"),
    MULTI_LOCK(2,"联锁"),
    RED_LOCK(3,"红锁"),

    // todo 可扩展
    UNKNOWN(404, "未知"),
    ;


    private int type;
    private String desc;

    LockHandleTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static LockHandleTypeEnum getEnumByType(Integer type) {
        for (LockHandleTypeEnum value : values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
