package webapp.homepage;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webapp.database.*;
import webapp.database.repositories.*;

@Controller
public class HomePage {
	@Autowired
    private LoginRepository loginRepository;

    @RequestMapping("/")
    public String getHomePage(Model model){
    	model.addAttribute("Name", "Thanasis");

//    	Login newUser = new Login();
//        newUser.setEmail("sdi@gmail.com");
//        newUser.setPwd("123");
//		loginRepository.save(newUser);
//        System.out.println(newUser);		
//        System.out.println("Saved");

        return "index";
    }

}
