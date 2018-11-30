package com.idm.controller;

import com.idm.dao.masterCategoryAO;
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

    /*@CrossOrigin
    @RequestMapping(value="/getspecificactivities", method=RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> getSpecificWalkthroughMasterActivitiesResources(@Valid @RequestParam("activities_bu") String bu) {
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!bu.equals("")) {
            try {
                masterWalkthroughActivitiesMod MWAM = new masterWalkthroughActivitiesMod();
                MWAM.setActivitiesBu(bu); // ini adalah integer, bisa diubah sesuai dengan isi recid nanti di database

                masterWalkthroughActivitiesDAO MWADAO = new masterWalkthroughActivitiesDAO();
                String jsonResponse = MWADAO.getMasterWalkthroughActivities(MWAM);

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
    @RequestMapping(value="/addactivities", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> addWalkthroughMasterActivitiesResources(
            @RequestParam("addActivitiesBu") String activitiesBu,
            @RequestParam("addActivitiesDiv") String activitiesDiv,
            @RequestParam("addActivitiesName") String activitiesName,
            @RequestParam("addActivitiesTimeStart") String activitiesTimeStart,
            @RequestParam("addActivitiesTimeEnd") String activitiesTimeEnd,
            @RequestParam("addCreatedOpt") String createdOpt) {

        // automatically add : created_opt, created_dt, edited_opt, edited_dt, client_address
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!activitiesBu.equals("") && !activitiesDiv.equals("") && !activitiesName.equals("")) {
            try {
                masterWalkthroughActivitiesMod MWAM = new masterWalkthroughActivitiesMod();
                MWAM.setActivitiesBu(activitiesBu);
                MWAM.setActivitiesDiv(activitiesDiv);
                MWAM.setActivitiesName(activitiesName);
                MWAM.setActivitiesTimeStart(activitiesTimeStart);
                MWAM.setActivitiesTimeEnd(activitiesTimeEnd);
                MWAM.setCreatedOpt(createdOpt);

                masterWalkthroughActivitiesDAO MWADAO = new masterWalkthroughActivitiesDAO();
                String jsonResponse = MWADAO.saveMasterWalkthroughActivities(MWAM);

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
    @RequestMapping(value="/editactivities", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> editWalkthroughMasterActivitiesResources(
            @RequestParam("editActivitiesTimeStart") String activitiesTimeStart,
            @RequestParam("editActivitiesTimeEnd") String activitiesTimeEnd,
            @RequestParam("editEditedOpt") String editedOpt,
            @RequestParam("editActivitiesBu") String activitiesBu,
            @RequestParam("editActivitiesDiv") String activitiesDiv,
            @RequestParam("editActivitiesName") String activitiesName) {

        // automatically add : created_opt, created_dt, edited_opt, edited_dt, client_address
        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!activitiesBu.equals("") && !activitiesDiv.equals("") && !activitiesName.equals("")) {
            try {
                masterWalkthroughActivitiesMod MWAM = new masterWalkthroughActivitiesMod();
                MWAM.setActivitiesTimeStart(activitiesTimeStart);
                MWAM.setActivitiesTimeEnd(activitiesTimeEnd);
                MWAM.setEditedOpt(editedOpt);
                MWAM.setActivitiesBu(activitiesBu);
                MWAM.setActivitiesDiv(activitiesDiv);
                MWAM.setActivitiesName(activitiesName);

                masterWalkthroughActivitiesDAO MWADAO = new masterWalkthroughActivitiesDAO();
                String jsonResponse = MWADAO.updateMasterWalkthroughActivities(MWAM);

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
    @RequestMapping(value="/deleteactivities", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<responseInfoServices> deleteWalkthroughMasterActivitiesResources(
            @RequestParam("deleteActivitiesBu") String activitiesBu,
            @RequestParam("deleteActivitiesDiv") String activitiesDiv,
            @RequestParam("deleteActivitiesName") String activitiesName) {

        responseInfoServices RIS = new responseInfoServices();
        HttpHeaders headers = new HttpHeaders();

        if(!activitiesBu.equals("") && !activitiesDiv.equals("") && !activitiesName.equals("")) {
            try {
                masterWalkthroughActivitiesMod MWAM = new masterWalkthroughActivitiesMod();
                MWAM.setActivitiesBu(activitiesBu);
                MWAM.setActivitiesDiv(activitiesDiv);
                MWAM.setActivitiesName(activitiesName);

                masterWalkthroughActivitiesDAO MWADAO = new masterWalkthroughActivitiesDAO();
                String jsonResponse = MWADAO.deleteMasterWalkthroughActivities(MWAM);

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
    }*/
}
