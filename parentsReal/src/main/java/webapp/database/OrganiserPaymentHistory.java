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
public class OrganiserPaymentHistory implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int transactionId;

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    @Temporal(DATE)
    Date timeStamp;

    @ManyToOne
    Organiser organiser;


    int moneyPayed;

    int oldBalcend;

    String message;

    public OrganiserPaymentHistory() {
    }

    public Organiser getOrganiser() {
        return organiser;
    }

    public void setOrganiser(Organiser organiser) {
        this.organiser = organiser;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    public int getOldBalcend() {
        return oldBalcend;
    }

    public void setOldBalcend(int oldBalcend) {
        this.oldBalcend = oldBalcend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
