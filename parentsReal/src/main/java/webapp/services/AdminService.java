package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
import webapp.database.Organiser;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.LocationRepository;
import webapp.database.repositories.OrganiserRepository;

/**
 * Created by ilias on 4/7/2017.
 */
@Service
public class AdminService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrganiserRepository organiserRepository;


    public boolean blockOrganiser(String id){
        Organiser organiser = organiserRepository.findOne(id);

        organiser.getLogin().setActive(false);

        return true;

    }

    public boolean unblockOrganiser(String id){
        Organiser organiser = organiserRepository.findOne(id);

        organiser.getLogin().setActive(true);

        return true;
    }

    public  boolean blockParent(String id){
        Customer costumer = customerRepository.findOne(id);

        costumer.getLogin().setActive(false);

        return true;
    }

    public  boolean unblockParent(String id){
        Customer costumer = customerRepository.findOne(id);

        costumer.getLogin().setActive(true);

        return true;
    }
}
