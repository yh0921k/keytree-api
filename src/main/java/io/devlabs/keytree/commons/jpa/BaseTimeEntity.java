package io.devlabs.keytree.commons.jpa;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

  @CreatedDate
  @Comment("생성일시")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Comment("최근수정일시")
  private LocalDateTime modifiedAt;
}
