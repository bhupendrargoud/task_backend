package com.gavika.payroll.controller;



	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.RestController;

	@RestController
	public class WelcomeController {

	    @GetMapping("/")
	    public String welcome() {
	        return "Hello from the backend of the Payroll application @gavika from jenkins";
	    }
	}


