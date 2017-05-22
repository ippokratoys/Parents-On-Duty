package webapp.resultpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
public class ResultController {

	@RequestMapping(value="/results",method=RequestMethod.GET)
	public String getResults(){
		return "results";
	}
}
