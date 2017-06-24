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
    public String showPage(@RequestParam Map<String,String> allParams,
						   @AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
						   Model model
//						   @RequestParam(name="id",required=true)int eventId,
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

	@RequestMapping(value="/profile",method= RequestMethod.GET)
	public String getProfilePage(Model model,
								 @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
	){
		if(userDetails.getAuthorities().toString().contains("PARENT")){
//			return "profile/parent/profile";
			return "redirect:parent/profile";
		}else if(userDetails.getAuthorities().toString().contains("ADMIN")){
//			return "profile/admin/profile";
			return "redirect:admin/profile";
		}else{
			return "redirect:organiser/profile";
		}
	}



}
