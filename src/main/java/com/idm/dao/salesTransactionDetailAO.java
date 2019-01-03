package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.transactionDetailMod;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class salesTransactionDetailAO {
    private Connection conn = null;

    HikariConfig hikariConfig = new HikariConfig("/hikari.properties");
    HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

    /*public String getTransactionDetail(transactionDetailMod TDM){
        String jsonResponse = "";

        ResultSet rs;

        int transactionDetailId = 0;
        int transactionId = 0;
        int productId = 0;
        String productName = "";
        int productQty = 0;
        int productPricePerUnit = 0;
        int productSalesPerUnit = 0;
        String isPreorder = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_TRANSACTION_DETAIL = new JSONArray();

        try {
            conn = hikariDataSource.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("SELECT transaction_detail_id, transaction_id, product_id, product_name, product_qty, product_price_per_unit, product_sales_per_unit, is_preorder FROM tb_transaction_detail WHERE transaction_id = ?");
            ps.setInt(1, TDM.getTransactionId());
            rs = ps.executeQuery();

            while (rs.next()) {
                transactionDetailId = rs.getInt("PRICE_ID");
                transactionId = rs.getInt("PRICE_PER_UNIT");
                productId = rs.getInt("SALES_PRICE");
                productName = rs.getString("START_DATE");
                productQty = rs.getInt("PRICE_ID");
                productPricePerUnit = rs.getInt("PRICE_PER_UNIT");
                productSalesPerUnit = rs.getInt("SALES_PRICE");
                isPreorder = rs.getString("END_DATE");

                JSONObject DATA_TD = new JSONObject();

                DATA_TD.put("TRANSACTION_DETAIL_ID", new Integer(transactionDetailId));
                DATA_TD.put("TRANSACTION_ID", new Integer(transactionId));
                DATA_TD.put("PRODUCT_ID", new Integer(productId));
                DATA_TD.put("PRODUCT_NAME", new String(productName));
                DATA_TD.put("PRODUCT_QTY", new Integer(productQty));
                DATA_TD.put("PRODUCT_PRICE_PER_UNIT", new Integer(productPricePerUnit));
                DATA_TD.put("PRODUCT_SALES_PER_UNIT", new Integer(productSalesPerUnit));
                DATA_TD.put("IS_PREORDER", new String(isPreorder));

                DATA_TRANSACTION_DETAIL.put(DATA_TD);
            }

            JSONObjectRoot.put("DATA_TRANSACTION_DETAIL", DATA_TRANSACTION_DETAIL);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }*/

    public String saveTransactionDetail(transactionDetailMod TDM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_TRANSACTION_DETAIL = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = hikariDataSource.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_transaction_detail (transaction_id, product_id, product_name, product_qty, product_price_per_unit, product_sales_per_unit, is_preorder, descriptions) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, TDM.getTransactionId());
            ps.setInt(2, TDM.getProductId());
            ps.setString(3, TDM.getProductName());
            ps.setInt(4, TDM.getProductQty());
            ps.setInt(5, TDM.getProductPricePerUnit());
            ps.setInt(6, TDM.getProductSalesPerUnit());
            ps.setString(7, TDM.getIsPreorder());
            ps.setString(8, TDM.getDescriptions());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add transaction detail data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject TRANSACTION_DETAIL = new JSONObject();

        TRANSACTION_DETAIL.put("RESULT", new Boolean(result));
        TRANSACTION_DETAIL.put("MESSAGE", new String(messageResult));
        DATA_TRANSACTION_DETAIL.put(TRANSACTION_DETAIL);

        JSONObjectRoot.put("DATA_TRANSACTION_DETAIL", DATA_TRANSACTION_DETAIL);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    /*public String updateMasterPrice(masterPriceMod MPM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRICE = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_price SET price_per_unit = ?, sales_price = ?, start_date = ?, end_date = ?, is_active = ? WHERE price_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPM.getPricePerUnit());
            ps.setInt(2, MPM.getSalesPrice());
            ps.setString(3, MPM.getStartDate());
            ps.setString(4, MPM.getEndDate());
            ps.setString(5, MPM.getIsActive());
            ps.setInt(6, MPM.getPriceId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update price data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PRICE = new JSONObject();

        DATA_PRICE.put("RESULT", new Boolean(result));
        DATA_PRICE.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PRICE.put(DATA_PRICE);

        JSONObjectRoot.put("DATA_MASTER_PRICE", DATA_MASTER_PRICE);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteMasterPrice(masterPriceMod MPM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRICE = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_price WHERE price_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPM.getPriceId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete price data.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PRICE = new JSONObject();

        DATA_PRICE.put("RESULT", new Boolean(result));
        DATA_PRICE.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PRICE.put(DATA_PRICE);

        JSONObjectRoot.put("DATA_MASTER_PRICE", DATA_MASTER_PRICE);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }*/
}
