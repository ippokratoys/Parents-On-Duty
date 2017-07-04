/**
 * Created by thanasis on 24/6/2017.
 */
package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
import webapp.database.Login;
import webapp.database.Organiser;
import webapp.database.repositories.CustomerRepository;
import webapp.database.repositories.LoginRepository;
import webapp.database.repositories.OrganiserRepository;

import java.util.Map;

@Service
public class RegisterService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    OrganiserRepository organiserRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Boolean isPasswordOk(String password) {
        if (password.length() >= 3 && password.length() <= 10) {
            return true;
        }
        return false;
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Organiser createOrganiser(Map<String,String> allParams) throws Exception{
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());

            if (entry.getValue() == null || entry.getValue() == "") {
                throw new Exception("Empty Field");
//                return null;
            }

            if (entry.getKey() == "password" && this.isPasswordOk(entry.getValue()) != false) {
                //if password not ok
                throw new Exception("Password not strong enough");
//                return null;
            }
        }
        Login newLogin = loginRepository.findOne(allParams.get("Email"));
        //if the user already exists redirect to home page
        if (newLogin != null) {
            throw new Exception("User exists");
        }

        newLogin = new Login();
        newLogin.setEmail(allParams.get("Email"));
        newLogin.setRole("ORGANISER");
        newLogin.setActive(true);
        newLogin.setPwd(hashPassword(allParams.get("Pwd")));
        newLogin.setUsername(allParams.get("username"));

        Organiser newOrganiser = new Organiser();
        newOrganiser.setLogin_email(newLogin.getEmail());
        newOrganiser.setTaxpayerId(allParams.get("ID"));
        newOrganiser.setName(allParams.get("Name"));
        newOrganiser.setSurname(allParams.get("Surname"));
        loginRepository.save(newLogin);
        organiserRepository.save(newOrganiser);

        return organiserRepository.findOne(allParams.get("Email"));
    }

    public Customer createCustomer(Map<String,String> allParams) throws Exception{
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
//            System.out.println(entry.getKey() + "/" + entry.getValue());
            if(entry.getValue()==null || entry.getValue()==""){
                throw new Exception("Empty Field");
//                return null;
            }
            if(entry.getKey()=="password" && this.isPasswordOk(entry.getValue())!=false ){
                //if password not ok
                throw new Exception("Password not strong enough");
//                return null;
            }
        }

        Login newLogin=loginRepository.findOne(allParams.get("Email"));
        //if the user already exists redirect to home page
        if(newLogin!=null){
            throw new Exception("User exists");
//            return null;
        }
        newLogin=new Login();
        newLogin.setEmail(allParams.get("Email"));
        newLogin.setRole("PARENT");
        newLogin.setActive(true);
        newLogin.setPwd(hashPassword(allParams.get("Pwd")));
        newLogin.setUsername(allParams.get("username"));

        Customer newCustomer = new Customer();
        newCustomer.setLogin_email(newLogin.getEmail());
        newCustomer.setPoints(15*100);
        newCustomer.setWallet(15*100);
        loginRepository.save(newLogin);
        customerRepository.save(newCustomer);

        return customerRepository.findOne(newCustomer.getLogin_email());
    }
}
