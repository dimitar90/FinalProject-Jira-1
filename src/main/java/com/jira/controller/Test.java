package com.jira.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Test {

	private final String prop;
	
	public Test(@Value("${log4j.appender.file.File}") String prp) {
		this.prop = prp;
		tst();
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void tst() {
		System.out.println(this.prop);
	}
}
