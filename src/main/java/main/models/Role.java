package main.models;

public enum Role {
    USER,
    ADMIN;

public String getAuthority() {
    return name();
}
}