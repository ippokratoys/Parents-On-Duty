package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webapp.database.*;
import webapp.database.elasticsearch.EventSearch;
import webapp.database.elasticsearch.EventSearchRepository;
import webapp.database.repositories.*;

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
    BookEventRepository bookEventRepository;
    @Autowired
    AdminTableRepository adminTableRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrganiserPaymentHistoryRepository organiserPaymentHistoryRepository;

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

/*    public Event createEvent(Map<String,String> allParams){

        return null;
    }*/

    public boolean addEventToEs(Event event){
        EventSearch eventSearch= new EventSearch(event);
        eventSearchRepository.save(eventSearch);
        return true;
    }

    public boolean boostEvent(Event event, String category) throws Exception{
        int importance=1;
        int cost = 0;
        if (category.equals("bronze")){
            importance = 2;
            cost = 10;
        }else if(category.equals("silver")){
            importance = 3;
            cost = 20;
        }else if(category.equals("gold")){
            importance = 4;
            cost = 30;
        }else{
            throw new Exception("Not Valid Option");
        }


        Organiser organiser = event.getOrganiser();
        if(organiser.getPoints() < cost*100){
            throw new Exception("Not enough money");
        }

        OrganiserPaymentHistory organiserPaymentHistory = new OrganiserPaymentHistory();
        organiserPaymentHistory.setTimeStamp(new Date());
        organiserPaymentHistory.setOldBalcend(organiser.getPoints());
        organiserPaymentHistory.setOrganiser(organiser);
        organiserPaymentHistory.setMoneyPayed(cost*100);
        String message = organiser.getName() + " " + organiser.getSurname()
                +" payed "+ cost + "€ to promote the event with id " + event.getIdEvents()+".";
        organiserPaymentHistoryRepository.save(organiserPaymentHistory);

        organiser.setPoints(organiser.getPoints() - 100*cost);
        organiserRepository.save(organiser);

        EventSearch eventSearch = eventSearchRepository.findOne(event.getIdEvents());
        eventSearch.setImportance(importance);
        eventSearchRepository.save(eventSearch);

        event.setImportance(importance);
        eventRepository.save(event);

        return true;

    }

    public int cancelEvent(Event event){
        int moneyReturned = 0;

        List <BookEvent> bookEvents = bookEventRepository.findAllByEvent(event);

        for (BookEvent book: bookEvents) {
            Customer customer = book.getCustomer();
            Organiser organiser = book.getEvent().getOrganiser();
            AdminTable adminTable = adminTableRepository.findOne(1);


            int customerNewBalance = customer.getPoints()+event.getPrice();
            moneyReturned += event.getPrice();
            int organiserNewBalance = organiser.getPoints() - (int) Math.round(event.getPrice()*0.9);
            int adminNewBalance = adminTableRepository.findOne(1).getOurPointsFromEvents()- (event.getPrice() - (int) Math.round(event.getPrice()*0.9)) ;

            customer.setPoints(customerNewBalance);
            organiser.setPoints(organiserNewBalance);
            adminTable.setGivenPoints(adminNewBalance);

            customerRepository.save(customer);
            organiserRepository.save(organiser);
            adminTableRepository.save(adminTable);
            bookEventRepository.delete(book);
        }

        EventSearch eventSearch = eventSearchRepository.findOne(event.getIdEvents());

        eventSearchRepository.delete(eventSearch);
        eventRepository.delete(event);

        return moneyReturned;
    }

    public boolean organiserAddMoney(Organiser organiser, int amount){
        OrganiserPaymentHistory organiserPaymentHistory = new OrganiserPaymentHistory();

        organiserPaymentHistory.setMessage("Organiser added " + amount+"("+amount/100+"e)"+" via card to his profile (probably for promotion)");
        organiserPaymentHistory.setMoneyPayed(amount);
        organiserPaymentHistory.setOldBalcend(organiser.getPoints());
        organiserPaymentHistory.setOrganiser(organiser);
        organiserPaymentHistory.setTimeStamp(new Date());
        organiser.addPoints(amount);

        organiserRepository.save(organiser);
        organiserPaymentHistoryRepository.save(organiserPaymentHistory);
        return true;
    }

}
