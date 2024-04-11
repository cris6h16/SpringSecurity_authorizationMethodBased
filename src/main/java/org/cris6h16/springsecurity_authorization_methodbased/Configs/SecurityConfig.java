package org.cris6h16.springsecurity_authorization_methodbased.Configs;

import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(conf->conf.disable())
                .build();
    }

    // @PreAuthorize("hasAuthority('permission:read')")
    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl rh =  new RoleHierarchyImpl();
        rh.setHierarchy("ROLE_ADMIN > ROLE_USER > permission:read");
        return rh;
    }

}
