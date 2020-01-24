package com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.entity.OrderEntity;
import com.java.model.ResponseModel;
import com.java.service.MySqlSerivce;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api")
public class Controller {
	
	private Gson gson = new GsonBuilder().create();
	
	@Autowired
	private MySqlSerivce mySqlSerivce;
	
	@GetMapping(value = "/saveEntity")
	public void saveEntity() {
		mySqlSerivce.saveEntity();
	}

	@GetMapping(value = "/jdbcTemplateTest", produces = MediaType.APPLICATION_JSON_VALUE)
	public String jdbcTemplateTest() {
		List<OrderEntity> list = null;
		try {
			list = mySqlSerivce.jdbcTemplateTest();
		} catch (Exception e) {
			log.error("發生error reason : " + e);
			ResponseModel responseModel = new ResponseModel();
			responseModel.setErrorMsg("發生error reason : " + e);
			return gson.toJson(responseModel);
		}
		return gson.toJson(list);
	}
	
	@GetMapping(value = "/repositoryTest", produces = MediaType.APPLICATION_JSON_VALUE)
	public String repositoryTest() {
		List<OrderEntity> list = null;
		try {
			list = mySqlSerivce.repositoryTest();
		} catch (Exception e) {
			log.error("發生error reason : " + e);
			ResponseModel responseModel = new ResponseModel();
			responseModel.setErrorMsg("發生error reason : " + e);
			return gson.toJson(responseModel);
		}
		return gson.toJson(list);
	}
	
}
