package webapp.database;

import java.io.Serializable;
import javax.persistence.*;


import javax.persistence.Entity;
import javax.persistence.Id;

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

	private String username;

	private String pwd;

	private Boolean active;

	private String role;
	//bi-directional one-to-one association to Customer
	@OneToOne(mappedBy="login")
	private Customer customer;

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

	public Organiser getOrganiser() {
		return this.organiser;
	}

	public void setOrganiser(Organiser organiser) {
		this.organiser = organiser;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}