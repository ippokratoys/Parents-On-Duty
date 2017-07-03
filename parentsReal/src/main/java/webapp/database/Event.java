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

	@Temporal(DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date day;

	private Time time;

	private int price;

	//bi-directional many-to-one association to Eventsgroup
	@ManyToOne
	private Eventsgroup eventsgroup;

	//bi-directional many-to-one association to Location
	@ManyToOne
	private Location location;

	//bi-directional many-to-one association to Organiser
	@ManyToOne
	private Organiser organiser;

	private int importance=1;

	public List<BookEvent> getBookEvents() {
		return bookEvents;
	}

	public void setBookEvents(List<BookEvent> bookEvents) {
		this.bookEvents = bookEvents;
	}

	@OneToMany(mappedBy = "event")
	private List<BookEvent> bookEvents;

	//that means that get-it only if asked
	// correct is id.eventsIdEvents
	@OneToMany(mappedBy="event",fetch = FetchType.LAZY)
	private List<EventFeedback> eventFeedbacks;

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

	public List<EventFeedback> getEventFeedback() {
		return this.eventFeedbacks;
	}

	public void setEventFeedback(List<EventFeedback> eventFeedback) {
		this.eventFeedbacks = eventFeedback;
	}

	public List<EventFeedback> addEventsfeedback(EventFeedback newFeedback){
		this.eventFeedbacks.add(newFeedback);
		return eventFeedbacks;
	}


	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}


	public int getSpots() {
		return spots;
	}

	public void setSpots(int spots) {
		this.spots = spots;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}