package com.skillstest.librarian.data.security;

public enum Roles {
    READER ("READER"),
    WRITER ("WRITER"),
    EDITOR ("EDITOR"),
    ADMIN ("ADMIN");
    private final String title;
    private final String persistedTitle;
    private Roles(String title) {
        this.title = title;
        persistedTitle = "ROLE_" + title;
    }
    public String getTitle() {
        return title;
    }
    public String getPersistedTitle() {return persistedTitle;}
}
