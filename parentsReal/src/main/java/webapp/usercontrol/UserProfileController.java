package webapp.usercontrol;

import jdk.nashorn.internal.objects.NativeUint8Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webapp.database.Customer;
import webapp.database.repositories.CustomerRepository;

/**
 * Created by thanasis on 6/6/2017.
 */
@Controller
public class UserProfileController {


    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(value="/user/profile",method= RequestMethod.GET)
    public String showProfile(
            @AuthenticationPrincipal final UserDetails userDetails,//we add this so we know if is logged to show correct bar
            Model model
    ){
        return "profile/parent/profile";
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
}
