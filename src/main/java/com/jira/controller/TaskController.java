package com.jira.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RequestMapping(value = "/tasks")
public class TaskController {
	
	@RequestMapping(method=RequestMethod.GET, value="/create")
	public String createTask() {
		
		return "create-task";
	}
}
