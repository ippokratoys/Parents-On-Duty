package webapp.searchpage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultSearchPage {

    @RequestMapping(value="/search",method= RequestMethod.GET)
    public String search(Model model){
    	model.addAttribute("name","ilias");
    	return "search";
    }

}
