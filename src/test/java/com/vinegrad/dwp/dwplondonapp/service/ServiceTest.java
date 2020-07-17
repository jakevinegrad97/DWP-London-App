package com.vinegrad.dwp.dwplondonapp.service;

import static com.vinegrad.dwp.dwplondonapp.utils.Constants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.vinegrad.dwp.dwplondonapp.model.User;
import com.vinegrad.dwp.dwplondonapp.utils.CalcUtils;
import com.vinegrad.dwp.dwplondonapp.utils.URLUtils;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {

	private User londoner1;
	private User londoner2;
	private User nearLondoner1;
	private User nearLondoner2;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private URLUtils urlUtils;
	
	@Mock
	private CalcUtils calcUtils;
	
	@Before
	public void setup() {
		londoner1 = new User(1, "John", "Doe", "john@doe.com", "1.0.0.0", 1.0, 1.0);
		londoner2 = new User(2, "Sally", "Smith", "sally@smith.com", "2.0.0.0", -5.0, 100.0);
		nearLondoner1 = new User(3, "Bill", "Kee", "bill@kee.com", "1.5.0.0", LONDON_LATITUDE + 0.1, LONDON_LONGITUDE + 1.0);
		nearLondoner1 = new User(4, "Angela", "Perkins", "angie@p.com", "8.5.0.0", LONDON_LATITUDE + 0.1, LONDON_LONGITUDE - 1.0);
		when(urlUtils.getJsonString(LONDON_URL)).thenReturn("test1");
		when(urlUtils.getJsonString(USERS_URL)).thenReturn("test2");
	}
	
	@Test
	public void testEmptySetReturnedWithNoData() {
		when(calcUtils.getUsersFromString("test1")).thenReturn(new HashSet<>());
		when(calcUtils.getUsersFromStringWithin50("test2")).thenReturn(new HashSet<>());
		
		assertEquals(0, userService.getUsers().size());
		
		verify(calcUtils, times(1)).getUsersFromString("test1");
		verify(calcUtils, times(1)).getUsersFromStringWithin50("test2");
	}
	
	@Test
	public void testCorrectSetReturnedWithOnlyPeopleWhoAreFromLondon() {
		Set<User> set = new HashSet<>();
		set.add(londoner1);
		set.add(londoner2);
		when(calcUtils.getUsersFromString("test1")).thenReturn(set);
		when(calcUtils.getUsersFromStringWithin50("test2")).thenReturn(new HashSet<>());
		
		assertEquals(2, userService.getUsers().size());
		
		verify(calcUtils, times(1)).getUsersFromString("test1");
		verify(calcUtils, times(1)).getUsersFromStringWithin50("test2");
	}
	
	@Test
	public void testCorrectSetReturnedWithOnlyPeopleWhoAreCurrentlyInLondon() {
		when(calcUtils.getUsersFromString("test1")).thenReturn(new HashSet<>());
		Set<User> set = new HashSet<>();
		set.add(nearLondoner1);
		set.add(nearLondoner2);
		when(calcUtils.getUsersFromStringWithin50("test2")).thenReturn(set);
		
		assertEquals(2, userService.getUsers().size());
		
		verify(calcUtils, times(1)).getUsersFromString("test1");
		verify(calcUtils, times(1)).getUsersFromStringWithin50("test2");
	}
	
	@Test
	public void testCorrectSetReturnedWithPeopleFromOrInLondon() {
		Set<User> set1 = new HashSet<>();
		set1.add(londoner1);
		set1.add(londoner2);
		Set<User> set2 = new HashSet<>();
		set2.add(nearLondoner1);
		set2.add(nearLondoner2);
		when(calcUtils.getUsersFromString("test1")).thenReturn(set1);
		when(calcUtils.getUsersFromStringWithin50("test2")).thenReturn(set2);
		
		assertEquals(4, userService.getUsers().size());
		
		verify(calcUtils, times(1)).getUsersFromString("test1");
		verify(calcUtils, times(1)).getUsersFromStringWithin50("test2");
	}
	
	@Test
	public void testDuplicatePersonIsntCountedTwice() {
		Set<User> set1 = new HashSet<>();
		set1.add(londoner1);
		set1.add(londoner2);
		Set<User> set2 = new HashSet<>();
		set2.add(nearLondoner1);
		set2.add(nearLondoner2);
		set2.add(londoner2);
		when(calcUtils.getUsersFromString("test1")).thenReturn(set1);
		when(calcUtils.getUsersFromStringWithin50("test2")).thenReturn(set2);
		
		assertEquals(4, userService.getUsers().size());
		
		verify(calcUtils, times(1)).getUsersFromString("test1");
		verify(calcUtils, times(1)).getUsersFromStringWithin50("test2");
	}
	
	@Test
	public void testEmptySetReturnedWhenJsonIsEmpty() {
		when(urlUtils.getJsonString(LONDON_URL)).thenReturn("[]");
		when(urlUtils.getJsonString(USERS_URL)).thenReturn("[]");
		
		assertEquals(0, userService.getUsers().size());
		
		verify(calcUtils, times(0)).getUsersFromString("[]");
		verify(calcUtils, times(0)).getUsersFromStringWithin50("[]");
	}
	
	@After
	public void afterEachTest() {
		verify(urlUtils, times(1)).getJsonString(LONDON_URL);
		verify(urlUtils, times(1)).getJsonString(USERS_URL);
	}
	
	
}
