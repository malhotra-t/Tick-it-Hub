package com.tickithub.myproj.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tickithub.myproj.dao.FuncTeamDAO;
import com.tickithub.myproj.dao.ManageIrPrDAO;
import com.tickithub.myproj.dao.UserDAO;
import com.tickithub.myproj.pojo.FunctionalTeam;
import com.tickithub.myproj.pojo.Incident;
import com.tickithub.myproj.pojo.Problem;
import com.tickithub.myproj.pojo.SignUpRequest;
import com.tickithub.myproj.pojo.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserRegistrationController {
	@Autowired
	UserDAO userDao;

	@Autowired
	FuncTeamDAO funcTeamDao;
	
	@Autowired
	ManageIrPrDAO irPrDao;
	private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@ModelAttribute("user") User user) {

		return "home";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/signup.htm", method = RequestMethod.GET)
	public String signup(@ModelAttribute("signUpRequest") SignUpRequest signUpRequest) {

		return "signup";
	}

	@RequestMapping(value = "/signup.htm", method = RequestMethod.POST)
	public String saveuser(@ModelAttribute("signUpRequest") SignUpRequest signUpRequest,
			@ModelAttribute("user") User user) {
		signUpRequest.setSignUpStatus("Pending Acceptance");
		signUpRequest.setRaisedOn(new Date());
		userDao.saveRequest(signUpRequest);

		return "home";
	}
	
	//This method allows window.location to work. Everything we need in jsp is already in session when user logged in.
	@RequestMapping(value = "/dashboard.htm", method = RequestMethod.GET)
	public String irDash(@ModelAttribute("user") User user, HttpSession session, HttpServletRequest request) {

		
		return login(user, session, request);
	}

	@RequestMapping(value = "/dashboard.htm", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, HttpSession session, HttpServletRequest request) {
		
		User userInDb = null;
		
		if(session.getAttribute("loggedUser") == null){
			 userInDb = userDao.checkLogin(user);
		} else {
			userInDb = (User) session.getAttribute("loggedUser"); 
		}
		
		
		// 1 - check uname and password
		// 2 - if match found then check role
		// 4 - if admin, show admindashboard.jsp
		// 3 - else if manager or member, show irdashboard.jsp
		if (userInDb != null) {
			session.setAttribute("loggedUser", userInDb);
			if (userInDb.getRole().equalsIgnoreCase("admin")) {
				List<SignUpRequest> signRequestList = userDao.fetchRequest();
				request.setAttribute("signUps", signRequestList);
				return "admindashboard";
			} else if (userInDb.getRole().equalsIgnoreCase("manager") || userInDb.getRole().equalsIgnoreCase("member")) {
				// new incident list *big
				FunctionalTeam fteam = funcTeamDao.fetchById(userInDb.getTeam().getTeamId());
				List<Incident> bigList = new ArrayList<Incident>();
				List<Problem> prList = fteam.getProblemList();
				// loop on problem list
				for(Problem prob:prList){
					// get all incidents & add to new *big lit
					bigList.addAll(irPrDao.fetchIncidents(prob.getPrNum()).getIrList());
				}							
				session.setAttribute("irList", bigList);
				session.setAttribute("probList", prList);
				return "dashboard";
			}

		}

		return "loginfail";
	}

	@RequestMapping(value = "/prdashboard.htm", method = RequestMethod.GET)
	public String problemDash(@ModelAttribute("user") User user) {
		
		// prList already populated above for use in jsp
		
		return "prdashboard";
	}

	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public String logout(@ModelAttribute("user") User user, HttpSession session) {
		session.invalidate();
		return "home";
	}
	
	@RequestMapping(value = "/rejectUser.htm", method = RequestMethod.POST)
	@ResponseBody
	public String rejectUser(HttpServletRequest req){
		
		// 1 read reqId using req.getParameter("reqId")
		int reqIdSelected = Integer.parseInt(req.getParameter("reqId"));
		
		// 2 look up that reqId in db using session.get()
		SignUpRequest reqInDb = userDao.checkRequest(reqIdSelected);
		
		if(reqInDb!=null){
			// 3 set the request status to Rejected and save
			reqInDb.setSignUpStatus("Rejected");
			userDao.saveRequest(reqInDb);
			sendRejectEmailTo(reqInDb.getUserEmail());
			return "success";
		}
		// in case of failure return error
		return "request not found";
	}

	private void sendRejectEmailTo(String userEmail) {
		try {
        	Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("testaro9999@gmail.com", "AkkadBakkad"));
            email.setSSL(true);
			email.setFrom("testaro9999@gmail.com");
	        email.setSubject("SignUp Request Rejected!");
	        email.setMsg("Sorry! Your request has been rejected. Please raise another request!");
	        email.addTo(userEmail);
	        email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@RequestMapping(value = "/acceptUser.htm", method = RequestMethod.POST)
	@ResponseBody
	public String acceptUser(HttpServletRequest req) {

		// 1 read reqId using req.getParameter("reqId")
		int reqIdSelected = Integer.parseInt(req.getParameter("reqId"));
		// 2 look up that reqId in db using session.get()
		SignUpRequest reqInDb = userDao.checkRequest(reqIdSelected);
		// 3 copy user details into a new user object
		if (reqInDb != null) {
			User acceptedUser = new User();
			acceptedUser.setFname(reqInDb.getFname());
			acceptedUser.setLname(reqInDb.getLname());
			acceptedUser.setRole(reqInDb.getRole());
			acceptedUser.setEmail(reqInDb.getUserEmail());
			acceptedUser.setUname(reqInDb.getUname());
			acceptedUser.setPassword(reqInDb.getPassword());
			// 4 if the role of user who sent request is manager then create a new team
			//and add user to list of members.
			if (reqInDb.getRole().equalsIgnoreCase("manager")) {
				FunctionalTeam funcTeam = new FunctionalTeam();
				Problem pr = new Problem();
				pr.setPrPriority("P3");
				pr.setPrStatus("Open");
				pr.setPrDesc("Default");
				pr.setPrLogDate(new Date());
				//pr.setPrSlaDate(DateUtils.addDays(new Date(),29));
				pr.setPrLoggedBy(acceptedUser);
				pr.setTeam(funcTeam);
				funcTeam.getProblemList().add(pr);
				funcTeam.setTeamName(reqInDb.getUname());
				funcTeam.getTeamMembers().add(acceptedUser);
				acceptedUser.setTeam(funcTeam);
				try {
					funcTeamDao.saveTeam(funcTeam);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(reqInDb.getRole().equalsIgnoreCase("member")) {		//if user who sent request is a member
				// search user by manager email
				String userEmail = reqInDb.getManagerEmail();
				User userWithEmail = userDao.searchUserByEmail(userEmail);
				//if user found
				if (userWithEmail != null) {
					// if manager, set the team for acceptedUser and add acceptedUser to team's member list and save team.
					if (userWithEmail.getRole().equalsIgnoreCase("manager")) {
						acceptedUser.setTeam(userWithEmail.getTeam());
						int teamId = userWithEmail.getTeam().getTeamId();
						
						FunctionalTeam team = funcTeamDao.fetchById(teamId);
						team.getTeamMembers().add(acceptedUser);
						try {
							funcTeamDao.saveTeam(team);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {			//if the email is not of manager,send alert.
						return "email entered is not of a manager.";
					}

				} else {				//if no user found, send alert
					return "no such manager user exists in system";
				}
			}
			// 5 set the request status to Accepted and save
			reqInDb.setSignUpStatus("Accepted");
			userDao.saveRequest(reqInDb);
			
			sendAcceptEmailTo(reqInDb.getUserEmail());
			
			return "success";
		} else {
			// in case of failure return error
			return "request not found";
		}
	}

	private void sendAcceptEmailTo(String emailId) {
		
        try {
        	Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("testaro9999@gmail.com", "AkkadBakkad"));
            email.setSSL(true);
			email.setFrom("testaro9999@gmail.com");
	        email.setSubject("SignUp Request Accepted");
	        email.setMsg("Request Accepted. Please try to login!");
	        email.addTo(emailId);
	        email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
