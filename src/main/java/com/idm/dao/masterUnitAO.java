package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterCategoryMod;
import com.idm.model.masterUnitMod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class masterUnitAO {
    private Connection conn = null;

//    HikariDataSource hikariDataSource;
//    HikariConfig hikariConfig;
//
//    HikariConfig config = new HikariConfig("hikari.properties");
//    HikariDataSource ds = new HikariDataSource(config);

    public String getAllMasterUnit(){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int unitId = 0;
        String unit = "";
        String addDate = "";
        String addBy = "";
        String editedDate = "";
        String editedBy = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_UNIT = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            String query = "SELECT unit_id, unit, add_date, add_by, edited_date, edited_by, is_active FROM tb_master_unit";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                unitId = rs.getInt("UNIT_ID");
                unit = rs.getString("UNIT");
                addDate = rs.getString("ADD_DATE");
                addBy = rs.getString("ADD_BY");
                editedDate = rs.getString("EDITED_DATE");
                editedBy = rs.getString("EDITED_BY");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_UNIT = new JSONObject();

                DATA_UNIT.put("UNIT_ID", new Integer(unitId));
                DATA_UNIT.put("UNIT", new String(unit));
                DATA_UNIT.put("ADD_DATE", new String(addDate));
                DATA_UNIT.put("ADD_BY", new String(addBy));
                DATA_UNIT.put("EDITED_DATE", new String(editedDate));
                DATA_UNIT.put("EDITED_BY", new String(editedBy));
                DATA_UNIT.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_UNIT.put(DATA_UNIT);
            }

            JSONObjectRoot.put("DATA_MASTER_UNIT", DATA_MASTER_UNIT);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String getMasterUnit(masterUnitMod MUM){
        String jsonResponse = "";

        int unitId = 0;
        String unit = "";
        String addDate = "";
        String addBy = "";
        String editedDate = "";
        String editedBy = "";
        String isActive = "";

        Statement stmt = null;
        ResultSet rs;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_UNIT = new JSONArray();

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("SELECT unit_id, unit, add_date, add_by, edited_date, edited_by, is_active FROM tb_master_unit WHERE unit_id = ?");
            ps.setInt(1, MUM.getUnitId());
            rs = ps.executeQuery();

            while (rs.next()) {
                unitId = rs.getInt("UNIT_ID");
                unit = rs.getString("UNIT");
                addDate = rs.getString("ADD_DATE");
                addBy = rs.getString("ADD_BY");
                editedDate = rs.getString("EDITED_DATE");
                editedBy = rs.getString("EDITED_BY");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_UNIT = new JSONObject();

                DATA_UNIT.put("UNIT_ID", new Integer(unitId));
                DATA_UNIT.put("UNIT", new String(unit));
                DATA_UNIT.put("ADD_DATE", new String(addDate));
                DATA_UNIT.put("ADD_BY", new String(addBy));
                DATA_UNIT.put("EDITED_DATE", new String(editedDate));
                DATA_UNIT.put("EDITED_BY", new String(editedBy));
                DATA_UNIT.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_UNIT.put(DATA_UNIT);
            }

            JSONObjectRoot.put("DATA_MASTER_UNIT", DATA_MASTER_UNIT);
            jsonResponse += JSONObjectRoot.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveMasterUnit(masterUnitMod MUM) throws JSONException {
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_UNIT = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_master_unit VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MUM.getUnit());
            ps.setString(2, MUM.getAddDate());
            ps.setString(3, MUM.getAddBy());
            ps.setString(4, MUM.getEditedDate());
            ps.setString(5, MUM.getEditedBy());
            ps.setString(6, MUM.getIsActive());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success add master unit data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_UNIT = new JSONObject();

        DATA_UNIT.put("RESULT", new Boolean(result));
        DATA_UNIT.put("MESSAGE", new String(messageResult));
        DATA_MASTER_UNIT.put(DATA_UNIT);

        JSONObjectRoot.put("DATA_MASTER_UNIT", DATA_MASTER_UNIT);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String updateMasterUnit(masterUnitMod MUM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_UNIT = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_unit SET unit = ?, edited_date = ?, edited_by = ?, is_active = ? WHERE unit_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, MUM.getUnit());
            ps.setString(2, MUM.getEditedDate());
            ps.setString(3, MUM.getEditedBy());
            ps.setString(4, MUM.getIsActive());
            ps.setInt(5, MUM.getUnitId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update unit data.";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_UNIT = new JSONObject();

        DATA_UNIT.put("RESULT", new Boolean(result));
        DATA_UNIT.put("MESSAGE", new String(messageResult));
        DATA_MASTER_UNIT.put(DATA_UNIT);

        JSONObjectRoot.put("DATA_MASTER_UNIT", DATA_MASTER_UNIT);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteMasterUnit(masterUnitMod MUM) throws JSONException{
        String jsonResponse = "";

        Statement stmt = null;

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_UNIT = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            dbConnection DC = new dbConnection();
            conn = DC.getConnection();

            stmt = conn.createStatement();
            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_unit WHERE unit_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MUM.getUnitId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete unit data.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_UNIT = new JSONObject();

        DATA_UNIT.put("RESULT", new Boolean(result));
        DATA_UNIT.put("MESSAGE", new String(messageResult));
        DATA_MASTER_UNIT.put(DATA_UNIT);

        JSONObjectRoot.put("DATA_MASTER_UNIT", DATA_MASTER_UNIT);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}
