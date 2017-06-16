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

import java.util.Map;


@Controller
public class RegisterController {

	@Autowired
	private PasswordEncoder passwordEncoder;

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
			return "register";
	}

	@Autowired
	LoginRepository loginRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	OrganiserRepository organiserRepository;

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
				model.addAttribute("error","empty");
				return "/register";
			}
			if(entry.getKey()=="password" && this.isPasswordOk(entry.getValue())!=false ){
				//if password not ok
				model.addAttribute("oldFields",allParams);
				model.addAttribute("error","pwd_not_good");
				return "/register";
			}
		}

		Login newLogin=loginRepository.findOne(allParams.get("Email"));
		//if the user already exists redirect to home page
		if(newLogin!=null){
			model.addAttribute("oldFields",allParams);
			model.addAttribute("error","exists");
			return "/register";
		}
		newLogin=new Login();
		newLogin.setEmail(allParams.get("Email"));
		newLogin.setRole("PARENT");
		newLogin.setActive(false);
		newLogin.setPwd(hashPassword(allParams.get("Pwd")));
		if(true){
			//if it's a parrent
			Customer newCustomer = new Customer();
			newCustomer.setLogin_email(newLogin.getEmail());
			newCustomer.setPoints(1);
			newCustomer.setWallet(2);
			loginRepository.save(newLogin);
			customerRepository.save(newCustomer);
		}else if(true){
			Organiser organiser= new Organiser();
			//if it's a organiser


//			customerRepository.save(newCustomer);
//			loginRepository.save(newLogin);
		}
		return "redirect:/login?success_register=True";
	}

	public Boolean isPasswordOk(String password){
		if(password.length()>=3 && password.length()<=10){
			return true;
		}
		return false;
	}

	public String hashPassword(String password){
		return passwordEncoder.encode(password);
	}
}
