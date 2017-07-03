package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webapp.database.*;
import webapp.database.repositories.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by thanasis on 22/6/2017.
 */
@Service
public class EventService {

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
            organiser.addPoints(event.getPrice());
            eventHandler.save(event);
            customerRepository.save(customer);
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

}
