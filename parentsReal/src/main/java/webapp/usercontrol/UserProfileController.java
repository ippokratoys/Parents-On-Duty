package webapp.usercontrol;

import jdk.nashorn.internal.objects.NativeUint8Array;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.database.Customer;
import webapp.database.repositories.CustomerRepository;
import webapp.services.CustomerService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by thanasis on 6/6/2017.
 */
@Controller
public class UserProfileController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value="/user/profile",method= RequestMethod.GET)
    public String showProfile(
            @AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
            Model model
    ){
        Customer customer=customerRepository.findOne(userDetails.getUsername());

        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //String date = (DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate));
        model.addAttribute("curUser",customer);
        model.addAttribute("localDate", date);
        return "profile/parent/profile";
    }

    @RequestMapping(value = "/user/history",method = RequestMethod.GET)
    public String showHistory(
            Model model,
            @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Customer curCustomer=customerRepository.findOne(userDetails.getUsername());

        model.addAttribute("localDate", date);
        model.addAttribute("curUser",curCustomer);
        return "profile/parent/history";
    }

    @RequestMapping(value = "/user/wallet",method = RequestMethod.GET)
    public String showWallet(
            Model model,
            @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Customer curCustomer=customerRepository.findOne(userDetails.getUsername());
        model.addAttribute("curUser",curCustomer);
        return "profile/parent/wallet";
    }

    @RequestMapping(value = "/user/wallet/add_money",method = RequestMethod.POST)
    public String addMoney(
            @RequestParam(value = "name", required = false) String cardName,
            @RequestParam(value = "card_number",required = false) String cardNumber,
            @RequestParam(value = "amount",required = true) int amount,
            Model model,
            @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Customer curCustomer=customerRepository.findOne(userDetails.getUsername());
        model.addAttribute("curUser",curCustomer);

        customerService.addPoints(curCustomer,amount);
        return "redirect:/user/profile";
    }

    @RequestMapping(value = "/user/historytrans",method = RequestMethod.GET)
    public String getTrans(
            Model model,
            @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Customer curCustomer=customerRepository.findOne(userDetails.getUsername());
        model.addAttribute("curUser",curCustomer);

        return "profile/parent/historytrans";
    }

    @RequestMapping(value = "/user/cancel/{book_id}",method = RequestMethod.POST)
    public String cancelEvent(
            Model model,
            @PathVariable("book_id") int bookID,
            @AuthenticationPrincipal final UserDetails userDetails//we add this so we know if is logged to show correct bar
    ){
        Customer curCustomer=customerRepository.findOne(userDetails.getUsername());
        model.addAttribute("curUser",curCustomer);
        System.out.println(curCustomer.getLogin_email());
        System.out.println("id:" + bookID);
        customerService.cancelEvent(bookID);
        return "profile/parent/profile";
    }

}
