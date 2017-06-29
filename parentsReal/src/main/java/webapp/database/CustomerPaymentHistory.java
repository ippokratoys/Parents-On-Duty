package webapp.database;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

/**
 * Created by thanasis on 29/6/2017.
 */

@Entity
public class CustomerPaymentHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int transactionID;

    @ManyToOne
    Customer customer;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(DATE)
    private Date timeStamp;

    int moneyPayed;

    int moneyGot;

    String message;

    String paymentMethod;

    public CustomerPaymentHistory() {
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getMoneyPayed() {
        return moneyPayed;
    }

    public void setMoneyPayed(int moneyPayed) {
        this.moneyPayed = moneyPayed;
    }

    public int getMoneyGot() {
        return moneyGot;
    }

    public void setMoneyGot(int moneyGot) {
        this.moneyGot = moneyGot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
