package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.database.*;

import webapp.database.repositories.*;

import java.util.Date;

/**
 * Created by ilias on 4/7/2017.
 */
@Service
public class AdminService {

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

    public boolean resetLoginPassword(Login login,String newPwd){
        String newHashedPwd = registerService.hashPassword(newPwd);
        login.setPwd(newHashedPwd);

        loginRepository.save(login);

        return true;
    }

    public boolean manageLocation(Location location,boolean accept) {
        location.setCertificateChecked(true);
        location.setValidUntil(null);
        if (accept == true) {
            location.setValidUntil(new Date());
        } else {
            location.setValidUntil(null);
        }
        locationRepository.save(location);
        return true;
    }

    public boolean blockOrganiser(String id){
        Organiser organiser = organiserRepository.findOne(id);
        if(organiser==null){
            return false;
        }

        organiser.getLogin().setActive(false);

        return true;

    }

    public boolean unblockOrganiser(String id){
        Organiser organiser = organiserRepository.findOne(id);
        if(organiser==null){
            return false;
        }

        organiser.getLogin().setActive(true);

        return true;
    }

    public  boolean blockParent(String id){
        Customer costumer = customerRepository.findOne(id);
        if(costumer==null){
            return false;
        }

        costumer.getLogin().setActive(false);

        return true;
    }

    public  boolean unblockParent(String id){
        Customer costumer = customerRepository.findOne(id);
        if(costumer==null){
            return false;
        }

        costumer.getLogin().setActive(true);

        return true;
    }

    public boolean payOrganiser(String id, int money){
        Organiser organiser = organiserRepository.findOne(id);
        if(organiser==null){
            return false;
        }
        if(organiser.getPoints() < money){
            return false;
        }
        OrganiserPaymentHistory organiserPaymentHistory = new OrganiserPaymentHistory();
        organiserPaymentHistory.setOrganiser(organiser);
        organiserPaymentHistory.setMoneyPayed(money);
        organiserPaymentHistory.setOldBalcend(organiser.getPoints());
        organiserPaymentHistory.setTimeStamp(new Date());

        String message = "Transferred "+ (money/100) +" € to " +organiser.getName() + " "+organiser.getSurname()
                + " bank account.";

        organiserPaymentHistory.setMessage(message);
        organiserPaymentHistoryRepository.save(organiserPaymentHistory);

        organiser.setPoints(organiser.getPoints()-money);
        organiserRepository.save(organiser);
        return true;
    }


}
