package com.tickithub.myproj.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tickithub.myproj.dao.FuncTeamDAO;
import com.tickithub.myproj.dao.ManageIrPrDAO;
import com.tickithub.myproj.dao.UserDAO;
import com.tickithub.myproj.pojo.FunctionalTeam;
import com.tickithub.myproj.pojo.Incident;
import com.tickithub.myproj.pojo.Problem;
import com.tickithub.myproj.pojo.User;

@Controller
public class ManageIrPrController {

	@Autowired
	ManageIrPrDAO irprDAO;
	
	@Autowired
	UserDAO userDao;

	@Autowired
	FuncTeamDAO funcTeamDao;

	@RequestMapping(value = "/createIr.htm", method = RequestMethod.POST)
	@ResponseBody
	public String createIncident(HttpServletRequest req, HttpSession session) {

		/*
		 * if(req!=null){ return "failure"; //test for runtime exception }
		 */
		
		User userinSession = (User) session.getAttribute("loggedUser");

		if (userinSession == null) {
			return "home";

		}
		
		String priority = req.getParameter("irPriority");
		String status = req.getParameter("irStatus");
		String desc = req.getParameter("irDesc");

		Incident ir = new Incident();
		ir.setIrPriority(priority);
		ir.setIrStatus(status);
		ir.setIrDesc(desc);

		ir.setIrLogDate(new Date());

		if (ir.getIrPriority().equalsIgnoreCase("P1")) {
			ir.setSlaDate(DateUtils.addDays(new Date(), 1));
		} else if (ir.getIrPriority().equalsIgnoreCase("P2")) {
			ir.setSlaDate(DateUtils.addDays(new Date(), 2));
		} else if (ir.getIrPriority().equalsIgnoreCase("P3")) {
			ir.setSlaDate(DateUtils.addDays(new Date(), 3));
		}

		ir.setIrLoggedBy(userinSession);
		FunctionalTeam fteam = funcTeamDao.fetchById(userinSession.getTeam().getTeamId());
		for (Problem pr : fteam.getProblemList()) {
			String description = pr.getPrDesc();
			if (description.equalsIgnoreCase("Default")) {
				ir.setProblem(pr);
				break;
			}
		}
		try {
			irprDAO.saveIncident(ir);
			return "success";
		} catch (Exception e) {

			return "failure";
		}

	}

	@RequestMapping(value = "/createPr.htm", method = RequestMethod.POST)
	@ResponseBody
	public String createPr(HttpServletRequest req, HttpSession session) {

		User userinSession = (User) session.getAttribute("loggedUser");

		if (userinSession == null) {
			return "home";

		}
		
		String priority = req.getParameter("prPriority");
		String status = req.getParameter("prStatus");
		String desc = req.getParameter("prDesc");

		Problem pr = new Problem();
		pr.setPrPriority(priority);
		pr.setPrStatus(status);
		pr.setPrDesc(desc);

		pr.setPrLogDate(new Date());

		if (pr.getPrPriority().equalsIgnoreCase("P1")) {
			pr.setPrSlaDate(DateUtils.addDays(new Date(), 5));
		} else if (pr.getPrPriority().equalsIgnoreCase("P2")) {
			pr.setPrSlaDate(DateUtils.addDays(new Date(), 20));
		} else if (pr.getPrPriority().equalsIgnoreCase("P3")) {
			pr.setPrSlaDate(DateUtils.addDays(new Date(), 40));
		}

		pr.setPrLoggedBy(userinSession);

		FunctionalTeam fteam = funcTeamDao.fetchById(userinSession.getTeam().getTeamId());
		pr.setTeam(fteam);
		fteam.getProblemList().add(pr);
		try {
			funcTeamDao.saveTeam(fteam);
			session.setAttribute("probList", fteam.getProblemList());
			return "success";
		} catch (Exception e) {
			return "failure";
		}

	}

	// get all the functional teams from database to display in modal for
	// assigning IR
	@RequestMapping(value = "/getFuncTeams.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getTeamName() {
		List<FunctionalTeam> teamList = funcTeamDao.getAllTeams();

		StringBuilder strBuild = new StringBuilder("");
		for (FunctionalTeam ft : teamList) {

			strBuild.append(ft.getTeamId());
			strBuild.append("-");
			strBuild.append(ft.getTeamName());
			strBuild.append(",");
		}
		int lastCommaPos = strBuild.lastIndexOf(",");
		strBuild = strBuild.deleteCharAt(lastCommaPos);
		return strBuild.toString();
	}

