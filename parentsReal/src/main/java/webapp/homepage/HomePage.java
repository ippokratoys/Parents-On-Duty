package webapp.homepage;


import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePage {

    @RequestMapping("/")
    public String getHomePage(Model model){
    	model.addAttribute("Name", "Thanasis");
        return "index";
    }

}
