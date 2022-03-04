package org.kolesnik.domain;

public enum AppRoles {

    ADMIN("ADMIN"),
    USER("USER"),
    MANAGER("MANAGER");

    private String appRole;

    AppRoles(String s) {
        this.appRole = s;
    }

    public String getAppRole() {
        return appRole;
    }
}
