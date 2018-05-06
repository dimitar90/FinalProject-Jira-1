package com.jira.controller;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jira.exception.DatabaseException;
import com.jira.exception.DateException;
import com.jira.interfaces.ITaskDao;
import com.jira.manager.UserManager;
import com.jira.model.User;

@RequestMapping(value ="/charts")
@Controller
public class ChartController {
	private static final String DATE_EXCEPTION_MESSAGE = "Тhe first date must be before the second";
	private static final String DATE_RANGE_BETWEEN_FORMAT = "The chart is for a period between %s";
	private static final String DATE_RANGE_BEFORE_FORMAT = "The chart is for a period before  %s";
	private static final String DATE_RANGE_AFTER_FORMAT = "The chart is for a period after %s";
	private static final String TWO_EQUAL_DATES_FORMAT = "The chart is for a %s";

	private final ITaskDao taskDao;
	private final UserManager userManager;
	@Autowired
	public ChartController(ITaskDao taskDao, UserManager userManager) {
		this.taskDao = taskDao;
		this.userManager = userManager;
	}
	
	@RequestMapping(method = RequestMethod.GET, value =  "/taskInfo")
	public String getTaskInfo(Model model, HttpSession session) {
		try {
			User loggedUser = this.userManager.getLoggedUser(session);
			if (loggedUser == null) {
				return "redirect:./../login";
			}
			
			Map<String, Integer> issues = this.taskDao.getCountForIssueTypes("", "");
			Map<String, Integer> states = this.taskDao.getCountForStateTypes("", "");
			String dateRangeFromFirstDateToEndDate = this.taskDao.getValidDataRangeForTaskCharts();

			this.setResultsFromMapToModel(model, issues, states);
			
			model.addAttribute("validDataRange", dateRangeFromFirstDateToEndDate);
			model.addAttribute("currentViewDateRange", String.format(DATE_RANGE_BETWEEN_FORMAT, dateRangeFromFirstDateToEndDate));

			return "task-charts";
		} catch (DatabaseException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value =  "/taskInfo")
	public String getTaskInfo(Model model, HttpServletRequest request, HttpSession session) {
		try {
			User loggedUser = this.userManager.getLoggedUser(session);
			if (loggedUser == null) {
				return "redirect:./../login";
			}
			
			String firstDate = request.getParameter("firstDate");
			String secondDate = request.getParameter("secondDate");
			
			if (!firstDate.isEmpty() && !secondDate.isEmpty() && Date.valueOf(firstDate).compareTo(Date.valueOf(secondDate)) > 0) {
				throw new DateException(DATE_EXCEPTION_MESSAGE);
			}
			
			Map<String, Integer> issues = this.taskDao.getCountForIssueTypes(firstDate, secondDate);
			Map<String, Integer> states = this.taskDao.getCountForStateTypes(firstDate,secondDate);
			
			this.setResultsFromMapToModel(model, issues, states);

			model.addAttribute("validDataRange", this.taskDao.getValidDataRangeForTaskCharts());
			model.addAttribute("currentViewDateRange", this.getCurrentViewDateRange(firstDate, secondDate));

			return "task-charts";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
	
	private void setResultsFromMapToModel(Model model, Map<String, Integer> issues, Map<String, Integer> states) {
		//check for any results for issues and states
		boolean isIssuesResults = this.checkForAnyResults(issues.values());
		boolean isStatesResults = this.checkForAnyResults(states.values());
		
		model.addAttribute("isIssuesResults", isIssuesResults);
		model.addAttribute("isStatesResults", isStatesResults);
		model.addAttribute("issues", issues);
		model.addAttribute("states", states);
	}
	
	private boolean checkForAnyResults(Collection<Integer> values) {
		boolean isThereAResult = false;
		
		for (Integer value : values) {
			if (value > 0) {
				isThereAResult = true;
				break;
			}
		}
		
		return isThereAResult;
	}

	private String getCurrentViewDateRange(String firstDate, String secondDate) throws DatabaseException {
		if (firstDate.isEmpty() && secondDate.isEmpty()) {
			return String.format(DATE_RANGE_BETWEEN_FORMAT, this.taskDao.getValidDataRangeForTaskCharts());
		}
		
		if (!firstDate.isEmpty() && !secondDate.isEmpty()) {
			if (Date.valueOf(firstDate).compareTo(Date.valueOf(secondDate)) == 0) {
				return String.format(TWO_EQUAL_DATES_FORMAT, firstDate);
			} else {
				return String.format(DATE_RANGE_BETWEEN_FORMAT, firstDate + " - " + secondDate);
			}
		}
		
		if (firstDate.isEmpty()) {
			return String.format(DATE_RANGE_BEFORE_FORMAT, secondDate);
		}
		
		return String.format(DATE_RANGE_AFTER_FORMAT, firstDate);
	}
}
