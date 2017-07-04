package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webapp.database.Event;
import webapp.database.Eventsgroup;
import webapp.database.Location;
import webapp.database.Organiser;
import webapp.database.elasticsearch.EventSearch;
import webapp.database.elasticsearch.EventSearchRepository;
import webapp.database.repositories.EventRepository;
import webapp.database.repositories.EventsgroupRepository;
import webapp.database.repositories.LocationRepository;
import webapp.database.repositories.OrganiserRepository;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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
    @Autowired
    EventService eventService;

    @Autowired
    private EventSearchRepository eventSearchRepository;
    private Predicate<Location> locationPredicate;

    //returns only the accepted locations
    public List<Location> getLocations(Organiser organiser){
        //if valid until is null it means that it's not valid
        organiser.getLocations().removeIf(location -> location.getValidUntil()==null);
        return organiser.getLocations();
    }

    public Event createExistingEvent(Organiser organiser,
                                Date date,
                                Time time,
                                int spots,
                                int price,
                                int eventsGroupId,
                                int loacationID
    ){
        Eventsgroup eventsgroup= eventsgroupRepository.findOne(eventsGroupId);

        Event event=new Event();
        event.setEventsgroup(eventsgroup);
        event.setImportance(1);
        event.setDay(date);
        event.setTime(time);
        event.setSpots(spots);
        event.setPrice(price);
        event.setOrganiser(organiser);
        event.setLocation(locationRepository.findOne(loacationID));

        Event dbEvent = eventRepository.save(event);
        addEventToEs(dbEvent);
        return dbEvent;
    }

    public Event createNewEvent(Organiser organiser,
                                String name,
                                String description,
                                Date date,
                                Time time,
                                int spots,
                                int price,
                                int loacationID,
                                int ageFrom,
                                int ageTo,
                                String categories,
                                MultipartFile file
    )throws Exception{
        Eventsgroup eventsgroup= new Eventsgroup();
        eventsgroup.setName(name);
        eventsgroup.setDescription(description);
        eventsgroup.setOrganiser(organiser);

        eventsgroup.setImagePath("");
        eventsgroup.setAgeFrom(ageFrom);
        eventsgroup.setAgeTo(ageTo);
        eventsgroup.setType(categories);
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
        System.out.println(file.getSize());
        String path = eventService.saveEventFile(file,dbEventsgroup);

        addEventToEs(dbEvent);
        return dbEvent;
    }

    public Event createEvent(Map<String,String> allParams){

        return null;
    }

    public boolean addEventToEs(Event event){
        EventSearch eventSearch= new EventSearch(event);
        eventSearchRepository.save(eventSearch);
        return true;
    }

}
