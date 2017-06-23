package webapp.database;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the organiser database table.
 * 
 */
@Entity
@NamedQuery(name="Organiser.findAll", query="SELECT o FROM Organiser o")
public class Organiser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String login_email;

	private String name;

	private String surname;

	private Date birthdate;

	private String taxpayerId;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="organiser")
	private List<Event> events;

	//bi-directional many-to-one association to Eventsgroup
	@OneToMany(mappedBy="organiser")
	private List<Eventsgroup> eventsgroups;


	@OneToMany(mappedBy="locationOwner")
	private List<Location> locations;

	//bi-directional one-to-one association to Login
	@OneToOne
	@JoinColumn(name="Login_email", insertable=false, updatable=false)
	private Login login;

	public Organiser() {
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public String getLogin_email() {
		return this.login_email;
	}

	public void setLogin_email(String login_email) {
		this.login_email = login_email;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public String getTaxpayerId() {
		return taxpayerId;
	}

	public void setTaxpayerId(String taxpayerId) {
		this.taxpayerId = taxpayerId;
	}


	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setOrganiser(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setOrganiser(null);

		return event;
	}

	public List<Eventsgroup> getEventsgroups() {
		return this.eventsgroups;
	}

	public void setEventsgroups(List<Eventsgroup> eventsgroups) {
		this.eventsgroups = eventsgroups;
	}

	public Eventsgroup addEventsgroup(Eventsgroup eventsgroup) {
		getEventsgroups().add(eventsgroup);
		eventsgroup.setOrganiser(this);

		return eventsgroup;
	}

	public Eventsgroup removeEventsgroup(Eventsgroup eventsgroup) {
		getEventsgroups().remove(eventsgroup);
		eventsgroup.setOrganiser(null);

		return eventsgroup;
	}

	public Login getLogin() {
		return this.login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

}