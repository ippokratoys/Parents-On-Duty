package webapp.eventpage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by thanasis on 16/6/2017.
 */
@Controller
public class NewEvent {
    @RequestMapping(value = "/organiser/new_event",method = RequestMethod.GET)
    public String newEventPage(Model model,
                              @RequestParam Map<String,String> allRequestParams
    ){
        return "profile/organiser/add_event";
    }

    @RequestMapping(value = "/organiser/new_event",method = RequestMethod.POST)
    public String addNewEventPage(Model model,
                              @RequestParam Map<String,String> allRequestParams
    ){
        return "profile/organiser/my_events";
    }
}
