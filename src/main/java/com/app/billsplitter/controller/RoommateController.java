package com.app.billsplitter.controller;

import com.app.billsplitter.entity.Roommate;
import com.app.billsplitter.service.RoommateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
public class RoommateController {

    private final Logger LOG = LoggerFactory.getLogger(RoommateController.class);

    private RoommateService roommateService;

    public RoommateController(RoommateService roommateService) {
        this.roommateService = roommateService;
    }

    @GetMapping(path = "/api/roommates", produces = APPLICATION_JSON_VALUE)
    public List<Roommate> getAllRoommates() {
        LOG.info("Getting all the roommates");
        return roommateService.getAllRoommates();
    }

    @PostMapping(path = "/api/roommates/update", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<Roommate> updateRoommates(@RequestBody List<Roommate> roommates) {
        LOG.info("Roommates to update / insert: " + roommates);
        return roommateService.updateRoommates(roommates);
    }

    @PostMapping(path = "/api/roommates/delete", consumes = APPLICATION_JSON_VALUE)
    public void deleteRoommates(@RequestBody List<Roommate> roommates) {
        LOG.info("Roommates to delete: " + roommates);
        roommateService.removeRoommates(roommates);
    }

    @GetMapping(path = "/api/roommates/{phone}", produces = APPLICATION_JSON_VALUE)
    public Roommate getRoommateByPhone(@PathVariable("phone") String phoneNo) {
        LOG.info("Getting roommate details for phone: " + phoneNo);
        Roommate roommate = roommateService.getRoommateByPhone(phoneNo);
        LOG.info("Details are: " + roommate);
        return roommate;
    }
}
