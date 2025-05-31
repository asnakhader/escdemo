package com.asnakhader.ecs.repository;

import com.asnakhader.ecs.model.PassengerDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PassengerRepository extends MongoRepository<PassengerDetails, String> {
    //PassengerDetails findByPNR(String pnr);
}
