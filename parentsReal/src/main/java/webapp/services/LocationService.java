package webapp.services;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.database.Location;
import webapp.database.Organiser;
import webapp.database.repositories.LocationRepository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ilias on 1/7/2017.
 */

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public boolean createLocation(String name, String route, String number, String town, String lat, String lon, String zip, Organiser organiser){

        Location newLocation = new Location();

        String address = route + " " + number + ", " + town;
        newLocation.setAddress(address);

        newLocation.setName(name);
        newLocation.setPostcode(zip);

        BigDecimal latitude = new BigDecimal(lat);
        newLocation.setLat(latitude);

        BigDecimal longtitude = new BigDecimal(lon);
        newLocation.setLon(longtitude);

        Date date = DateUtils.addDays(new Date(), -1);
        newLocation.setValidUntil(date);


        newLocation.setCertificateChecked(false);
        newLocation.setLocationOwner(organiser);

        locationRepository.save(newLocation);

        return true;
    }
}
