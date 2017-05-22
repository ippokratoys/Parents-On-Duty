package stp_back_end;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eventsfeedback database table.
 * 
 */
@Entity
@NamedQuery(name="Eventsfeedback.findAll", query="SELECT e FROM Eventsfeedback e")
public class Eventsfeedback implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EventsfeedbackPK id;

	private String content;

	//bi-directional many-to-one association to Event
	@ManyToOne
	@JoinColumn(name="Events_idEvents")
	private Event event;

	public Eventsfeedback() {
	}

	public EventsfeedbackPK getId() {
		return this.id;
	}

	public void setId(EventsfeedbackPK id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}