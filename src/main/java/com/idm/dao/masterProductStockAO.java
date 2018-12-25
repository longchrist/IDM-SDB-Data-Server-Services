package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterCategoryMod;
import com.idm.model.masterProductStockMod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class masterProductStockAO {
    private Connection conn = null;

    public String loadProductStockTable(masterProductStockMod MPSM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int productStockId = 0;
        int productId = 0;
        int platformId = 0;
        String platformType = "";
        String platformName = "";
        int stockQty = 0;
        int wasteQty = 0;
        String stockDescriptions = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRODUCT_STOCK = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("SELECT a.product_stock_id, a.product_id, a.platform_id, b.platform_type, b.platform_name, a.stock_qty, a.waste_qty, a.stock_descriptions, a.is_active FROM tb_product_stock a LEFT JOIN tb_master_platform b ON a.platform_id = b.platform_id WHERE a.product_id = ?");
            ps.setInt(1, MPSM.getProductId());
            rs = ps.executeQuery();

            while (rs.next()) {
                productStockId = rs.getInt("PRODUCT_STOCK_ID");
                productId = rs.getInt("PRODUCT_ID");
                platformId = rs.getInt("PLATFORM_ID");
                platformType = rs.getString("PLATFORM_TYPE");
                platformName = rs.getString("PLATFORM_NAME");
                stockQty = rs.getInt("STOCK_QTY");
                wasteQty = rs.getInt("WASTE_QTY");
                stockDescriptions = rs.getString("STOCK_DESCRIPTIONS");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_PRODUCT_STOCK = new JSONObject();

                DATA_PRODUCT_STOCK.put("PRODUCT_STOCK_ID", new Integer(productStockId));
                DATA_PRODUCT_STOCK.put("PRODUCT_ID", new Integer(productId));
                DATA_PRODUCT_STOCK.put("PLATFORM_ID", new Integer(platformId));
                DATA_PRODUCT_STOCK.put("PLATFORM_TYPE", new String(platformType));
                DATA_PRODUCT_STOCK.put("PLATFORM_NAME", new String(platformName));
                DATA_PRODUCT_STOCK.put("STOCK_QTY", new Integer(stockQty));
                DATA_PRODUCT_STOCK.put("WASTE_QTY", new Integer(wasteQty));
                DATA_PRODUCT_STOCK.put("STOCK_DESCRIPTIONS", new String(stockDescriptions));
                DATA_PRODUCT_STOCK.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_PRODUCT_STOCK.put(DATA_PRODUCT_STOCK);
            }

            JSONObjectRoot.put("DATA_MASTER_PRODUCT_STOCK", DATA_MASTER_PRODUCT_STOCK);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveProductStock(masterProductStockMod MPSM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRODUCT_STOCK = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_product_stock (product_id, platform_id, stock_qty, waste_qty, stock_descriptions, add_date, add_by, edited_date, edited_by, is_active) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPSM.getProductId());
            ps.setInt(2, MPSM.getPlatformId());
            ps.setInt(3, MPSM.getStockQty());
            ps.setInt(4, MPSM.getWasteQty());
            ps.setString(5, MPSM.getStockDescriptions());
            ps.setString(6, MPSM.getAddDate());
            ps.setString(7, MPSM.getAddBy());
            ps.setString(8, MPSM.getEditedDate());
            ps.setString(9, MPSM.getEditedBy());
            ps.setString(10, MPSM.getIsActive());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add product stock data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PRODUCT_STOCK = new JSONObject();

        DATA_PRODUCT_STOCK.put("RESULT", new Boolean(result));
        DATA_PRODUCT_STOCK.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PRODUCT_STOCK.put(DATA_PRODUCT_STOCK);

        JSONObjectRoot.put("DATA_MASTER_PRODUCT_STOCK", DATA_MASTER_PRODUCT_STOCK);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String updateProductStock(masterProductStockMod MPSM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRODUCT_STOCK = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_product_stock SET stock_qty = ? WHERE product_stock_id = ? AND platform_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPSM.getStockQty());
            ps.setInt(2, MPSM.getProductStockId());
            ps.setInt(3, MPSM.getPlatformId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update product stock data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PRODUCT_STOCK = new JSONObject();

        DATA_PRODUCT_STOCK.put("RESULT", new Boolean(result));
        DATA_PRODUCT_STOCK.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PRODUCT_STOCK.put(DATA_PRODUCT_STOCK);

        JSONObjectRoot.put("DATA_MASTER_PRODUCT_STOCK", DATA_MASTER_PRODUCT_STOCK);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}
