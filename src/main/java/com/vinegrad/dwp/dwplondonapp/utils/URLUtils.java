package com.vinegrad.dwp.dwplondonapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class URLUtils {

	public String getJsonString(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String json = br.readLine();
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "[]";
	}
	
	
}
