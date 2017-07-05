package webapp.usercontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.database.Event;
import webapp.database.EventFeedback;
import webapp.database.Location;
import webapp.database.Organiser;
import webapp.database.repositories.EventRepository;
import webapp.database.repositories.OrganiserRepository;
import webapp.services.EventService;
import webapp.services.OrganiserService;

import java.util.Date;

/**
 * Created by thanasis on 22/6/2017.
 */
@Controller
public class OrganiserController{
    @Autowired
    OrganiserRepository organiserRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventService eventService;

    @Autowired
    OrganiserService organiserService;
//    @Autowired
//    LocationownerRepository locationownerRepository;

    @RequestMapping(value = "/organiser/add_event",method = RequestMethod.GET)
    public String newEventPage(Model model,
                               @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Organiser curOrganiser = organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("organiser",curOrganiser);

        Iterable<Location> organiserLocations = organiserService.getLocations(curOrganiser);
        model.addAttribute("organiserPlaces",organiserLocations);
        return "profile/organiser/add_event";
    }

    @RequestMapping(value = "/organiser/add_place",method = RequestMethod.GET)
    public String newLocationPage(Model model,
                                  @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Organiser curOrganiser = organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("organiser",null);
        return "profile/organiser/add_place";
    }

    @RequestMapping(value = "/organiser/profile",method = RequestMethod.GET)
    public String profilePage(Model model,
                              @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Organiser organiser= organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("organiser",organiser);
        Date date = new Date();
        model.addAttribute("date", date);
//        model.addAttribute("availableSpots",eventService.getAvailableSpots());
        return "profile/organiser/profile";
    }

    @RequestMapping(value = "/organiser/wallet",method = RequestMethod.GET)
    public String walletPage(Model model,
                              @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Organiser organiser= organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("curUser",organiser);
//        model.addAttribute("availableSpots",eventService.getAvailableSpots());
        return "profile/organiser/wallet";
    }

    @RequestMapping(value = "/organiser/profile/cancel_event/{id}", method = RequestMethod.GET)
    public String cancelPage(Model model,
                             @PathVariable ("id") int id,
                             @AuthenticationPrincipal final UserDetails userDetails){

        Event event = eventRepository.findOne(id);
        int moneyreturned = organiserService.cancelEvent(event);
        Organiser organiser= organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("curUser",organiser);
        return "profile/organiser/profile?event_canceled=True";
    }

    @RequestMapping(value = "/organiser/add_money", method = RequestMethod.POST)
    public String addMoney(Model model,
                           @RequestParam("amount") int amount,
                           @AuthenticationPrincipal final UserDetails userDetails
    ){
        Organiser organiser = organiserRepository.findOne(userDetails.getUsername());
        if(amount>0){
            organiserService.organiserAddMoney(organiser,amount*100);
        }
        return "redirect:/organiser/profile";
    }


    @RequestMapping(value = "/organiser/history", method = RequestMethod.GET)
    public String historyOfEvents(Model model,
                                        @AuthenticationPrincipal final  UserDetails userDetails
    ){

        Organiser organiser = organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("curUser", organiser);
        model.addAttribute("localDate", new Date());
        return "profile/organiser/history";
    }

    @RequestMapping(value = "/organiser/historytrans", method = RequestMethod.GET)
    public String historyOfTransactions(Model model,
                                        @AuthenticationPrincipal final  UserDetails userDetails
    ){

        Organiser organiser = organiserRepository.findOne(userDetails.getUsername());
        model.addAttribute("curUser", organiser);
        return "profile/organiser/historytrans";
    }

    @RequestMapping(value = "/organiser/promote_event", method = RequestMethod.POST)
    public String promoteEvent(@AuthenticationPrincipal final UserDetails userDetails,
                               @RequestParam("boost_cat")String promotionClass,
                               @RequestParam("eventID")int eventID
    ){
        Event event = eventRepository.findOne(eventID);
        Date curDate = new Date();
        if(curDate.after(event.getDay())){
            return "redirect:/organiser/profile?old_event=true";
        }
        try {
            organiserService.boostEvent(event,promotionClass);
        } catch (Exception e) {
            if(e.getMessage()=="Not Valid Option"){
                return "redirect:/organiser/profile?unknown_option=true";
            }else if(e.getMessage()=="Not enough money"){
                return "redirect:/organiser/wallet?"+"not_enough_money=true";
            }else{
                return "redirect:/organiser/profile?unknown_error=true";
            }
        }
        return "redirect:/organiser/profile?event_boosted=true";
    }


}
