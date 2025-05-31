package com.asnakhader.ecs.service;

import com.asnakhader.ecs.model.PassengerDetails;
import com.asnakhader.ecs.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public PassengerDetails createPassenger() {
        PassengerDetails pax = new PassengerDetails();
        pax.setName("Alice");
        pax.setPnr("1234");
        passengerRepository.save(pax);
        return pax;
    }

    public List<PassengerDetails> getAllPassengers() {
        return passengerRepository.findAll();
    }
}
