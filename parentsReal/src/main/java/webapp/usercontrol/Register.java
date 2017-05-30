package webapp.usercontrol;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class Register {

	@RequestMapping(value="/register",method= RequestMethod.GET)
    public String showPage(
//    	@RequestParam(name="id",required=true)int eventId,
    	Model model
    	){
			return "register";
		}

}
