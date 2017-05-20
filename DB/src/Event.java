package stp_back_end;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the events database table.
 * 
 */
@Entity
@Table(name="events")
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idEvents;

	private String day;

	private String time;

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
	@ManyToMany
	@JoinTable(
		name="events_has_customer"
		, joinColumns={
			@JoinColumn(name="Events_idEvents",insertable=false, updatable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="Customer_Login_email",insertable=false, updatable=false)
			}
		)
	private List<Customer> customers;

	//bi-directional many-to-one association to Eventsfeedback
	@OneToMany(mappedBy="event")
	private List<Eventsfeedback> eventsfeedbacks;

	public Event() {
	}

	public int getIdEvents() {
		return this.idEvents;
	}

	public void setIdEvents(int idEvents) {
		this.idEvents = idEvents;
	}

	public String getDay() {
		return this.day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
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

	public List<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

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

}