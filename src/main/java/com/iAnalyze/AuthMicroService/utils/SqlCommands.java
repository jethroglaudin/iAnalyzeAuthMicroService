package com.iAnalyze.AuthMicroService.utils;


public abstract class SqlCommands {
    public static final String SQL_CREATE = String.format("INSERT INTO usersauth(id, username, email, password) VALUES(?, ?, ?, ?)");
    public static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM usersauth WHERE email = ?";
    //    public static final String SQL_FIND_BY_ID = "SELECT id, username, email, password " +
//            "FROM usersauth WHERE id = ?";
    public static final String SQL_FIND_BY_ID = String.format("SELECT * FROM usersauth WHERE id = ?");
    public static final String SQL_FIND_BY_EMAIL = "SELECT * FROM usersauth WHERE email = ?";
}
