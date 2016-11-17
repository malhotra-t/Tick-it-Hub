package com.tickithub.myproj.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
public class User {
	@Id
	@GeneratedValue
	@Column(unique=true, nullable=false)
	private int userId;
	
	@Column
	private String fname;
	@Column
	private String lname;
	@Column
	private String role;
	
	@ManyToOne 
	private FunctionalTeam team;
	@Column
	private String email;

	@Column(unique=true, nullable=false)
	private String uname;
	@Column
	private String password;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public FunctionalTeam getTeam() {
		return team;
	}
	public void setTeam(FunctionalTeam team) {
		this.team = team;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
