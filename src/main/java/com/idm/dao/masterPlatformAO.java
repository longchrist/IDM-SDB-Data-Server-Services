package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterPlatformMod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class masterPlatformAO {
    private Connection conn = null;

//    HikariDataSource hikariDataSource;
//    HikariConfig hikariConfig;
//
//    HikariConfig config = new HikariConfig("hikari.properties");
//    HikariDataSource ds = new HikariDataSource(config);

    public String getAllMasterPlatform(){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int platformId = 0;
        String platformType = "";
        String platformName = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PLATFORM = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            String query = "SELECT platform_id, platform_type, platform_name, is_active FROM tb_master_platform";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                platformId = rs.getInt("PLATFORM_ID");
                platformType = rs.getString("PLATFORM_TYPE");
                platformName = rs.getString("PLATFORM_NAME");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_PLATFORM = new JSONObject();

                DATA_PLATFORM.put("PLATFORM_ID", new Integer(platformId));
                DATA_PLATFORM.put("PACKAGING_TYPE", new String(platformType));
                DATA_PLATFORM.put("PACKAGING_NAME", new Integer(platformName));
                DATA_PLATFORM.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_PLATFORM.put(DATA_PLATFORM);
            }

            JSONObjectRoot.put("DATA_MASTER_PLATFORM", DATA_MASTER_PLATFORM);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String getMasterPlatform(masterPlatformMod MPM){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int platformId = 0;
        String platformType = "";
        String platformName = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PLATFORM = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("SELECT platform_id, platform_type, platform_name, is_active FROM tb_master_platform WHERE platform_id = ?");
            ps.setInt(1, MPM.getPlatformId());
            rs = ps.executeQuery();

            while (rs.next()) {
                platformId = rs.getInt("PLATFORM_ID");
                platformType = rs.getString("PLATFORM_TYPE");
                platformName = rs.getString("PLATFORM_NAME");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_PLATFORM = new JSONObject();

                DATA_PLATFORM.put("PLATFORM_ID", new Integer(platformId));
                DATA_PLATFORM.put("PACKAGING_TYPE", new String(platformType));
                DATA_PLATFORM.put("PACKAGING_NAME", new Integer(platformName));
                DATA_PLATFORM.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_PLATFORM.put(DATA_PLATFORM);
            }

            JSONObjectRoot.put("DATA_MASTER_PLATFORM", DATA_MASTER_PLATFORM);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveMasterPlatform(masterPlatformMod MPM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PLATFORM = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_master_platform VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MPM.getPlatformType());
            ps.setString(2, MPM.getPlatformName());
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

        JSONObject DATA_PLATFORM = new JSONObject();

        DATA_PLATFORM.put("RESULT", new Boolean(result));
        DATA_PLATFORM.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PLATFORM.put(DATA_PLATFORM);

        JSONObjectRoot.put("DATA_MASTER_PLATFORM", DATA_MASTER_PLATFORM);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String updateMasterPlatform(masterPlatformMod MPM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PLATFORM = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_platform SET platform_type = ?, platform_name = ?, is_active = ? WHERE platform_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MPM.getPlatformType());
            ps.setString(2, MPM.getPlatformName());
            ps.setString(3, MPM.getIsActive());
            ps.setInt(4, MPM.getPlatformId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update platform data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PLATFORM = new JSONObject();

        DATA_PLATFORM.put("RESULT", new Boolean(result));
        DATA_PLATFORM.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PLATFORM.put(DATA_PLATFORM);

        JSONObjectRoot.put("DATA_MASTER_PLATFORM", DATA_MASTER_PLATFORM);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteMasterPackaging(masterPlatformMod MPM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PLATFORM = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_platform WHERE platform_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPM.getPlatformId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete platform data.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PLATFORM = new JSONObject();

        DATA_PLATFORM.put("RESULT", new Boolean(result));
        DATA_PLATFORM.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PLATFORM.put(DATA_PLATFORM);

        JSONObjectRoot.put("DATA_MASTER_PLATFORM", DATA_MASTER_PLATFORM);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}
