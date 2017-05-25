package webapp.eventpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import webapp.database.Event;
import webapp.database.repositories.EventRepository;
import webapp.database.repositories.EventsgroupRepository;

@Controller
public class EventPageController {
	@Autowired
	private EventRepository eventHandler;

	@Autowired
	private EventsgroupRepository eventsgroupHandler;

	@RequestMapping(value="/event",method= RequestMethod.GET)
    public String showPage(
    	@RequestParam(name="id",required=true)int eventId,
    	Model model){
			Event curEvent = eventHandler.findOne(eventId);
			model.addAttribute("theEvent",curEvent);
			return "event";
		}

}
