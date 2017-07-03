package webapp.usercontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webapp.database.Customer;
import webapp.database.Location;
import webapp.database.Organiser;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.EventRepository;
import webapp.database.repositories.LocationRepository;
import webapp.database.repositories.OrganiserRepository;

import java.util.List;

/**
 * Created by thanasis on 22/6/2017.
 */
@Controller
public class AdminController {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    OrganiserRepository organiserRepository;
    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(value="/admin/profile")
    public String profilePage(Model model){
        return "profile/admin/profile";
    }

    @RequestMapping(value="/admin/accept_location",method= RequestMethod.GET)
    public String acceptLocations(Model model){

        List<Location> locations = locationRepository.findAllByCertificateChecked(false);
        System.out.println(locations.toString());
        model.addAttribute("locations", locations);

        return "profile/admin/accept_location";
    }

    @RequestMapping(value="/admin/manage_organisers",method= RequestMethod.GET)
    public String manageOrganiser(Model model){

        Iterable<Organiser> organisers = organiserRepository.findAll();
        model.addAttribute("organisers", organisers);

        return "profile/admin/manage_organisers";
    }

    @RequestMapping(value="/admin/manage_parents",method= RequestMethod.GET)
    public String manageParent(Model model){

        Iterable<Customer> customers = customerRepository.findAll();
        model.addAttribute("parents", customers);

        return "profile/admin/manage_parents";
    }
}
