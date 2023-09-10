package io.devlabs.keytree.domains.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {

  @Autowired UserRepository userRepository;

  @Test
  @DisplayName("사용자 이메일 주소로 엔티티 조회")
  void findByEmail() {
    // given
    User user = createUser();
    User savedUser = userRepository.save(user);

    // when
    Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

    // then
    assertThat(foundUser.isPresent()).isTrue();
    assertThat(foundUser.get()).isSameAs(savedUser);
    assertThat(foundUser.get()).isEqualTo(savedUser);
  }

  private User createUser() {
    return User.builder()
        .startedAt(LocalDateTime.now())
        .email("devlabs@gmail.com")
        .password("Devlabs1111!")
        .name("devlabs")
        .phone("010-1111-2222")
        .build();
  }
}
