package io.devlabs.keytree.commons.redis;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedissonUtils {

  private final RedissonClient redissonClient;

  public void setValue(String key, String value) {
    redissonClient.getBucket(key).set(value);
  }

  public Optional<String> getValue(String key) {
    String value = (String) redissonClient.getBucket(key).get();
    return Optional.ofNullable(value);
  }
}
