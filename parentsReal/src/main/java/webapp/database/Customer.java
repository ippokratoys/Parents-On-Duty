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

	//bi-directional one-to-one association to Login
	@JoinColumn(name="Login_email", insertable=false, updatable=false)
	@OneToOne
	private Login login;

	//bi-directional many-to-many association to Event
	@ManyToMany(mappedBy="customers")
	private List<Event> events;

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

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
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

}
