package webapp.services;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    @Autowired
    private FileUploadService fileUploadService;

    public boolean createLocation(String name, String route, String number, String town, String lat, String lon, String zip, Organiser organiser,MultipartFile locationFile,MultipartFile certificateFile){

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

        Location dbLocation = locationRepository.save( newLocation );


        String fileName="";
        fileName = String.valueOf(dbLocation.getId());
        String[] buff=locationFile.getName().split("\\.");
        String fileNamePostFix=buff[buff.length-1];
        fileName+="."+fileNamePostFix;

        fileUploadService.store(locationFile,fileName, FileUploadService.FileType.LOCATION);
        dbLocation.setImagePath(fileName);


//        fileName = String.valueOf(dbLocation.getId());
//        String[] buff=locationFile.getName().split("\\.");
//        String fileNamePostFix=buff[buff.length-1];
//        fileName+="."+fileNamePostFix;
//
//        fileUploadService.store(locationFile,fileName, FileUploadService.FileType.LOCATION);
//        dbLocation.setCertificatePath(fileName);



        locationRepository.save( dbLocation );

        return true;
    }
}
