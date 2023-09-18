package io.devlabs.keytree.commons.annotation;

import io.devlabs.keytree.domains.auth.domain.UserRole;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HasRole {
  UserRole role();
}
