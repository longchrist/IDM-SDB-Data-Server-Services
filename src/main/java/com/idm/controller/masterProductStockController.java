package com.idm.controller;

import com.idm.dao.masterCategoryAO;
import com.idm.dao.masterProductStockAO;
import com.idm.dao.masterShippingAO;
import com.idm.model.masterCategoryMod;
import com.idm.model.masterProductStockMod;
import com.idm.model.responseInfoServices;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.sql.Connection;

@RestController
@RequestMapping(value="/productstock")
public class masterProductStockController {
    @Autowired
    ServletContext context;
    private Connection conn = null;

    /*@CrossOrigin
    @RequestMapping(value="/getallshipping", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getAllShipping() {
        String All = "";
        masterShippingAO MSAO = new masterShippingAO();
        All = MSAO.getAllMasterShipping();
        return All;
    }*/

    @CrossOrigin
    @RequestMapping(value="/loadproductstocktable", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> getProductStockData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                JSONObject dataObject = new JSONObject(data);

                masterProductStockMod MPSM = new masterProductStockMod();
                MPSM.setProductId(dataObject.getInt("PRODUCT_ID"));

                masterProductStockAO MPSAO = new masterProductStockAO();
                String jsonResponse = MPSAO.loadProductStockTable(MPSM);

                RIS.setJsonResponse(jsonResponse);

                headers.add("Response", jsonResponse);
                return new ResponseEntity<responseInfoServices>(RIS, headers, HttpStatus.OK);
            } catch(Exception ex){
                ex.printStackTrace();
                return new ResponseEntity<responseInfoServices>(HttpStatus.BAD_REQUEST);
            }
        } else {
            System.out.println("parameters is null");
            return new ResponseEntity<responseInfoServices>(HttpStatus.BAD_REQUEST);
        }
    }

    /*@CrossOrigin
    @RequestMapping(value="/addcategory", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> addMasterCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                Encryptor enc = new Encryptor();
                String dataDecrypt = enc.decrypt(data);
                JSONObject dataObject = new JSONObject(dataDecrypt);

                masterCategoryMod MWAM = new masterCategoryMod();
                MWAM.setCategoryName(dataObject.getString("CATEGORY_NAME"));
                MWAM.setCategoryDescription(dataObject.getString("CATEGORY_DESCRIPTIONS"));
                MWAM.setAddDate(dataObject.getString("ADD_DATE"));
                MWAM.setAddBy(dataObject.getString("ADD_BY"));
                MWAM.setEditedDate(dataObject.getString("EDITED_DATE"));
                MWAM.setEditedBy(dataObject.getString("EDITED_BY"));
                MWAM.setIsActive(dataObject.getString("IS_ACTIVE"));

                masterCategoryAO MCAO = new masterCategoryAO();
                String jsonResponse = MCAO.saveMasterCategory(MWAM);

                RIS.setJsonResponse(jsonResponse);

                headers.add("Response", jsonResponse);
                return new ResponseEntity<responseInfoServices>(RIS, headers, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<responseInfoServices>(HttpStatus.BAD_REQUEST);
            }
        } else {
            System.out.println("parameters is null");
            return new ResponseEntity<responseInfoServices>(HttpStatus.BAD_REQUEST);
        }
    }*/

    @CrossOrigin
    @RequestMapping(value="/editproductstocktable", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> editMasterCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                JSONObject dataObject = new JSONObject(data);
                JSONObject platformStockData = dataObject.getJSONObject("PRODUCT_STOCK");

                JSONObject productStockId = platformStockData.getJSONObject("productStockId");
                JSONObject idPlatform = platformStockData.getJSONObject("idPlatform");
                JSONObject stockPerPlatform = platformStockData.getJSONObject("stockPerPlatform");
                int ix = platformStockData.getInt("platformLoops");
                for(int i = 0; i < ix; i++){
                    int product_stock_id = productStockId.getInt(String.valueOf(i));
                    int id_platform = idPlatform.getInt(String.valueOf(i));
                    int stock_per_platform = stockPerPlatform.getInt(String.valueOf(i));

                    System.out.println("productstockid : "+product_stock_id+"stock : "+stock_per_platform);
                    masterProductStockMod MPSM = new masterProductStockMod();
                    MPSM.setProductStockId(product_stock_id);
                    MPSM.setPlatformId(id_platform);
                    MPSM.setStockQty(stock_per_platform);

                    masterProductStockAO MPSAO = new masterProductStockAO();
                    MPSAO.updateProductStock(MPSM);
                }

                JSONObject JSONObjectRoot = new JSONObject();
                JSONArray HEAD_DATA = new JSONArray();
                JSONObject DATA = new JSONObject();

                DATA.put("RESULT", "success");
                DATA.put("MESSAGE", "SUCCESS. Updated Stock Data.");
                HEAD_DATA.put(DATA);

                JSONObjectRoot.put("responseMessage", DATA);
                String jsonResponse = JSONObjectRoot.toString();
                RIS.setJsonResponse(jsonResponse);

                headers.add("responseMessage", "Success. Process result with no-errors.");

                return new ResponseEntity<responseInfoServices>(RIS, headers, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<responseInfoServices>(HttpStatus.BAD_REQUEST);
            }
        } else {
            System.out.println("parameters is null");
            return new ResponseEntity<responseInfoServices>(HttpStatus.BAD_REQUEST);
        }
    }

    /*@CrossOrigin
    @RequestMapping(value="/deletecategory", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> deleteMasterCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                Encryptor enc = new Encryptor();
                String dataDecrypt = enc.decrypt(data);
                JSONObject dataObject = new JSONObject(dataDecrypt);

                masterCategoryMod MWAM = new masterCategoryMod();
                MWAM.setCategoryId(dataObject.getInt("CATEGORY_ID"));

                masterCategoryAO MCAO = new masterCategoryAO();
                String jsonResponse = MCAO.deleteMasterCategory(MWAM);

                RIS.setJsonResponse(jsonResponse);

                headers.add("Response", jsonResponse);
                return new ResponseEntity<responseInfoServices>(RIS, headers, HttpStatus.OK);
            } catch(Exception ex){
                ex.printStackTrace();
                return new ResponseEntity<responseInfoServices>(HttpStatus.BAD_REQUEST);
            }
        } else {
            System.out.println("parameters is null");
            return new ResponseEntity<responseInfoServices>(HttpStatus.BAD_REQUEST);
        }
    }*/
}
