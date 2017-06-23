package webapp.database;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * The persistent class for the eventsfeedback database table.
 * 
 */
@Entity
//@NamedQuery(name="Eventsfeedback.findAll", query="SELECT e FROM Eventsfeedback e")
public class Eventsfeedback implements Serializable {
	private static final long serialVersionUID = 1L;

	//that means that is creates a column from the embedded element
	@EmbeddedId
	@AttributeOverride(name="idEventsFeedback",column=@Column(name="id_events_feedback"))
	private EventsfeedbackPK id;

	private String content;

	private int rating;

	private Date date;

	//bi-directional many-to-one association to Event
	@ManyToOne
	@MapsId("eventsIdEvents")
	@JoinColumn(name="events_id_events")
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

}