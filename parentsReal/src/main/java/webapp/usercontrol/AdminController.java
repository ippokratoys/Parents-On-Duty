package webapp.usercontrol;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.database.*;
import webapp.database.repositories.*;
import webapp.services.AdminService;
import webapp.services.RegisterService;

import javax.jws.WebParam;
import javax.print.DocFlavor;
import java.util.List;

/**
 * Created by thanasis on 22/6/2017.
 */
@Controller
public class AdminController {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    AdminService adminService;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrganiserRepository organiserRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    RegisterService registerService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    OrganiserPaymentHistoryRepository organiserPaymentHistoryRepository;
    @Autowired
    CustomerPaymentHistoryRepository customerPaymentHistoryRepository;

    @RequestMapping(value="/admin/profile")
    public String profilePage(Model model){
        if(customerRepository.customerMoneyIn()!=null){
            model.addAttribute("customersMoneyIn",customerRepository.customerMoneyIn()/100d);
        }else{
            model.addAttribute("customersMoneyIn",0.0);
        }

        if(customerPaymentHistoryRepository.customersMoneyPayed()!=null){
            model.addAttribute("customerMoney",customerRepository.customerMoneyIn());
        }else{
            model.addAttribute("customerMoney",0.0);
        }

        if(customerPaymentHistoryRepository.customerFromBonus()!=null){
            model.addAttribute("customerBonusMoney",customerPaymentHistoryRepository.customerFromBonus()/100d);
        }else{
            model.addAttribute("customerBonusMoney",0.0);
        }

        if(organiserPaymentHistoryRepository.organisersMoneyGiven()!=null){
            model.addAttribute("moneyToOrganisers",organiserPaymentHistoryRepository.organisersMoneyGiven()/100d);
        }else{
            model.addAttribute("moneyToOrganisers",0.0);
        }

        if(organiserRepository.organisersMoney()!=null){
            model.addAttribute("organisersMoneyIn",organiserRepository.organisersMoney()/100d);
        }else{
            model.addAttribute("organisersMoneyIn",0.0);
        }

        if(organiserPaymentHistoryRepository.organisersMoneyIn()!=null){
            model.addAttribute("organiserMoneyFromAdverts",organiserPaymentHistoryRepository.organisersMoneyIn()/100d);
        }else{
            model.addAttribute("organiserMoneyFromAdverts",0.0);
        }

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

    @RequestMapping(value = "/admin/login/reset",method = RequestMethod.POST)
    public String resetPassword(Model model,
                                @RequestParam("login_email")String loginEmail,
                                @RequestParam("new_pwd")String newPwd
    ){
        Login login = loginRepository.findOne(loginEmail);
        if(login==null){
            return "redirect:/admin/manage_organisers?"+"user_not_found=true";
        }
        adminService.resetLoginPassword(login,newPwd);
        if(customerRepository.findOne(loginEmail)!=null){
            return "redirect:/admin/manage_parents?"+"customer_deleted=true";
        }else if(organiserRepository.findOne(loginEmail)!=null){
            return "redirect:/admin/manage_organisers?"+"organiser_deleted=true";
        }else{
            return "redirect:/admin/profile?"+"someone_deleted=true";
        }
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


    @RequestMapping(value = "/admin/login/block",method = RequestMethod.POST)
    public String blockLogin(Model model,
                             @RequestParam("login_email") String loginEmail,
                             @RequestParam("action") String action
    ){
        if(action.equals("block")){
            System.out.println("will block "+ loginEmail);
            if(adminService.blockLogin(loginEmail)==false){
                return "redirect:/admin/profile?login_not_found=true";
            }
        }else if(action.equals("unblock")){
            System.out.println("will un block "+ loginEmail);
            if(adminService.unblockLogin(loginEmail)==false){
                return "redirect:/admin/profile?login_not_found=true";
            }
        }

        if(customerRepository.findOne(loginEmail)!=null){
            return "redirect:/admin/manage_parents?"+"customer_blocked=true";
        }else if(organiserRepository.findOne(loginEmail)!=null){
            return "redirect:/admin/manage_organisers?"+"organiser_blocked=true";
        }else{
            return "redirect:/admin/profile?"+"someone_blocked=true";
        }

    }

    @RequestMapping(value = "/admin/give_money",method = RequestMethod.POST)
    public String giveMoney(Model model,
                            @RequestParam("login_email")String loginEmail,
                            @RequestParam("amount")int amount
    ){
        if(adminService.payOrganiser(loginEmail,amount*100)==false){
            return "redirect:/admin/manage_organisers?problem=true";
        }
        return "redirect:/admin/manage_organisers";
    }
    @RequestMapping(value = "admin/login/give_money",method = RequestMethod.POST)
    public String payOrganiser(@RequestParam("login_email") String login_email,
                               @RequestParam("amount") int amount
    ){
        if(adminService.payOrganiser(login_email,amount*100) == false ){
            return "redirect:/admin/manage_organisers?failed_pay=true";
        }
        return "redirect:/admin/manage_organisers";
    }
}
