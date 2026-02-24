package com.ds.studenterp.controller;

import java.util.Optional;

import com.ds.studenterp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds.studenterp.entity.Admin;

@CrossOrigin(origins = "https://localhost:5173")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/login")
	public Admin login(@RequestBody Admin admin){
		return adminService.login(admin.getEmail(), admin.getPassword());
	}
}
