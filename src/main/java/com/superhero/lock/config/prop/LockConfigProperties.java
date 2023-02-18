package com.superhero.lock.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 *config 属性配置
 * @see Config
 *@author weijianxun
 *@date 2023/2/18 12:26
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "lock.config")
public class LockConfigProperties {
    /**
     * 看门狗时间，单位毫秒
     */
    private Duration lockWatchdogTimeout = Duration.ofMillis(30 * 1000);

    /**
     * 可扩展
     * @see org.redisson.config.Config
     */
}
