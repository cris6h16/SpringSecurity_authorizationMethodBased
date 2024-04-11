package org.cris6h16.springsecurity_authorization_methodbased.CustomAnnotations;

import org.springframework.security.access.prepost.PostAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PostAuthorize("returnObject.getBody().owner == authentication.name")
public @interface OwnerInResponseSameToAuthenticationName {
}
