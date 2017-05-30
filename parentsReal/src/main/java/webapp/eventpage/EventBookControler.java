package webapp.eventpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import webapp.database.Event;
import webapp.database.repositories.EventRepository;

@Controller
public class EventBookControler {

	@Autowired
	private EventRepository eventHandler;

	@RequestMapping(value="/event/book",method= RequestMethod.GET)
    public String showPage(
    	@RequestParam(name="id",required=true)int eventId,
    	Model model){
			
			Event curEvent = eventHandler.findOne(eventId);
			model.addAttribute("theEvent",curEvent);
			return "event_book";
		}
}