	//assign selected ir to another team
	@RequestMapping(value = "/asgnIrToTeam.htm", method = RequestMethod.POST)
	@ResponseBody
	public String assignIrToTeam(HttpServletRequest req) {

		int selectedIrNum = Integer.parseInt(req.getParameter("irId"));
		String selectedTeam = req.getParameter("asgnToTeamId");
		String[] strArr = selectedTeam.split("-");
		int asgnToTeamId = Integer.parseInt(strArr[0]);

		FunctionalTeam targetTeam = funcTeamDao.fetchById(asgnToTeamId);
		List<Problem> targetPrList = targetTeam.getProblemList();
		for (Problem targetPr : targetPrList) {
			if (targetPr.getPrDesc().equalsIgnoreCase("Default")) {

				int targetPrId = targetPr.getPrNum();
				irprDAO.assignIrToNewPr(selectedIrNum, targetPrId);
				break;
			}
		}
				
		return "success";
		
	}
	
	//assign selected ir to another problem within same team
	@RequestMapping(value = "/assignIr.htm", method = RequestMethod.POST)
	@ResponseBody
	public String tagToPr(HttpServletRequest req) {
		try{
			Integer selectedIrNum = Integer.parseInt(req.getParameter("selectedIrToAsgnVar"));
			Integer selectedPr = Integer.parseInt(req.getParameter("prTagVar"));
			irprDAO.assignIrToNewPr(selectedIrNum, selectedPr);
			return "success";
		}catch(NumberFormatException e){
			return "Enter a numeric value";
		}catch(Exception e){
			return "failure";
		}
		
	}
	
	//close selected incident
	@RequestMapping(value = "/closeIr.htm", method = RequestMethod.POST)
	@ResponseBody
	public String closeSelIr(HttpServletRequest req, HttpSession session) {
		
		User userinSession = (User) session.getAttribute("loggedUser");

		if (userinSession == null) {
			return "home";

		}

		int selIrNum = Integer.parseInt(req.getParameter("selIr"));
		Incident irToClose = irprDAO.getSelectedIr(selIrNum);
		irToClose.setIrResolvedBy(userinSession);
		irToClose.setIrStatus("Closed");
		try {
			irprDAO.saveIncident(irToClose);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failure";
		}
		
	}
	
	//close selected problem and all incidents under it
	@RequestMapping(value = "/closePr.htm", method = RequestMethod.POST)
	@ResponseBody
	public String closeSelPr(HttpServletRequest req, HttpSession session) {
		
		User userinSession = (User) session.getAttribute("loggedUser");

		if (userinSession == null) {
			return "home";

		}

		int selPrNum = Integer.parseInt(req.getParameter("selPr"));
		Problem prToClose = irprDAO.fetchIncidents(selPrNum);
		prToClose.setPrResolvedBy(userinSession);
		prToClose.setPrStatus("Closed");
		List<Incident> relatedIrs = prToClose.getIrList();
		for(Incident ir:relatedIrs){
			ir.setIrResolvedBy(userinSession);
			ir.setIrStatus("Closed");
			User irOwner = ir.getIrLoggedBy();
			sendClosureEmail(irOwner.getEmail());
		}
		try {
			irprDAO.saveProblem(prToClose);
			
			
			// get other probs of team and update in session
			
			FunctionalTeam team = funcTeamDao.fetchById(prToClose.getTeam().getTeamId());
			
			session.setAttribute("probList", team.getProblemList());
			
			sendClosureEmail(prToClose.getPrLoggedBy().getEmail());
			
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failure";
		}
		
	}

	private void sendClosureEmail(String ownerEmail) {
		
		try {
        	Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("testaro9999@gmail.com", "AkkadBakkad"));
            email.setSSL(true);
			email.setFrom("testaro9999@gmail.com");
	        email.setSubject("Incident Closure!");
	        email.setMsg("The incident raied by you has been closed!");
	        email.addTo(ownerEmail);
	        email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
