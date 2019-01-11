package com.idm.controller;

import com.idm.connection.Encryptor;
import com.idm.dao.*;
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
@RequestMapping(value="/transaction")
public class salesTransactionController {
    @Autowired
    ServletContext context;
    private Connection conn = null;

    @CrossOrigin
    @RequestMapping(value="/getalltransaction", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getAllTransaction() {
        String All = "";
        salesTransactionAO MTAO = new salesTransactionAO();
        All = MTAO.getAllTransaction();
        return All;
    }

    // get transaction filtered by date
    @CrossOrigin
    @RequestMapping(value="/gettransactionrange", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> getSalesTransaction(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                JSONObject dataObject = new JSONObject(data);

                transactionDateRange TDR = new transactionDateRange();
                TDR.setTransactionDateFrom(dataObject.getString("SEARCH_DATE_FROM"));
                TDR.setTransactionDateTo(dataObject.getString("SEARCH_DATE_TO"));

                salesTransactionAO STAO = new salesTransactionAO();
                String jsonResponse = STAO.getTransaction(TDR);

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

    @CrossOrigin
    @RequestMapping(value="/addtransaction", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    //public ResponseEntity<responseInfoServices> addSalesTransactionData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
    public ResponseEntity<responseInfoServices> addSalesTransactionData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                String stringDate = sdfDate.format(now);

                JSONObject dataObject = new JSONObject(data);
                String TRANSACTION_DATA = dataObject.toString();
                JSONObject TRANSACTION_HEADING_DATA = dataObject.getJSONObject("TRANSACTION_HEADING_DATA");
                JSONObject TRANSACTION_DETAIL_DATA = dataObject.getJSONObject("TRANSACTION_DETAIL_DATA");

                System.out.println(TRANSACTION_HEADING_DATA);
                System.out.println(TRANSACTION_DETAIL_DATA);

                    transactionMod TM = new transactionMod();
                    TM.setCustomerId(TRANSACTION_HEADING_DATA.getInt("CUSTOMER_ID"));
                    TM.setPlatformId(TRANSACTION_HEADING_DATA.getInt("PLATFORM_ID"));
                    TM.setPackagingId(TRANSACTION_HEADING_DATA.getString("PACKAGING_ID"));
                    TM.setShippingId(TRANSACTION_HEADING_DATA.getInt("SHIPPING_ID"));
                    TM.setInvoice(TRANSACTION_HEADING_DATA.getString("INVOICE"));
                    TM.setTotalTransaction(TRANSACTION_HEADING_DATA.getInt("TOTAL_TRANSACTION"));
                    TM.setPackagingPrice(TRANSACTION_HEADING_DATA.getInt("PACKAGING_PRICE"));
                    TM.setShippingPrice(TRANSACTION_HEADING_DATA.getInt("SHIPPING_PRICE"));
                    TM.setTransactionDate(TRANSACTION_HEADING_DATA.getString("TRANSACTION_DATE"));
                    TM.setIsPreorder(TRANSACTION_HEADING_DATA.getString("IS_PREORDER"));
                    TM.setAddDate(stringDate);
                    TM.setAddBy("INITIAL");
                    TM.setEditedDate(stringDate);
                    TM.setEditedBy("INITIAL");

                    salesTransactionAO MTAO = new salesTransactionAO();
                    int newGeneratedTransactionId = Integer.parseInt(MTAO.saveTransaction(TM));

                    int transactionProductCounting = TRANSACTION_DETAIL_DATA.getInt("transactionCount");
                    JSONArray transactionDetailArr = TRANSACTION_DETAIL_DATA.getJSONArray("transactionDetail");

                    for(int ix = 0; ix < transactionProductCounting; ix++){
                        JSONObject productDetailData = transactionDetailArr.getJSONObject(ix);
                        int currentProductStock = productDetailData.getInt("currentProductStock");
                        String isPreorderOut = productDetailData.getString("isPreorderOut");
                        int productId = productDetailData.getInt("productId");
                        String productName = productDetailData.getString("productName");
                        int pricePerUnit = productDetailData.getInt("pricePerUnit");
                        int salesPrice = productDetailData.getInt("salesPrice");
                        String productNotesOut = productDetailData.getString("productNotesOut");
                        int quantityOut = productDetailData.getInt("quantityOut");

                        transactionDetailMod TDM = new transactionDetailMod();
                        TDM.setTransactionId(newGeneratedTransactionId);
                        TDM.setProductId(productId);
                        TDM.setProductName(productName);
                        TDM.setProductQty(quantityOut);
                        TDM.setProductPricePerUnit(pricePerUnit);
                        TDM.setProductSalesPerUnit(salesPrice);
                        TDM.setIsPreorder(isPreorderOut);
                        TDM.setDescriptions(productNotesOut);
                        //System.out.println("Data Detail Items #"+ix+" : "+currentProductStock+" - "+isPreorderOut+" - "+productId+" - "+salesPrice+" - "+productNotesOut+" - "+quantityOut);

                        // minus the stock function NOT FIXED
                        masterProductStockMod MPSM = new masterProductStockMod();
                        MPSM.setStockQty(quantityOut);
                        MPSM.setProductId(productId);
                        MPSM.setPlatformId(TRANSACTION_HEADING_DATA.getInt("PLATFORM_ID"));

                        masterProductStockAO MPSAO = new masterProductStockAO();
                        String test = MPSAO.updateTransactionProductStockOut(MPSM);

                        System.out.println(test);
                        // end of minus stock function

                        salesTransactionDetailAO STDAO = new salesTransactionDetailAO();
                        STDAO.saveTransactionDetail(TDM);
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

    /*
    @CrossOrigin
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
