package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.database.*;
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
    OrganiserRepository organiserRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BookEventRepository bookEventRepository;

    @Autowired
    private AdminTableRepositorie adminTableRepositorie;

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

    public void cancelEvent(BookEvent bookEvent){
        Customer customer = bookEvent.getCustomer();
        Event event = bookEvent.getEvent();
        Organiser organiser = event.getOrganiser();
        AdminTable adminTable = adminTableRepositorie.findOne(1);

        int customerNewBalance = customer.getPoints()+event.getPrice();
        int organiserNewBalance = organiser.getPoints() - (int) Math.round(event.getPrice()*0.9);
        int adminNewBalance = adminTableRepositorie.findOne(1).getOurPointsFromEvents()- (event.getPrice() - (int) Math.round(event.getPrice()*0.9)) ;

        customer.setPoints(customerNewBalance);
        organiser.setPoints(organiserNewBalance);
        adminTable.setGivenPoints(adminNewBalance);

        customerRepository.save(customer);
        organiserRepository.save(organiser);
        adminTableRepositorie.save(adminTable);
        bookEventRepository.delete(bookEvent);
    }

    public void cancelEvent(int bookId){
        BookEvent bookEvent = bookEventRepository.findOne(bookId);
        if(bookEvent==null){
            return ;
        }
        cancelEvent(bookEvent);
    }
}