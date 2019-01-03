package com.idm.controller;

import com.idm.connection.Encryptor;
import com.idm.dao.masterPackagingAO;
import com.idm.model.masterPackagingMod;
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
@RequestMapping(value="/packaging")
public class masterPackagingController {
    @Autowired
    ServletContext context;
    private Connection conn = null;

    @CrossOrigin
    @RequestMapping(value="/getallpackaging", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getAllPackaging() {
        String All = "";
        masterPackagingAO MPAO = new masterPackagingAO();
        All = MPAO.getAllMasterPackaging();
        return All;
    }

    @CrossOrigin
    @RequestMapping(value="/getpackaging", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> getMasterPackagingData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                JSONObject dataObject = new JSONObject(data);

                masterPackagingMod MPM = new masterPackagingMod();
                MPM.setPackagingId(dataObject.getInt("PACKAGING_ID"));

                masterPackagingAO MPAO = new masterPackagingAO();
                String jsonResponse = MPAO.getMasterPackaging(MPM);

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
    @RequestMapping(value="/addpackaging", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> addMasterPackagingData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                String stringDate = sdfDate.format(now);

                JSONObject dataObject = new JSONObject(data);

                masterPackagingMod MPM = new masterPackagingMod();
                MPM.setPackagingType(dataObject.getString("PACKAGING_TYPE"));
                MPM.setPackagingPrice(dataObject.getInt("PACKAGING_PRICE"));
                MPM.setAddDate(stringDate);
                MPM.setAddBy("INITIAL");
                MPM.setEditedDate(stringDate);
                MPM.setEditedBy("INITIAL");
                MPM.setIsActive("Y");

                masterPackagingAO MPAO = new masterPackagingAO();
                String jsonResponse = MPAO.saveMasterPackaging(MPM);

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
    @RequestMapping(value="/editpackaging", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> editMasterPackagingData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                String stringDate = sdfDate.format(now);

                JSONObject dataObject = new JSONObject(data);

                masterPackagingMod MPM = new masterPackagingMod();
                MPM.setPackagingType(dataObject.getString("PACKAGING_TYPE"));
                MPM.setPackagingPrice(dataObject.getInt("PACKAGING_PRICE"));
                MPM.setEditedDate(stringDate);
                MPM.setEditedBy("EDITED");
                MPM.setIsActive("Y");
                MPM.setPackagingId(dataObject.getInt("PACKAGING_ID"));

                masterPackagingAO MPAO = new masterPackagingAO();
                String jsonResponse = MPAO.updateMasterPackaging(MPM);

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
    @RequestMapping(value="/deletepackaging", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> deleteMasterPackagingData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                Encryptor enc = new Encryptor();
                String dataDecrypt = enc.decrypt(data);
                JSONObject dataObject = new JSONObject(dataDecrypt);

                masterPackagingMod MPM = new masterPackagingMod();
                MPM.setPackagingId(dataObject.getInt("PACKAGING_ID"));

                masterPackagingAO MPAO = new masterPackagingAO();
                String jsonResponse = MPAO.deleteMasterPackaging(MPM);

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
