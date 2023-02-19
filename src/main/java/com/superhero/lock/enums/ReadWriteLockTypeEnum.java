package com.superhero.lock.enums;

import lombok.Getter;

import java.util.Objects;

/**
 *读写锁
 *
 *@author weijianxun
 *@date 2023/2/19 20:43
 */
@Getter
public enum ReadWriteLockTypeEnum {

    READ(1, "读锁"),
    WRITE(2,"写锁"),
    UNKNOWN(404, "未知"),
    ;


    private Integer type;

    private String desc;

    ReadWriteLockTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static ReadWriteLockTypeEnum getEnumByType(Integer type) {
        for (ReadWriteLockTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
