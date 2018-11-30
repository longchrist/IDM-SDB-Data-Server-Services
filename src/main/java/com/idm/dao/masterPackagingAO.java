package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterPackagingMod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class masterPackagingAO {
    private Connection conn = null;

//    HikariDataSource hikariDataSource;
//    HikariConfig hikariConfig;
//
//    HikariConfig config = new HikariConfig("hikari.properties");
//    HikariDataSource ds = new HikariDataSource(config);

    public String getAllMasterPackaging(){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int packagingId = 0;
        String packagingType = "";
        int packagingPrice = 0;
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PACKAGING = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            String query = "SELECT packaging_id, packaging_type, packaging_price, is_active FROM tb_master_packaging";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                packagingId = rs.getInt("PACKAGING_ID");
                packagingType = rs.getString("PACKAGING_TYPE");
                packagingPrice = rs.getInt("PACKAGING_PRICE");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_PACKAGING = new JSONObject();

                DATA_PACKAGING.put("PACKAGING_ID", new Integer(packagingId));
                DATA_PACKAGING.put("PACKAGING_TYPE", new String(packagingType));
                DATA_PACKAGING.put("PACKAGING_PRICE", new Integer(packagingPrice));
                DATA_PACKAGING.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_PACKAGING.put(DATA_PACKAGING);
            }

            JSONObjectRoot.put("DATA_MASTER_PACKAGING", DATA_MASTER_PACKAGING);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String getMasterPackaging(masterPackagingMod MPM){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int packagingId = 0;
        String packagingType = "";
        int packagingPrice = 0;
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PACKAGING = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("SELECT packaging_id, packaging_type, packaging_price, is_active FROM tb_master_packaging WHERE packaging_id = ?");
            ps.setInt(1, MPM.getPackagingId());
            rs = ps.executeQuery();

            while (rs.next()) {
                packagingId = rs.getInt("PACKAGING_ID");
                packagingType = rs.getString("PACKAGING_TYPE");
                packagingPrice = rs.getInt("PACKAGING_PRICE");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_PACKAGING = new JSONObject();

                DATA_PACKAGING.put("PACKAGING_ID", new Integer(packagingId));
                DATA_PACKAGING.put("PACKAGING_TYPE", new String(packagingType));
                DATA_PACKAGING.put("PACKAGING_PRICE", new Integer(packagingPrice));
                DATA_PACKAGING.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_PACKAGING.put(DATA_PACKAGING);
            }

            JSONObjectRoot.put("DATA_MASTER_PACKAGING", DATA_MASTER_PACKAGING);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveMasterPackaging(masterPackagingMod MPM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PACKAGING = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_master_packaging VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MPM.getPackagingType());
            ps.setInt(2, MPM.getPackagingPrice());
            ps.setString(3, MPM.getIsActive());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add packaging data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PACKAGING = new JSONObject();

        DATA_PACKAGING.put("RESULT", new Boolean(result));
        DATA_PACKAGING.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PACKAGING.put(DATA_PACKAGING);

        JSONObjectRoot.put("DATA_MASTER_PACKAGING", DATA_MASTER_PACKAGING);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String updateMasterPackaging(masterPackagingMod MPM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PACKAGING = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_packaging SET packaging_type = ?, packaging_price = ?, is_active = ? WHERE packaging_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MPM.getPackagingType());
            ps.setInt(2, MPM.getPackagingPrice());
            ps.setString(3, MPM.getIsActive());
            ps.setInt(4, MPM.getPackagingId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update packaging data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PACKAGING = new JSONObject();

        DATA_PACKAGING.put("RESULT", new Boolean(result));
        DATA_PACKAGING.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PACKAGING.put(DATA_PACKAGING);

        JSONObjectRoot.put("DATA_MASTER_PACKAGING", DATA_MASTER_PACKAGING);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteMasterPackaging(masterPackagingMod MPM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PACKAGING = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_packaging WHERE packaging_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPM.getPackagingId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete packaging data.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PACKAGING = new JSONObject();

        DATA_PACKAGING.put("RESULT", new Boolean(result));
        DATA_PACKAGING.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PACKAGING.put(DATA_PACKAGING);

        JSONObjectRoot.put("DATA_MASTER_PACKAGING", DATA_MASTER_PACKAGING);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}
