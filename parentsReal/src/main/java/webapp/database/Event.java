package webapp.database;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

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
	private int idEvents;

	private int spots;

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

	//bi-directional many-to-many association to Customer
//	@ManyToMany
//	@JoinTable(
//		name="events_has_customer"
//		, joinColumns={
//				@JoinColumn(name="Events_idEvents",insertable=false, updatable=false, referencedColumnName = "idEvents")
//			}
//		, inverseJoinColumns={
//				@JoinColumn(name="Customer_Login_email",insertable=false, updatable=false,referencedColumnName = "login_email")
//			}
//	)
//	@ManyToMany(mappedBy = "primaryKey.event",
//			cascade = CascadeType.ALL)
//	private List<Event> eventHasCustomers;

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