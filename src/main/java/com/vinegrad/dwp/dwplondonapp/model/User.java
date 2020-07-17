package com.vinegrad.dwp.dwplondonapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Comparable<User> {

	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String ipAddress;
	private double latitude;
	private double longitude;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public int compareTo(User o) {
		return (int) (id - o.id);
	}
	
	
}
