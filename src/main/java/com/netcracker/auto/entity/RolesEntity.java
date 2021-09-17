package com.netcracker.auto.entity;

import org.springframework.security.core.GrantedAuthority;

public enum RolesEntity implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, ROLE_SELLER;

    @Override
    public String getAuthority() {
        return name();
    }
}
