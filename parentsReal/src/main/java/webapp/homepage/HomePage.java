package webapp.homepage;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webapp.database.*;
import webapp.database.repositories.*;

@Controller
public class HomePage {

    @RequestMapping("/")
    public String getHomePage(
    		Model model
   	){
        return "index";
    }

}
