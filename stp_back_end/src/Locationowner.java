package stp_back_end;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the locationowner database table.
 * 
 */
@Entity
@NamedQuery(name="Locationowner.findAll", query="SELECT l FROM Locationowner l")
public class Locationowner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String login_email;

	private String name;

	private String surname;

	//bi-directional many-to-one association to Location
	@OneToMany(mappedBy="locationowner")
	private List<Location> locations;

	//bi-directional one-to-one association to Login
	@OneToOne
	@JoinColumn(name="Login_email", insertable=false, updatable=false)
	private Login login;

	public Locationowner() {
	}

	public String getLogin_email() {
		return this.login_email;
	}

	public void setLogin_email(String login_email) {
		this.login_email = login_email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Location> getLocations() {
		return this.locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Location addLocation(Location location) {
		getLocations().add(location);
		location.setLocationowner(this);

		return location;
	}

	public Location removeLocation(Location location) {
		getLocations().remove(location);
		location.setLocationowner(null);

		return location;
	}

	public Login getLogin() {
		return this.login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

}