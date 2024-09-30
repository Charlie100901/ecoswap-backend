package com.ecoswap.ecoswap.user.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Role {
    USER (Arrays.asList(Permission.VIEW_PRODUCTS)),
    ADMIN   (Arrays.asList(Permission.VIEW_USERS));

    private List<Permission> permissions;

}
