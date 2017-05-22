package stp_back_end;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the login database table.
 * 
 */
@Entity
@NamedQuery(name="Login.findAll", query="SELECT l FROM Login l")
public class Login implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String email;

	private String pwd;

	//bi-directional one-to-one association to Customer
	@OneToOne(mappedBy="login")
	private Customer customer;

	//bi-directional one-to-one association to Locationowner
	@OneToOne(mappedBy="login")
	private Locationowner locationowner;

	//bi-directional one-to-one association to Organiser
	@OneToOne(mappedBy="login")
	private Organiser organiser;

	public Login() {
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Locationowner getLocationowner() {
		return this.locationowner;
	}

	public void setLocationowner(Locationowner locationowner) {
		this.locationowner = locationowner;
	}

	public Organiser getOrganiser() {
		return this.organiser;
	}

	public void setOrganiser(Organiser organiser) {
		this.organiser = organiser;
	}
	

}