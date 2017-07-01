package webapp.event;

import org.omg.CORBA.DATA_CONVERSION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webapp.database.Event;
import webapp.database.Eventsgroup;
import webapp.database.Organiser;
import webapp.database.elasticsearch.EventSearch;
import webapp.database.elasticsearch.EventSearchRepository;
import webapp.database.repositories.EventRepository;
import webapp.database.repositories.EventsgroupRepository;
import webapp.database.repositories.OrganiserRepository;
import webapp.services.OrganiserService;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * Created by thanasis on 16/6/2017.
 */
@Controller
public class NewEventController {
//    the get method is in usercontroller / organiser

    @Autowired
    private OrganiserService organiserService;
    @Autowired
    private OrganiserRepository organiserRepository;

    private int getPrice(String priceStr) {
        int price = 0;
        if (priceStr.contains(".")) {
            String[] bufferprice = priceStr.split("\\.");
            System.out.println("price0 " + bufferprice[0]);
            System.out.println("price1 " + bufferprice[1]);
            price = Integer.parseInt(priceStr.replace(".", "")) * ((int) Math.pow(10, (double) 3 - bufferprice[1].length()));
        } else if (priceStr.contains(",")) {
            String[] buffer = priceStr.split(",");
            System.out.println("price0 " + buffer);
            System.out.println("price1 " + buffer);
            price = Integer.parseInt(priceStr.replace(",", "")) * buffer[1].length();
        } else {
            price = Integer.parseInt(priceStr) * 100;
        }
        return price;
    }

    private Time getTime(String dateTime) {
        String[] splited = dateTime.split(" ");
        String[] timeSplit = splited[1].split(":");

        int minute = Integer.parseInt(timeSplit[0]);
        int hour = Integer.parseInt(timeSplit[1]);

        Time time = new Time(hour, minute, 0);
        return time;
    }

    private Date getDate(String dateTime) {
        String[] splited = dateTime.split(" ");
        Date date1=null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(splited[0]);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date1;
    }

    @RequestMapping(value = "/organiser/add_event", method = RequestMethod.POST)
    public String newEventFormSubmit(Model model,
                                     @RequestParam Map<String, String> allRequestParams,
                                     @RequestParam(name = "title") String name,
                                     @RequestParam(name = "desc") String description,
                                     @RequestParam(name = "date") String dateTime,
                                     @RequestParam(name = "spots") int spots,
                                     @RequestParam(name = "price") String priceStr,
                                     @RequestParam(name = "location_id") int location,
                                     @RequestParam(name = "file") MultipartFile file,
                                     @RequestParam(name = "age_from") int ageFrom,
                                     @RequestParam(name = "age_to") int ageTo,
                                     @RequestParam(name = "categories") String categories,
                                     @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ) throws ParseException {
        System.out.println("allRequestParams = [" + allRequestParams + "]");
        for (int i = 0; i < allRequestParams.size(); i++) {
            if (name == "" || description == "" || dateTime == "" || spots == 0  || file.isEmpty()) {
                return "reditect:profile/organiser/add_event?error=empty_field";
            }
        }
        System.out.println(allRequestParams);
//        String dateStr = allRequestParams.get("date");


        Date date1 = getDate(dateTime);
        Time time = getTime(dateTime);
        int price = getPrice(priceStr);
        Organiser organiser = organiserRepository.findOne(userDetails.getUsername());
        System.out.println(file.getSize());
        try {
            organiserService.createNewEvent(organiser, name, description, date1, time, spots, price, location, ageFrom, ageTo, categories, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/organiser/profile?EventAdded=True";
    }

    @RequestMapping(value = "/organiser/add_existing_event", method = RequestMethod.POST)
    public String newExistingEventFormSubmit(Model model,
                                             @RequestParam Map<String, String> allRequestParams,
                                             @RequestParam(name = "date") String dateTime,
                                             @RequestParam(name = "spots") int spots,
                                             @RequestParam(name = "price") String priceStr,
                                             @RequestParam(name = "location_id") int location,
                                             @RequestParam(name = "existing_event_id") int eventsGroupId,
                                             @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        int price = getPrice(priceStr);
        Date date1 = getDate(dateTime);
        Time time = getTime(dateTime);
        Organiser organiser = organiserRepository.findOne(userDetails.getUsername());
        organiserService.createExistingEvent(organiser,date1,time,spots,price,eventsGroupId,location);
        return "redirect:/organiser/profile?EventAdded=True";
    }
}