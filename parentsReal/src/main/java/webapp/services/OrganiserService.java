package webapp.services;

import ch.qos.logback.core.boolex.EvaluationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webapp.database.Event;
import webapp.database.Eventsgroup;
import webapp.database.Location;
import webapp.database.Organiser;
import webapp.database.repositories.EventRepository;
import webapp.database.repositories.EventsgroupRepository;
import webapp.database.repositories.LocationRepository;
import webapp.database.repositories.OrganiserRepository;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by thanasis on 23/6/2017.
 */

@Service
public class OrganiserService {
    @Autowired
    OrganiserRepository organiserRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    EventsgroupRepository eventsgroupRepository;
    @Autowired
    EventRepository eventRepository;

    public List<Location> getLocations(Organiser organiser){
        return organiser.getLocations();
    }

    public Event createNewEvent(Organiser organiser,
                             String name,
                             String description,
                             Date date,
                             Time time,
                             int spots,
                             int price,
                             int loacationID,
                             MultipartFile file
//                             int ageFrom,
//                             int ageTo,
//                             String categories
    )throws Exception{
        Eventsgroup eventsgroup= new Eventsgroup();
        eventsgroup.setName(name);
        eventsgroup.setDescription(description);
        eventsgroup.setOrganiser(organiser);

        eventsgroup.setImagePath("");
//        eventsgroup.setAgeFrom(ageFrom);
//        eventsgroup.setAgeTo(ageTo);
//        eventsgroup.setType(categories);
        Eventsgroup dbEventsgroup = eventsgroupRepository.save(eventsgroup);

        Event event=new Event();
        event.setEventsgroup(dbEventsgroup);
        event.setImportance(1);
        event.setDay(date);
        event.setTime(time);
        event.setSpots(spots);
        event.setPrice(price);
        event.setOrganiser(organiser);
        event.setLocation(locationRepository.findOne(loacationID));

        Event dbEvent = eventRepository.save(event);
        return dbEvent;
    }

    public Event createEvent(Map<String,String> allParams){

        return null;
    }
}
