package com.idm.controller;

import com.idm.connection.Encryptor;
import com.idm.dao.masterCategoryAO;
import com.idm.model.responseInfoServices;
import com.idm.model.masterCategoryMod;

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
@RequestMapping(value="/category")
public class masterCategoryController {
    @Autowired
    ServletContext context;
    private Connection conn = null;

    @CrossOrigin
    @RequestMapping(value="/getallcategory", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getAllCategory() {
        String All = "";
        masterCategoryAO MCCAO = new masterCategoryAO();
        All = MCCAO.getAllMasterCategory();
        return All;
    }

    @CrossOrigin
    @RequestMapping(value="/getcategory", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> getMasterCategoryData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                Encryptor enc = new Encryptor();
                String dataDecrypt = enc.decrypt(data);
                JSONObject dataObject = new JSONObject(dataDecrypt);

                masterCategoryMod MWAM = new masterCategoryMod();
                MWAM.setCategoryId(dataObject.getInt("category_id"));

                masterCategoryAO MCAO = new masterCategoryAO();
                String jsonResponse = MCAO.getMasterCategory(MWAM);

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
    }

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
    }
}
