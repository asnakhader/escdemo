package com.asnakhader.ecs.controller;

import com.asnakhader.ecs.model.PassengerDetails;
import com.asnakhader.ecs.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pax")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    // Save a passenger
    @PostMapping("/save")
    public PassengerDetails savePassenger(@RequestBody PassengerDetails passenger) {
        return passengerService.createPassenger();
    }

    // Get all passengers
    @GetMapping("/findAll")
    public List<PassengerDetails> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

}
