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
public class NewLocationController {
    @RequestMapping(value = "/organiser/add_place",method = RequestMethod.POST)
    public String newLocationFormSubmit(Model model,
                                        @RequestParam Map<String,String> allRequestParams,
                                        @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        return "profile/organiser/my_places";
    }
}
