package webapp.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.util.resources.ga.LocaleNames_ga;
import webapp.database.Eventsgroup;
import webapp.database.initializer.CsvInserts;
import webapp.database.repositories.*;

import java.beans.EventHandler;

/**
 * Created by thanasis on 23/6/2017.
 */
@Controller
public class DatabaseInserts {
    @Autowired
    LoginRepository loginRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrganiserRepository organiserRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    EventRepository eventHandler;

    @Autowired
    EventsgroupRepository eventsgroupHandler;


    @Autowired
    CsvInserts myCsvHandler;

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
		myCsvHandler.locationCsvInsertions(locationRepository,"../stp_back_end/locations10.csv",organiserRepository);
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
		myCsvHandler.locationCsvInsertions(locationRepository,"../stp_back_end/locations10.csv",organiserRepository);
		myCsvHandler.eventsgroupCsvInsertions(eventsgroupHandler,"../stp_back_end/events_group.csv",organiserRepository);
		myCsvHandler.eventsCsvInsertions(eventHandler,"../stp_back_end/events.csv",organiserRepository,eventsgroupHandler,locationRepository);
		return "redirect:/test/add";
	}

}
