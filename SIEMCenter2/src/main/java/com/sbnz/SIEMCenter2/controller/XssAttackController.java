package com.sbnz.SIEMCenter2.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sbnz.SIEMCenter2.model.XssModel;

@RestController
@RequestMapping("/xss")
public class XssAttackController {
		
	@RequestMapping(value="/save", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public XssModel saveXss(@RequestBody  XssModel model){
		return model;
	
	
	}
	@RequestMapping(value="/get/{id}", method=RequestMethod.GET)
	public String saveXsss(@PathVariable String id){
		return id;
	
	
	}
}
