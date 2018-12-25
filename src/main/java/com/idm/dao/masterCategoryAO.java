package com.idm.dao;

import com.idm.model.masterCategoryMod;
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

    public String getAllMasterCategory(){
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

    public String getMasterCategory(masterCategoryMod MCM){
        String jsonResponse = "";

        int categoryId = 0;
        String categoryName = "";
        String categoryDescriptions = "";
        String addDate = "";
        String addBy = "";
        String editedDate = "";
        String editedBy = "";
        String isActive = "";

        Statement stmt = null;
        ResultSet rs;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_CATEGORY = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("SELECT category_id, category_name, category_descriptions, add_date, add_by, edited_date, edited_by, is_active FROM tb_master_category WHERE category_id = ?");
            ps.setInt(1, MCM.getCategoryId());
            rs = ps.executeQuery();

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

    public String saveMasterCategory(masterCategoryMod MCM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_CATEGORY = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_master_category (category_name, category_descriptions, add_date, add_by, edited_date, edited_by, is_active) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MCM.getCategoryName());
            ps.setString(2, MCM.getCategoryDescription());
            ps.setString(3, MCM.getAddDate());
            ps.setString(4, MCM.getAddBy());
            ps.setString(5, MCM.getEditedDate());
            ps.setString(6, MCM.getEditedBy());
            ps.setString(7, MCM.getIsActive());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add category.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_CATEGORY = new JSONObject();

        DATA_CATEGORY.put("RESULT", new Boolean(result));
        DATA_CATEGORY.put("MESSAGE", new String(messageResult));
        DATA_MASTER_CATEGORY.put(DATA_CATEGORY);

        JSONObjectRoot.put("DATA_MASTER_CATEGORY", DATA_MASTER_CATEGORY);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String updateMasterCategory(masterCategoryMod MCM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_CATEGORY = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_category SET category_name = ?, category_descriptions = ?, is_active = ? WHERE category_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MCM.getCategoryName());
            ps.setString(2, MCM.getCategoryDescription());
            ps.setString(3, MCM.getIsActive());
            ps.setInt(4, MCM.getCategoryId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update category data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_CATEGORY = new JSONObject();

        DATA_CATEGORY.put("RESULT", new Boolean(result));
        DATA_CATEGORY.put("MESSAGE", new String(messageResult));
        DATA_MASTER_CATEGORY.put(DATA_CATEGORY);

        JSONObjectRoot.put("DATA_MASTER_CATEGORY", DATA_MASTER_CATEGORY);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteMasterCategory(masterCategoryMod MCM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_CATEGORY = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_category WHERE category_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MCM.getCategoryId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete category.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_CATEGORY = new JSONObject();

        DATA_CATEGORY.put("RESULT", new Boolean(result));
        DATA_CATEGORY.put("MESSAGE", new String(messageResult));
        DATA_MASTER_CATEGORY.put(DATA_CATEGORY);

        JSONObjectRoot.put("DATA_MASTER_CATEGORY", DATA_MASTER_CATEGORY);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}
