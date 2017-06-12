package webapp.resultpage;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import webapp.database.*;
import webapp.database.initializer.CsvInserts;
import webapp.database.repositories.*;

@Controller
public class ResultController {

	@Autowired
	private EventRepository eventHandler;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	LocationownerRepository locationownerRepository;
	@Autowired
	private EventsgroupRepository eventsgroupHandler;
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private OrganiserRepository organiserRepository;
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
    	/*System.out.println("The Form elements:");
    	System.out.println(allRequestParams.toString());
    	System.out.println("Address:"+address);
    	System.out.println("Date:"+date);
    	System.out.println(price);
    	System.out.println(age);
    	System.out.println("Extra Tags:"+extraTags);
    	*/

    	List<Eventsgroup> results=eventsgroupHandler.findByName(address);
    	String allWords[]=address.split(" ");
		for (String oneWord:allWords) {
			results.addAll(eventsgroupHandler.findByNameContaining(oneWord));
		}
    	//System.out.println(results.get(0).getEvents().get(0).getEventsfeedbacks());

		model.addAttribute("allEventsGroup",results);
    	return "results";
    }

    @RequestMapping("/init/login")
	public String initLogin(){
		CsvInserts.loginCsvInsertions(loginRepository,customerRepository,"../stp_back_end/parents.csv");
		return "redirect:/";
	}

	@RequestMapping("/init/organiser")
	public String initOrganiser(){
		CsvInserts.organiserCsvInsertions(loginRepository,organiserRepository,"../stp_back_end/organiser.csv");
		return "redirect:/";
	}
	@RequestMapping("/init/locations")
	public String initLocations(){
		CsvInserts.locationCsvInsertions(locationRepository,"../stp_back_end/locations10.csv",locationownerRepository);
		return "redirect:/";
	}

	@RequestMapping("/init/eventsgroup")
	public String initEventsgroup(){
		CsvInserts.eventsgroupCsvInsertions(eventsgroupHandler,"../stp_back_end/events_group.csv",organiserRepository);
		return "redirect:/";
	}

	@RequestMapping("/init/events")
	public String initEvents(){
		CsvInserts.eventsCsvInsertions(eventHandler,"../stp_back_end/events.csv",organiserRepository,eventsgroupHandler,locationRepository);
		return "redirect:/";
	}

	@RequestMapping("init/all")
	public String initAll(){
		CsvInserts.loginCsvInsertions(loginRepository,customerRepository,"../stp_back_end/parents.csv");
		CsvInserts.organiserCsvInsertions(loginRepository,organiserRepository,"../stp_back_end/organiser.csv");
		CsvInserts.locationCsvInsertions(locationRepository,"../stp_back_end/locations10.csv",locationownerRepository);
		CsvInserts.eventsgroupCsvInsertions(eventsgroupHandler,"../stp_back_end/events_group.csv",organiserRepository);
		CsvInserts.eventsCsvInsertions(eventHandler,"../stp_back_end/events.csv",organiserRepository,eventsgroupHandler,locationRepository);
		return "redirect:/";
	}
}
