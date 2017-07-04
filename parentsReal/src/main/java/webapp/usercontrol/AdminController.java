package webapp.usercontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.database.*;
import webapp.database.repositories.*;
import webapp.services.AdminService;

import java.util.List;

/**
 * Created by thanasis on 22/6/2017.
 */
@Controller
public class AdminController {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    OrganiserRepository organiserRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    AdminService adminService;

    @RequestMapping(value="/admin/profile")
    public String profilePage(Model model){
        return "profile/admin/profile";
    }

    @RequestMapping(value="/admin/accept_location",method= RequestMethod.GET)
    public String acceptLocations(Model model){

        List<Location> locations = locationRepository.findAllByCertificateChecked(false);
        System.out.println(locations.toString());
        model.addAttribute("locations", locations);

        return "profile/admin/accept_location";
    }

    @RequestMapping(value="/admin/manage_organisers",method= RequestMethod.GET)
    public String manageOrganiser(Model model){

        Iterable<Organiser> organisers = organiserRepository.findAll();
        model.addAttribute("organisers", organisers);

        return "profile/admin/manage_organisers";
    }

    @RequestMapping(value="/admin/manage_parents",method= RequestMethod.GET)
    public String manageParent(Model model){

        Iterable<Customer> customers = customerRepository.findAll();
        model.addAttribute("parents", customers);

        return "profile/admin/manage_parents";
    }

    @RequestMapping(value = "/admin/login/reset/{login_email}",method = RequestMethod.POST)
    public String resetPassword(Model model,
                                @PathVariable("login_email")String loginEmail,
                                @RequestParam("new_pwd")String newPwd
    ){
        Login login = loginRepository.findOne(loginEmail);
        if(login==null){
            return "redirect:/admin/profile?"+"user_not_found=true";
        }
        adminService.resetLoginPassword(login,newPwd);
        return "redirect:/admin/progile?"+"user_deleted=true";
    }

    @RequestMapping(value = "/admin/accept_location/{location_id}",method = RequestMethod.POST)
    public String acceptPlace(Model model,
                              @PathVariable("location_id")int locationID,
                              @RequestParam("accept")String accept,
                              @RequestParam(value = "until",required = false)String dateUntilStr
    ){
        Location location = locationRepository.findOne(locationID);
        if(location==null){
            return "redirect:/admin/accept_location?"+"location_not_found=true";
        }
        boolean res=true;
        if(accept=="yes"){
            res=true;
        }else if(accept=="no"){
            res=false;
        }
        adminService.manageLocation(location,res);

        return "redirect:/admin/accept_location";
    }


}
