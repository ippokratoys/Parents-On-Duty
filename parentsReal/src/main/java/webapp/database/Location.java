package webapp.database;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the location database table.
 * 
 */
@Entity
@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(precision=10, scale=6)
	float lat;
	@Column(precision=10, scale=6)
	float lot;

	private String address;

	private String postcode;

	private String name;

	private String imagePath;

	private Date validUntil;

	private String certificatePath;


	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="location")
	private List<Event> events;

	//bi-directional many-to-one association to Organiser
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Organiser locationOwner;

	public Location() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLot() {
		return lot;
	}

	public void setLot(float lot) {
		this.lot = lot;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

	public String getCertificatePath() {
		return certificatePath;
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setLocation(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setLocation(null);

		return event;
	}

	public Organiser getLocationOwner() {
		return this.locationOwner;
	}
	
	//change in the future
	public void setLocationOwner(Organiser locationOwner) {
		this.locationOwner = locationOwner;
	}

}