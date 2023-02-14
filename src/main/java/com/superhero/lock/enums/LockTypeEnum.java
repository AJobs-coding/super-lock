package com.superhero.lock.enums;

import lombok.Getter;

/**
 *锁类型
 *
 *@author weijianxun
 *@date 2023/2/14 14:25
 */
@Getter
public enum LockTypeEnum {
    NO_FAIR(1, "不公平锁"),
    FAIR(2, "公平锁"),
    UNKNOWN(404, "未知");
    /**
     * 锁类型
     */
    private int type;

    /**
     * 描述
     */
    private String desc;

    LockTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
