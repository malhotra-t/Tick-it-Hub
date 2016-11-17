package com.tickithub.myproj.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import com.tickithub.myproj.pojo.SignUpRequest;
import com.tickithub.myproj.pojo.User;

@Component
public class UserDAO extends DAO{
	
	//Confirm user
	public User checkLogin(User user){
		Session hbSession = getSession();
		Transaction tx = hbSession.beginTransaction();
		String qryStr = "From User where uname = :userNameStr";
		Query qry = getSession().createQuery(qryStr);
		qry.setString("userNameStr", user.getUname());
		
		User userInDb = (User) qry.uniqueResult();
		tx.commit();
		if(userInDb!=null && userInDb.getPassword().equals(user.getPassword())){
			return userInDb;
		}
		
		return null;
		
	}
	//save sign up request
	public void saveRequest(SignUpRequest signUpRequest){
		
		Session hbSession = getSession();
		Transaction tx = hbSession.beginTransaction();
		hbSession.saveOrUpdate(signUpRequest);
		tx.commit();
	}
	
	//get all requests in admin dashboard
	public List<SignUpRequest> fetchRequest(){
		Session hbSession = getSession();
		Transaction tx = hbSession.beginTransaction();
			String qryStr = "From SignUpRequest";
			Query qry = getSession().createQuery(qryStr);
			List<SignUpRequest> signUpRequests = qry.list();
			tx.commit();
			return signUpRequests;		
	}
	
	//get the request object which is being accepted
	public SignUpRequest checkRequest(int reqIdSelected){
		Session hbSession = getSession();
		Transaction tx = hbSession.beginTransaction();
		String qryStr = "From SignUpRequest where reqId = :reqIdSelected";
		Query qry = getSession().createQuery(qryStr);
		qry.setString("reqIdSelected", Integer.toString(reqIdSelected));
		SignUpRequest reqInDb = (SignUpRequest) qry.uniqueResult();
		tx.commit();
		return reqInDb;
	}
	
	//search user by email and check the role
	//if role is manager then fetch functional team
	//else give an error
	public User searchUserByEmail(String userEmail){
		Session hbSession = getSession();
		Transaction tx = hbSession.beginTransaction();
		String qryStr = "From User where email=:searchEmail";
		Query qry = getSession().createQuery(qryStr);
		qry.setString("searchEmail", userEmail);
		User userWithEmail = null;
		
		userWithEmail = (User) qry.uniqueResult();
		
		
		
		if(userWithEmail!=null){
			Hibernate.initialize(userWithEmail.getTeam());
		}
		tx.commit();
		
		
		return userWithEmail;
	}

}
