/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idm.controller;

import java.sql.Connection;
import javax.servlet.ServletContext;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
}
