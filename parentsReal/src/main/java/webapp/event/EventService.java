package webapp.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
import webapp.database.Event;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.EventRepository;

import javax.sound.midi.Track;
import java.beans.EventHandler;

/**
 * Created by thanasis on 22/6/2017.
 */
@Service
public class EventService {

    @Autowired
    EventRepository eventHandler;
    @Autowired
    CustomerRepository customerRepository;

    int getAvailableSpots(Event event){
        return event.getSpots()-event.getCustomers().size();
    }

    boolean bookEvent(Event event,Customer customer,int numberOfSpots) throws Exception{
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
        event.getCustomers().add(customer);
        customer.setPoints(customer.getPoints()-event.getPrice());
        eventHandler.save(event);
        customerRepository.save(customer);
        //somehow unlock here
        return true;
    }

}
