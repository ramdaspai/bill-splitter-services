package com.app.billsplitter.service;

import com.app.billsplitter.entity.Roommate;
import com.app.billsplitter.repository.RoommateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoommateService {

    private final Logger LOG = LoggerFactory.getLogger(RoommateService.class);

    private RoommateRepository roommateRepository;

    public RoommateService(RoommateRepository roommateRepository) {
        this.roommateRepository = roommateRepository;
    }


    public List<Roommate> getAllRoommates() {
        LOG.info("Roommate list is: " + roommateRepository.findAll());
        return roommateRepository.findAll();
    }

    public List<Roommate> updateRoommates(List<Roommate> roommates) {
        return roommateRepository.saveAll(roommates);
    }

    public void removeRoommates(List<Roommate> roommates) {
        roommateRepository.deleteAll(roommates);
    }

    public Roommate getRoommateByPhone(String phoneNo) {
        return roommateRepository.findByPhone(phoneNo);
    }
}
