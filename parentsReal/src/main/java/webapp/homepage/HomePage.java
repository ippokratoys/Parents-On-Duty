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
	@Autowired
    private LoginRepository loginRepository;

    @RequestMapping("/")
    public String getHomePage(
    		Model model,
        	@AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    	){
    	model.addAttribute("Name", "Thanasis");
    	if(userDetails==null){
        	System.out.println("not Loged in");

    	}else{
        	System.out.println(userDetails.getUsername());
        	System.out.println(userDetails.getAuthorities().toString());    		
    	}

        return "index";
    }

}
