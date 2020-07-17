package com.vinegrad.dwp.dwplondonapp.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.vinegrad.dwp.dwplondonapp.model.User;
import com.vinegrad.dwp.dwplondonapp.service.UserService;


@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {
	
	private User user1;
	private User user2;
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;
	
	@Before
	public void setup() {
		user1 = new User(1, "John", "Doe", "john@doe.com", "1.0.0.0", 1.0, 1.0);
		user2 = new User(2, "Sally", "Smith", "sally@smith.com", "2.0.0.0", -5.0, 100.0);
	}
	
	@Test
	public void testUserControllerReturnsEmptyListWhenServiceReturnsEmptySet() {
		when(userService.getUsers()).thenReturn(new HashSet<>());
		assertEquals(0, userController.getUsersFromOrNearLondon().size());
		verify(userService, times(1)).getUsers();
	}
	
	@Test
	public void testUserControllerReturnsListWithOneUserWhenServiceReturnsOneUser() {
		when(userService.getUsers()).thenReturn(Set.of(user1));
		assertEquals(1, userController.getUsersFromOrNearLondon().size());
		verify(userService, times(1)).getUsers();
	}
	
	@Test
	public void testUserControllerReturnsListOrderedOnIdWithMultipleUsers() {
		when(userService.getUsers()).thenReturn(Set.of(user2, user1));
		List<User> list = userController.getUsersFromOrNearLondon();
		assertEquals(2, list.size());
		assertEquals(1, list.get(0).getId());
		assertEquals(2, list.get(1).getId());
		verify(userService, times(1)).getUsers();
	}

}
