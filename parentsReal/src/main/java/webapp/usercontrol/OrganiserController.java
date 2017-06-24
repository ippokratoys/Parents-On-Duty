package webapp.usercontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webapp.database.Location;
import webapp.database.Organiser;
import webapp.database.repositories.OrganiserRepository;
import webapp.services.EventService;
import webapp.services.OrganiserService;

/**
 * Created by thanasis on 22/6/2017.
 */
@Controller
public class OrganiserController{
    @Autowired
    OrganiserRepository organiserRepository;
    @Autowired
    EventService eventService;

    @Autowired
    OrganiserService organiserService;
//    @Autowired
//    LocationownerRepository locationownerRepository;

    @RequestMapping(value = "/organiser/add_event",method = RequestMethod.GET)
    public String newEventPage(Model model,
                               @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Organiser curOrganiser = organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("organiser",curOrganiser);

        Iterable<Location> organiserLocations = organiserService.getLocations(curOrganiser);
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

    @RequestMapping(value = "/organiser/profile",method = RequestMethod.GET)
    public String profilePage(Model model,
                              @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Organiser organiser= organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("organiser",organiser);
//        model.addAttribute("availableSpots",eventService.getAvailableSpots());
        return "profile/organiser/profile";
    }

}
