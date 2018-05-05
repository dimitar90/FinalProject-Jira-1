package com.jira.interfaces;

import javax.servlet.ServletOutputStream;

import org.springframework.stereotype.Component;

@Component
public interface IExportDao {
	public void exportIntoPdf(ServletOutputStream os);
}
