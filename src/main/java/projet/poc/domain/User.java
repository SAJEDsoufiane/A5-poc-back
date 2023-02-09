package projet.poc.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@Builder
@Entity
public class User {

	private Long id;
	  
	private String firstname;
	
	private String lastname;
	private String fullname;

	
	private @Id String username;

	private String password;
	
	@ManyToOne
	private User manager;
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="USER_ROLE",
	joinColumns= {
			@JoinColumn(name="USER_ID")
	},
	inverseJoinColumns = {
			@JoinColumn(name="ROLE_ID")
	})
	
	private Set<Role> role;
	

	public User() {
		this.role= new HashSet<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFname() {
		return firstname;
	}

	public void setFname(String fname) {
		this.firstname = fname;
	}

	public String getLname() {
		return lastname;
	}

	public void setLname(String lname) {
		this.lastname = lname;
	}

	public String getUserName() {
		return username;
	}
	public String getUserId() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public Role getRole() {
		return role.iterator().next();
	}

	public void setRole(Role role) {
		this.role.add(role);
	}
	
}
