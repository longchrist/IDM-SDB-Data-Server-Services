package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterShippingMod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class masterShippingAO {
    private Connection conn = null;

//    HikariDataSource hikariDataSource;
//    HikariConfig hikariConfig;
//
//    HikariConfig config = new HikariConfig("hikari.properties");
//    HikariDataSource ds = new HikariDataSource(config);

    public String getAllMasterShipping(){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int shippingId = 0;
        String shippingCourier = "";
        String shippingService = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SHIPPING = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            String query = "SELECT shipping_id, shipping_courier, shipping_service, is_active FROM tb_master_shipping";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                shippingId = rs.getInt("SHIPPING_ID");
                shippingCourier = rs.getString("SHIPPING_COURIER");
                shippingService = rs.getString("SHIPPING_SERVICE");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_SHIPPING = new JSONObject();

                DATA_SHIPPING.put("SHIPPING_ID", new Integer(shippingId));
                DATA_SHIPPING.put("SHIPPING_COURIER", new String(shippingCourier));
                DATA_SHIPPING.put("SHIPPING_SERVICE", new String(shippingService));
                DATA_SHIPPING.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_SHIPPING.put(DATA_SHIPPING);
            }

            JSONObjectRoot.put("DATA_MASTER_SHIPPING", DATA_MASTER_SHIPPING);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String getMasterShipping(masterShippingMod MSM){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int shippingId = 0;
        String shippingCourier = "";
        String shippingService = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SHIPPING = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("SELECT shipping_id, shipping_courier, shipping_service, is_active FROM tb_master_shipping WHERE shipping_id = ?");
            ps.setInt(1, MSM.getShippingId());
            rs = ps.executeQuery();

            while (rs.next()) {
                shippingId = rs.getInt("SHIPPING_ID");
                shippingCourier = rs.getString("SHIPPING_COURIER");
                shippingService = rs.getString("SHIPPING_SERVICE");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_SHIPPING = new JSONObject();

                DATA_SHIPPING.put("SHIPPING_ID", new Integer(shippingId));
                DATA_SHIPPING.put("SHIPPING_COURIER", new String(shippingCourier));
                DATA_SHIPPING.put("SHIPPING_SERVICE", new String(shippingService));
                DATA_SHIPPING.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_SHIPPING.put(DATA_SHIPPING);
            }

            JSONObjectRoot.put("DATA_MASTER_SHIPPING", DATA_MASTER_SHIPPING);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveMasterShipping(masterShippingMod MSM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SHIPPING = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_master_shipping VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MSM.getShippingCourier());
            ps.setString(2, MSM.getShippingService());
            ps.setString(3, MSM.getIsActive());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add shipping data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_SHIPPING = new JSONObject();

        DATA_SHIPPING.put("RESULT", new Boolean(result));
        DATA_SHIPPING.put("MESSAGE", new String(messageResult));
        DATA_MASTER_SHIPPING.put(DATA_SHIPPING);

        JSONObjectRoot.put("DATA_MASTER_SHIPPING", DATA_MASTER_SHIPPING);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String updateMasterShipping(masterShippingMod MSM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SHIPPING = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_shipping SET shipping_courier = ?, shipping_service = ?, is_active = ? WHERE shipping_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MSM.getShippingCourier());
            ps.setString(2, MSM.getShippingService());
            ps.setString(3, MSM.getIsActive());
            ps.setInt(4, MSM.getShippingId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update shipping data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_SHIPPING = new JSONObject();

        DATA_SHIPPING.put("RESULT", new Boolean(result));
        DATA_SHIPPING.put("MESSAGE", new String(messageResult));
        DATA_MASTER_SHIPPING.put(DATA_SHIPPING);

        JSONObjectRoot.put("DATA_MASTER_SHIPPING", DATA_MASTER_SHIPPING);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteMasterShipping(masterShippingMod MSM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SHIPPING = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_shipping WHERE shipping_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MSM.getShippingId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete shipping data.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_SHIPPING = new JSONObject();

        DATA_SHIPPING.put("RESULT", new Boolean(result));
        DATA_SHIPPING.put("MESSAGE", new String(messageResult));
        DATA_MASTER_SHIPPING.put(DATA_SHIPPING);

        JSONObjectRoot.put("DATA_MASTER_SHIPPING", DATA_MASTER_SHIPPING);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}