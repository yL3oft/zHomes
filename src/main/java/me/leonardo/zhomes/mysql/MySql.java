package me.leonardo.zhomes.mysql;

import me.leonardo.zhomes.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySql {

    private String host = "phpmyadmin-eua.underz.cloud";
    private String port = "3306";
    private String database = "s54_zhomes_customers";
    private String username = "u54_8yEn8i0Imt";
    private String password = "kNWBx3=2!y3Me74C7r5eAW9w";

    private Connection connection;

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if(!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + "?useSSL=false",
                    username, password);
        }
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
