package webapp.eventpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import webapp.database.Customer;
import webapp.database.Event;
import webapp.database.Login;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.EventRepository;
import webapp.database.repositories.LoginRepository;
import webapp.database.repositories.UserRepository;

@Controller
public class EventBookControler {

	@Autowired
	private EventRepository eventHandler;
	@Autowired
	private CustomerRepository customerHander;

	@RequestMapping(value="/event/book",method= RequestMethod.GET)

    public String showPage(
		@AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
		@RequestParam(name="id",required=true)int eventId,
		Model model){
			System.out.println("new custome nigga");
			Event curEvent = eventHandler.findOne(eventId);
			//We know he is login because of spring security
			//if no event found throw 404
			if(curEvent==null){
				return "error404";
			}

			Customer curUser=customerHander.findOne(userDetails.getUsername());
			if(curUser.getPoints()<curEvent.getPrice()){
				//not enough money to book at this event
				//maybe redirect to profile page
				return "event?id="+curEvent.getIdEvents()+"?error=Not_enough_points";
			}


			model.addAttribute("theEvent",curEvent);
			model.addAttribute("customer",curUser);
			return "event_book";
		}
}
