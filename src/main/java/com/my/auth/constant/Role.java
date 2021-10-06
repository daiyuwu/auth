package com.my.auth.constant;

import java.util.Arrays;

public enum Role {
    SUPER, MANAGER, OPERATOR;

    public Role fromName(String name) {
        return Arrays.stream(values())
                .filter(r -> name.equals(r.name()))
                .findFirst()
                .orElse(null);
    }
}
