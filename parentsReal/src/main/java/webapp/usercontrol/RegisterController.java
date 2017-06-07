package webapp.usercontrol;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RegisterController {

	@RequestMapping(value="/register",method= RequestMethod.GET)
    public String showPage(
		@AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
//    	@RequestParam(name="id",required=true)int eventId,
			Model model
    	){
			if(userDetails!=null){
				//if the user is connected can't register
				return "redirect:/profile";
			}
			return "register";
		}

}
