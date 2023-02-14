package com.superhero.lock.config;

import com.superhero.lock.enums.LockServerTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *锁服务配置类
 *
 *@author weijianxun
 *@date 2023/2/14 9:53
 */
@ConfigurationProperties(prefix = "lock.server")
@Getter
@Setter
public class LockServerProperties {

    /**
     * 服务模式：
     * single
     * cluster
     * masterSlave
     * @see LockServerTypeEnum
     */
    private String serverType;

    /**
     * 单节点
     */
    private SingleServerConfig single;

    /**
     * 集群
     */
    private ClusterServersConfig cluster;

    /**
     * 主从
     */
    private MasterSlaveServersConfig masterSlave;
}
