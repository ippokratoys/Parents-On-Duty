package webapp.resultpage;

import java.util.Map;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import webapp.database.*;
import webapp.database.elasticsearch.EsEventsgroupRepository;
import webapp.database.initializer.CsvInserts;
import webapp.database.repositories.*;

@Controller
public class ResultController {

//	@Autowired
//	PasswordEncoder passwordEncoder;
	@Autowired
	CsvInserts myCsvHandler;

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
		myCsvHandler.loginCsvInsertions(loginRepository,customerRepository,"../stp_back_end/parents.csv");
		return "redirect:/";
	}

	@RequestMapping("/init/organiser")
	public String initOrganiser(){
		myCsvHandler.organiserCsvInsertions(loginRepository,organiserRepository,"../stp_back_end/organiser.csv");
		return "redirect:/";
	}
	@RequestMapping("/init/locations")
	public String initLocations(){
		myCsvHandler.locationCsvInsertions(locationRepository,"../stp_back_end/locations10.csv",locationownerRepository);
		return "redirect:/";
	}

	@RequestMapping("/init/eventsgroup")
	public String initEventsgroup(){
		myCsvHandler.eventsgroupCsvInsertions(eventsgroupHandler,"../stp_back_end/events_group.csv",organiserRepository);
		return "redirect:/";
	}

	@RequestMapping("/init/events")
	public String initEvents(){
		myCsvHandler.eventsCsvInsertions(eventHandler,"../stp_back_end/events.csv",organiserRepository,eventsgroupHandler,locationRepository);
		return "redirect:/";
	}

	@RequestMapping("init/all")
	public String initAll(){
		myCsvHandler.loginCsvInsertions(loginRepository,customerRepository,"../stp_back_end/parents.csv");
		myCsvHandler.organiserCsvInsertions(loginRepository,organiserRepository,"../stp_back_end/organiser.csv");
		myCsvHandler.locationCsvInsertions(locationRepository,"../stp_back_end/locations10.csv",locationownerRepository);
		myCsvHandler.eventsgroupCsvInsertions(eventsgroupHandler,"../stp_back_end/events_group.csv",organiserRepository);
		myCsvHandler.eventsCsvInsertions(eventHandler,"../stp_back_end/events.csv",organiserRepository,eventsgroupHandler,locationRepository);
		return "redirect:/";
	}

	@Autowired
	private EsEventsgroupRepository esEventsgroupRepository;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@RequestMapping("/test/add")
	public String testAddPage(){
		List<Eventsgroup> allEvents= (List<Eventsgroup>) eventsgroupHandler.findAll();
		for (Eventsgroup aEvent: allEvents) {
			//i make it null because else i get stack overflow(because of one to one with event)
			aEvent.setOrganiser(null);
			aEvent.setEvents(null);
			esEventsgroupRepository.save(aEvent);
		}
		return "index";
	}

	@RequestMapping("/test/search")
	public String testSearchPage(@RequestParam("name") String key){
		System.out.println(esEventsgroupRepository);
		List<Eventsgroup> restult= esEventsgroupRepository.findByName(key);
		for (Eventsgroup aEvent: restult) {
			System.out.print(aEvent.getName()+"\t");
			System.out.print(aEvent.getIdEventsGroup()+"\t");
			System.out.print(aEvent.getType()+"\t");
			System.out.println("--------------------");
		}
		return "index";
	}

	@RequestMapping("/test/init")
	public String testPage(){
//		Eventsgroup eventsgroup=new Eventsgroup(1,"Paidikh mou xara","asteio","eee ta exoume pei na xame na legame kai na xame na paoume ",null,null);
//		System.out.println(eventsgroup);
//		System.out.println(eventsgroup.toString());
//		esEventsgroupRepository.save(eventsgroup);
		return "index";
	}
}
