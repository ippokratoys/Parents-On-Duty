package webapp.usercontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.database.Login;
import webapp.database.repositories.LoginRepository;
import webapp.database.repositories.UserRepository;

import java.util.Map;


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
	@Autowired
	LoginRepository loginRepository;
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
		//validate that everything is ok
		for (Map.Entry<String, String> entry : allParams.entrySet())
		{
			System.out.println(entry.getKey() + "/" + entry.getValue());
			if(entry.getValue()==null || entry.getValue()==""){
				model.addAttribute("oldFields",allParams);
				return "redirect:/register?error=empty";
			}
			if(entry.getKey()=="password" && this.isPasswordOk(entry.getValue())!=false ){
				//if password not ok
				model.addAttribute("oldFields",allParams);
			}
		}

		Login new_login=loginRepository.findOne(allParams.get("Email"));
		//if the user already exists redirect to home page
		if(new_login!=null){
			model.addAttribute("oldFields",allParams);
			return "redirect:/register?error=exists";
		}
		new_login=new Login();
		new_login.setEmail(allParams.get("Email"));
		new_login.setRole("PARENT");
		new_login.setPwd(hashPassword(allParams.get("Pwd")));
		loginRepository.save(new_login);
		return "user/profile?new=True";
	}

	Boolean isPasswordOk(String password){
		if(password.length()>=3 && password.length()<=10){
			return true;
		}
		return false;
	}

	String hashPassword(String password){
		return password;
	}
}
