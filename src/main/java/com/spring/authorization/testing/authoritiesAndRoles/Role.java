package com.spring.authorization.testing.authoritiesAndRoles;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.spring.authorization.testing.authoritiesAndRoles.Authority.*;


public enum Role {
    USER(Set.of(PRODUCT_READ)),
    ADMIN(Set.of(PRODUCT_READ, PRODUCT_WRITE)),
    SUPER_ADMIN(Set.of(USER_READ, USER_WRITE, USER_DELETE,USER_UPDATE));

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    private final Set<Authority> authorities;

    Role(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getAuthorities().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getAuthorities()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
