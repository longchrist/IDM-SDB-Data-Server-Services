package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterCustomerMod;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class masterCustomerAO {
    private Connection conn = null;

    public String getAllMasterCustomer(){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int customerId = 0;
        String customerName = "";
        String customerAddress = "";
        String customerProvince = "";
        String customerCity = "";
        String customerZip = "";
        String customerCountry = "";
        String customerPhone = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_CUSTOMER = new JSONArray();

        try {
            conn = dbConnection.getConnection();

            stmt = conn.createStatement();
            String query = "SELECT customer_id, customer_name, customer_address, customer_province, customer_city, customer_zip, customer_country, customer_phone, is_active FROM tb_master_customer";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                customerId = rs.getInt("CUSTOMER_ID");
                customerName = rs.getString("CUSTOMER_NAME");
                customerAddress = rs.getString("CUSTOMER_ADDRESS");
                customerProvince = rs.getString("CUSTOMER_PROVINCE");
                customerCity = rs.getString("CUSTOMER_CITY");
                customerZip = rs.getString("CUSTOMER_ZIP");
                customerCountry = rs.getString("CUSTOMER_COUNTRY");
                customerPhone = rs.getString("CUSTOMER_PHONE");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_CUSTOMER = new JSONObject();

                DATA_CUSTOMER.put("CUSTOMER_ID", new Integer(customerId));
                DATA_CUSTOMER.put("CUSTOMER_NAME", new String(customerName));
                DATA_CUSTOMER.put("CUSTOMER_ADDRESS", new String(customerAddress));
                DATA_CUSTOMER.put("CUSTOMER_PROVINCE", new String(customerProvince));
                DATA_CUSTOMER.put("CUSTOMER_CITY", new String(customerCity));
                DATA_CUSTOMER.put("CUSTOMER_ZIP", new String(customerZip));
                DATA_CUSTOMER.put("CUSTOMER_COUNTRY", new String(customerCountry));
                DATA_CUSTOMER.put("CUSTOMER_PHONE", new String(customerPhone));
                DATA_CUSTOMER.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_CUSTOMER.put(DATA_CUSTOMER);
            }

            JSONObjectRoot.put("DATA_MASTER_CUSTOMER", DATA_MASTER_CUSTOMER);
            jsonResponse += JSONObjectRoot.toString();

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String getMasterCategory(masterCustomerMod MCM){
        String jsonResponse = "";

        ResultSet rs;

        int customerId = 0;
        String customerName = "";
        String customerAddress = "";
        String customerProvince = "";
        String customerCity = "";
        String customerZip = "";
        String customerCountry = "";
        String customerPhone = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_CUSTOMER = new JSONArray();

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("SELECT customer_id, customer_name, customer_address, customer_province, customer_city, customer_zip, customer_country, customer_phone, is_active FROM tb_master_customer WHERE customer_id = ?");
            ps.setInt(1, MCM.getCustomerId());
            rs = ps.executeQuery();

            while (rs.next()) {
                customerId = rs.getInt("CUSTOMER_ID");
                customerName = rs.getString("CUSTOMER_NAME");
                customerAddress = rs.getString("CUSTOMER_ADDRESS");
                customerProvince = rs.getString("CUSTOMER_PROVINCE");
                customerCity = rs.getString("CUSTOMER_CITY");
                customerZip = rs.getString("CUSTOMER_ZIP");
                customerCountry = rs.getString("CUSTOMER_COUNTRY");
                customerPhone = rs.getString("CUSTOMER_PHONE");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_CUSTOMER = new JSONObject();

                DATA_CUSTOMER.put("CUSTOMER_ID", new Integer(customerId));
                DATA_CUSTOMER.put("CUSTOMER_NAME", new String(customerName));
                DATA_CUSTOMER.put("CUSTOMER_ADDRESS", new String(customerAddress));
                DATA_CUSTOMER.put("CUSTOMER_PROVINCE", new String(customerProvince));
                DATA_CUSTOMER.put("CUSTOMER_CITY", new String(customerCity));
                DATA_CUSTOMER.put("CUSTOMER_ZIP", new String(customerZip));
                DATA_CUSTOMER.put("CUSTOMER_COUNTRY", new String(customerCountry));
                DATA_CUSTOMER.put("CUSTOMER_PHONE", new String(customerPhone));
                DATA_CUSTOMER.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_CUSTOMER.put(DATA_CUSTOMER);
            }

            JSONObjectRoot.put("DATA_MASTER_CUSTOMER", DATA_MASTER_CUSTOMER);
            jsonResponse += JSONObjectRoot.toString();

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveMasterCustomer(masterCustomerMod MCM) throws JSONException {
        String jsonResponse = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_CUSTOMER = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_master_customer (customer_name, customer_address, customer_province, customer_city, customer_zip, customer_country, customer_phone, add_date, add_by, edited_date, edited_by, is_active) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MCM.getCustomerName());
            ps.setString(2, MCM.getCustomerAddress());
            ps.setString(3, MCM.getCustomerProvince());
            ps.setString(4, MCM.getCustomerCity());
            ps.setString(5, MCM.getCustomerZip());
            ps.setString(6, MCM.getCustomerCountry());
            ps.setString(7, MCM.getCustomerPhone());
            ps.setString(8, MCM.getAddDate());
            ps.setString(9, MCM.getAddBy());
            ps.setString(10, MCM.getEditedDate());
            ps.setString(11, MCM.getEditedBy());
            ps.setString(12, MCM.getIsActive());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add customer data.";
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_CUSTOMER = new JSONObject();

        DATA_CUSTOMER.put("RESULT", new Boolean(result));
        DATA_CUSTOMER.put("MESSAGE", new String(messageResult));
        DATA_MASTER_CUSTOMER.put(DATA_CUSTOMER);

        JSONObjectRoot.put("DATA_MASTER_CUSTOMER", DATA_MASTER_CUSTOMER);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String updateMasterCustomer(masterCustomerMod MCM) throws JSONException{
        String jsonResponse = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_CUSTOMER = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_customer SET customer_name = ?, customer_address = ?, customer_province = ?, customer_city = ?, customer_zip = ?, customer_country = ?, customer_phone = ?, edited_date = ?, edited_by = ?, is_active = ? WHERE customer_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MCM.getCustomerName());
            ps.setString(2, MCM.getCustomerAddress());
            ps.setString(3, MCM.getCustomerProvince());
            ps.setString(4, MCM.getCustomerCity());
            ps.setString(5, MCM.getCustomerZip());
            ps.setString(6, MCM.getCustomerCountry());
            ps.setString(7, MCM.getCustomerPhone());
            ps.setString(8, MCM.getEditedDate());
            ps.setString(9, MCM.getEditedBy());
            ps.setString(10, MCM.getIsActive());
            ps.setInt(11, MCM.getCustomerId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update customer data.";
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_CUSTOMER = new JSONObject();

        DATA_CUSTOMER.put("RESULT", new Boolean(result));
        DATA_CUSTOMER.put("MESSAGE", new String(messageResult));
        DATA_MASTER_CUSTOMER.put(DATA_CUSTOMER);

        JSONObjectRoot.put("DATA_MASTER_CUSTOMER", DATA_MASTER_CUSTOMER);
        jsonResponse += JSONObjectRoot.toString();


        return jsonResponse;
    }

    public String deleteMasterCategory(masterCustomerMod MCM) throws JSONException{
        String jsonResponse = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_CUSTOMER = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_customer WHERE customer_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MCM.getCustomerId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete customer.";
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_CUSTOMER = new JSONObject();

        DATA_CUSTOMER.put("RESULT", new Boolean(result));
        DATA_CUSTOMER.put("MESSAGE", new String(messageResult));
        DATA_MASTER_CUSTOMER.put(DATA_CUSTOMER);

        JSONObjectRoot.put("DATA_MASTER_CUSTOMER", DATA_MASTER_CUSTOMER);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}
