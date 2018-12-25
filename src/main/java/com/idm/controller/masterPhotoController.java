package com.idm.controller;

import com.idm.model.responseInfoServices;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

@RestController
@RequestMapping(value="/photo")
public class masterPhotoController {
    @Autowired
    ServletContext context;
    private Connection conn = null;

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://xampp//htdocs//IDM_CMS//web_resources//product_photo//";

    @CrossOrigin
    @RequestMapping(value="/uploadphoto", method= RequestMethod.POST)
    public ResponseEntity<responseInfoServices> uploadProductPhoto(@Valid @RequestParam("file") MultipartFile formData) {
        try {
            responseInfoServices RIS = new responseInfoServices();
            HttpHeaders headers = new HttpHeaders();

            // Get the file and save it somewhere
            byte[] bytes = formData.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + formData.getOriginalFilename());
            Files.write(path, bytes);

            String jsonResponse = "";
            JSONObject JSONObjectRoot = new JSONObject();
            JSONArray DATA_UPLOAD_PHOTO = new JSONArray();

            JSONObject PHOTO_DATA = new JSONObject();
            PHOTO_DATA.put("UPLOAD_PATH", new String(formData.getOriginalFilename()));
            DATA_UPLOAD_PHOTO.put(PHOTO_DATA);

            JSONObjectRoot.put("DATA_UPLOAD_PHOTO", DATA_UPLOAD_PHOTO);
            jsonResponse += JSONObjectRoot.toString();

            RIS.setJsonResponse(jsonResponse);

            headers.add("Response", jsonResponse);

            //System.out.println("message You successfully uploaded '" + formData.getOriginalFilename() + "'");

            return new ResponseEntity<responseInfoServices>(RIS, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<responseInfoServices>(HttpStatus.BAD_REQUEST);
        }
    }

}
