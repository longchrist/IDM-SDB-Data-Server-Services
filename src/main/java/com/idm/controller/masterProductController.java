package com.idm.controller;

import com.idm.dao.masterPriceAO;
import com.idm.dao.masterProductAO;
import com.idm.model.masterPriceMod;
import com.idm.model.masterProductMod;
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
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping(value="/product")
public class masterProductController {
    @Autowired
    ServletContext context;
    private Connection conn = null;

    @CrossOrigin
    @RequestMapping(value="/getallproduct", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getAllProduct() {
        String All = "";
        masterProductAO MPAO = new masterProductAO();
        All = MPAO.getAllMasterProduct();
        return All;
    }

    @CrossOrigin
    @RequestMapping(value="/addproduct", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> addMasterProductController(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                JSONObject dataObject = new JSONObject(data);
                String PRODUCT_GENERAL_DATA = dataObject.toString();
                JSONObject productGeneralData = dataObject.getJSONObject("PRODUCT_GENERAL_DATA");
                JSONObject stockProductData = dataObject.getJSONObject("STOCK_PRODUCT_DATA");
                JSONObject pricingData = dataObject.getJSONObject("PRICING_DATA");
                JSONArray productPhotoData = dataObject.getJSONArray("PRODUCT_PHOTO_DATA");

                System.out.println(productGeneralData);
                System.out.println(stockProductData);
                System.out.println(pricingData);

                for(int ix = 0; ix < productPhotoData.length(); ix++){
                    JSONObject photoData = productPhotoData.getJSONObject(ix);
                    int temporaryId = photoData.getInt("temporaryId");
                    String temporaryPath = photoData.getString("temporaryPath");

                    System.out.println("temporary id = "+temporaryId+" - temporary path : "+temporaryPath);
                }

                masterPriceMod masterPriceMod = new masterPriceMod();
                    masterPriceMod.setPricePerUnit(pricingData.getInt("PRICE_PER_UNIT"));
                    masterPriceMod.setSalesPrice(pricingData.getInt("SALES_PRICE"));
                    masterPriceMod.setStartDate(pricingData.getString("SALES_START_DATE")+" 00:00:00");
                    masterPriceMod.setEndDate(pricingData.getString("SALES_END_DATE")+" 00:00:00");
                    masterPriceMod.setIsActive("Y"); // default auto Y

                masterPriceAO MPAO = new masterPriceAO();
                String responseAddPricingData = MPAO.saveMasterPrice(masterPriceMod);

                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                String stringDate = sdfDate.format(now);

                System.out.println(stringDate);

                masterProductMod MPM = new masterProductMod();
                    MPM.setCategoryId(productGeneralData.getInt("CATEGORY_ID"));
                    MPM.setSubCategoryId(productGeneralData.getInt("SUB_CATEGORY_ID"));
                    MPM.setPriceId(Integer.parseInt(responseAddPricingData));
                    MPM.setUnitId(productGeneralData.getInt("UNIT_ID"));
                    MPM.setProductName(productGeneralData.getString("PRODUCT_NAME"));
                    MPM.setProductUnit(productGeneralData.getInt("PRODUCT_UNIT"));
                    MPM.setProductQuantity(productGeneralData.getInt("PRODUCT_QUANTITY"));
                    MPM.setProductDescriptions(productGeneralData.getString("PRODUCT_DESCRIPTIONS"));
                    MPM.setProductCondition(productGeneralData.getString("PRODUCT_CONDITION"));
                    MPM.setProductNotes(productGeneralData.getString("PRODUCT_NOTES"));
                    MPM.setAddDate(stringDate);
                    MPM.setAddBy("INITIAL UPLOAD");
                    MPM.setEditedDate("2018-01-01 00:00:00");
                    MPM.setEditedBy("");
                    MPM.setIsActive("Y");

               /* masterProductAO MPAO = new masterProductAO();
                String responseAddProduct = MPAO.saveMasterProduct(MPM);

                System.out.println(responseAddProduct);
                JSONObject objectResponseAddProduct = new JSONObject(responseAddProduct);

                masterProductStockMod MPSM = new masterProductStockMod();
                MPSM.setProductId();
                MPSM.setPlatformId(dataObject.getInt("PLATFORM_ID"));
                MPSM.setStockQty(dataObject.getInt("STOCK_QTY"));
                MPSM.setWasteQty(dataObject.getInt("WASTE_QTY"));
                MPSM.setStockDescriptions(dataObject.getString("STOCK_DESCRIPTIONS"));*/

                //RIS.setJsonResponse(jsonResponse);

                headers.add("Response", PRODUCT_GENERAL_DATA.toString());
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
    @RequestMapping(value="/editcategory", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> editMasterCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

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
                MWAM.setCategoryId(dataObject.getInt("CATEGORY_ID"));

                masterCategoryAO MCAO = new masterCategoryAO();
                String jsonResponse = MCAO.updateMasterCategory(MWAM);

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
    }

    @CrossOrigin
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
