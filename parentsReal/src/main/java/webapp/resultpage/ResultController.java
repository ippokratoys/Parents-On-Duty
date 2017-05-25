package webapp.resultpage;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResultController {

    @RequestMapping(value="/results",method= RequestMethod.GET)
    public String search(
   		@RequestParam Map<String,String> allRequestParams,
    	@RequestParam(name="Address") String address,
   		@RequestParam(name="Dates") String date,
   		@RequestParam(name="Price") int price,
   		@RequestParam(name="Age") int age,
   		@RequestParam(name="ExtraTags") String extraTags,
   		Model model)
    {
    	System.out.println("The Form elements:");
    	System.out.println(allRequestParams.toString());
    	System.out.println("Address:"+address);
    	System.out.println("Date:"+date);
    	System.out.println(price);
    	System.out.println(age);
    	System.out.println("Extra Tags:"+extraTags);
    	
    	return "results";
    }

}
