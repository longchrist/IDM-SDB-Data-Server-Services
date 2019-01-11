package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterShippingMod;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class masterShippingAO {
    private Connection conn = null;

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
            conn = dbConnection.getConnection();

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

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String getMasterShipping(masterShippingMod MSM){
        String jsonResponse = "";

        ResultSet rs;

        int shippingId = 0;
        String shippingCourier = "";
        String shippingService = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SHIPPING = new JSONArray();

        try {
            conn = dbConnection.getConnection();

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

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveMasterShipping(masterShippingMod MSM) throws JSONException {
        String jsonResponse = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SHIPPING = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_master_shipping (shipping_courier, shipping_service, add_date, add_by, edited_date, edited_by, is_active) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MSM.getShippingCourier());
            ps.setString(2, MSM.getShippingService());
            ps.setString(3, MSM.getAddDate());
            ps.setString(4, MSM.getAddBy());
            ps.setString(5, MSM.getEditedDate());
            ps.setString(6, MSM.getEditedBy());
            ps.setString(7, MSM.getIsActive());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add shipping data.";
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
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

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SHIPPING = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_shipping SET shipping_courier = ?, shipping_service = ?, edited_date = ?, edited_by = ?, is_active = ? WHERE shipping_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MSM.getShippingCourier());
            ps.setString(2, MSM.getShippingService());
            ps.setString(3, MSM.getEditedDate());
            ps.setString(4, MSM.getEditedBy());
            ps.setString(5, MSM.getIsActive());
            ps.setInt(6, MSM.getShippingId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update shipping data.";
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
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

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SHIPPING = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_shipping WHERE shipping_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MSM.getShippingId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete shipping data.";
            }

            ps.close();
            conn.close();
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
