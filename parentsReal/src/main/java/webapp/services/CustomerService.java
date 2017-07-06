package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.database.*;
import webapp.database.repositories.*;

import java.math.BigDecimal;
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
    EventFeedbackRepositorie eventFeedbackRepositorie;

    @Autowired
    private AdminTableRepository adminTableRepository;

    @Autowired
    CustomerPaymentHistoryRepository customerPaymentHistoryRepository;

    public int realMoneyToPoints(int realMoney) {
        if (realMoney >= 100*100){
            return (int) Math.round(realMoney*1.07);
        } else if (realMoney >= 80*100){
            return (int) Math.round(realMoney*1.06);
        } else if(realMoney>=50*100){
            return (int) Math.round(realMoney*1.04);
        }else if (realMoney >= 30*100) {
            return (int) Math.round(realMoney*1.03);
        } else {
            return realMoney;
        }
    }

    public void addPoints(Customer customer, int amount) {
        LocalDate localDate = LocalDate.now();
        int dbAmount = amount * 100;
        CustomerPaymentHistory customerPaymentHistory = new CustomerPaymentHistory();
        Date dateNow = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        customerPaymentHistory.setCustomer(customer);
        customerPaymentHistory.setMoneyPayed(dbAmount);
        customerPaymentHistory.setMoneyGot(realMoneyToPoints(amount*100));
        customerPaymentHistory.setPaymentMethod("Card");
        customerPaymentHistory.setTimeStamp(dateNow);
        customerPaymentHistory.setMessage("Normal form page | old money=" + customer.getPoints());

        customer.setPoints(customer.getPoints() + realMoneyToPoints(amount*100));

        customerRepository.save(customer);
        customerPaymentHistoryRepository.save(customerPaymentHistory);
    }

    public void cancelEvent(BookEvent bookEvent) {
        Customer customer = bookEvent.getCustomer();
        Event event = bookEvent.getEvent();
        Organiser organiser = event.getOrganiser();
        AdminTable adminTable = adminTableRepository.findOne(1);

        int customerNewBalance = customer.getPoints() + event.getPrice();
        int organiserNewBalance = organiser.getPoints() - (int) Math.round(event.getPrice() * 0.9);
        int adminNewBalance = adminTableRepository.findOne(1).getOurPointsFromEvents() - (event.getPrice() - (int) Math.round(event.getPrice() * 0.9));

        customer.setPoints(customerNewBalance);
        organiser.setPoints(organiserNewBalance);
        adminTable.setGivenPoints(adminNewBalance);

        customerRepository.save(customer);
        organiserRepository.save(organiser);
        adminTableRepository.save(adminTable);
        bookEventRepository.delete(bookEvent);
    }

    public void cancelEvent(int bookId) {
        BookEvent bookEvent = bookEventRepository.findOne(bookId);
        if (bookEvent == null) {
            return;
        }
        cancelEvent(bookEvent);
    }

    public void newFeedback(BookEvent bookEvent,String text, int rating){
        Date date = new Date();
        EventFeedback eventFeedback = new EventFeedback(text, rating, date, bookEvent.getEvent(), bookEvent.getCustomer());

        bookEvent.setHasComment(true);


        bookEventRepository.save(bookEvent);
        eventFeedbackRepositorie.save(eventFeedback);


    }

    public void updateLocation(Customer customer, BigDecimal lat, BigDecimal lon ){

        customer.setLat(lat);
        customer.setLon(lon);
        customerRepository.save(customer);
    }
}