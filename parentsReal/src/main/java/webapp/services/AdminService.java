package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
import webapp.database.Location;
import webapp.database.Login;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.LocationRepository;
import webapp.database.repositories.LoginRepository;
import webapp.database.repositories.OrganiserRepository;

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

    public boolean resetLoginPassword(Login login,String newPwd){
        String newHashedPwd = registerService.hashPassword(newPwd);
        login.setPwd(newHashedPwd);

        loginRepository.save(login);

        return true;
    }

    public boolean manageLocation(Location location,boolean accept){
        location.setCertificateChecked(true);
        location.setValidUntil(null);
        if(accept==true){
            location.setValidUntil(new Date());
        }else {
            location.setValidUntil(null);
        }
        locationRepository.save(location);
        return true;
    }
}
