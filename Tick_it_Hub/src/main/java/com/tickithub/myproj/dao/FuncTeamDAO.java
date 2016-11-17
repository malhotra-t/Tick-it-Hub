package com.tickithub.myproj.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.tickithub.myproj.pojo.FunctionalTeam;
import com.tickithub.myproj.pojo.Incident;
import com.tickithub.myproj.pojo.SignUpRequest;
@Component
public class FuncTeamDAO extends DAO{
	
	
	//save new team
		public void saveTeam(FunctionalTeam funcTeam) throws Exception{
			try{
			Session hbSession = getSession();
			Transaction tx = hbSession.beginTransaction();
			hbSession.saveOrUpdate(funcTeam);
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		//fetch team by id
		public FunctionalTeam fetchById(int teamId) {
			Session hbSession = getSession();
			Transaction tx = hbSession.beginTransaction();
			FunctionalTeam ft = hbSession.get(FunctionalTeam.class, teamId);
			
			Hibernate.initialize(ft.getTeamMembers());
			Hibernate.initialize(ft.getProblemList());
			tx.commit();
			return ft;
		}
		
		//fetch all func teams
		public List<FunctionalTeam> getAllTeams(){
			Session hbSession = getSession();
			Transaction tx = hbSession.beginTransaction();
			String qryStr = "From FunctionalTeam";
			Query qry = hbSession.createQuery(qryStr);
			List<FunctionalTeam> teamList = qry.list();
			tx.commit();
			return teamList;
		}

}
