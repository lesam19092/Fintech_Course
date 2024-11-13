package org.example.authentication_service.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority  {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
