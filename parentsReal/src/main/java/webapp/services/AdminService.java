package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
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


}
