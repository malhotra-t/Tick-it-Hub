package com.tickithub.myproj.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class SignUpRequest {
	@Id
	@GeneratedValue
	@Column(unique=true, nullable=false)
	private int reqId;
	
	@Column(nullable=false)
	private String fname;
	
	@Column(nullable=false)
	private String lname;
	
	@Column(nullable=false)
	private String role;
	
	@Column(nullable=false)
	private String userEmail;
	
	@Column(nullable=false)
	private String managerEmail;
	
	@Column(nullable=false)
	private String signUpStatus;
	
	private Date raisedOn;
	
	@Column(unique=true, nullable=false)
	private String uname;
	
	@Column(nullable=false)
	private String password;
	
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
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public Date getRaisedOn() {
		return raisedOn;
	}
	public void setRaisedOn(Date raisedOn) {
		this.raisedOn = raisedOn;
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
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getSignUpStatus() {
		return signUpStatus;
	}
	public void setSignUpStatus(String signUpStatus) {
		this.signUpStatus = signUpStatus;
	}
	
	

}
