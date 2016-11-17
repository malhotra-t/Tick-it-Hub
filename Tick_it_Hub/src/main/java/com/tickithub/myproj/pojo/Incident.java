package com.tickithub.myproj.pojo;

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
public class Incident {
	@Id
	@GeneratedValue
	private int irNum;
	private String irPriority;
	private String irStatus;
	private String irDesc;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date irLogDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date slaDate;
	
	@ManyToOne
	private User irLoggedBy;
	
	@ManyToOne
	private User irResolvedBy;
	
	@ManyToOne
	private Problem problem; //This will be many-to-one
	
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public int getIrNum() {
		return irNum;
	}
	public void setIrNum(int irNum) {
		this.irNum = irNum;
	}
	public String getIrPriority() {
		return irPriority;
	}
	public void setIrPriority(String irPriority) {
		this.irPriority = irPriority;
	}
	public String getIrStatus() {
		return irStatus;
	}
	public void setIrStatus(String irStatus) {
		this.irStatus = irStatus;
	}
	public String getIrDesc() {
		return irDesc;
	}
	public void setIrDesc(String irDesc) {
		this.irDesc = irDesc;
	}
	public Date getIrLogDate() {
		return irLogDate;
	}
	public void setIrLogDate(Date irLogDate) {
		this.irLogDate = irLogDate;
	}
	public Date getSlaDate() {
		return slaDate;
	}
	public void setSlaDate(Date slaDate) {
		this.slaDate = slaDate;
	}
	public User getIrLoggedBy() {
		return irLoggedBy;
	}
	public void setIrLoggedBy(User irLoggedBy) {
		this.irLoggedBy = irLoggedBy;
	}
	public User getIrResolvedBy() {
		return irResolvedBy;
	}
	public void setIrResolvedBy(User irResolvedBy) {
		this.irResolvedBy = irResolvedBy;
	}
	

}
