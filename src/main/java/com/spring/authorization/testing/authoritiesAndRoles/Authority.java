package com.spring.authorization.testing.authoritiesAndRoles;


public enum Authority {
    USER_READ("user:read"),
    USER_WRITE("user:write"),

    USER_UPDATE("user:update"),

    USER_DELETE("user:delete"),
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write");

    private final String authorities;


    Authority(String authorities) {
        this.authorities = authorities;
    }


    public String getAuthorities() {
        return authorities;
    }

}
