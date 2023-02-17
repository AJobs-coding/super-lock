package com.superhero.lock.config;

import com.superhero.lock.util.CollectionsUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.Objects;

/**
 * reddison 客户端生产工厂
 *
 *@author weijianxun
 *@date 2023/2/17 20:03
 */
@Slf4j
@Getter
@Setter
public class ReddisonClientFactory {

    private RedissonClient redissonClient;

    private List<RedissonClient> redissonClients;

    public void shutdown() {
        if (Objects.nonNull(redissonClient)) {
            shutdownReddisonClient(redissonClient);
        }

        if (CollectionsUtil.isNotEmpty(redissonClients)) {
            for (RedissonClient client : redissonClients) {
                shutdownReddisonClient(redissonClient);
            }
        }
    }

    private void shutdownReddisonClient(RedissonClient redissonClient) {
        try {
            redissonClient.shutdown();
        } catch (Exception e) {
            log.error("com.superhero.lock.config.ReddisonClientFactory.shutdownReddisonClient", e);
        }
    }
}
