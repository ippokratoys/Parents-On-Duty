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

import java.util.Map;

/**
 * Created by ilias on 23/6/2017.
 */

@Controller
public class RegisterHostController {
    @Autowired
    private PasswordEncoder passwordEncoder;

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
    ) {
        if (userDetails != null) {
            //if the user is connected can't register
            return "redirect:/profile";
        }
        //validate that everything is ok
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            if (entry.getValue() == null || entry.getValue() == "") {
                model.addAttribute("oldFields", allParams);
                model.addAttribute("error", "empty");
                return "/host_register";
            }
            if (entry.getKey() == "password" && this.isPasswordOk(entry.getValue()) != false) {
                //if password not ok
                model.addAttribute("oldFields", allParams);
                model.addAttribute("error", "pwd_not_good");
                return "/host_register";
            }
        }

        Login newLogin = loginRepository.findOne(allParams.get("Email"));
        //if the user already exists redirect to home page
        if (newLogin != null) {
            model.addAttribute("oldFields", allParams);
            model.addAttribute("error", "exists");
            return "/register";
        }
        newLogin = new Login();
        newLogin.setEmail(allParams.get("Email"));
        newLogin.setRole("ORGANISER");
        newLogin.setActive(true);
        newLogin.setPwd(hashPassword(allParams.get("Pwd")));


        Organiser newOrganiser = new Organiser();
        newOrganiser.setLogin_email(newLogin.getEmail());
        newOrganiser.setTaxpayerId(allParams.get("ID"));
        newOrganiser.setName(allParams.get("Name"));
        newOrganiser.setSurname(allParams.get("Surname"));
        loginRepository.save(newLogin);
        organiserRepository.save(newOrganiser);

        return "redirect:/login?success_register=True";
    }

    public Boolean isPasswordOk(String password) {
        if (password.length() >= 3 && password.length() <= 10) {
            return true;
        }
        return false;
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}