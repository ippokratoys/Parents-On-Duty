package stp_back_end;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eventsgroup database table.
 * 
 */
@Entity
@NamedQuery(name="Eventsgroup.findAll", query="SELECT e FROM Eventsgroup e")
public class Eventsgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idEventsGroup;

	private String name;

	private String type;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="eventsgroup")
	private List<Event> events;

	//bi-directional many-to-one association to Organiser
	@ManyToOne
	private Organiser organiser;

	public Eventsgroup() {
	}

	public int getIdEventsGroup() {
		return this.idEventsGroup;
	}

	public void setIdEventsGroup(int idEventsGroup) {
		this.idEventsGroup = idEventsGroup;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setEventsgroup(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setEventsgroup(null);

		return event;
	}

	public Organiser getOrganiser() {
		return this.organiser;
	}

	public void setOrganiser(Organiser organiser) {
		this.organiser = organiser;
	}

}