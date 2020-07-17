package com.vinegrad.dwp.dwplondonapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinegrad.dwp.dwplondonapp.model.User;
import com.vinegrad.dwp.dwplondonapp.service.UserService;

@RestController
@RequestMapping("/getUsers")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public List<User> getUsersFromOrNearLondon() {
		return service.getUsers()
				.stream()
				.sorted((u1, u2) -> u1.compareTo(u2))
				.collect(Collectors.toList());
	}
	
	
}