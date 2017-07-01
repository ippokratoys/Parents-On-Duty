package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.database.BookEvent;
import webapp.database.Customer;
import webapp.database.Event;
import webapp.database.Organiser;
import webapp.database.repositories.BookEventRepository;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.EventRepository;
import webapp.database.repositories.OrganiserRepository;

import java.util.Date;

/**
 * Created by thanasis on 22/6/2017.
 */
@Service
public class EventService {

    @Autowired
    EventRepository eventHandler;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BookEventRepository bookEventRepository;
    @Autowired
    OrganiserRepository organiserRepository;

    public int getAvailableSpots(Event event){
        return event.getSpots()-event.getBookEvents().size();
    }

    public boolean bookEvent(Event event,Customer customer,int numberOfSpots) throws Exception{
        if(event==null){
            throw new Exception("Event is null");
        }
        if(customer==null){
            throw new Exception("Customer is null");
        }

////////////////somehow lock here//////////////////////
        if(customer.getPoints()<(event.getPrice()*numberOfSpots)){
            throw new Exception("Not enough points");
        }
        if(getAvailableSpots(event)<numberOfSpots){
            throw new Exception("Not enough sports available");
        }

        for (int i=0 ; i < numberOfSpots; i++) {

            BookEvent bookEventNew = new BookEvent();
            bookEventNew.setBookDate(new Date());
            bookEventNew.setCustomer(customer);
            bookEventNew.setEvent(event);
            bookEventRepository.save(bookEventNew);

            customer.setPoints(customer.getPoints() - event.getPrice());
            Organiser organiser = event.getOrganiser();
            organiser.addPoints(event.getPrice());
            eventHandler.save(event);
            customerRepository.save(customer);
        }
//////////////////somehow unlock here//////////////////////////////////
        return true;
    }
}
