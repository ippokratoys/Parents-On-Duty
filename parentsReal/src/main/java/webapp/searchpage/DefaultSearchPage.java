package webapp.searchpage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultSearchPage {

    @RequestMapping("/search")
    public String search(Model model){
    	model.addAttribute("name","ilias");
    	return "search";
    }

}
