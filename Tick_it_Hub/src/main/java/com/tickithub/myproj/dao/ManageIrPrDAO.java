package com.tickithub.myproj.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.tickithub.myproj.pojo.FunctionalTeam;
import com.tickithub.myproj.pojo.Incident;
import com.tickithub.myproj.pojo.Problem;

@Component
public class ManageIrPrDAO extends DAO {

	// save  incident
	public void saveIncident(Incident ir) throws Exception {

		try {
			Session hbSession = getSession();
			Transaction tx = hbSession.beginTransaction();
			hbSession.saveOrUpdate(ir);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	// save  problem
		public Problem saveProblem(Problem pr) throws Exception {

			try {
				Session hbSession = getSession();
				Transaction tx = hbSession.beginTransaction();
				hbSession.saveOrUpdate(pr);
				tx.commit();
				return pr;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
				
			}
		}

	// get all incidents for a problem
	public Problem fetchIncidents(int prId) {
		Session hbSession = getSession();
		Transaction tx = hbSession.beginTransaction();
		Problem pr = hbSession.get(Problem.class, prId);
		Hibernate.initialize(pr.getIrList());
		tx.commit();
		return pr;
	}

	// get the selected IR to be assigned to other team by id
	public Incident getSelectedIr(int irNum) {
		Session hbSession = getSession();
		Transaction tx = hbSession.beginTransaction();
		Incident ir = hbSession.get(Incident.class, irNum);
		Hibernate.initialize(ir.getProblem());
		tx.commit();
		return ir;
	}
	
	public int assignIrToNewPr(int ir, int pr){
		
		Session hbSession = getSession();
		Transaction tx = hbSession.beginTransaction();
		String hql = "update Incident ir set ir.problem.prNum=:prNum where ir.irNum=:irNum";
		Query qry = hbSession.createQuery(hql);
		qry.setInteger("prNum", pr);
		qry.setInteger("irNum", ir);
		
		int rows = qry.executeUpdate();
		tx.commit();
		
		return rows;
	}

}
