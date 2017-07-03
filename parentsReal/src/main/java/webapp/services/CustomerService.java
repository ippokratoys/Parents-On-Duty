package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.database.Customer;
import webapp.database.CustomerPaymentHistory;
import webapp.database.repositories.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by thanasis on 2/7/2017.
 */

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerPaymentHistoryRepository customerPaymentHistoryRepository;

    public int realMoneyToPoints(int realMoney){
        if(realMoney>=100){
            return realMoney+8;
        }else if(realMoney>=80){
            return realMoney+5;
        }else if(realMoney>=30){
            return realMoney+1;
        }else {
            return realMoney;
        }
    }

    public void addPoints(Customer customer,int amount){
        LocalDate localDate = LocalDate.now();
        int dbAmount=amount*100;
        CustomerPaymentHistory customerPaymentHistory = new CustomerPaymentHistory();
        Date dateNow = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        customerPaymentHistory.setCustomer(customer);
        customerPaymentHistory.setMoneyPayed(dbAmount);
        customerPaymentHistory.setMoneyGot(realMoneyToPoints(amount)*100);
        customerPaymentHistory.setPaymentMethod("Card");
        customerPaymentHistory.setTimeStamp(dateNow);
        customerPaymentHistory.setMessage("Normal form page | old money="+customer.getPoints());

        customer.setPoints(customer.getPoints()+realMoneyToPoints(amount)*100);

        customerRepository.save(customer);
        customerPaymentHistoryRepository.save(customerPaymentHistory);
    }
}
