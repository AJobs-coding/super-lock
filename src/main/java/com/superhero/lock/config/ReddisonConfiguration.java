package com.superhero.lock.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.superhero.lock.enums.LockServerTypeEnum;
import com.superhero.lock.util.CollectionsUtil;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *reddison 配置类
 *
 *@author weijianxun
 *@date 2023/2/14 9:40
 */
@Configuration
@ImportAutoConfiguration(classes = {LockHandleConfiguration.class})
@EnableConfigurationProperties(value = {LockServerProperties.class, LockMoreServerProperties.class})
public class ReddisonConfiguration {

    @Autowired
    private LockServerProperties lockServerProperties;

    @Autowired
    private LockMoreServerProperties lockMoreServerProperties;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        initServer(config, lockServerProperties);
        return Redisson.create(config);
    }

    @Bean(value = "reddisonClientFactory", destroyMethod = "shutdown")
    public ReddisonClientFactory reddisonClientFactory() {
        ReddisonClientFactory reddisonClientFactory = new ReddisonClientFactory();

        RedissonClient redissonClient = initReddisionClient();
        if (Objects.nonNull(redissonClient)) {
            reddisonClientFactory.setRedissonClient(redissonClient);
        }

        List<RedissonClient> redissonClients = initMoreReddisonClient();
        if (CollectionsUtil.isNotEmpty(redissonClients)) {
            reddisonClientFactory.setRedissonClients(redissonClients);
        }

        return reddisonClientFactory;
    }

    private RedissonClient initReddisionClient() {
        Config config = new Config();
        boolean initServer = initServer(config, lockServerProperties);
        if (initServer) {
            return Redisson.create(config);
        }
        return null;
    }


    private List<RedissonClient> initMoreReddisonClient() {
        if (CollectionsUtil.isNotEmpty(lockMoreServerProperties.servers)) {
            List<RedissonClient> list = new ArrayList<>(lockMoreServerProperties.getServers().size());
            for (LockServerProperties server : lockMoreServerProperties.getServers()) {
                Config config = new Config();
                boolean initServer = initServer(config, server);
                if (initServer) {
                    list.add(Redisson.create(config));
                }
            }
            if (CollectionsUtil.isNotEmpty(list)) {
                return list;
            }
        }
        return null;
    }

    private boolean initServer(Config config, LockServerProperties lockServerProperties) {
        String serverType = lockServerProperties.getServerType();
        LockServerTypeEnum enumByType = LockServerTypeEnum.getEnumByType(serverType);
        switch (enumByType) {
            case SINGLE:
                initSingleServer(config, lockServerProperties);
                break;
            case CLUSTER:
                initClusterServer(config, lockServerProperties);
                break;
            case MASTER_SALVE:
                initMasterSlaveServer(config, lockServerProperties);
                break;
            default:
                return false;
        }
        return true;
    }

    private void initSingleServer(Config config, LockServerProperties lockServerProperties) {
        SingleServerConfig single = lockServerProperties.getSingle();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        BeanUtil.copyProperties(single, singleServerConfig, CopyOptions.create().ignoreNullValue());
    }

    private void initClusterServer(Config config, LockServerProperties lockServerProperties) {
        ClusterServersConfig cluster = lockServerProperties.getCluster();
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        BeanUtil.copyProperties(cluster, clusterServersConfig, CopyOptions.create().ignoreNullValue());
    }

    private void initMasterSlaveServer(Config config, LockServerProperties lockServerProperties) {
        MasterSlaveServersConfig masterSlave = lockServerProperties.getMasterSlave();
        MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers();
        BeanUtil.copyProperties(masterSlave, masterSlaveServersConfig, CopyOptions.create().ignoreNullValue());
    }
}
