package com.jira.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jira.db.DBManager;
import com.jira.exception.DatabaseException;
import com.jira.interfaces.IExportDao;

@Controller
@RequestMapping(value = "/export")
public class ExportController {
	
	private final IExportDao exportDao;
	
	public ExportController(IExportDao exportDao) {
		this.exportDao = exportDao;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pdf")
	public void exportTasksIntoPdf(HttpServletResponse response) throws DatabaseException, IOException {
		try {
			ServletOutputStream os = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + "allTasks.pdf" + "\"");
			this.exportDao.exportIntoPdf(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
