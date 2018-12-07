/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idm.controller;

import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.validation.Valid;

import com.idm.connection.Encryptor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.idm.connection.dbConnection;
import com.idm.model.responseInfoServices;

/**
 *
 * @author user
 */
@RestController
@RequestMapping(value="/test")
public class test {
    
    @Autowired
    ServletContext context;
    private Connection conn = null;

    @CrossOrigin
    @RequestMapping(value="/testConnection", method=RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getClientWalkthroughChecklist() {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        dbConnection dbcon = new dbConnection();
        Connection conn = null;
        conn = dbcon.getConnection();

        String All = "";
        JSONObject JSONObjectRoot = new JSONObject();
        JSONObjectRoot.put("Message", "IDM Server Service Response : "+conn.toString());
        All += JSONObjectRoot.toString();
        return All;
    }

    @CrossOrigin
    @RequestMapping(value="/getEncrypt", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> getEncrypt(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                System.out.println(data);

                Encryptor enc = new Encryptor();
                String dataEncrypt = enc.encrypt(data);

                System.out.println(dataEncrypt);

                headers.add("Response", dataEncrypt);
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
    @RequestMapping(value="/getDecrypt", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> getDecrypt(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                System.out.println("Original Data : "+data);

                Encryptor enc = new Encryptor();
                String dataDecrypt = enc.decrypt(data);

                System.out.println(dataDecrypt);

                headers.add("Response", dataDecrypt);
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
