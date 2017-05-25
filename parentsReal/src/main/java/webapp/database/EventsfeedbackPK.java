package webapp.database;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eventsfeedback database table.
 * 
 */
@Embeddable
public class EventsfeedbackPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idEventsFeedback;

//	@MapsId(value="event")
//	@ManyToOne
//	@JoinColumn(name="eventsIdEvents")
//	private Event eventsIdEvents;

	@Column(insertable=false, updatable=false)
	private int eventsIdEvents;
	

	public EventsfeedbackPK() {
	}
	public int getIdEventsFeedback() {
		return this.idEventsFeedback;
	}
	public void setIdEventsFeedback(int idEventsFeedback) {
		this.idEventsFeedback = idEventsFeedback;
	}
	public int getEventsIdEvents() {
		return this.eventsIdEvents;
	}
	public void setEventsIdEvents(int eventsIdEvents) {
		this.eventsIdEvents = eventsIdEvents;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EventsfeedbackPK)) {
			return false;
		}
		EventsfeedbackPK castOther = (EventsfeedbackPK)other;
		return 
			(this.idEventsFeedback == castOther.idEventsFeedback)
			&& (this.eventsIdEvents == castOther.eventsIdEvents);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEventsFeedback;
		hash = hash * prime + this.eventsIdEvents;
		
		return hash;
	}
}