package webapp.usercontrol;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import java.awt.*;
import java.util.Map;

/**
 * Created by ilias on 23/6/2017.
 */

@Controller
public class RegisterHostController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RegisterService registerService;
    @RequestMapping(value = "/host_register", method = RequestMethod.GET)
    public String showPage(
            @AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
            @RequestParam(name = "id", required = false) Map allParams,
            Model model
    ) {
        if (userDetails != null) {
            //if the user is connected can't register
            return "redirect:/profile";
        }
        return "host_register";
    }

    @Autowired
    LoginRepository loginRepository;
    @Autowired
    OrganiserRepository organiserRepository;

    @RequestMapping(value = "/host_register", method = RequestMethod.POST)
    public String showPage(
            @RequestParam Map<String, String> allParams,
            @AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
            Model model
    ){
        if (userDetails != null) {
            //if the user is connected can't register
            return "redirect:/profile";
        }
        try {
            registerService.createOrganiser(allParams);
        } catch (Exception e) {
            if(e.getMessage()=="User exists"){
                model.addAttribute("oldFields", allParams);
                model.addAttribute("error", "exists");
                return "host_register";
            }else if(e.getMessage()=="Password not strong enough"){
                model.addAttribute("oldFields", allParams);
                model.addAttribute("error", "empty");
                return "host_register";
            }else if(e.getMessage()=="Empty Field"){
                model.addAttribute("oldFields", allParams);
                model.addAttribute("error", "pwd_not_good");
                return "host_register";
            }else{
                System.out.println("ERROR HAPPENED CAN'T HANDLE");
                return "/";
            }
        }
        return "redirect:/login?success_register=True";
    }
}