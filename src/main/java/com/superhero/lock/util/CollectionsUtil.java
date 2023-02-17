package com.superhero.lock.util;

import java.util.Collection;
import java.util.Objects;

/**
 *集合工具类
 *
 *@author weijianxun
 *@date 2023/2/17 20:15
 */
public class CollectionsUtil {

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }
}
