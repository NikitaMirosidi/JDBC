package JDBC.controller;

import java.sql.SQLException;

public interface BaseController {

    void general();

    void save();

    void get() throws SQLException;

    void getAll() throws SQLException;

    void update() throws SQLException, NoSuchFieldException, IllegalAccessException;

    void delete() throws SQLException;
}