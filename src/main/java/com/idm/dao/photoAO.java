package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterProductStockMod;
import com.idm.model.photoMod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class photoAO {
    private Connection conn = null;

    public String savePhoto(photoMod PM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_PRODUCT_PHOTO = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_photo (product_id, photo_link, photo_descriptions, photo_alt, add_date, add_by, edited_date, edited_by, is_active, is_primary) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, PM.getProductId());
            ps.setString(2, PM.getPhotoLink());
            ps.setString(3, PM.getPhotoDescriptions());
            ps.setString(4, PM.getPhotoAlt());
            ps.setString(5, PM.getAddDate());
            ps.setString(6, PM.getAddBy());
            ps.setString(7, PM.getEditedDate());
            ps.setString(8, PM.getEditedBy());
            ps.setString(9, PM.getIsActive());
            ps.setString(10, PM.getIsPrimary());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add product photo data.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject PRODUCT_PHOTO = new JSONObject();

        PRODUCT_PHOTO.put("RESULT", new Boolean(result));
        PRODUCT_PHOTO.put("MESSAGE", new String(messageResult));
        DATA_PRODUCT_PHOTO.put(PRODUCT_PHOTO);

        JSONObjectRoot.put("DATA_PRODUCT_PHOTO", DATA_PRODUCT_PHOTO);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

}
