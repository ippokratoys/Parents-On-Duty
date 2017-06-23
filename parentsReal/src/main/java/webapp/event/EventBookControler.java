package webapp.event;

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
import webapp.services.EventService;

import java.util.Map;

@Controller
public class EventBookControler {

	@Autowired
	private EventRepository eventHandler;
	@Autowired
	private CustomerRepository customerHander;
	@Autowired
	private EventService eventService;

	@RequestMapping(value="/event/book",method= RequestMethod.GET)
    public String showPage(
		@AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
		@RequestParam(name="id",required=true)int eventId,
		Model model){
			//We know he is login because of spring security
			//if no event found throw 404
			Event curEvent = eventHandler.findOne(eventId);
			Customer curUser=customerHander.findOne(userDetails.getUsername());

			if(curEvent==null){
				return "error404";
			}
			if(curUser.getPoints()<curEvent.getPrice()){
				//not enough money to book at this event
				//maybe redirect to profile page
				return "redirect:/user/wallet?error=Not_enough_points";
			}

			int availableSits=0;
//			availableSits=curEvent.getSpots() - curEvent.getEventHasCustomers().size();
			model.addAttribute("availableSits",availableSits);
			model.addAttribute("curEvent",curEvent);
			model.addAttribute("customer",curUser);
			return "booknow";
		}

	@RequestMapping(value="/event/book/confirm",method=RequestMethod.POST)
	public String bookHandler(Model model,
							  @AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
							  @RequestParam() Map<String,String> allParams
	){
		//We know he is login because of spring security
		int eventId=Integer.parseInt( allParams.get("eventId") );
		int numberOfSpots=Integer.parseInt( allParams.get("theseis") );

		Event curEvent = eventHandler.findOne(eventId);
		Customer curUser=customerHander.findOne(userDetails.getUsername());

		//if no event found throw 404
		if(curEvent==null){
			return "error404";
		}
		try {
			eventService.bookEvent(curEvent,curUser,numberOfSpots);
		} catch (Exception e) {
			if(e.getMessage()=="Event is null"){
				return "error404";
			}else if(e.getMessage()=="Customer is null"){
				return "redirect:/login";
			}else if(e.getMessage()=="Note enough points"){
				return "event?id="+curEvent.getIdEvents()+"?error=Not_enough_points"+"=True";
			}else if(e.getMessage()=="Not enough sports available"){
				return "redirect:/event/book?id="+eventId+"&"+"Not_enough_spots"+"=True";
			}
		}

		model.addAttribute("theEvent",curEvent);
		model.addAttribute("customer",curUser);

		return "redirect:/user/profile?error=book_done";
	}

}
