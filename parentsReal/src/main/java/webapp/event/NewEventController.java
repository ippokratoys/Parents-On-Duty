package webapp.event;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
public class NewEventController {
//    the get method is in usercontroller / organiser

    @RequestMapping(value = "/organiser/add_event",method = RequestMethod.POST)
    public String newEventFormSubmit(Model model,
                                     @RequestParam Map<String,String> allRequestParams,
                                     @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        System.out.println("allRequestParams = [" + allRequestParams +"]");
        for (int i = 0; i < allRequestParams.size(); i++) {
            if(allRequestParams.get(i)=="" || allRequestParams.get(i).trim()==""){
                return "reditect:profile/organiser/add_event?error=empty_field";
            }
        }

        //check if form elements are ok

        return "redirect:profile/organiser/profile?EventAdded=True";
    }

}
