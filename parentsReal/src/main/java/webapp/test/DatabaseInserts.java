package webapp.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.util.resources.ga.LocaleNames_ga;
import webapp.database.AdminTable;
import webapp.database.Eventsgroup;
import webapp.database.Login;
import webapp.database.initializer.CsvInserts;
import webapp.database.repositories.*;

import java.beans.EventHandler;

/**
 * Created by thanasis on 23/6/2017.
 */
@Controller
public class DatabaseInserts {
    @Autowired
	AdminTableRepositorie adminTableRepositorie;
    @Autowired
    LoginRepository loginRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
		myCsvHandler.loginCsvInsertions(loginRepository,customerRepository,"csv/parents.csv");
		return "redirect:/";
	}

	@RequestMapping("/init/organiser")
	public String initOrganiser(){
		myCsvHandler.organiserCsvInsertions(loginRepository,organiserRepository,"csv/organiser.csv");
		return "redirect:/";
	}
	@RequestMapping("/init/locations")
	public String initLocations(){
		myCsvHandler.locationCsvInsertions(locationRepository,"csv/locations10.csv",organiserRepository);
		return "redirect:/";
	}

	@RequestMapping("/init/eventsgroup")
	public String initEventsgroup(){
		myCsvHandler.eventsgroupCsvInsertions(eventsgroupHandler,"csv/events_group.csv",organiserRepository);
		return "redirect:/";
	}

	@RequestMapping("/init/events")
	public String initEvents(){
		myCsvHandler.eventsCsvInsertions(eventHandler,"csv/events.csv",organiserRepository,eventsgroupHandler,locationRepository);
		return "redirect:/";
	}

	@RequestMapping("/init/admin/table")
	public String initAdminTable(){
		AdminTable adminTable = new AdminTable();
		adminTable.setId(1);
		adminTable.setGivenPoints(0);
		adminTable.setOurPointsFromEvents(0);
		adminTable.setOurPointsFromPromotion(0);
		adminTableRepositorie.save(adminTable);
		return "redirect:/";
	}

	@RequestMapping("/init/admin/user")
	public String initAdminUser(){
		Login login = new Login();
		login.setUsername("admin");
		login.setEmail("admin@pod.gr");
		login.setPwd(passwordEncoder.encode("admin"));
		login.setRole("ADMIN");
		login.setActive(true);
		loginRepository.save(login);
		return "redirect:/";
	}

	@RequestMapping("init/all")
	public String initAll(){

		myCsvHandler.loginCsvInsertions(loginRepository,customerRepository,"csv/parents.csv");
		myCsvHandler.organiserCsvInsertions(loginRepository,organiserRepository,"csv/organiser.csv");
		myCsvHandler.locationCsvInsertions(locationRepository,"csv/locations10.csv",organiserRepository);
		myCsvHandler.eventsgroupCsvInsertions(eventsgroupHandler,"csv/events_group.csv",organiserRepository);
		myCsvHandler.eventsCsvInsertions(eventHandler,"csv/events.csv",organiserRepository,eventsgroupHandler,locationRepository);

		AdminTable adminTable = new AdminTable();
		adminTable.setId(1);
		adminTable.setGivenPoints(0);
		adminTable.setOurPointsFromEvents(0);
		adminTable.setOurPointsFromPromotion(0);
		adminTableRepositorie.save(adminTable);

		Login login = new Login();
		login.setUsername("admin");
		login.setEmail("admin@pod.gr");
		login.setPwd(passwordEncoder.encode("admin"));
		login.setRole("ADMIN");
		login.setActive(true);
		loginRepository.save(login);

		return "redirect:/test/add";
	}

}
