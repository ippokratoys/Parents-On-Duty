package webapp.eventpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import webapp.database.Customer;
import webapp.database.Event;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.EventRepository;

import java.util.Map;

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
				return "redirect:/user/wallet?error=Not_enough_points";
			}

			int availableSits=0;
			availableSits=curEvent.getSpots() - curEvent.getCustomers().size();
			model.addAttribute("availableSits",availableSits);
			model.addAttribute("curEvent",curEvent);
			model.addAttribute("customer",curUser);
			return "booknow";
		}

	@RequestMapping(value="/event/book/confirm",method=RequestMethod.POST)
	public String bookHandler(
			@AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
			@RequestParam() Map<String,String> allParams,
			Model model){
		System.out.println("new custome nigga");
		int eventId=Integer.parseInt( allParams.get("eventId") );
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
		curEvent.getCustomers().add(curUser);
		curUser.setPoints(curUser.getPoints()-curEvent.getPrice());
		eventHandler.save(curEvent);
		customerHander.save(curUser);
		return "redirect:/user/profile?error=book_done";
	}

}
