package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
import webapp.database.Event;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.EventRepository;

/**
 * Created by thanasis on 22/6/2017.
 */
@Service
public class EventService {

    @Autowired
    EventRepository eventHandler;
    @Autowired
    CustomerRepository customerRepository;

    public int getAvailableSpots(Event event){
        return 10;
//        return event.getSpots()-event.getEventHasCustomers().size();
    }

    public boolean bookEvent(Event event,Customer customer,int numberOfSpots) throws Exception{
        if(event==null){
            throw new Exception("Event is null");
        }
        if(customer==null){
            throw new Exception("Customer is null");
        }

        //somehow lock here
        if(customer.getPoints()<event.getPrice()){
            throw new Exception("Not enough points");
        }
        if(getAvailableSpots(event)<numberOfSpots){
            throw new Exception("Not enough sports available");
        }
//        event.getEventHasCustomers().add(customer);
        customer.setPoints(customer.getPoints()-event.getPrice());
        eventHandler.save(event);
        customerRepository.save(customer);
        //somehow unlock here
        return true;
    }

}
