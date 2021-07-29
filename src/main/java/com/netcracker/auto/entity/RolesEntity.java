package com.netcracker.auto.entity;

import org.springframework.security.core.GrantedAuthority;

public enum RolesEntity implements GrantedAuthority {
    ADMIN, USER, SELLER;

    @Override
    public String getAuthority() {
        return name();
    }
}
