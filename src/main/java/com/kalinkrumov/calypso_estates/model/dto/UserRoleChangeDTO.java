package com.kalinkrumov.calypso_estates.model.dto;

import com.kalinkrumov.calypso_estates.model.entity.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserRoleChangeDTO {

    private List<UserRole> roles = new ArrayList<>();

    public List<UserRole> getRoles() {
        return roles;
    }

    public UserRoleChangeDTO setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }
}
