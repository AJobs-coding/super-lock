package com.superhero.lock.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.superhero.lock.enums.LockServerTypeEnum;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *reddison 配置类
 *
 *@author weijianxun
 *@date 2023/2/14 9:40
 */
@Configuration
@ImportAutoConfiguration(classes = {LockHandleConfiguration.class})
@EnableConfigurationProperties(value = {LockServerProperties.class})
public class ReddisonConfiguration {

    @Autowired
    private LockServerProperties lockServerProperties;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        initServer(config);
        return Redisson.create(config);
    }


    private void initServer(Config config) {
        String serverType = lockServerProperties.getServerType();
        LockServerTypeEnum enumByType = LockServerTypeEnum.getEnumByType(serverType);
        switch (enumByType) {
            case SINGLE:
                initSingleServer(config);
                break;
            case CLUSTER:
                initClusterServer(config);
                break;
            case MASTER_SALVE:
                initMasterSlaveServer(config);
                break;
            default:
                break;
        }
    }

    private void initSingleServer(Config config) {
        SingleServerConfig single = lockServerProperties.getSingle();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        BeanUtil.copyProperties(single, singleServerConfig, CopyOptions.create().ignoreNullValue());
    }

    private void initClusterServer(Config config) {
        ClusterServersConfig cluster = lockServerProperties.getCluster();
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        BeanUtil.copyProperties(cluster, clusterServersConfig, CopyOptions.create().ignoreNullValue());
    }

    private void initMasterSlaveServer(Config config) {
        MasterSlaveServersConfig masterSlave = lockServerProperties.getMasterSlave();
        MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers();
        BeanUtil.copyProperties(masterSlave, masterSlaveServersConfig, CopyOptions.create().ignoreNullValue());
    }
}
