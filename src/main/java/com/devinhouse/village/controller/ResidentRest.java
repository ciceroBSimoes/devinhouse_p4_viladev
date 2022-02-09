package com.devinhouse.village.controller;

import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devinhouse.village.model.transport.ResidentDTO;
import com.devinhouse.village.service.ResidentService;

@RestController
@RequestMapping("/resident")
public class ResidentRest {

	private ResidentService residentService;

	public ResidentRest(ResidentService residentService) {
		this.residentService = residentService;
	}

	@GetMapping("/list-all")
	public HashMap<String, String> listAllResidents() throws SQLException {
		return residentService.listAll();
	}

	@GetMapping("/list-by-name/{name}")
	public HashMap<String, String> listResidentsByName(@PathVariable String name) throws SQLException {
		return residentService.listByName(name);
	}

	@GetMapping("/list-by-month/{month}")
	public HashMap<String, String> listResidentsByMonth(@PathVariable String month) throws SQLException {
		return residentService.listByMonth(month);
	}

	@GetMapping("/list-by-age/{age}")
	public HashMap<String, String> listResidentsByAge(@PathVariable Integer age) throws SQLException {
		return residentService.listByAge(age);
	}

	@GetMapping("/details/{id}")
	public ResidentDTO findById(@PathVariable Integer id) throws SQLException {
		return residentService.findById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) throws SQLException{
		return residentService.delete(id);
	}
	@PostMapping("/create")
	public ResponseEntity<HttpStatus> create(@RequestBody ResidentDTO resident) throws SQLException{
		return residentService.create(resident);
		
	}
	
}
