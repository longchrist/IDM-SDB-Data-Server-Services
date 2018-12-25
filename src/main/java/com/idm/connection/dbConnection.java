/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idm.connection;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 *
 * @author user
 */
public class dbConnection {
    
//    private static HikariConfig config = new HikariConfig();
//    private static HikariDataSource ds;
//    
//    public HikariDataSource getHikariConnection(){
//        config.setJdbcUrl("jdbc:mysql:localhost:3306/db_indomakers_data");
//        config.setUsername("root");
//        config.setPassword("");
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//
//        ds = new HikariDataSource(config);
//        
//        return ds;
//    }
//    
//    public static Connection getConnection() throws SQLException {
//        return ds.getConnection();
//    }
    
    public Connection conn = null;
    public Connection getConnection(){
        Connection resultConn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //conn = DriverManager.getConnection("jdbc:mysql:172.25.1.190:home/sarirasa/data/sispos-tesate.fdb?lc_ctype=ISO8859_1","sispos","masterpos");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_indomakers_data","root","root");

            //Statement stm = conn.createStatement();
            //ResultSet res= stm.executeQuery("SELECT * FROM WTDP_ACTIVITIES");
            //while (res.next()) {
            //    System.out.println("DEBUG WALKTHRU DP NAME : " + res.getString("WILAYAH"));
            //}
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    public void closeConnection(){
        closeConnection();
    }
    
    /*public static void main(String[] args) {
        dbConnection dbc = new dbConnection();
        dbc.getConnection();
    }*/
}
