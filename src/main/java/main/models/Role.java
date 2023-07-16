package main.models;

public enum Role {
    USER,
    ADMIN;

//    public String getRoleName() {
//        return this.name();
//    }

public String getAuthority() {
    return name();
}
}