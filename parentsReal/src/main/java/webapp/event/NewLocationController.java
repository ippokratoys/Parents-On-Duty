package webapp.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.database.Organiser;
import webapp.database.repositories.LocationRepository;
import webapp.database.repositories.OrganiserRepository;
import webapp.services.LocationService;

import java.util.Map;

/**
 * Created by thanasis on 16/6/2017.
 */
@Controller
public class NewLocationController {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    OrganiserRepository organiserRepository;

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/organiser/add_place",method = RequestMethod.POST)
    public String newLocationFormSubmit(Model model,
                                        @RequestParam(name="Place") String name,
                                        @RequestParam(name="route") String route,
                                        @RequestParam(name="number") String number,
                                        @RequestParam(name="town") String town,
                                        @RequestParam(name="lat") String lat,
                                        @RequestParam(name="lon") String lon,
                                        @RequestParam(name="zip") String zip,
                                        @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        System.out.println("allRequestParams = [" + name + route +"]");

        Organiser organiser= organiserRepository.findOne(userDetails.getUsername());
        locationService.createLocation(name, route, number, town, lat, lon, zip, organiser);

        return "redirect:/organiser/profile?added_new_location=true";
    }
}
