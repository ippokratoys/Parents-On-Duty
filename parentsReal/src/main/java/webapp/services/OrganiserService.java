package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.database.Location;
import webapp.database.Organiser;
import webapp.database.repositories.LocationRepository;
import webapp.database.repositories.OrganiserRepository;

import java.util.List;

/**
 * Created by thanasis on 23/6/2017.
 */

@Service
public class OrganiserService {
    @Autowired
    OrganiserRepository organiserRepository;
    @Autowired
    LocationRepository locationRepository;

    public List<Location> getLocations(Organiser organiser){
        return organiser.getLocations();
    }

}