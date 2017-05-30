package webapp.usercontrol;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping(value="/login",method= RequestMethod.GET)
    public String showPage(
   		@RequestParam Map<String,String> allParams,
       	@AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
//    	@RequestParam(name="id",required=true)int eventId,
    	Model model
    	){
			System.out.println(allParams);
			System.out.println(allParams.get("error"));
			
			if(userDetails!=null && allParams.get("logout")==null ){
				System.out.println(userDetails);
				return "redirect:/";
			}
			System.out.println("ook come login");
			return "login";
		}
}
