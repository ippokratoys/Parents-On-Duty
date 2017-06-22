package webapp.usercontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webapp.database.Customer;
import webapp.database.Location;
import webapp.database.Locationowner;
import webapp.database.Organiser;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.LocationownerRepository;
import webapp.database.repositories.OrganiserRepository;

import java.util.List;

/**
 * Created by thanasis on 22/6/2017.
 */
@Controller
public class OrganiserController {
    @Autowired
    OrganiserRepository organiserRepository;
    @Autowired
    LocationownerRepository locationownerRepository;

    @RequestMapping(value = "/organiser/add_event",method = RequestMethod.GET)
    public String newEventPage(Model model,
                               @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Organiser curOrganiser = organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("organiser",curOrganiser);

        Iterable<Locationowner> organiserLocations = locationownerRepository.findAll();
        model.addAttribute("organiserPlaces",organiserLocations);
        return "profile/organiser/add_event";
    }

    @RequestMapping(value = "/organiser/add_place",method = RequestMethod.GET)
    public String newLocationPage(Model model,
                                  @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Organiser curOrganiser = organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("organiser",null);
        return "profile/organiser/add_place";
    }


}
