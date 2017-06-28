package webapp.database;

import java.io.Serializable;
import javax.persistence.*;


import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
//@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String login_email;

	private int points;

	private int wallet;

//	@Column(precision=10, scale=6)
//	float lat;
//	@Column(precision=10, scale=6)
//	float lot;
	@Column(precision = 10, scale = 6)
	private BigDecimal lat;

	@Column(precision = 10, scale = 6)
	private BigDecimal lon;

	//bi-directional one-to-one association to Login
	@JoinColumn(name="Login_email", insertable=false, updatable=false)
	@OneToOne
	private Login login;

	@OneToMany(mappedBy = "customer")
	private List<BookEvent> bookEvents;


	//bi-directional many-to-many association to Event
//	@ManyToMany(mappedBy="customer")
//	@OneToMany(mappedBy = "primaryKey.customer",
//			cascade = CascadeType.ALL)
//	private List<EventHasCustomer> eventHasCustomers;

	public Customer() {

	}

	public String getLogin_email() {
		return this.login_email;
	}

	public void setLogin_email(String login_email) {
		this.login_email = login_email;
	}



	public Login getLogin() {
		return this.login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

//	public List<EventHasCustomer> getEventHasCustomers() {
//		return this.eventHasCustomers;
//	}
//
//	public void setEventHasCustomers(List<EventHasCustomer> eventHasCustomers) {
//		this.eventHasCustomers = eventHasCustomers;
//	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getWallet() {
		return wallet;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	public BigDecimal getLat() {
		return lat;
	}


	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLon() {
		return lon;
	}

	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

	public List<BookEvent> getBookEvents() {
		return bookEvents;
	}

	public void setBookEvents(List<BookEvent> bookEvents) {
		this.bookEvents = bookEvents;
	}
}
