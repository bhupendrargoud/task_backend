package com.gavika.payroll.controller;



	import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.RestController;
	@CrossOrigin(origins = "*")
	@RestController
	public class WelcomeController {

	    @GetMapping("/")
	    public String welcome() {
	        return " Payroll application @gavika ";
	    }
	}


