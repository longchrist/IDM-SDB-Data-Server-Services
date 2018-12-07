package com.idm.controller;

import com.idm.connection.Encryptor;
import com.idm.dao.masterSubCategoryAO;
import com.idm.model.masterSubCategoryMod;
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

@RestController
@RequestMapping(value="/subcategory")
public class masterSubCategoryController {
    @Autowired
    ServletContext context;
    private Connection conn = null;

    @CrossOrigin
    @RequestMapping(value="/getallsubcategory", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getAllSubCategory() {
        String All = "";
        masterSubCategoryAO MSCAO = new masterSubCategoryAO();
        All = MSCAO.getAllMasterSubCategory();
        return All;
    }

    // get sub category by category_id variables
    @CrossOrigin
    @RequestMapping(value="/getsubcategory", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> getMasterSubCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                JSONObject dataObject = new JSONObject(data);

                masterSubCategoryMod MSCM = new masterSubCategoryMod();
                MSCM.setCategoryId(dataObject.getInt("CATEGORY_ID"));

                masterSubCategoryAO MSCAO = new masterSubCategoryAO();
                String jsonResponse = MSCAO.getMasterSubCategory(MSCM);

                RIS.setJsonResponse(jsonResponse);

                headers.add("Response", jsonResponse);
                return new ResponseEntity<responseInfoServices>(RIS, headers, HttpStatus.OK);

                /*Encryptor enc = new Encryptor();
                String dataDecrypt = enc.decrypt(data);
                JSONObject dataObject = new JSONObject(dataDecrypt);

                masterSubCategoryMod MSCM = new masterSubCategoryMod();
                MSCM.setCategoryId(dataObject.getInt("category_id"));

                masterSubCategoryAO MSCAO = new masterSubCategoryAO();
                String jsonResponse = MSCAO.getMasterSubCategory(MSCM);

                RIS.setJsonResponse(jsonResponse);

                headers.add("Response", jsonResponse);
                return new ResponseEntity<responseInfoServices>(RIS, headers, HttpStatus.OK);*/
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
    @RequestMapping(value="/addsubcategory", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> addMasterSubCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                Encryptor enc = new Encryptor();
                String dataDecrypt = enc.decrypt(data);
                JSONObject dataObject = new JSONObject(dataDecrypt);

                masterSubCategoryMod MSCM = new masterSubCategoryMod();
                MSCM.setCategoryId(dataObject.getInt("CATEGORY_ID"));
                MSCM.setSubCategory(dataObject.getString("SUB_CATEGORY"));
                MSCM.setIsActive(dataObject.getString("IS_ACTIVE"));

                masterSubCategoryAO MSCAO = new masterSubCategoryAO();
                String jsonResponse = MSCAO.saveMasterSubCategory(MSCM);

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
    @RequestMapping(value="/editsubcategory", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> editMasterSubCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                Encryptor enc = new Encryptor();
                String dataDecrypt = enc.decrypt(data);
                JSONObject dataObject = new JSONObject(dataDecrypt);

                masterSubCategoryMod MSCM = new masterSubCategoryMod();
                MSCM.setCategoryId(dataObject.getInt("CATEGORY_ID"));
                MSCM.setSubCategory(dataObject.getString("SUB_CATEGORY"));
                MSCM.setIsActive(dataObject.getString("IS_ACTIVE"));
                MSCM.setSubCategoryId(dataObject.getInt("SUB_CATEGORY_ID"));

                masterSubCategoryAO MSCAO = new masterSubCategoryAO();
                String jsonResponse = MSCAO.updateMasterSubCategory(MSCM);

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
    @RequestMapping(value="/deletesubcategory", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> deleteMasterSubCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                Encryptor enc = new Encryptor();
                String dataDecrypt = enc.decrypt(data);
                JSONObject dataObject = new JSONObject(dataDecrypt);

                masterSubCategoryMod MSCM = new masterSubCategoryMod();
                MSCM.setSubCategoryId(dataObject.getInt("SUB_CATEGORY_ID"));

                masterSubCategoryAO MSCAO = new masterSubCategoryAO();
                String jsonResponse = MSCAO.deleteMasterSubCategory(MSCM);

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
}
