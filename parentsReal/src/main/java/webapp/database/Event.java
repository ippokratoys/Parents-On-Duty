package webapp.database;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Time;
//import java.sql.Date;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.DATE;

/**
 * The persistent class for the events database table.
 * 
 */
@Entity
//@Table(name="events")
//@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int idEvents;

	private int spots;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(DATE)
	private Date day;

	private Time time;

	private double price;

	//bi-directional many-to-one association to Eventsgroup
	@ManyToOne
	private Eventsgroup eventsgroup;

	//bi-directional many-to-one association to Location
	@ManyToOne
	private Location location;

	//bi-directional many-to-one association to Organiser
	@ManyToOne
	private Organiser organiser;

	public List<BookEvent> getBookEvents() {
		return bookEvents;
	}

	public void setBookEvents(List<BookEvent> bookEvents) {
		this.bookEvents = bookEvents;
	}

	@OneToMany(mappedBy = "event")
	private List<BookEvent> bookEvents;

	//bi-directional many-to-one association to Eventsfeedback
	//that means that get-it only if asked
	// correct is id.eventsIdEvents
	@OneToMany(mappedBy="event",fetch = FetchType.LAZY)
	private List<Eventsfeedback> eventsfeedbacks;

	public Event() {
	}

	public int getIdEvents() {
		return this.idEvents;
	}

	public void setIdEvents(int idEvents) {
		this.idEvents = idEvents;
	}

	public Date getDay() {
		return this.day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Time getTime() {
		return this.time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Eventsgroup getEventsgroup() {
		return this.eventsgroup;
	}

	public void setEventsgroup(Eventsgroup eventsgroup) {
		this.eventsgroup = eventsgroup;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Organiser getOrganiser() {
		return this.organiser;
	}

	public void setOrganiser(Organiser organiser) {
		this.organiser = organiser;
	}

//	public List<EventHasCustomer> getEventHasCustomers() {
//		return this.eventHasCustomers;
//	}
//
//	public void setEventHasCustomers(List<EventHasCustomer> eventHasCustomers) {
//		this.eventHasCustomers = eventHasCustomers;
//	}

	public List<Eventsfeedback> getEventsfeedbacks() {
		return this.eventsfeedbacks;
	}

	public void setEventsfeedbacks(List<Eventsfeedback> eventsfeedbacks) {
		this.eventsfeedbacks = eventsfeedbacks;
	}

	public Eventsfeedback addEventsfeedback(Eventsfeedback eventsfeedback) {
		getEventsfeedbacks().add(eventsfeedback);
		eventsfeedback.setEvent(this);

		return eventsfeedback;
	}

	public Eventsfeedback removeEventsfeedback(Eventsfeedback eventsfeedback) {
		getEventsfeedbacks().remove(eventsfeedback);
		eventsfeedback.setEvent(null);

		return eventsfeedback;
	}

	public int getSpots() {
		return spots;
	}

	public void setSpots(int spots) {
		this.spots = spots;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}