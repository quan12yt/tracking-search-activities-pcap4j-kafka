package com.quan12yt.trackingsearchhistory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
public class SearchHistoryController {

    Connection connection = null;
    ResultSet resultSet = null;
    Statement statement = null;

    @GetMapping("/get")
    public void get() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager
                    .getConnection("jdbc:sqlite:/C:\\Users\\quan.pham\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\History");
            if(connection.isClosed()){
                return;
            }
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery("SELECT * FROM urls where visit_count > 1");
            while (resultSet.next()) {
                System.out.println("URL [" + resultSet.getString("url") + "]" +
                        ", visit count [" + resultSet.getString("visit_count") + "]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
