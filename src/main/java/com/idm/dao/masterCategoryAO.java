package com.idm.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.idm.connection.dbConnection;

public class masterCategoryAO {
    private Connection conn = null;

//    HikariDataSource hikariDataSource;
//    HikariConfig hikariConfig;
//
//    HikariConfig config = new HikariConfig("hikari.properties");
//    HikariDataSource ds = new HikariDataSource(config);

    public String getAllmasterBrand(){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int categoryId = 0;
        String categoryName = "";
        String categoryDescriptions = "";
        String addDate = "";
        String addBy = "";
        String editedDate = "";
        String editedBy = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_CATEGORY = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            String query = "SELECT category_id, category_name, category_descriptions, add_date, add_by, edited_date, edited_by, is_active FROM tb_master_category";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                categoryId = rs.getInt("CATEGORY_ID");
                categoryName = rs.getString("CATEGORY_NAME");
                categoryDescriptions = rs.getString("CATEGORY_DESCRIPTIONS");
                addDate = rs.getString("ADD_DATE");
                addBy = rs.getString("ADD_BY");
                editedDate = rs.getString("EDITED_DATE");
                editedBy = rs.getString("EDITED_BY");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_CATEGORY = new JSONObject();

                DATA_CATEGORY.put("CATEGORY_ID", new Integer(categoryId));
                DATA_CATEGORY.put("CATEGORY_NAME", new String(categoryName));
                DATA_CATEGORY.put("CATEGORY_DESCRIPTIONS", new String(categoryDescriptions));
                DATA_CATEGORY.put("ADD_DATE", new String(addDate));
                DATA_CATEGORY.put("ADD_BY", new String(addBy));
                DATA_CATEGORY.put("EDITED_DATE", new String(editedDate));
                DATA_CATEGORY.put("EDITED_BY", new String(editedBy));
                DATA_CATEGORY.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_CATEGORY.put(DATA_CATEGORY);
            }

            JSONObjectRoot.put("DATA_MASTER_CATEGORY", DATA_MASTER_CATEGORY);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    /*public String getMasterBrand(masterBrandMod MBM){
        String jsonResponse = "";

        String brandId = "";
        String brandName = "";

        Statement stmt = null;
        ResultSet rs;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_BRAND = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("SELECT brand_id, brand_name FROM m_brand WHERE brand_id = ?");
            ps.setString(1, MBM.getBrandId());
            rs = ps.executeQuery();

            while (rs.next()) {
                brandId = rs.getString("BRAND_ID");
                brandName = rs.getString("BRAND_NAME");

                JSONObject DATA_BRAND = new JSONObject();

                DATA_BRAND.put("BRAND_ID", new String(brandId));
                DATA_BRAND.put("BRAND_NAME", new String(brandName));

                DATA_MASTER_BRAND.put(DATA_BRAND);
            }

            JSONObjectRoot.put("DATA_MASTER_BRAND", DATA_MASTER_BRAND);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveMasterBrand(masterBrandMod MBM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_BRAND = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO m_brand VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MBM.getBrandId());
            ps.setString(2, MBM.getBrandName());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add brand.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_BRAND = new JSONObject();

        DATA_BRAND.put("RESULT", new Boolean(result));
        DATA_BRAND.put("MESSAGE", new String(messageResult));
        DATA_MASTER_BRAND.put(DATA_BRAND);

        JSONObjectRoot.put("DATA_MASTER_BRAND", DATA_MASTER_BRAND);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String updateMasterBrand(masterBrandMod MBM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_BRAND = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("UPDATE m_brand SET brand_name = ? WHERE brand_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MBM.getBrandName());
            ps.setString(2, MBM.getBrandId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update brand.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_BRAND = new JSONObject();

        DATA_BRAND.put("RESULT", new Boolean(result));
        DATA_BRAND.put("MESSAGE", new String(messageResult));
        DATA_MASTER_BRAND.put(DATA_BRAND);

        JSONObjectRoot.put("DATA_MASTER_BRAND", DATA_MASTER_BRAND);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteMasterBrand(masterBrandMod MBM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_BRAND = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM m_brand WHERE brand_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MBM.getBrandId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete brand.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_BRAND = new JSONObject();

        DATA_BRAND.put("RESULT", new Boolean(result));
        DATA_BRAND.put("MESSAGE", new String(messageResult));
        DATA_MASTER_BRAND.put(DATA_BRAND);

        JSONObjectRoot.put("DATA_MASTER_BRAND", DATA_MASTER_BRAND);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }*/
}
