package com.vinegrad.dwp.dwplondonapp.service;

import static com.vinegrad.dwp.dwplondonapp.utils.Constants.*;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinegrad.dwp.dwplondonapp.model.User;
import com.vinegrad.dwp.dwplondonapp.utils.CalcUtils;
import com.vinegrad.dwp.dwplondonapp.utils.URLUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private URLUtils urlUtils;
	
	@Autowired
	private CalcUtils calcUtils;
	
	public UserServiceImpl(URLUtils urlUtils, CalcUtils calcUtils) {
		this.urlUtils = urlUtils;
		this.calcUtils = calcUtils;
	}

	@Override
	public Set<User> getUsers() {
		Set<User> users = getUsersFromURL(LONDON_URL, false);
		Set<User> usersNear = getUsersFromURL(USERS_URL, true);
		users.addAll(usersNear);
		return users;
	}
	
	private Set<User> getUsersFromURL(String urlString, boolean within50) {
		String json = urlUtils.getJsonString(urlString);
		if(json.equals("[]")) {
			return new HashSet<>();
		}
		if(within50) {
			return calcUtils.getUsersFromStringWithin50(json);	
		} else {
			return calcUtils.getUsersFromString(json);
		}
	}
}
