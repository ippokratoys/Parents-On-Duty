package webapp.database;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by thanasis on 3/7/2017.
 */
@Entity
public class EventFeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int feedback;

    private String content;

    private int rating;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Event event;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    public EventFeedback() {
    }

    public EventFeedback(String content, int rating, Date date, Event event, Customer customer) {
        this.content = content;
        this.rating = rating;
        this.date = date;
        this.event = event;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
