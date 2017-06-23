package webapp.database;

import java.io.Serializable;
import javax.persistence.*;


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

	private double points;

	private double wallet;

	@Column(precision=10, scale=6)
	float lat;
	@Column(precision=10, scale=6)
	float lot;

	//bi-directional one-to-one association to Login
	@JoinColumn(name="Login_email", insertable=false, updatable=false)
	@OneToOne
	private Login login;

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

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
		this.wallet = wallet;
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

}
