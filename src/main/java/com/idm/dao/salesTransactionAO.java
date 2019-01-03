package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.transactionDateRange;
import com.idm.model.transactionMod;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class salesTransactionAO {
    private Connection conn = null;

    HikariConfig hikariConfig = new HikariConfig("/hikari.properties");
    HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

    public String getAllTransaction(){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int transactionId = 0;
        int customerId = 0;
        int platformId = 0;
        String invoice = "";
        int totalTransaction = 0;
        int shippingPrice = 0;
        String packagingId = "";
        String transactionDate = "";
        String isPreorder = "";
        int shippingId = 0;
        int packagingPrice = 0;
        String addDate = "";
        String addBy = "";
        String editedDate = "";
        String editedBy = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_TRANSACTION = new JSONArray();

        try {
            conn = hikariDataSource.getConnection();

            stmt = conn.createStatement();
            String query = "SELECT transaction_id, customer_id, platform_id, invoice, total_transaction, shipping_price, packaging_id, transaction_date, is_preorder, shipping_id, packaging_price, add_date, add_by, edited_date, edited_by FROM tb_transaction";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                transactionId = rs.getInt("TRANSACTION_ID");
                customerId = rs.getInt("CUSTOMER_ID");
                platformId = rs.getInt("PLATFORM_ID");
                invoice = rs.getString("INVOICE");
                totalTransaction = rs.getInt("TOTAL_TRANSACTION");
                shippingPrice = rs.getInt("SHIPPING_PRICE");
                packagingId = rs.getString("PACKAGING_ID");
                transactionDate = rs.getString("TRANSACTION_DATE");
                isPreorder = rs.getString("IS_PREORDER");
                shippingId = rs.getInt("SHIPPING_ID");
                packagingPrice = rs.getInt("PACKAGING_PRICE");
                addDate = rs.getString("ADD_DATE");
                addBy = rs.getString("ADD_BY");
                editedDate = rs.getString("EDITED_DATE");
                editedBy = rs.getString("EDITED_BY");

                JSONObject DATA_T = new JSONObject();

                DATA_T.put("TRANSACTION_ID", new Integer(transactionId));
                DATA_T.put("CUSTOMER_ID", new Integer(customerId));
                DATA_T.put("PLATFORM_ID", new Integer(platformId));
                DATA_T.put("INVOICE", new String(invoice));
                DATA_T.put("TOTAL_TRANSACTION", new Integer(totalTransaction));
                DATA_T.put("SHIPPING_PRICE", new Integer(shippingPrice));
                DATA_T.put("PACKAGING_ID", new Integer(packagingId));
                DATA_T.put("TRANSACTION_DATE", new String(transactionDate));
                DATA_T.put("IS_PREORDER", new String(isPreorder));
                DATA_T.put("SHIPPING_ID", new Integer(shippingId));
                DATA_T.put("PACKAGING_PRICE", new Integer(packagingPrice));
                DATA_T.put("ADD_DATE", new String(addDate));
                DATA_T.put("ADD_BY", new String(addBy));
                DATA_T.put("EDITED_DATE", new String(editedDate));
                DATA_T.put("EDITED_BY", new String(editedBy));

                DATA_TRANSACTION.put(DATA_T);
            }

            JSONObjectRoot.put("DATA_TRANSACTION", DATA_TRANSACTION);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String getTransaction(transactionDateRange TDR){
        String jsonResponse = "";

        ResultSet rs;

        int transactionId = 0;
        int customerId = 0;
            String customerName = "";
            String customerProvince = "";
            String customerPhone = "";
        int platformId = 0;
            String platformType = "";
            String platformName = "";
        String invoice = "";
        int totalTransaction = 0;
        int shippingPrice = 0;
        int packagingId = 0;
        String transactionDate = "";
        String isPreorder = "";
        int shippingId = 0;
            String shippingCourier = "";
            String shippingService = "";
        int packagingPrice = 0;
        String addDate = "";
        String addBy = "";
        String editedDate = "";
        String editedBy = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_TRANSACTION = new JSONArray();

        try {
            conn = hikariDataSource.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("SELECT a.transaction_id, a.customer_id, b.customer_name, b.customer_address, b.customer_province, b.customer_city, b.customer_zip, b.customer_phone, a.platform_id, c.platform_type, c.platform_name, a.invoice, a.total_transaction, a.shipping_price, a.packaging_id, a.transaction_date, a.is_preorder, a.shipping_id, d.shipping_courier, d.shipping_service, a.packaging_price, a.add_date, a.add_by, a.edited_date, a.edited_by, SUM(e.product_price_per_unit * e.product_qty) as fund_price, SUM(e.product_sales_per_unit * e.product_qty) as sales_price FROM tb_transaction a LEFT JOIN tb_master_customer b ON a.customer_id = b.customer_id LEFT JOIN tb_master_platform c ON a.platform_id = c.platform_id LEFT JOIN tb_master_shipping d ON a.shipping_id = d.shipping_id LEFT JOIN tb_transaction_detail e ON a.transaction_id = e.transaction_id WHERE a.transaction_date BETWEEN ? AND ? GROUP BY a.transaction_id");
            ps.setString(1, TDR.getTransactionDateFrom());
            ps.setString(2, TDR.getTransactionDateTo());
            rs = ps.executeQuery();

            while (rs.next()) {
                int fundPricing = 0;
                int salesPricing = 0;
                int fundAndSalesDiffer = 0;
                int totalProfit = 0; // harga jual - modal - shipping - packaging

                transactionId = rs.getInt("TRANSACTION_ID");
                customerId = rs.getInt("CUSTOMER_ID");
                    customerName = rs.getString("CUSTOMER_NAME");
                    customerProvince = rs.getString("CUSTOMER_PROVINCE");
                    customerPhone = rs.getString("CUSTOMER_PHONE");
                platformId = rs.getInt("PLATFORM_ID");
                    platformType = rs.getString("PLATFORM_TYPE");
                    platformName = rs.getString("PLATFORM_NAME");
                invoice = rs.getString("INVOICE");
                totalTransaction = rs.getInt("TOTAL_TRANSACTION");
                shippingPrice = rs.getInt("SHIPPING_PRICE");
                packagingId = rs.getInt("PACKAGING_ID");
                transactionDate = rs.getString("TRANSACTION_DATE");
                isPreorder = rs.getString("IS_PREORDER");
                shippingId = rs.getInt("SHIPPING_ID");
                    shippingCourier = rs.getString("SHIPPING_COURIER");
                    shippingService = rs.getString("SHIPPING_SERVICE");
                packagingPrice = rs.getInt("PACKAGING_PRICE");
                addDate = rs.getString("ADD_DATE");
                addBy = rs.getString("ADD_BY");
                editedDate = rs.getString("EDITED_DATE");
                editedBy = rs.getString("EDITED_BY");

                fundPricing = rs.getInt("FUND_PRICE");
                salesPricing = rs.getInt("SALES_PRICE");
                fundAndSalesDiffer = salesPricing - fundPricing;
                totalProfit = fundAndSalesDiffer - packagingPrice;

                JSONObject DATA_T = new JSONObject();

                DATA_T.put("TRANSACTION_ID", new Integer(transactionId));
                DATA_T.put("CUSTOMER_ID", new Integer(customerId));
                DATA_T.put("CUSTOMER_NAME", new String(customerName));
                DATA_T.put("CUSTOMER_PROVINCE", new String(customerProvince));
                DATA_T.put("CUSTOMER_PHONE", new String(customerPhone));
                DATA_T.put("PLATFORM_ID", new Integer(platformId));
                DATA_T.put("PLATFORM_TYPE", new String(platformType));
                DATA_T.put("PLATFORM_NAME", new String(platformName));
                DATA_T.put("INVOICE", new String(invoice));
                DATA_T.put("TOTAL_TRANSACTION", new Integer(totalTransaction));
                DATA_T.put("SHIPPING_PRICE", new Integer(shippingPrice));
                DATA_T.put("PACKAGING_ID", new Integer(packagingId));
                DATA_T.put("TRANSACTION_DATE", new String(transactionDate));
                DATA_T.put("IS_PREORDER", new String(isPreorder));
                DATA_T.put("SHIPPING_ID", new Integer(shippingId));
                DATA_T.put("SHIPPING_COURIER", new String(shippingCourier));
                DATA_T.put("SHIPPING_SERVICE", new String(shippingService));
                DATA_T.put("PACKAGING_PRICE", new Integer(packagingPrice));
                DATA_T.put("FUND_PRICE", new Integer(fundPricing));
                DATA_T.put("SALES_PRICE", new Integer(salesPricing));
                DATA_T.put("FUND_AND_SALES_DIFFER", new Integer(fundAndSalesDiffer));
                DATA_T.put("TOTAL_PROFIT", new Integer(totalProfit));
                DATA_T.put("ADD_DATE", new String(addDate));
                DATA_T.put("ADD_BY", new String(addBy));
                DATA_T.put("EDITED_DATE", new String(editedDate));
                DATA_T.put("EDITED_BY", new String(editedBy));

                DATA_TRANSACTION.put(DATA_T);
            }

            JSONObjectRoot.put("DATA_TRANSACTION", DATA_TRANSACTION);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveTransaction(transactionMod TM) throws JSONException {
        String jsonResponse = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_TRANSACTION = new JSONArray();

        String responseGeneratedKeys = "";

        boolean result = false;
        String messageResult = "";

        try {
            conn = hikariDataSource.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_transaction (customer_id, platform_id, invoice, total_transaction, shipping_price, packaging_id, transaction_date, is_preorder, shipping_id, packaging_price, add_date, add_by, edited_date, edited_by)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, TM.getCustomerId());
            ps.setInt(2, TM.getPlatformId());
            ps.setString(3, TM.getInvoice());
            ps.setInt(4, TM.getTotalTransaction());
            ps.setInt(5, TM.getShippingPrice());
            ps.setString(6, TM.getPackagingId());
            ps.setString(7, TM.getTransactionDate());
            ps.setString(8, TM.getIsPreorder());
            ps.setInt(9, TM.getShippingId());
            ps.setInt(10, TM.getPackagingPrice());
            ps.setString(11, TM.getAddDate());
            ps.setString(12, TM.getAddBy());
            ps.setString(13, TM.getEditedDate());
            ps.setString(14, TM.getEditedBy());

            if(ps.executeUpdate() > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()){
                    responseGeneratedKeys = rs.getString(1);
                }
                result = true;
                messageResult = "Success add transaction data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_T = new JSONObject();

        DATA_T.put("RESULT", new Boolean(result));
        DATA_T.put("MESSAGE", new String(messageResult));
        DATA_TRANSACTION.put(DATA_T);

        JSONObjectRoot.put("DATA_TRANSACTION", DATA_TRANSACTION);
        jsonResponse += JSONObjectRoot.toString();

        return responseGeneratedKeys;
    }

    public String updateTransaction(transactionMod TM) throws JSONException{
        String jsonResponse = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_TRANSACTION = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = hikariDataSource.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_transaction SET\n" +
                    "customer_id = ?,\n" +
                    "platform_id = ?,\n" +
                    "invoice = ?,\n" +
                    "total_transaction = ?,\n" +
                    "shipping_price = ?,\n" +
                    "packaging_id = ?,\n" +
                    "transaction_date = ?,\n" +
                    "is_preorder = ?,\n" +
                    "shipping_id = ?,\n" +
                    "packaging_price = ?,\n" +
                    "add_date = ?,\n" +
                    "add_by = ?,\n" +
                    "edited_date = ?,\n" +
                    "edited_by = ?\n" +
                    "WHERE transaction_id = ?", Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, TM.getCustomerId());
            ps.setInt(2, TM.getPlatformId());
            ps.setString(3, TM.getInvoice());
            ps.setInt(4, TM.getTotalTransaction());
            ps.setInt(5, TM.getShippingPrice());
            ps.setString(6, TM.getPackagingId());
            ps.setString(7, TM.getTransactionDate());
            ps.setString(8, TM.getIsPreorder());
            ps.setInt(9, TM.getShippingId());
            ps.setInt(10, TM.getPackagingPrice());
            ps.setString(11, TM.getAddDate());
            ps.setString(12, TM.getAddBy());
            ps.setString(13, TM.getEditedDate());
            ps.setString(14, TM.getEditedBy());
            ps.setInt(15, TM.getTransactionId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update transaction data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_T = new JSONObject();

        DATA_T.put("RESULT", new Boolean(result));
        DATA_T.put("MESSAGE", new String(messageResult));
        DATA_TRANSACTION.put(DATA_T);

        JSONObjectRoot.put("DATA_TRANSACTION", DATA_TRANSACTION);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteTransaction(transactionMod TM) throws JSONException{
        String jsonResponse = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_TRANSACTION = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = hikariDataSource.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_transaction WHERE transaction_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, TM.getTransactionId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete transaction data.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_T = new JSONObject();

        DATA_T.put("RESULT", new Boolean(result));
        DATA_T.put("MESSAGE", new String(messageResult));
        DATA_TRANSACTION.put(DATA_T);

        JSONObjectRoot.put("DATA_TRANSACTION", DATA_TRANSACTION);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}
