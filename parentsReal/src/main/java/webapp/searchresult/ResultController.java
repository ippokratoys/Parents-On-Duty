package webapp.searchresult;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.thymeleaf.expression.Dates;
import webapp.database.*;
//import webapp.database.initializer.CsvInserts;
import webapp.database.elasticsearch.EventSearch;
import webapp.database.repositories.*;
import webapp.services.ResultService;

@Controller
public class ResultController {

	SimpleDateFormat dateParser = new SimpleDateFormat("dd/ΜΜ/yyyy");
	{
		SimpleDateFormat dateParser = new SimpleDateFormat("dd/ΜΜ/yyyy");
	}

	@Autowired
	ResultService resultService;

	@Autowired
	private EventsgroupRepository eventsgroupHandler;

	@Autowired
	private CustomerRepository customerRepository;

	@RequestMapping(value="/results",method= RequestMethod.GET)
    public String search(
			@AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
			@RequestParam Map<String,String> allRequestParams,
			@RequestParam(name="free_text") String freeText,
			@RequestParam(name="Dates") String date,
			@RequestParam(name="Price") int price,
			@RequestParam(name="Age") String age,
			@RequestParam(name="distance") String distance,
			@RequestParam(name="lat") String lat,
			@RequestParam(name="lon") String lon,
			Model model
	){
		int distanceArg=-1;
		if (distance == null || distance == ""){
			distanceArg= 100;
		}else{
			distanceArg=Integer.parseInt(distance);
		}

		int ageArg=-1;
		if (age == null || age == ""){
			ageArg=-1;
		}else{
			ageArg=Integer.parseInt(age);
		}

		System.out.println(allRequestParams.toString());
    	System.out.println("Free text: "+freeText);
    	System.out.println("Date: "+date);
    	System.out.println("Price: "+ price);
    	System.out.println("Age: "+ageArg);
    	System.out.println("Distance: "+distanceArg);


    	List<EventSearch> results=null;
		String[] dates = date.split("-");
		if(date ==null || date=="" || date==" " ||dates.length==1){
			dates=new String[2];
			dates[0]=null;dates[1]=null;
		}

		if ((lat != "") && (lon != "")) {
			double latd = Double.parseDouble(lat);
			double lond = Double.parseDouble(lon);
			results = resultService.getResultsByUser(freeText, dates[0], dates[1], price, ageArg, distanceArg, latd, lond);
		}
		else {
			if (userDetails != null && userDetails.getAuthorities().toString().contains("PARENT")) {
				Customer myUser = customerRepository.findOne(userDetails.getUsername());
				if(myUser.getLon()!=null && myUser.getLon()!=null){
					results = resultService.getResultsByUser(freeText, dates[0], dates[1], price, ageArg, distanceArg, myUser.getLat().doubleValue(), myUser.getLon().doubleValue());
				}else {
					results = resultService.getResults(freeText, dates[0], dates[1], price, ageArg, distanceArg);
				}
			} else {
				results = resultService.getResults(freeText, dates[0], dates[1], price, ageArg, distanceArg);
			}
			//System.out.println(results.get(0).getEventHasCustomers().get(0).getEventsfeedbacks());
		}

		model.addAttribute("allParams",allRequestParams);
		model.addAttribute("searchResults",results);
    	return "results";
    }

}
