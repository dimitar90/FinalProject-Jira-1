package com.jira.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.jira.exception.ExportException;
import com.jira.exception.UserDataException;
import com.jira.interfaces.IExportDao;
import com.jira.manager.UserManager;
import com.jira.model.User;

@Controller
@RequestMapping(value = "/export")
public class ExportController {
	private static final String NOT_LOGGED_MESSAGE = "You must logged in if you want download pdf with tasks!";
	private static final String SUCCESSFULLY_DOWNLOAD_PDF_MESSAGE = "User %s with id:%d successfully downloaded pdf file with all tasks.";
			
    private static final Logger logger = LogManager.getLogger(ExportController.class);
	private final IExportDao exportDao;
	private final UserManager userManager;

	@Autowired
	public ExportController(IExportDao exportDao, UserManager userManager) {
		this.exportDao = exportDao;
		this.userManager = userManager;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pdf")
	public String exportTasksIntoPdf(HttpServletResponse response, HttpSession session, Model model) throws DatabaseException, IOException {
		try {
			User loggedUser = this.userManager.getLoggedUser(session);
			if (loggedUser == null) {
				throw new ExportException(NOT_LOGGED_MESSAGE);
			}
			
			ServletOutputStream os = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + "allTasks.pdf" + "\"");
			this.exportDao.exportIntoPdf(os);
			
			logger.info(String.format(SUCCESSFULLY_DOWNLOAD_PDF_MESSAGE, loggedUser.getName(), loggedUser.getId()));
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			logger.error(e);
			return "error";
		}
	}
}
