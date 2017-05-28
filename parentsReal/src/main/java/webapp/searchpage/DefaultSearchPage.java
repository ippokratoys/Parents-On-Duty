package webapp.searchpage;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultSearchPage {

    @RequestMapping(value="/search",method= RequestMethod.GET)
    public String search(Model model,
    	@AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
    	@RequestParam(name="Address_Search") String address,
   		@RequestParam(name="Date") String date,
   		@RequestParam(name="Prices") int price,
   		@RequestParam(name="Age") int age,
   		@RequestParam(name="Extra_Tags") String extraTags)
    {
    	System.out.println("The Form elements:");
    	System.out.println("Address:"+address);
    	System.out.println("Date:"+date);
    	System.out.println(price);
    	System.out.println(age);
    	System.out.println("Extra Tags:"+extraTags);
    	return "search";
    }

}
