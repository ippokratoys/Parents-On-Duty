package webapp.searchresult;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import webapp.database.*;
//import webapp.database.initializer.CsvInserts;
import webapp.database.repositories.*;
import webapp.services.ResultService;

@Controller
public class ResultController {

	@Autowired
	ResultService resultService;

	@Autowired
	private EventsgroupRepository eventsgroupHandler;

	@RequestMapping(value="/results",method= RequestMethod.GET)
    public String search(
   		@RequestParam Map<String,String> allRequestParams,
    	@RequestParam(name="free_text") String freeText,
   		@RequestParam(name="Dates") String date,
   		@RequestParam(name="Price") int price,
   		@RequestParam(name="Age") int age,
   		@RequestParam(name="ExtraTags") String extraTags,
   		Model model
	){
    	/*System.out.println("The Form elements:");
    	System.out.println(allRequestParams.toString());
    	System.out.println("Address:"+address);
    	System.out.println("Date:"+date);
    	System.out.println(price);
    	System.out.println(age);
    	System.out.println("Extra Tags:"+extraTags);
    	*/

    	List<Eventsgroup> results=eventsgroupHandler.findByName(freeText);
		results=resultService.getResults(freeText);
    	//System.out.println(results.get(0).getEventHasCustomers().get(0).getEventsfeedbacks());

		model.addAttribute("allParams",allRequestParams);
		model.addAttribute("allEventsGroup",results);
    	return "results";
    }

}
