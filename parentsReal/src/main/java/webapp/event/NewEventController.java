package webapp.event;

import org.springframework.beans.factory.annotation.Autowired;
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
    OrganiserService organiserService;
    @Autowired
    private OrganiserRepository organiserRepository;

    @RequestMapping(value = "/organiser/add_event",method = RequestMethod.POST)
    public String newEventFormSubmit(Model model,
                                     @RequestParam Map<String,String> allRequestParams,
                                     @RequestParam(name="title") String name,
                                     @RequestParam(name="desc") String description,
                                     @RequestParam(name="date") String dateTime,
                                     @RequestParam(name="spots") int spots,
                                     @RequestParam(name="price") String priceStr,
                                     @RequestParam(name="location_id") int location,
                                     @RequestParam(name="file",required = false) MultipartFile file,
//                                     @RequestParam(name="age_from") int ageFrom,
//                                     @RequestParam(name="age_to") int ageTo,
//                                     @RequestParam(name="categories") String categories,
                                     @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ) throws ParseException {
        System.out.println("allRequestParams = [" + allRequestParams +"]");
        for (int i = 0; i < allRequestParams.size(); i++) {
            if(name=="" || description=="" || dateTime=="" || spots==0 /* || file.isEmpty()*/){
                return "reditect:profile/organiser/add_event?error=empty_field";
            }
        }
        System.out.println(allRequestParams);
//        String dateStr = allRequestParams.get("date");

        String[] splited = dateTime.split(" ");
//        System.out.println("all "+dateTime);
//        System.out.println("0 "+splited[0]);
//        System.out.println("1 "+splited[1]);
        String[] timeSplit = splited[1].split(":");
        int minute = Integer.parseInt(timeSplit[0]);
        int hour = Integer.parseInt(timeSplit[1]);

        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(splited[0]);
        Time time= new Time(hour,minute,0);
        Organiser organiser = organiserRepository.findOne(userDetails.getUsername());
        int price = 0;
        if(priceStr.contains(".")){
            String[] bufferprice = priceStr.split("\\.");
            System.out.println("price0 "+bufferprice[0]);
            System.out.println("price1 "+bufferprice[1]);
            price = Integer.parseInt(priceStr.replace(".",""))* ((int) Math.pow(10, (double)  3-bufferprice[1].length()));
        }else if(priceStr.contains(",")){
            String[] buffer = priceStr.split(",");
            System.out.println("price0 "+buffer);
            System.out.println("price1 "+buffer);
            price = Integer.parseInt(priceStr.replace(",",""))*buffer[1].length();
        }else{
            price = Integer.parseInt(priceStr)*100;
        }
        try {
            organiserService.createNewEvent(organiser,name,description,date1,time,spots,price,location,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Event newEvent = new Event();
//        //newEvent.setOrganiser();
//        newEvent.setDay(date1);
//        newEvent.setSpots(Integer.parseInt(allRequestParams.get("spots")));
//        newEvent.setTime(Time.valueOf(splited[1]));
//        newEvent.setPrice(Integer.parseInt(allRequestParams.get("price")));
//        newEvent.setImportance(1);
////        newEvent.setLocation();
//
//        Eventsgroup newEventsgroup = new Eventsgroup();
//        newEventsgroup.setName(allRequestParams.get("title"));
//        newEventsgroup.setDescription(allRequestParams.get("desc"));
//  //      newEventsgroup.setAgeFrom("agefrom");
////        newEventsgroup.setAgeTo("ageto");
//      //  newEventsgroup.setType("type");
////        newEventsgroup.setOrganiser();
////        newEventsgroup.setEvents(newEvent);
////         newEvent.setEventsgroup(newEventsgroup);
////        newEventsgroup.setImagePath("the place where we are gonna store");
//        //check if form elements are ok
//
        return "redirect:/organiser/profile?EventAdded=True";
    }

}
