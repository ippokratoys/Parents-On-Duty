package webapp.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.database.Event;
import webapp.database.Eventsgroup;
import webapp.database.repositories.EventRepository;
import webapp.database.repositories.EventsgroupRepository;

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
    EventRepository eventRepository;

    @Autowired
    EventsgroupRepository eventsgroupRepository;

    @RequestMapping(value = "/organiser/add_event",method = RequestMethod.POST)
    public String newEventFormSubmit(Model model,
                                     @RequestParam Map<String,String> allRequestParams,
                                     @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ) throws ParseException {
        System.out.println("allRequestParams = [" + allRequestParams +"]");
        for (int i = 0; i < allRequestParams.size(); i++) {
            if(allRequestParams.get(i)=="" || allRequestParams.get(i).trim()==""){
                return "reditect:profile/organiser/add_event?error=empty_field";
            }
        }
        String dateStr = allRequestParams.get("date");
        String[] splited = dateStr.split("\\s+");
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(splited[0]);
        Event newEvent = new Event();
        //newEvent.setOrganiser();
        newEvent.setDay(date1);
        newEvent.setSpots(Integer.parseInt(allRequestParams.get("spots")));
        newEvent.setTime(Time.valueOf(splited[1]));
        newEvent.setPrice(Integer.parseInt(allRequestParams.get("price")));
        newEvent.setImportance(1);
//        newEvent.setLocation();

        Eventsgroup newEventsgroup = new Eventsgroup();
        newEventsgroup.setName(allRequestParams.get("title"));
        newEventsgroup.setDescription(allRequestParams.get("desc"));
  //      newEventsgroup.setAgeFrom("agefrom");
//        newEventsgroup.setAgeTo("ageto");
      //  newEventsgroup.setType("type");
//        newEventsgroup.setOrganiser();
//        newEventsgroup.setEvents(newEvent);
//         newEvent.setEventsgroup(newEventsgroup);
//        newEventsgroup.setImagePath("the place where we are gonna store");
        //check if form elements are ok

        return "redirect:profile/organiser/profile?EventAdded=True";
    }

}
