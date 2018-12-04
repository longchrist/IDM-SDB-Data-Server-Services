package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterSubCategoryMod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class masterSubCategoryAO {
    private Connection conn = null;

//    HikariDataSource hikariDataSource;
//    HikariConfig hikariConfig;
//
//    HikariConfig config = new HikariConfig("hikari.properties");
//    HikariDataSource ds = new HikariDataSource(config);

    public String getAllMasterSubCategory(){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int subCategoryId = 0;
        int categoryId = 0;
        String subCategory = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SUB_CATEGORY = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            String query = "SELECT sub_category_id, category_id, sub_category, is_active FROM tb_master_sub_category";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                subCategoryId = rs.getInt("SUB_CATEGORY_ID");
                categoryId = rs.getInt("CATEGORY_ID");
                subCategory = rs.getString("SUB_CATEGORY");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_SUB_CATEGORY = new JSONObject();

                DATA_SUB_CATEGORY.put("SUB_CATEGORY_ID", new Integer(subCategoryId));
                DATA_SUB_CATEGORY.put("CATEGORY_ID", new Integer(categoryId));
                DATA_SUB_CATEGORY.put("SUB_CATEGORY", new String(subCategory));
                DATA_SUB_CATEGORY.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_SUB_CATEGORY.put(DATA_SUB_CATEGORY);
            }

            JSONObjectRoot.put("DATA_MASTER_SUB_CATEGORY", DATA_MASTER_SUB_CATEGORY);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String getMasterPrice(masterSubCategoryMod MSCM){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int subCategoryId = 0;
        int categoryId = 0;
        String subCategory = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SUB_CATEGORY = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("SELECT sub_category_id, category_id, sub_category, is_active FROM tb_master_sub_category WHERE sub_category_id = ?");
            ps.setInt(1, MSCM.getSubCategoryId());
            rs = ps.executeQuery();

            while (rs.next()) {
                subCategoryId = rs.getInt("SUB_CATEGORY_ID");
                categoryId = rs.getInt("CATEGORY_ID");
                subCategory = rs.getString("SUB_CATEGORY");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_SUB_CATEGORY = new JSONObject();

                DATA_SUB_CATEGORY.put("SUB_CATEGORY_ID", new Integer(subCategoryId));
                DATA_SUB_CATEGORY.put("CATEGORY_ID", new Integer(categoryId));
                DATA_SUB_CATEGORY.put("SUB_CATEGORY", new String(subCategory));
                DATA_SUB_CATEGORY.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_SUB_CATEGORY.put(DATA_SUB_CATEGORY);
            }

            JSONObjectRoot.put("DATA_MASTER_SUB_CATEGORY", DATA_MASTER_SUB_CATEGORY);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveMasterSubCategory(masterSubCategoryMod MSCM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SUB_CATEGORY = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_master_sub_category VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MSCM.getSubCategoryId());
            ps.setInt(2, MSCM.getCategoryId());
            ps.setString(3, MSCM.getSubCategory());
            ps.setString(4, MSCM.getIsActive());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add sub category data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_SUB_CATEGORY = new JSONObject();

        DATA_SUB_CATEGORY.put("RESULT", new Boolean(result));
        DATA_SUB_CATEGORY.put("MESSAGE", new String(messageResult));
        DATA_MASTER_SUB_CATEGORY.put(DATA_SUB_CATEGORY);

        JSONObjectRoot.put("DATA_MASTER_SUB_CATEGORY", DATA_MASTER_SUB_CATEGORY);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String updateMasterSubCategory(masterSubCategoryMod MSCM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SUB_CATEGORY = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_sub_category SET sub_category_id = ?, sub_category = ?, is_active = ? WHERE sub_category_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MSCM.getCategoryId());
            ps.setString(2, MSCM.getSubCategory());
            ps.setString(3, MSCM.getIsActive());
            ps.setInt(4, MSCM.getSubCategoryId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update sub category data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_SUB_CATEGORY = new JSONObject();

        DATA_SUB_CATEGORY.put("RESULT", new Boolean(result));
        DATA_SUB_CATEGORY.put("MESSAGE", new String(messageResult));
        DATA_MASTER_SUB_CATEGORY.put(DATA_SUB_CATEGORY);

        JSONObjectRoot.put("DATA_MASTER_SUB_CATEGORY", DATA_MASTER_SUB_CATEGORY);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteMasterSubCategory(masterSubCategoryMod MSCM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_SUB_CATEGORY = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_sub_category WHERE sub_category_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MSCM.getSubCategoryId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete sub_category data.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_SUB_CATEGORY = new JSONObject();

        DATA_SUB_CATEGORY.put("RESULT", new Boolean(result));
        DATA_SUB_CATEGORY.put("MESSAGE", new String(messageResult));
        DATA_MASTER_SUB_CATEGORY.put(DATA_SUB_CATEGORY);

        JSONObjectRoot.put("DATA_MASTER_SUB_CATEGORY", DATA_MASTER_SUB_CATEGORY);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}
