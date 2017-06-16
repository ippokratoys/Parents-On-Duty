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
public class NewLocation{
    @RequestMapping(value = "/organiser/new_location",method = RequestMethod.GET)
    public String newLocationPage(Model model,
                              @RequestParam Map<String,String> allRequestParams
    ){
        return "profile/organiser/add_place";
    }
    @RequestMapping(value = "/organiser/new_location",method = RequestMethod.POST)
    public String addNewLocationPage(Model model,
                              @RequestParam Map<String,String> allRequestParams
    ){
        return "profile/organiser/my_places";
    }
}
