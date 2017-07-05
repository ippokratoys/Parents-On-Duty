package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webapp.database.*;
import webapp.database.elasticsearch.EventSearch;
import webapp.database.repositories.*;

import java.util.Date;
import java.util.List;

/**
 * Created by thanasis on 22/6/2017.
 */
@Service
public class EventService {

    @Autowired
    EventFeedbackRepositorie eventFeedbackRepositorie;
    @Autowired
    EventRepository eventHandler;
    @Autowired
    EventsgroupRepository eventsgroupRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BookEventRepository bookEventRepository;
    @Autowired
    OrganiserRepository organiserRepository;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private AdminTableRepository adminTableRepository;

    @Value("file_save.folder.prefix")
    String MAIN_PREFIX="";

    @Value("file_save.folder.event.pictures.prefix")
    String PHOTO_PREFIX="";


    public int getAvailableSpots(Event event){
        return event.getSpots()-event.getBookEvents().size();
    }

    public boolean bookEvent(Event event,Customer customer,int numberOfSpots) throws Exception{
        if(event==null){
            throw new Exception("Event is null");
        }
        if(customer==null){
            throw new Exception("Customer is null");
        }

////////////////somehow lock here//////////////////////
        if(customer.getPoints()<(event.getPrice()*numberOfSpots)){
            throw new Exception("Not enough points");
        }
        if(getAvailableSpots(event)<numberOfSpots){
            throw new Exception("Not enough sports available");
        }

        for (int i=0 ; i < numberOfSpots; i++) {

            BookEvent bookEventNew = new BookEvent();
            bookEventNew.setBookDate(new Date());
            bookEventNew.setCustomer(customer);
            bookEventNew.setEvent(event);
            bookEventRepository.save(bookEventNew);

            customer.setPoints(customer.getPoints() - event.getPrice());
            Organiser organiser = event.getOrganiser();
            organiser.addPoints((int) Math.round(event.getPrice()*0.9) );
            AdminTable adminTable = adminTableRepository.findOne(1);
            adminTable.setOurPointsFromEvents(event.getPrice() - (int) Math.round(event.getPrice()*0.9));
            eventHandler.save(event);
            customerRepository.save(customer);
            adminTableRepository.save(adminTable);
            organiserRepository.save(organiser);
        }
//////////////////somehow unlock here//////////////////////////////////
        return true;
    }

    public String saveEventFile(MultipartFile file, Eventsgroup eventsgroup){

        if (file.isEmpty()) {
            return "null";
        }
        String fileName=null;
        fileName = String.valueOf(eventsgroup.getIdEventsGroup());
        String[] buff=file.getName().split("\\.");
        String fileNamePostFix=buff[buff.length-1];
        fileName+="."+fileNamePostFix;

        fileUploadService.store(file,fileName, FileUploadService.FileType.EVENT);

        eventsgroup.setImagePath("file/event/"+fileName);
        eventsgroupRepository.save(eventsgroup);
        return "redirect:/uploadStatus";
    }

    public int getRating(Event event){
        if(event==null){
            return -1;
        }
        List<EventFeedback> allFeedbacks = eventFeedbackRepositorie.findOrganiserFeedbacks(event.getOrganiser());
        System.out.println(allFeedbacks);
        int avg=0;
        if(allFeedbacks.size()==0 ){
            return 0;
        }
        for (EventFeedback aFeedback :
                allFeedbacks) {
            avg += aFeedback.getRating();
        }
        avg  = (int) Math.round(((double) avg) / allFeedbacks.size());
        return avg;
    }

    public int getRating(EventSearch event){
//        eventHandler.findOne(event.getId());
        System.out.println("event id = " + event.getId());
        return getRating( eventHandler.findOne(event.getId()) );
    }


    public List<EventFeedback> allFeedbacks(Event event){

        List<EventFeedback> allFeedbacks = eventFeedbackRepositorie.findOrganiserFeedbacks(event.getOrganiser());
        System.out.println(allFeedbacks);

        //if it's about the same event(but old) it get's higher
        for (EventFeedback aFeeback :
                allFeedbacks) {
            if (aFeeback.getEvent().getEventsgroup() == event.getEventsgroup()) {
//                allFeedbacks.remove(aFeeback);
//                allFeedbacks.add(0,aFeeback);
            }
         }
         return allFeedbacks;
    }

}
