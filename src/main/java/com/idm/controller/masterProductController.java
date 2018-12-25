package com.idm.controller;

import com.idm.dao.masterPriceAO;
import com.idm.dao.masterProductAO;
import com.idm.dao.masterProductStockAO;
import com.idm.dao.photoAO;
import com.idm.model.*;

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
                    JSONObject platformStockData = stockProductData.getJSONObject("PLATFORM_STOCK_DATA");
                JSONObject unitData = dataObject.getJSONObject("UNIT_DATA");
                JSONObject pricingData = dataObject.getJSONObject("PRICING_DATA");
                JSONArray productPhotoData = dataObject.getJSONArray("PRODUCT_PHOTO_DATA");

                System.out.println(productGeneralData);
                System.out.println(stockProductData);
                System.out.println(unitData);
                System.out.println(pricingData);

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

                masterProductMod MPM = new masterProductMod();
                    MPM.setCategoryId(productGeneralData.getInt("CATEGORY_ID"));
                    MPM.setSubCategoryId(productGeneralData.getInt("SUB_CATEGORY_ID"));
                    MPM.setPriceId(Integer.parseInt(responseAddPricingData));
                    MPM.setUnitId(unitData.getInt("UNIT_ID"));
                    MPM.setProductName(productGeneralData.getString("PRODUCT_NAME"));
                    MPM.setProductUnit(unitData.getInt("UNIT_NUMBER"));
                    MPM.setProductQuantity(productGeneralData.getInt("PRODUCT_QUANTITY"));
                    MPM.setProductDescriptions(productGeneralData.getString("PRODUCT_DESCRIPTIONS"));
                    MPM.setProductCondition(productGeneralData.getString("PRODUCT_CONDITION"));
                    MPM.setProductNotes(productGeneralData.getString("PRODUCT_NOTES"));
                    MPM.setAddDate(stringDate);
                    MPM.setAddBy("INITIAL");
                    MPM.setEditedDate(stringDate);
                    MPM.setEditedBy("INITIAL");
                    MPM.setIsActive("Y");

                masterProductAO masterProductAO = new masterProductAO();
                String responseAddProduct = masterProductAO.saveMasterProduct(MPM);

                //System.out.println(responseAddProduct);

                JSONObject stockPlatform = platformStockData.getJSONObject("stockPerPlatform");
                //System.out.println(stockPlatform);
                JSONObject platformId = platformStockData.getJSONObject("idPlatform");
                //System.out.println(platformId);
                int ix = platformStockData.getInt("platformLoops");
                for(int i = 0; i < ix; i++){
                    String stock = stockPlatform.getString(String.valueOf(i));
                    int id = platformId.getInt(String.valueOf(i));

                    System.out.println("platformid : "+id+"stock : "+stock);
                    masterProductStockMod MPSM = new masterProductStockMod();
                        MPSM.setProductId(Integer.parseInt(responseAddProduct));
                        MPSM.setPlatformId(id);
                        MPSM.setStockQty(Integer.parseInt(stock));
                        MPSM.setWasteQty(0);
                        MPSM.setStockDescriptions("");
                        MPSM.setAddDate(stringDate);
                        MPSM.setAddBy("INITIAL");
                        MPSM.setEditedDate(stringDate);
                        MPSM.setEditedBy("INITIAL");
                        MPSM.setIsActive("Y");

                        masterProductStockAO MPSAO = new masterProductStockAO();
                        MPSAO.saveProductStock(MPSM);
                }

                for(int ixa = 0; ixa < productPhotoData.length(); ixa++){
                    JSONObject photoData = productPhotoData.getJSONObject(ixa);
                    int temporaryId = photoData.getInt("temporaryId");
                    String temporaryPath = photoData.getString("temporaryPath");

                    photoMod PM = new photoMod();
                        PM.setProductId(Integer.parseInt(responseAddProduct));
                        PM.setPhotoLink("product_photo/"+temporaryPath);
                        PM.setPhotoDescriptions("");
                        PM.setPhotoAlt(String.valueOf(temporaryId));
                        PM.setAddDate(stringDate);
                        PM.setAddBy("INITIAL");
                        PM.setEditedDate(stringDate);
                        PM.setEditedBy("INITIAL");
                        PM.setIsActive("Y");
                        String isPrimary = (ixa == 0) ? "Y" : "N";
                        PM.setIsPrimary(isPrimary);

                        photoAO PAO = new photoAO();
                        PAO.savePhoto(PM);
                }

                JSONObject JSONObjectRoot = new JSONObject();
                JSONArray HEAD_DATA = new JSONArray();
                JSONObject DATA = new JSONObject();

                DATA.put("RESULT", "success");
                DATA.put("MESSAGE", "SUCCESS. Process result with no-errors.");
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
