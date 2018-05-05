package com.jira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value="/charts")
@Controller
public class ChartController {
	
	@RequestMapping(method = RequestMethod.GET, value="/show")
	public String showCharts() {
		return "charts";
	}
}
