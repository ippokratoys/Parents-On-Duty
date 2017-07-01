package webapp.usercontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webapp.database.Location;
import webapp.database.repositories.EventRepository;
import webapp.database.repositories.LocationRepository;

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

    @RequestMapping(value="/admin/accept_event",method= RequestMethod.GET)
    public String AcceptLocations(Model model){

        List<Location> locations = locationRepository.findAllByCertificateChecked(false);
        System.out.println(locations.toString());
        model.addAttribute("locations", locations);

        return "profile/admin/accept_location   ";
    }

}
