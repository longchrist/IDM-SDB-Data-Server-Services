package com.idm.controller;

import com.idm.connection.Encryptor;
import com.idm.dao.masterCustomerAO;
import com.idm.model.masterCustomerMod;
import com.idm.model.responseInfoServices;
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
@RequestMapping(value="/customer")
public class masterCustomerController {
    @Autowired
    ServletContext context;
    private Connection conn = null;

    @CrossOrigin
    @RequestMapping(value="/getallcustomer", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getAllCustomer() {
        String All = "";
        masterCustomerAO MCCAO = new masterCustomerAO();
        All = MCCAO.getAllMasterCustomer();
        return All;
    }


    @CrossOrigin
    @RequestMapping(value="/addcustomer", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> addMasterCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                String stringDate = sdfDate.format(now);

                JSONObject dataObject = new JSONObject(data);

                masterCustomerMod MCM = new masterCustomerMod();
                MCM.setCustomerName(dataObject.getString("CUSTOMER_NAME"));
                MCM.setCustomerAddress(dataObject.getString("CUSTOMER_ADDRESS"));
                MCM.setCustomerProvince(dataObject.getString("CUSTOMER_PROVINCE"));
                MCM.setCustomerCity(dataObject.getString("CUSTOMER_CITY"));
                MCM.setCustomerZip(dataObject.getString("CUSTOMER_ZIP"));
                MCM.setCustomerCountry(dataObject.getString("CUSTOMER_COUNTRY"));
                MCM.setCustomerPhone(dataObject.getString("CUSTOMER_PHONE"));
                MCM.setAddDate(stringDate);
                MCM.setAddBy("INITIAL");
                MCM.setEditedDate(stringDate);
                MCM.setEditedBy("INITIAL");
                MCM.setIsActive("Y");

                masterCustomerAO MCAO = new masterCustomerAO();
                String jsonResponse = MCAO.saveMasterCustomer(MCM);

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
    @RequestMapping(value="/editcustomer", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> editMasterCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                String stringDate = sdfDate.format(now);

                JSONObject dataObject = new JSONObject(data);

                masterCustomerMod MCM = new masterCustomerMod();
                MCM.setCustomerId(dataObject.getInt("CUSTOMER_ID"));
                MCM.setCustomerName(dataObject.getString("CUSTOMER_NAME"));
                MCM.setCustomerAddress(dataObject.getString("CUSTOMER_ADDRESS"));
                MCM.setCustomerProvince(dataObject.getString("CUSTOMER_PROVINCE"));
                MCM.setCustomerCity(dataObject.getString("CUSTOMER_CITY"));
                MCM.setCustomerZip(dataObject.getString("CUSTOMER_ZIP"));
                MCM.setCustomerCountry(dataObject.getString("CUSTOMER_COUNTRY"));
                MCM.setCustomerPhone(dataObject.getString("CUSTOMER_PHONE"));
                MCM.setEditedDate(stringDate);
                MCM.setEditedBy("EDITED");
                MCM.setIsActive("Y");

                masterCustomerAO MCAO = new masterCustomerAO();
                String jsonResponse = MCAO.updateMasterCustomer(MCM);

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
