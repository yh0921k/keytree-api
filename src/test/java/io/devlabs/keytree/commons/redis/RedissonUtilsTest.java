package io.devlabs.keytree.commons.redis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedissonUtilsTest {

  @Autowired RedissonUtils redissonUtils;

  @Autowired RedissonClient redissonClient;

  @AfterEach
  public void afterEach() {
    RKeys keys = redissonClient.getKeys();
    keys.flushdb();
  }

  @Test
  @DisplayName("Redisson 단일값 저장 및 조회 테스트")
  public void setValue() {
    // given
    String key = "TestKey";
    String value = "TestValue";

    // when
    redissonUtils.setValue(key, value);
    String foundValue = redissonUtils.getValue(key);

    // then
    assertThat(foundValue).isNotNull();
    assertThat(foundValue).isEqualTo(value);
  }
}
