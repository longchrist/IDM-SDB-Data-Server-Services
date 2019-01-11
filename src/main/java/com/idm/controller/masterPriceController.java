package com.idm.controller;

import com.idm.connection.Encryptor;
import com.idm.dao.masterCategoryAO;
import com.idm.dao.masterPriceAO;
import com.idm.model.masterCategoryMod;
import com.idm.model.masterPriceMod;
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
@RequestMapping(value="/pricing")
public class masterPriceController {
    @Autowired
    ServletContext context;
    private Connection conn = null;

    @CrossOrigin
    @RequestMapping(value="/editproductpricing", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> editPricingProductData(@Valid @RequestParam("timestamp") String timestamp, @Valid @RequestParam("data") String data) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!timestamp.equals("") && !data.equals("")) {
            try {
                JSONObject dataObject = new JSONObject(data);

                masterPriceMod MPM = new masterPriceMod();
                MPM.setPricePerUnit(dataObject.getInt("PRICE_PER_UNIT"));
                MPM.setSalesPrice(dataObject.getInt("SALES_PRICE"));
                MPM.setStartDate(dataObject.getString("PRICE_START_DATE"));
                MPM.setEndDate(dataObject.getString("PRICE_END_DATE"));
                MPM.setPriceId(dataObject.getInt("PRICING_ID"));

                masterPriceAO MPAO = new masterPriceAO();
                String jsonResponse = MPAO.updateMasterPrice(MPM);

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
}
