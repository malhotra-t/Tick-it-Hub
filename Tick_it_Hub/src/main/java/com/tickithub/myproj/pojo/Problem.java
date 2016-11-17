package com.tickithub.myproj.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
public class Problem {
	@Id
	@GeneratedValue
	private int prNum;
	private String prPriority;
	private String prStatus;
	private String prDesc;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date prLogDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date prSlaDate;
	
	@ManyToOne
	private User prLoggedBy;
	
	@ManyToOne
	private User prResolvedBy;
	
	@OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<Incident> irList;
	
	@ManyToOne
	private FunctionalTeam team;
	
		
	public Problem(){
	}
	
	public FunctionalTeam getTeam() {
		return team;
	}
	public void setTeam(FunctionalTeam team) {
		this.team = team;
	}
	public int getPrNum() {
		return prNum;
	}
	public void setPrNum(int prNum) {
		this.prNum = prNum;
	}
	public String getPrPriority() {
		return prPriority;
	}
	public void setPrPriority(String prPriority) {
		this.prPriority = prPriority;
	}
	public String getPrStatus() {
		return prStatus;
	}
	public void setPrStatus(String prStatus) {
		this.prStatus = prStatus;
	}
	public String getPrDesc() {
		return prDesc;
	}
	public void setPrDesc(String prDesc) {
		this.prDesc = prDesc;
	}
	public Date getPrLogDate() {
		return prLogDate;
	}
	public void setPrLogDate(Date prLogDate) {
		this.prLogDate = prLogDate;
	}
	public Date getPrSlaDate() {
		return prSlaDate;
	}
	public void setPrSlaDate(Date prSlaDate) {
		this.prSlaDate = prSlaDate;
	}
	public User getPrLoggedBy() {
		return prLoggedBy;
	}
	public void setPrLoggedBy(User prLoggedBy) {
		this.prLoggedBy = prLoggedBy;
	}
	public User getPrResolvedBy() {
		return prResolvedBy;
	}
	public void setPrResolvedBy(User prResolvedBy) {
		this.prResolvedBy = prResolvedBy;
	}
	public List<Incident> getIrList() {
		return irList;
	}
	public void setIrList(List<Incident> irList) {
		this.irList = irList;
	}
	


}
