package com.vinegrad.dwp.dwplondonapp.utils;

import static com.vinegrad.dwp.dwplondonapp.utils.Constants.*;

import static org.junit.Assert.*;

import java.util.Set;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.vinegrad.dwp.dwplondonapp.model.User;

public class CalcUtilsTest {

	private CalcUtils calcUtils;
	private String json;
	
	@Before
	public void setup() {
		calcUtils = new CalcUtils();
		json = "[{\"id\": 1, \"first_name\": \"Maurise\", \"last_name\": \"Shieldon\", \"email\": \"mshieldon0@squidoo.com\", \"ip_address\": \"192.57.232.111\", \"latitude\": 34.003135, \"longitude\": -117.7228641}, {\"id\": 2, \"first_name\": \"Bendix\", \"last_name\": \"Halgarth\", \"email\": \"bhalgarth1@timesonline.co.uk\", \"ip_address\": \"4.185.73.82\", \"latitude\": -2.9623869, \"longitude\": 104.7399789}, {\"id\": 3, \"first_name\": \"Meghan\", \"last_name\": \"Southall\", \"email\": \"msouthall2@ihg.com\", \"ip_address\": \"21.243.184.215\", \"latitude\": \"51.5489435\", \"longitude\": \"0.3860497\"}]";
	}
	
	@Test
	public void testGetUsersFromStringReturnsCorrectNumberOfUsers() {
		Set<User> users = calcUtils.getUsersFromString(json);
		assertEquals(3, users.size());
	}
	
	@Test
	public void testGetUsersFromStringWithin50ExcludesThoseNotWithin50Miles() {
		Set<User> users = calcUtils.getUsersFromStringWithin50(json);
		assertEquals(1, users.size());
	}
	
	@Test
	public void testGetUserFromJSONReturnsUser() {
		JSONObject jsonObject = new JSONObject("{\"id\": 1, \"first_name\": \"Maurise\", \"last_name\": \"Shieldon\", \"email\": \"mshieldon0@squidoo.com\", \"ip_address\": \"192.57.232.111\", \"latitude\": 34.003135, \"longitude\": -117.7228641}");
		User user = calcUtils.getUserFromJSON(jsonObject);
		assertEquals(1, user.getId());
		assertEquals("Maurise", user.getFirstName());
		assertEquals("Shieldon", user.getLastName());
		assertEquals("mshieldon0@squidoo.com", user.getEmail());
		assertEquals("192.57.232.111", user.getIpAddress());
		assertEquals(34.003135, user.getLatitude(), 0.01);
		assertEquals(-117.7228641, user.getLongitude(), 0.01);
	}
	
	@Test
	public void testAreCoordinatesInRangeWhenCoordinatesAreInRange() {
		assertTrue(calcUtils.areCoordinatesInRange(51.5, 0.39));
	}
	
	@Test
	public void testAreCoordinatesInRangeWhenCoordinatesAreNotInRange() {
		assertFalse(calcUtils.areCoordinatesInRange(100, -100));
	}
	
	@Test
	public void testHaversineFunctionReturnCorrectValue() {
		assertEquals(11.288, calcUtils.haversine(51.5, LONDON_LATITUDE, 0.39, LONDON_LONGITUDE), 0.01);
	}
	
	@Test
	public void testHavMethodReturnsCorrectValue() {
		double expected = Math.pow(Math.sin(5), 2);
		double actual = calcUtils.hav(11, 1);
		assertEquals(expected, actual, 0.01);
	}
	
	@Test
	public void testDegreesToRadians() {
		assertEquals(2 * Math.PI, calcUtils.toRadians(360), 0.01);
		assertEquals(Math.PI / 4, calcUtils.toRadians(45), 0.01);
		assertEquals(- 1 * Math.PI / 2, calcUtils.toRadians(-90), 0.01);
	}
}
