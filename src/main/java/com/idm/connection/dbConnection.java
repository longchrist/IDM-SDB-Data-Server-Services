/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idm.connection;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class dbConnection {

    /*HikariConfig hikariConfig = new HikariConfig("/hikari.properties");
    HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

    public Connection conn = null;
    public Connection getConnection(){
        try {
            conn = hikariDataSource.getConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    public void closeConnection(){
        closeConnection();
    }*/

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:mysql://indomakers.com:3306/db_indomakers_data" );
        config.setUsername( "airoo.valkyrie" );
        config.setPassword( "123qwaszx.," );
        config.addDataSourceProperty("maximumPoolSize", "10");
        config.addDataSourceProperty("leakDetectionThreshold", "2000");
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private dbConnection() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    /*public static void main(String[] args) {
        dbConnection dbc = new dbConnection();
        dbc.getConnection();
    }*/
}
