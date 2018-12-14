package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterProductMod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class masterProductAO {
    private Connection conn = null;

//    HikariDataSource hikariDataSource;
//    HikariConfig hikariConfig;
//
//    HikariConfig config = new HikariConfig("hikari.properties");
//    HikariDataSource ds = new HikariDataSource(config);

    public String getAllMasterProduct(){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int productId = 0;
        int categoryId = 0;
        int subCategoryId = 0;
        int priceId = 0; // price relasi 1 : 1
        int pricePerUnit = 0;
        int salesPrice = 0;

        int unitId = 0;
        String unit = "";

        int photoId = 0;
        String photoLink = "";
        String photoDescriptions = "";
        String photoAlt = "";

        String productName = "";
        int productUnit = 0;
        int productQty = 0; // tabel tb_product_stock
        String productDescriptions = "";
        String productCondition = "";
        String productNotes = "";
        String addDate = "";
        String addBy = "";
        String editedDate = "";
        String editedBy = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRODUCT = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            /*String query = "SELECT a.product_id, a.category_id, b.category_name, a.sub_category_id, c.sub_category, a.price_id, d.price_per_unit, d.sales_price, a.stock_id, a.status_id, g.unit_id, g.unit, a.photo_id, a.product_name, a.product_unit, a.product_qty, a.product_descriptions, a.product_condition, a.product_notes, a.add_date, a.add_by, a.edited_date, a.edited_by, a.is_active FROM tb_master_product a" +
                    "LEFT JOIN tb_master_category b ON a.category_id = b.category_id " +
                    "LEFT JOIN tb_master_sub_category c ON a.sub_category_id = c.sub_category_id " +
                    "LEFT JOIN tb_master_price d ON a.price_id = d.price_id";*/

            PreparedStatement ps = this.conn.prepareStatement("SELECT a.product_id, a.category_id, b.category_name, a.sub_category_id, c.sub_category, a.price_id, d.price_per_unit, d.sales_price, SUM(f.stock_qty) as product_qty, g.unit_id, g.unit, e.photo_id, e.photo_link, e.photo_descriptions, e.photo_alt, a.product_name, a.product_unit, a.product_descriptions, a.product_condition, a.product_notes, a.add_date, a.add_by, a.edited_date, a.edited_by, a.is_active FROM tb_master_product a " +
                    "LEFT JOIN tb_master_category b ON a.category_id = b.category_id " +
                    "LEFT JOIN tb_master_sub_category c ON a.sub_category_id = c.sub_category_id " +
                    "LEFT JOIN tb_master_price d ON a.price_id = d.price_id " +
                    "LEFT JOIN tb_photo e ON a.product_id = e.product_id " +
                    "LEFT JOIN tb_product_stock f ON a.product_id = f.product_id " +
                    "LEFT JOIN tb_master_unit g ON a.unit_id = g.unit_id WHERE e.is_primary = 'Y' GROUP BY a.product_id");

            rs = ps.executeQuery();

            while (rs.next()) {
                productId = rs.getInt("PRODUCT_ID");
                categoryId = rs.getInt("CATEGORY_ID");
                subCategoryId = rs.getInt("SUB_CATEGORY_ID");

                priceId = rs.getInt("PRICE_ID");
                pricePerUnit = rs.getInt("PRICE_PER_UNIT");
                salesPrice = rs.getInt("SALES_PRICE");

                unitId = rs.getInt("UNIT_ID");
                unit = rs.getString("UNIT");

                photoId = rs.getInt("PHOTO_ID");
                photoLink = rs.getString("PHOTO_LINK");
                photoDescriptions = rs.getString("PHOTO_DESCRIPTIONS");
                photoAlt = rs.getString("PHOTO_ALT");

                productName = rs.getString("PRODUCT_NAME");
                productUnit = rs.getInt("PRODUCT_UNIT");
                productQty = rs.getInt("PRODUCT_QTY");
                productDescriptions = rs.getString("PRODUCT_DESCRIPTIONS");
                productCondition = rs.getString("PRODUCT_CONDITION");
                productNotes = rs.getString("PRODUCT_NOTES");
                addDate = rs.getString("ADD_DATE");
                addBy = rs.getString("ADD_BY");
                editedDate = rs.getString("EDITED_DATE");
                editedBy = rs.getString("EDITED_BY");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_PRODUCT = new JSONObject();

                    DATA_PRODUCT.put("PRODUCT_ID", new Integer(productId));
                    DATA_PRODUCT.put("CATEGORY_ID", new Integer(categoryId));
                    DATA_PRODUCT.put("SUB_CATEGORY_ID", new Integer(subCategoryId));

                    DATA_PRODUCT.put("PRICE_ID", new Integer(priceId));
                    DATA_PRODUCT.put("PRICE_PER_UNIT", new Integer(pricePerUnit));
                    DATA_PRODUCT.put("SALES_PRICE", new Integer(salesPrice));

                    DATA_PRODUCT.put("UNIT_ID", new Integer(unitId));
                    DATA_PRODUCT.put("UNIT", new String(unit));

                    DATA_PRODUCT.put("PHOTO_ID", new Integer(photoId));
                    DATA_PRODUCT.put("PHOTO_LINK", new String(photoLink));
                    DATA_PRODUCT.put("PHOTO_DESCRIPTIONS", new String(photoDescriptions));
                    DATA_PRODUCT.put("PHOTO_ALT", new String(photoAlt));

                    DATA_PRODUCT.put("PRODUCT_NAME", new String(productName));
                    DATA_PRODUCT.put("PRODUCT_UNIT", new Integer(productUnit));
                    DATA_PRODUCT.put("PRODUCT_QTY", new Integer(productQty));
                    DATA_PRODUCT.put("PRODUCT_DESCRIPTIONS", new String(productDescriptions));
                    DATA_PRODUCT.put("PRODUCT_CONDITION", new String(productCondition));
                    DATA_PRODUCT.put("PRODUCT_NOTES", new String(productNotes));
                    DATA_PRODUCT.put("ADD_DATE", new String(addDate));
                    DATA_PRODUCT.put("ADD_BY", new String(addBy));
                    DATA_PRODUCT.put("EDITED_DATE", new String(editedDate));
                    DATA_PRODUCT.put("EDITED_BY", new String(editedBy));
                    DATA_PRODUCT.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_PRODUCT.put(DATA_PRODUCT);
            }

            JSONObjectRoot.put("DATA_MASTER_PRODUCT", DATA_MASTER_PRODUCT);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String getMasterProduct(masterProductMod MPM){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int productId = 0;
        int categoryId = 0;
        int subCategoryId = 0;
        int priceId = 0;
        int stockId = 0;
        int statusId = 0;
        int unitId = 0;
        int photoId = 0;
        String productName = "";
        int productUnit = 0;
        int productQty = 0;
        String productDescriptions = "";
        String productCondition = "";
        String productNotes = "";
        String addDate = "";
        String addBy = "";
        String editedDate = "";
        String editedBy = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRODUCT = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("SELECT product_id, category_id, sub_category_id, price_id, stock_id, status_id, unit_id, photo_id, product_name, product_unit, product_qty, product_descriptions, product_condition, product_notes, add_date, add_by, edited_date, edited_by, is_active FROM tb_master_product WHERE product_id = ?");
            ps.setInt(1, MPM.getProductId());
            rs = ps.executeQuery();

            while (rs.next()) {
                productId = rs.getInt("PRODUCT_ID");
                categoryId = rs.getInt("CATEGORY_ID");
                subCategoryId = rs.getInt("SUB_CATEGORY_ID");
                priceId = rs.getInt("PRICE_ID");
                stockId = rs.getInt("STOCK_ID");
                statusId = rs.getInt("STATUS_ID");
                unitId = rs.getInt("UNIT_ID");
                photoId = rs.getInt("PHOTO_ID");
                productName = rs.getString("PRODUCT_NAME");
                productUnit = rs.getInt("PRODUCT_UNIT");
                productQty = rs.getInt("PRODUCT_QTY");
                productDescriptions = rs.getString("PRODUCT_DESCRIPTIONS");
                productCondition = rs.getString("PRODUCT_CONDITION");
                productNotes = rs.getString("PRODUCT_NOTES");
                addDate = rs.getString("ADD_DATE");
                addBy = rs.getString("ADD_BY");
                editedDate = rs.getString("EDITED_DATE");
                editedBy = rs.getString("EDITED_BY");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_PRODUCT = new JSONObject();

                DATA_PRODUCT.put("PRODUCT_ID", new Integer(productId));
                DATA_PRODUCT.put("CATEGORY_ID", new Integer(categoryId));
                DATA_PRODUCT.put("SUB_CATEGORY_ID", new Integer(subCategoryId));
                DATA_PRODUCT.put("PRICE_ID", new Integer(priceId));
                DATA_PRODUCT.put("STOCK_ID", new Integer(stockId));
                DATA_PRODUCT.put("STATUS_ID", new Integer(statusId));
                DATA_PRODUCT.put("UNIT_ID", new Integer(unitId));
                DATA_PRODUCT.put("PHOTO_ID", new Integer(photoId));
                DATA_PRODUCT.put("PRODUCT_NAME", new String(productName));
                DATA_PRODUCT.put("PRODUCT_UNIT", new Integer(productUnit));
                DATA_PRODUCT.put("PRODUCT_QTY", new Integer(productQty));
                DATA_PRODUCT.put("PRODUCT_DESCRIPTIONS", new String(productDescriptions));
                DATA_PRODUCT.put("PRODUCT_CONDITION", new String(productCondition));
                DATA_PRODUCT.put("PRODUCT_NOTES", new String(productNotes));
                DATA_PRODUCT.put("ADD_DATE", new String(addDate));
                DATA_PRODUCT.put("ADD_BY", new String(addBy));
                DATA_PRODUCT.put("EDITED_DATE", new String(editedDate));
                DATA_PRODUCT.put("EDITED_BY", new String(editedBy));
                DATA_PRODUCT.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_PRODUCT.put(DATA_PRODUCT);
            }

            JSONObjectRoot.put("DATA_MASTER_PRODUCT", DATA_MASTER_PRODUCT);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveMasterProduct(masterProductMod MPM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRODUCT = new JSONArray();

        int returnedId = 0;

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_master_product VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPM.getCategoryId());
            ps.setInt(2, MPM.getSubCategoryId());
            ps.setInt(3, MPM.getPriceId());
            ps.setInt(6, MPM.getUnitId());
            ps.setString(8, MPM.getProductName());
            ps.setInt(9, MPM.getProductUnit());
            ps.setInt(10, MPM.getProductQuantity());
            ps.setString(11, MPM.getProductDescriptions());
            ps.setString(12, MPM.getProductCondition());
            ps.setString(13, MPM.getProductNotes());
            ps.setString(14, MPM.getAddDate());
            ps.setString(15, MPM.getAddBy());
            ps.setString(16, MPM.getEditedDate());
            ps.setString(17, MPM.getEditedBy());
            ps.setString(18, MPM.getIsActive());

            if(ps.executeUpdate() > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()){
                    returnedId=rs.getInt(1);
                }
                result = true;
                messageResult = "Success add new product data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PRODUCT = new JSONObject();

        DATA_PRODUCT.put("ID", new Integer(returnedId));
        DATA_PRODUCT.put("RESULT", new Boolean(result));
        DATA_PRODUCT.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PRODUCT.put(DATA_PRODUCT);

        JSONObjectRoot.put("DATA_MASTER_PRODUCT", DATA_MASTER_PRODUCT);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String updateMasterProduct(masterProductMod MPM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRODUCT = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_product SET \n" +
                    "category_id = ?,\n" +
                    "sub_category_id = ?,\n" +
                    "price_id = ?,\n" +
                    "stock_id = ?,\n" +
                    "status_id = ?,\n" +
                    "unit_id = ?,\n" +
                    "photo_id = ?,\n" +
                    "product_name = ?,\n" +
                    "product_unit = ?,\n" +
                    "product_qty = ?,\n" +
                    "product_descriptions = ?,\n" +
                    "product_condition = ?,\n" +
                    "product_notes = ?,\n" +
                    "add_date = ?,\n" +
                    "add_by = ?,\n" +
                    "edited_date = ?,\n" +
                    "edited_by = ?,\n" +
                    "is_active = ?\n" +
                    "WHERE product_id  = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPM.getCategoryId());
            ps.setInt(2, MPM.getSubCategoryId());
            ps.setInt(3, MPM.getPriceId());
            ps.setInt(6, MPM.getUnitId());
            ps.setString(8, MPM.getProductName());
            ps.setInt(9, MPM.getProductUnit());
            ps.setInt(10, MPM.getProductQuantity());
            ps.setString(11, MPM.getProductDescriptions());
            ps.setString(12, MPM.getProductCondition());
            ps.setString(13, MPM.getProductNotes());
            ps.setString(14, MPM.getAddDate());
            ps.setString(15, MPM.getAddBy());
            ps.setString(16, MPM.getEditedDate());
            ps.setString(17, MPM.getEditedBy());
            ps.setString(18, MPM.getIsActive());
            ps.setInt(19, MPM.getProductId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update product data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PRODUCT = new JSONObject();

        DATA_PRODUCT.put("RESULT", new Boolean(result));
        DATA_PRODUCT.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PRODUCT.put(DATA_PRODUCT);

        JSONObjectRoot.put("DATA_MASTER_PRODUCT", DATA_MASTER_PRODUCT);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteMasterProduct(masterProductMod MPM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRODUCT = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_product WHERE product_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPM.getProductId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete product data.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PRODUCT = new JSONObject();

        DATA_PRODUCT.put("RESULT", new Boolean(result));
        DATA_PRODUCT.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PRODUCT.put(DATA_PRODUCT);

        JSONObjectRoot.put("DATA_MASTER_PRODUCT", DATA_MASTER_PRODUCT);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}
