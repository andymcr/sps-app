package org.salephoto.models;

import java.io.Serializable;


public class Location {
	private double latitude;
	private double longitude;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(final double newLatitude) {
		latitude = newLatitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(final double newLongitude) {
		longitude = newLongitude;
	}

}
