package com.vinegrad.dwp.dwplondonapp.utils;

import static java.lang.Math.*;
import static com.vinegrad.dwp.dwplondonapp.utils.Constants.*;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.vinegrad.dwp.dwplondonapp.model.User;

@Component
public class CalcUtils {
	
	public Set<User> getUsersFromString(String in) {
		Set<User> users = new HashSet<>();
		JSONArray array = new JSONArray(in);
		for(int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			users.add(getUserFromJSON(object));
		}
		return users;
	}

	public Set<User> getUsersFromStringWithin50(String in) {
		Set<User> users = new HashSet<>();
		JSONArray array = new JSONArray(in);
		for(int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			double latitude = object.getDouble("latitude");
			double longitude = object.getDouble("longitude");
			if(areCoordinatesInRange(latitude, longitude)) {
				users.add(getUserFromJSON(object));
			}
		}
		return users;
	}
	
	User getUserFromJSON(JSONObject object) {
		double latitude = object.getDouble("latitude");
		double longitude = object.getDouble("longitude");
		long id = object.getLong("id");
		String firstName = object.getString("first_name");
		String lastName = object.getString("last_name");
		String email = object.getString("email");
		String ipAddress = object.getString("ip_address");
		return new User(id, firstName, lastName, email, ipAddress, latitude, longitude);
	}

	boolean areCoordinatesInRange(double latitude, double longitude) {
		return haversine(latitude, LONDON_LATITUDE, longitude, LONDON_LONGITUDE) <= 50;
	}
	
	double haversine(double lat1, double lat2, double long1, double long2) {
		double lat1Rad = toRadians(lat1);
		double lat2Rad = toRadians(lat2);
		double hav1 = hav(lat2Rad, lat1Rad);
		double cos1 = cos(lat1Rad);
		double cos2 = cos(lat2Rad);
		double hav2 = hav(toRadians(long2), toRadians(long1));
		double arcsinSqrt = asin(sqrt(hav1 + cos1 * cos2 * hav2));
		return 2 * 3958.8 * arcsinSqrt;
		
	}
	
	double hav(double num1, double num2) {
		return pow(sin((num1 - num2) / 2), 2);
	}
	
	double toRadians(double degrees) {
		return degrees * (PI / 180);
	}
}
