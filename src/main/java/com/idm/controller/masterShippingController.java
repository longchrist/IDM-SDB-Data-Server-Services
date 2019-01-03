package com.idm.controller;

import com.idm.dao.masterShippingAO;
import com.idm.model.masterShippingMod;
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
@RequestMapping(value="/shipping")
public class masterShippingController {
    @Autowired
    ServletContext context;
    private Connection conn = null;

    @CrossOrigin
    @RequestMapping(value="/getallshipping", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getAllShipping() {
        String All = "";
        masterShippingAO MSAO = new masterShippingAO();
        All = MSAO.getAllMasterShipping();
        return All;
    }

    @CrossOrigin
    @RequestMapping(value="/addshipping", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> addMasterShippingData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                String stringDate = sdfDate.format(now);

                JSONObject dataObject = new JSONObject(data);

                masterShippingMod MSM = new masterShippingMod();
                MSM.setShippingCourier(dataObject.getString("SHIPPING_COURIER"));
                MSM.setShippingService(dataObject.getString("SHIPPING_SERVICE"));
                MSM.setAddDate(stringDate);
                MSM.setAddBy("INITIAL");
                MSM.setEditedDate(stringDate);
                MSM.setEditedBy("INITIAL");
                MSM.setIsActive(dataObject.getString("IS_ACTIVE"));

                masterShippingAO MSAO = new masterShippingAO();
                String jsonResponse = MSAO.saveMasterShipping(MSM);

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
    @RequestMapping(value="/editshipping", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> editMasterShippingData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                String stringDate = sdfDate.format(now);

                JSONObject dataObject = new JSONObject(data);

                masterShippingMod MSM = new masterShippingMod();
                MSM.setShippingCourier(dataObject.getString("SHIPPING_COURIER"));
                MSM.setShippingService(dataObject.getString("SHIPPING_SERVICE"));
                MSM.setEditedDate(stringDate);
                MSM.setEditedBy("EDITED");
                MSM.setIsActive(dataObject.getString("IS_ACTIVE"));
                MSM.setShippingId(dataObject.getInt("SHIPPING_ID"));

                masterShippingAO MSAO = new masterShippingAO();
                String jsonResponse = MSAO.updateMasterShipping(MSM);

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
