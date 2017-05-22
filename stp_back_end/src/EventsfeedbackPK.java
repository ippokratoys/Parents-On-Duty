package stp_back_end;

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

	@Column(insertable=false, updatable=false)
	private int events_idEvents;

	public EventsfeedbackPK() {
	}
	public int getIdEventsFeedback() {
		return this.idEventsFeedback;
	}
	public void setIdEventsFeedback(int idEventsFeedback) {
		this.idEventsFeedback = idEventsFeedback;
	}
	public int getEvents_idEvents() {
		return this.events_idEvents;
	}
	public void setEvents_idEvents(int events_idEvents) {
		this.events_idEvents = events_idEvents;
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
			&& (this.events_idEvents == castOther.events_idEvents);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEventsFeedback;
		hash = hash * prime + this.events_idEvents;
		
		return hash;
	}
}