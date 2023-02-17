package com.superhero.lock.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 *Description
 *
 *@author weijianxun
 *@date 2023/2/17 19:53
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "lock.more")
public class LockMoreServerProperties {
    List<LockServerProperties> servers;
}
