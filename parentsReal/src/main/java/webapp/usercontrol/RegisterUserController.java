package webapp.usercontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.database.Customer;
import webapp.database.Login;
import webapp.database.Organiser;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.LoginRepository;
import webapp.database.repositories.OrganiserRepository;
import webapp.services.RegisterService;

import java.util.Map;


@Controller
public class RegisterUserController {

	@Autowired
	private RegisterService registerService;

	@RequestMapping(value="/register",method= RequestMethod.GET)
    public String showPage(
		@AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
    	@RequestParam(name="id",required=false)Map allParams,
			Model model
    ){
		if(userDetails!=null){
			//if the user is connected can't register
			return "redirect:/profile";
		}
		return "user_register";
	}

	@RequestMapping(value="/register",method= RequestMethod.POST)
	public String showPage(
			@RequestParam Map<String, String> allParams,
			@AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
			Model model
	){
		if(userDetails!=null){
			//if the user is connected can't register
			return "redirect:/profile";
		}
		try {
			registerService.createCustomer(allParams);
		} catch (Exception e) {
			if(e.getMessage()=="User exists"){
				model.addAttribute("oldFields", allParams);
				model.addAttribute("error", "exists");
				return "/register/user";
			}else if(e.getMessage()=="Password not strong enough"){
				model.addAttribute("oldFields", allParams);
				model.addAttribute("error", "empty");
				return "/register/user";
			}else if(e.getMessage()=="Empty Field"){
				model.addAttribute("oldFields", allParams);
				model.addAttribute("error", "pwd_not_good");
				return "/register/user";
			}else{
				System.out.println("ERROR HAPPENED CAN'T HANDLE");
				return "/";
			}
		}
		return "redirect:/login?success_register=True";
	}

}
