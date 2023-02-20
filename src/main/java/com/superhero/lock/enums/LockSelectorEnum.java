package com.superhero.lock.enums;

import lombok.Getter;

import java.util.Objects;

/**
 *锁选择器
 *
 *@author weijianxun
 *@date 2023/2/19 22:58
 */
@Getter
public enum LockSelectorEnum {
    LOCK(1, "lock"),
    MULTI(LockHandleTypeEnum.MULTI_LOCK.getType(), "联合锁"),
    RED(LockHandleTypeEnum.RED_LOCK.getType(), "红锁"),
    READ(4, "写锁"),
    WRITE(5,"写锁")
    ;


    private Integer type;

    private String desc;

    LockSelectorEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static LockSelectorEnum getEnumByType(Integer type) {
        for (LockSelectorEnum value : values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }

        return null;
    }
}
